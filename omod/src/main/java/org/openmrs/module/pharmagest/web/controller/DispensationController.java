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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.GestionnairePharma;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LigneDispensationId;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.impl.DispensationServiceImpl;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
import org.openmrs.module.pharmagest.validator.OrdonnanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulaireOrdonnance")
@RequestMapping(value = "/module/pharmagest/dispensation.form")
public class DispensationController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	DispensationValidator dispensationValidator;

	@RequestMapping(method = RequestMethod.POST, params = { "btn_recherche" })
	public void rechercher(@ModelAttribute("formulaireOrdonnance") FormulaireOrdonnance formulaireOrdonnance,
			BindingResult result, @RequestParam(required = true, value = "numPatient") String numPatient,
			HttpSession session, ModelMap model) {
	
		PatientComplement patientComplement= Context.getService(ParametersDispensationService.class)
				.getPatientComplementByIdentifier(numPatient);
		
		if (patientComplement != null) {
			
			model.addAttribute("patientIdentifier", patientComplement.getPatientIdentifierId());
			List<Regime> regimes = (List<Regime>) Context.getService(ParametersDispensationService.class)
					.getAllRegimes();
			List<Produit> produits = (List<Produit>) Context.getService(ParametersDispensationService.class)
					.getAllProduits();
			List<Medecin> medecins = (List<Medecin>) Context.getService(ParametersDispensationService.class)
					.getAllMedecins();
			List<Programme> programmes = (List<Programme>) Context.getService(ParametersDispensationService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);
			formulaireOrdonnance.setIdParam(numPatient);
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("mess", "find");
			model.addAttribute("var", "1");
			
			//recuperer la dernière dispensation
			Ordonnance dispensation =Context.getService(DispensationServiceImpl.class).getLastDispensation(patientComplement);
			if(dispensation!=null){
				model.addAttribute("regime", dispensation.getRegime().getRegimLib());
				model.addAttribute("rdv", dispensation.getOrdDateRdv());
			}
			
		} else {
			model.addAttribute("mess", "echec");
		}

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initDispenser(ModelMap model) {
		FormulaireOrdonnance formulaireOrdonnance = new FormulaireOrdonnance();
		// gestion du gestionnaire
		GestionnairePharma gestionnairePharma = new GestionnairePharma();
		gestionnairePharma.setPrepId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setPrepNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setPrepPrenom(Context.getAuthenticatedUser().getLastName());
		formulaireOrdonnance.setGestionnairePharma(gestionnairePharma);
		List<Regime> regimes = (List<Regime>) Context.getService(ParametersDispensationService.class).getAllRegimes();
		List<Produit> produits = (List<Produit>) Context.getService(ParametersDispensationService.class)
				.getAllProduits();
		List<Medecin> medecins = (List<Medecin>) Context.getService(ParametersDispensationService.class)
				.getAllMedecins();
		List<Programme> programmes = (List<Programme>) Context.getService(ParametersDispensationService.class)
				.getAllProgrammes();
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		model.addAttribute("var", "0");

		return "/module/pharmagest/dispensation";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(@ModelAttribute("formulaireOrdonnance") FormulaireOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			// dispensationValidator.validate(formulaireOrdonnance, result);

			List<Regime> regimes = (List<Regime>) Context.getService(ParametersDispensationService.class)
					.getAllRegimes();
			List<Produit> produits = (List<Produit>) Context.getService(ParametersDispensationService.class)
					.getAllProduits();
			List<Medecin> medecins = (List<Medecin>) Context.getService(ParametersDispensationService.class)
					.getAllMedecins();
			List<Programme> programmes = (List<Programme>) Context.getService(ParametersDispensationService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);

			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			if (!result.hasErrors()) {

				// contrôle du Stock
				StockerId stockerId = new StockerId();
				if (formulaireOrdonnance.getProduit() != null)
					stockerId.setProdId(formulaireOrdonnance.getProduit().getProdId());
				if (formulaireOrdonnance.getProgramme() != null)
					stockerId.setProgramId(formulaireOrdonnance.getProgramme().getProgramId());
				Stocker stocker = Context.getService(GestionStockService.class).getStockerById(stockerId);
				int stockQte = 0;
				if (stocker != null)
					stockQte = stocker.getStockQte();
				if (stockQte >= formulaireOrdonnance.getServQteServi()) {

					LigneDispensation lgnDisp = new LigneDispensation();
					lgnDisp.setProduit(formulaireOrdonnance.getProduit());
					lgnDisp.setOrdonnance(formulaireOrdonnance.getOrdonnance());
					lgnDisp.setServPrixUnit(formulaireOrdonnance.getServPrixUnit());
					lgnDisp.setServQteDem(formulaireOrdonnance.getServQteDem());
					lgnDisp.setServQteServi(formulaireOrdonnance.getServQteServi());
					lgnDisp.setId(formulaireOrdonnance.addLigneDispensationId());
					formulaireOrdonnance.getTabdispensation().addLigneDispensation(lgnDisp);

					model.addAttribute("ligneDispensations",
							formulaireOrdonnance.getTabdispensation().getLigneDispensationsCollection());
					model.addAttribute("mess", "accept");
				} else {
					model.addAttribute("mess", "refuse");
				}
				model.addAttribute("var", "1");
			} else {
				model.addAttribute("var", "1");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(@ModelAttribute("formulaireOrdonnance") FormulaireOrdonnance formulaireOrdonnance,
			@ModelAttribute("ligneDispensations") HashMap<Integer, LigneDispensation> ligneDispensations,
			BindingResult result, HttpSession session, ModelMap model) {
		// dispensationValidator.validate(formulaireOrdonnance, result);

		if (!result.hasErrors()) {
			// save ordonnance
			Ordonnance ord = new Ordonnance();
			ord = formulaireOrdonnance.getOrdonnance();
			Context.getService(DispensationService.class).saveOrdonnance(ord);
			// save ligne dispensation

			Map lignes = formulaireOrdonnance.getTabdispensation().getLigneDispensations();
			for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				LigneDispensation ligne = (LigneDispensation) lignes.get(key);
				LigneDispensation ld = new LigneDispensation();
				LigneDispensationId ldId = new LigneDispensationId();
				ldId.setOrdId(ord.getOrdId());
				ldId.setProdId(ligne.getProduit().getProdId());
				ld.setId(ldId);
				ld.setOrdonnance(ord);
				ld.setProduit(ligne.getProduit());
				ld.setServPrixUnit(ligne.getServPrixUnit());
				ld.setServQteDem(ligne.getServQteDem());
				ld.setServQteServi(ligne.getServQteServi());
				Context.getService(DispensationService.class).saveLigneDispensation(ld);
				// gestion du Stocker
				StockerId stockerId = new StockerId();
				stockerId.setProdId(ligne.getProduit().getProdId());
				stockerId.setProgramId(formulaireOrdonnance.getOrdonnance().getProgramme().getProgramId());
				Stocker stocker = Context.getService(GestionStockService.class).getStockerById(stockerId);
				if (stocker != null) {
					Integer stockQte = stocker.getStockQte() - ld.getServQteDem();
					stocker.setId(stockerId);
					stocker.setStockQte(stockQte);
					Context.getService(GestionStockService.class).updateStocker(stocker);

					// insertion dans histoMvm
					HistoMouvementStock histoMouvementStock = new HistoMouvementStock();
					histoMouvementStock.setMvtDate(formulaireOrdonnance.getOrdonnance().getOrdDateDispen());
					histoMouvementStock.setMvtDate(ord.getOrdDateDispen());
					// histoMouvementStock.setMvtLot();
					// histoMouvementStock.setMvtMotif(mvtMotif);
					histoMouvementStock.setMvtProgramme(ord.getProgramme().getProgramId());
					histoMouvementStock.setMvtQte(ligne.getServQteServi());
					histoMouvementStock.setMvtQteStock(stocker.getStockQte());
					histoMouvementStock.setMvtTypeMvt(Context.getService(ParametersDispensationService.class)
							.getTypeOperationById(2).getTrecptId());
					histoMouvementStock.setProduit(ligne.getProduit());
					Context.getService(GestionStockService.class).saveHistoMvmStock(histoMouvementStock);
				}
			}
			model.addAttribute("mess", "save");
		} else {
			List<Regime> regimes = (List<Regime>) Context.getService(ParametersDispensationService.class)
					.getAllRegimes();
			List<Produit> produits = (List<Produit>) Context.getService(ParametersDispensationService.class)
					.getAllProduits();
			List<Medecin> medecins = (List<Medecin>) Context.getService(ParametersDispensationService.class)
					.getAllMedecins();
			List<Programme> programmes = (List<Programme>) Context.getService(ParametersDispensationService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);

			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);

		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneDispensation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireOrdonnance") FormulaireOrdonnance formulaireOrdonnance, BindingResult result,
			HttpSession session, ModelMap model) {
		List<Regime> regimes = (List<Regime>) Context.getService(ParametersDispensationService.class).getAllRegimes();
		List<Produit> produits = (List<Produit>) Context.getService(ParametersDispensationService.class)
				.getAllProduits();
		List<Medecin> medecins = (List<Medecin>) Context.getService(ParametersDispensationService.class)
				.getAllMedecins();
		List<Programme> programmes = (List<Programme>) Context.getService(ParametersDispensationService.class)
				.getAllProgrammes();
		model.addAttribute("programmes", programmes);

		// model.addAttribute("patientIdentifier",
		// patientIdentifier.getPatient());
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		formulaireOrdonnance.getTabdispensation().removeLigneDispensationById(paramId);
		model.addAttribute("ligneDispensations",
				formulaireOrdonnance.getTabdispensation().getLigneDispensationsCollection());
		model.addAttribute("var", "1");

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Produit.class, new PropertyEditorSupport() {
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
				Produit produit = Context.getService(ParametersDispensationService.class).getProduitById(nbr);
				this.setValue(produit);
			}
		});
		binder.registerCustomEditor(Regime.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				Regime regime = Context.getService(ParametersDispensationService.class)
						.getRegimeById(Integer.parseInt(text));
				this.setValue(regime);
			}
		});
		binder.registerCustomEditor(Medecin.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				Medecin medecin = Context.getService(ParametersDispensationService.class)
						.getMedecinById(Integer.parseInt(text));
				this.setValue(medecin);
			}
		});

		binder.registerCustomEditor(Programme.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				Programme programme = Context.getService(ParametersDispensationService.class)
						.getProgrammeById(Integer.parseInt(text));
				this.setValue(programme);
			}
		});

	}

}
