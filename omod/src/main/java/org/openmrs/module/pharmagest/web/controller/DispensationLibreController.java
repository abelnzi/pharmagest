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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LigneDispensationId;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulairePharmOrdonnance;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulaireOrdonnance")
@RequestMapping(value = "/module/pharmagest/dispensationLibre.form")
public class DispensationLibreController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	DispensationValidator dispensationValidator;

	

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initDispenser(ModelMap model) {
		FormulairePharmOrdonnance formulaireOrdonnance = new FormulairePharmOrdonnance();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		formulaireOrdonnance.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();

		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();

		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		model.addAttribute("var", "0");

		return "/module/pharmagest/dispensationLibre";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			dispensationValidator.validate(formulaireOrdonnance, result);

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
					.getAllMedecins();

			model.addAttribute("programmes", programmes);

			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);

			if (!result.hasErrors()) {
				// contrôle du Stock
				List<PharmStocker> stockerList = (ArrayList<PharmStocker>) Context.getService(GestionStockService.class)
						.getPharmStockersByPP(formulaireOrdonnance.getPharmProduit(),
								formulaireOrdonnance.getPharmProgramme());

				if (!stockerList.isEmpty()) {
					Collections.sort(stockerList);

					Integer qte = formulaireOrdonnance.getLdQteServi();
					Integer qteTotalServi = 0;
					boolean condition = true;
					Iterator it = stockerList.iterator();
					while (it.hasNext() && condition == true) {
						PharmStocker stock = (PharmStocker) it.next();
						if (stock.getStockQte() >= qte) {

							LigneDispensationMvt lgnDisp = new LigneDispensationMvt();
							lgnDisp.setPharmProduit(formulaireOrdonnance.getPharmProduit());
							lgnDisp.setLdPrixUnit(formulaireOrdonnance.getLdPrixUnit());
							lgnDisp.setLdQteDem(formulaireOrdonnance.getLdQteDem());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot());
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());
							lgnDisp.setLdQteServi(qte);
							if (qte != 0)
								formulaireOrdonnance.getTabDispensationMvt().addLigneDispensation(lgnDisp);
							qteTotalServi = qteTotalServi + qte;
							condition = false;

						} else {
							LigneDispensationMvt lgnDisp = new LigneDispensationMvt();
							lgnDisp.setPharmProduit(formulaireOrdonnance.getPharmProduit());
							lgnDisp.setLdPrixUnit(formulaireOrdonnance.getLdPrixUnit());
							lgnDisp.setLdQteDem(formulaireOrdonnance.getLdQteDem());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot());
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());
							lgnDisp.setLdQteServi(stock.getStockQte());
							formulaireOrdonnance.getTabDispensationMvt().addLigneDispensation(lgnDisp);

							qteTotalServi = qteTotalServi + lgnDisp.getLdQteServi();
							qte = qte - stock.getStockQte();
						}
					}

					if (formulaireOrdonnance.getLdQteDem() <= qteTotalServi) {

						model.addAttribute("mess", "accept");

					} else if (formulaireOrdonnance.getLdQteDem() > qteTotalServi) {

						model.addAttribute("mess", "refuse");

					}
					model.addAttribute("ligneDispensations",
							formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
					model.addAttribute("var", "1");
					model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
				}else{
					model.addAttribute("ligneDispensations",
							formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
					model.addAttribute("mess", "refuse");
				}

			}
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("var", "1");
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			@ModelAttribute("ligneDispensations") HashMap<Integer, LigneDispensation> ligneDispensations,
			BindingResult result, HttpSession session, ModelMap model) {
		dispensationValidator.validate(formulaireOrdonnance, result);

		if (!result.hasErrors()) {
			// save ordonnance
			PharmOrdonnance ord = new PharmOrdonnance();
			ord = formulaireOrdonnance.getPharmOrdonnance();
			ord.setOrdDateSaisi(new Date());
			// ord.setPharmGestionnairePharma(formulaireOrdonnance.getGestionnairePharma());
			if (ord.getOrdDateRdv() == null) {
				Date datRdV = ord.getOrdDateDispen();
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(datRdV);
				gc.add(GregorianCalendar.DATE, ord.getOrdNbreJrsTrai());
				ord.setOrdDateRdv(gc.getTime());
			}
			Context.getService(DispensationService.class).savePharmOrdonnance(ord);
			// save operation
			PharmOperation operation = new PharmOperation();
			operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(2));
			operation.setOperDateRecept(ord.getOrdDateDispen());
			operation.setOperDateSaisi(new Date());
			operation.setPharmGestionnairePharma(ord.getPharmGestionnairePharma());
			operation.setPharmProgramme(ord.getPharmProgramme());
			Context.getService(OperationService.class).savePharmOperation(operation);
			// save ligne dispensation
			Map lignes = formulaireOrdonnance.getTabDispensationMvt().getLigneDispensations();
			for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				LigneDispensationMvt ligne = (LigneDispensationMvt) lignes.get(key);
				PharmLigneDispensation ld = new PharmLigneDispensation();
				PharmLigneDispensationId ldId = new PharmLigneDispensationId();
				ldId.setOrdId(ord.getOrdId());
				ldId.setProdId(ligne.getPharmProduit().getProdId());
				// verifier la ligne
				PharmLigneDispensation varLd = Context.getService(DispensationService.class)
						.getPharmLigneDispensation(ldId);

				if (varLd != null) {
					ld = varLd;
					ld.setPharmOrdonnance(ord);
					ld.setPharmProduit(ligne.getPharmProduit());
					ld.setLdPrixUnit(ligne.getLdPrixUnit());
					ld.setLdQteDem(ligne.getLdQteDem());
					ld.setLdQteServi(ligne.getLdQteServi() + varLd.getLdQteServi());
					Context.getService(DispensationService.class).updatePharmLigneDispensation(ld);
				} else {
					ld.setId(ldId);
					ld.setPharmOrdonnance(ord);
					ld.setPharmProduit(ligne.getPharmProduit());
					ld.setLdPrixUnit(ligne.getLdPrixUnit());
					ld.setLdQteDem(ligne.getLdQteDem());
					ld.setLdQteServi(ligne.getLdQteServi());
					Context.getService(DispensationService.class).savePharmLigneDispensation(ld);
				}

				// Gestion ProduitAttribut
				PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(ligne.getPharmProduit(), ligne.getLgnRecptLot());
				// gestion des stock
				PharmStockerId stockerId = new PharmStockerId();
				stockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
				stockerId.setProgramId(ord.getPharmProgramme().getProgramId());
				PharmStocker stocker = Context.getService(GestionStockService.class).getPharmStockerById(stockerId);

				if (stocker != null && (stocker.getStockQte() >= ld.getLdQteServi())) {
					Integer stockQte = stocker.getStockQte() - ld.getLdQteServi();
					stocker.setStockQte(stockQte);
					stocker.setStockDateStock(ord.getOrdDateDispen());
					Context.getService(GestionStockService.class).updatePharmStocker(stocker);
					// insertion dans histoMvm
					PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
					histoMouvementStock.setMvtDate(ord.getOrdDateDispen());
					histoMouvementStock.setMvtLot(ligne.getLgnRecptLot());

					histoMouvementStock.setMvtProgramme(ord.getPharmProgramme().getProgramId());
					histoMouvementStock.setMvtQte(ld.getLdQteServi());
					histoMouvementStock.setMvtQteStock(stocker.getStockQte());
					histoMouvementStock.setMvtTypeMvt(operation.getPharmTypeOperation().getToperId());
					histoMouvementStock.setPharmProduit(ligne.getPharmProduit());
					Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
				}

			}
			model.addAttribute("mess", "save");
		} else {
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
					.getAllMedecins();
			model.addAttribute("programmes", programmes);

			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);

		}
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
				.getAllProduits();
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
				.getAllMedecins();
		model.addAttribute("programmes", programmes);

		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneDispensation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();

		formulaireOrdonnance.getTabDispensationMvt().removeLigneDispensationById(paramId);
		model.addAttribute("ligneDispensations",
				formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
		model.addAttribute("var", "1");
		model.addAttribute("mess", "delete");
		
		
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("programmes", programmes);

		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);

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
