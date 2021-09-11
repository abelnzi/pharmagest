package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.LigneCommandeSite;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.validator.ModifPPSValidatorAjout;
import org.openmrs.module.pharmagest.validator.TraitementPPSValidator;
import org.openmrs.module.pharmagest.validator.TraitementPPSValidatorAjout;
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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("formulaireTraitementsPPS")
//@RequestMapping(value = "/module/pharmagest/traitementsPPS.form")
public class _TraitementsPPSController {

	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	TraitementPPSValidatorAjout traitementPPSValidatorAjout;
	@Autowired
	TraitementPPSValidator traitementPPSValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String initSaisiesPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/traitementsPPS";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void recherche(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {
			PharmCommandeSite commandeSite = Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteById(Integer.parseInt(paramId));
			formulaireTraitementsPPS.setCommandeSite(commandeSite);
			Collection<LigneCommandeSite> ligneMvms = this.correspLigneCmdSiteToMvt(commandeSite.getPharmLigneCommandeSites());
			Iterator it= ligneMvms.iterator();
			while (it.hasNext()) {
				LigneCommandeSite ligne= (LigneCommandeSite)it.next();
				//Trouver le stock du produit
				Integer totalstock=0;
				Collection<PharmStocker> stocks=Context.getService(GestionStockService.class).getPharmStockersByProduit(ligne.getProduit());
				for (PharmStocker stock : stocks) {
					totalstock=totalstock+stock.getStockQte();
				}
				ligne.setSdu(totalstock);
				formulaireTraitementsPPS.getTabSaisiesPPS().addLigneCommandeSite(ligne);
			}
			
