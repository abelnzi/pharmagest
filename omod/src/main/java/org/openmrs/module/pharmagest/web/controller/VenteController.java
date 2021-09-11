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
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmAssurance;
import org.openmrs.module.pharmagest.PharmClient;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneAssurance;
import org.openmrs.module.pharmagest.PharmLigneAssuranceId;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmPrixProduit;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.metier.ContaintStock;
import org.openmrs.module.pharmagest.metier.FormulaireVente;
import org.openmrs.module.pharmagest.metier.GestionAlert;
import org.openmrs.module.pharmagest.metier.GestionContrainte;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.validator.Dispensation2Validator;
import org.openmrs.module.pharmagest.validator.DispensationAjoutValidator;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
import org.openmrs.module.pharmagest.validator.VenteAjoutValidator;
import org.openmrs.module.pharmagest.validator.VenteEditValidator;
import org.openmrs.module.pharmagest.validator.VenteSaveValidator;
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
@SessionAttributes("formulaireVente")

public class VenteController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	VenteSaveValidator venteSaveValidator;
	@Autowired
	VenteAjoutValidator venteAjoutValidator;
	@Autowired
	VenteEditValidator  venteEditValidator;

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.GET)
	public String initDispenser(ModelMap model) {
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
		return "/module/pharmagest/vente";
	}

	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/vente";
	}

	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {
		formulaireVente.setTypeVente(request.getParameter("typeVente"));
		
		venteEditValidator.validate(formulaireVente, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();		
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();

		if (!result.hasErrors()) {
			
			String varTest="";
			//Gestion des assurés connus
			boolean test=(formulaireVente.getTypeVente().equals("I"));
			if(test){
				if(formulaireVente.getNewAssur().equals("N")){
					
					PharmLigneAssurance ligneAssure=Context.getService(DispensationService.class).getPharmLigneAssuranceByAttri(formulaireVente.getAssurance(), formulaireVente.getNumPatient());
					
					if(ligneAssure!=null){
						formulaireVente.setClient(ligneAssure.getPharmClient());
						formulaireVente.setTaux(ligneAssure.getLaTaux());						
						} else {
							varTest="IC";
						}
				}
				
			}
			
			model.addAttribute("formulaireVente", formulaireVente);
			model.addAttribute("programmes", programmes);
			
			model.addAttribute("produits",
						this.transformToList(formulaireVente.getPharmProgramme().getPharmProduits()));
			
			model.addAttribute("medecins", medecins);
			model.addAttribute("assurances", assurances);
			
			//gestion de la contraintre lié à l'inventaire
			if(new GestionContrainte().autoriserOperation(formulaireVente.getPharmProgramme(),
					formulaireVente.getOrdDateDispen())){
				
			if(varTest=="IC"){//Gestion des clients non trouvés
				model.addAttribute("var", "0");
				model.addAttribute("mess", "IC");
				}else{
					model.addAttribute("var", "1");
				}
				
			}else{
				model.addAttribute("mess", "aut");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				model.addAttribute("dateAut", dateFormat.format(formulaireVente.getOrdDateDispen()));
				model.addAttribute("var", "0");
			}
			
			
			
		} else {
			model.addAttribute("formulaireVente", formulaireVente);
			model.addAttribute("programmes", programmes);
			model.addAttribute("medecins", medecins);
			model.addAttribute("assurances", assurances);
			model.addAttribute("var", "0");
		}

	}

	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			venteAjoutValidator.validate(formulaireVente, result);

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();
			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
					.getAllMedecins();

			model.addAttribute("programmes", programmes);

			
			model.addAttribute("produits",
						this.transformToList(formulaireVente.getPharmProgramme().getPharmProduits()));
			
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", assurances);

			if (!result.hasErrors()) {
				
				//gestion des prix
				
				PharmPrixProduit produitPrix=Context.getService(ProduitService.class).getPharmPrixProduitByPP(formulaireVente.getPharmProduit(),
						formulaireVente.getPharmProgramme().getProgramId());
				
				int prixVente = (produitPrix!=null)?produitPrix.getPrixVente():0;
				
				
				// contrôle du Stock
				List<PharmStocker> stockerList = (ArrayList<PharmStocker>) Context.getService(GestionStockService.class)
						.getPharmStockersByPP(formulaireVente.getPharmProduit(),
								formulaireVente.getPharmProgramme());

				if (!stockerList.isEmpty()) {
					// Collections.sort(stockerList);
					Collections.sort(stockerList, Collections.reverseOrder());

					Integer qte = formulaireVente.getLdQteServi();
					Integer qteTotalServi = 0;
					boolean condition = true;
					Iterator it = stockerList.iterator();
					while (it.hasNext() && condition == true) {
						PharmStocker stock = (PharmStocker) it.next();
						if (stock.getStockQte() >= qte) {

							LigneDispensationMvt lgnDisp = new LigneDispensationMvt();
							lgnDisp.setPharmProduit(formulaireVente.getPharmProduit());
							lgnDisp.setLdPrixUnit(prixVente);
							lgnDisp.setLdQteDem(formulaireVente.getLdQteDem());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot());
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());
							lgnDisp.setLdQteServi(qte);
							if (qte != 0)
								formulaireVente.getTabDispensationMvt().addLigneDispensation(lgnDisp);
							qteTotalServi = qteTotalServi + qte;
							condition = false;

						} else {
							LigneDispensationMvt lgnDisp = new LigneDispensationMvt();
							lgnDisp.setPharmProduit(formulaireVente.getPharmProduit());
							lgnDisp.setLdPrixUnit(prixVente);
							lgnDisp.setLdQteDem(formulaireVente.getLdQteDem());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot());
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());
							lgnDisp.setLdQteServi(stock.getStockQte());
							formulaireVente.getTabDispensationMvt().addLigneDispensation(lgnDisp);

							qteTotalServi = qteTotalServi + lgnDisp.getLdQteServi();
							qte = qte - stock.getStockQte();
						}
					}

					if (formulaireVente.getLdQteDem() <= qteTotalServi) {

						model.addAttribute("mess", "accept");

					} else if (formulaireVente.getLdQteDem() > qteTotalServi) {

						model.addAttribute("mess", "refuse");

					}
					model.addAttribute("ligneDispensations",
							formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
					model.addAttribute("var", "1");
					model.addAttribute("formulaireVente", formulaireVente);
				} else {
					model.addAttribute("ligneDispensations",
							formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
					model.addAttribute("mess", "refuse");
				}
				formulaireVente.setPharmProduit(null);
				formulaireVente.setLdQteServi(null);
				formulaireVente.setLdQteDem(null);
				formulaireVente.setLdPrixUnit(null);

				model.addAttribute("formulaireVente", formulaireVente);
				model.addAttribute("var", "1");
				model.addAttribute("ligneDispensations",
						formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
				
				//Gestion du total des prix
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireVente.getTabDispensationMvt().getLigneDispensations()));
				

			} else {
				formulaireVente.setPharmProduit(null);
				formulaireVente.setLdQteDem(null);
				formulaireVente.setLdQteServi(null);
				formulaireVente.setLdPrixUnit(null);
				model.addAttribute("formulaireVente", formulaireVente);
				model.addAttribute("var", "1");
				model.addAttribute("ligneDispensations",
						formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());		
				//Gestion du total des prix
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireVente.getTabDispensationMvt().getLigneDispensations()));
			}

		/*} catch (Exception e) {
			e.getStackTrace();
		}*/

	}
	
	
	public int gestionPrix(Map map){
		int total=0;
		Map lignes = map;
		for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
			Object key = i.next();
			LigneDispensationMvt ligne = (LigneDispensationMvt) lignes.get(key);
			total=total+ ligne.getLdPrixUnit()*ligne.getLdQteServi();				   	
		}
		return total;
	}

	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.POST, params = { "btn_enregistrer" })
	public String saveDispensation(@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {
		// try {

		venteSaveValidator.validate(formulaireVente, result);
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		//List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();

		if (!result.hasErrors()) {
			// save ordonnance
			PharmOrdonnance ord = new PharmOrdonnance();
			ord = formulaireVente.getPharmOrdonnance();
			ord.setOrdDateSaisi(new Date());
			// ord.setPharmGestionnairePharma(formulaireVente.getGestionnairePharma());
			if (ord.getOrdDateRdv() == null && ord.getOrdNbreJrsTrai()!=null) {
				Date datRdV = ord.getOrdDateDispen();
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(datRdV);
				gc.add(GregorianCalendar.DATE, ord.getOrdNbreJrsTrai());
				ord.setOrdDateRdv(gc.getTime());				
			}
			//ord.setOrdNum(formulaireVente.getNumPatient());	
			ord.setOrdNumeroPatient(formulaireVente.getNumPatient());
			
			
			
			//save client 
			if(formulaireVente.getTypeVente().equals("D"))formulaireVente.setClient(formulaireVente.getClientClient());//client vente directe
			PharmClient client=formulaireVente.getClient();
			client.setCliDateSaisi(new Date());			
			Context.getService(DispensationService.class).savePharmClient(client);//client save
			
			ord.setPharmClient(client);
			Context.getService(DispensationService.class).savePharmOrdonnance(ord);//ord save
			
			boolean test=(formulaireVente.getTypeVente().equals("I"));
			if(test){
				if(formulaireVente.getNewAssur().equals("O")){			
				//save ligne assurance
					PharmLigneAssuranceId assurId=new PharmLigneAssuranceId();
					assurId.setAssurId(formulaireVente.getAssurance().getAssurId());
					assurId.setCliId(client.getCliId());
					PharmLigneAssurance assur=new PharmLigneAssurance();
					assur.setId(assurId);
					assur.setLaTaux(formulaireVente.getTaux());
					assur.setLaNumAssur(formulaireVente.getNumPatient());
					assur.setPharmAssurance(formulaireVente.getAssurance());
					assur.setPharmClient(client);
					Context.getService(DispensationService.class).savePharmLigneAssurance(assur);
				}else{
					
					PharmLigneAssurance ligneAssure=Context.getService(DispensationService.class).getPharmLigneAssuranceByAttri(formulaireVente.getAssurance(), formulaireVente.getNumPatient());
					formulaireVente.setClient(ligneAssure.getPharmClient());
					formulaireVente.setTaux(ligneAssure.getLaTaux());
				}
				
			}
			
			// save operation
			PharmOperation operation = new PharmOperation();
			operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(2));
			operation.setOperDateRecept(ord.getOrdDateDispen());
			operation.setOperDateSaisi(new Date());
			operation.setPharmGestionnairePharma(ord.getPharmGestionnairePharma());
			operation.setPharmProgramme(ord.getPharmProgramme());
			operation.setOperRef(ord.getOrdId()+"");
			operation.setOperNum(ord.getOrdNumeroPatient());
			Context.getService(OperationService.class).savePharmOperation(operation);
			// save ligne dispensation
			Map lignes = formulaireVente.getTabDispensationMvt().getLigneDispensations();
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
				//System.out.println("stocker.getStockQte() :: "+stocker.getStockQte());
				//System.out.println(" ld.getLdQteServi() :: "+ ld.getLdQteServi());
				//System.out.println(" ligne.getLdQteServi() :: "+ ligne.getLdQteServi());
				if (stocker != null) {
					Integer stockQte = stocker.getStockQte() - ligne.getLdQteServi();
					stocker.setStockQte(stockQte);
					stocker.setStockDateStock(ord.getOrdDateDispen());
					Context.getService(GestionStockService.class).updatePharmStocker(stocker);
					// insertion dans histoMvm
					PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
					histoMouvementStock.setMvtDate(ord.getOrdDateDispen());
					histoMouvementStock.setMvtLot(ligne.getLgnRecptLot());

					histoMouvementStock.setMvtProgramme(ord.getPharmProgramme().getProgramId());
					histoMouvementStock.setMvtQte(ligne.getLdQteServi());
					histoMouvementStock.setMvtQteStock(stocker.getStockQte());
					histoMouvementStock.setMvtTypeMvt(operation.getPharmTypeOperation().getToperId());
					histoMouvementStock.setPharmProduit(ligne.getPharmProduit());
					histoMouvementStock.setPharmOperation(operation);
					Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);

					// save PharmligneOperation
					PharmLigneOperation lo = new PharmLigneOperation();
					PharmLigneOperationId loId = new PharmLigneOperationId();
					loId.setOperId(operation.getOperId());
					loId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					lo.setId(loId);
					lo.setPharmOperation(operation);
					lo.setPharmProduitAttribut(pharmProduitAttribut);
					lo.setLgnOperDatePerem(ligne.getLgnDatePerem());
					lo.setLgnOperLot(ligne.getLgnRecptLot());
					lo.setLgnOperPrixAchat(ligne.getLdPrixUnit());
					lo.setLgnOperQte(ligne.getLdQteServi());
					Context.getService(OperationService.class).savePharmLigneOperation(lo);
				}

			}
			model.addAttribute("mess", "save");
			//Gestion du total des prix
			model.addAttribute("totalGlobal", this.gestionPrix(formulaireVente.getTabDispensationMvt().getLigneDispensations()));
			
			model.addAttribute("formulaireVente", formulaireVente);
			model.addAttribute("ligneDispensations",
					formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
			if(formulaireVente.getTypeVente()=="I")model.addAttribute("info", "I");
			if(formulaireVente.getTypeVente()=="D")model.addAttribute("info", "D");
			
			//this.initialisation(model);
			return "/module/pharmagest/recuOrdonnance";
		} else {
			model.addAttribute("formulaireVente", formulaireVente);
			model.addAttribute("programmes", programmes);
			model.addAttribute("var", "1");
			
			model.addAttribute("produits",
						this.transformToList(formulaireVente.getPharmProgramme().getPharmProduits()));
			
			model.addAttribute("medecins", medecins);
			model.addAttribute("assurances", assurances);
			model.addAttribute("ligneDispensations",
					formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
			if(formulaireVente.getTypeVente()=="I")model.addAttribute("info", "I");
			if(formulaireVente.getTypeVente()=="D")model.addAttribute("info", "D");
			return "/module/pharmagest/vente";
		}

		/*
		 * } catch (Exception e) { e.getStackTrace(); }
		 */

	}
	
	@RequestMapping(value = "/module/pharmagest/recuOrdonnance.form", method = RequestMethod.GET)
	public void recuOrdonnance(@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {
		
		model.addAttribute("totalGlobal", this.gestionPrix(formulaireVente.getTabDispensationMvt().getLigneDispensations()));
		
		model.addAttribute("formulaireVente", formulaireVente);
		model.addAttribute("ligneDispensations",
				formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
		if(formulaireVente.getTypeVente()=="I")model.addAttribute("info", "I");
		if(formulaireVente.getTypeVente()=="D")model.addAttribute("info", "D");
		Location defaultLocation = Context.getLocationService().getDefaultLocation();
		model.addAttribute("site", defaultLocation.getName());
		System.out.println("-----------------site-----------------"+defaultLocation.getName());
	}

	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneDispensation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {
		//dispensationValidator.validate(formulaireVente, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		if (!result.hasErrors()) {
			formulaireVente.getTabDispensationMvt().removeLigneDispensationById(paramId);
			model.addAttribute("ligneDispensations",
					formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
			model.addAttribute("var", "1");
			model.addAttribute("mess", "delete");
			if(formulaireVente.getTypeVente()=="I")model.addAttribute("info", "I");
			if(formulaireVente.getTypeVente()=="D")model.addAttribute("info", "D");
		}

		model.addAttribute("formulaireVente", formulaireVente);
		model.addAttribute("programmes", programmes);

		
		model.addAttribute("produits",
					this.transformToList(formulaireVente.getPharmProgramme().getPharmProduits()));
		
		model.addAttribute("medecins", medecins);
		model.addAttribute("assurances", assurances);
		//Gestion du total des prix
		model.addAttribute("totalGlobal", this.gestionPrix(formulaireVente.getTabDispensationMvt().getLigneDispensations()));

	}
	
	@RequestMapping(value = "/module/pharmagest/vente.form",method = RequestMethod.GET, params = { "modifId" })
	public void modifLigneDispensation(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);

		// model.addAttribute("patientIdentifier",
		// patientIdentifier.getPatient());


		if (!result.hasErrors()) {
			LigneDispensationMvt ligne=formulaireVente.getTabDispensationMvt().getLigneDispensationById(modifId);
			formulaireVente.setLdPrixUnit(ligne.getLdPrixUnit());
			formulaireVente.setLdQteDem(ligne.getLdQteDem());
			formulaireVente.setLdQteServi(ligne.getLdQteServi());
			//formulaireVente.setLgnRecptLot(ligne.getLgnRecptLot());
			formulaireVente.setPharmProduit(ligne.getPharmProduit());
			//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			//model.addAttribute("datePerem",formatter.format(ligne.getLgnDatePerem()));
			
			formulaireVente.getTabDispensationMvt().removeLigneDispensationById(modifId);
			model.addAttribute("ligneDispensations",
					formulaireVente.getTabDispensationMvt().getLigneDispensationsCollection());
			model.addAttribute("mess", "supprim");
		}
		model.addAttribute("formulaireVente", formulaireVente);
		model.addAttribute("bar", "1");
		
		model.addAttribute("produits", this.transformToList(formulaireVente.getPharmProgramme().getPharmProduits()));
		
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", assurances);
		model.addAttribute("var", "1");
		//Gestion du total des prix
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireVente.getTabDispensationMvt().getLigneDispensations()));

	}

	@SuppressWarnings("unchecked")
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}

	public void initialisation(ModelMap model) {
		FormulaireVente formulaireVente = new FormulaireVente();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		formulaireVente.setPharmGestionnairePharma(gestionnairePharma);
		formulaireVente.setNewAssur("O");//option par defaut
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();

		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireVente", formulaireVente);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("assurances", assurances);
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
		
		binder.registerCustomEditor(PharmAssurance.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmAssurance assurance = Context.getService(DispensationService.class)
						.getPharmAssuranceById(Integer.parseInt(text));
				this.setValue(assurance);
			}
		});

	}

}
