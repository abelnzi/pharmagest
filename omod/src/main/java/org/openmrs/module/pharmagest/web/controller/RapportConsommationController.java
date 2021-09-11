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

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneOperation;
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
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireRapportCommande;
import org.openmrs.module.pharmagest.metier.FormulaireSite;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.metier.RapportConsoBean;
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
@SessionAttributes("formulaireProgramme")
public class RapportConsommationController {



	@RequestMapping(value = "/module/pharmagest/rapportConso.form", method = RequestMethod.GET)
	public String initRapportConso(ModelMap model) {
		FormulaireProgramme formulaireProgramme = new FormulaireProgramme();
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
		
		model.addAttribute("formulaireProgramme", formulaireProgramme);
		model.addAttribute("programmes", programmes);

		return "/module/pharmagest/rapportConso";
	}

	@RequestMapping(value = "/module/pharmagest/rapportConso.form", method = RequestMethod.POST, params = {
			"btn_rechercher" })
	public void rapportConsommation(@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme,
			BindingResult result, HttpSession session, ModelMap model) throws ParseException {
		// try {
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("formulaireProgramme", formulaireProgramme);
		model.addAttribute("programmes", programmes);
		SimpleDateFormat sf = new SimpleDateFormat("MM-yyyy");
		model.addAttribute("dateMois", "du mois de : "+sf.format(formulaireProgramme.getDateConso()));

		// Gestion du critère de date
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = new Date();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(formulaireProgramme.getDateConso());
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

		if (!result.hasErrors()) {

			// verifier si la commande existe
			Location defaultLocation = Context.getLocationService().getDefaultLocation();
			if (defaultLocation != null) {
				PharmSite site = Context.getService(SiteService.class)
						.getPharmSiteByCode(defaultLocation.getPostalCode());
				if (site == null) {
					site = new PharmSite();
					site.setSitCode(defaultLocation.getPostalCode());
					site.setSitLib(defaultLocation.getName());
					Context.getService(SiteService.class).savePharmSite(site);
				}

				ArrayList<PharmCommandeSite> commandes = (ArrayList<PharmCommandeSite>) Context
						.getService(CommandeSiteService.class)
						.getPharmCommandeSiteByPeriod(site, formulaireProgramme.getPharmProgramme(), maxDate,"VA");
				PharmCommandeSite commandeSite = null;
				Collection<RapportConsoBean> listRapConso = null;

				if (!(commandes.isEmpty())) {

					commandeSite = commandes.get(0);
					System.out.println("commandeSite : " + commandeSite.getComSitePeriodDate().toString());
					Collection<PharmLigneCommandeSite> ligneCommandeSites = commandeSite.getPharmLigneCommandeSites();
					listRapConso = this.correspondance(ligneCommandeSites);
					formulaireProgramme.setListConso(this.traiterListConso(listRapConso));
					model.addAttribute("listRapConso", formulaireProgramme.getListConso());
					model.addAttribute("tab", "1");
					model.addAttribute("var", "2");
					//System.out.println("formulaireProgramme.getListConso()" + formulaireProgramme.getListConso().size());

				} else {
					
					ArrayList<PharmInventaire> inventaires = (ArrayList<PharmInventaire>) Context
							.getService(InventaireService.class)
							.getPharmInventaireByPeriod(formulaireProgramme.getPharmProgramme(), maxDate,true);
					ArrayList<PharmCommandeSite> consoSites = (ArrayList<PharmCommandeSite>) Context
							.getService(CommandeSiteService.class)
							.getPharmCommandeByPeriod(formulaireProgramme.getPharmProgramme(), maxDate,"VA");//prendre en compte les dotations aux services
					
					if ( !(inventaires.isEmpty())) { // verifier s'il existe un inventaire du mois saisi
					
					boolean initStockVal=true;
					
					// Inserer le rapport commande
					commandeSite = new PharmCommandeSite();
					commandeSite.setPharmProgramme(formulaireProgramme.getPharmProgramme());
					commandeSite.setPharmSite(site);
					commandeSite.setComSiteCode("");
					commandeSite.setComSiteDateCom(new Date());
					commandeSite.setComSitePeriodDate(maxDate);
					// commandeSite.setComSitePeriodLib(comSitePeriodLib);
					commandeSite.setComSiteFlag(0);
					commandeSite.setComStockMax(2);
					formulaireProgramme.setCommandeSite(commandeSite);
					//Context.getService(CommandeSiteService.class).savePharmCommandeSite(commandeSite);

					/*************************
					 * creer les lignes de consommation à partir du stock
					 ****************************/
					listRapConso = new ArrayList<RapportConsoBean>();
					Collection listProduits=formulaireProgramme.getPharmProgramme().getPharmProduits();
					//Collection listStocks = this.traiterListStock(Context.getService(GestionStockService.class).getPharmStockersByProgram(formulaireProgramme.getPharmProgramme(),minDate, maxDate));

					Iterator it = listProduits.iterator();
					while (it.hasNext()) {
						PharmProduit prod = (PharmProduit) it.next();

						Collection listOperation = Context.getService(OperationService.class)
								.getPharmOperationsByPeriod(formulaireProgramme.getPharmProgramme(), minDate, maxDate);

						RapportConsoBean consoBean = new RapportConsoBean();

						int perte = 0;
						int qteUtil = 0;
						int qteRecu = 0;
						int qteStock=0;
						
						int input=0;

						Iterator itOp = listOperation.iterator();

						while (itOp.hasNext()) {
							PharmOperation operation = (PharmOperation) itOp.next();
						
							for (PharmLigneOperation ligneOperation : operation.getPharmLigneOperations()) {

							if (ligneOperation.getPharmProduitAttribut().getPharmProduit().getProdCode().equals(prod.getProdCode())) {
								input=1;
								switch (operation.getPharmTypeOperation().getToperId()) {
								case 1:
									qteRecu = qteRecu + ligneOperation.getLgnOperQte();
									break;
								case 2:
									qteUtil = qteUtil + ligneOperation.getLgnOperQte();
									break;
								/*case 8:
									qteStock = qteStock + ligneOperation.getLgnOperQte();
									break;*/
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
								case 19 :									
									qteUtil = qteUtil - ligneOperation.getLgnOperQte();
									break;
								}
							}
							
						 }

						}
						
						
						
						//System.out.println("-----------------------------------------qteRecu ::"+qteRecu);
						
						if(input==1){

						consoBean.setPertes(perte);
						// consoBean.setNbrJrsRup(nbrJrsRup);
						// consoBean.setQteDistM1(qteDistM1);
						// consoBean.setQteDistM2(qteDistM2);
						consoBean.setQteRecu(qteRecu);
						consoBean.setQteUtil(qteUtil);
						//consoBean.setStockDispo(qteStock);
						// consoBean.setStockIni(stock.getStockQteIni());
						consoBean.setProduit(prod);
						
						
						for (PharmLigneInventaire lgnInv : inventaires.get(0).getPharmLigneInventaires()) {
							if(lgnInv.getPharmProduitAttribut().getPharmProduit().getProdCode()==consoBean.getProduit().getProdCode()){
								consoBean.setStockDispo(consoBean.getStockDispo()+lgnInv.getQtePro());
							}
						}
						
						
						/***********determiner le stock initial*************/
						Calendar calendar2 = new GregorianCalendar();
						calendar2.setTime(maxDate);
						calendar2.add(Calendar.MONTH, -1);
						Date oldMax=calendar2.getTime();
						
						/***********determiner le stock initial*************/
						
						ArrayList<PharmInventaire> inventairesOld = (ArrayList<PharmInventaire>) Context
								.getService(InventaireService.class)
								.getPharmInventaireByPeriod(formulaireProgramme.getPharmProgramme(), oldMax,true);
						if(!inventairesOld.isEmpty()){
							for (PharmLigneInventaire lgnInv : inventairesOld.get(0).getPharmLigneInventaires()) {
								if(lgnInv.getPharmProduitAttribut().getPharmProduit().getProdCode()==consoBean.getProduit().getProdCode()){
									consoBean.setStockIni(consoBean.getStockIni()+lgnInv.getQtePro());
									//System.out.println("-----------------------------------------------------------------------------");
									//System.out.println("InventaireOld : "+lgnInv.getQtePro()+" CODE : "+lgnInv.getPharmProduitAttribut().getPharmProduit().getProdCode());
								}
							}
						}
						
						/***********determiner le stock initial*************/
						ArrayList<PharmCommandeSite> oldRapport =  (ArrayList<PharmCommandeSite>) Context
								.getService(CommandeSiteService.class).getPharmCommandeSiteByPeriod(site, formulaireProgramme.getPharmProgramme(), oldMax,"VA");
						
						if(!oldRapport.isEmpty()){
							initStockVal=false;
							model.addAttribute("attribut", "disabled");
							for (PharmLigneCommandeSite ligne: oldRapport.get(0).getPharmLigneCommandeSites()) {
								if(ligne.getPharmProduit().getProdCode()==consoBean.getProduit().getProdCode()){
									consoBean.setQteDistM1(ligne.getLgnComQteUtil());
									consoBean.setQteDistM2(ligne.getLgnQteDistriM1());
									//System.out.println("-----------------------------------------------------------------------------");
									//System.out.println("oldRapport : "+ligne.getLgnComQteUtil());
								}
							}
						}
						
						// renseigner la quantité utilisée des PPS
						if(!(consoSites.isEmpty())){
							for (PharmCommandeSite consoSite : consoSites) {
								if(consoSite.getPharmSite().getSitFlag()==null)consoSite.getPharmSite().setSitFlag("");
								if(!consoSite.getPharmSite().getSitFlag().equals("DA")){
								Map<PharmProduit, PharmLigneCommandeSite> consoMap = this
										.transformToMap(consoSite.getPharmLigneCommandeSites());
								if (consoMap.containsKey(consoBean.getProduit())) {
									PharmLigneCommandeSite ligneConso = consoMap.get(consoBean.getProduit());
									int qteUtilse = consoBean.getQteUtil();
									int stockIni = consoBean.getStockIni();
									int pertes = consoBean.getPertes();
									int stockDispo=consoBean.getStockDispo();
									consoBean.setQteUtil(qteUtilse + ligneConso.getLgnComQteUtil());
									if(initStockVal)consoBean.setStockIni(stockIni+ligneConso.getLgnComQteIni());
									//System.out.println("initStockVal Boolean : "+initStockVal);
									consoBean.setPertes(pertes+ligneConso.getLgnComPertes());
									consoBean.setStockDispo(stockDispo+ligneConso.getLgnComStockDispo());
								}
								
								}
								
							}
						
						}
						
						
						
						
						
						listRapConso.add(consoBean);

						}
					}

					
					Collection<RapportConsoBean> listConso = this.traiterListConso(listRapConso);
					
					// Inserer les lignes de consommation
					/*
					Iterator itC = listConso.iterator();
					while (itC.hasNext()) {
						RapportConsoBean conso = (RapportConsoBean) itC.next();

						// Inserer les lignes rapport de consommation
						PharmLigneCommandeSiteId commandeSiteId = new PharmLigneCommandeSiteId();
						commandeSiteId.setProdId(conso.getProduit().getProdId());
						commandeSiteId.setComSiteId(commandeSite.getComSiteId());

						PharmLigneCommandeSite ligneCommandeSite = new PharmLigneCommandeSite();
						ligneCommandeSite.setId(commandeSiteId);
						ligneCommandeSite.setLgnComNbreJrsRup(conso.getNbrJrsRup());
						ligneCommandeSite.setLgnComPertes(conso.getPertes());
						ligneCommandeSite.setLgnComQteIni(conso.getStockIni());
						ligneCommandeSite.setLgnComQteRecu(conso.getQteRecu());
						ligneCommandeSite.setLgnComQteUtil(conso.getQteUtil());
						ligneCommandeSite.setLgnComStockDispo(conso.getStockDispo());
						ligneCommandeSite.setLgnQteDistriM1(conso.getQteDistM1());
						ligneCommandeSite.setLgnQteDistriM2(conso.getQteDistM2());
						// ligneCommandeSite.setLgnQtePro();
						ligneCommandeSite.setPharmCommandeSite(commandeSite);
						ligneCommandeSite.setPharmProduit(conso.getProduit());
						Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(ligneCommandeSite);

					}*/

					formulaireProgramme.setListConso(listConso);
					model.addAttribute("listRapConso", listConso);
					model.addAttribute("tab", "2");
					model.addAttribute("var", "2");
					
				}else {// pas d'inventaire
					SimpleDateFormat formatter2 = new SimpleDateFormat("MM-yyyy");
					model.addAttribute("var","0");
					model.addAttribute("mess",formatter2.format(formulaireProgramme.getDateConso()));
				}

				}
				

			}else{// pas de default location
				
				model.addAttribute("var","1");
				model.addAttribute("mess","indiquer la localite par defaut");
				
			}

		}

		// } catch (Exception e) {
		// e.getMessage();
		// }

	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/pharmagest/rapportConso.form", method = RequestMethod.POST, params = { "btn_valder" })
	public void valider(@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		

		if (!result.hasErrors()) {	
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			model.addAttribute("formulaireProgramme", formulaireProgramme);
			model.addAttribute("programmes", programmes);
			
			
			Collection<RapportConsoBean> listRapConsoNew = new ArrayList<RapportConsoBean>();
			
			PharmCommandeSite commandeSite=formulaireProgramme.getCommandeSite();
			commandeSite.setComSiteFlag(1);
			Context.getService(CommandeSiteService.class).savePharmCommandeSite(commandeSite);
			
			Map<String, RapportConsoBean> listConso = this.transformToMap(formulaireProgramme.getListConso());
			System.out.println("listConso ::"+listConso.size());

			int taille = formulaireProgramme.getListConso().size();
			for (int i = 1; i <= taille; i++) {
				String nbrJrsRup = request.getParameter("nbrJrsRup" + i);
				int nbrJrsRupInt=Integer.parseInt(nbrJrsRup);
				String qteDistM1 = request.getParameter("qteDistM1" + i);
				//int qteDistM1Int=Integer.parseInt(qteDistM1);
				int qteDistM1Int = (qteDistM1!=null) ? Integer.parseInt(qteDistM1) : 0 ;
				String qteDistM2 = request.getParameter("qteDistM2" + i);
				//int qteDistM2Int=Integer.parseInt(qteDistM2);
				int qteDistM2Int = (qteDistM2!=null) ? Integer.parseInt(qteDistM2) : 0 ;
				
				String idProd = request.getParameter("idProd" + i);
				int idProdInt=Integer.parseInt(idProd);
				
				//System.out.println("idProdInt ::"+idProdInt);
				
				PharmProduit produit=Context.getService(ProduitService.class).getPharmProduitById(idProdInt);
				//System.out.println("produit ::"+produit.getFullName());
				
								
				
				RapportConsoBean rapportConsoBean=listConso.get(produit.getProdCode());
				//System.out.println("RapportConsobean :: "+listConso.get(produit.getProdCode()).toString());
				
				if(rapportConsoBean!=null){
					rapportConsoBean.setNbrJrsRup(nbrJrsRupInt);
					rapportConsoBean.setQteDistM1(qteDistM1Int);
					rapportConsoBean.setQteDistM2(qteDistM2Int);
					
					/***********inserer les lignes du rapport***********/
					PharmLigneCommandeSiteId ligneCommandeSiteId = new PharmLigneCommandeSiteId();
					ligneCommandeSiteId.setProdId(idProdInt);
					ligneCommandeSiteId.setComSiteId(commandeSite.getComSiteId());

					PharmLigneCommandeSite ligneCommandeSite = new PharmLigneCommandeSite();
					ligneCommandeSite.setId(ligneCommandeSiteId);
					
					ligneCommandeSite.setLgnComNbreJrsRup(rapportConsoBean.getNbrJrsRup());
					ligneCommandeSite.setLgnComPertes(rapportConsoBean.getPertes());
					ligneCommandeSite.setLgnComQteIni(rapportConsoBean.getStockIni());
					ligneCommandeSite.setLgnComQteRecu(rapportConsoBean.getQteRecu());
					ligneCommandeSite.setLgnComQteUtil(rapportConsoBean.getQteUtil());
					ligneCommandeSite.setLgnComStockDispo(rapportConsoBean.getStockDispo());
					ligneCommandeSite.setLgnQteDistriM1(rapportConsoBean.getQteDistM1());
					ligneCommandeSite.setLgnQteDistriM2(rapportConsoBean.getQteDistM2());
					//ligneCommandeSite.setLgnQtePro(rapportConsoBean.get);
					ligneCommandeSite.setPharmProduit(rapportConsoBean.getProduit());
					
					Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(ligneCommandeSite);
					
					listRapConsoNew.add(rapportConsoBean);
				}

			}
			
			model.addAttribute("listRapConso", listRapConsoNew);
			model.addAttribute("tab", "1");
			model.addAttribute("var", "2");

		}

	}
	
	public Map<String, RapportConsoBean> transformToMap(Collection<RapportConsoBean> list) {
		Map<String, RapportConsoBean> listNew = Collections.synchronizedMap(new HashMap());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			RapportConsoBean conso = (RapportConsoBean) it.next();
			listNew.put(conso.getProduit().getProdCode(), conso);
			//System.out.println("---------------------------------------------------");
			//System.out.println("conso :: "+conso.getProduit().getFullName());
		}
		return listNew;

	}


