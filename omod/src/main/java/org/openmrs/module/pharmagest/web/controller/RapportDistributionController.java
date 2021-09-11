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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulairePeriode;
import org.openmrs.module.pharmagest.metier.LigneCommandeSite;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
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
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * The main controller.
 */
@Controller
//@SessionAttributes("formulaireDistri")
@SessionAttributes("formulairePeriode")
public class RapportDistributionController {
	
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(value = "/module/pharmagest/ruptureESPC.form", method = RequestMethod.GET)
	public String rupturePPSParam(ModelMap model) {
		FormulairePeriode formulairePeriode= new FormulairePeriode();
		model.addAttribute("formulairePeriode", formulairePeriode);
		return "/module/pharmagest/ruptureESPC";
	}
	
		@RequestMapping(value = "/module/pharmagest/ruptureESPC.form", method = RequestMethod.POST, params = {
		"btn_rechercher" })
	public void rupturePPSPost(@ModelAttribute("formulairePeriode") FormulairePeriode formulairePeriode,
		BindingResult result, HttpSession session, ModelMap model) {
		
		if (!result.hasErrors()) {
		
			if (formulairePeriode.getDateDebut()!=null && formulairePeriode.getDateFin()!=null){
				
			
			Collection ligneList = Context.getService(CommandeSiteService.class).getProduitRupture(
										formulairePeriode.getDateDebut(), formulairePeriode.getDateFin());
			//System.out.println("============formulairePeriode.getDateDebut()============"+formulairePeriode.getDateDebut());
			//System.out.println("============ligneList============"+ligneList.size());
			model.addAttribute("list", ligneList);
			model.addAttribute("var", 1);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
			model.addAttribute("periode", periode);
			
				}
		
		}
	}
		
		@RequestMapping(value = "/module/pharmagest/promptitudeESPC.form", method = RequestMethod.GET)
		public String promtitudeParam(ModelMap model) {
			this.initialisation(model);
			return "/module/pharmagest/promptitudeESPC";
		}
		
			@RequestMapping(value = "/module/pharmagest/promptitudeESPC.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
		public void promptitudePost(@ModelAttribute("formulairePeriode") FormulairePeriode formulairePeriode,
			BindingResult result, HttpSession session, ModelMap model) {
			
			if (!result.hasErrors()) {
				
				List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
						.getAllProgrammes();
				model.addAttribute("programmes", programmes);
			
				if (formulairePeriode.getDateDebut()!=null && formulairePeriode.getDateFin()!=null){
					
				
				Collection ligneList = Context.getService(CommandeSiteService.class).getPharmCommandeByPeriod(formulairePeriode.getProgramme(),
											formulairePeriode.getDateDebut(),formulairePeriode.getDateFin(), "VATR_NUR_Prompt");
							
				
				model.addAttribute("list", ligneList);
				model.addAttribute("var", 1);
				SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
				String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
				model.addAttribute("periode", periode);
				model.addAttribute("formulairePeriode", formulairePeriode);
					}
			
			}
		}
			
			
			
			
			@RequestMapping(value = "/module/pharmagest/etatStockESPC.form", method = RequestMethod.GET)
			public String initEtatStock(ModelMap model) {
				this.initialisation(model);
				return "/module/pharmagest/etatStockESPC";
			}
			
				@RequestMapping(value = "/module/pharmagest/etatStockESPC.form", method = RequestMethod.POST, params = {
				"btn_rechercher" })
			public void etatStock(@ModelAttribute("formulairePeriode") FormulairePeriode formulairePeriode,
				BindingResult result, HttpSession session, ModelMap model) {
					
					List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
							.getAllProgrammes();
					model.addAttribute("programmes", programmes);
					model.addAttribute("formulairePeriode", formulairePeriode);
					
				if (!result.hasErrors()) {			
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(formulairePeriode.getDateDebut());
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH) + 1;
					// int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
					int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

					String mois;
					if (month < 10) {
						mois = "0" + month;
					} else {
						mois = month + "";
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date minDate = null;
					Date maxDate = null;
					try {
						minDate = df.parse(year + "-" + mois + "-01");
						maxDate = df.parse(year + "-" + mois + "-" + maxDay);
					} catch (ParseException e) {
						e.printStackTrace();
					}
											
					
					Collection<PharmLigneCommandeSite> ligneList = Context.getService(CommandeSiteService.class).getPharmLigneCommandeByParams(formulairePeriode.getProgramme(),
												minDate,maxDate);		
					//System.out.println("=============ligneList==========="+ligneList.size() );
					model.addAttribute("list", this.correspLigneCmdSiteToMvt(ligneList));
					model.addAttribute("var", 1);
						
				
				} 
				
			}
				
				public Collection<LigneCommandeSite> correspLigneCmdSiteToMvt(Collection<PharmLigneCommandeSite> pharmligneCommandeSites){
					List<LigneCommandeSite> listMvm = new ArrayList<LigneCommandeSite>();

					Iterator it= pharmligneCommandeSites.iterator();
					while (it.hasNext()) {
						PharmLigneCommandeSite ligne= (PharmLigneCommandeSite)it.next();
						LigneCommandeSite ligneMvm = new LigneCommandeSite();
						ligneMvm.setCommandeSite(ligne.getPharmCommandeSite());
						//ligneMvm.setDatePerem(ligne.get);
						ligneMvm.setNbrJourRuptur(ligne.getLgnComNbreJrsRup());
						//ligneMvm.setNumLot(ligne.get);
						ligneMvm.setProduit(ligne.getPharmProduit());
						ligneMvm.setQteDistri1(ligne.getLgnQteDistriM1());
						ligneMvm.setQteDistri2(ligne.getLgnQteDistriM2());
						ligneMvm.setQtePerte(ligne.getLgnComPertes());
						ligneMvm.setQteRecu(ligne.getLgnComQteRecu());
						ligneMvm.setQteUtil(ligne.getLgnComQteUtil());
						ligneMvm.setStockDispo(ligne.getLgnComStockDispo());
						ligneMvm.setStockIni(ligne.getLgnComQteIni());
						
						/***********determiner la cmm et msd*************/
						double som=(double)ligne.getLgnComQteUtil()+ligne.getLgnQteDistriM1()+ligne.getLgnQteDistriM2();			
						ligneMvm.setCmm((int) Math.ceil(som/3d));
						double stockDispo=(double)ligneMvm.getStockDispo();
						double cmmDouble=(ligneMvm.getCmm()!=0) ?(double)ligneMvm.getCmm():0;
						if(ligneMvm.getCmm()!=0 )ligneMvm.setMdu(this.arrondir(  (double) (stockDispo/cmmDouble),1) );
					
						listMvm.add(ligneMvm);
						/*System.out.println("==========SDU======================"+ligneMvm.getStockDispo());
						System.out.println("-----------------------------------------------------------------");
						System.out.println("getCmm :: "+ ligneMvm.getCmm());
						System.out.println("getMdu :: "+ ligneMvm.getMdu());
						System.out.println("stockDispo :: "+ stockDispo);*/
					}
					 return listMvm;
				}
				
				public Double arrondir(Double d,int p){

					return (double) (Math.round(d * Math.pow(10,p))/Math.pow(10,p));
								
				}

	
	public void initialisation(ModelMap model) {
		FormulairePeriode formulairePeriode = new FormulairePeriode();
		model.addAttribute("formulairePeriode", formulairePeriode);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		// formulaireSaisiesPPS.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
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

		binder.registerCustomEditor(PharmSite.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmSite site = Context.getService(SiteService.class).getPharmSiteById(Integer.parseInt(text));
				this.setValue(site);
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
