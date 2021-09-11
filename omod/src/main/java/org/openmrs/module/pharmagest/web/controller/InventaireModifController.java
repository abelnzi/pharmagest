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
import org.openmrs.module.pharmagest.PharmFournisseur;
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
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.CoutAchatProduit;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.metier.LigneDispensationMvt;
import org.openmrs.module.pharmagest.metier.LigneInventaireMvt;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.validator.Inventaire2Validator;
import org.openmrs.module.pharmagest.validator.InventaireAjoutValidator;
import org.openmrs.module.pharmagest.validator.InventaireValidator;
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
@SessionAttributes("formulaireInventaire")

public class InventaireModifController {
	
	@Autowired
	InventaireValidator inventaireValidator;

	@Autowired
	Inventaire2Validator inventaire2Validator;

	@Autowired
	InventaireAjoutValidator inventaireAjoutValidator;
	
	/**********************************************Trouver la liste d'inventaire****************************************************************/
	@RequestMapping(value = "/module/pharmagest/listInventaireModif.form", method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listInventaireModif";
	}

	@RequestMapping(value = "/module/pharmagest/listInventaireModif.form",method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {

		//listRPPSValidator.validate(formulaireTraitementsPPS, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {

			Collection<PharmInventaire> inventaires;
			if (formulaireInventaire.getDateParam() != null) {
				inventaires = Context.getService(InventaireService.class).getPharmInventaireByPeriod(
						formulaireInventaire.getPharmProgramme(), formulaireInventaire.getDateParam(),false);
				//System.out.println(" mois inventaire :"+formulaireInventaire.getDateParam().toString());
			} else {
				inventaires = Context.getService(InventaireService.class).getPharmInventaireByProgram(formulaireInventaire.getPharmProgramme());
			}

			model.addAttribute("inventaires", inventaires);
			if(inventaires.isEmpty())model.addAttribute("mess", "vide");

		}

	}
	/**********************************************modifier l'Entête ****************************************************************/

	@RequestMapping(value = "/module/pharmagest/inventaireModif.form",method = RequestMethod.GET, params = { "paramId" })
	public String initModif(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
		
		formulaireInventaire = new FormulairePharmInventaire();
		
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		formulaireInventaire.setGestionnairePharma(gestionnairePharma);
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		model.addAttribute("var", "0");
		
	
		PharmInventaire inventaire = Context.getService(InventaireService.class)
					.getPharmInventaireById(Integer.parseInt(paramId));
		formulaireInventaire.setPharmInventaire(inventaire);
		formulaireInventaire.setPharmProgramme(inventaire.getPharmProgramme());
		model.addAttribute("formulaireInventaire", formulaireInventaire);	
		
		return "/module/pharmagest/inventaireModif";
	}
	
	@RequestMapping(value = "/module/pharmagest/inventaireSupprim.form",method = RequestMethod.GET, params = { "paramId" })
	public String supprimer(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
			
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("formulaireInventaire", formulaireInventaire);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		model.addAttribute("var", "0");	
		
		PharmInventaire inventaire = Context.getService(InventaireService.class)
					.getPharmInventaireById(Integer.parseInt(paramId));
		if(inventaire!=null){
			for (PharmLigneInventaire ligne : inventaire.getPharmLigneInventaires()) {
				Context.getService(InventaireService.class).deletePharmLigneInventaire(ligne);
			}
			Context.getService(InventaireService.class).deletePharmInventaire(inventaire);
		}
		
		
		
		return "/module/pharmagest/listInventaireModif";
	}

	@RequestMapping(value = "/module/pharmagest/inventaireModif.form",method = RequestMethod.POST, params = { "reset" })
	public String annuler(
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("formulaireInventaire", formulaireInventaire);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		model.addAttribute("var", "0");	
		
		return "/module/pharmagest/listInventaireModif";
	}
	
