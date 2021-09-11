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

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
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

public class RapportStockController {

	//@RequestMapping(value = "/module/pharmagest/rapportStockTotal.form", method = RequestMethod.GET)
	/*public String stockTotal(ModelMap model) {

		Collection<PharmStocker> listStocks = Context.getService(GestionStockService.class).getAllPharmStockers();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		model.addAttribute("dateRap", "au : "+formatter.format(new Date()));
		model.addAttribute("listStocks", listStocks);
		//model.addAttribute("listStocksProduit", this.traiterListStock(listStocks));
		return "/module/pharmagest/rapportStockTotal";
	}*/
	
	@RequestMapping(value = "/module/pharmagest/rapportStockTotal.form", method = RequestMethod.GET)
	public String stockTotalGet(ModelMap model) {
		FormulaireProduit formulaireProduit = new FormulaireProduit();
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		model.addAttribute("dateRap", "au : "+formatter.format(new Date()));
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireProduit", formulaireProduit);

		return "/module/pharmagest/rapportStockTotal";
	}

	@RequestMapping(value = "/module/pharmagest/rapportStockTotal.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void stockTotalPost(@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);
			model.addAttribute("formulaireProduit", formulaireProduit);
			
			if (!result.hasErrors()) {
				
				Collection<PharmStocker> listStocks = Context.getService(GestionStockService.class)
						.getPharmStockersByProgram(formulaireProduit.getProgramme());
				model.addAttribute("listStocks", listStocks);
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				model.addAttribute("dateRap", formatter.format(new Date()));
				model.addAttribute("var", 1);
			}

			//} catch (Exception e) {
			//	e.getMessage();
			//}

	}
	
	public Map<PharmProduit, StockBean> traiterListStock(Collection<PharmStocker> list) {

		Map<PharmProduit, StockBean> listNew = Collections.synchronizedMap(new HashMap());

		Iterator it = list.iterator();
		while (it.hasNext()) {
			PharmStocker stock = (PharmStocker) it.next();

			if (listNew.containsKey(stock.getPharmProduitAttribut().getPharmProduit())) {
				StockBean var = listNew.get(stock.getPharmProduitAttribut().getPharmProduit());
				var.setStockQte(var.getStockQte()+stock.getStockQte());
				//var.setStockQteIni(var.getStockQteIni()+stock.getStockQteIni());
				listNew.put(var.getPharmProduit(), var);

			} else {
				StockBean stockBean = new StockBean();
				stockBean.setPharmProduit(stock.getPharmProduitAttribut().getPharmProduit());
				stockBean.setPharmProgramme(stock.getPharmProgramme());
				stockBean.setStockDateStock(stock.getStockDateStock());
				stockBean.setStockQte(stock.getStockQte());
				stockBean.setStockQteIni(stock.getStockQteIni());
				stockBean.setStockQteMax(stock.getStockQteMax());
				stockBean.setStockQteMin(stock.getStockQteIni());
				listNew.put(stock.getPharmProduitAttribut().getPharmProduit(), stockBean);
			}

		}
		
		

		return listNew;
	}

	@RequestMapping(value = "/module/pharmagest/rapportStockProduit.form", method = RequestMethod.GET)
	public String stockProduitGet(ModelMap model) {
		FormulaireProduit formulaireProduit = new FormulaireProduit();
		/*List<PharmProduit> pharmProduits = (List<PharmProduit>) Context.getService(ParametresService.class)
				.getAllProduits();*/
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		model.addAttribute("dateRap", "au : "+formatter.format(new Date()));
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireProduit", formulaireProduit);
		//model.addAttribute("produits", pharmProduits);

		return "/module/pharmagest/rapportStockProduit";
	}

	@RequestMapping(value = "/module/pharmagest/rapportStockProduit.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void stockProduitPost(@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			/*List<PharmProduit> pharmProduits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();*/
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);
			model.addAttribute("formulaireProduit", formulaireProduit);
			//model.addAttribute("produits", pharmProduits);

			if (!result.hasErrors()) {
				
				Collection<PharmStocker> listStocks = Context.getService(GestionStockService.class)
						.getPharmStockersByProgram(formulaireProduit.getProgramme());
				
				model.addAttribute("listStocks", this.traiterListStock(listStocks).values());
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				model.addAttribute("dateRap", formatter.format(new Date()));
				model.addAttribute("var", 1);
			}

			//} catch (Exception e) {
			//	e.getMessage();
			//}

	}
	@RequestMapping(value = "/module/pharmagest/rapportConsoProdJour.form", method = RequestMethod.GET)
	public String initConsoProduitJour(ModelMap model) {
		FormulairePeriode formulairePeriode = new FormulairePeriode();
		/*List<PharmProduit> pharmProduits = (List<PharmProduit>) Context.getService(ParametresService.class)
				.getAllProduits();*/

		model.addAttribute("formulairePeriode", formulairePeriode);
		//model.addAttribute("produits", pharmProduits);

		return "/module/pharmagest/rapportConsoProdJour";
	}

	@RequestMapping(value = "/module/pharmagest/rapportConsoProdJour.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void consoProduitJour(@ModelAttribute("formulairePeriode") FormulairePeriode formulairePeriode,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			List<PharmProduit> pharmProduits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			model.addAttribute("formulairePeriode", formulairePeriode);
			model.addAttribute("produits", pharmProduits);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
			model.addAttribute("periode", periode);

			if (!result.hasErrors()) {
				
				Map<PharmProduit, ConsoByProduit> listConso=Collections.synchronizedMap(new HashMap());
				Collection listOperation = Context.getService(OperationService.class)
						.getPharmOperationsByPeriodConso(formulairePeriode.getDateDebut(),formulairePeriode.getDateFin());
				Iterator it = listOperation.iterator();
				int action=0;
				while (it.hasNext()) {
					PharmOperation operation = (PharmOperation) it.next();
					
					
					action=(operation.getPharmTypeOperation().getToperId()==2) ? 1 : 0 ;
					
					
					Set<PharmLigneOperation> list=operation.getPharmLigneOperations();
					PharmProgramme programme =operation.getPharmProgramme();
					for (PharmLigneOperation ligne : list) {
						ConsoByProduit consoProd=new ConsoByProduit();
						consoProd.setProduit(ligne.getPharmProduitAttribut().getPharmProduit());
						consoProd.setProgramme(programme);
						if(action==1){
							consoProd.setQteConso(ligne.getLgnOperQte());
							}else{
								consoProd.setQteConso(-ligne.getLgnOperQte());
								}
						
						PharmStocker stocker=Context.getService(GestionStockService.class).getPharmStockersByProduitAttribut(ligne.getPharmProduitAttribut(),operation.getPharmProgramme());
						if(stocker!=null){
							consoProd.setQteStock(stocker.getStockQte());
							}else{
								consoProd.setQteStock(0);
						}
						
						//insertion dans la liste
						if(listConso.containsKey(consoProd.getProduit())){
							ConsoByProduit var=listConso.get(consoProd.getProduit());
							var.setQteConso(var.getQteConso()+consoProd.getQteConso());
							var.setQteStock(var.getQteStock()+consoProd.getQteStock());
							listConso.put(var.getProduit(), var);
						}else{
							listConso.put(consoProd.getProduit(), consoProd);
						}
						
						
					}
				}
				
				model.addAttribute("listConso", listConso.values());
				
			}

		} catch (Exception e) {
			e.getMessage();
		}

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
