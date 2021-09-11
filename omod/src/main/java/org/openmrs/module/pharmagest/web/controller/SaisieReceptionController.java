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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.metier.LigneReceptionMvt;
import org.openmrs.module.pharmagest.validator.ReceptionEditeValidator;
import org.openmrs.module.pharmagest.validator.ReceptionValidator;
import org.openmrs.module.pharmagest.validator.ReceptionValidatorAjout;
import org.openmrs.module.pharmagest.validator.ReceptionValidatorAjoutDetail;
import org.openmrs.module.pharmagest.validator.Stock2Validator;
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
@SessionAttributes("formulaireReception")
@RequestMapping(value = "/module/pharmagest/saisieReception.form")
public class SaisieReceptionController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	ReceptionValidator receptionValidator;
	@Autowired
	ReceptionValidatorAjout receptionValidatorAjout;
	@Autowired
	ReceptionValidatorAjoutDetail receptionValidatorAjoutDetail;
	@Autowired
	ReceptionEditeValidator receptionEditeValidator;
	

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initStockFour(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/saisieReception";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/saisieReception";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			receptionEditeValidator.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			/*List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();*/

			if (!result.hasErrors()) {
				if(this.autoriserOperation(formulaireReception.getPharmProgramme(),
						formulaireReception.getRecptDateRecept())){ //valider la date de l'operation
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				
				
				//System.out.println("----------------formulaireReception.getTypeReception------------------"+formulaireReception.getTypeReception());
				if(formulaireReception.getTypeReception()=="Detail")model.addAttribute("choix", "Detail");
				if(formulaireReception.getTypeReception()=="Gros")model.addAttribute("choix", "Gros");
				model.addAttribute("var", "1");
				
				} else {
					model.addAttribute("formulaireReception", formulaireReception);
					model.addAttribute("fournisseurs", fournisseurs);
					model.addAttribute("programmes", programmes);
					model.addAttribute("var", "0");
					model.addAttribute("mess", "aut");
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					model.addAttribute("dateAut", dateFormat.format(formulaireReception.getRecptDateRecept()));
				}
				
			} else {
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("var", "0");
			}

		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public Boolean autoriserOperation(PharmProgramme programme,Date dateOperation ){
		PharmInventaire inventaire=Context.getService(InventaireService.class).getPharmInventaireForAuthorize(programme);
		if(inventaire==null)return true ;
		if(inventaire.getInvDeb().after(dateOperation)){
			return false ;
		}
		return true;
		
	}
	
	public int gestionPrix(Map map){
		int total=0;
		Map lignes = map;
		for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
			Object key = i.next();			
			LigneReceptionMvt ligne = (LigneReceptionMvt) lignes.get(key);
			if(ligne.getLgnRecptPrixAchat()==null)ligne.setLgnRecptPrixAchat(0);
			if(ligne.getLgnRecptQte()==null)ligne.setLgnRecptQte(0);
			total=total+ ligne.getLgnRecptPrixAchat()*ligne.getLgnRecptQte();				   	
		}
		return total;
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider_detail" })
	public void addLigneReceptionDetail(
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model ) {
		try {
			receptionValidatorAjoutDetail.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			

			if (!result.hasErrors()) {
				
				
				LigneReceptionMvt lngReception;
				
				lngReception=formulaireReception.getTabReceptionMvt().getLigneReceptionById(
								formulaireReception.getProduitDetail().getProdId()+formulaireReception.getLgnRecptLotDetail());
				
				if(lngReception!=null){
					lngReception.setLgnRecptQteDetailRecu(lngReception.getLgnRecptQteDetailRecu()+formulaireReception.getLgnRecptQteDetailRecu());
					lngReception.setLgnRecptQteDetailLivree(lngReception.getLgnRecptQteDetailLivree()+formulaireReception.getLgnRecptQteDetailLivree());
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduit());
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu()/convers);
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree()/convers);
					} else {
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu());
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree());
					}
					
				} else {
					lngReception = new LigneReceptionMvt();
					lngReception.setReception(formulaireReception.getPharmReception());
					lngReception.setProduit(formulaireReception.getProduitDetail());
					
					lngReception.setLgnRecptPrixAchatDetail(formulaireReception.getLgnRecptPrixAchatDetail());
					lngReception.setLgnRecptPrixAchat(formulaireReception.getLgnRecptPrixAchatDetail());
					lngReception.setLgnRecptLotDetail(formulaireReception.getLgnRecptLotDetail().replaceAll(" ", ""));
					lngReception.setLgnRecptLot(formulaireReception.getLgnRecptLotDetail().replaceAll(" ", ""));
					lngReception.setLgnDatePeremDetail(formulaireReception.getLgnDatePeremDetail());
					lngReception.setLgnDatePerem(formulaireReception.getLgnDatePeremDetail());
					lngReception.setLgnRecptObservDetail(formulaireReception.getLgnRecptObservDetail().trim());
					lngReception.setLgnRecptObserv(formulaireReception.getLgnRecptObservDetail().trim());
					lngReception.setLgnRecptQteDetailRecu(formulaireReception.getLgnRecptQteDetailRecu());
					lngReception.setLgnRecptQteDetailLivree(formulaireReception.getLgnRecptQteDetailLivree());	
					//System.out.println("=============Qte Detail==============="+formulaireReception.getLgnRecptQteDetailRecu());
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduitDetail());
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu()/convers);
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree()/convers);
						//System.out.println("=============convers==============="+convers);
					} else {
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu());
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree());
					}
					
					//System.out.println("=============Prix==============="+lngReception.getLgnRecptPrixAchat());
					//System.out.println("=============Qte==============="+lngReception.getLgnRecptQte());
					
				}
				
				

				formulaireReception.getTabReceptionMvt().addLigneReception(lngReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "valid");

				formulaireReception.setProduitDetail(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				formulaireReception.setLgnRecptPrixAchatDetail(null);
				formulaireReception.setLgnRecptPrixAchat(null);
				
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
				if(formulaireReception.getTypeReception()=="Detail")model.addAttribute("choix", "Detail");
				if(formulaireReception.getTypeReception()=="Gros")model.addAttribute("choix", "Gros");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			} else {
				formulaireReception.setProduitDetail(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				formulaireReception.setLgnRecptPrixAchatDetail(null);
				formulaireReception.setLgnRecptPrixAchat(null);

				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("var", "1");
				if(formulaireReception.getTypeReception()=="Detail")model.addAttribute("choix", "Detail");
				if(formulaireReception.getTypeReception()=="Gros")model.addAttribute("choix", "Gros");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider_gros" })
	public void addLigneReceptionGros(
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			receptionValidatorAjout.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			

			if (!result.hasErrors()) {
				LigneReceptionMvt lngReception;
				//System.out.println("---------------------- vie -----------------------"+formulaireReception.getProduit());
				lngReception=formulaireReception.getTabReceptionMvt().getLigneReceptionById(
								formulaireReception.getProduit().getProdId()+formulaireReception.getLgnRecptLot());
				
				if(lngReception!=null){
					lngReception.setLgnRecptQte(lngReception.getLgnRecptQte()+formulaireReception.getLgnRecptQte());
					lngReception.setLgnRecptQteLivree(lngReception.getLgnRecptQteLivree()+formulaireReception.getLgnRecptQteLivree());
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduit());
					//System.out.println("------------------ produitCompl --------- "+produitCompl);
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQteDetailRecu(formulaireReception.getLgnRecptQte()*convers);
						lngReception.setLgnRecptQteDetailLivree(formulaireReception.getLgnRecptQteLivree()*convers);
					} else {
						lngReception.setLgnRecptQteDetailRecu(formulaireReception.getLgnRecptQte());
						lngReception.setLgnRecptQteDetailLivree(formulaireReception.getLgnRecptQteLivree());
					}
					
				} else {
					lngReception = new LigneReceptionMvt();
					lngReception.setReception(formulaireReception.getPharmReception());
					lngReception.setProduit(formulaireReception.getProduit());
					lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQte());
					lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteLivree());
					lngReception.setLgnRecptPrixAchat(formulaireReception.getLgnRecptPrixAchat());
					lngReception.setLgnRecptPrixAchatDetail(formulaireReception.getLgnRecptPrixAchat());
					lngReception.setLgnRecptLot(formulaireReception.getLgnRecptLot().replaceAll(" ", ""));
					lngReception.setLgnRecptLotDetail(formulaireReception.getLgnRecptLot().replaceAll(" ", ""));
					lngReception.setLgnDatePerem(formulaireReception.getLgnDatePerem());
					lngReception.setLgnDatePeremDetail(formulaireReception.getLgnDatePerem());
					lngReception.setLgnRecptObserv(formulaireReception.getLgnRecptObserv().trim());
					lngReception.setLgnRecptObservDetail(formulaireReception.getLgnRecptObserv().trim());
					
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduit());
					//System.out.println("------------------ produitCompl --------- "+produitCompl);
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQteDetailRecu(formulaireReception.getLgnRecptQte()*convers);
						lngReception.setLgnRecptQteDetailLivree(formulaireReception.getLgnRecptQteLivree()*convers);
						
					} else {
						lngReception.setLgnRecptQteDetailRecu(formulaireReception.getLgnRecptQte());
						lngReception.setLgnRecptQteDetailLivree(formulaireReception.getLgnRecptQteLivree());
					}
				
				}
				
				

				formulaireReception.getTabReceptionMvt().addLigneReception(lngReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "valid");

				formulaireReception.setProduit(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				formulaireReception.setLgnRecptPrixAchatDetail(null);
				formulaireReception.setLgnRecptPrixAchat(null);

				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
				if(formulaireReception.getTypeReception()=="Detail")model.addAttribute("choix", "Detail");
				if(formulaireReception.getTypeReception()=="Gros")model.addAttribute("choix", "Gros");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			} else {
				formulaireReception.setProduit(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				formulaireReception.setLgnRecptPrixAchatDetail(null);
				formulaireReception.setLgnRecptPrixAchat(null);

				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("var", "1");
				if(formulaireReception.getTypeReception()=="Detail")model.addAttribute("choix", "Detail");
				if(formulaireReception.getTypeReception()=="Gros")model.addAttribute("choix", "Gros");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneReception(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {

		try {
			//receptionValidator.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			
			if (!result.hasErrors()) {
				formulaireReception.getTabReceptionMvt().removeLigneReceptionById(paramId);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("info", formulaireReception.getPharmFournisseur());
				model.addAttribute("var", "1");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			} else {
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
	public void ModifLigneReception(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {

		//try {
			receptionValidator.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			
			
			if (!result.hasErrors()) {

				LigneReceptionMvt ligne=formulaireReception.getTabReceptionMvt().getLigneReceptionById(modifId);
				formulaireReception.setLgnRecptLot(ligne.getLgnRecptLot().replaceAll(" ", ""));
				formulaireReception.setLgnRecptPrixAchat(ligne.getLgnRecptPrixAchat());
				formulaireReception.setLgnRecptQte(ligne.getLgnRecptQte());
				formulaireReception.setLgnRecptQteLivree(ligne.getLgnRecptQteLivree());
				formulaireReception.setProduit(ligne.getProduit());
				formulaireReception.setLgnRecptQteDetailLivree(ligne.getLgnRecptQteDetailLivree());
				formulaireReception.setLgnRecptQteDetailRecu(ligne.getLgnRecptQteDetailRecu());
				formulaireReception.setLgnRecptObserv(ligne.getLgnRecptObserv().trim());
				
				formulaireReception.setLgnRecptLotDetail(ligne.getLgnRecptLotDetail().replaceAll(" ", ""));
				formulaireReception.setLgnRecptPrixAchatDetail(ligne.getLgnRecptPrixAchatDetail());
				//System.out.println("------------------------------ligne.getProduitDetail()----------------------"+ligne.getProduitDetail());
				formulaireReception.setProduitDetail(ligne.getProduitDetail());
				formulaireReception.setLgnRecptQteDetailLivree(ligne.getLgnRecptQteDetailLivree());
				formulaireReception.setLgnRecptQteDetailRecu(ligne.getLgnRecptQteDetailRecu());
				formulaireReception.setLgnRecptObservDetail(ligne.getLgnRecptObservDetail().trim());
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				if(ligne.getLgnDatePeremDetail()!=null){
				model.addAttribute("datePerem",formatter.format(ligne.getLgnDatePeremDetail()));
				}else {
					model.addAttribute("datePerem",formatter.format(ligne.getLgnDatePerem()));
				}
				formulaireReception.getTabReceptionMvt().removeLigneReceptionById(modifId);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				//System.out.println("------------------------------ici----------------------");
				//model.addAttribute("info", formulaireReception.getPharmFournisseur());
				model.addAttribute("var", "1");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			} else {
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				//System.out.println("------------------------------ici 2---------------------");
				model.addAttribute("var", "1");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}

		/*} catch (Exception e) {
			e.getMessage();
		}*/

	}
	
	

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {
		// try {
		receptionValidator.validate(formulaireReception, result);
		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
				.getAllFournisseurs();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		// save reception
		if (!result.hasErrors()) {
			PharmReception reception = new PharmReception();
			reception = formulaireReception.getPharmReception();
			PharmTypeOperation typeOperation = Context.getService(ParametresService.class).getTypeOperationById(1);
			reception.setPharmTypeOperation(typeOperation);
			reception.setRecptFlag("NV");
			reception.setRecptDateSaisi(new Date());
			reception.setPharmGestionnairePharma(formulaireReception.getPharmGestionnairePharma());
			Context.getService(GestionReceptionService.class).savePharmReception(reception);

			Map lignes = formulaireReception.getTabReceptionMvt().getLigneReceptions();
			for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				LigneReceptionMvt ligne = (LigneReceptionMvt) lignes.get(key);

				// Gestion ProduitAttribut
				PharmProduitAttribut pharmProduitAttribut = new PharmProduitAttribut();
				PharmProduitAttribut varProd = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(ligne.getProduit(), ligne.getLgnRecptLot());
				if (varProd != null) {
					pharmProduitAttribut = varProd;
					pharmProduitAttribut.setProdDatePerem(ligne.getLgnDatePerem());
					pharmProduitAttribut.setFlagValid(true);
					Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
					
				} else {
					pharmProduitAttribut.setPharmProduit(ligne.getProduit());
					pharmProduitAttribut.setProdAttriDate(new Date());
					pharmProduitAttribut.setProdDatePerem(ligne.getLgnDatePerem());
					pharmProduitAttribut.setProdLot(ligne.getLgnRecptLot());
					pharmProduitAttribut.setFlagValid(true);
					Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
				}

				// save PharmligneReception
				PharmLigneReception ld = new PharmLigneReception();
				PharmLigneReceptionId ldId = new PharmLigneReceptionId();
				ldId.setRecptId(reception.getRecptId());
				ldId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
				ld.setId(ldId);
				ld.setPharmReception(reception);
				ld.setPharmProduitAttribut(pharmProduitAttribut);				
				ld.setLgnRecptPrix(ligne.getLgnRecptPrixAchat());
				ld.setLgnRecptQteDetailLivre(ligne.getLgnRecptQteDetailLivree());
				ld.setLgnRecptQteDetailRecu(ligne.getLgnRecptQteDetailRecu());
				ld.setLgnRecptQteLivre(ligne.getLgnRecptQteLivree());
				ld.setLgnRecptQteRecu(ligne.getLgnRecptQte());
				ld.setLgnRecptObserv(ligne.getLgnRecptObserv());
				Context.getService(GestionReceptionService.class).savePharmLigneReception(ld);	


			}
			model.addAttribute("mess", "success");
			this.initialisation(model);
		} else {

			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
			model.addAttribute("formulaireReception", formulaireReception);
			model.addAttribute("ligneReceptions",
					formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
			model.addAttribute("var", "1");
			model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			
		}

		// } catch (Exception e) {
		// e.getMessage();
		// }

	}

	

	
	public List<PharmProduitCompl> transformToList(Set<PharmProduit> set) {
		//List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		//Collections.sort(list, Collections.reverseOrder());		
		List<PharmProduitCompl> listCpl = new ArrayList<PharmProduitCompl>();
		for (PharmProduit pharmProduit : set) {
			PharmProduitCompl produitCpl=Context.getService(ProduitService.class).getProduitComplByProduit(pharmProduit);
			if(produitCpl!=null)listCpl.add(produitCpl);
		}
		
		Collections.sort(listCpl, Collections.reverseOrder());		
		return listCpl;
	}

	public void initialisation(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		FormulaireReception formulaireReception = new FormulaireReception();
		model.addAttribute("formulaireReception", formulaireReception);
		if(Context.hasPrivilege("pharmacie")) {
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		formulaireReception.setPharmGestionnairePharma(gestionnairePharma);
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
