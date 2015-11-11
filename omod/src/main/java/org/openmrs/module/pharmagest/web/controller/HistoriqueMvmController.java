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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.HistoMvmResponseJson;
import org.openmrs.module.pharmagest.metier.HistoMvmType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller

public class HistoriqueMvmController {

	@RequestMapping(value = "/module/pharmagest/histoMvm.form", method = RequestMethod.GET)
	public String initHistoMvm(ModelMap model) {
		List<HistoMvmType> list = new ArrayList<HistoMvmType>();
		@SuppressWarnings("rawtypes")
		Collection histoMvmlist = Context.getService(GestionStockService.class).getAllPharmHistoMvmStocks();
		@SuppressWarnings("rawtypes")
		Iterator it = histoMvmlist.iterator();
		while (it.hasNext()) // tant que j'ai un element non parcouru
		{
			HistoMvmType histoType = new HistoMvmType();
			PharmHistoMouvementStock histo = (PharmHistoMouvementStock) it.next();
			histoType.setHistoMouvementStock(histo);
			histoType.setTypeOperation(
					Context.getService(ParametresService.class).getTypeOperationById(histo.getMvtTypeMvt()));
			list.add(histoType);

		}
		Collections.sort(list, Collections.reverseOrder());
		model.addAttribute("list", list);
		return "/module/pharmagest/histoMvm";
	}

	private HistoMvmResponseJson prepareJsonResponse(List<PharmHistoMouvementStock> histoMouvements, int count,
			MultiValueMap<String, String> parametresAjax) {
		HistoMvmResponseJson reponseJson = new HistoMvmResponseJson();
		reponseJson.setsEcho(getIntFirstValue(parametresAjax, "sEcho"));
		reponseJson.setiTotalDisplayRecords(count);
		reponseJson.setAaData(histoMouvements);
		return reponseJson;
	}

	private Integer getIntFirstValue(MultiValueMap<String, String> parametres, String key) {
		Integer res = null;
		String s = parametres.getFirst(key);
		if (StringUtils.isNotEmpty(s)) {
			res = Integer.parseInt(s);
		}
		return res;
	}

}
