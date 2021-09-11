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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.ContaintStock;
import org.openmrs.module.pharmagest.metier.FormulaireStockFournisseur;
import org.openmrs.module.pharmagest.metier.GestionAlert;
import org.openmrs.module.pharmagest.metier.GestionContrainte;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.metier.LigneOperationMvt;
import org.openmrs.module.pharmagest.validator.StockEntree2Validator;
import org.openmrs.module.pharmagest.validator.StockEntreeValidator;
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
@RequestMapping(value = "/module/pharmagest/stockSortie.form")
public class StockMvmSortieController {
	@Autowired
	StockEntreeValidator stockValidator;
	@Autowired
	StockEntreeValidator stockValidatorAjout;
	@Autowired
	StockEntree2Validator stock2Validator;

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initStockFour(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/stockSortie";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		//gestion des périmés
		List<ContaintStock> listStockPerim= new GestionAlert().getProduitsPerimes();
		Iterator it = listStockPerim.iterator();
		while (it.hasNext()) {
			ContaintStock containtStock = (ContaintStock) it.next();
			PharmProduitAttribut produitAttribut=containtStock.getPharmProduitAttribut();
			produitAttribut.setFlagValid(false);
			Context.getService(OperationService.class).savePharmProduitAttribut(produitAttribut);				
		}
		return "/module/pharmagest/stockSortie";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(@ModelAttribute("formulaireStockFourni") FormulaireStockFournisseur formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			stock2Validator.validate(formulaireStockFourni, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			if (!result.hasErrors()) {
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				
				//gestion de la contraintre lié à l'inventaire
				if(new GestionContrainte().autoriserOperation(formulaireStockFourni.getPharmProgramme(),
						formulaireStockFourni.getOperDateRecept())){
					model.addAttribute("var", "1");
				}else{
					model.addAttribute("mess", "aut");
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					model.addAttribute("dateAut", dateFormat.format(formulaireStockFourni.getOperDateRecept()));
					model.addAttribute("var", "0");
				}
				
			}else{
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				
				model.addAttribute("var", "0");
			}
				
				

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireStockFourni") FormulaireStockFournisseur formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			stockValidatorAjout.validate(formulaireStockFourni, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {

				
				// contrôle du Stock
				List<PharmStocker> stockerList=null;
				if(formulaireStockFourni.getPharmTypeOperation().getToperId()==7){
				stockerList = (ArrayList<PharmStocker>) Context.getService(GestionStockService.class)
						.getPharmStockersSorti(formulaireStockFourni.getProduit(),formulaireStockFourni.getLgnRecptLot(),
								formulaireStockFourni.getPharmProgramme(),false);
				}else {
					stockerList = (ArrayList<PharmStocker>) Context.getService(GestionStockService.class)
							.getPharmStockersSorti(formulaireStockFourni.getProduit(),formulaireStockFourni.getLgnRecptLot(),
									formulaireStockFourni.getPharmProgramme(),true);
				}

				if (!stockerList.isEmpty()) {
					// Collections.sort(stockerList);
					Collections.sort(stockerList, Collections.reverseOrder());

					Integer qte = formulaireStockFourni.getLgnRecptQte();
					Integer qteTotalServi = 0;
					boolean condition = true;
					Iterator it = stockerList.iterator();
					while (it.hasNext() && condition == true) {
						PharmStocker stock = (PharmStocker) it.next();
						if (stock.getStockQte() >= qte) {

							LigneOperationMvt lgnDisp = new LigneOperationMvt();
							lgnDisp.setOperation(formulaireStockFourni.getPharmOperation());
							lgnDisp.setProduit(formulaireStockFourni.getProduit());
							lgnDisp.setLgnRecptQte(qte);
							lgnDisp.setLgnRecptPrixAchat(formulaireStockFourni.getLgnRecptPrixAchat());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot().replaceAll(" ", ""));
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());

							if (qte != 0)
								formulaireStockFourni.getTabOperationMvt().addLigneOperation(lgnDisp);
							qteTotalServi = qteTotalServi + qte;
							condition = false;

						} else {
							LigneOperationMvt lgnDisp = new LigneOperationMvt();
							lgnDisp.setOperation(formulaireStockFourni.getPharmOperation());
							lgnDisp.setProduit(formulaireStockFourni.getProduit());
							lgnDisp.setLgnRecptQte(stock.getStockQte());
							lgnDisp.setLgnRecptPrixAchat(formulaireStockFourni.getLgnRecptPrixAchat());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot().replaceAll(" ", ""));
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());

							formulaireStockFourni.getTabOperationMvt().addLigneOperation(lgnDisp);

							qteTotalServi = qteTotalServi + lgnDisp.getLgnRecptQte();
							qte = qte - stock.getStockQte();
						}
					}
					

