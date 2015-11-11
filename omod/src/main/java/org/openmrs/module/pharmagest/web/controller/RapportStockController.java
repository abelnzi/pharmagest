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
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller

public class RapportStockController {

	@RequestMapping(value = "/module/pharmagest/rapportStockTotal.form", method = RequestMethod.GET)
	public String stockTotal(ModelMap model) {

		
		Collection<PharmStocker> listStocks = Context.getService(GestionStockService.class).getAllPharmStockers();
		model.addAttribute("listStocks", listStocks);
		return "/module/pharmagest/rapportStockTotal";
	}

	@RequestMapping(value = "/module/pharmagest/rapportStockProduit.form", method = RequestMethod.GET)
	public String stockProduitGet(ModelMap model) {
		FormulaireProduit formulaireProduit=new FormulaireProduit();
		List<PharmProduit> pharmProduits = (List<PharmProduit>) Context.getService(ParametresService.class)
				.getAllProduits();

		model.addAttribute("formulaireProduit", formulaireProduit);
		model.addAttribute("produits", pharmProduits);

		return "/module/pharmagest/rapportStockProduit";
	}

	@RequestMapping(value = "/module/pharmagest/rapportStockProduit.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void stockProduitPost(@ModelAttribute("formulaireProduit") FormulaireProduit formulaireProduit,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			List<PharmProduit> pharmProduits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			model.addAttribute("formulaireProduit", formulaireProduit);
			model.addAttribute("produits", pharmProduits);

			if (!result.hasErrors()) {
				@SuppressWarnings("rawtypes")
				Collection listStocks = Context.getService(GestionStockService.class)
						.getPharmStockersByProduit(formulaireProduit.getPharmProduit());
				model.addAttribute("listStocks", listStocks);
			}

		} catch (Exception e) {
			e.getMessage();
		}

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
