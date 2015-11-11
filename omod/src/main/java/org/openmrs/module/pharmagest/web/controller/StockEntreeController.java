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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireStockFournisseur;
import org.openmrs.module.pharmagest.metier.LigneOperationMvt;
import org.openmrs.module.pharmagest.validator.StockValidator;
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
@SessionAttributes("formulaireStockFourni")
@RequestMapping(value = "/module/pharmagest/stockFournisseur.form")
public class StockEntreeController {
	@Autowired
	StockValidator stockValidator;

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initStockFour(ModelMap model) {
		FormulaireStockFournisseur formulaireStockFourni = new FormulaireStockFournisseur();
		model.addAttribute("formulaireStockFourni", formulaireStockFourni);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		formulaireStockFourni.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
				.getAllFournisseurs();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		return "/module/pharmagest/stockFournisseur";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireStockFourni") FormulaireStockFournisseur formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			stockValidator.validate(formulaireStockFourni, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {

				LigneOperationMvt lngOperation = new LigneOperationMvt();
				lngOperation.setOperation(formulaireStockFourni.getPharmOperation());
				lngOperation.setProduit(formulaireStockFourni.getProduit());
				lngOperation.setLgnRecptQte(formulaireStockFourni.getLgnRecptQte());
				lngOperation.setLgnRecptPrixAchat(formulaireStockFourni.getLgnRecptPrixAchat());
				lngOperation.setLgnRecptLot(formulaireStockFourni.getLgnRecptLot());
				lngOperation.setLgnDatePerem(formulaireStockFourni.getLgnDatePerem());

				formulaireStockFourni.getTabOperationMvt().addLigneOperation(lngOperation);
				model.addAttribute("ligneOperations",
						formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
				model.addAttribute("mess", "valid");
			}

			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneOperation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireStockFourni") FormulaireStockFournisseur formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {

		try {
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			

			// identifier la clé
			/*
			 * String key =
			 * formulaireStockFourni.getPharmProduitAttribut().getPharmProduit()
			 * .getProdId() + "_" + formulaireStockFourni.getLgnOperLot() ;
			 */

			formulaireStockFourni.getTabOperationMvt().removeLigneOperationById(paramId);
			model.addAttribute("ligneOperations",
					formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
			model.addAttribute("mess", "delete");
			
			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);
			
			
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(
			@ModelAttribute("formulaireStockFourni") FormulaireStockFournisseur formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			stockValidator.validate(formulaireStockFourni, result);
			// save operation
			if (!result.hasErrors()) {
				PharmOperation operation = new PharmOperation();
				operation = formulaireStockFourni.getPharmOperation();

				PharmTypeOperation typeOperation = Context.getService(ParametresService.class).getTypeOperationById(1);
				operation.setPharmTypeOperation(typeOperation);
				Context.getService(OperationService.class).savePharmOperation(operation);

				Map lignes = formulaireStockFourni.getTabOperationMvt().getLigneOperations();
				for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
					Object key = i.next();
					LigneOperationMvt ligne = (LigneOperationMvt) lignes.get(key);

					// Gestion ProduitAttribut
					PharmProduitAttribut pharmProduitAttribut = new PharmProduitAttribut();
					PharmProduitAttribut varProd = Context.getService(OperationService.class)
							.getPharmProduitAttributByElement(ligne.getProduit(), ligne.getLgnRecptLot());
					if (varProd != null) {
						pharmProduitAttribut = varProd;
					} else {
						pharmProduitAttribut.setPharmProduit(formulaireStockFourni.getProduit());
						pharmProduitAttribut.setProdAttriDate(new Date());
						pharmProduitAttribut.setProdDatePerem(ligne.getLgnDatePerem());
						pharmProduitAttribut.setProdLot(ligne.getLgnRecptLot());
						pharmProduitAttribut.setFlagValid(true);
						Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
					}

					// save PharmligneOperation
					PharmLigneOperation ld = new PharmLigneOperation();
					PharmLigneOperationId ldId = new PharmLigneOperationId();
					ldId.setOperId(operation.getOperId());
					ldId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					ld.setId(ldId);
					ld.setPharmOperation(operation);
					ld.setPharmProduitAttribut(pharmProduitAttribut);
					ld.setLgnOperDatePerem(ligne.getLgnDatePerem());
					ld.setLgnOperLot(ligne.getLgnRecptLot());
					ld.setLgnOperPrixAchat(ligne.getLgnRecptPrixAchat());
					ld.setLgnOperQte(ligne.getLgnRecptQte());
					Context.getService(OperationService.class).savePharmLigneOperation(ld);

					// gestion du Stocker
					PharmStockerId stockerId = new PharmStockerId();
					stockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					stockerId.setProgramId(operation.getPharmProgramme().getProgramId());
					PharmStocker stocker = Context.getService(GestionStockService.class).getPharmStockerById(stockerId);
					if (stocker != null) {
						Integer stockQte = stocker.getStockQte() + ld.getLgnOperQte();
						stocker.setStockQte(stockQte);
						stocker.setStockDateStock(operation.getOperDateRecept());
						Context.getService(GestionStockService.class).updatePharmStocker(stocker);
						// insertion dans histoMvm
						PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
						histoMouvementStock.setMvtDate(operation.getOperDateRecept());
						histoMouvementStock.setMvtLot(ligne.getLgnRecptLot());

						histoMouvementStock.setMvtProgramme(operation.getPharmProgramme().getProgramId());
						histoMouvementStock.setMvtQte(ld.getLgnOperQte());
						histoMouvementStock.setMvtQteStock(stocker.getStockQte());
						histoMouvementStock.setMvtTypeMvt(
								Context.getService(ParametresService.class).getTypeOperationById(1).getToperId());
						histoMouvementStock.setPharmProduit(ligne.getProduit());
						Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
					} else {
						PharmStocker stocker2 = new PharmStocker();
						stocker2.setId(stockerId);
						stocker2.setStockQte(ld.getLgnOperQte());
						stocker2.setStockDateStock(operation.getOperDateRecept());
						Context.getService(GestionStockService.class).savePharmStocker(stocker2);
						// insertion dans histoMvm
						PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
						histoMouvementStock.setMvtDate(operation.getOperDateRecept());
						histoMouvementStock.setMvtLot(ligne.getLgnRecptLot());

						histoMouvementStock.setMvtProgramme(operation.getPharmProgramme().getProgramId());
						histoMouvementStock.setMvtQte(ld.getLgnOperQte());
						histoMouvementStock.setMvtQteStock(stocker2.getStockQte());
						histoMouvementStock.setMvtTypeMvt(
								Context.getService(ParametresService.class).getTypeOperationById(1).getToperId());
						histoMouvementStock.setPharmProduit(ligne.getProduit());
						Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
					}

				}
				model.addAttribute("mess", "success");
			}
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			formulaireStockFourni = new FormulaireStockFournisseur();
			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);
			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
		} catch (Exception e) {
			e.getStackTrace();
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
		binder.registerCustomEditor(PharmFournisseur.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmFournisseur fournisseur = Context.getService(ParametresService.class)
						.getFournisseurById(Integer.parseInt(text));
				this.setValue(fournisseur);
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