					if (formulaireStockFourni.getLgnRecptQte() <= qteTotalServi) {

						model.addAttribute("mess", "valid");

					} else if (formulaireStockFourni.getLgnRecptQte() > qteTotalServi) {
						model.addAttribute("mess", "insufisant");
					}
					
					//gestion des retours
					PharmProduitAttribut pdAttr=Context.getService(OperationService.class).getPharmProduitAttributByElement(formulaireStockFourni.getProduit()
							, formulaireStockFourni.getLgnRecptLot());
					System.out.println("----------------formulaireStockFourni.getPharmOperation()---------------------" + formulaireStockFourni.getPharmOperation());
					System.out.println("----------------formulaireStockFourni.getOperation().getPharmTypeOperation()---------------------" + formulaireStockFourni.getPharmOperation().getPharmTypeOperation());
					int tOper=(formulaireStockFourni.getPharmOperation().getPharmTypeOperation()!=null)?
							formulaireStockFourni.getPharmOperation().getPharmTypeOperation().getToperId():0;
					System.out.println("----------------tOper---------------------" + tOper);
					if (pdAttr!=null && tOper ==16
							&& this.contrainteRetour(formulaireStockFourni.getPharmProgramme(), formulaireStockFourni.getOperNum(), pdAttr,qteTotalServi)){ 
						System.out.println("----------------ici---------------------");
						model.addAttribute("mess", "echecRetour");
						model.addAttribute("qteRetour", qteTotalServi);
						model.addAttribute("lotProd", formulaireStockFourni.getProduit().getFullName()+"--"+formulaireStockFourni.getLgnRecptLot());
						formulaireStockFourni.getTabOperationMvt().removeLigneOperationById(
								formulaireStockFourni.getProduit().getProdId()+formulaireStockFourni.getLgnRecptLot());
						}
					
					
					model.addAttribute("ligneOperations",
							formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
					model.addAttribute("var", "1");
					model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				} else {
					model.addAttribute("ligneOperations",
							formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
					model.addAttribute("mess", "indisponible");
				}

				
				formulaireStockFourni.setProduit(null);
				formulaireStockFourni.setLgnDatePerem(null);
				formulaireStockFourni.setLgnRecptLot(null);
				formulaireStockFourni.setLgnRecptQte(null);

				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			} else {
				formulaireStockFourni.setProduit(null);
				formulaireStockFourni.setLgnDatePerem(null);
				formulaireStockFourni.setLgnRecptLot(null);
				formulaireStockFourni.setLgnRecptQte(null);

				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				model.addAttribute("ligneOperations",
						formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
				model.addAttribute("var", "1");
			}
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/

	}
	
