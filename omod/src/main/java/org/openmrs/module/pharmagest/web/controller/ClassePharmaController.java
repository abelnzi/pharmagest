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
import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.api.ClassePharmaService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireClassePharma;
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
public class ClassePharmaController {
	protected final Log log = LogFactory.getLog(getClass());

	/*@Autowired
	ClassePharmaValidator classePharmaValidator;*/

	@RequestMapping(value = "/module/pharmagest/classePharma.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addClassePharma(@ModelAttribute("formulaireClassePharma") FormulaireClassePharma formulaireClassePharma,
			BindingResult result, HttpSession session, ModelMap model) {
		//classePharmaValidator.validate(formulaireClassePharma, result);
		Context.getService(ClassePharmaService.class).savePharmClassePharma(formulaireClassePharma.getPharmClassePharma());
		return "redirect:/module/pharmagest/classePharma.form";
	}

	@RequestMapping(value = "/module/pharmagest/classePharma.form", method = RequestMethod.POST, params = {
			"btn_modifier" })
	public String updateClassePharma(
			@ModelAttribute("formulaireClassePharma") FormulaireClassePharma formulaireClassePharma, BindingResult result,
			@RequestParam(required = true, value = "identifiant") String identifiant, HttpSession session,
			ModelMap model) {
		//classePharmaValidator.validate(formulaireClassePharma, result);
		PharmClassePharma classePharma = Context.getService(ClassePharmaService.class)
				.getPharmClassePharmaById(Integer.parseInt(identifiant));
		classePharma.setClassPharmNom(formulaireClassePharma.getClassPharmNom());
		
		
		Context.getService(ClassePharmaService.class)
				.updatePharmClassePharma(classePharma);
		return "redirect:/module/pharmagest/classePharma.form";
	}

	@RequestMapping(value = "/module/pharmagest/classePharma.form", method = RequestMethod.GET)
	public String initClassePharma(ModelMap model) {

		FormulaireClassePharma formulaireClassePharma = new FormulaireClassePharma();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmClassePharma> classePharmas = (List<PharmClassePharma>) Context.getService(ClassePharmaService.class)
				.getAllPharmClassePharmas();

		model.addAttribute("classePharmas", classePharmas);
		model.addAttribute("formulaireClassePharma", formulaireClassePharma);

		return "/module/pharmagest/classePharma";
	}

	@RequestMapping(value = "/module/pharmagest/classePharma.form", method = RequestMethod.GET, params = { "editId" })
	public void editClassePharma(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireClassePharma") FormulaireClassePharma formulaireClassePharma, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmClassePharma classePharma = Context.getService(ClassePharmaService.class)
				.getPharmClassePharmaById(Integer.parseInt(editId));
		formulaireClassePharma.setPharmClassePharma(classePharma);
		List<PharmClassePharma> classePharmas = (List<PharmClassePharma>) Context.getService(ClassePharmaService.class)
				.getAllPharmClassePharmas();

		model.addAttribute("classePharmas", classePharmas);
		model.addAttribute("formulaireClassePharma", formulaireClassePharma);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);

	}

	@RequestMapping(value = "/module/pharmagest/classePharma.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteClassePharma(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireClassePharma") FormulaireClassePharma formulaireClassePharma, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmClassePharma classePharma = Context.getService(ClassePharmaService.class)
				.getPharmClassePharmaById(Integer.parseInt(deleteId));
		Context.getService(ClassePharmaService.class).deletePharmClassePharma(classePharma);
		return "redirect:/module/pharmagest/classePharma.form";
	}

}
