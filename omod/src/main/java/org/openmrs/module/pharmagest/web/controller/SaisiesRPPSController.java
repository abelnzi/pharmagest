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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireSaisiesPPS;
import org.openmrs.module.pharmagest.metier.FormulaireStockFournisseur;
import org.openmrs.module.pharmagest.metier.LigneCommandeSite;
import org.openmrs.module.pharmagest.validator.SaisiePPS2Validator;
import org.openmrs.module.pharmagest.validator.SaisiePPSValidator;
import org.openmrs.module.pharmagest.validator.SaisiePPSValidatorAjout;
import org.openmrs.module.pharmagest.validator.StockValidator;
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
@SessionAttributes("formulaireSaisiesPPS")
@RequestMapping(value = "/module/pharmagest/saisiesRPPS.form")
public class SaisiesRPPSController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	SaisiePPSValidator saisiePPSValidator;
	@Autowired
	SaisiePPSValidatorAjout saisiePPSValidatorAjout;
	@Autowired
	SaisiePPS2Validator saisiePPS2Validator;

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initSaisiesPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/saisiesRPPS";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/saisiesRPPS";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(@ModelAttribute("formulaireSaisiesPPS") FormulaireSaisiesPPS formulaireSaisiesPPS,
			BindingResult result, HttpSession session, ModelMap model , HttpServletRequest request) {
		try {
			saisiePPS2Validator.validate(formulaireSaisiesPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			String type = request.getParameter("urgent");
			
			
			//verifier le type de rapport
			ArrayList<PharmCommandeSite> consoSites = new ArrayList<PharmCommandeSite>();
			if(type==null){
					consoSites = (ArrayList<PharmCommandeSite>) Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteByPeriod(formulaireSaisiesPPS.getPharmSite(),formulaireSaisiesPPS.getPharmProgramme(), formulaireSaisiesPPS.getComSitePeriodDate(),"NUR");
					formulaireSaisiesPPS.setComSitePeriodLib("");
					//System.out.println(" ------------------- consoSites ----------------------- : " + consoSites.size() );
			}else { //rapport urgent
					consoSites = (ArrayList<PharmCommandeSite>) Context
						.getService(CommandeSiteService.class)
						.getPharmCommandeSiteByPeriod(formulaireSaisiesPPS.getPharmSite(),
								formulaireSaisiesPPS.getPharmProgramme(), formulaireSaisiesPPS.getComSitePeriodDate(),"UR");
					formulaireSaisiesPPS.setComSitePeriodLib("urgent");
			}
			
			if (!result.hasErrors()) {
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				if (consoSites.isEmpty()) {
					model.addAttribute("var", "1");
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					model.addAttribute("moisDe", formatter.format(formulaireSaisiesPPS.getComSitePeriodDate()));
					model.addAttribute("mess", "existe");
					this.initialisation(model);
				}
			} else {
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				// model.addAttribute("produits",
				// formulaireSaisiesPPS.getPharmProgramme().getPharmProduits());
				model.addAttribute("var", "0");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(@ModelAttribute("formulaireSaisiesPPS") FormulaireSaisiesPPS formulaireSaisiesPPS,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			saisiePPSValidatorAjout.validate(formulaireSaisiesPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {

				LigneCommandeSite ligneCommandeSite = new LigneCommandeSite();
				ligneCommandeSite.setCommandeSite(formulaireSaisiesPPS.getCommandeSite());
				ligneCommandeSite.setProduit(formulaireSaisiesPPS.getProduit());
				ligneCommandeSite.setStockIni(formulaireSaisiesPPS.getLgnComQteIni());
				ligneCommandeSite.setQteRecu(formulaireSaisiesPPS.getLgnComQteRecu());
				ligneCommandeSite.setQteUtil(formulaireSaisiesPPS.getLgnComQteUtil());
				ligneCommandeSite.setQtePerte(formulaireSaisiesPPS.getLgnComPertes());
				ligneCommandeSite.setStockDispo(formulaireSaisiesPPS.getLgnComStockDispo());
				ligneCommandeSite.setNbrJourRuptur(formulaireSaisiesPPS.getLgnComNbreJrsRup());
				ligneCommandeSite.setQteDistri1(formulaireSaisiesPPS.getLgnQteDistriM1());
				ligneCommandeSite.setQteDistri2(formulaireSaisiesPPS.getLgnQteDistriM2());

				formulaireSaisiesPPS.getTabSaisiesPPS().addLigneCommandeSite(ligneCommandeSite);

				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
				model.addAttribute("mess", "valid");

				formulaireSaisiesPPS.setProduit(null);
				formulaireSaisiesPPS.setLgnComQteIni(null);
				formulaireSaisiesPPS.setLgnComQteRecu(null);
				formulaireSaisiesPPS.setLgnComQteUtil(null);
				formulaireSaisiesPPS.setLgnComPertes(null);
				formulaireSaisiesPPS.setLgnComStockDispo(null);
				formulaireSaisiesPPS.setLgnComNbreJrsRup(null);
				formulaireSaisiesPPS.setLgnQteDistriM1(null);
				formulaireSaisiesPPS.setLgnQteDistriM2(null);

				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			} else {
				formulaireSaisiesPPS.setProduit(null);
				formulaireSaisiesPPS.setLgnComQteIni(null);
				formulaireSaisiesPPS.setLgnComQteRecu(null);
				formulaireSaisiesPPS.setLgnComQteUtil(null);
				formulaireSaisiesPPS.setLgnComPertes(null);
				formulaireSaisiesPPS.setLgnComStockDispo(null);
				formulaireSaisiesPPS.setLgnComNbreJrsRup(null);
				formulaireSaisiesPPS.setLgnQteDistriM1(null);
				formulaireSaisiesPPS.setLgnQteDistriM2(null);

				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");

				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneOperation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireSaisiesPPS") FormulaireSaisiesPPS formulaireSaisiesPPS, BindingResult result,
			HttpSession session, ModelMap model) {

		try {
			saisiePPSValidator.validate(formulaireSaisiesPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {
				formulaireSaisiesPPS.getTabSaisiesPPS().removeLigneCommandeSiteById(paramId);
				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			} else {
				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
	public void modifLigneOperation(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireSaisiesPPS") FormulaireSaisiesPPS formulaireSaisiesPPS, BindingResult result,
			HttpSession session, ModelMap model) {

		try {
			saisiePPSValidator.validate(formulaireSaisiesPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {
				LigneCommandeSite  ligne=formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSiteById(modifId);
				formulaireSaisiesPPS.setLgnComNbreJrsRup(ligne.getNbrJourRuptur());
				formulaireSaisiesPPS.setLgnComPertes(ligne.getQtePerte());
				formulaireSaisiesPPS.setLgnComQteIni(ligne.getStockIni());
				formulaireSaisiesPPS.setLgnComQteRecu(ligne.getQteRecu());
				formulaireSaisiesPPS.setLgnComQteUtil(ligne.getQteUtil());
				formulaireSaisiesPPS.setLgnComStockDispo(ligne.getStockDispo());
				formulaireSaisiesPPS.setLgnQteDistriM1(ligne.getQteDistri1());
				formulaireSaisiesPPS.setLgnQteDistriM2(ligne.getQteDistri2());
				formulaireSaisiesPPS.setProduit(ligne.getProduit());
				
				formulaireSaisiesPPS.getTabSaisiesPPS().removeLigneCommandeSiteById(modifId);
				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			} else {
				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(@ModelAttribute("formulaireSaisiesPPS") FormulaireSaisiesPPS formulaireSaisiesPPS,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			saisiePPSValidator.validate(formulaireSaisiesPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			// save operation
			if (!result.hasErrors()) {
				PharmCommandeSite commandeSite = new PharmCommandeSite();
				commandeSite = formulaireSaisiesPPS.getCommandeSite();
				commandeSite.setComSiteDateSaisi(new Date());
				commandeSite.setComSiteFlag(0);
				Context.getService(CommandeSiteService.class).savePharmCommandeSite(commandeSite);

				Map lignes = formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSites();
				for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
					Object key = i.next();
					LigneCommandeSite ligne = (LigneCommandeSite) lignes.get(key);

					PharmLigneCommandeSite lgnCommande = new PharmLigneCommandeSite();
					PharmLigneCommandeSiteId lgnCommandeId = new PharmLigneCommandeSiteId();

					lgnCommandeId.setComSiteId(commandeSite.getComSiteId());
					lgnCommandeId.setProdId(ligne.getProduit().getProdId());

					lgnCommande.setId(lgnCommandeId);
					lgnCommande.setLgnComNbreJrsRup(ligne.getNbrJourRuptur());
					lgnCommande.setLgnComPertes(ligne.getQtePerte());
					lgnCommande.setLgnComQteIni(ligne.getStockIni());
					lgnCommande.setLgnComQteRecu(ligne.getQteRecu());
					lgnCommande.setLgnComQteUtil(ligne.getQteUtil());
					lgnCommande.setLgnComStockDispo(ligne.getStockDispo());
					lgnCommande.setLgnQteDistriM1(ligne.getQteDistri1());
					lgnCommande.setLgnQteDistriM2(ligne.getQteDistri2());
					lgnCommande.setPharmCommandeSite(ligne.getCommandeSite());
					lgnCommande.setPharmProduit(ligne.getProduit());
					Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(lgnCommande);

				}
				model.addAttribute("mess", "success");
				this.initialisation(model);
			} else {

				model.addAttribute("ligneCommandeSites",
						formulaireSaisiesPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireSaisiesPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}

		} catch (Exception e) {
			// System.err.print("Erreur : " + e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.GET, params = { "pdf" })
	public ModelAndView pdfMethod() {
		// create some sample data
		List<Book> listBooks = new ArrayList<Book>();
		listBooks.add(new Book("Spring in Action", "Craig Walls", "1935182358", "June 29th 2011", 31.98F));
		listBooks.add(
				new Book("Spring in Practice", "Willie Wheeler, Joshua White", "1935182056", "May 16th 2013", 31.95F));
		listBooks.add(new Book("Pro Spring 3", "Clarence Ho, Rob Harrop", "1430241071", "April 18th 2012", 31.85F));
		listBooks.add(
				new Book("Spring Integration in Action", "Mark Fisher", "1935182439", "September 26th 2012", 28.73F));

		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("pdfView", "listBooks", listBooks);

	}

	@SuppressWarnings("unchecked")
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}

	public void initialisation(ModelMap model) {
		FormulaireSaisiesPPS formulaireSaisiesPPS = new FormulaireSaisiesPPS();
		model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
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
