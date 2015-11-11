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
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.metier.FormulaireProgramme;
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
public class ProgrammeController {
	protected final Log log = LogFactory.getLog(getClass());

	/*@Autowired
	ProgrammeValidator programmeValidator;*/

	@RequestMapping(value = "/module/pharmagest/programme.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addProgramme(@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme,
			BindingResult result, HttpSession session, ModelMap model) {
		//programmeValidator.validate(formulaireProgramme, result);
		Context.getService(ProgrammeService.class).savePharmProgramme(formulaireProgramme.getPharmProgramme());
		return "redirect:/module/pharmagest/programme.form";
	}

	@RequestMapping(value = "/module/pharmagest/programme.form", method = RequestMethod.POST, params = {
			"btn_modifier" })
	public String updateProgramme(
			@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme, BindingResult result,
			@RequestParam(required = true, value = "identifiant") String identifiant, HttpSession session,
			ModelMap model) {
		//programmeValidator.validate(formulaireProgramme, result);
		PharmProgramme programme = Context.getService(ProgrammeService.class)
				.getPharmProgrammeById(Integer.parseInt(identifiant));
		programme.setProgramLib(formulaireProgramme.getProgramLib());
		
		
		Context.getService(ProgrammeService.class)
				.updatePharmProgramme(programme);
		return "redirect:/module/pharmagest/programme.form";
	}

	@RequestMapping(value = "/module/pharmagest/programme.form", method = RequestMethod.GET)
	public String initProgramme(ModelMap model) {

		FormulaireProgramme formulaireProgramme = new FormulaireProgramme();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ProgrammeService.class)
				.getAllPharmProgrammes();

		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireProgramme", formulaireProgramme);

		return "/module/pharmagest/programme";
	}

	@RequestMapping(value = "/module/pharmagest/programme.form", method = RequestMethod.GET, params = { "editId" })
	public void editProgramme(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmProgramme programme = Context.getService(ProgrammeService.class)
				.getPharmProgrammeById(Integer.parseInt(editId));
		formulaireProgramme.setPharmProgramme(programme);
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ProgrammeService.class)
				.getAllPharmProgrammes();

		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireProgramme", formulaireProgramme);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);

	}

	@RequestMapping(value = "/module/pharmagest/programme.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteProgramme(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmProgramme programme = Context.getService(ProgrammeService.class)
				.getPharmProgrammeById(Integer.parseInt(deleteId));
		Context.getService(ProgrammeService.class).deletePharmProgramme(programme);
		return "redirect:/module/pharmagest/programme.form";
	}

}