	public int calculTotalPrix(Map map){
		int total=0;
		Map lignes = map;
		for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
			Object key = i.next();			
			LigneInventaireMvt ligne = (LigneInventaireMvt) lignes.get(key);
			if(ligne.getPrixInv()==null)ligne.setPrixInv(0);
			if(ligne.getQtePhysique()==null)ligne.setQtePhysique(0);
			total=total+ ligne.getPrixInv()*ligne.getQtePhysique();				   	
		}
		return total;
	}

	@RequestMapping(value = "/module/pharmagest/inventaireModif.form",method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		inventaire2Validator.validate(formulaireInventaire, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		Date invDev = formulaireInventaire.getInvDeb();
		
		// formulaireInventaire.setInvDeb(new Date(invDev));
		if (!result.hasErrors()) {
			//gère l'erreur de session et créer les lignes du tableau
			    formulaireInventaire.setPharmInventaire(Context.getService(InventaireService.class).getPharmInventaireById(formulaireInventaire.getPharmInventaire().getInvId()));
			    formulaireInventaire.setInvDeb(invDev);
			    formulaireInventaire.setPharmProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme());
			    model.addAttribute("formulaireInventaire", formulaireInventaire);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));
				
				
				Collection<LigneInventaireMvt> ligneMvms = this.correspLigneInvToMvt(formulaireInventaire.getPharmInventaire().getPharmLigneInventaires());
				
				Iterator it= ligneMvms.iterator();
				while (it.hasNext()) {
					LigneInventaireMvt ligne= (LigneInventaireMvt)it.next();
					formulaireInventaire.getTabInventaire().addLigneInventaire(ligne);
				}
				
				model.addAttribute("ligneInventaires", formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
				model.addAttribute("var", "1");
				model.addAttribute("tab", "1");
				model.addAttribute("totalGlobal",this.calculTotalPrix(formulaireInventaire.getTabInventaire().getLigneInventaires()));
			
		}

	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}
	public Collection<LigneInventaireMvt> correspLigneInvToMvt(Collection<PharmLigneInventaire> ligneInventaires ){
		List<LigneInventaireMvt> listMvm = new ArrayList<LigneInventaireMvt>();

		Iterator it= ligneInventaires.iterator();
		while (it.hasNext()) {
			PharmLigneInventaire ligne= (PharmLigneInventaire)it.next();
			LigneInventaireMvt ligneMvm = new LigneInventaireMvt();
			ligneMvm.setEcart(ligne.getEcart());
			ligneMvm.setLgnDatePerem(ligne.getPharmProduitAttribut().getProdDatePerem());
			ligneMvm.setLgnLot(ligne.getPharmProduitAttribut().getProdLot());
			ligneMvm.setObservation(ligne.getObservation());
			ligneMvm.setProduit(ligne.getPharmProduitAttribut().getPharmProduit());
			ligneMvm.setQteLogique(ligne.getQteLogique());
			ligneMvm.setQtePhysique(ligne.getQtePhysique());
			ligneMvm.setPrixInv(ligne.getPrixInv());
			listMvm.add(ligneMvm);
			//System.out.println("-----------------------------------------------------------------");
			//System.out.println("Dateperempt :: "+ ligneMvm.getLgnDatePerem());
		}
		 return listMvm;
	}
	
	
	/**********************************************modifier les lignes ****************************************************************/
	
	@RequestMapping(value = "/module/pharmagest/inventaireModif.form",method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model,HttpServletRequest request) {
		try {
			inventaireAjoutValidator.validate(formulaireInventaire, result);
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();

			// model.addAttribute("formulaireInventaire", formulaireInventaire);
			// model.addAttribute("programmes", programmes);
			// model.addAttribute("produits",
			// this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));

			if (!result.hasErrors()) {

				LigneInventaireMvt ligneInventaire = new LigneInventaireMvt();
				ligneInventaire.setProduit(formulaireInventaire.getProduit());
				ligneInventaire.setQtePhysique(formulaireInventaire.getQtePhysique());
				ligneInventaire.setObservation(formulaireInventaire.getObservation().trim());
				ligneInventaire.setLgnDatePerem(formulaireInventaire.getLgnDatePerem());
				ligneInventaire.setLgnLot(formulaireInventaire.getLgnLot().replaceAll(" ", ""));

				// determiner le stock logique du produit
				PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(formulaireInventaire.getProduit(),
								formulaireInventaire.getLgnLot());
				if (pharmProduitAttribut != null) {
					
					CoutAchatProduit coutProduitActuel=Context.getService(GestionReceptionService.class).getCoutProduit(pharmProduitAttribut.getProdAttriId(), null);
					CoutAchatProduit coutProduitPrecedent=Context.getService(GestionReceptionService.class)
									.getCoutProduit(pharmProduitAttribut.getProdAttriId(), coutProduitActuel.getDateCout());
					int prixA=coutProduitActuel.getPrixAchat();
					int prixP=(coutProduitPrecedent.getPrixAchat()!=null)?coutProduitPrecedent.getPrixAchat():0;
					int prixMoyen = (int) Math.ceil((prixA+prixP)/2);
					
					ligneInventaire.setPrixInv(prixMoyen);

					PharmStockerId pharmStockerId = new PharmStockerId();
					pharmStockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					pharmStockerId.setProgramId(formulaireInventaire.getPharmProgramme().getProgramId());
					PharmStocker pharmStocker = Context.getService(GestionStockService.class)
							.getPharmStockerById(pharmStockerId);
					if (pharmStocker != null) {
						ligneInventaire.setQteLogique(pharmStocker.getStockQte());
						ligneInventaire
								.setEcart(formulaireInventaire.getQtePhysique()-pharmStocker.getStockQte());
					} else {
						ligneInventaire.setQteLogique(0);
						ligneInventaire.setEcart(formulaireInventaire.getQtePhysique());
					}
				} else {
					ligneInventaire.setQteLogique(0);
					ligneInventaire.setEcart(formulaireInventaire.getQtePhysique());
				}
				
				//gestion de la modif du tableau
				int taille = formulaireInventaire.getTabInventaire().getLigneInventaireCollection().size();
				
					for (int i = 1; i <= taille; i++) {
						String lot = request.getParameter("lot" + i);
						
						String datePerem = request.getParameter("datePerem" + i);
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date dateFormat =null;
						try {
							if(datePerem!=null) dateFormat=formatter.parse(datePerem);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							dateFormat=null;
						}
						String qtePhys = request.getParameter("qtePhys" + i);					
						int qtePhysInt=(qtePhys!=null) ? Integer.parseInt(qtePhys) : 0 ;
						String observ = request.getParameter("observ" + i);
	
						String idProd = request.getParameter("idProd" + i); 
						
						LigneInventaireMvt ligneMvt=formulaireInventaire.getTabInventaire().getLigneInventaireById(idProd);
						ligneMvt.setLgnDatePerem(dateFormat);
						ligneMvt.setLgnLot(lot);
						ligneMvt.setObservation(observ);
						ligneMvt.setQtePhysique(qtePhysInt);
						
						
						formulaireInventaire.getTabInventaire().removeLigneInventaireById(idProd);
						formulaireInventaire.getTabInventaire().addLigneInventaire(ligneMvt);					
					}
				

				//gerer les lignes de même numéro de lot
				LigneInventaireMvt ligneNew=formulaireInventaire.getTabInventaire().getLigneInventaireById(ligneInventaire.getProduit().getProdId()+ligneInventaire.getLgnLot());
				if(ligneNew!=null){
					ligneInventaire.setQtePhysique(ligneInventaire.getQtePhysique()+ligneNew.getQtePhysique());
					ligneInventaire
					.setEcart(ligneInventaire.getQtePhysique()-ligneInventaire.getQteLogique());
				}
				
				
				formulaireInventaire.getTabInventaire().addLigneInventaire(ligneInventaire);
				
				/*model.addAttribute("ligneInventaires",
						formulaireInventaire.getTabInventaire().getLigneInventaireCollection());*/
				model.addAttribute("mess", "valid");

				formulaireInventaire.setProduit(null);
				formulaireInventaire.setQtePhysique(null);
				formulaireInventaire.setLgnLot(null);
				formulaireInventaire.setLgnDatePerem(null);
				formulaireInventaire.setObservation(null);
			}
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("ligneInventaires",
					formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits",
					this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));
			model.addAttribute("var", "1");
			model.addAttribute("tab", "1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/module/pharmagest/inventaireModif.form",method = RequestMethod.GET, params = { "supprimId" })
	public void deleteLigneOperation(@RequestParam(value = "supprimId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model,HttpServletRequest request) {

		//try {

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();

			model.addAttribute("programmes", programmes);
			model.addAttribute("produits",
					this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));
			
			//delete ligne dans la base
			LigneInventaireMvt ligneMvt=formulaireInventaire.getTabInventaire().getLigneInventaireById(paramId);
			/*PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
					.getPharmProduitAttributByElement(ligneMvt.getProduit(), ligneMvt.getLgnLot());
			if(pharmProduitAttribut!=null){
			PharmLigneInventaireId pharmLigneInventaireId=new PharmLigneInventaireId();
			pharmLigneInventaireId.setInvId(formulaireInventaire.getPharmInventaire().getInvId());
			pharmLigneInventaireId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
			PharmLigneInventaire pharmLigneInventaire = Context.getService(InventaireService.class).getPharmLigneInventaire(pharmLigneInventaireId);
			if(pharmLigneInventaire!=null)Context.getService(InventaireService.class).deletePharmLigneInventaire(pharmLigneInventaire);
			}*/

			
			
			//gestion de la modif du tableau
			/*int taille = formulaireInventaire.getTabInventaire().getLigneInventaireCollection().size();
			System.out.println("---------------taille--------------------------"+taille);
				for (int i = 1; i <= taille; i++) {
					String lot = request.getParameter("lot" + i);
					
					String datePerem = request.getParameter("datePerem" + i);
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date dateFormat =null;
					try {
						if(datePerem!=null) dateFormat=formatter.parse(datePerem);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						dateFormat=null;
					}
					String qtePhys = request.getParameter("qtePhys" + i);					
					int qtePhysInt=(qtePhys!=null) ? Integer.parseInt(qtePhys) : 0 ;
					String observ = request.getParameter("observ" + i);

					String idProd = request.getParameter("idProd" + i); 
					
					System.out.println("---------------i--------------------------"+i);
					System.out.println("---------------lot--------------------------"+request.getParameter("lot" + i));
					System.out.println("---------------idProd--------------------------"+idProd);
					System.out.println("-------------------datePerem----------------------"+request.getParameter("datePerem" + i));
					System.out.println("-------------------qtePhys----------------------"+qtePhys);
					System.out.println("----------------------observ-------------------"+observ);
					
					LigneInventaireMvt mvt=formulaireInventaire.getTabInventaire().getLigneInventaireById(idProd);
					mvt.setLgnDatePerem(dateFormat);
					mvt.setLgnLot(lot);
					mvt.setObservation(observ);
					mvt.setQtePhysique(qtePhysInt);
					
					formulaireInventaire.getTabInventaire().removeLigneInventaireById(idProd);
					formulaireInventaire.getTabInventaire().addLigneInventaire(mvt);					
				}*/
			
			formulaireInventaire.getTabInventaire().removeLigneInventaireById(paramId);
			
			model.addAttribute("ligneInventaires",
					formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("mess", "delete");
			model.addAttribute("var", "1");
			model.addAttribute("tab", "1");
			formulaireInventaire.setProduit(null);
			formulaireInventaire.setQtePhysique(null);
			formulaireInventaire.setLgnLot(null);
			formulaireInventaire.setLgnDatePerem(null);
			formulaireInventaire.setObservation(null);
		//} catch (Exception e) {
		//	e.getMessage();
		//}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/pharmagest/inventaireModif.form",method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void valider(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {

			// save operation
			PharmOperation operation = new PharmOperation();
			operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(8));
			operation.setOperDateRecept(formulaireInventaire.getPharmInventaire().getInvDeb());
			operation.setOperDateSaisi(new Date());
			//  gestionnaire
			PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
			gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
			gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
			gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
			Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

			operation.setPharmGestionnairePharma(gestionnairePharma);
			operation.setPharmProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme());
			operation.setOperNum(formulaireInventaire.getPharmInventaire().getInvId()+"");
			Context.getService(OperationService.class).savePharmOperation(operation);
			
			//update inventaire
			//System.out.print("------------formulaireInventaire.getPharmInventaire()--------- ::"+formulaireInventaire.getPharmInventaire().getInvId());
			PharmInventaire inventaire=Context.getService(InventaireService.class).getPharmInventaireById(formulaireInventaire.getPharmInventaire().getInvId());
			inventaire.setInvDeb(formulaireInventaire.getInvDeb());
			inventaire.setInvDateModif(new Date());
			inventaire.setPharmGestionnaireModif(formulaireInventaire.getGestionnairePharma());
			Context.getService(InventaireService.class).updatePharmInventaire(inventaire);
			//supprimer toute les lignes des inventaires
			Context.getService(InventaireService.class).deletePharmLigneInventairesByInventaire(inventaire);
			
			
			Collection<PharmLigneInventaire> ligneInventaires = new ArrayList<PharmLigneInventaire>();

			int taille = formulaireInventaire.getTabInventaire().getLigneInventaireCollection().size();
			System.out.print("---------------------taille-----------------"+taille);
			for (int i = 1; i <= taille; i++) {
				String lot = request.getParameter("lot" + i);
				
				String datePerem = request.getParameter("datePerem" + i);
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateFormat =null;
				try {
					if(datePerem!=null) dateFormat=formatter.parse(datePerem);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					dateFormat=null;
				}
				String qtePhys = request.getParameter("qtePhys" + i);
				
				int qtePhysInt=(qtePhys!=null) ? Integer.parseInt(qtePhys) : 0 ;
				String observ = request.getParameter("observ" + i);

				String idProd = request.getParameter("idProd" + i);
				
				LigneInventaireMvt ligneMvt=formulaireInventaire.getTabInventaire().getLigneInventaireById(idProd);
				ligneMvt.setLgnDatePerem(dateFormat);
				ligneMvt.setLgnLot(lot);
				ligneMvt.setObservation(observ);
				//System.out.println("-----------------------------------------qtePhysInt :: " +qtePhysInt);
				ligneMvt.setQtePhysique(qtePhysInt);
				
				PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(ligneMvt.getProduit(), ligneMvt.getLgnLot());
				
				if (pharmProduitAttribut == null) {
					pharmProduitAttribut = new PharmProduitAttribut();
					pharmProduitAttribut.setPharmProduit(ligneMvt.getProduit());
					pharmProduitAttribut.setProdAttriDate(new Date());
					pharmProduitAttribut.setProdDatePerem(ligneMvt.getLgnDatePerem());
					pharmProduitAttribut.setProdLot(ligneMvt.getLgnLot());
					pharmProduitAttribut.setFlagValid(true);
					//System.out.println("test : " + ligneMvt.getProduit().getFullName());
					Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
				//prendre en compte le stock

					PharmStockerId pharmStockerId = new PharmStockerId();
					pharmStockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					pharmStockerId.setProgramId(formulaireInventaire.getPharmProgramme().getProgramId());
					PharmStocker pharmStocker = Context.getService(GestionStockService.class)
							.getPharmStockerById(pharmStockerId);
					if (pharmStocker != null) {
						ligneMvt.setQteLogique(pharmStocker.getStockQte());
						ligneMvt
								.setEcart(ligneMvt.getQtePhysique()-pharmStocker.getStockQte());
					} else {
						ligneMvt.setQteLogique(0);
						ligneMvt.setEcart(ligneMvt.getQtePhysique());
					}
				
					
				}else{
					pharmProduitAttribut.setProdAttriDate(new Date());
					pharmProduitAttribut.setProdDatePerem(ligneMvt.getLgnDatePerem());
					pharmProduitAttribut.setProdLot(ligneMvt.getLgnLot());
					pharmProduitAttribut.setFlagValid(true);
					Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
					ligneMvt
					.setEcart(ligneMvt.getQtePhysique()-ligneMvt.getQteLogique());
				}

				PharmLigneInventaireId liId = new PharmLigneInventaireId();
				liId.setInvId(formulaireInventaire.getPharmInventaire().getInvId());
				liId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
								
				PharmLigneInventaire li=Context.getService(InventaireService.class).getPharmLigneInventaire(liId);
								
					li = new PharmLigneInventaire();
					li.setId(liId);
					li.setObservation(ligneMvt.getObservation());
					li.setQteLogique(ligneMvt.getQteLogique());
					li.setQtePhysique(ligneMvt.getQtePhysique());
					li.setEcart(ligneMvt.getEcart());
					li.setPrixInv(ligneMvt.getPrixInv());
					li.setPharmInventaire(formulaireInventaire.getPharmInventaire());
					li.setPharmProduitAttribut(pharmProduitAttribut);					
					Context.getService(InventaireService.class).savePharmLigneInventaire(li);		
					
					ligneInventaires.add(li);
					
					formulaireInventaire.getTabInventaire().addLigneInventaire(ligneMvt);
							
			}
			
			/*PharmInventaire inv = Context.getService(InventaireService.class)
					.getPharmInventaireById(formulaireInventaire.getPharmInventaire().getInvId());*/
			model.addAttribute("inventaires", ligneInventaires);
			model.addAttribute("totalGlobal",this.calculTotalPrix(formulaireInventaire.getTabInventaire().getLigneInventaires()));
			model.addAttribute("tab", "2");
			model.addAttribute("var", "2");

		}

	}
	
	/******************************************************************************************************************************/
	
	
	public void initialisation(ModelMap model) {
		
		FormulairePharmInventaire formulaireInventaire = new FormulairePharmInventaire();
		model.addAttribute("formulaireInventaire", formulaireInventaire);
		model.addAttribute("user", Context.getAuthenticatedUser());
		if(Context.hasPrivilege("pharmacie")) {
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		// formulaireSaisiesPPS.setPharmGestionnairePharma(gestionnairePharma);
		

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		model.addAttribute("var", "0");
		}
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
		binder.registerCustomEditor(PharmFournisseur.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmFournisseur fournisseur = Context.getService(ParametresService.class)
						.getFournisseurById(Integer.parseInt(text));
				this.setValue(fournisseur);
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