	public Collection<RapportConsoBean> traiterListConso(Collection<RapportConsoBean> list) {

		Map<PharmProduit, RapportConsoBean> listNew = Collections.synchronizedMap(new HashMap());

		Iterator it = list.iterator();
		while (it.hasNext()) {
			RapportConsoBean conso = (RapportConsoBean) it.next();

			if (listNew.containsKey(conso.getProduit())) {
				RapportConsoBean consoVar = listNew.get(conso.getProduit());
				consoVar.setPertes(consoVar.getPertes() + conso.getPertes());
				// consoNew.setNbrJrsRup(nbrJrsRup);
				// consoNew.setQteDistM1(qteDistM1);
				// consoNew.setQteDistM2(qteDistM2);
				consoVar.setQteRecu(consoVar.getQteRecu() + conso.getQteRecu());
				consoVar.setQteUtil(consoVar.getQteUtil() + conso.getQteUtil());
				consoVar.setStockDispo(consoVar.getStockDispo() + conso.getStockDispo());
				// consoNew.setStockIni(stock.getStockQteIni());
				consoVar.setProduit(conso.getProduit());
				listNew.put(consoVar.getProduit(), consoVar);

			} else {

				listNew.put(conso.getProduit(), conso);
			}

		}

		return listNew.values();
	}

