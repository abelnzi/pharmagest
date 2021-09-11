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
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
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
import org.openmrs.module.pharmagest.metier.ContaintStock;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulairePharmOrdonnance;
import org.openmrs.module.pharmagest.metier.GestionAlert;
import org.openmrs.module.pharmagest.metier.GestionContrainte;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.validator.Dispensation2Validator;
import org.openmrs.module.pharmagest.validator.DispensationAjoutValidator;
import org.openmrs.module.pharmagest.validator.DispensationValidator;
import org.openmrs.module.pharmagest.web.view.UpdateOrdonnance;
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
@RequestMapping(value = "/module/pharmagest/dispensation.form")
public class DispensationController {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	DispensationValidator dispensationValidator;
	@Autowired
	DispensationAjoutValidator dispensationAjoutValidator;
	@Autowired
	Dispensation2Validator dispensation2Validator;

	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.POST, params = { "btn_recherche" })
	public void rechercher(@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, @RequestParam(required = true, value = "numPatient") String numPatient,
			HttpSession session, ModelMap model) {

		
		Collection<PatientIdentifier> patientIdentifiers = (Collection<PatientIdentifier>) Context.getService(ParametresService.class)
				.getPatientIdentifierByIdentifier(numPatient.replaceAll(" ", ""));		
		

		if (patientIdentifiers.size()==1 && this.checkPatient(numPatient.replaceAll(" ", ""))) {
			PatientIdentifier patientIdentifier = null;
			
			for (PatientIdentifier pi : patientIdentifiers) {
				patientIdentifier=pi;
			}
			
			// determiner l'age et le sexe
			Context.addProxyPrivilege("View People");
			Person person = Context.getPersonService().getPerson(patientIdentifier.getPatient());
			Context.removeProxyPrivilege("View People");
			formulaireOrdonnance.setAge(person.getAge());
			formulaireOrdonnance.setSexe(person.getGender());

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();

			List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class)
					.getAllMedecins();
			
			//get UPID or code 	VIH
			int idType=patientIdentifier.getIdentifierType().getId();
			String upid="";
			String code="";
			if(idType==6) {
				PatientIdentifier identifierVIH = (PatientIdentifier) Context.getService(ParametresService.class)
						.getPatientIdentifierByIdentifierType(person.getId(),3);				
				code=(identifierVIH!=null)?identifierVIH.getIdentifier():"";
				formulaireOrdonnance.setIdParam(code);
				
				patientIdentifier=identifierVIH;
				
				PatientIdentifier identifierUPID = (PatientIdentifier) Context.getService(ParametresService.class)
						.getPatientIdentifierByIdentifierType(person.getId(),6);				
				upid=(identifierUPID!=null)?identifierUPID.getIdentifier():"";
				
			}else {
				PatientIdentifier identifierUPID = (PatientIdentifier) Context.getService(ParametresService.class)
						.getPatientIdentifierByIdentifierType(person.getId(),6);				
				upid=(identifierUPID!=null)?identifierUPID.getIdentifier():"";
			}
			
			formulaireOrdonnance.setPatientIdentifier(patientIdentifier);
			formulaireOrdonnance.setIdParam(patientIdentifier.getIdentifier());
			model.addAttribute("patientIdentifier", patientIdentifier.getIdentifier());
			model.addAttribute("UPID", upid);
			
			/****** determination du nombre de jour de traitemet restant à faire******/
			// recuperer la dernière dispensation
			PharmOrdonnance ordonnance =	Context.getService(DispensationService.class).getPharmOrdonnanceByIdentifier(patientIdentifier);
			long jourRestant=0;
			if(ordonnance!=null){
				//System.out.println("------------------ordonnance----------: "+ordonnance.getOrdId());
				Date dateDispens=ordonnance.getOrdDateDispen();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(dateDispens);
				calendar.add(Calendar.DATE, ordonnance.getOrdNbreJrsTrai());
				long oneDay= (1000*60*60*24);
				Date finTraitement=calendar.getTime();
				Date today=new Date();				
				long restJour= (finTraitement.getTime()-today.getTime())/oneDay;
				jourRestant=restJour;
				//System.out.println("jour de traitement restant : "+restJour);
				//if (restJour<0) jourRestant=0;
				
				formulaireOrdonnance.setPharmRegime(ordonnance.getPharmRegime());
				model.addAttribute("regime", ordonnance.getPharmRegime().getRegimLib());
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				
				model.addAttribute("rdv", formatter.format(ordonnance.getOrdDateDispen()));
				//System.out.println("------------------finTraitementAnterieur----------- "+formatter.format(ordonnance.getOrdDateRdv()));
				model.addAttribute("finTraitementAnterieur", formatter.format(ordonnance.getOrdDateRdv()));
			}
			
			//System.out.println("------------------aucune ordonnance----------- ");
			
			model.addAttribute("jourRestant", jourRestant);

			model.addAttribute("programmes", programmes);
			
			model.addAttribute("produits", produits);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("mess", "find");
			model.addAttribute("bar", "1");
			model.addAttribute("var", "0");

			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			
			// recuperer la dernière dispensation
//			PharmOrdonnance dispensation = Context.getService(DispensationService.class)
//					.getLastPharmOrdonnance(patientIdentifier);
//			
//			if (dispensation != null) {
//				System.out.println("------------------dispensation----------: "+dispensation.getOrdId());
//				formulaireOrdonnance.setPharmRegime(dispensation.getPharmRegime());
//				model.addAttribute("regime", dispensation.getPharmRegime().getRegimLib());
//				model.addAttribute("rdv", dispensation.getOrdDateDispen());
//			}
			
			//gestion des périmés
			List<ContaintStock> listStockPerim= new GestionAlert().getProduitsPerimes();
			Iterator it = listStockPerim.iterator();
			while (it.hasNext()) {
				ContaintStock containtStock = (ContaintStock) it.next();
				PharmProduitAttribut produitAttribut=containtStock.getPharmProduitAttribut();
				produitAttribut.setFlagValid(false);
				Context.getService(OperationService.class).savePharmProduitAttribut(produitAttribut);				
			}
			

		} else {			
			model.addAttribute("mess", "echec");
			
			StringBuffer msgBuffer = new StringBuffer();
			// gestion des doublons
		
			if(patientIdentifiers.size()>1){
				msgBuffer.append("les patients suivant ont les mêmes identifiant : \n");
				msgBuffer.append("\n");
				 for (PatientIdentifier patientIdentifier : patientIdentifiers) {
					msgBuffer.append(" - "+patientIdentifier.getPatient().getFamilyName()+" ");
					msgBuffer.append(patientIdentifier.getPatient().getGivenName()+" ");
					msgBuffer.append(patientIdentifier.getPatient().getMiddleName()+" ");
					msgBuffer.append("_genre :"+patientIdentifier.getPatient().getGender()+" ");
					msgBuffer.append("_Age :"+patientIdentifier.getPatient().getAge()+" ");
					msgBuffer.append("\n");
				}
				
			} else if(!this.checkPatient(numPatient.replaceAll(" ", ""))){
				msgBuffer.append("le dossier du patient ");
				msgBuffer.append(numPatient.replaceAll(" ", ""));
				msgBuffer.append(" est clôturé");
			}
			  else{
				msgBuffer.append("Aucun patient trouver");
			}
			
			model.addAttribute("msgBuffer", msgBuffer);
		}

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public String initDispenser(ModelMap model) {
		this.initialisation(model);
		//new UpdateOrdonnance().cleanIdentifer("0111/01/00197/09");
		System.out.println("============gestionnairePharma init=================");

		return "/module/pharmagest/dispensation";
	}
	@RequestMapping(method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/dispensation";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(
			@ModelAttribute("formulaireOrdonnance") FormulairePharmOrdonnance formulaireOrdonnance,
			BindingResult result, HttpSession session, ModelMap model) {
		dispensation2Validator.validate(formulaireOrdonnance, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		
		List<PharmRegime> regimes = (List<PharmRegime>) Context.getService(ParametresService.class).getAllRegimes();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		
		if (!result.hasErrors()) {
			/*Calendar calendar = new GregorianCalendar();
			calendar.setTime(formulaireOrdonnance.getOrdDateRdv());
			calendar.add(Calendar.DATE, -1);
			formulaireOrdonnance.setOrdDateRdv(calendar.getTime());*/
			
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
			
			//gestion de la contraintre lié à l'inventaire et à la date future
			boolean bInventaire=new GestionContrainte().autoriserOperation(formulaireOrdonnance.getPharmProgramme(),
					formulaireOrdonnance.getOrdDateDispen());
			boolean bDateFuture=formulaireOrdonnance.getOrdDateDispen().before(new Date());
			if(bInventaire && bDateFuture){
				model.addAttribute("var", "1");
			}else if (!bInventaire){
				model.addAttribute("mess", "aut");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				model.addAttribute("dateAut", dateFormat.format(formulaireOrdonnance.getOrdDateDispen()));
				model.addAttribute("var", "0");
			} else if (!bDateFuture){
				model.addAttribute("mess", "aut2");
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				model.addAttribute("dateAut", dateFormat.format(formulaireOrdonnance.getOrdDateDispen()));
				model.addAttribute("var", "0");
			}
			
			
			
		}else{
			model.addAttribute("formulaireOrdonnance", formulaireOrdonnance);
			model.addAttribute("programmes", programmes);
			model.addAttribute("medecins", medecins);
			model.addAttribute("regimes", regimes);
			model.addAttribute("bar", "1");
			model.addAttribute("var", "0");
		}

		

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
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

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
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
			
			
			//mettre à jour  de la date de fin de traitement dans le module clinique
			//Encounter encounter=Context.getService(DispensationService.class).getLastEncounter(formulaireOrdonnance.getPatientIdentifier().getPatient().getPatientId(),ord.getOrdDateDispen());
			
			//Verifier les produits saisis si cotri seul
			Map lignes = formulaireOrdonnance.getTabDispensationMvt().getLigneDispensations();
			boolean resp=this.checkProduits(lignes);
			System.out.println("====================resp==================="+resp);
			
			//regime de pharmacie
			Integer concept=this.getRegime(ord.getPharmRegime().getRegimId());
			
			
			
			if(formulaireOrdonnance.getPharmOrdonnance().getPharmProgramme().getProgramId()==1 && concept!=null && resp==true) {
			
				Context.addProxyPrivilege("View People");
				Context.addProxyPrivilege("View Concepts");
				Context.addProxyPrivilege("Add Observations");
				Context.addProxyPrivilege("Edit Observations");
				Person person= Context.getPersonService().getPerson(formulaireOrdonnance.getPatientIdentifier().getPatient().getPersonId());
				//Context.removeProxyPrivilege("View People");
				
				//System.out.println("===========ID PatientIdentifier============="+formulaireOrdonnance.getPatientIdentifier().getId());

				Location location=formulaireOrdonnance.getPatientIdentifier().getLocation();
				
				
				//Context.addProxyPrivilege("View Concepts");				
				Obs obs1= new Obs();				
				obs1.setConcept(Context.getConceptService().getConcept(165010));
				obs1.setCreator(Context.getAuthenticatedUser());
				obs1.setDateCreated(new Date());
				//obs1.setEncounter(encounter);
				obs1.setLocation(location);
				obs1.setObsDatetime(ord.getOrdDateDispen());			
				obs1.setPerson(person);
				obs1.setValueDatetime(ord.getOrdDateDispen());
				Context.getObsService().saveObs(obs1,null);
				
				
				Obs obs2= new Obs();
				obs2.setConcept(Context.getConceptService().getConcept(165011));
				obs2.setCreator(Context.getAuthenticatedUser());
				obs2.setDateCreated(new Date());
				//obs2.setEncounter(encounter);
				obs2.setLocation(location);
				obs2.setObsDatetime(ord.getOrdDateDispen());
				obs2.setPerson(person);
				obs2.setValueNumeric(new Integer( ord.getOrdNbreJrsTrai()).doubleValue());//
				Context.getObsService().saveObs(obs2,null);
				
				
				
				Obs obs3= new Obs();
				obs3.setConcept(Context.getConceptService().getConcept(165033));
				obs3.setCreator(Context.getAuthenticatedUser());
				obs3.setDateCreated(new Date());
				obs3.setLocation(location);
				obs3.setObsDatetime(ord.getOrdDateDispen());
				obs3.setPerson(person);
				obs3.setValueCoded(Context.getConceptService().getConcept(concept));//
				Context.getObsService().saveObs(obs3,null);
				
				
				// Date de ruptue des medicaments
				Concept conceptObs=Context.getConceptService().getConcept(165040);
				if(conceptObs!=null){
					Obs obs4= new Obs();				
					obs4.setConcept(Context.getConceptService().getConcept(165040));
					obs4.setCreator(Context.getAuthenticatedUser());
					obs4.setDateCreated(new Date());
					obs4.setLocation(location);
					obs4.setObsDatetime(ord.getOrdDateDispen());			
					obs4.setPerson(person);
					obs4.setValueDatetime(ord.getOrdDateRdv());
					Context.getObsService().saveObs(obs4,null);
		
				}
				
				Context.removeProxyPrivilege("View People");
				Context.removeProxyPrivilege("View Concepts");
				Context.removeProxyPrivilege("Add Observations");
				Context.removeProxyPrivilege("Edit Observations");	
				
			}else if (formulaireOrdonnance.getPharmOrdonnance().getPharmProgramme().getProgramId()==1 
					&& concept==null) { // VIH: Si regime different de regime ARV
				//Date de RDV inchanger pour un autre regime autre que ARV
				ord.setOrdDateRdv(ord.getOrdDateDispen());
			}
			
						
			//ord.setPatientIdentifier(null);
			
			if(formulaireOrdonnance.getPharmOrdonnance().getPharmProgramme().getProgramId()==1 &&
					concept!=null && resp==false ) {
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
				model.addAttribute("mess", "notsave");
				model.addAttribute("bar", "1");
				model.addAttribute("var", "1");
				
			}else { // save si ARV 
				
				Context.getService(DispensationService.class).savePharmOrdonnance(ord);
				
				// save operation
				PharmOperation operation = new PharmOperation();
				operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(2));
				operation.setOperDateRecept(ord.getOrdDateDispen());
				operation.setOperDateSaisi(new Date());
				operation.setPharmGestionnairePharma(ord.getPharmGestionnairePharma());
				operation.setPharmProgramme(ord.getPharmProgramme());
				operation.setOperRef(ord.getOrdId()+"");
				Context.getService(OperationService.class).savePharmOperation(operation);
				// save ligne dispensation
				//Map lignes = formulaireOrdonnance.getTabDispensationMvt().getLigneDispensations();
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
						histoMouvementStock.setPharmOperation(operation);
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
				
			}
			
			
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
	
	public boolean checkProduits(Map lignesProduits) {
		boolean var=false;
		ArrayList<String> listProduitARV = new ArrayList<String>();
		
		for (Iterator i = lignesProduits.keySet().iterator(); i.hasNext();) {
			Object key = i.next();
			LigneDispensationMvt ligne = (LigneDispensationMvt) lignesProduits.get(key);
			String searchedValue = ligne.getPharmProduit().getProdCode();
			if(!this.containtCode(searchedValue))var=true;
						
		}
		
		return var;
		
	}
	
	public boolean containtCode(String code) {
		boolean found =false;
		String[] stringArray = new String[]{"AR01321","AR01340","AR01350","AR28140","AR28145","AR41427","AM28145"};
		for(String x : stringArray){
			//System.out.println("====================x==================="+x);
			//System.out.println("====================code==================="+code);
			//System.out.println("====================x.equals(code)==================="+(x.equals(code)));
			if(x.equals(code)){
				found  = true;
		        break;
		    }
		}
		return found ;
		
	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
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
	
	@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
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
	
	public boolean checkPatient(String identifier){
		Date dateInit=Context.getService(DispensationService.class).getEncounterByIdentifier(1, identifier);
		Date dateCloture=Context.getService(DispensationService.class).getEncounterByIdentifier(5, identifier);
		
		if(dateInit!=null && dateCloture!=null && (dateCloture.after(dateInit))){
			return false;
		}
		return true;
	}
	
	public void initialisation(ModelMap model) {

		FormulairePharmOrdonnance formulaireOrdonnance = new FormulairePharmOrdonnance();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		//System.out.println("============gestionnairePharma================="+gestionnairePharma);
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		System.out.println("============save gestionnairePharma=================");

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
