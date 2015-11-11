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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.Inventaire;
import org.openmrs.module.pharmagest.LigneInventaire;
import org.openmrs.module.pharmagest.LigneInventaireId;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaireId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireInventaire;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.openmrs.module.pharmagest.metier.LigneInventaireMvt;
import org.openmrs.module.pharmagest.validator.InventaireValidator;
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
@SessionAttributes("formulaireInventaire")
@RequestMapping(value = "/module/pharmagest/inventaire.form")
public class InventaireController {

	@Autowired
	InventaireValidator inventaireValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String initStockFour(ModelMap model) {
		FormulairePharmInventaire formulaireInventaire = new FormulairePharmInventaire();
		model.addAttribute("formulaireInventaire", formulaireInventaire);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		return "/module/pharmagest/inventaire";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			// inventaireValidator.validate(formulaireInventaire, result);
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);

			if (!result.hasErrors()) {

				LigneInventaireMvt ligneInventaire = new LigneInventaireMvt();
				ligneInventaire.setProduit(formulaireInventaire.getProduit());
				ligneInventaire.setQtePhysique(formulaireInventaire.getQtePhysique());
				ligneInventaire.setObservation(formulaireInventaire.getObservation());
				ligneInventaire.setLgnDatePerem(formulaireInventaire.getLgnDatePerem());
				ligneInventaire.setLgnLot(formulaireInventaire.getLgnLot());

				// determiner le stock logique du produit
				PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(formulaireInventaire.getProduit(),
								formulaireInventaire.getLgnLot());
				if (pharmProduitAttribut != null) {

					PharmStockerId pharmStockerId = new PharmStockerId();
					pharmStockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					pharmStockerId.setProgramId(formulaireInventaire.getPharmProgramme().getProgramId());
					PharmStocker pharmStocker = Context.getService(GestionStockService.class)
							.getPharmStockerById(pharmStockerId);
					if (pharmStocker != null) {
						ligneInventaire.setQteLogique(pharmStocker.getStockQte());
						ligneInventaire
								.setEcart(Math.abs(pharmStocker.getStockQte() - formulaireInventaire.getQtePhysique()));
					} else {
						ligneInventaire.setQteLogique(0);
						ligneInventaire.setEcart(formulaireInventaire.getQtePhysique());
					}
				} else {
					ligneInventaire.setQteLogique(0);
					ligneInventaire.setEcart(formulaireInventaire.getQtePhysique());
				}

				formulaireInventaire.getTabInventaire().addLigneInventaire(ligneInventaire);
				model.addAttribute("ligneInventaires",
						formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
				model.addAttribute("mess", "valid");
			}
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneOperation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire, BindingResult result,
			HttpSession session, ModelMap model) {

		try {

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", produits);

			formulaireInventaire.getTabInventaire().removeLigneInventaireById(paramId);
			model.addAttribute("ligneInventaires",
					formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("mess", "delete");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			//inventaireValidator.validate(formulaireInventaire, result);
	
			if (!result.hasErrors()) {
				PharmInventaire pharmInventaire = new PharmInventaire();
				pharmInventaire = formulaireInventaire.getPharmInventaire();
				pharmInventaire.setInvDateCrea(new Date());
				
				Context.getService(InventaireService.class).savePharmInventaire(pharmInventaire);
				// save ligne dispensation
				Map lignes = formulaireInventaire.getTabInventaire().getLigneInventaires();
				for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
					Object key = i.next();
					LigneInventaireMvt ligne = (LigneInventaireMvt) lignes.get(key);
					
					// determiner le stock logique du produit
					PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
							.getPharmProduitAttributByElement(formulaireInventaire.getProduit(),
									formulaireInventaire.getLgnLot());
					if(pharmProduitAttribut==null){
						pharmProduitAttribut=new PharmProduitAttribut();
						pharmProduitAttribut.setPharmProduit(formulaireInventaire.getProduit());
						pharmProduitAttribut.setProdAttriDate(new Date());
						pharmProduitAttribut.setProdDatePerem(ligne.getLgnDatePerem());
						pharmProduitAttribut.setProdLot(ligne.getLgnLot());
						pharmProduitAttribut.setFlagValid(true);
						Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
					}

					PharmLigneInventaireId liId = new PharmLigneInventaireId();
					liId.setInvId(pharmInventaire.getInvId());
					liId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					liId.setProgramId(formulaireInventaire.getPharmProgramme().getProgramId());
					
					PharmLigneInventaire li = new PharmLigneInventaire();
					li.setId(liId);
					li.setObservation(ligne.getObservation());
					li.setQteLogique(ligne.getQteLogique());
					li.setQtePhysique(ligne.getQtePhysique());
					li.setEcart(ligne.getEcart());
					li.setPharmInventaire(pharmInventaire);
					li.setPharmProduitAttribut(pharmProduitAttribut);
					li.setPharmProgramme(formulaireInventaire.getPharmProgramme());
					Context.getService(InventaireService.class).savePharmLigneInventaire(li);

					// insertion dans histoMvm
					PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
					histoMouvementStock.setMvtDate(pharmInventaire.getInvDeb());
					histoMouvementStock.setMvtLot(ligne.getLgnLot());

					histoMouvementStock.setMvtProgramme(formulaireInventaire.getPharmProgramme().getProgramId());
					histoMouvementStock.setMvtQte(ligne.getQtePhysique());
					histoMouvementStock.setMvtQteStock(ligne.getQteLogique());
					histoMouvementStock.setMvtTypeMvt(
							Context.getService(ParametresService.class).getTypeOperationById(8).getToperId());
					histoMouvementStock.setPharmProduit(ligne.getProduit());
					Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);

				}
				model.addAttribute("mess", "save");
			}
			model.addAttribute("formulaireInventaire", formulaireInventaire);
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
