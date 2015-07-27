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
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.GestionnairePharma;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.LingeOperationId;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.TypeOperation;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
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
		FormulaireStockFourni formulaireStockFourni = new FormulaireStockFourni();
		model.addAttribute("formulaireStockFourni", formulaireStockFourni);
		// gestion du gestionnaire
		GestionnairePharma gestionnairePharma = new GestionnairePharma();
		gestionnairePharma
				.setPrepId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setPrepNom(Context.getAuthenticatedUser()
				.getFirstName());
		gestionnairePharma.setPrepPrenom(Context.getAuthenticatedUser()
				.getLastName());
		formulaireStockFourni.setGestionnairePharma(gestionnairePharma);
		List<Fournisseur> fournisseurs = (List<Fournisseur>) Context
				.getService(ParametersDispensationService.class)
				.getAllFournisseurs();
		List<Programme> programmes = (List<Programme>) Context.getService(
				ParametersDispensationService.class).getAllProgrammes();
		List<Produit> produits = (List<Produit>) Context.getService(
				ParametersDispensationService.class).getAllProduits();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		return "/module/pharmagest/stockFournisseur";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireStockFourni") FormulaireStockFourni formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			stockValidator.validate(formulaireStockFourni, result);
			List<Fournisseur> fournisseurs = (List<Fournisseur>) Context
					.getService(ParametersDispensationService.class)
					.getAllFournisseurs();
			List<Programme> programmes = (List<Programme>) Context.getService(
					ParametersDispensationService.class).getAllProgrammes();
			List<Produit> produits = (List<Produit>) Context.getService(
					ParametersDispensationService.class).getAllProduits();

			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);

			if (!result.hasErrors()) {

				LingeOperation lngOperation = new LingeOperation();
				lngOperation.setOperation(formulaireStockFourni.getOperation());
				lngOperation.setProduit(formulaireStockFourni.getProduit());
				lngOperation.setLgnRecptQte(formulaireStockFourni
						.getLgnRecptQte());
				lngOperation.setLgnRecptPrixAchat(formulaireStockFourni
						.getLgnRecptPrixAchat());
				lngOperation.setLgnRecptLot(formulaireStockFourni
						.getLgnRecptLot());
				lngOperation.setLgnDatePerem(formulaireStockFourni
						.getLgnDatePerem());
				lngOperation.setId(formulaireStockFourni.addLigneOperationId());

				formulaireStockFourni.getTabOperation().addLigneOperation(
						lngOperation);
				model.addAttribute("ligneOperations", formulaireStockFourni
						.getTabOperation().getLigneOperationsCollection());
				model.addAttribute("mess", "valid");
			}
			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneOperation(
			@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireStockFourni") FormulaireStockFourni formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {

		try {

			List<Fournisseur> fournisseurs = (List<Fournisseur>) Context
					.getService(ParametersDispensationService.class)
					.getAllFournisseurs();
			List<Programme> programmes = (List<Programme>) Context.getService(
					ParametersDispensationService.class).getAllProgrammes();
			List<Produit> produits = (List<Produit>) Context.getService(
					ParametersDispensationService.class).getAllProduits();

			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);

			formulaireStockFourni.getTabOperation().removeLigneOperationById(
					paramId);
			model.addAttribute("ligneOperations", formulaireStockFourni
					.getTabOperation().getLigneOperationsCollection());
			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
			model.addAttribute("mess", "delete");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(
			@ModelAttribute("formulaireStockFourni") FormulaireStockFourni formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			stockValidator.validate(formulaireStockFourni, result);
			// save operation
			if (!result.hasErrors()) {
				Operation operation = new Operation();
				operation = formulaireStockFourni.getOperation();
				TypeOperation typeOperation = Context.getService(
						ParametersDispensationService.class)
						.getTypeOperationById(1);
				operation.setTypeOperation(typeOperation);
				Context.getService(OperationService.class).saveOperation(
						operation);
				// save ligne dispensation

				Map lignes = formulaireStockFourni.getTabOperation()
						.getLigneOperations();
				for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
					Object key = i.next();
					LingeOperation ligne = (LingeOperation) lignes.get(key);
					LingeOperation ld = new LingeOperation();
					LingeOperationId ldId = new LingeOperationId();
					ldId.setRecptId(operation.getRecptId());
					ldId.setProdId(ligne.getProduit().getProdId());
					ld.setId(ldId);
					ld.setOperation(operation);
					ld.setLgnDatePerem(ligne.getLgnDatePerem());
					ld.setLgnRecptLot(ligne.getLgnRecptLot());
					ld.setLgnRecptPrixAchat(ligne.getLgnRecptPrixAchat());
					ld.setLgnRecptQte(ligne.getLgnRecptQte());
					Context.getService(OperationService.class)
							.saveLigneOperation(ld);

					// gestion du Stocker
					StockerId stockerId = new StockerId();
					stockerId.setProdId(ligne.getProduit().getProdId());
					stockerId.setProgramId(operation.getProgramme()
							.getProgramId());
					Stocker stocker = Context.getService(
							GestionStockService.class)
							.getStockerById(stockerId);
					if (stocker != null) {
						Integer stockQte = stocker.getStockQte()
								+ ld.getLgnRecptQte();
						stocker.setStockQte(stockQte);
						Context.getService(GestionStockService.class)
								.updateStocker(stocker);
						// insertion dans histoMvm
						HistoMouvementStock histoMouvementStock = new HistoMouvementStock();
						histoMouvementStock.setMvtDate(formulaireStockFourni
								.getOperation().getRecptDateRecept());
						// histoMouvementStock.setMvtLot();
						// histoMouvementStock.setMvtMotif(mvtMotif);
						histoMouvementStock
								.setMvtProgramme(formulaireStockFourni
										.getOperation().getProgramme()
										.getProgramId());
						histoMouvementStock.setMvtQte(ld.getLgnRecptQte());
						histoMouvementStock.setMvtQteStock(stocker
								.getStockQte());
						histoMouvementStock
								.setMvtTypeMvt(Context
										.getService(
												ParametersDispensationService.class)
										.getTypeOperationById(1).getTrecptId());
						histoMouvementStock.setProduit(ligne.getProduit());
						Context.getService(GestionStockService.class)
								.saveHistoMvmStock(histoMouvementStock);
					} else {
						Stocker stocker2 = new Stocker();
						stocker2.setId(stockerId);
						stocker2.setStockQte(ld.getLgnRecptQte());
						Context.getService(GestionStockService.class)
								.saveStocker(stocker2);
						// insertion dans histoMvm
						HistoMouvementStock histoMouvementStock = new HistoMouvementStock();
						histoMouvementStock.setMvtDate(formulaireStockFourni
								.getOperation().getRecptDateRecept());
						// histoMouvementStock.setMvtLot();
						// histoMouvementStock.setMvtMotif(mvtMotif);
						histoMouvementStock
								.setMvtProgramme(formulaireStockFourni
										.getOperation().getProgramme()
										.getProgramId());
						histoMouvementStock.setMvtQte(ld.getLgnRecptQte());
						histoMouvementStock.setMvtQteStock(stocker2
								.getStockQte());
						histoMouvementStock
								.setMvtTypeMvt(Context
										.getService(
												ParametersDispensationService.class)
										.getTypeOperationById(1).getTrecptId());
						histoMouvementStock.setProduit(ligne.getProduit());
						Context.getService(GestionStockService.class)
								.saveHistoMvmStock(histoMouvementStock);
					}

				}
				model.addAttribute("mess", "success");
			}
			model.addAttribute("formulaireStockFourni", formulaireStockFourni);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
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
				Produit produit = Context.getService(
						ParametersDispensationService.class)
						.getProduitById(nbr);
				this.setValue(produit);
			}
		});
		binder.registerCustomEditor(Fournisseur.class,
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						Fournisseur fournisseur = Context.getService(
								ParametersDispensationService.class)
								.getFournisseurById(Integer.parseInt(text));
						this.setValue(fournisseur);
					}
				});
		binder.registerCustomEditor(Programme.class,
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						Programme programme = Context.getService(
								ParametersDispensationService.class)
								.getProgrammeById(Integer.parseInt(text));
						this.setValue(programme);
					}
				});

	}

}
