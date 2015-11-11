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
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.api.FournisseurService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireFournisseur;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
import org.openmrs.module.pharmagest.validator.FournisseurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
public class FournisseurController {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	FournisseurValidator fournisseurValidator;

	@RequestMapping(value = "/module/pharmagest/fournisseur.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addFournisseur(@ModelAttribute("formulaireFournisseur") FormulaireFournisseur formulaireFournisseur,
			BindingResult result, HttpSession session, ModelMap model) {
		fournisseurValidator.validate(formulaireFournisseur, result);
		Context.getService(FournisseurService.class).savePharmFournisseur(formulaireFournisseur.getPharmFournisseur());
		return "redirect:/module/pharmagest/fournisseur.form";
	}

	@RequestMapping(value = "/module/pharmagest/fournisseur.form", method = RequestMethod.POST, params = {
			"btn_modifier" })
	public String updateFournisseur(
			@ModelAttribute("formulaireFournisseur") FormulaireFournisseur formulaireFournisseur, BindingResult result,
			@RequestParam(required = true, value = "identifiant") String identifiant, HttpSession session,
			ModelMap model) {
		fournisseurValidator.validate(formulaireFournisseur, result);
		PharmFournisseur fournisseur = Context.getService(FournisseurService.class)
				.getPharmFournisseurById(Integer.parseInt(identifiant));
		fournisseur.setFourAdresse(formulaireFournisseur.getFourAdresse());
		fournisseur.setFourDesign1(formulaireFournisseur.getFourDesign1());
		fournisseur.setFourDesign2(formulaireFournisseur.getFourDesign2());
		fournisseur.setFourTel1(formulaireFournisseur.getFourTel1());
		fournisseur.setFourTel2(formulaireFournisseur.getFourTel2());
		
		Context.getService(FournisseurService.class)
				.updatePharmFournisseur(fournisseur);
		return "redirect:/module/pharmagest/fournisseur.form";
	}

	@RequestMapping(value = "/module/pharmagest/fournisseur.form", method = RequestMethod.GET)
	public String initFournisseur(ModelMap model) {

		FormulaireFournisseur formulaireFournisseur = new FormulaireFournisseur();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(FournisseurService.class)
				.getAllPharmFournisseurs();

		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("formulaireFournisseur", formulaireFournisseur);

		return "/module/pharmagest/fournisseur";
	}

	@RequestMapping(value = "/module/pharmagest/fournisseur.form", method = RequestMethod.GET, params = { "editId" })
	public void editFournisseur(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireFournisseur") FormulaireFournisseur formulaireFournisseur, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmFournisseur fournisseur = Context.getService(FournisseurService.class)
				.getPharmFournisseurById(Integer.parseInt(editId));
		formulaireFournisseur.setPharmFournisseur(fournisseur);
		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(FournisseurService.class)
				.getAllPharmFournisseurs();

		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("formulaireFournisseur", formulaireFournisseur);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);

	}

	@RequestMapping(value = "/module/pharmagest/fournisseur.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteFournisseur(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireFournisseur") FormulaireFournisseur formulaireFournisseur, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmFournisseur fournisseur = Context.getService(FournisseurService.class)
				.getPharmFournisseurById(Integer.parseInt(deleteId));
		Context.getService(FournisseurService.class).deletePharmFournisseur(fournisseur);
		return "redirect:/module/pharmagest/fournisseur.form";
	}

}
