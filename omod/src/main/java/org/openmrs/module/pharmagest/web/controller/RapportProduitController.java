/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.metier.ContaintStock;
import org.openmrs.module.pharmagest.metier.FormulairePeriode;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.GestionAlert;
import org.openmrs.module.pharmagest.metier.HistoMvmResponseJson;
import org.openmrs.module.pharmagest.metier.HistoMvmType;
import org.openmrs.module.pharmagest.validator.RapportMvmValidator;
import org.openmrs.module.pharmagest.validator.TraitementPPSValidatorAjout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulaireProduit")
public class RapportProduitController {
	
	@Autowired
	RapportMvmValidator rapportMvmValidator;
	

	@RequestMapping(value = "/module/pharmagest/rapportPPI.form", method = RequestMethod.GET)
	public String initHistoMvm(ModelMap model) {
		
		FormulaireProduit formulaireProduit= new FormulaireProduit();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireProduit", formulaireProduit);	
		model.addAttribute("var", "0");

		return "/module/pharmagest/rapportPPI";
	}
	
	@RequestMapping(value = "/module/pharmagest/rapportPPI.form",method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit,
			BindingResult result, HttpSession session, ModelMap model) {

		rapportMvmValidator.validate(formulaireProduit, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireProduit", formulaireProduit);

		if (!result.hasErrors()) {
			
			ArrayList<Integer>listTypeOpe=this.getTypeOperation(formulaireProduit.getTypeRapport());
			Collection<PharmOperation> listOperations = new ArrayList<PharmOperation>() ;
			for (Integer typeId : listTypeOpe) {
				Collection<PharmOperation> list=Context.getService(OperationService.class).getPharmOperationsByAttri(formulaireProduit.getProgramme(),formulaireProduit.getDateDebut()
						, formulaireProduit.getDateFin(),Context.getService(ParametresService.class).getTypeOperationById(typeId) );
				Iterator it=list.iterator();
				while (listOperations!=null && it.hasNext()){
					PharmOperation operation = (PharmOperation) it.next();
				    listOperations.add(operation);
				    }
			}
			
			
			if( listOperations.isEmpty()){
				model.addAttribute("var", "0");
				model.addAttribute("mess", "vide");
			}else {
				model.addAttribute("listProduits", this.extratLigneOperation(listOperations));
				model.addAttribute("var", "1");
			}
			
			Location defaultLocation = Context.getLocationService().getDefaultLocation();
			model.addAttribute("site", defaultLocation);

			

		}else{
			model.addAttribute("var", "0");
		}

	}
	public Collection<PharmLigneOperation> extratLigneOperation(Collection<PharmOperation> operations){
		Collection<PharmLigneOperation> ligneoperations=new ArrayList<PharmLigneOperation>();
		for (PharmOperation operation : operations) {
			Iterator it=operation.getPharmLigneOperations().iterator();
			while (it.hasNext()){
				PharmLigneOperation ligne = (PharmLigneOperation) it.next();
				ligneoperations.add(ligne);
			    }
		}		
		return ligneoperations;
	}
	public ArrayList<Integer> getTypeOperation(String choix){
		ArrayList<Integer> arrayType=null;
		if(choix.equals("perte")){
			arrayType=new ArrayList<Integer>();
			arrayType.add(6);arrayType.add(7);arrayType.add(10);arrayType.add(11);arrayType.add(12);	
		} else if(choix.equals("perime")){
			arrayType=new ArrayList<Integer>();
			arrayType.add(7);
		}else if(choix.equals("ajustement")){
			arrayType=new ArrayList<Integer>();
			arrayType.add(3);arrayType.add(4);arrayType.add(5);arrayType.add(13);arrayType.add(14);
			arrayType.add(15);arrayType.add(16);arrayType.add(17);arrayType.add(18);
		}
		return  arrayType;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		binder.registerCustomEditor(PharmProduit.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				int nbr = 0;
				NumberFormat format = NumberFormat.getInstance();
				try {
					nbr = format.parse(text).intValue();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PharmProduit produit = Context.getService(ParametresService.class).getProduitById(nbr);
				this.setValue(produit);
			}
		});

		

		binder.registerCustomEditor(PharmProgramme.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmProgramme programme = Context.getService(ParametresService.class)
						.getProgrammeById(Integer.parseInt(text));
				this.setValue(programme);
			}
		});

	}

}