	public boolean contrainteRetour(PharmProgramme programme,String bl,PharmProduitAttribut produitAttribut,int qteTotalServi){
		
		int qteRecept=0;
		int qteRetour=qteTotalServi;
		Collection<PharmReception> receptions =Context.getService(GestionReceptionService.class)
				.getPharmReceptionByBL(programme, bl);
		Iterator it= receptions.iterator();
		while (it.hasNext()) {
				PharmReception recept= (PharmReception)it.next();
				PharmLigneReceptionId ligneId= new PharmLigneReceptionId();
				ligneId.setProdAttriId(produitAttribut.getProdAttriId());
				ligneId.setRecptId(recept.getRecptId());
				PharmLigneReception ligne=Context.getService(GestionReceptionService.class).getPharmLigneReception(ligneId);
				if(ligne!=null)qteRecept=qteRecept+ligne.getLgnRecptQteDetailRecu();
			}
		
		Collection<PharmOperation> operations=Context.getService(OperationService.class).getPharmOperationsByBL(programme, 
				bl, Context.getService(ParametresService.class).getTypeOperationById(16));
		if (operations!=null && !operations.isEmpty()) {
			Iterator it2 = operations.iterator();
			while (it2.hasNext()) {
				PharmOperation oper = (PharmOperation) it2.next();
				PharmLigneOperationId ligneId = new PharmLigneOperationId();
				ligneId.setProdAttriId(produitAttribut.getProdAttriId());
				ligneId.setOperId(oper.getOperId());
				PharmLigneOperation ligne = Context.getService(OperationService.class)
						.getPharmLigneOperationByID(ligneId);
				qteRetour = qteRetour + ligne.getLgnOperQte();
			} 
		}
		System.out.println("------------------qteRetour------------"+qteRetour);
		System.out.println("------------------qteRecept------------"+qteRecept);
		if(qteRetour>qteRecept)return true;		
		return false;		
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

					
			if (!result.hasErrors()) {
				formulaireStockFourni.getTabOperationMvt().removeLigneOperationById(paramId);
				model.addAttribute("ligneOperations",
						formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("mess", "delete");
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}else{
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
	public void modifLigneOperation(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireStockFourni") FormulaireStockFournisseur formulaireStockFourni,
			BindingResult result, HttpSession session, ModelMap model) {

		try {
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
			if (!result.hasErrors()) {
				
				LigneOperationMvt ligne=formulaireStockFourni.getTabOperationMvt().getLigneOperationById(modifId);
				formulaireStockFourni.setLgnRecptLot(ligne.getLgnRecptLot());
				formulaireStockFourni.setLgnRecptPrixAchat(ligne.getLgnRecptPrixAchat());
				formulaireStockFourni.setLgnRecptQte(ligne.getLgnRecptQte());
				formulaireStockFourni.setProduit(ligne.getProduit());
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				model.addAttribute("datePerem",formatter.format(ligne.getLgnDatePerem()));
				
				formulaireStockFourni.getTabOperationMvt().removeLigneOperationById(modifId);
				model.addAttribute("ligneOperations",
						formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("mess", "delete");
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
				
			}else{
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", this.transformToList(formulaireStockFourni.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}
			
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
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			// save operation
			if (!result.hasErrors()) {
				PharmOperation operation = new PharmOperation();
				operation = formulaireStockFourni.getPharmOperation();
				Context.getService(OperationService.class).savePharmOperation(operation);

				// save ligne dispensation

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
						PharmStocker stocker = Context.getService(GestionStockService.class)
								.getPharmStockerById(stockerId);
						if (stocker != null ) {
							Integer stockQte = stocker.getStockQte() - ligne.getLgnRecptQte();
							stocker.setStockQte(stockQte);
							stocker.setStockDateStock(operation.getOperDateRecept());
							Context.getService(GestionStockService.class).updatePharmStocker(stocker);
							// insertion dans histoMvm
							PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
							histoMouvementStock.setMvtDate(operation.getOperDateRecept());
							histoMouvementStock.setMvtLot(ligne.getLgnRecptLot());

							histoMouvementStock.setMvtProgramme(operation.getPharmProgramme().getProgramId());
							histoMouvementStock.setMvtQte(ligne.getLgnRecptQte());
							histoMouvementStock.setMvtQteStock(stocker.getStockQte());
							histoMouvementStock.setMvtTypeMvt(operation.getPharmTypeOperation().getToperId());
							histoMouvementStock.setPharmProduit(ligne.getProduit());
							histoMouvementStock.setPharmOperation(operation);
							Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
						} else {
							model.addAttribute("mess", "insufisant");
						}
					} else {
						model.addAttribute("mess", "indisponible");
					}

				}
				formulaireStockFourni = new FormulaireStockFournisseur();
				model.addAttribute("mess", "success");
				this.initialisation(model);
			} else {
				model.addAttribute("formulaireStockFourni", formulaireStockFourni);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", produits);
				model.addAttribute("ligneOperations",
						formulaireStockFourni.getTabOperationMvt().getLigneOperationsCollection());
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}

	public void initialisation(ModelMap model) {

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
		model.addAttribute("var", "0");
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

		binder.registerCustomEditor(PharmTypeOperation.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmTypeOperation typeOperation = Context.getService(ParametresService.class)
						.getTypeOperationById(Integer.parseInt(text));
				this.setValue(typeOperation);
			}
		});

	}

}
