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
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.ConsoByProduit;
import org.openmrs.module.pharmagest.metier.FormulaireDispensationLibre;
import org.openmrs.module.pharmagest.metier.FormulairePeriode;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireSite;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.metier.RapportConsoBean;
import org.openmrs.module.pharmagest.metier.StockBean;
import org.openmrs.module.pharmagest.web.view.Book;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulairePeriode")
@RequestMapping(value = "/module/pharmagest/reverseDispensation.form")
public class reverseDispensationController {


	@RequestMapping( method = RequestMethod.GET)
	public String initConsoProduitJour(ModelMap model) {
		FormulaireDispensationLibre formulairePeriode = new FormulaireDispensationLibre();
		model.addAttribute("formulairePeriode", formulairePeriode);
		model.addAttribute("var", 0);
		return "/module/pharmagest/reverseDispensation";
	}

	@RequestMapping( method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void recherche(@ModelAttribute("formulairePeriode") FormulaireDispensationLibre formulairePeriode,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			
			model.addAttribute("formulairePeriode", formulairePeriode);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
			model.addAttribute("periode", periode);

			if (!result.hasErrors()) {				
				Collection<PharmOrdonnance> listDispensation = Context.getService(DispensationService.class)
						.getPharmOrdonnancesLibresByPeriod(formulairePeriode.getDateDebut(),formulairePeriode.getDateFin());
				//System.out.println("-----------------listDispensation a voir-------------------"+listDispensation);
					if (listDispensation.size()!=0){
						model.addAttribute("listDispensation", listDispensation);
						model.addAttribute("var", 1);
						formulairePeriode.setListDispensation(listDispensation);
					}else{
						model.addAttribute("mess", 1);
						model.addAttribute("var", 0);
					}
				
				}
				

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
			@RequestMapping(method = RequestMethod.POST, params = {
			"btn_valider" })
		public void valider(@ModelAttribute("formulairePeriode") FormulaireDispensationLibre formulairePeriode,
				BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {
		
			
			model.addAttribute("formulairePeriode", formulairePeriode);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulairePeriode.getDateDebut())+" au "+sf.format(formulairePeriode.getDateFin());
			model.addAttribute("periode", periode);
			model.addAttribute("var", 2); 
			
			if (!result.hasErrors()) {
			Collection<PharmOrdonnance> listDispenLibre = new ArrayList<PharmOrdonnance>();
			Collection<PharmOrdonnance> listDispenEnreg = new ArrayList<PharmOrdonnance>();
			
			int taille = formulairePeriode.getListDispensation().size();
			//System.out.println("=================taille================="+taille);
			for (int i = 1; i <= taille; i++) {
				String ordNumPatient = request.getParameter("id" + i);
				if(ordNumPatient==null)ordNumPatient="0";
				String dispensId = request.getParameter("dispensId" + i);
				if(dispensId==null)dispensId=0+"";
				int dispensIdInt=Integer.parseInt(dispensId);				
				PharmOrdonnance dispensation=Context.getService(DispensationService.class).getPharmOrdonnanceById(dispensIdInt);
				System.out.println("=================dispensation================="+dispensation.getOrdId());
				
				System.out.println("=================ordNumPatient================="+ordNumPatient.replaceAll(" ", ""));
				Collection<PatientIdentifier> patientIdentifiers = Context.getService(ParametresService.class)
						.getPatientIdentifierByIdentifier(ordNumPatient.replaceAll(" ", ""));
				//System.out.println("=================patientIdentifiers================="+patientIdentifiers);
				
				if(patientIdentifiers.size()==1 && dispensation!=null && this.checkPatient(ordNumPatient.replaceAll(" ", ""))){
					//System.out.println("=================ordNumPatient2================="+ordNumPatient.replaceAll(" ", ""));
					
					/*
					 * PatientIdentifier patientIdentifier = null; for (PatientIdentifier pi :
					 * patientIdentifiers) { patientIdentifier=pi; }
					 */
					
					PatientIdentifier patientIdentifier =patientIdentifiers.iterator().next();
					//System.out.println("=================patientIdentifier================="+patientIdentifier.getPatientIdentifierId());
					
					
					dispensation.setPatientIdentifier(patientIdentifier);
					System.out.println("=================dispensation================="+dispensation.getPatientIdentifier().getPatientIdentifierId());
					dispensation.setOrdNumeroPatient(ordNumPatient.replaceAll(" ", ""));
					Context.getService(DispensationService.class).updatePharmOrdonnance(dispensation);
					//mettre à jour  de la date de fin de traitement dans le module clinique
					
					Integer concept=null;
					if(dispensation.getPharmRegime()!=null) concept=this.getRegime(dispensation.getPharmRegime().getRegimId());
					
					if(concept!=null) {
						Context.addProxyPrivilege("View People");
						Context.addProxyPrivilege("View Concepts");
						Context.addProxyPrivilege("Add Observations");
						Context.addProxyPrivilege("Edit Observations");
						Person person= Context.getPersonService().getPerson(patientIdentifier.getPatient().getPersonId());
						//Context.removeProxyPrivilege("View People");
						
						
						//Context.addProxyPrivilege("View Concepts");				
						Obs obs1= new Obs();				
						obs1.setConcept(Context.getConceptService().getConcept(165010));
						obs1.setCreator(Context.getAuthenticatedUser());
						obs1.setDateCreated(new Date());
						//obs1.setEncounter(encounter);
						obs1.setLocation(Context.getLocationService().getDefaultLocation());
						obs1.setObsDatetime(dispensation.getOrdDateDispen());			
						obs1.setPerson(person);
						obs1.setValueDatetime(dispensation.getOrdDateDispen());
						Context.getObsService().saveObs(obs1,null);
						
						
						Obs obs2= new Obs();
						obs2.setConcept(Context.getConceptService().getConcept(165011));
						obs2.setCreator(Context.getAuthenticatedUser());
						obs2.setDateCreated(new Date());
						//obs2.setEncounter(encounter);
						obs2.setLocation(Context.getLocationService().getDefaultLocation());
						obs2.setObsDatetime(dispensation.getOrdDateDispen());
						obs2.setPerson(person);
						if(dispensation.getOrdNbreJrsTrai()!=null)obs2.setValueNumeric(new Integer( dispensation.getOrdNbreJrsTrai()).doubleValue());//
						Context.getObsService().saveObs(obs2,null);
						
						//regime de pharmacie
						//System.out.println("==================concept==============="+this.getRegime(dispensation.getPharmRegime().getRegimId()));
						
						if(concept!=null){
							Obs obs3= new Obs();
							obs3.setConcept(Context.getConceptService().getConcept(165033));
							obs3.setCreator(Context.getAuthenticatedUser());
							obs3.setDateCreated(new Date());
							obs3.setLocation(Context.getLocationService().getDefaultLocation());
							obs3.setObsDatetime(dispensation.getOrdDateDispen());
							obs3.setPerson(person);
							obs3.setValueCoded(Context.getConceptService().getConcept(concept));//
							Context.getObsService().saveObs(obs3,null);
						}
						
						// Date de ruptue des medicaments
						Concept conceptObs=Context.getConceptService().getConcept(165040);
						if(conceptObs!=null){
							Obs obs4= new Obs();				
							obs4.setConcept(Context.getConceptService().getConcept(165040));
							obs4.setCreator(Context.getAuthenticatedUser());
							obs4.setDateCreated(new Date());
							obs4.setLocation(Context.getLocationService().getDefaultLocation());
							obs4.setObsDatetime(dispensation.getOrdDateDispen());			
							obs4.setPerson(person);
							obs4.setValueDatetime(dispensation.getOrdDateRdv());
							Context.getObsService().saveObs(obs4,null);
				
						}

						Context.removeProxyPrivilege("View People");
						Context.removeProxyPrivilege("View Concepts");
						Context.removeProxyPrivilege("Add Observations");
						Context.removeProxyPrivilege("Edit Observations");	
					}
									
					listDispenEnreg.add(dispensation);	
					
				}else {
					listDispenLibre.add(dispensation);					
				}
				
				
			}
			model.addAttribute("listDispenEnreg", listDispenEnreg);
			model.addAttribute("listDispenLibre", listDispenLibre);
			model.addAttribute("var", 2);
			
		}else{
			result.getFieldError();
			
		}
					
		
		}
			
		public boolean checkPatient(String identifier){
			Date dateInit=Context.getService(DispensationService.class).getEncounterByIdentifier(1, identifier);
			Date dateCloture=Context.getService(DispensationService.class).getEncounterByIdentifier(5, identifier);
			
			if(dateInit!=null && dateCloture!=null && (dateCloture.after(dateInit))){
				return false;
			}
			return true;
		}
			
			public Integer getRegime(int regimId){
				Integer conceptId=0;
				switch(regimId){  
				case 1 : conceptId = 164058; break ;
				case 2 : conceptId = 164612; break ;
				case 3 : conceptId = 164613; break ;
				case 4 : conceptId = 164061; break ;
				case 6 : conceptId = 164060; break ;
				case 7 : conceptId = 164006; break;
				case 8 : conceptId = 164003; break;
				case 9 : conceptId = 164005; break;
				case 10 : conceptId = 164611 ; break ;
				case 11 : conceptId = 164407; break ;
				case 12 : conceptId = 164627; break ;
				case 13 : conceptId = 164416; break ;
				case 14 : conceptId = 164409; break ;
				case 15 : conceptId = 164411; break ;
				case 17 : conceptId = 164613; break ;
				case 18 : conceptId =165035; break ;
				case 19 : conceptId = 164642; break;
				case 20 : conceptId = 164142; break ;

				case 22 : conceptId = 164144; break ;

				case 24 : conceptId =165036; break ;

				case 26 : conceptId = 164416; break ;
				case 27 : conceptId = 164409; break ;

				case 35 : conceptId = 164410; break ;
				case 37 : conceptId = 165038; break;
				case 38 : conceptId = 165039; break;
				case 39 : conceptId = 165041; break;
				case 40 : conceptId = 165042; break;
				case 41 : conceptId = 165043; break;
				
				case 42 : conceptId =164642; break ;
				case 43 : conceptId =164654; break ;
				case 44 : conceptId =164655; break ;
				case 45 : conceptId =164647; break ;
				case 46 : conceptId =164646; break ;
				case 47 : conceptId =164611; break ;
				case 48 : conceptId =164636; break ;
				case 49 : conceptId =164643; break ;
				case 50 : conceptId =164656; break ;
				case 51 : conceptId =164657; break ;
				case 52 : conceptId =164631; break ;
				case 53 : conceptId =164633; break ;
				case 54 : conceptId =164632; break ;
				case 55 : conceptId =164644; break ;
				case 56 : conceptId =164634; break ;
				case 57 : conceptId =164635; break ;
				case 58 : conceptId =164628; break ;
				case 59 : conceptId =164658; break ;
				case 60 : conceptId =164629; break ;
				case 61 : conceptId =164639; break ;
				case 62 : conceptId =164660; break ;
				case 63 : conceptId =164659; break ;
				case 64 : conceptId =164651; break ;
				case 65 : conceptId =164641; break ;
				case 66 : conceptId =164662; break ;
				case 67 : conceptId =164652; break ;
				case 68 : conceptId =164638; break ;
				case 69 : conceptId =164653; break ;
				case 70 : conceptId =164680; break ;
				case 71 : conceptId =164661; break ;
				case 72 : conceptId =164637; break ;
				case 73 : conceptId =164640; break ;
				case 74 : conceptId =165236; break ;
				case 75 : conceptId =165237; break ;
				case 76 : conceptId =165238; break ;
				
			    default:conceptId=null;  
			    
			    }  
				return conceptId;
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
