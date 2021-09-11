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
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.metier.FormulaireModifPPS;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.openmrs.module.pharmagest.metier.LigneOperationMvt;
import org.openmrs.module.pharmagest.metier.LigneReceptionMvt;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
import org.openmrs.module.pharmagest.metier.TabReceptionMvt;
import org.openmrs.module.pharmagest.validator.ReceptionEditeValidator;
import org.openmrs.module.pharmagest.validator.ReceptionValidator;
import org.openmrs.module.pharmagest.validator.ReceptionValidatorAjout;
import org.openmrs.module.pharmagest.validator.Stock2Validator;
import org.openmrs.module.pharmagest.validator.StockValidator;
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
@SessionAttributes("formulaireReception")

public class HistorisationReceptionController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ReceptionValidator receptionValidator;
	@Autowired
	ReceptionValidatorAjout receptionValidatorAjout;
	@Autowired
	ReceptionEditeValidator receptionEditeValidator;
	
	/**********************************************génerer la Liste************************************************/
	
	@RequestMapping(value = "/module/pharmagest/listReceptionHisto.form",method = RequestMethod.GET)
	public String initListPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listReceptionHisto";
	}

	@RequestMapping(value = "/module/pharmagest/listReceptionHisto.form",method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {

		//stock2Validator.validate(formulaireReception, result);

		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
				.getAllFournisseurs();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("programmes", programmes);
			

		if (!result.hasErrors()) {
			
			Collection<PharmReception> receptions;
			if (formulaireReception.getDateDebut()!= null || formulaireReception.getDateFin()!= null ) {
				//System.out.println("-------------------ici0--------------");
				receptions = Context.getService(GestionReceptionService.class).getPharmReceptionsByAttri(formulaireReception.getPharmProgramme(),
						formulaireReception.getDateDebut(),formulaireReception.getDateFin(),"VA");
			} else {				
				receptions = Context.getService(GestionReceptionService.class).getPharmReceptionsByAttri(formulaireReception.getPharmProgramme(),
						null,"VA");				
			}

			model.addAttribute("receptions", receptions);
			model.addAttribute("formulaireReception", formulaireReception);
			model.addAttribute("var", "1");

		}else{
			
			model.addAttribute("formulaireReception", formulaireReception);
		}

	}
	/*******************************************Entête de la modification**************************************************/
	@RequestMapping(value = "/module/pharmagest/histoReception.form",method = RequestMethod.GET, params = { "paramId" })
	public void initModif(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {
		this.initialisation(model);
		
		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
				.getAllFournisseurs();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("fournisseurs", fournisseurs);
		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {
			PharmReception reception = Context.getService(GestionReceptionService.class)
					.getPharmReceptionById(Integer.parseInt(paramId));
			formulaireReception.setPharmReception(reception);
			//System.out.println("========================"+formulaireReception.getPharmReception().getPharmGestionnairePharma().getGestNom());
			formulaireReception.setPharmProgramme(reception.getPharmProgramme());
			Collection<LigneReceptionMvt> ligneMvms = this.correspLigneReceptionToMvt(reception.getPharmLigneReceptions());
			TabReceptionMvt tabReceptionMvt=new TabReceptionMvt();
			Iterator it= ligneMvms.iterator();
			while (it.hasNext()) {
				LigneReceptionMvt ligne= (LigneReceptionMvt)it.next();
				tabReceptionMvt.addLigneReception(ligne);
			}
			
			//formulaireReception.setTabReceptionMvt(tabReceptionMvt);
			
			model.addAttribute("produits",
					this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
			model.addAttribute("formulaireReception", formulaireReception);
			model.addAttribute("ligneReceptions",tabReceptionMvt.getLigneReceptionsCollection());
			model.addAttribute("var", "0");
			model.addAttribute("totalGlobal", this.gestionPrix(tabReceptionMvt.getLigneReceptions()));
		}

	}
	
	
	public int gestionPrix(Map map){
		int total=0;
		Map lignes = map;
		for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
			Object key = i.next();			
			LigneReceptionMvt ligne = (LigneReceptionMvt) lignes.get(key);
			if(ligne.getLgnRecptPrixAchat()==null)ligne.setLgnRecptPrixAchat(0);
			if(ligne.getLgnRecptQte()==null)ligne.setLgnRecptQte(0);
			total=total+ ligne.getLgnRecptPrixAchat()*ligne.getLgnRecptQte();				   	
		}
		return total;
	}
	
	
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}

	
	public Collection<LigneReceptionMvt> correspLigneReceptionToMvt(Collection<PharmLigneReception> pharmligneReceptions ){
		List<LigneReceptionMvt> listMvm = new ArrayList<LigneReceptionMvt>();

		Iterator it= pharmligneReceptions.iterator();
		while (it.hasNext()) {		
			PharmLigneReception ligne= (PharmLigneReception)it.next();
			LigneReceptionMvt ligneMvm = new LigneReceptionMvt();
			ligneMvm.setReception(ligne.getPharmReception());
			ligneMvm.setProduit(ligne.getPharmProduitAttribut().getPharmProduit());
			ligneMvm.setProduitDetail(ligne.getPharmProduitAttribut().getPharmProduit());
			ligneMvm.setLgnRecptQteDetailRecu(ligne.getLgnRecptQteDetailRecu());
			ligneMvm.setLgnRecptQteDetailLivree(ligne.getLgnRecptQteDetailLivre());
			ligneMvm.setLgnRecptPrixAchatDetail(ligne.getLgnRecptPrix());
			ligneMvm.setLgnRecptLotDetail(ligne.getPharmProduitAttribut().getProdLot());
			ligneMvm.setLgnDatePeremDetail(ligne.getPharmProduitAttribut().getProdDatePerem());
			ligneMvm.setLgnRecptObservDetail(ligne.getLgnRecptObserv());
			ligneMvm.setLgnRecptQte(ligne.getLgnRecptQteRecu());
			ligneMvm.setLgnRecptQteLivree(ligne.getLgnRecptQteLivre());
			ligneMvm.setLgnRecptPrixAchat(ligne.getLgnRecptPrix());
			ligneMvm.setLgnRecptLot(ligne.getPharmProduitAttribut().getProdLot());
			ligneMvm.setLgnDatePerem(ligne.getPharmProduitAttribut().getProdDatePerem());
			ligneMvm.setLgnRecptObserv(ligne.getLgnRecptObserv());
			listMvm.add(ligneMvm);
			
			/*//gestion des qtes
			PharmProduitCompl produitCompl=Context.getService(ProduitService.class).
					getProduitComplByProduit(ligne.getPharmProduitAttribut().getPharmProduit());
			if(produitCompl!=null) {
				int convers=produitCompl.getProdCplUnitConvers();
				if(ligne.getLgnRecptQteRecu()==null || ligne.getLgnRecptQteRecu()==0)
					ligneMvm.setLgnRecptQte(ligne.getLgnRecptQteDetailRecu()/convers);
				if(ligne.getLgnRecptQteLivre()==null || ligne.getLgnRecptQteLivre()==0)
					ligneMvm.setLgnRecptQteLivree(ligne.getLgnRecptQteDetailLivre()/convers);
			}	else {
				ligneMvm.setLgnRecptQte(ligne.getLgnRecptQteDetailRecu());
				ligneMvm.setLgnRecptQteLivree(ligne.getLgnRecptQteDetailLivre());
			}*/
			
			//listMvm.add(ligneMvm);
			//System.out.println("-----------------------------------------------------------------");
		}
		 return listMvm;
	}
	

	public void initialisation(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		FormulaireReception formulaireReception = new FormulaireReception();
		model.addAttribute("formulaireReception", formulaireReception);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		formulaireReception.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
				.getAllFournisseurs();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("fournisseurs", fournisseurs);
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
