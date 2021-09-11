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

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */
@Controller
public class SiteController {
	protected final Log log = LogFactory.getLog(getClass());


	@RequestMapping(value = "/module/pharmagest/site.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addSite(@ModelAttribute("formulaireSite") FormulaireSite formulaireSite,
			BindingResult result, HttpSession session, ModelMap model) {
		
		Context.getService(SiteService.class).savePharmSite(formulaireSite.getPharmSite());
		return "redirect:/module/pharmagest/site.form";
	}

	@RequestMapping(value = "/module/pharmagest/site.form", method = RequestMethod.POST, params = {
			"btn_modifier" })
	public String updateSite(
			@ModelAttribute("formulaireSite") FormulaireSite formulaireSite, BindingResult result,
			@RequestParam(required = true, value = "identifiant") String identifiant, HttpSession session,
			ModelMap model) {
		//siteValidator.validate(formulaireSite, result);
		PharmSite site = Context.getService(SiteService.class)
				.getPharmSiteById(Integer.parseInt(identifiant));
		site.setSitLib(formulaireSite.getSitLib());
		site.setSitCode(formulaireSite.getSitCode());
		
		
		Context.getService(SiteService.class)
				.updatePharmSite(site);
		return "redirect:/module/pharmagest/site.form";
	}

	@RequestMapping(value = "/module/pharmagest/site.form", method = RequestMethod.GET)
	public String initSite(ModelMap model) {

		FormulaireSite formulaireSite = new FormulaireSite();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class)
				.getAllPharmSites();

		model.addAttribute("sites", sites);
		model.addAttribute("formulaireSite", formulaireSite);

		return "/module/pharmagest/site";
	}

	@RequestMapping(value = "/module/pharmagest/site.form", method = RequestMethod.GET, params = { "editId" })
	public void editSite(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireSite") FormulaireSite formulaireSite, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmSite site = Context.getService(SiteService.class)
				.getPharmSiteById(Integer.parseInt(editId));
		formulaireSite.setPharmSite(site);
		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class)
				.getAllPharmSites();

		model.addAttribute("sites", sites);
		model.addAttribute("formulaireSite", formulaireSite);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);

	}

	@RequestMapping(value = "/module/pharmagest/site.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteSite(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireSite") FormulaireSite formulaireSite, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmSite site = Context.getService(SiteService.class)
				.getPharmSiteById(Integer.parseInt(deleteId));
		Context.getService(SiteService.class).deletePharmSite(site);
		return "redirect:/module/pharmagest/site.form";
	}

}
