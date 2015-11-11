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
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.ClassePharmaService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
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
public class ProduitController {
	protected final Log log = LogFactory.getLog(getClass());

	//@Autowired
	//ProduitValidator produitValidator;

	@RequestMapping(value = "/module/pharmagest/produit.form", method = RequestMethod.POST, params = {
			"btn_enregistrer" })
	public String addProduit(@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit,
			BindingResult result, HttpSession session, ModelMap model) {
		//produitValidator.validate(formulaireProduit, result);
		Context.getService(ProduitService.class).savePharmProduit(formulaireProduit.getPharmProduit());
		return "redirect:/module/pharmagest/produit.form";
	}

	@RequestMapping(value = "/module/pharmagest/produit.form", method = RequestMethod.POST, params = {
			"btn_modifier" })
	public String updateProduit(
			@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit, BindingResult result,
			@RequestParam(required = true, value = "identifiant") String identifiant, HttpSession session,
			ModelMap model) {
		//produitValidator.validate(formulaireProduit, result);
		PharmProduit produit = Context.getService(ProduitService.class)
				.getPharmProduitById(Integer.parseInt(identifiant));
		produit.setProdLib(formulaireProduit.getProdLib());
		
		Context.getService(ProduitService.class)
				.updatePharmProduit(produit);
		return "redirect:/module/pharmagest/produit.form";
	}

	@RequestMapping(value = "/module/pharmagest/produit.form", method = RequestMethod.GET)
	public String initProduit(ModelMap model) {
		List<PharmClassePharma> classePharma = (List<PharmClassePharma>) Context.getService(ClassePharmaService.class)
				.getAllPharmClassePharmas();
		FormulaireProduit formulaireProduit = new FormulaireProduit();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ProduitService.class)
				.getAllPharmProduits();

		model.addAttribute("classePharma", classePharma);
		model.addAttribute("produits", produits);
		model.addAttribute("formulaireProduit", formulaireProduit);

		return "/module/pharmagest/produit";
	}

	@RequestMapping(value = "/module/pharmagest/produit.form", method = RequestMethod.GET, params = { "editId" })
	public void editProduit(@RequestParam(value = "editId") String editId,
			@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit, BindingResult result,
			HttpSession session, ModelMap model) {
		List<PharmClassePharma> classePharma = (List<PharmClassePharma>) Context.getService(ClassePharmaService.class)
				.getAllPharmClassePharmas();
		PharmProduit produit = Context.getService(ProduitService.class)
				.getPharmProduitById(Integer.parseInt(editId));
		formulaireProduit.setPharmProduit(produit);
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ProduitService.class)
				.getAllPharmProduits();
		
		model.addAttribute("classePharma", classePharma);
		model.addAttribute("produits", produits);
		model.addAttribute("formulaireProduit", formulaireProduit);
		model.addAttribute("var", "2");
		model.addAttribute("identifiant", editId);

	}

	@RequestMapping(value = "/module/pharmagest/produit.form", method = RequestMethod.GET, params = { "deleteId" })
	public String deleteProduit(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit, BindingResult result,
			HttpSession session, ModelMap model) {
		PharmProduit produit = Context.getService(ProduitService.class)
				.getPharmProduitById(Integer.parseInt(deleteId));
		Context.getService(ProduitService.class).deletePharmProduit(produit);
		return "redirect:/module/pharmagest/produit.form";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		binder.registerCustomEditor(PharmClassePharma.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmClassePharma classePharma = Context.getService(ClassePharmaService.class)
						.getPharmClassePharmaById(Integer.parseInt(text));
				this.setValue(classePharma);
			}
		});

	}

}
