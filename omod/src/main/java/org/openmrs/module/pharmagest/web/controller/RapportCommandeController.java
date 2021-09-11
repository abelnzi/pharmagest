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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaireId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmLigneRcId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.RapportCommandeService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireRapportCommande;
import org.openmrs.module.pharmagest.metier.FormulaireSite;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.metier.RapportConsoBean;
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
@SessionAttributes("formulaireRapportCommande")
public class RapportCommandeController {

	@RequestMapping(value = "/module/pharmagest/rapportCommande.form", method = RequestMethod.GET)
	public String initRapportConso(ModelMap model) {
		FormulaireRapportCommande formulaireRapportCommande = new FormulaireRapportCommande();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		
		Location defaultLocation = Context.getLocationService().getDefaultLocation();
		if (defaultLocation != null) {
			PharmSite site = Context.getService(SiteService.class)
					.getPharmSiteByCode(defaultLocation.getPostalCode());
			if(site!=null){
				site.setSitFlag("DA");//desactive le site
				Context.getService(SiteService.class).updatePharmSite(site);				
			}
			
		}

		model.addAttribute("formulaireRapportCommande", formulaireRapportCommande);
		model.addAttribute("programmes", programmes);

		return "/module/pharmagest/rapportCommande";
	}

	@RequestMapping(value = "/module/pharmagest/rapportCommande.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void rapportConsommation(@ModelAttribute("formulaireRapportCommande") FormulaireRapportCommande formulaireRapportCommande,
			BindingResult result, HttpSession session, ModelMap model) throws ParseException {
		// try {
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("formulaireRapportCommande", formulaireRapportCommande);
		model.addAttribute("programmes", programmes);
		SimpleDateFormat sf = new SimpleDateFormat("MM-yyyy");
		model.addAttribute("dateMois", "du mois de : "+sf.format(formulaireRapportCommande.getDateCommande()));
		// Gestion du critère de date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(formulaireRapportCommande.getDateCommande());
		//calendar.setTime(date);
		//calendar.add(Calendar.MONTH, -1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String mois;
		if (month < 10) {
			mois = "0" + month;
			if (month == 0)
				mois = "12";
		} else {
			mois = month + "";
		}
			
		Date minDate = formatter.parse(year + "-" + mois + "-01");
		Date maxDate = formatter.parse(year + "-" + mois + "-" + maxDay);
		
		//System.out.println("maxDate : " + formatter.format(maxDate));
		//System.out.println("minDate : " + formatter.format(minDate));
		

		if (!result.hasErrors()) {

			// verifier si le rapport commande existe
			ArrayList<PharmRapportCommande> commandes = (ArrayList<PharmRapportCommande>) Context
					.getService(RapportCommandeService.class)
					.getPharmRapportCommandeByPeriod(formulaireRapportCommande.getPharmProgramme(), maxDate);
			PharmRapportCommande commandeRapport = null;
			Collection<RapportCommandeBean> listRapCommande = null;

			if (!(commandes.isEmpty())) {

				commandeRapport = commandes.get(0);
				//System.out.println("commandeSite : " + commandeRapport.getRapComDateCom().toString());
				Collection<PharmLigneRc> ligneRcs = commandeRapport.getPharmLigneRcs();
				listRapCommande = this.correspondance(ligneRcs);

				model.addAttribute("listRapCommande", this.traiterListConso(listRapCommande).values());
				model.addAttribute("var",1);
				model.addAttribute("tab",1);
				

			} else {
				// selectionner les commandes des sites
				ArrayList<PharmCommandeSite> consoSites = (ArrayList<PharmCommandeSite>) Context
						.getService(CommandeSiteService.class)
						.getPharmCommandeByPeriod(formulaireRapportCommande.getPharmProgramme(), maxDate,"VATR_NUR");
				ArrayList<PharmInventaire> inventaires = (ArrayList<PharmInventaire>) Context
						.getService(InventaireService.class)
						.getPharmInventaireByPeriod(formulaireRapportCommande.getPharmProgramme(), maxDate,true);
				
				List<Integer> arrayProg=new ArrayList<Integer>();//Pour la gestion des programmes NPSP
				arrayProg.add(3);arrayProg.add(4);arrayProg.add(8);		
				
				if (!(inventaires.isEmpty()) || 
						arrayProg.contains(formulaireRapportCommande.getPharmProgramme().getProgramId())) { // verifier s'il existe des rapports de consommation saisis
					boolean initStockVal=true;
					int sdu2=0;
					int sdu3=0;
					// créer l'entete du rapport commande
					commandeRapport = new PharmRapportCommande();
					commandeRapport.setPharmProgramme(formulaireRapportCommande.getPharmProgramme());
					/**************Gestion du code du site****************/
					Location defaultLocation = Context.getLocationService().getDefaultLocation();
					if (defaultLocation != null) {
						PharmSite site = Context.getService(SiteService.class)
								.getPharmSiteByCode(defaultLocation.getPostalCode());
						if(site!=null){
							site.setSitFlag("DA");//desactive le site
							Context.getService(SiteService.class).updatePharmSite(site);				
						}
						
					}
					commandeRapport.setRapComCode(defaultLocation.getPostalCode());
					commandeRapport.setRapComDateCom(new Date());
					commandeRapport.setRapComFalg(0);
					commandeRapport.setRapComPeriodDate(maxDate);
					commandeRapport.setRapComStockMax(4);

					formulaireRapportCommande.setRapportCommande(commandeRapport);
					//Context.getService(RapportCommandeService.class).savePharmRapportCommande(commandeRapport);

					/*************************
					 * creer les lignes du rapport commande à partir du stock
					 ****************************/
					listRapCommande = new ArrayList<RapportCommandeBean>();

					/*Collection listStocks = this.traiterListStock(Context.getService(GestionStockService.class)
							.getPharmStockersByProgram(formulaireRapportCommande.getPharmProgramme(),minDate, maxDate));
					System.out.println("listStocks : " +listStocks.size());*/
					
					Collection listProduits=formulaireRapportCommande.getPharmProgramme().getPharmProduits();
					Collection listOperation = Context.getService(OperationService.class)
							.getPharmOperationsByPeriod(formulaireRapportCommande.getPharmProgramme(), minDate, maxDate);
					//System.out.println("listOperation : "+listOperation.size());
					Iterator it = listProduits.iterator();
					while (it.hasNext()) {
						PharmProduit prod = (PharmProduit) it.next();						

						RapportCommandeBean commande = new RapportCommandeBean();

						int perte = 0;
						int qteUtil = 0;
						int qteRecu = 0;
						int stockDispo=0;
						int ajustement=0;
						int distribution=0;
						
						int input=0;
						
						Iterator itOp = listOperation.iterator();
						
						while (itOp.hasNext()) {
							
						PharmOperation operation = (PharmOperation) itOp.next();
							
						for (PharmLigneOperation ligneOperation : operation.getPharmLigneOperations()) {
							
							
							
							if (ligneOperation.getPharmProduitAttribut().getPharmProduit().getProdCode().equals(prod.getProdCode())) {
								input=1;
								switch (operation.getPharmTypeOperation().getToperId()) {
								case 3:
									ajustement = ajustement + ligneOperation.getLgnOperQte();
									break;
								case 4:
									ajustement = ajustement + ligneOperation.getLgnOperQte();
									break;
								case 5:
									ajustement = ajustement - ligneOperation.getLgnOperQte();
									break;
								case 13:
									ajustement = ajustement + ligneOperation.getLgnOperQte();
									break;
								case 14:
									ajustement = ajustement + ligneOperation.getLgnOperQte();
									break;
								case 15:
									ajustement = ajustement - ligneOperation.getLgnOperQte();
									break;
								case 16:
									ajustement = ajustement - ligneOperation.getLgnOperQte();
									break;
								case 17:
									ajustement = ajustement - ligneOperation.getLgnOperQte();
									break;
								case 18:
									ajustement = ajustement + ligneOperation.getLgnOperQte();
									break;
								
									
								case 1:
									qteRecu = qteRecu + ligneOperation.getLgnOperQte();
									break;
								case 2:
									qteUtil = qteUtil + ligneOperation.getLgnOperQte();
									break;
								
								case 6:
									perte = perte + ligneOperation.getLgnOperQte();
									break;
								case 7:
									perte = perte + ligneOperation.getLgnOperQte();
									break;
								case 10:
									perte = perte + ligneOperation.getLgnOperQte();
									break;
								case 11:
									perte = perte + ligneOperation.getLgnOperQte();
									break;
								case 12:
									perte = perte + ligneOperation.getLgnOperQte();
									break;
								/*case 9:
									distribution = distribution + ligneOperation.getLgnOperQte();
									break;*/
								case 19 :									
									qteUtil = qteUtil - ligneOperation.getLgnOperQte();
									break;
								/*case 8:
									stockDispo = stockDispo + ligneOperation.getLgnOperQte();//stock disponible du dernier inventaire
									break;*/
									/*
									 * case 9: qteUtil = qteUtil +
									 * ligneOperation.getLgnOperQte();
									 */

								}
							}
							
						}

						}
						
					
						
						if(input==1){//ajouter à la liste s'il existe une ligne du produit dans les opérations
						commande.setPertes(perte);
						commande.setQteRecu(qteRecu);
						commande.setQteUtil(qteUtil/*+distribution*/);
						//commande.setStockDispo(stockDispo);
						commande.setProduit(prod);
						commande.setAjustements(ajustement);
						// commande.setCmm(cmm);
						// commande.setMoisStock(moisStock);
						// commande.setNbrJrsRup(nbrJrsRup);
						// commande.setNbrSiteRup(nbrSiteRup);
						
						for (PharmLigneInventaire lgnInv : inventaires.get(0).getPharmLigneInventaires()) {
							if(lgnInv.getPharmProduitAttribut().getPharmProduit().getProdCode()==commande.getProduit().getProdCode()){
								
								lgnInv.setQtePro((lgnInv.getQtePro()==null)?0:lgnInv.getQtePro());//valeur null de lgnInv.getQtePro
								commande.setStockDispo(commande.getStockDispo()+lgnInv.getQtePro());
							}
						}
						
						//Gestion des programmes NPSP				
						//System.out.println("-----------------------arrayProg.contains(formulaireRapportCommande.getPharmProgramme().getProgramId())------------------"+arrayProg.contains(formulaireRapportCommande.getPharmProgramme().getProgramId()));
						if(arrayProg.contains(formulaireRapportCommande.getPharmProgramme().getProgramId())){
							Collection<PharmStocker> listStock=Context.getService(GestionStockService.class).getPharmStockersByPP(commande.getProduit(),
									formulaireRapportCommande.getPharmProgramme());
							int qteStock=0;
							for(PharmStocker lgnStock :listStock){
								qteStock=qteStock+lgnStock.getStockQte();
							}
							commande.setStockDispo(qteStock);
						}
						
						/***********determiner le stock initial*************/						
						
						Calendar calendar2 = new GregorianCalendar();
						calendar2.setTime(maxDate);
						calendar2.add(Calendar.MONTH, -1);
						//System.out.println("calendar2.getTime() :" + formatter.format(calendar2.getTime()));
						Date oldMax=calendar2.getTime();
						ArrayList<PharmRapportCommande> oldCommandes = (ArrayList<PharmRapportCommande>) Context
								.getService(RapportCommandeService.class)
								.getPharmRapportCommandeByPeriod(formulaireRapportCommande.getPharmProgramme(), oldMax);
						
						if(!oldCommandes.isEmpty()){
							initStockVal=false;
							Collection<RapportCommandeBean> rapport =this.correspondance(oldCommandes.get(0).getPharmLigneRcs());
							Iterator itRap = rapport.iterator();
							while (itRap.hasNext()) {
								RapportCommandeBean ligne = (RapportCommandeBean) itRap.next();
								if(ligne.getProduit().getProdCode()==commande.getProduit().getProdCode()){
									commande.setStockIni(ligne.getStockDispo());
									sdu2=ligne.getQteUtil();
								}
							}
						} else{
							ArrayList<PharmInventaire> inventairesOld = (ArrayList<PharmInventaire>) Context
									.getService(InventaireService.class)
									.getPharmInventaireByPeriod(formulaireRapportCommande.getPharmProgramme(), oldMax,true);
							//System.out.println("inventairesOld size :: "+ inventairesOld.size());
							if(!inventairesOld.isEmpty()){
								for (PharmLigneInventaire lgnInv : inventairesOld.get(0).getPharmLigneInventaires()) {
									if(lgnInv.getPharmProduitAttribut().getPharmProduit().getProdCode()==commande.getProduit().getProdCode()){
										commande.setStockIni(commande.getStockIni()+lgnInv.getQtePro());
										//System.out.println("-----------------------------------------------------------------------------");
										//System.out.println("InventaireOld : "+lgnInv.getQtePro()+" CODE : "+lgnInv.getPharmProduitAttribut().getPharmProduit().getProdCode());
									}
								}
							}
						}
						
						
						
						listRapCommande.add(commande);
						
						}
					}
						

						
					

					// Inserer les lignes de consommation
					Collection<RapportCommandeBean> listRapCommandeNew = new ArrayList<RapportCommandeBean>();
					Map<PharmProduit, RapportCommandeBean> listConso = traiterListConso(listRapCommande);

					for (RapportCommandeBean conso : listConso.values()) {

						// Inserer les lignes rapport de consommation
						PharmLigneRcId ligneCommandeId = new PharmLigneRcId();
						ligneCommandeId.setProdId(conso.getProduit().getProdId());
						//ligneCommandeId.setRapComId(commandeRapport.getRapComId());

						PharmLigneRc ligneCommande = new PharmLigneRc();
						ligneCommande.setId(ligneCommandeId);
						
						//System.out.println("-----------------------conso------------------"+conso.getProduit().getFullName()+" :"+conso.getQteUtil());
						// renseigner la quantité utilisée des PPS
						if(!(consoSites.isEmpty())){
							for (PharmCommandeSite consoSite : consoSites) {
								if(consoSite.getPharmSite().getSitFlag()==null)consoSite.getPharmSite().setSitFlag("");
								if(!consoSite.getPharmSite().getSitFlag().equals("DA")){
								Map<PharmProduit, PharmLigneCommandeSite> consoMap = this
										.transformToMap(consoSite.getPharmLigneCommandeSites());
								if (consoMap.containsKey(conso.getProduit())) {
									PharmLigneCommandeSite ligneConso = consoMap.get(conso.getProduit());
									int qteUtil = conso.getQteUtil();
									int stockIni = conso.getStockIni();
									int pertes = conso.getPertes();
									int stockDispo=conso.getStockDispo();
									conso.setQteUtil(qteUtil + ligneConso.getLgnComQteUtil());
									if(initStockVal)conso.setStockIni(stockIni+ligneConso.getLgnComQteIni());
									//System.out.println("-----------------------conso2------------------"+conso.getProduit().getFullName()+" :"+conso.getQteUtil());
									conso.setPertes(pertes+ligneConso.getLgnComPertes());
									conso.setStockDispo(stockDispo+ligneConso.getLgnComStockDispo());
								}
								
								}
								
							}
						
						}
						
						
						/*******************************************determiner la CMM***********************************/									
						Calendar calendar2 = new GregorianCalendar();
						calendar2.setTime(maxDate);
						
						calendar2.add(Calendar.MONTH, -2);
						Date date3=calendar2.getTime();

						//System.out.println("------------------date1 :" + formatter.format(maxDate));
						//System.out.println("------------------date2 :" + formatter.format(oldMax));
						//System.out.println("------------------date3 :" + formatter.format(date3));
						
					
						
						ArrayList<PharmRapportCommande> commande3 = (ArrayList<PharmRapportCommande>) Context
								.getService(RapportCommandeService.class)
								.getPharmRapportCommandeByPeriod(formulaireRapportCommande.getPharmProgramme(), date3);
						if(!commande3.isEmpty()){
							Collection<PharmLigneRc> rapport =commande3.get(0).getPharmLigneRcs();
							Iterator itRap = rapport.iterator();
							while (itRap.hasNext()) {
								PharmLigneRc ligne = (PharmLigneRc) itRap.next();
								if(ligne.getPharmProduit().getProdCode()==conso.getProduit().getProdCode()){
									sdu3=ligne.getLgnRcQteUtil();
								}
							}
						}
						conso.setQteCom(0);//Initialise à zero
						conso.setCmm(conso.getQteUtil());//reste tel si la cmm ne peux etre
						double som=(double)conso.getQteUtil()+sdu2+sdu3;
						if(sdu2!=0 && sdu3!=0 )conso.setCmm((int) Math.round(som/3d));
						double stockDispo=(double) conso.getStockDispo();
						double cmmDouble=(conso.getCmm()!=0) ?(double)conso.getCmm():0;
						if(conso.getCmm()!=0)conso.setMoisStock(this.arrondir((double)(stockDispo/cmmDouble),1));
						if(conso.getCmm()*4-conso.getStockDispo()>=0)conso.setQteCom(Math.round(conso.getCmm()*4-conso.getStockDispo()));
						
						
						/************mise à jour des lignes de commande************/
						ligneCommande.setLgnRcJrsRup(conso.getNbrJrsRup());
						ligneCommande.setLgnRcPerte(conso.getPertes());
						ligneCommande.setLgnRcStockIni(conso.getStockIni());
						ligneCommande.setLgnRcQteRecu(conso.getQteRecu());
						ligneCommande.setLgnRcQteUtil(conso.getQteUtil());
						ligneCommande.setLgnRcStockDispo(conso.getStockDispo());
						ligneCommande.setLgnRcAjust(conso.getAjustements());
						ligneCommande.setLgnRcConsoMoyenMens(conso.getCmm());
						ligneCommande.setLgnRcMoisStockDispo(conso.getMoisStock());
						ligneCommande.setLgnRcQteCom(conso.getQteCom());
						// ligneCommande.setLgnRcSiteRup(lgnRcSiteRup);
						ligneCommande.setPharmRapportCommande(commandeRapport);
						ligneCommande.setPharmProduit(conso.getProduit());
						//Context.getService(RapportCommandeService.class).savePharmLigneRc(ligneCommande);
						

						listRapCommandeNew.add(conso);
					}

					formulaireRapportCommande.setListRapCommande(this.traiterListConso(listRapCommandeNew));
					model.addAttribute("listRapCommande", this.traiterListConso(listRapCommandeNew).values());
					model.addAttribute("var",1);
					model.addAttribute("tab",2);

				}else {
					SimpleDateFormat formatter2 = new SimpleDateFormat(" MM-yyyy");
					model.addAttribute("var",0);
					model.addAttribute("mess",formatter2.format(formulaireRapportCommande.getDateCommande()));
				}
			}

		}

		// } catch (Exception e) {
		// e.getMessage();
		// }

	}
	
	
	public Double arrondir(Double d,int p){

		return (double) (Math.round(d * Math.pow(10,p))/Math.pow(10,p));
					
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/pharmagest/rapportCommande.form", method = RequestMethod.POST, params = { "btn_valder" })
	public void valider(@ModelAttribute("formulaireRapportCommande") FormulaireRapportCommande formulaireRapportCommande,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {


		if (!result.hasErrors()) {	
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			
			
			Collection<RapportCommandeBean> listRapCommandeNew = new ArrayList<RapportCommandeBean>();
			
			PharmRapportCommande commande=formulaireRapportCommande.getRapportCommande();
			Context.getService(RapportCommandeService.class).savePharmRapportCommande(commande);

			int taille = formulaireRapportCommande.getListRapCommande().values().size();
			for (int i = 1; i <= taille; i++) {
				//String ajust = request.getParameter("ajust" + i);
				//int ajustInt=Integer.parseInt(ajust);
				String siteRup = request.getParameter("siteRup" + i);
				int siteRupInt=Integer.parseInt(siteRup);
				String cmm = request.getParameter("cmm" + i);
				int cmmInt=Integer.parseInt(cmm);
				String moisStock = request.getParameter("moisStock" + i);
				double moisStockInt=(moisStock!=null) ? Double.parseDouble(moisStock) : 0 ;
				
				String qteACom = request.getParameter("qteACom" + i);
				int qteAComInt=(qteACom!=null) ? Integer.parseInt(qteACom) : 0 ;
				//System.out.println("qteACom :: "+qteACom);
				//System.out.println("qteACom :: "+qteAComInt);
				
				String idProd = request.getParameter("idProd" + i);
				int idProdInt=Integer.parseInt(idProd);
				
				//System.out.println("idProdInt ::"+idProdInt);
				
				PharmProduit produit=Context.getService(ProduitService.class).getPharmProduitById(idProdInt);
				//System.out.println("produit ::"+produit.getFullName());
				Map<PharmProduit, RapportCommandeBean> listCommande = formulaireRapportCommande.getListRapCommande();
				//System.out.println("listCommande ::"+listCommande.size());
				RapportCommandeBean rapportCommandeBean=new RapportCommandeBean();
				for ( Map.Entry<PharmProduit, RapportCommandeBean> entry : listCommande.entrySet()) {
					PharmProduit key = entry.getKey();
				    RapportCommandeBean val = entry.getValue();
				    if(key.getProdCode().equals(produit.getProdCode()))rapportCommandeBean=val;
				    //System.out.println("rapportCommandeBean ::"+rapportCommandeBean.getProduit().getFullName());
				    //System.out.println("rapportCommandeBean ::"+rapportCommandeBean.getCmm());
				}
				
				rapportCommandeBean.setCmm(cmmInt);
				//rapportCommandeBean.setAjustements(ajustInt);
				rapportCommandeBean.setNbrSiteRup(siteRupInt);
				rapportCommandeBean.setMoisStock(moisStockInt);
				rapportCommandeBean.setQteCom(qteAComInt);
				
				/***********inserer les lignes de commandes***********/
				PharmLigneRcId ligneCommandeId = new PharmLigneRcId();
				ligneCommandeId.setProdId(idProdInt);
				ligneCommandeId.setRapComId(commande.getRapComId());

				PharmLigneRc ligneCommande = new PharmLigneRc();
				ligneCommande.setId(ligneCommandeId);
				
				/************mise à jour des lignes de commande************/
				ligneCommande.setLgnRcJrsRup(rapportCommandeBean.getNbrJrsRup());
				ligneCommande.setLgnRcPerte(rapportCommandeBean.getPertes());
				ligneCommande.setLgnRcStockIni(rapportCommandeBean.getStockIni());
				ligneCommande.setLgnRcQteRecu(rapportCommandeBean.getQteRecu());
				ligneCommande.setLgnRcQteUtil(rapportCommandeBean.getQteUtil());
				ligneCommande.setLgnRcStockDispo(rapportCommandeBean.getStockDispo());
				ligneCommande.setLgnRcAjust(rapportCommandeBean.getAjustements());
				ligneCommande.setLgnRcConsoMoyenMens(rapportCommandeBean.getCmm());
				ligneCommande.setLgnRcMoisStockDispo(rapportCommandeBean.getMoisStock());
				ligneCommande.setLgnRcQteCom(rapportCommandeBean.getQteCom());
				ligneCommande.setLgnRcSiteRup(rapportCommandeBean.getNbrSiteRup());
				ligneCommande.setLgnRcAjust(rapportCommandeBean.getAjustements());
				ligneCommande.setLgnRcConsoMoyenMens(rapportCommandeBean.getCmm());
				ligneCommande.setLgnRcMoisStockDispo(rapportCommandeBean.getMoisStock());
				
				ligneCommande.setPharmRapportCommande(commande);
				ligneCommande.setPharmProduit(rapportCommandeBean.getProduit());
				Context.getService(RapportCommandeService.class).savePharmLigneRc(ligneCommande);
				listRapCommandeNew.add(rapportCommandeBean);
			}
			
			model.addAttribute("listRapCommande", listRapCommandeNew);
			model.addAttribute("var",1);
			model.addAttribute("tab",1);
			model.addAttribute("formulaireRapportCommande", formulaireRapportCommande);
			model.addAttribute("programmes", programmes);

		}

	}

	public Map<PharmProduit, RapportCommandeBean> traiterListConso(Collection<RapportCommandeBean> list) {

		Map<PharmProduit, RapportCommandeBean> listNew = Collections.synchronizedMap(new HashMap());

		Iterator it = list.iterator();
		while (it.hasNext()) {
			RapportCommandeBean commande = (RapportCommandeBean) it.next();

			if (listNew.containsKey(commande.getProduit())) {
				RapportCommandeBean consoVar = listNew.get(commande.getProduit());
				consoVar.setPertes(consoVar.getPertes() + commande.getPertes());
				consoVar.setNbrJrsRup(consoVar.getNbrJrsRup() + commande.getNbrJrsRup());
				consoVar.setMoisStock(consoVar.getMoisStock() + commande.getMoisStock());
				consoVar.setNbrSiteRup(consoVar.getNbrSiteRup() + commande.getNbrSiteRup());
				consoVar.setQteRecu(consoVar.getQteRecu() + commande.getQteRecu());
				consoVar.setQteUtil(consoVar.getQteUtil() + commande.getQteUtil());
				consoVar.setStockDispo(consoVar.getStockDispo() + commande.getStockDispo());
				consoVar.setStockIni(consoVar.getStockIni() + commande.getStockIni());
				consoVar.setProduit(commande.getProduit());
				consoVar.setCmm(consoVar.getCmm() + commande.getCmm());
				consoVar.setAjustements(consoVar.getAjustements() + commande.getAjustements());
				consoVar.setQteCom(consoVar.getQteCom()+commande.getQteCom());

				listNew.put(consoVar.getProduit(), consoVar);

			} else {

				listNew.put(commande.getProduit(), commande);
			}

		}
		
		//System.out.println("listNew : " + listNew.size());

		return listNew;
	}

	public Map<PharmProduit, PharmLigneCommandeSite> transformToMap(Set<PharmLigneCommandeSite> list) {
		Map<PharmProduit, PharmLigneCommandeSite> listNew = Collections.synchronizedMap(new HashMap());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PharmLigneCommandeSite conso = (PharmLigneCommandeSite) it.next();
			listNew.put(conso.getPharmProduit(), conso);
		}
		return listNew;

	}

	// faire corresponde lignecommande et ligne rapportconsoBean
	public Collection<RapportCommandeBean> correspondance(Collection<PharmLigneRc> ligneRs) {

		List<RapportCommandeBean> listRap = new ArrayList<RapportCommandeBean>();

		Iterator it1 = ligneRs.iterator();
		while (it1.hasNext()) {
			PharmLigneRc ligne = (PharmLigneRc) it1.next();
			RapportCommandeBean commandeBean = new RapportCommandeBean();
			commandeBean.setPertes(ligne.getLgnRcPerte());
			commandeBean.setNbrJrsRup(ligne.getLgnRcJrsRup());
			commandeBean.setMoisStock(ligne.getLgnRcMoisStockDispo());
			if (ligne.getLgnRcSiteRup() != null)
				commandeBean.setNbrSiteRup(ligne.getLgnRcSiteRup());
			commandeBean.setQteRecu(ligne.getLgnRcQteRecu());
			commandeBean.setQteUtil(ligne.getLgnRcQteUtil());
			commandeBean.setStockDispo(ligne.getLgnRcStockDispo());
			if (ligne.getLgnRcStockIni() != null)
				commandeBean.setStockIni(ligne.getLgnRcStockIni());
			commandeBean.setProduit(ligne.getPharmProduit());
			commandeBean.setCmm(ligne.getLgnRcConsoMoyenMens());
			commandeBean.setAjustements(ligne.getLgnRcAjust());
			commandeBean.setQteCom(ligne.getLgnRcQteCom());
			listRap.add(commandeBean);
		}
		//System.out.println("listRap : " + listRap.size());
		return listRap;
	}
	
	public Collection<PharmProduit> traiterListStock(Collection<PharmStocker> list){
		Map<String, PharmProduit> listNew = Collections.synchronizedMap(new HashMap());

		Iterator it= list.iterator();
		while (it.hasNext()) {
			PharmStocker stock = (PharmStocker) it.next();
			
			if(!listNew.containsKey(stock.getPharmProduitAttribut().getPharmProduit().getProdCode())){
				listNew.put(stock.getPharmProduitAttribut().getPharmProduit().getProdCode(), stock.getPharmProduitAttribut().getPharmProduit());
			}
		}
	
		
		return listNew.values();
		
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
