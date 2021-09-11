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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmInventaire;
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
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.BordereauDistribution;
import org.openmrs.module.pharmagest.metier.ConsoByProduit;
import org.openmrs.module.pharmagest.metier.FormulaireDistribution;
import org.openmrs.module.pharmagest.metier.FormulaireInventaire;
import org.openmrs.module.pharmagest.metier.FormulaireModifPPS;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireSaisiesPPS;
import org.openmrs.module.pharmagest.metier.FormulaireSite;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.metier.RapportConsoBean;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
import org.openmrs.module.pharmagest.web.view.Book;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * The main controller.
 */
@Controller
//@SessionAttributes("formulaireDistri")
@SessionAttributes("formulaireTraitementsPPS")
public class HistorisationController {
	
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(value = "/module/pharmagest/listHistoDistribution.form", method = RequestMethod.GET)
	public String initSaisiesPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listHistoDistribution";
	}
	
	@RequestMapping(value = "/module/pharmagest/listHistoDistribution.form", method = RequestMethod.POST, params = { "btn_rechercher" })
	public void listtDistribution(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		listRPPSValidator.validate(formulaireTraitementsPPS, result);

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
			

		if (!result.hasErrors()) {
			
			Collection<PharmCommandeSite> commandeSites;
			if (formulaireTraitementsPPS.getDateParam() != null) {
				//System.out.println("---------------------la 1------------------------------");
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteByPeriod(
						formulaireTraitementsPPS.getSite(), formulaireTraitementsPPS.getProgramme(),
						formulaireTraitementsPPS.getDateParam(),null);
			} else {
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteBySP(
						formulaireTraitementsPPS.getSite(), formulaireTraitementsPPS.getProgramme(),null);
			}
			
			if(!commandeSites.isEmpty()){
				model.addAttribute("var", "1");
				model.addAttribute("commandeSites", commandeSites);
			}else {
				model.addAttribute("var", "2");
			}

		}

	}

	@RequestMapping(value = "/module/pharmagest/histoDistribution.form", method = RequestMethod.GET, params = { "paramId" })
	public void histoDistribution(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		
		if (!result.hasErrors()) {
			PharmCommandeSite consommation = (PharmCommandeSite) Context
					.getService(CommandeSiteService.class).getPharmCommandeSiteById(Integer.parseInt(paramId));
			
				model.addAttribute("consommation", consommation);
				
			

		}

	}

	@RequestMapping(value = "/module/pharmagest/listBorderoDistribution.form", method = RequestMethod.GET)
	public String initBordero(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listBorderoDistribution";
	}
	@RequestMapping(value = "/module/pharmagest/listBorderoDistribution.form", method = RequestMethod.POST, params = { "btn_rechercher" })
	public void listBordero(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		listRPPSValidator.validate(formulaireTraitementsPPS, result);

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
			

		if (!result.hasErrors()) {
			
			Collection<PharmCommandeSite> commandeSites;
			if (formulaireTraitementsPPS.getDateParam() != null) {
				//System.out.println("---------------------la 1------------------------------");
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteByPeriod(
						formulaireTraitementsPPS.getSite(), formulaireTraitementsPPS.getProgramme(),
						formulaireTraitementsPPS.getDateParam(),"TR");
			} else {
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteBySP(
						formulaireTraitementsPPS.getSite(), formulaireTraitementsPPS.getProgramme(),"TR");
			}
			
			if(!commandeSites.isEmpty()){
				model.addAttribute("var", "1");
				model.addAttribute("commandeSites", commandeSites);
			} else {
				model.addAttribute("var", "2");
			}

		}

	}
	
	@RequestMapping(value = "/module/pharmagest/bordereauDistribution.form", method = RequestMethod.GET, params = { "paramId" })
	public void histoBordero(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		if (!result.hasErrors()) {
			PharmCommandeSite consommation = (PharmCommandeSite) Context
					.getService(CommandeSiteService.class).getPharmCommandeSiteById(Integer.parseInt(paramId));
			
				PharmOperation operation = Context.getService(OperationService.class).getPharmOperationsByRef(
						consommation.getPharmProgramme(), consommation.getComSiteId()+"",
						Context.getService(ParametresService.class).getTypeOperationById(9));
				//System.out.println("--------------operation---------------------- "+operation.getOperId());
				
				model.addAttribute("consommation", consommation);
				model.addAttribute("bordereaux", this.getBordereauDistribution(consommation, operation));
		}

	}
	
	public Map<PharmProduit, PharmLigneCommandeSite> transformToMap(Set<PharmLigneCommandeSite> list) {
		Map<PharmProduit, PharmLigneCommandeSite> listNew = Collections.synchronizedMap(new HashMap());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PharmLigneCommandeSite conso = (PharmLigneCommandeSite) it.next();
			listNew.put(conso.getPharmProduit(), conso);
		}
		return listNew;

	}
	
	
	public Collection<BordereauDistribution> getBordereauDistribution(PharmCommandeSite commandeSite, PharmOperation operation) {
		Map<PharmProduit, PharmLigneCommandeSite> mapCommandes = this.transformToMap(commandeSite.getPharmLigneCommandeSites());
		
		ArrayList<BordereauDistribution> listBordereaux = new ArrayList<BordereauDistribution>();
		
		Iterator it=operation.getPharmLigneOperations().iterator();
		while (it.hasNext()) {
			PharmLigneOperation ligneOperation = (PharmLigneOperation) it.next();
			PharmProduit produit=ligneOperation.getPharmProduitAttribut().getPharmProduit();
			BordereauDistribution bordereau = new BordereauDistribution();
			bordereau.setLigneOperation(ligneOperation);
			if(mapCommandes.containsKey(produit)) {
				bordereau.setLigneCommandeSite(mapCommandes.get(produit));
			}
			listBordereaux.add(bordereau);			
			
		}
		
		//System.out.println("--------------listBordereaux------------------"+listBordereaux.size());
		return listBordereaux;

	}

	
	
	public void initialisation(ModelMap model) {
		FormulaireTraitementsPPS formulaireTraitementsPPS = new FormulaireTraitementsPPS();
		model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		// formulaireSaisiesPPS.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
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

		binder.registerCustomEditor(PharmSite.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmSite site = Context.getService(SiteService.class).getPharmSiteById(Integer.parseInt(text));
				this.setValue(site);
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
