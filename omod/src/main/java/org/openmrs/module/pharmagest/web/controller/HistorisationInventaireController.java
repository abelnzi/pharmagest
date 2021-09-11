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
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.SiteService;
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

public class HistorisationInventaireController {
	
	@Autowired
	InventaireValidator inventaireValidator;

	@Autowired
	Inventaire2Validator inventaire2Validator;

	@Autowired
	InventaireAjoutValidator inventaireAjoutValidator;
	
	/**********************************************Trouver la liste d'inventaire****************************************************************/
	@RequestMapping(value = "/module/pharmagest/listHistoInventaire.form", method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listHistoInventaire";
	}

	@RequestMapping(value = "/module/pharmagest/listHistoInventaire.form",method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {

		//listRPPSValidator.validate(formulaireTraitementsPPS, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {

			Collection<PharmInventaire> inventaires =Context.getService(InventaireService.class).getPharmInventairesByPP(
					formulaireInventaire.getPharmProgramme(), formulaireInventaire.getDateParam(),formulaireInventaire.getDateParam2(),"VA");
			
			model.addAttribute("inventaires", inventaires);
			if(inventaires.isEmpty())model.addAttribute("mess", "vide");

		}

	}
	
	public int calculTotalPrix(Set<PharmLigneInventaire> list,String mode){
		int total=0;
		Set lignes = list;
		if(mode.equals("phys")) {
			for (PharmLigneInventaire ligne : list) {
				if(ligne.getPrixInv()==null)ligne.setPrixInv(0);
				if(ligne.getQtePhysique()==null)ligne.setQtePhysique(0);
				total=total+ ligne.getPrixInv()*ligne.getQtePhysique();	
			}
		} else {
			for (PharmLigneInventaire ligne : list) {
				if(ligne.getPrixInv()==null)ligne.setPrixInv(0);
				if(ligne.getQteLogique()==null)ligne.setQteLogique(0);
				total=total+ ligne.getPrixInv()*ligne.getQteLogique();	
			}
		} 				
		
		return total;
	}
	

	@RequestMapping(value = "/module/pharmagest/histoInventaire.form",method = RequestMethod.GET, params = { "paramId" })
	public void initModif(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireInven", formulaireInventaire);
		if (!result.hasErrors()) {
			int id=(paramId!=null) ? Integer.parseInt(paramId) : 0 ;
			PharmInventaire inventaire =  Context.getService(InventaireService.class).getPharmInventaireById(id);
			
			formulaireInventaire.setPharmInventaire(inventaire);			
			model.addAttribute("inventaires", inventaire.getPharmLigneInventaires());
			
			model.addAttribute("totalTheo",this.calculTotalPrix(inventaire.getPharmLigneInventaires(), "theo"));
			model.addAttribute("totalPhys",this.calculTotalPrix(inventaire.getPharmLigneInventaires(), "phys"));
			model.addAttribute("var", "1");
			

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
			listMvm.add(ligneMvm);
			//System.out.println("-----------------------------------------------------------------");
			//System.out.println("Dateperempt :: "+ ligneMvm.getLgnDatePerem());
		}
		 return listMvm;
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
