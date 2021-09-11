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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaireId;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireModifPPS;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireSaisiesPPS;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.LigneCommandeSite;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.metier.LigneInventaireMvt;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
import org.openmrs.module.pharmagest.validator.ModifPPSValidator;
import org.openmrs.module.pharmagest.validator.ModifPPSValidatorAjout;
import org.openmrs.module.pharmagest.validator.SaisiePPS2Validator;
import org.openmrs.module.pharmagest.validator.SaisiePPSValidator;
import org.openmrs.module.pharmagest.validator.SaisiePPSValidatorAjout;
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
@SessionAttributes("formulaireModif")

public class ModifierPPSController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ModifPPSValidator modifPPSValidator;
	@Autowired
	SaisiePPSValidator saisiePPSValidator;
	@Autowired
	ModifPPSValidatorAjout modifPPSValidatorAjout;
	@Autowired
	SaisiePPS2Validator saisiePPS2Validator;
	
	/**********************************************génerer la Liste************************************************/
	
	@RequestMapping(value = "/module/pharmagest/listPPSModification.form",method = RequestMethod.GET)
	public String initListPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listPPSModification";
	}

	@RequestMapping(value = "/module/pharmagest/listPPSModification.form",method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model) {

		modifPPSValidator.validate(formulaireModif, result);

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
			

		if (!result.hasErrors()) {
			
			Collection<PharmCommandeSite> commandeSites;
			if (formulaireModif.getDateParam() != null) {
				System.out.println("============Je suis 1===============");
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteByPeriod(
						formulaireModif.getSite(), formulaireModif.getProgramme(),
						formulaireModif.getDateParam(),"NV");
			} else {
				System.out.println("============Je suis 2===============");
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteBySP(
						formulaireModif.getSite(), formulaireModif.getProgramme(),"NV");
			}

			model.addAttribute("commandeSites", commandeSites);

		}

	}
	/*******************************************Entête de la modification**************************************************/
	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.GET, params = { "paramId" })
	public void initModif(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model) {
		formulaireModif=new FormulaireModifPPS();
		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {
			PharmCommandeSite commandeSite = Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteById(Integer.parseInt(paramId));
			formulaireModif.setCommandeSite(commandeSite);
			formulaireModif.setPharmProgramme(commandeSite.getPharmProgramme());
			Collection<LigneCommandeSite> ligneMvms = this.correspLigneCmdSiteToMvt(commandeSite.getPharmLigneCommandeSites(),commandeSite);
			
			Iterator it= ligneMvms.iterator();
			while (it.hasNext()) {
				LigneCommandeSite ligne= (LigneCommandeSite)it.next();
				formulaireModif.getTabSaisiesPPS().addLigneCommandeSite(ligne);
			}
			
			model.addAttribute("produits",
					this.transformToList(formulaireModif.getPharmProgramme().getPharmProduits()));
			model.addAttribute("formulaireModif", formulaireModif);
			model.addAttribute("ligneCommandeSites",formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			model.addAttribute("tab", "1");
		}

	}
	
	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.POST, params = { "btn_supprimer" })
	public String supprimer(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model) {
		
		Context.getService(CommandeSiteService.class).deletePharmCommandeSite(formulaireModif.getCommandeSite());
		
		this.initialisation(model);
		return "redirect:/module/pharmagest/listPPSModification.form";
			
	}

	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.POST, params = { "reset" })
	public String annuler(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model) {
		
		this.initialisation(model);
		return "redirect:/module/pharmagest/listPPSModification.form";
			
	}
	
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}
	
	
	
	

	/******************************************Ajouter et modifier les lignes du tableau***************************************************/
	
	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.POST, params = { "btn_ajouter" })
	public void addLigneDispensation(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model) {
		try {
			modifPPSValidatorAjout.validate(formulaireModif, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {

				LigneCommandeSite ligneCommandeSite = new LigneCommandeSite();
				ligneCommandeSite.setCommandeSite(formulaireModif.getCommandeSite());
				ligneCommandeSite.setProduit(formulaireModif.getProduit());
				ligneCommandeSite.setStockIni(formulaireModif.getLgnComQteIni());
				ligneCommandeSite.setQteRecu(formulaireModif.getLgnComQteRecu());
				ligneCommandeSite.setQteUtil(formulaireModif.getLgnComQteUtil());
				ligneCommandeSite.setQtePerte(formulaireModif.getLgnComPertes());
				ligneCommandeSite.setStockDispo(formulaireModif.getLgnComStockDispo());
				ligneCommandeSite.setNbrJourRuptur(formulaireModif.getLgnComNbreJrsRup());
				ligneCommandeSite.setQteDistri1(formulaireModif.getLgnQteDistriM1());
				ligneCommandeSite.setQteDistri2(formulaireModif.getLgnQteDistriM2());

				formulaireModif.getTabSaisiesPPS().addLigneCommandeSite(ligneCommandeSite);

				model.addAttribute("ligneCommandeSites",
						formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection());
				model.addAttribute("mess", "valid");

				formulaireModif.setProduit(null);
				formulaireModif.setLgnComQteIni(null);
				formulaireModif.setLgnComQteRecu(null);
				formulaireModif.setLgnComQteUtil(null);
				formulaireModif.setLgnComPertes(null);
				formulaireModif.setLgnComStockDispo(null);
				formulaireModif.setLgnComNbreJrsRup(null);
				formulaireModif.setLgnQteDistriM1(null);
				formulaireModif.setLgnQteDistriM2(null);

				model.addAttribute("formulaireModif", formulaireModif);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireModif.getPharmProgramme().getPharmProduits()));
				
				model.addAttribute("tab", "1");
			} else {
				formulaireModif.setProduit(null);
				formulaireModif.setLgnComQteIni(null);
				formulaireModif.setLgnComQteRecu(null);
				formulaireModif.setLgnComQteUtil(null);
				formulaireModif.setLgnComPertes(null);
				formulaireModif.setLgnComStockDispo(null);
				formulaireModif.setLgnComNbreJrsRup(null);
				formulaireModif.setLgnQteDistriM1(null);
				formulaireModif.setLgnQteDistriM2(null);

				model.addAttribute("formulaireModif", formulaireModif);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireModif.getPharmProgramme().getPharmProduits()));
				
				model.addAttribute("tab", "1");

				model.addAttribute("ligneCommandeSites",
						formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.GET, params = { "supprimId" })
	public void deleteLigneModif(@RequestParam(value = "supprimId") String supprimId,
			@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif, BindingResult result,
			HttpSession session, ModelMap model) {

		try {
			//saisiePPSValidator.validate(formulaireModif, result);
			List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {
				
				//delete ligne dans la base
				LigneCommandeSite ligneMvt=formulaireModif.getTabSaisiesPPS().getLigneCommandeSiteById(supprimId);
				
				PharmLigneCommandeSiteId pharmLigneCommandeId= new PharmLigneCommandeSiteId();
				pharmLigneCommandeId.setComSiteId(ligneMvt.getCommandeSite().getComSiteId());
				pharmLigneCommandeId.setProdId(ligneMvt.getProduit().getProdId());
				
				PharmLigneCommandeSite pharmLigneCommande= Context.getService(CommandeSiteService.class).getPharmLigneCommandeSite(pharmLigneCommandeId);
				
				if(pharmLigneCommande!=null)Context.getService(CommandeSiteService.class).deletePharmLigneCommandeSite(pharmLigneCommande);
			
				
				formulaireModif.getTabSaisiesPPS().removeLigneCommandeSiteById(supprimId);
				model.addAttribute("ligneCommandeSites",
						formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireModif", formulaireModif);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireModif.getPharmProgramme().getPharmProduits()));
				model.addAttribute("tab", "1");
				model.addAttribute("mess", "delete");
			} else {
				model.addAttribute("ligneCommandeSites",
						formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection());

				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireModif", formulaireModif);
				model.addAttribute("sites", sites);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireModif.getPharmProgramme().getPharmProduits()));
				model.addAttribute("tab", "1");
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	
	
	public Collection<LigneCommandeSite> correspLigneCmdSiteToMvt(Collection<PharmLigneCommandeSite> pharmligneCommandeSites , PharmCommandeSite commande){
		
		//====================== Gestion de la commande N1=================================
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(commande.getComSitePeriodDate());
		calendar.add(Calendar.MONTH, -1);
		Date moisN1=calendar.getTime();
		//System.out.println("==============Mois============="+df.format(moisN1));
		
		Collection<PharmCommandeSite> listCommandeN1 = Context.getService(CommandeSiteService.class).getPharmCommandeSiteByPeriod(
				commande.getPharmSite(), commande.getPharmProgramme(),moisN1,"NUR");
		
		PharmCommandeSite commandeN1=null;
		
		if(!listCommandeN1.isEmpty())commandeN1=new ArrayList<PharmCommandeSite>(listCommandeN1).get(0);
		
		Map<String, PharmLigneCommandeSite> mapMvmN1 = Collections.synchronizedMap(new HashMap());
		if(commandeN1!=null) {
			Iterator itN1= commandeN1.getPharmLigneCommandeSites().iterator();
			while (itN1.hasNext()) {
				PharmLigneCommandeSite ligne= (PharmLigneCommandeSite)itN1.next();
				mapMvmN1.put(ligne.getPharmProduit().getProdCode(),ligne);
			}
		}
		
		
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
			
			if(mapMvmN1.containsKey(ligneMvm.getProduit().getProdCode())) {
				PharmLigneCommandeSite lgN1=mapMvmN1.get(ligneMvm.getProduit().getProdCode());
				ligneMvm.setSduN1(lgN1.getLgnComStockDispo());
				ligneMvm.setQl(lgN1.getLgnQtePro());
			}
			
			listMvm.add(ligneMvm);
			
		}
		
		
		
		
		 return listMvm;
	}
	
	
	/*******************************************************************************************************/

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void save(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits",
				this.transformToList(formulaireModif.getPharmProgramme().getPharmProduits()));

		if (!result.hasErrors()) {

			
			int taille = formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection().size();
			for (int i = 1; i <= taille; i++) {
				

				String qteIni = request.getParameter("qteIni" + i);				
				int qteIniInt=(qteIni!=null && !(qteIni.equals(""))) ? Integer.parseInt(qteIni) : 0 ;				
				String qteRecu = request.getParameter("qteRecu" + i);				
				int qteRecuInt=(qteRecu!=null && !(qteRecu.equals(""))) ? Integer.parseInt(qteRecu) : 0 ;
				String qteUtil = request.getParameter("qteUtil" + i);				
				int qteUtilInt=(qteUtil!=null && !(qteUtil.equals(""))) ? Integer.parseInt(qteUtil) : 0 ;				
				String qtePerte = request.getParameter("qtePerte" + i);				
				int qtePerteInt=(qtePerte!=null && !(qtePerte.equals(""))) ? Integer.parseInt(qtePerte) : 0 ; 					
				String stockDispo = request.getParameter("stockDispo" + i);				
				int stockDispoInt=(stockDispo!=null && !(stockDispo.equals(""))) ? Integer.parseInt(stockDispo) : 0 ; 					
				String nbrJourRuptur = request.getParameter("nbrJourRuptur" + i);				
				int nbrJourRupturInt=(nbrJourRuptur!=null && !(nbrJourRuptur.equals(""))) ? Integer.parseInt(nbrJourRuptur) : 0 ; 					
				String qteDistri1 = request.getParameter("qteDistri1" + i);				
				int qteDistri1Int=(qteDistri1!=null && !(qteDistri1.equals(""))) ? Integer.parseInt(qteDistri1) : 0 ; 				
				String qteDistri2 = request.getParameter("qteDistri2" + i);				
				int qteDistri2Int=(qteDistri2!=null && !(qteDistri2.equals(""))) ? Integer.parseInt(qteDistri2) : 0 ;	
				String idProd = request.getParameter("idProd" + i);				
				
				LigneCommandeSite  ligneCommandeSite =formulaireModif.getTabSaisiesPPS().getLigneCommandeSiteById(idProd);
				//ligneCommandeSite.setCommandeSite(ligne.getPharmCommandeSite());
				//ligneMvm.setDatePerem(ligne.get);
				ligneCommandeSite.setNbrJourRuptur(nbrJourRupturInt);
				//ligneMvm.setNumLot(ligne.get);
				//ligneCommandeSite.setProduit(ligne.getPharmProduit());
				ligneCommandeSite.setQteDistri1(qteDistri1Int);
				ligneCommandeSite.setQteDistri2(qteDistri2Int);
				ligneCommandeSite.setQtePerte(qtePerteInt);
				ligneCommandeSite.setQteRecu(qteRecuInt);
				ligneCommandeSite.setQteUtil(qteUtilInt);
				ligneCommandeSite.setStockDispo(stockDispoInt);
				ligneCommandeSite.setStockIni(qteIniInt);
				
				
				
				
				PharmLigneCommandeSiteId pharmLigneCommandeId= new PharmLigneCommandeSiteId();
				pharmLigneCommandeId.setComSiteId(formulaireModif.getCommandeSite().getComSiteId());
				pharmLigneCommandeId.setProdId(ligneCommandeSite.getProduit().getProdId());
				
				
				PharmLigneCommandeSite pharmLigneCommande= Context.getService(CommandeSiteService.class)
						.getPharmLigneCommandeSite(pharmLigneCommandeId);
				//System.out.println("--------------pharmLigneCommande------- ::"+pharmLigneCommande.toString());
				
				if(pharmLigneCommande!=null){
				
				pharmLigneCommande.setId(pharmLigneCommandeId);
				pharmLigneCommande.setLgnComNbreJrsRup(ligneCommandeSite.getNbrJourRuptur());
				pharmLigneCommande.setLgnComPertes(ligneCommandeSite.getQtePerte());
				pharmLigneCommande.setLgnComQteIni(ligneCommandeSite.getStockIni());
				pharmLigneCommande.setLgnComQteRecu(ligneCommandeSite.getQteRecu());
				pharmLigneCommande.setLgnComQteUtil(ligneCommandeSite.getQteUtil());
				pharmLigneCommande.setLgnComStockDispo(ligneCommandeSite.getStockDispo());
				pharmLigneCommande.setLgnQteDistriM1(ligneCommandeSite.getQteDistri1());
				pharmLigneCommande.setLgnQteDistriM2(ligneCommandeSite.getQteDistri2());
				//pharmLigneCommande.setLgnQtePro(ligneCommandeSite.get);
				pharmLigneCommande.setPharmCommandeSite(ligneCommandeSite.getCommandeSite());
				pharmLigneCommande.setPharmProduit(ligneCommandeSite.getProduit());

				Context.getService(CommandeSiteService.class).updatePharmLigneCommandeSite(pharmLigneCommande);
				
				}else {
					pharmLigneCommande=new PharmLigneCommandeSite();
					pharmLigneCommande.setId(pharmLigneCommandeId);
					pharmLigneCommande.setLgnComNbreJrsRup(ligneCommandeSite.getNbrJourRuptur());
					pharmLigneCommande.setLgnComPertes(ligneCommandeSite.getQtePerte());
					pharmLigneCommande.setLgnComQteIni(ligneCommandeSite.getStockIni());
					pharmLigneCommande.setLgnComQteRecu(ligneCommandeSite.getQteRecu());
					pharmLigneCommande.setLgnComQteUtil(ligneCommandeSite.getQteUtil());
					pharmLigneCommande.setLgnComStockDispo(ligneCommandeSite.getStockDispo());
					pharmLigneCommande.setLgnQteDistriM1(ligneCommandeSite.getQteDistri1());
					pharmLigneCommande.setLgnQteDistriM2(ligneCommandeSite.getQteDistri2());
					//pharmLigneCommande.setLgnQtePro(ligneCommandeSite.get);
					pharmLigneCommande.setPharmCommandeSite(ligneCommandeSite.getCommandeSite());
					pharmLigneCommande.setPharmProduit(ligneCommandeSite.getProduit());

					Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(pharmLigneCommande);
					
				}

				
			}

			PharmCommandeSite commandeSite = Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteById(formulaireModif.getCommandeSite().getComSiteId());
			model.addAttribute("ligneCommandeSites",
					formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection());
			
			model.addAttribute("tab", "1");
			model.addAttribute("mess", "save");

		}

	}
	
	@RequestMapping(value = "/module/pharmagest/modifierPPS.form",method = RequestMethod.POST, params = { "btn_valider" })
	public void valider(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
		
		//mettre à jour la commande
		PharmCommandeSite commande=Context.getService(CommandeSiteService.class).getPharmCommandeSiteById(formulaireModif.getCommandeSite().getComSiteId());
		commande.setComSiteFlag(1);
		if(commande!=null)Context.getService(CommandeSiteService.class).updatePharmCommandeSite(commande);

		if (!result.hasErrors()) {

			
			int taille = formulaireModif.getTabSaisiesPPS().getLigneCommandeSitesCollection().size();
			for (int i = 1; i <= taille; i++) {
				

				String qteIni = request.getParameter("qteIni" + i);				
				int qteIniInt=(qteIni!=null && !(qteIni.equals(""))) ? Integer.parseInt(qteIni) : 0 ;				
				String qteRecu = request.getParameter("qteRecu" + i);				
				int qteRecuInt=(qteRecu!=null && !(qteRecu.equals(""))) ? Integer.parseInt(qteRecu) : 0 ;
				String qteUtil = request.getParameter("qteUtil" + i);				
				int qteUtilInt=(qteUtil!=null && !(qteUtil.equals(""))) ? Integer.parseInt(qteUtil) : 0 ;				
				String qtePerte = request.getParameter("qtePerte" + i);				
				int qtePerteInt=(qtePerte!=null && !(qtePerte.equals(""))) ? Integer.parseInt(qtePerte) : 0 ; 					
				String stockDispo = request.getParameter("stockDispo" + i);				
				int stockDispoInt=(stockDispo!=null && !(stockDispo.equals(""))) ? Integer.parseInt(stockDispo) : 0 ; 					
				String nbrJourRuptur = request.getParameter("nbrJourRuptur" + i);				
				int nbrJourRupturInt=(nbrJourRuptur!=null && !(nbrJourRuptur.equals(""))) ? Integer.parseInt(nbrJourRuptur) : 0 ; 					
				String qteDistri1 = request.getParameter("qteDistri1" + i);				
				int qteDistri1Int=(qteDistri1!=null && !(qteDistri1.equals(""))) ? Integer.parseInt(qteDistri1) : 0 ; 				
				String qteDistri2 = request.getParameter("qteDistri2" + i);				
				int qteDistri2Int=(qteDistri2!=null && !(qteDistri2.equals(""))) ? Integer.parseInt(qteDistri2) : 0 ; 	
				String idProd = request.getParameter("idProd" + i);				
				
				LigneCommandeSite  ligneCommandeSite =formulaireModif.getTabSaisiesPPS().getLigneCommandeSiteById(idProd);
				//ligneCommandeSite.setCommandeSite(ligne.getPharmCommandeSite());
				//ligneMvm.setDatePerem(ligne.get);
				ligneCommandeSite.setNbrJourRuptur(nbrJourRupturInt);
				//ligneMvm.setNumLot(ligne.get);
				//ligneCommandeSite.setProduit(ligne.getPharmProduit());
				ligneCommandeSite.setQteDistri1(qteDistri1Int);
				ligneCommandeSite.setQteDistri2(qteDistri2Int);
				ligneCommandeSite.setQtePerte(qtePerteInt);
				ligneCommandeSite.setQteRecu(qteRecuInt);
				ligneCommandeSite.setQteUtil(qteUtilInt);
				ligneCommandeSite.setStockDispo(stockDispoInt);
				ligneCommandeSite.setStockIni(qteIniInt);
				
				PharmLigneCommandeSiteId pharmLigneCommandeId= new PharmLigneCommandeSiteId();
				pharmLigneCommandeId.setComSiteId(formulaireModif.getCommandeSite().getComSiteId());
				pharmLigneCommandeId.setProdId(ligneCommandeSite.getProduit().getProdId());
				
				PharmLigneCommandeSite pharmLigneCommande= Context.getService(CommandeSiteService.class)
						.getPharmLigneCommandeSite(pharmLigneCommandeId);
				
				if(pharmLigneCommande!=null){
					
					pharmLigneCommande.setId(pharmLigneCommandeId);
					pharmLigneCommande.setLgnComNbreJrsRup(ligneCommandeSite.getNbrJourRuptur());
					pharmLigneCommande.setLgnComPertes(ligneCommandeSite.getQtePerte());
					pharmLigneCommande.setLgnComQteIni(ligneCommandeSite.getStockIni());
					pharmLigneCommande.setLgnComQteRecu(ligneCommandeSite.getQteRecu());
					pharmLigneCommande.setLgnComQteUtil(ligneCommandeSite.getQteUtil());
					pharmLigneCommande.setLgnComStockDispo(ligneCommandeSite.getStockDispo());
					pharmLigneCommande.setLgnQteDistriM1(ligneCommandeSite.getQteDistri1());
					pharmLigneCommande.setLgnQteDistriM2(ligneCommandeSite.getQteDistri2());
					//pharmLigneCommande.setLgnQtePro(ligneCommandeSite.get);
					pharmLigneCommande.setPharmCommandeSite(ligneCommandeSite.getCommandeSite());
					pharmLigneCommande.setPharmProduit(ligneCommandeSite.getProduit());

					Context.getService(CommandeSiteService.class).updatePharmLigneCommandeSite(pharmLigneCommande);
					
					}else {
						pharmLigneCommande=new PharmLigneCommandeSite();
						pharmLigneCommande.setId(pharmLigneCommandeId);
						pharmLigneCommande.setLgnComNbreJrsRup(ligneCommandeSite.getNbrJourRuptur());
						pharmLigneCommande.setLgnComPertes(ligneCommandeSite.getQtePerte());
						pharmLigneCommande.setLgnComQteIni(ligneCommandeSite.getStockIni());
						pharmLigneCommande.setLgnComQteRecu(ligneCommandeSite.getQteRecu());
						pharmLigneCommande.setLgnComQteUtil(ligneCommandeSite.getQteUtil());
						pharmLigneCommande.setLgnComStockDispo(ligneCommandeSite.getStockDispo());
						pharmLigneCommande.setLgnQteDistriM1(ligneCommandeSite.getQteDistri1());
						pharmLigneCommande.setLgnQteDistriM2(ligneCommandeSite.getQteDistri2());
						//pharmLigneCommande.setLgnQtePro(ligneCommandeSite.get);
						pharmLigneCommande.setPharmCommandeSite(ligneCommandeSite.getCommandeSite());
						pharmLigneCommande.setPharmProduit(ligneCommandeSite.getProduit());

						Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(pharmLigneCommande);
						
					}

				
			}

			PharmCommandeSite commandeSite = Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteById(formulaireModif.getCommandeSite().getComSiteId());
			model.addAttribute("pharmLigneCommandeSites", commandeSite.getPharmLigneCommandeSites());
			model.addAttribute("tab", "2");
			model.addAttribute("mess", "valid");
			model.addAttribute("validAt","disabled");
		}

	}

	public void initialisation(ModelMap model) {
		FormulaireModifPPS formulaireModif = new FormulaireModifPPS();
		model.addAttribute("formulaireModif", formulaireModif);
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
		model.addAttribute("var", "0");
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
