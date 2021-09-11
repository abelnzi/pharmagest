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

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LigneDispensationId;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulairePharmOrdonnance;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.validator.Dispensation2Validator;
import org.openmrs.module.pharmagest.validator.DispensationAjoutValidator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import jxl.write.DateTime;

/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulaireOrdonnance")
//@RequestMapping(value = "/module/pharmagest/dispensation.form")
public class _DispensationController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	DispensationValidator dispensationValidator;
	@Autowired
	DispensationAjoutValidator dispensationAjoutValidator;
	@Autowired
	Dispensation2Validator dispensation2Validator;

	@SuppressWarnings("unused")
	//@RequestMapping(method = RequestMethod.POST, params = { "btn_recherche" })
	public void rechercher(@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, @RequestParam(required = true, value = "numPatient") String numPatient,
			HttpSession session, ModelMap model) {

		Collection<PatientIdentifier> patientIdentifiers = Context.getService(ParametresService.class)
				.getPatientIdentifierByIdentifier(numPatient.replaceAll(" ", ""));
		PatientIdentifier patientIdentifier = null;				
		for (PatientIdentifier pi : patientIdentifiers) {
			patientIdentifier=pi;
		}

		if (patientIdentifier != null) {
			// determiner l'age et le sexe
			@SuppressWarnings("deprecation")
			Person person = Context.getPersonService().getPerson(patientIdentifier.getPatient());
			formulaireOrdonnance.setAge(person.getAge());
			formulaireOrdonnance.setSexe(person.getGender());

			formulaireOrdonnance.setPatientIdentifier(patientIdentifier);
			model.addAttribute("patientIdentifier", patientIdentifier.getIdentifier());

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();

			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
					.getAllMedecins();
			
			/****** determination du nombre de jour de traitemet restant à faire******/
			PharmOrdonnance ordonnance =	Context.getService(DispensationService.class).getPharmOrdonnanceByIdentifier(patientIdentifier);
			long jourRestant=0;
			if(ordonnance!=null){
				Date dateDispens=ordonnance.getOrdDateDispen();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dateDispens);
				calendar.add(Calendar.DATE, ordonnance.getOrdNbreJrsTrai());
				long oneDay= (1000*60*60*24);
				Date finTraitement=calendar.getTime();
				Date today=new Date();				
				long restJour= (today.getTime() - finTraitement.getTime())/oneDay;
				System.out.println("jour de traitement restant : "+restJour);
				if (restJour<=0) jourRestant=-restJour;
			}
			model.addAttribute("jourRestant", jourRestant);

			model.addAttribute("programmes", programmes);
			formulaireOrdonnance.setIdParam(numPatient);
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("mess", "find");
			model.addAttribute("bar", "1");
			model.addAttribute("var", "0");

			// recuperer la dernière dispensation
			PharmOrdonnance dispensation = Context.getService(DispensationService.class)
					.getLastPharmOrdonnance(patientIdentifier);
			if (dispensation != null) {
				formulaireOrdonnance.setPharmRegime(dispensation.getPharmRegime());
				model.addAttribute("regime", dispensation.getPharmRegime().getRegimLib());
				model.addAttribute("rdv", dispensation.getOrdDateDispen());
			}

		} else {
			model.addAttribute("mess", "echec");
		}

	}

	@SuppressWarnings("deprecation")
	//@RequestMapping(method = RequestMethod.GET)
	public String initDispenser(ModelMap model) {
		this.initialisation(model);

		return "/module/pharmagest/dispensation";
	}
	//@RequestMapping(method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/dispensation";
	}
	
	//@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		dispensation2Validator.validate(formulaireOrdonnance, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		
		if (!result.hasErrors()) {
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("programmes", programmes);
			if(!formulaireOrdonnance.getPharmRegime().getPharmProduits().isEmpty()){
				model.addAttribute("produits",this.transformToList(formulaireOrdonnance.getPharmRegime().getPharmProduits()));
			}else {
				model.addAttribute("produits", this.transformToList(formulaireOrdonnance.getPharmProgramme().getPharmProduits()));
			}
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("bar", "1");
			model.addAttribute("var", "1");
		}else{
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("programmes", programmes);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("bar", "1");
			model.addAttribute("var", "0");
		}

		

	}

	//@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			dispensationValidator.validate(formulaireOrdonnance, result);

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();
			List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
					.getAllMedecins();

			model.addAttribute("programmes", programmes);

			if(!formulaireOrdonnance.getPharmRegime().getPharmProduits().isEmpty()){
				model.addAttribute("produits",this.transformToList(formulaireOrdonnance.getPharmRegime().getPharmProduits()));
			}else {
				model.addAttribute("produits", this.transformToList(formulaireOrdonnance.getPharmProgramme().getPharmProduits()));
			}
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);

			if (!result.hasErrors()) {
				// contrôle du Stock
				List<PharmStocker> stockerList = (ArrayList<PharmStocker>) Context.getService(GestionStockService.class)
						.getPharmStockersByPP(formulaireOrdonnance.getPharmProduit(),
								formulaireOrdonnance.getPharmProgramme());
				model.addAttribute("info", stockerList.isEmpty());
				if (!stockerList.isEmpty()) {
					//Collections.sort(stockerList);
					Collections.sort(stockerList, Collections.reverseOrder());

					Integer qte = formulaireOrdonnance.getLdQteServi();
					Integer qteTotalServi = 0;
					boolean condition = true;
					Iterator it = stockerList.iterator();
					while (it.hasNext() && condition == true) {
						PharmStocker stock = (PharmStocker) it.next();
						if (stock.getStockQte() >= qte) {

							LigneDispensationMvt lgnDisp = new LigneDispensationMvt();
							lgnDisp.setPharmProduit(formulaireOrdonnance.getPharmProduit());
							lgnDisp.setLdPrixUnit(formulaireOrdonnance.getLdPrixUnit());
							lgnDisp.setLdQteDem(formulaireOrdonnance.getLdQteDem());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot());
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());
							lgnDisp.setLdQteServi(qte);
							if (qte != 0)
								formulaireOrdonnance.getTabDispensationMvt().addLigneDispensation(lgnDisp);
							qteTotalServi = qteTotalServi + qte;
							condition = false;

						} else {
							LigneDispensationMvt lgnDisp = new LigneDispensationMvt();
							lgnDisp.setPharmProduit(formulaireOrdonnance.getPharmProduit());
							lgnDisp.setLdPrixUnit(formulaireOrdonnance.getLdPrixUnit());
							lgnDisp.setLdQteDem(formulaireOrdonnance.getLdQteDem());
							lgnDisp.setLgnRecptLot(stock.getPharmProduitAttribut().getProdLot());
							lgnDisp.setLgnDatePerem(stock.getPharmProduitAttribut().getProdDatePerem());
							lgnDisp.setLdQteServi(stock.getStockQte());
							formulaireOrdonnance.getTabDispensationMvt().addLigneDispensation(lgnDisp);

							qteTotalServi = qteTotalServi + lgnDisp.getLdQteServi();
							qte = qte - stock.getStockQte();
						}
					}

					if (formulaireOrdonnance.getLdQteDem() <= qteTotalServi) {

						model.addAttribute("mess", "accept");

					} else if (formulaireOrdonnance.getLdQteDem() > qteTotalServi) {

						model.addAttribute("mess", "refuse");

					}
					model.addAttribute("ligneDispensations",
							formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
					model.addAttribute("bar", "1");
					model.addAttribute("var", "1");
					model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
				} else {
					model.addAttribute("ligneDispensations",
							formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
					model.addAttribute("mess", "refuse");
				}
				
				formulaireOrdonnance.setPharmProduit(null);
				formulaireOrdonnance.setLdQteServi(null);
				formulaireOrdonnance.setLdQteDem(null);
				formulaireOrdonnance.setLdPrixUnit(null);

				model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
				model.addAttribute("bar", "1");
				model.addAttribute("var", "1");
				model.addAttribute("ligneDispensations",
						formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());

			} else {
				formulaireOrdonnance.setPharmProduit(null);
				formulaireOrdonnance.setLdQteDem(null);
				formulaireOrdonnance.setLdQteServi(null);
				formulaireOrdonnance.setLdPrixUnit(null);
				model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
				model.addAttribute("bar", "1");
				model.addAttribute("var", "1");
				model.addAttribute("ligneDispensations",
						formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
			}

		/*} catch (Exception e) {
			e.getStackTrace();
		}*/

	}

	//@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			@ModelAttribute("ligneDispensations") HashMap<Integer, LigneDispensation> ligneDispensations,
			BindingResult result, HttpSession session, ModelMap model) {
		dispensationValidator.validate(formulaireOrdonnance, result);
		

		if (!result.hasErrors()) {
			// save ordonnance
			PharmOrdonnance ord = new PharmOrdonnance();
			ord = formulaireOrdonnance.getPharmOrdonnance();
			ord.setOrdDateSaisi(new Date());
			// ord.setPharmGestionnairePharma(formulaireOrdonnance.getGestionnairePharma());
			if (ord.getOrdDateRdv() == null) {
				Date datRdV = ord.getOrdDateDispen();
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(datRdV);
				gc.add(GregorianCalendar.DATE, ord.getOrdNbreJrsTrai());
				ord.setOrdDateRdv(gc.getTime());
			}
			Context.getService(DispensationService.class).savePharmOrdonnance(ord);
			
			//mettre à jour  de la date de fin de traitement dans le module clinique
			Encounter encounter=Context.getService(DispensationService.class).getLastEncounter(formulaireOrdonnance.getPatientIdentifier().getPatient().getPatientId(), null);
			if(encounter!=null){
				Person person= Context.getPersonService().getPerson(formulaireOrdonnance.getPatientIdentifier().getPatient().getPersonId());
				Obs obs1=Context.getService(DispensationService.class).getObs(encounter.getEncounterId(), 165010, person.getPersonId());
				if(obs1!=null){
					obs1.setChangedBy(Context.getAuthenticatedUser());
					obs1.setDateChanged(new Date());
					obs1.setLocation(Context.getLocationService().getDefaultLocation());
					obs1.setObsDatetime(new Date());
					obs1.setValueDatetime(ord.getOrdDateDispen());
					Context.getObsService().saveObs(obs1,"update");
				} else {
				obs1= new Obs();				
				obs1.setConcept(Context.getConceptService().getConcept(165010));
				obs1.setCreator(Context.getAuthenticatedUser());
				obs1.setDateCreated(new Date());
				obs1.setEncounter(encounter);
				obs1.setLocation(Context.getLocationService().getDefaultLocation());
				obs1.setObsDatetime(new Date());			
				obs1.setPerson(person);
				obs1.setValueDatetime(ord.getOrdDateDispen());
				Context.getObsService().saveObs(obs1,null);
				}
				
				Obs obs2;
				obs2=Context.getService(DispensationService.class).getObs(encounter.getEncounterId(), 165011, person.getPersonId());
				System.out.println("valeur du obs :: "+obs2);
				if(obs2!=null){
					obs2.setChangedBy(Context.getAuthenticatedUser());
					obs2.setDateChanged(new Date());
					obs2.setLocation(Context.getLocationService().getDefaultLocation());
					obs2.setObsDatetime(new Date());
					obs2.setValueDatetime(ord.getOrdDateDispen());
					Context.getObsService().saveObs(obs2,"update");
					System.out.println("Jsuis dans le update");
				} else {
					System.out.println("Jsuis dans le save");
				obs2= new Obs();
				obs2.setConcept(Context.getConceptService().getConcept(165011));
				obs2.setCreator(Context.getAuthenticatedUser());
				obs2.setDateCreated(new Date());
				obs2.setEncounter(encounter);
				obs2.setLocation(Context.getLocationService().getDefaultLocation());
				obs2.setObsDatetime(new Date());
				obs2.setPerson(person);
				obs2.setValueNumeric(new Integer( ord.getOrdNbreJrsTrai()).doubleValue());//
				Context.getObsService().saveObs(obs2,null);
				
				}
			}
			
			// save operation
			PharmOperation operation = new PharmOperation();
			operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(2));
			operation.setOperDateRecept(ord.getOrdDateDispen());
			operation.setOperDateSaisi(new Date());
			operation.setPharmGestionnairePharma(ord.getPharmGestionnairePharma());
			operation.setPharmProgramme(ord.getPharmProgramme());
			Context.getService(OperationService.class).savePharmOperation(operation);
			// save ligne dispensation
			Map lignes = formulaireOrdonnance.getTabDispensationMvt().getLigneDispensations();
			for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				LigneDispensationMvt ligne = (LigneDispensationMvt) lignes.get(key);
				PharmLigneDispensation ld = new PharmLigneDispensation();
				PharmLigneDispensationId ldId = new PharmLigneDispensationId();
				ldId.setOrdId(ord.getOrdId());
				ldId.setProdId(ligne.getPharmProduit().getProdId());
				// verifier la ligne
				PharmLigneDispensation varLd = Context.getService(DispensationService.class)
						.getPharmLigneDispensation(ldId);
				
				//traiter les  produits avec des lots differents 
				if (varLd != null) {
					ld = varLd;
					ld.setPharmOrdonnance(ord);
					ld.setPharmProduit(ligne.getPharmProduit());
					ld.setLdPrixUnit(ligne.getLdPrixUnit());
					ld.setLdQteDem(ligne.getLdQteDem());
					ld.setLdQteServi(ligne.getLdQteServi() + varLd.getLdQteServi());
					Context.getService(DispensationService.class).updatePharmLigneDispensation(ld);
				} else {
					ld.setId(ldId);
					ld.setPharmOrdonnance(ord);
					ld.setPharmProduit(ligne.getPharmProduit());
					ld.setLdPrixUnit(ligne.getLdPrixUnit());
					ld.setLdQteDem(ligne.getLdQteDem());
					ld.setLdQteServi(ligne.getLdQteServi());
					Context.getService(DispensationService.class).savePharmLigneDispensation(ld);
				}

				// Gestion ProduitAttribut
				PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(ligne.getPharmProduit(), ligne.getLgnRecptLot());
				// gestion des stock
				PharmStockerId stockerId = new PharmStockerId();
				stockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
				stockerId.setProgramId(ord.getPharmProgramme().getProgramId());
				PharmStocker stocker = Context.getService(GestionStockService.class).getPharmStockerById(stockerId);

				if (stocker != null ) {
					Integer stockQte = stocker.getStockQte() - ligne.getLdQteServi();
					stocker.setStockQte(stockQte);
					stocker.setStockDateStock(ord.getOrdDateDispen());
					Context.getService(GestionStockService.class).updatePharmStocker(stocker);
					// insertion dans histoMvm
					PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
					histoMouvementStock.setMvtDate(ord.getOrdDateDispen());
					histoMouvementStock.setMvtLot(ligne.getLgnRecptLot());

					histoMouvementStock.setMvtProgramme(ord.getPharmProgramme().getProgramId());
					histoMouvementStock.setMvtQte(ligne.getLdQteServi());
					histoMouvementStock.setMvtQteStock(stocker.getStockQte());
					histoMouvementStock.setMvtTypeMvt(operation.getPharmTypeOperation().getToperId());
					histoMouvementStock.setPharmProduit(ligne.getPharmProduit());
					Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
					
					// save PharmligneOperation
					PharmLigneOperation lo = new PharmLigneOperation();
					PharmLigneOperationId loId = new PharmLigneOperationId();
					loId.setOperId(operation.getOperId());
					loId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					lo.setId(loId);
					lo.setPharmOperation(operation);
					lo.setPharmProduitAttribut(pharmProduitAttribut);
					lo.setLgnOperDatePerem(ligne.getLgnDatePerem());
					lo.setLgnOperLot(ligne.getLgnRecptLot());
					lo.setLgnOperPrixAchat(ligne.getLdPrixUnit());
					lo.setLgnOperQte(ligne.getLdQteServi());
					Context.getService(OperationService.class).savePharmLigneOperation(lo);
					
				}

			}
			model.addAttribute("mess", "save");
			this.initialisation(model);
		} else {
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
			List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("programmes", programmes);

			if(!formulaireOrdonnance.getPharmRegime().getPharmProduits().isEmpty()){
				model.addAttribute("produits",this.transformToList(formulaireOrdonnance.getPharmRegime().getPharmProduits()));
			}else {
				model.addAttribute("produits", this.transformToList(formulaireOrdonnance.getPharmProgramme().getPharmProduits()));
			}
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("ligneDispensations",
					formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
		}


	}

	//@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneDispensation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);

		// model.addAttribute("patientIdentifier",
		// patientIdentifier.getPatient());


		if (!result.hasErrors()) {
			formulaireOrdonnance.getTabDispensationMvt().removeLigneDispensationById(paramId);
			model.addAttribute("ligneDispensations",
					formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
			model.addAttribute("mess", "supprim");
		}
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("bar", "1");
		if(!formulaireOrdonnance.getPharmRegime().getPharmProduits().isEmpty()){
			model.addAttribute("produits",this.transformToList(formulaireOrdonnance.getPharmRegime().getPharmProduits()));
		}else {
			model.addAttribute("produits", this.transformToList(formulaireOrdonnance.getPharmProgramme().getPharmProduits()));
		}
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		model.addAttribute("var", "1");

	}
	
	//@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
	public void modifLigneDispensation(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);

		// model.addAttribute("patientIdentifier",
		// patientIdentifier.getPatient());


		if (!result.hasErrors()) {
			LigneDispensationMvt ligne=formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationById(modifId);
			formulaireOrdonnance.setLdPrixUnit(ligne.getLdPrixUnit());
			formulaireOrdonnance.setLdQteDem(ligne.getLdQteDem());
			formulaireOrdonnance.setLdQteServi(ligne.getLdQteServi());
			//formulaireOrdonnance.setLgnRecptLot(ligne.getLgnRecptLot());
			formulaireOrdonnance.setPharmProduit(ligne.getPharmProduit());
			//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			//model.addAttribute("datePerem",formatter.format(ligne.getLgnDatePerem()));
			
			formulaireOrdonnance.getTabDispensationMvt().removeLigneDispensationById(modifId);
			model.addAttribute("ligneDispensations",
					formulaireOrdonnance.getTabDispensationMvt().getLigneDispensationsCollection());
			model.addAttribute("mess", "supprim");
		}
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("bar", "1");
		if(!formulaireOrdonnance.getPharmRegime().getPharmProduits().isEmpty()){
			model.addAttribute("produits",this.transformToList(formulaireOrdonnance.getPharmRegime().getPharmProduits()));
		}else {
			model.addAttribute("produits", this.transformToList(formulaireOrdonnance.getPharmProgramme().getPharmProduits()));
		}
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		model.addAttribute("var", "1");

	}
	
	@SuppressWarnings("unchecked")
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}
	
	public void initialisation(ModelMap model) {

		FormulairePharmOrdonnance formulaireOrdonnance = new FormulairePharmOrdonnance();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		formulaireOrdonnance.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();

		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();

		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("regimes", regimes);
		model.addAttribute("var", "0");
		model.addAttribute("bar", "0");
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