	@RequestMapping(value = "/module/pharmagest/rapportConso.form", method = RequestMethod.POST, params = {
			"print_pdf" })
	public ModelAndView pdfRapportConsommation(
			@ModelAttribute("formulaireProgramme") FormulaireProgramme formulaireProgramme, BindingResult result,
			HttpSession session, ModelMap model) {
		System.out.println("formulaireProgramme.getListConso()" + formulaireProgramme.getListConso().size());
		List<RapportConsoBean> listRapConso = new ArrayList<RapportConsoBean>(formulaireProgramme.getListConso());
		return new ModelAndView("consoPdfView", "listRapConso", listRapConso);
	}

	@RequestMapping(value = "/module/pharmagest/rapportConso.form", method = RequestMethod.POST, params = {
			"print_excel" })
	public ModelAndView excelRapportConsommation() { // create some sample data
		List<Book> listBooks = new ArrayList<Book>();
		listBooks.add(new Book("Effective Java", "Joshua Bloch", "0321356683", "May 28, 2008", 38.11F));
		listBooks.add(
				new Book("Head First Java", "Kathy Sierra & Bert Bates", "0596009208", "February 9, 2005", 30.80F));
		listBooks.add(new Book("Java Generics and Collections", "Philip Wadler", "0596527756", "Oct 24, 2006", 29.52F));
		listBooks.add(new Book("Thinking in Java", "Bruce Eckel", "0596527756", "February 20, 2006", 43.97F));
		listBooks.add(new Book("Spring in Action", "Craig Walls", "1935182358", "June 29, 2011", 31.98F));

		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "listBooks", listBooks);
	}

	// faire corresponde lignecommande et ligne rapportconsoBean
	public Collection<RapportConsoBean> correspondance(Collection<PharmLigneCommandeSite> ligneCommandeSites) {

		List<RapportConsoBean> listRapConso = new ArrayList<RapportConsoBean>();

		Iterator it1 = ligneCommandeSites.iterator();
		while (it1.hasNext()) {
			PharmLigneCommandeSite ligne = (PharmLigneCommandeSite) it1.next();
			RapportConsoBean consoBean = new RapportConsoBean();
			consoBean.setPertes(ligne.getLgnComPertes());
			// consoBean.setNbrJrsRup(nbrJrsRup);
			// consoBean.setQteDistM1(qteDistM1);
			// consoBean.setQteDistM2(qteDistM2);
			consoBean.setQteRecu(ligne.getLgnComQteRecu());
			consoBean.setQteUtil(ligne.getLgnComQteUtil());
			consoBean.setStockDispo(ligne.getLgnComStockDispo());
			// consoBean.setStockIni(stock.getStockQteIni());
			consoBean.setProduit(ligne.getPharmProduit());

			listRapConso.add(consoBean);

		}
		System.out.println("listRapConso : " + listRapConso.size());
		return listRapConso;
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
	
	public Map<PharmProduit, PharmLigneCommandeSite> transformToMap(Set<PharmLigneCommandeSite> list) {
		Map<PharmProduit, PharmLigneCommandeSite> listNew = Collections.synchronizedMap(new HashMap());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PharmLigneCommandeSite conso = (PharmLigneCommandeSite) it.next();
			listNew.put(conso.getPharmProduit(), conso);
		}
		return listNew;

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