			model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
			model.addAttribute("commandeSite",commandeSite);
			model.addAttribute("produits",
					this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
			model.addAttribute("ligneCommandeSites",formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			model.addAttribute("tab", "1");
		}

	}
	
	
	@RequestMapping(method = RequestMethod.POST, params = { "btn_ajouter" })
	public void addLigneDispensation(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
		    traitementPPSValidatorAjout.validate(formulaireTraitementsPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();

			if (!result.hasErrors()) {
				
								
				PharmLigneCommandeSite ligne=Context.getService(CommandeSiteService.class).getPharmLigneCommandeSite(
						new PharmLigneCommandeSiteId(formulaireTraitementsPPS.getProduit().getProdId(),formulaireTraitementsPPS.getCommandeSite().getComSiteId()));
				
				if(ligne==null){									
					LigneCommandeSite ligneCommandeSite = new LigneCommandeSite();
					ligneCommandeSite.setCommandeSite(formulaireTraitementsPPS.getCommandeSite());
					ligneCommandeSite.setProduit(formulaireTraitementsPPS.getProduit());
					ligneCommandeSite.setStockIni(formulaireTraitementsPPS.getLgnComQteIni());
					ligneCommandeSite.setQteRecu(formulaireTraitementsPPS.getLgnComQteRecu());
					ligneCommandeSite.setQteUtil(formulaireTraitementsPPS.getLgnComQteUtil());
					ligneCommandeSite.setQtePerte(formulaireTraitementsPPS.getLgnComPertes());
					ligneCommandeSite.setStockDispo(formulaireTraitementsPPS.getLgnComStockDispo());
					ligneCommandeSite.setNbrJourRuptur(formulaireTraitementsPPS.getLgnComNbreJrsRup());
					ligneCommandeSite.setQteDistri1(formulaireTraitementsPPS.getLgnQteDistriM1());
					ligneCommandeSite.setQteDistri2(formulaireTraitementsPPS.getLgnQteDistriM2());
					//Trouver le stock du produit
					Integer totalstock=0;
					Collection<PharmStocker> stocks=Context.getService(GestionStockService.class).getPharmStockersByProduit(ligneCommandeSite.getProduit());
					for (PharmStocker stock : stocks) {
						totalstock=totalstock+stock.getStockQte();
					}
					ligneCommandeSite.setSdu(totalstock);
					
					formulaireTraitementsPPS.getTabSaisiesPPS().addLigneCommandeSite(ligneCommandeSite);
					model.addAttribute("mess", "valid");
				}else{
					model.addAttribute("mess", "noValid");
				}
				

				

				model.addAttribute("ligneCommandeSites",
						formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
				

				formulaireTraitementsPPS.setProduit(null);
				formulaireTraitementsPPS.setLgnComQteIni(null);
				formulaireTraitementsPPS.setLgnComQteRecu(null);
				formulaireTraitementsPPS.setLgnComQteUtil(null);
				formulaireTraitementsPPS.setLgnComPertes(null);
				formulaireTraitementsPPS.setLgnComStockDispo(null);
				formulaireTraitementsPPS.setLgnComNbreJrsRup(null);
				formulaireTraitementsPPS.setLgnQteDistriM1(null);
				formulaireTraitementsPPS.setLgnQteDistriM2(null);

				model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
				model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
				
				model.addAttribute("tab", "1");
			} else {
				formulaireTraitementsPPS.setProduit(null);
				formulaireTraitementsPPS.setLgnComQteIni(null);
				formulaireTraitementsPPS.setLgnComQteRecu(null);
				formulaireTraitementsPPS.setLgnComQteUtil(null);
				formulaireTraitementsPPS.setLgnComPertes(null);
				formulaireTraitementsPPS.setLgnComStockDispo(null);
				formulaireTraitementsPPS.setLgnComNbreJrsRup(null);
				formulaireTraitementsPPS.setLgnQteDistriM1(null);
				formulaireTraitementsPPS.setLgnQteDistriM2(null);

				model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
				model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
				
				model.addAttribute("tab", "1");

				model.addAttribute("ligneCommandeSites",
						formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			}

		//} catch (Exception e) {
		//	e.printStackTrace();
		//}

	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
	public void modifLigneOperation(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS, BindingResult result,
			HttpSession session, ModelMap model) {

		//try {
			//saisiePPSValidator.validate(formulaireTraitementsPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();

			if (!result.hasErrors()) {
				
				int proID=(modifId!=null)?Integer.parseInt(modifId):0;
				
				PharmLigneCommandeSite ligneCS=Context.getService(CommandeSiteService.class).getPharmLigneCommandeSite(
						new PharmLigneCommandeSiteId(proID,formulaireTraitementsPPS.getCommandeSite().getComSiteId()));
				//System.out.println("-----------------------proID--------------"+proID);
				//System.out.println("-------------------formulaireTraitementsPPS.getCommandeSite().getComSiteId()------------------"+formulaireTraitementsPPS.getCommandeSite().getComSiteId());
				//System.out.println("----------------ligneCS---------------------"+ligneCS);
				if(ligneCS==null){
					LigneCommandeSite  ligne=formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSiteById(modifId);
					formulaireTraitementsPPS.setLgnComNbreJrsRup(ligne.getNbrJourRuptur());
					formulaireTraitementsPPS.setLgnComPertes(ligne.getQtePerte());
					formulaireTraitementsPPS.setLgnComQteIni(ligne.getStockIni());
					formulaireTraitementsPPS.setLgnComQteRecu(ligne.getQteRecu());
					formulaireTraitementsPPS.setLgnComQteUtil(ligne.getQteUtil());
					formulaireTraitementsPPS.setLgnComStockDispo(ligne.getStockDispo());
					formulaireTraitementsPPS.setLgnQteDistriM1(ligne.getQteDistri1());
					formulaireTraitementsPPS.setLgnQteDistriM2(ligne.getQteDistri2());
					formulaireTraitementsPPS.setProduit(ligne.getProduit());
					
					formulaireTraitementsPPS.getTabSaisiesPPS().removeLigneCommandeSiteById(modifId);
					model.addAttribute("mess", "modif");
				}else{
					model.addAttribute("mess", "noModif");
				}
				
				model.addAttribute("ligneCommandeSites",
						formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				
				
				model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
				model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("tab", "1");
			} else {
				model.addAttribute("ligneCommandeSites",
						formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
				model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("tab", "1");
			}

		//} catch (Exception e) {
		//	e.getMessage();
		//}

	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "supprimId" })
	public void deleteLigneOperation(@RequestParam(value = "supprimId") String supprimId,
			@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS, BindingResult result,
			HttpSession session, ModelMap model) {

		try {
			//saisiePPSValidator.validate(formulaireTraitementsPPS, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			if (!result.hasErrors()) {
				int proID=(supprimId!=null)?Integer.parseInt(supprimId):0;
				PharmLigneCommandeSite ligneCS=Context.getService(CommandeSiteService.class).getPharmLigneCommandeSite(
						new PharmLigneCommandeSiteId(proID,formulaireTraitementsPPS.getCommandeSite().getComSiteId()));
				if(ligneCS==null){
					formulaireTraitementsPPS.getTabSaisiesPPS().removeLigneCommandeSiteById(supprimId);
					model.addAttribute("mess", "delete");
				}else{
					model.addAttribute("mess", "noDelete");
				}
				model.addAttribute("ligneCommandeSites",
						formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				
				model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
				model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("tab", "1");
			} else {
				model.addAttribute("ligneCommandeSites",
						formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
				model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
				model.addAttribute("tab", "1");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	
	
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}
	
	public Collection<LigneCommandeSite> correspLigneCmdSiteToMvt(Collection<PharmLigneCommandeSite> pharmligneCommandeSites ){
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
			listMvm.add(ligneMvm);
			//System.out.println("-----------------------------------------------------------------");
			//System.out.println("Dateperempt :: "+ ligneMvm.getLgnDatePerem());
		}
		 return listMvm;
	}

	

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = { "btn_valder" })
	public void valider(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
		traitementPPSValidator.validate(formulaireTraitementsPPS, result);
		
		if (!result.hasErrors()) {

			// save operation
			PharmOperation operation = new PharmOperation();
			
			//gestion des distribution pour commande urgente
			/*PharmTypeOperation typeOperation=Context.getService(ParametresService.class).getTypeOperationById(17);			
			if(typeOperation==null)Context.getService(ParametresService.class).
												saveTypeOperation(new PharmTypeOperation(17,"Distribution pour commande urgente",false,null));*/
			
			operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(9));
			
			operation.setOperDateRecept(formulaireTraitementsPPS.getDateParam());
			operation.setOperDateSaisi(new Date());
			// Gestionnaire
			PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
			gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
			gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
			gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
			Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
			//date de traitement
			String dateTraitementString = request.getParameter("dateTraitement");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						
			operation.setOperDateRecept(formulaireTraitementsPPS.getDateParam());
			
			operation.setPharmGestionnairePharma(gestionnairePharma);
			operation.setPharmProgramme(formulaireTraitementsPPS.getCommandeSite().getPharmProgramme());
			operation.setOperRef(formulaireTraitementsPPS.getCommandeSite().getComSiteId()+"");
			Context.getService(OperationService.class).savePharmOperation(operation);
			
			// update commandeSite
			
			 formulaireTraitementsPPS.getCommandeSite().setComSiteFlag(2);
			 Context.getService(CommandeSiteService.class).updatePharmCommandeSite(formulaireTraitementsPPS.getCommandeSite());
			 
			//créer l'entete de distribution 
			 PharmReception reception = new PharmReception();				
			 PharmTypeOperation typeOperation = Context.getService(ParametresService.class).getTypeOperationById(9);
			 reception.setPharmTypeOperation(typeOperation);
			 reception.setPharmGestionnairePharma(gestionnairePharma);
			 reception.setPharmProgramme(formulaireTraitementsPPS.getCommandeSite().getPharmProgramme());
			 reception.setRecptFlag("V");
			 Context.getService(GestionReceptionService.class).savePharmReception(reception);
			 
			 
			boolean boucle = true;
			int i = 1;
			int taille = formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection().size();
			//System.out.println("----------------------taille------------------ " + taille);
			while(boucle && i <= taille){
			
				String idProd = request.getParameter("idProd" + i);
				String qtePro = request.getParameter("qtePro" + i);
				//System.out.println("qtePro : " + qtePro);
				//System.out.println("idProd : " + idProd);
				// double d=Double.parseDouble(qtePro);
				// int qteProInt= (int) Math.ceil(d);
				
				int proID=(idProd!=null)?Integer.parseInt(idProd):0;

				PharmLigneCommandeSiteId pharmLigneCommandeSiteId = new PharmLigneCommandeSiteId();

				pharmLigneCommandeSiteId.setProdId(proID);
				pharmLigneCommandeSiteId.setComSiteId(formulaireTraitementsPPS.getCommandeSite().getComSiteId());

				PharmLigneCommandeSite param = Context.getService(CommandeSiteService.class)
						.getPharmLigneCommandeSite(pharmLigneCommandeSiteId);
				if(param!=null){
					param.setLgnQtePro(Integer.parseInt(qtePro));
					Context.getService(CommandeSiteService.class).updatePharmLigneCommandeSite(param);
				} /*else {
					LigneCommandeSite  ligneCommandeSite =formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSiteById(idProd);
					
					param=new PharmLigneCommandeSite();
					param.setId(pharmLigneCommandeSiteId);
					param.setLgnComNbreJrsRup(ligneCommandeSite.getNbrJourRuptur());
					param.setLgnComPertes(ligneCommandeSite.getQtePerte());
					param.setLgnComQteIni(ligneCommandeSite.getStockIni());
					param.setLgnComQteRecu(ligneCommandeSite.getQteRecu());
					param.setLgnComQteUtil(ligneCommandeSite.getQteUtil());
					param.setLgnComStockDispo(ligneCommandeSite.getStockDispo());
					param.setLgnQteDistriM1(ligneCommandeSite.getQteDistri1());
					param.setLgnQteDistriM2(ligneCommandeSite.getQteDistri2());
					param.setLgnQtePro(Integer.parseInt(qtePro));
					param.setPharmCommandeSite(ligneCommandeSite.getCommandeSite());
					param.setPharmProduit(ligneCommandeSite.getProduit());

					Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(param);
				}*/

				// contrôle du Stock
				PharmProduit produit=formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSiteById(idProd).getProduit();
				//System.out.println("-------------produit------------------------"+produit.getFullName());
				List<PharmStocker> stockerList = (ArrayList<PharmStocker>) Context.getService(GestionStockService.class)
						.getPharmStockersByPP(produit,
								formulaireTraitementsPPS.getCommandeSite().getPharmProgramme());
				
				PharmHistoMouvementStock histoMouvementStock=null;
				
				int qteStock = 0;
				if (!stockerList.isEmpty()) {
					Collections.sort(stockerList);
					
					for (PharmStocker pharmStocker : stockerList) {
						qteStock = qteStock + pharmStocker.getStockQte();
					} 
				}

					Integer qte = Integer.parseInt(qtePro);

					if (qteStock >= qte) {// verifier si la quantité en stock
											// est suffisante

						//System.out.println("---------------------jsuis là 0-----------");
						
						Integer qteTotalServi = 0;
						boolean condition = true;
						Iterator it = stockerList.iterator();

						while (it.hasNext() && condition == true) {
							PharmStocker stock = (PharmStocker) it.next();
							if (stock.getStockQte() >= qte) {
								//System.out.println("---------------------jsuis là 1-----------");
								// gestion des lignes d'opération
								PharmLigneOperationId ligneOperationId = new PharmLigneOperationId();
								ligneOperationId.setOperId(operation.getOperId());
								ligneOperationId.setProdAttriId(stock.getPharmProduitAttribut().getProdAttriId());
								PharmLigneOperation ligneOperation = new PharmLigneOperation();
								ligneOperation.setLgnOperQte(qte);
								ligneOperation.setPharmOperation(operation);
								ligneOperation.setPharmProduitAttribut(stock.getPharmProduitAttribut());
								ligneOperation.setId(ligneOperationId);
								Context.getService(OperationService.class).savePharmLigneOperation(ligneOperation);

								// Gestion stock
								Integer stockQte = stock.getStockQte() - qte;
								stock.setStockQte(stockQte);
								stock.setStockDateStock(formulaireTraitementsPPS.getCommandeSite().getComSiteDateCom());
								Context.getService(GestionStockService.class).updatePharmStocker(stock);

								// insertion dans histoMvm
								histoMouvementStock = new PharmHistoMouvementStock();
								histoMouvementStock
										.setMvtDate(formulaireTraitementsPPS.getCommandeSite().getComSiteDateCom());
								histoMouvementStock.setMvtLot(stock.getPharmProduitAttribut().getProdLot());
								histoMouvementStock.setMvtProgramme(
										formulaireTraitementsPPS.getCommandeSite().getPharmProgramme().getProgramId());
								histoMouvementStock.setMvtQte(qte);
								histoMouvementStock.setMvtQteStock(stock.getStockQte());
								histoMouvementStock.setMvtTypeMvt(operation.getPharmTypeOperation().getToperId());
								histoMouvementStock.setPharmProduit(stock.getPharmProduitAttribut().getPharmProduit());
								histoMouvementStock.setPharmOperation(operation);
								Context.getService(GestionStockService.class)
										.savePharmHistoMvmStock(histoMouvementStock);
								
								// save PharmligneReception
								PharmLigneReception ld = new PharmLigneReception();
								PharmLigneReceptionId ldId = new PharmLigneReceptionId();
								ldId.setRecptId(reception.getRecptId());
								ldId.setProdAttriId(stock.getPharmProduitAttribut().getProdAttriId());
								ld.setId(ldId);
								ld.setPharmReception(reception);
								ld.setPharmProduitAttribut(stock.getPharmProduitAttribut());								
								ld.setLgnRecptQteDetailRecu(qte);								
								ld.setLgnRecptObserv("Distribution");
								Context.getService(GestionReceptionService.class).savePharmLigneReception(ld);	

								qteTotalServi = qteTotalServi + qte;
								condition = false;

							} else {
								//System.out.println("---------------------jsuis là 2-----------");
								// Gestion stock
								
								stock.setStockQte(stock.getStockQte());
								stock.setStockDateStock(formulaireTraitementsPPS.getCommandeSite().getComSiteDateCom());
								Context.getService(GestionStockService.class).updatePharmStocker(stock);
								
								// gestion des lignes d'opération
								PharmLigneOperationId ligneOperationId = new PharmLigneOperationId();
								ligneOperationId.setOperId(operation.getOperId());
								ligneOperationId.setProdAttriId(stock.getPharmProduitAttribut().getProdAttriId());
								PharmLigneOperation ligneOperation = new PharmLigneOperation();
								ligneOperation.setLgnOperQte(qte);
								ligneOperation.setPharmOperation(operation);
								ligneOperation.setPharmProduitAttribut(stock.getPharmProduitAttribut());
								ligneOperation.setId(ligneOperationId);
								Context.getService(OperationService.class).savePharmLigneOperation(ligneOperation);

								// insertion dans histoMvm
								histoMouvementStock = new PharmHistoMouvementStock();
								histoMouvementStock
										.setMvtDate(formulaireTraitementsPPS.getCommandeSite().getComSiteDateCom());
								histoMouvementStock.setMvtLot(stock.getPharmProduitAttribut().getProdLot());
								histoMouvementStock.setMvtProgramme(
										formulaireTraitementsPPS.getCommandeSite().getPharmProgramme().getProgramId());
								histoMouvementStock.setMvtQte(stock.getStockQte());
								histoMouvementStock.setMvtQteStock(stock.getStockQte());
								histoMouvementStock.setMvtTypeMvt(operation.getPharmTypeOperation().getToperId());
								histoMouvementStock.setPharmProduit(stock.getPharmProduitAttribut().getPharmProduit());
								histoMouvementStock.setPharmOperation(operation);
								Context.getService(GestionStockService.class)
										.savePharmHistoMvmStock(histoMouvementStock);
								
								// save PharmligneReception
								PharmLigneReception ld = new PharmLigneReception();
								PharmLigneReceptionId ldId = new PharmLigneReceptionId();
								ldId.setRecptId(reception.getRecptId());
								ldId.setProdAttriId(stock.getPharmProduitAttribut().getProdAttriId());
								ld.setId(ldId);
								ld.setPharmReception(reception);
								ld.setPharmProduitAttribut(stock.getPharmProduitAttribut());								
								ld.setLgnRecptQteDetailRecu(qte);								
								ld.setLgnRecptObserv("Distribution");
								Context.getService(GestionReceptionService.class).savePharmLigneReception(ld);	

								qteTotalServi = qteTotalServi + qte;
								qte = qte - stock.getStockQte();
							}
						}
						model.addAttribute("tab", "2");
						LigneCommandeSite  ligneCommandeSite =formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSiteById(idProd);
						ligneCommandeSite.setQtePro(Integer.parseInt(qtePro));
						formulaireTraitementsPPS.getTabSaisiesPPS().addLigneCommandeSite(ligneCommandeSite);
					} else {
						boucle=false;
						
						formulaireTraitementsPPS.getCommandeSite().setComSiteFlag(1);
						Context.getService(CommandeSiteService.class).updatePharmCommandeSite(formulaireTraitementsPPS.getCommandeSite());
						//System.out.println("-----------histoMouvementStock-------------" + histoMouvementStock);
						//System.out.println("---------------operation-----------------------" + operation.getOperId());
						//System.out.println("---------------reception-----------------------" + reception.getRecptId());
						
						Context.getService(GestionStockService.class).deletePharmHistoMvmStockByOperId(operation.getOperId());
						Context.getService(OperationService.class).deletePharmLigneOperationsByOperation(operation);
						Context.getService(OperationService.class).deletePharmOperation(operation);
						Context.getService(GestionReceptionService.class).deletePharmLigneReceptionsByReception(reception);
						Context.getService(GestionReceptionService.class).deletePharmReception(reception);
						
						 
						model.addAttribute("mess", "refuse");
						String info="La quantité "+qte+" de "+produit.getFullName()+" proposée est supérieur au stock de "+ qteStock +" disponible " ;
						model.addAttribute("info", info);
						/*model.addAttribute("ligneCommandeSites",
								formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
						model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);*/
						
						model.addAttribute("tab", "1");
					}

				
				
				i++;
				//System.out.println("Voici la ligne " + i);
			}

			PharmCommandeSite commandeSite = Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteById(formulaireTraitementsPPS.getCommandeSite().getComSiteId());
			model.addAttribute("commandeSite", commandeSite);
			model.addAttribute("dateTraitement", formatter.format(formulaireTraitementsPPS.getDateParam()));
			model.addAttribute("produits",
					this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
			model.addAttribute("ligneCommandeSites",
					formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
			

		}else{
			model.addAttribute("ligneCommandeSites",
					formulaireTraitementsPPS.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
			model.addAttribute("produits",
					this.transformToList(formulaireTraitementsPPS.getPharmProgramme().getPharmProduits()));
			model.addAttribute("commandeSite",formulaireTraitementsPPS.getCommandeSite());
			model.addAttribute("tab", "1");
		}

	}

	public void initialisation(ModelMap model) {
		FormulaireTraitementsPPS formulaireTraitementsPPS = new FormulaireTraitementsPPS();
		model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		// formulaireTraitementsPPS.setPharmGestionnairePharma(gestionnairePharma);
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
