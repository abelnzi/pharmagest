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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.RapportCommandeService;
import org.openmrs.module.pharmagest.metier.ContaintStock;
import org.openmrs.module.pharmagest.metier.GestionAlert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller
public class pharmagestManageController {

	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/module/pharmagest/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		GestionAlert gestAlert= new GestionAlert();
		model.addAttribute("user", Context.getAuthenticatedUser());

		model.addAttribute("listStockMin", gestAlert.getStockMin());
		model.addAttribute("listStockMax", gestAlert.getStockMax());
		model.addAttribute("listProdPerim", gestAlert.getProduitsPerimes());
		model.addAttribute("listProdAlert", gestAlert.getProduitsAlerte());
		
		//gestion des périmés
		List<ContaintStock> listStockPerim= gestAlert.getProduitsPerimes();
		Iterator it = listStockPerim.iterator();
		while (it.hasNext()) {
			ContaintStock containtStock = (ContaintStock) it.next();
			PharmProduitAttribut produitAttribut=containtStock.getPharmProduitAttribut();
			produitAttribut.setFlagValid(false);
			Context.getService(OperationService.class).savePharmProduitAttribut(produitAttribut);				
		}

	}

	@RequestMapping(value = "/module/pharmagest/rapportStock", method = RequestMethod.GET)
	public void achat(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/parametrage", method = RequestMethod.GET)
	public void parametrage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/dispensationChoix", method = RequestMethod.GET)
	public void dispensationChoix(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	
	@RequestMapping(value = "/module/pharmagest/interoperabiliteMenu", method = RequestMethod.GET)
	public void interoperabiliteMenu(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/mouvementStock", method = RequestMethod.GET)
	public void mouvementStock(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/inventaireMenu", method = RequestMethod.GET)
	public void inventaireMenu(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/rapportMenu", method = RequestMethod.GET)
	public void rapportMenu(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/distributionMenu", method = RequestMethod.GET)
	public void distributionMenu(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	@RequestMapping(value = "/module/pharmagest/receptionMenu", method = RequestMethod.GET)
	public void receptionMenu(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(value = "/module/pharmagest/test", method = RequestMethod.GET)
	public void test(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

}
