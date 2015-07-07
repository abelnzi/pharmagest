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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.RegimeTest;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
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
@RequestMapping(value = "/module/pharmagest/dispensation.form")
public class  DispensationController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(method=RequestMethod.POST, params = { "btn_recherche" })
	public void rechercher(
            @RequestParam(required=true, value="numPatient") Integer numPatient,
            HttpSession session, ModelMap model) {
		
		PatientIdentifier patientIdentifier = Context.getPatientService().getPatientIdentifier(numPatient);
		patientIdentifier.getPatient().getPatientIdentifier().getIdentifier();
		model.addAttribute("patientIdentifier", patientIdentifier.getPatient());		
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String initDispenser(ModelMap model) {
		FormulaireOrdonnance formulaireOrdonnance = new FormulaireOrdonnance();
		List<RegimeTest> regimes=(List<RegimeTest>) Context.getService(ParametersDispensationService.class).getAllRegimes();
        model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
        model.addAttribute("regimes", regimes);
        return "/module/pharmagest/dispensation";
	}
	
	 @RequestMapping( method = RequestMethod.POST, params={"btn_recherche"})
	 public String addDispensation(@ModelAttribute("formulaireOrdonnance")
	                            FormulaireOrdonnance formulaireOrdonnance, BindingResult result) {
	       	         
	        return "redirect:contacts.html";
	    }
	
}
