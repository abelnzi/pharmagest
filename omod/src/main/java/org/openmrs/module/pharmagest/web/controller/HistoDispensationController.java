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
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.ConsoByProduit;
import org.openmrs.module.pharmagest.metier.FormulairePeriode;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireSite;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.metier.RapportConsoBean;
import org.openmrs.module.pharmagest.metier.StockBean;
import org.openmrs.module.pharmagest.web.view.Book;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

public class HistoDispensationController {


	@RequestMapping(value = "/module/pharmagest/histoDispensation.form", method = RequestMethod.GET)
	public String initConsoProduitJour(ModelMap model) {
		FormulairePeriode formulairePeriode = new FormulairePeriode();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("programmes", programmes);

		model.addAttribute("formulairePeriode", formulairePeriode);

		return "/module/pharmagest/histoDispensation";
	}

	@RequestMapping(value = "/module/pharmagest/histoDispensation.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void consoProduitJour(@ModelAttribute("formulairePeriode") FormulairePeriode formulairePeriode,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);
			
			model.addAttribute("formulairePeriode", formulairePeriode);

			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
			model.addAttribute("periode", periode);

			if (!result.hasErrors()) {				
				Collection<PharmOrdonnance> listDispensation = Context.getService(DispensationService.class)
						.getPharmOrdonnanceByPeriod(formulairePeriode.getDateDebut(),formulairePeriode.getDateFin(),formulairePeriode.getProgramme(),null);
					if (listDispensation.size()!=0){
						model.addAttribute("listDispensation", this.dispensCorrespondance(listDispensation));
						model.addAttribute("var", 1);
					}else{
						model.addAttribute("var", 2);
					}
				
				}
				

		/*} catch (Exception e) {
			e.getMessage();
		}*/

	}
	
	public Collection<PharmOrdonnance> dispensCorrespondance(Collection<PharmOrdonnance>  listDispensation) {
		
		Collection<PharmOrdonnance> listOrd = new ArrayList<PharmOrdonnance>();
        System.out.println("==============listDispensation==============="+listDispensation.size());
		Iterator it = listDispensation.iterator();
		while (it.hasNext()) {
			PharmOrdonnance ligne = (PharmOrdonnance) it.next();
			PatientIdentifier patientIdentifier=ligne.getPatientIdentifier();
			if(patientIdentifier!=null) {
				Context.addProxyPrivilege("View People");
				Person person = Context.getPersonService().getPerson(patientIdentifier.getPatient());
				Context.removeProxyPrivilege("View People");
				ligne.setOrdAge(person.getAge());
				ligne.setOrdGenre(person.getGender());
				ligne.setOrdNum(patientIdentifier.getIdentifier());
				
				//determiner le poids
				Encounter e=Context.getService(DispensationService.class).
						getLastEncounter(person.getPersonId(), ligne.getOrdDateDispen());
				if(e!=null) {
					Obs o=Context.getService(DispensationService.class).getObs(e.getEncounterId(), 5089, person.getPersonId());
					if(o!=null)ligne.setOrdService(""+o.getValueNumeric());					
				}
				
			}
			//determiner le nombre de jours de traitement restant
			Date dateDispens=ligne.getOrdDateDispen();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateDispens);
			calendar.add(Calendar.DATE, ligne.getOrdNbreJrsTrai());
			long oneDay= (1000*60*60*24);
			Date finTraitement=calendar.getTime();
			Date today=new Date();				
			long restJour= (finTraitement.getTime()-today.getTime())/oneDay;
			ligne.setOrdHospi(""+restJour);
			
			listOrd.add(ligne);
		}		
		
		return listOrd;		
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

		binder.registerCustomEditor(PharmRegime.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmRegime regime = Context.getService(ParametresService.class).getRegimeById(Integer.parseInt(text));
				this.setValue(regime);
			}
		});
		binder.registerCustomEditor(PharmMedecin.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmMedecin medecin = Context.getService(ParametresService.class)
						.getMedecinById(Integer.parseInt(text));
				this.setValue(medecin);
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
