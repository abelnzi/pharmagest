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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.RegimeService;
import org.openmrs.module.pharmagest.metier.FormulaireRegime;
import org.openmrs.module.pharmagest.validator.FournisseurValidator;
import org.openmrs.module.pharmagest.validator.RegimeValidator;
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

/**
 * The main controller.
 */
@Controller
public class RegimeController {
	
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	RegimeValidator regimeValidator;

	@RequestMapping(value = "/module/pharmagest/regime.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addRegime(@ModelAttribute("formulaireRegime") FormulaireRegime formulaireRegime, BindingResult result,
			HttpSession session, ModelMap model) {
		regimeValidator.validate(formulaireRegime, result);
		Context.getService(RegimeService.class).savePharmRegime(formulaireRegime.getPharmRegime());
		return "redirect:/module/pharmagest/regime.form";
	}

	@RequestMapping(value = "/module/pharmagest/regime.form", method = RequestMethod.POST, params = { "btn_modifier" })
	public String updateRegime(@ModelAttribute("formulaireRegime") FormulaireRegime formulaireRegime,
			BindingResult result, @RequestParam(required = true, value = "identifiant") String identifiant,
			HttpSession session, ModelMap model) {
		regimeValidator.validate(formulaireRegime, result);
		PharmRegime regime = Context.getService(RegimeService.class).getPharmRegimeById(Integer.parseInt(identifiant));
		regime.setRegimLib(formulaireRegime.getRegimLib());
		regime.setPharmProgramme(formulaireRegime.getPharmProgramme());

		Context.getService(RegimeService.class).updatePharmRegime(regime);
		return "redirect:/module/pharmagest/regime.form";
	}

	@RequestMapping(value = "/module/pharmagest/regime.form", method = RequestMethod.GET)
	public String initRegime(ModelMap model) {

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		FormulaireRegime formulaireRegime = new FormulaireRegime();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(RegimeService.class).getAllPharmRegimes();

		model.addAttribute("regimes", regimes);
		model.addAttribute("formulaireRegime", formulaireRegime);
		model.addAttribute("programmes", programmes);

		return "/module/pharmagest/regime";
	}

	@RequestMapping(value = "/module/pharmagest/regime.form", method = RequestMethod.GET, params = { "editId" })
	public void editRegime(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireRegime") FormulaireRegime formulaireRegime, BindingResult result,
			HttpSession session, ModelMap model) {
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		PharmRegime regime = Context.getService(RegimeService.class).getPharmRegimeById(Integer.parseInt(editId));
		formulaireRegime.setPharmRegime(regime);
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(RegimeService.class).getAllPharmRegimes();

		model.addAttribute("regimes", regimes);
		model.addAttribute("formulaireRegime", formulaireRegime);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);
		model.addAttribute("programmes", programmes);

	}

	@RequestMapping(value = "/module/pharmagest/regime.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteRegime(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireRegime") FormulaireRegime formulaireRegime, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmRegime regime = Context.getService(RegimeService.class).getPharmRegimeById(Integer.parseInt(deleteId));
		Context.getService(RegimeService.class).deletePharmRegime(regime);
		return "redirect:/module/pharmagest/regime.form";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

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
