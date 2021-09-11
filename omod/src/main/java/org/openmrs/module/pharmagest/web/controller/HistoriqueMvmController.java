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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.metier.FormulairePeriode;
import org.openmrs.module.pharmagest.metier.HistoMvmResponseJson;
import org.openmrs.module.pharmagest.metier.HistoMvmType;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * The main controller.
 */
@Controller

public class HistoriqueMvmController {

	@RequestMapping(value = "/module/pharmagest/histoMvm.form", method = RequestMethod.GET)
	public String initHistoMvm(ModelMap model) {
		
		FormulairePeriode formulairePeriode= new FormulairePeriode();
		model.addAttribute("formulairePeriode", formulairePeriode);
		

		return "/module/pharmagest/histoMvm";
	}
	
	@RequestMapping(value = "/module/pharmagest/histoMvm.form", method = RequestMethod.POST, params = {
		"btn_rechercher" })
	public void stockProduitPost(@ModelAttribute("formulairePeriode") FormulairePeriode formulairePeriode,
		BindingResult result, HttpSession session, ModelMap model) {
		
		if (!result.hasErrors()) {
		
			if (formulairePeriode.getDateDebut()!=null && formulairePeriode.getDateFin()!=null){
				
			List<HistoMvmType> list = new ArrayList<HistoMvmType>();
			@SuppressWarnings("rawtypes")
			Collection histoMvmlist = Context.getService(GestionStockService.class).getPharmHistoMvmByPeriod(formulairePeriode.getDateDebut(), formulairePeriode.getDateFin());
			@SuppressWarnings("rawtypes")
			Iterator it = histoMvmlist.iterator();
			while (it.hasNext()) // tant que j'ai un element non parcouru
			{
				HistoMvmType histoType = new HistoMvmType();
				PharmHistoMouvementStock histo = (PharmHistoMouvementStock) it.next();
				histoType.setHistoMouvementStock(histo);
				histoType.setTypeOperation(
						Context.getService(ParametresService.class).getTypeOperationById(histo.getMvtTypeMvt()));
				histoType.setProgramme(Context.getService(ProgrammeService.class).getPharmProgrammeById(histo.getMvtProgramme()));
				list.add(histoType);
	
			}
			Collections.sort(list, Collections.reverseOrder());
			model.addAttribute("list", list);
			model.addAttribute("var", 1);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
			model.addAttribute("periode", periode);
			
				}
		
		}
	}

	
/*	@RequestMapping(value = "/module/pharmagest/histoMvm.form",method = RequestMethod.POST, params = { "btn_enregistrer" })
    public ModelAndView generatePdfReport(ModelAndView modelAndView){


        Map<String,Object> parameterMap = new HashMap<String,Object>();

        Collection histoMvmlist = Context.getService(GestionStockService.class).getAllPharmHistoMvmStocks();

        JRDataSource JRdataSource = new JRBeanCollectionDataSource(histoMvmlist);

        parameterMap.put("datasource", JRdataSource);

        //pdfReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("pdfReport", parameterMap);

        return modelAndView;

    }//generatePdfReport
*/
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
	
	private String decrypteAsccii(String s){
		StringTokenizer st = new StringTokenizer(s,"h");
		System.out.println("-------Nombre de mots------------:" + st.countTokens());
		
		
		int n=0;
		while (st.hasMoreTokens()) {
			n=n+1;
			System.out.println(st.nextToken());
			System.out.println("n::" + n);
			/*try {
				int codeASCII = Integer.parseInt(st.nextToken());
				char lettre = (char) codeASCII; 
				System.out.println("lettre::"+lettre);				
			} catch (Exception e) {
				System.out.println("-------erreur de convertion de caractère------------:" + n);
			}*/
			
		}
		return null;
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
