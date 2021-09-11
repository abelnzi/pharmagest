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
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.api.MedecinService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireMedecin;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
import org.openmrs.module.pharmagest.validator.MedecinValidator;
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
public class MedecinController {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	MedecinValidator medecinValidator;

	@RequestMapping(value = "/module/pharmagest/medecin.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addMedecin(@ModelAttribute("formulaireMedecin") FormulaireMedecin formulaireMedecin,
			BindingResult result, HttpSession session, ModelMap model) {
		medecinValidator.validate(formulaireMedecin, result);
		Context.getService(MedecinService.class).savePharmMedecin(formulaireMedecin.getPharmMedecin());
		return "redirect:/module/pharmagest/medecin.form";
	}

	@RequestMapping(value = "/module/pharmagest/medecin.form", method = RequestMethod.POST, params = {
			"btn_modifier" })
	public String updateMedecin(
			@ModelAttribute("formulaireMedecin") FormulaireMedecin formulaireMedecin, BindingResult result,
			@RequestParam(required = true, value = "identifiant") String identifiant, HttpSession session,
			ModelMap model) {
		medecinValidator.validate(formulaireMedecin, result);
		PharmMedecin medecin = Context.getService(MedecinService.class)
				.getPharmMedecinById(Integer.parseInt(identifiant));
		medecin.setMedAdresse(formulaireMedecin.getMedAdresse());
		medecin.setMedNom(formulaireMedecin.getMedNom());
		medecin.setMedPrenom(formulaireMedecin.getMedPrenom());
		medecin.setMedTel(formulaireMedecin.getMedTel());
		medecin.setMedCode(formulaireMedecin.getMedCode());
		
		Context.getService(MedecinService.class)
				.updatePharmMedecin(medecin);
		return "redirect:/module/pharmagest/medecin.form";
	}

	@RequestMapping(value = "/module/pharmagest/medecin.form", method = RequestMethod.GET)
	public String initMedecin(ModelMap model) {

		FormulaireMedecin formulaireMedecin = new FormulaireMedecin();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(MedecinService.class)
				.getAllPharmMedecins();

		model.addAttribute("medecins", medecins);
		model.addAttribute("formulaireMedecin", formulaireMedecin);

		return "/module/pharmagest/medecin";
	}

	@RequestMapping(value = "/module/pharmagest/medecin.form", method = RequestMethod.GET, params = { "editId" })
	public void editMedecin(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireMedecin") FormulaireMedecin formulaireMedecin, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmMedecin medecin = Context.getService(MedecinService.class)
				.getPharmMedecinById(Integer.parseInt(editId));
		formulaireMedecin.setPharmMedecin(medecin);
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(MedecinService.class)
				.getAllPharmMedecins();

		model.addAttribute("medecins", medecins);
		model.addAttribute("formulaireMedecin", formulaireMedecin);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);

	}

	@RequestMapping(value = "/module/pharmagest/medecin.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteMedecin(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireMedecin") FormulaireMedecin formulaireMedecin, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmMedecin medecin = Context.getService(MedecinService.class)
				.getPharmMedecinById(Integer.parseInt(deleteId));
		Context.getService(MedecinService.class).deletePharmMedecin(medecin);
		return "redirect:/module/pharmagest/medecin.form";
	}

}
