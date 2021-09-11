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
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.metier.FormulaireModifPPS;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.openmrs.module.pharmagest.metier.LigneReceptionMvt;
import org.openmrs.module.pharmagest.metier.TabReceptionMvt;
import org.openmrs.module.pharmagest.validator.ReceptionEditeValidator;
import org.openmrs.module.pharmagest.validator.ReceptionValidator;
import org.openmrs.module.pharmagest.validator.ReceptionValidatorAjout;
import org.openmrs.module.pharmagest.validator.ReceptionValidatorAjoutDetail;
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

public class ModifierReceptionController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ReceptionValidator receptionValidator;
	@Autowired
	ReceptionValidatorAjout receptionValidatorAjout;
	@Autowired
	ReceptionEditeValidator receptionEditeValidator;
	@Autowired
	ReceptionValidatorAjoutDetail receptionValidatorAjoutDetail;
	
	/**********************************************génerer la Liste************************************************/
	
	@RequestMapping(value = "/module/pharmagest/listReceptionModif.form",method = RequestMethod.GET)
	public String initListPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listReceptionModif";
	}

	@RequestMapping(value = "/module/pharmagest/listReceptionModif.form",method = RequestMethod.POST, params = { "btn_rechercher" })
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
			if (formulaireReception.getRecptDateRecept() != null) {
				//System.out.println("-------------------ici0--------------");
				receptions = Context.getService(GestionReceptionService.class).getPharmReceptionsByAttri(formulaireReception.getPharmProgramme(),
						formulaireReception.getRecptDateRecept(),"NV");
			} else {
				
				receptions = Context.getService(GestionReceptionService.class).getPharmReceptionsByAttri(formulaireReception.getPharmProgramme(),
						null,"NV");
				
			}

			model.addAttribute("receptions", receptions);
			model.addAttribute("formulaireReception", formulaireReception);
			model.addAttribute("var", "1");

		}else{
			
			model.addAttribute("formulaireReception", formulaireReception);
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
			//System.out.println("==========ligne.getLgnRecptQte()==================="+ligne.getLgnRecptQte());
			total=total+ ligne.getLgnRecptPrixAchat()*ligne.getLgnRecptQte();				   	
		}
		return total;
	}
	
	@RequestMapping(value = "/module/pharmagest/listReceptionModif.form",method = RequestMethod.GET, params = { "deleteId" })
	public void deleteReception(@RequestParam(value = "deleteId") String deleteId,
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {

			//receptionValidator.validate(formulaireReception, result);
			
			if (!result.hasErrors()) {	
				PharmReception reception=Context.getService(GestionReceptionService.class).getPharmReceptionById(Integer.parseInt(deleteId));
				if(reception!=null){
					PharmReception var=reception;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					model.addAttribute("dateRecept", dateFormat.format(var.getRecptDateRecept()));
					model.addAttribute("fournisseur",var.getPharmFournisseur().getFourDesign1());
					model.addAttribute("programme",var.getPharmProgramme().getProgramLib());
					Context.getService(GestionReceptionService.class).deletePharmLigneReceptionsByReception(reception);			
					Context.getService(GestionReceptionService.class).deletePharmReception(reception);	
					model.addAttribute("suppr", "suppr");
					List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
							.getAllProgrammes();
					model.addAttribute("programmes", programmes);
					model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
				}		
				
				
			} 

		

	}
	/*******************************************Entête de la modification**************************************************/
	@RequestMapping(value = "/module/pharmagest/modifierReception.form",method = RequestMethod.GET, params = { "paramId" })
	public void initModif(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model) {
		formulaireReception=new FormulaireReception();
		
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
			formulaireReception.setPharmProgramme(reception.getPharmProgramme());
			Collection<LigneReceptionMvt> ligneMvms = this.correspLigneReceptionToMvt(reception.getPharmLigneReceptions());
			
			Iterator it= ligneMvms.iterator();
			while (it.hasNext()) {
				LigneReceptionMvt ligne= (LigneReceptionMvt)it.next();
				formulaireReception.getTabReceptionMvt().addLigneReception(ligne);
			}
			model.addAttribute("produits",
					this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
			model.addAttribute("formulaireReception", formulaireReception);
			model.addAttribute("ligneReceptions",formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
			model.addAttribute("var", "0");
			//System.out.println("==========formulaireReception.getTabReceptionMvt().getLigneReceptions()==================="+formulaireReception.getTabReceptionMvt().getLigneReceptions().size());
			model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
		}

	}
	
	

	@RequestMapping(value = "/module/pharmagest/modifierReception.form",method = RequestMethod.POST, params = { "reset" })
	public String annuler(@ModelAttribute("formulaireModif") FormulaireModifPPS formulaireModif,
			BindingResult result, HttpSession session, ModelMap model) {
		
		this.initialisation(model);
		return "redirect:/module/pharmagest/listReceptionModif.form";
			
	}
	
	public List<PharmProduitCompl> transformToList(Set<PharmProduit> set) {
		//List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		//Collections.sort(list, Collections.reverseOrder());		
		List<PharmProduitCompl> listCpl = new ArrayList<PharmProduitCompl>();
		for (PharmProduit pharmProduit : set) {
			PharmProduitCompl produitCpl=Context.getService(ProduitService.class).getProduitComplByProduit(pharmProduit);
			if(produitCpl!=null)listCpl.add(produitCpl);
		}
		
		Collections.sort(listCpl, Collections.reverseOrder());		
		return listCpl;
	}

	/******************************************Ajouter et modifier les lignes du tableau
	 * @throws ParseException ***************************************************/
	
	@RequestMapping(value = "/module/pharmagest/modifierReception.form",method = RequestMethod.POST, params = { "btn_valider_detail" })
	public void addModifDetail(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) throws ParseException {
		//try {
			receptionValidatorAjoutDetail.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			

			if (!result.hasErrors()) {				
				LigneReceptionMvt lngReception;
				
				lngReception=formulaireReception.getTabReceptionMvt().getLigneReceptionById(
						formulaireReception.getProduitDetail().getProdId()+formulaireReception.getLgnRecptLotDetail());
				
				if(lngReception!=null){
					lngReception.setLgnRecptQteDetailRecu(lngReception.getLgnRecptQteDetailRecu()+formulaireReception.getLgnRecptQteDetailRecu());
					lngReception.setLgnRecptQteDetailLivree(lngReception.getLgnRecptQteDetailLivree()+formulaireReception.getLgnRecptQteDetailLivree());
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduitDetail());
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu()/convers);
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree()/convers);
					} else {
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu());
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree());
					}
					
				} else {
					lngReception = new LigneReceptionMvt();
					lngReception.setReception(formulaireReception.getPharmReception());
					lngReception.setProduit(formulaireReception.getProduitDetail());
					lngReception.setProduitDetail(formulaireReception.getProduitDetail());
					//lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQte());
					//lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteLivree());
					lngReception.setLgnRecptPrixAchatDetail(formulaireReception.getLgnRecptPrixAchatDetail());
					lngReception.setLgnRecptPrixAchat(formulaireReception.getLgnRecptPrixAchatDetail());
					lngReception.setLgnRecptLotDetail(formulaireReception.getLgnRecptLotDetail().replaceAll(" ", ""));
					lngReception.setLgnRecptLot(formulaireReception.getLgnRecptLotDetail().replaceAll(" ", ""));
					lngReception.setLgnDatePeremDetail(formulaireReception.getLgnDatePeremDetail());
					lngReception.setLgnDatePerem(formulaireReception.getLgnDatePeremDetail());
					lngReception.setLgnRecptObservDetail(formulaireReception.getLgnRecptObservDetail().trim());
					lngReception.setLgnRecptObserv(formulaireReception.getLgnRecptObservDetail().trim());
					lngReception.setLgnRecptQteDetailRecu(formulaireReception.getLgnRecptQteDetailRecu());
					lngReception.setLgnRecptQteDetailLivree(formulaireReception.getLgnRecptQteDetailLivree());
					
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduitDetail());
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu()/convers);
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree()/convers);
					} else {
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu());
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree());
					}
				
				}
				
				

				
				formulaireReception.setProduitDetail(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);

				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "0");
				
				//Prise en compte des modifications du tableau
				int taille = formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection().size();
				//System.out.println("------------ getTabReceptionMvt -------------- "+ taille);
				for (int i = 1; i <= (taille -1); i++) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					String numLot = request.getParameter("numLot" + i);
					String observ = request.getParameter("observ" + i);
					String peremption = request.getParameter("peremption" + i);	
					Date datePeremp=(peremption!=null && peremption!="") ? formatter.parse(peremption) : null ;
					String qteLivre = request.getParameter("qtelivre" + i);	
					int qteLivreInt=(qteLivre!=null) ? Integer.parseInt(qteLivre) : 0 ;
					String qte = request.getParameter("qte" + i);	
					int qteInt=(qte!=null) ? Integer.parseInt(qte) : 0 ;
					String prix = request.getParameter("prix" + i);					
					int prixInt=(prix!=null && prix!="") ? Integer.parseInt(prix) : 0 ;
					String idProd= request.getParameter("idProd" + i);
					int idProdInt=(idProd!=null) ? Integer.parseInt(idProd) : 0 ;
					PharmProduit produit =Context.getService(ProduitService.class).getPharmProduitById(idProdInt);
					//System.out.println("------------ idProd --------------------"+ idProd);
					//System.out.println("------------ numLot --------------------"+ numLot);
					//System.out.println("------------ tableau produit --------------------"+ idProdInt +" ----produit---- "+produit);
					LigneReceptionMvt ligneReceptionMvt= new LigneReceptionMvt();
					ligneReceptionMvt.setLgnDatePeremDetail(datePeremp);
					ligneReceptionMvt.setLgnDatePerem(datePeremp);
					ligneReceptionMvt.setLgnRecptLotDetail(numLot);
					ligneReceptionMvt.setLgnRecptLot(numLot);
					ligneReceptionMvt.setLgnRecptPrixAchatDetail(prixInt);
					ligneReceptionMvt.setLgnRecptPrixAchat(prixInt);
					ligneReceptionMvt.setLgnRecptQteDetailRecu(qteInt);
					ligneReceptionMvt.setLgnRecptQteDetailLivree(qteLivreInt);
					ligneReceptionMvt.setProduitDetail(produit);
					ligneReceptionMvt.setProduit(produit);
					ligneReceptionMvt.setReception(formulaireReception.getPharmReception());
					ligneReceptionMvt.setLgnRecptObservDetail(observ);
					ligneReceptionMvt.setLgnRecptObserv(observ);
					
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduit());
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu()/convers);
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree()/convers);
					} else {
						lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQteDetailRecu());
						lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteDetailLivree());
					}
					
					
					//System.out.println("------------ Observation detail-----------------------"+ idProdInt+"/"+numLot);
					formulaireReception.getTabReceptionMvt().removeLigneReceptionById(idProdInt+numLot);
					formulaireReception.getTabReceptionMvt().addLigneReception(ligneReceptionMvt);		

				}
				
				formulaireReception.getTabReceptionMvt().addLigneReception(lngReception);
				
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "valid");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
				
				
			} else {
				formulaireReception.setProduitDetail(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("var", "0");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}

		/*} catch (Exception e) {
			e.printStackTrace();
		}*/

	}
	
	@RequestMapping(value = "/module/pharmagest/modifierReception.form",method = RequestMethod.POST, params = { "btn_valider_gros" })
	public void addModifGros(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) throws ParseException {
		//try {
			receptionValidatorAjout.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			

			if (!result.hasErrors()) {
				LigneReceptionMvt lngReception;
				
				lngReception=formulaireReception.getTabReceptionMvt().getLigneReceptionById(
						formulaireReception.getProduit().getProdId()+formulaireReception.getLgnRecptLot());
				
				if(lngReception!=null){
					lngReception.setLgnRecptQte(lngReception.getLgnRecptQte()+formulaireReception.getLgnRecptQte());
					lngReception.setLgnRecptQteLivree(lngReception.getLgnRecptQteLivree()+formulaireReception.getLgnRecptQteLivree());
				} else {
					lngReception = new LigneReceptionMvt();
					lngReception.setReception(formulaireReception.getPharmReception());
					lngReception.setProduit(formulaireReception.getProduit());
					lngReception.setProduitDetail(formulaireReception.getProduit());
					lngReception.setLgnRecptQte(formulaireReception.getLgnRecptQte());
					lngReception.setLgnRecptQteLivree(formulaireReception.getLgnRecptQteLivree());
					lngReception.setLgnRecptPrixAchat(formulaireReception.getLgnRecptPrixAchat());
					lngReception.setLgnRecptPrixAchatDetail(formulaireReception.getLgnRecptPrixAchat());
					lngReception.setLgnRecptLot(formulaireReception.getLgnRecptLot().replaceAll(" ", ""));
					lngReception.setLgnRecptLotDetail(formulaireReception.getLgnRecptLot().replaceAll(" ", ""));
					lngReception.setLgnDatePerem(formulaireReception.getLgnDatePerem());
					lngReception.setLgnDatePeremDetail(formulaireReception.getLgnDatePerem());
					lngReception.setLgnRecptObserv(formulaireReception.getLgnRecptObserv().trim());
					lngReception.setLgnRecptObservDetail(formulaireReception.getLgnRecptObserv().trim());

								
				}
				//convertion en unité au detail
				PharmProduitCompl pCompl=Context.getService(ProduitService.class).getProduitComplByProduit(formulaireReception.getProduit());
				//System.out.println("------------------ produitCompl --------- "+pCompl);
				if(pCompl!=null){
					
					int convers=pCompl.getProdCplUnitConvers();
					lngReception.setLgnRecptQteDetailRecu(lngReception.getLgnRecptQte()*convers);
					lngReception.setLgnRecptQteDetailLivree(lngReception.getLgnRecptQteLivree()*convers);
				} else {
					lngReception.setLgnRecptQteDetailRecu(lngReception.getLgnRecptQte());
					lngReception.setLgnRecptQteDetailLivree(lngReception.getLgnRecptQteLivree());
				}
				//System.out.println("------------ lngReception-----------------------"+ lngReception.getLgnRecptQteDetailLivree()+"/"+lngReception.getLgnRecptQteDetailRecu());
				

				//System.out.println("----------- verifier produit ---------------"+lngReception.getProduit().getProdId());

				

				formulaireReception.setProduitDetail(null);
				formulaireReception.setProduit(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				formulaireReception.setLgnRecptPrixAchatDetail(null);
				formulaireReception.setLgnRecptPrixAchat(null);

				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "0");
				
				//Prise en compte des modifications du tableau
				int taille = formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection().size();
				//System.out.println("------------ getTabReceptionMvt -------------- "+ taille);
				for (int i = 1; i <= taille; i++) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					String numLot = request.getParameter("numLotGros" + i);
					String observ = request.getParameter("observGros" + i);
					String peremption = request.getParameter("peremptionGros" + i);	
					Date datePeremp=(peremption!=null) ? formatter.parse(peremption) : null ;
					String qteLivre = request.getParameter("qtelivreGros" + i);	
					int qteLivreInt=(qteLivre!=null) ? Integer.parseInt(qteLivre) : 0 ;
					String qte = request.getParameter("qteGros" + i);	
					int qteInt=(qte!=null) ? Integer.parseInt(qte) : 0 ;
					String prix = request.getParameter("prixGros" + i);					
					int prixInt=(prix!=null && prix!="") ? Integer.parseInt(prix) : 0 ;
					String idProd= request.getParameter("idProdGros" + i);
					int idProdInt=(idProd!=null) ? Integer.parseInt(idProd) : 0 ;
					PharmProduit produit =Context.getService(ProduitService.class).getPharmProduitById(idProdInt);
					
					
					LigneReceptionMvt ligneReceptionMvt= new LigneReceptionMvt();
					ligneReceptionMvt.setLgnDatePerem(datePeremp);
					ligneReceptionMvt.setLgnDatePeremDetail(datePeremp);
					ligneReceptionMvt.setLgnRecptLot(numLot);
					ligneReceptionMvt.setLgnRecptLotDetail(numLot);
					ligneReceptionMvt.setLgnRecptPrixAchat(prixInt);
					ligneReceptionMvt.setLgnRecptPrixAchatDetail(prixInt);
					ligneReceptionMvt.setLgnRecptQte(qteInt);
					ligneReceptionMvt.setLgnRecptQteLivree(qteLivreInt);
					ligneReceptionMvt.setProduit(produit);
					ligneReceptionMvt.setProduitDetail(produit);
					ligneReceptionMvt.setReception(formulaireReception.getPharmReception());
					ligneReceptionMvt.setLgnRecptObserv(observ);
					ligneReceptionMvt.setLgnRecptObservDetail(observ);
					
					//convertion en unité au detail
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(produit);
					//System.out.println("------------------ produitCompl --------- "+produitCompl);
					if(produitCompl!=null){
						int convers=produitCompl.getProdCplUnitConvers();
						ligneReceptionMvt.setLgnRecptQteDetailRecu(qteInt*convers);
						ligneReceptionMvt.setLgnRecptQteDetailLivree(qteLivreInt*convers);
					}else{
						ligneReceptionMvt.setLgnRecptQteDetailRecu(qteInt);
						ligneReceptionMvt.setLgnRecptQteDetailLivree(qteLivreInt);
					}
					
					//System.out.println("------------ ligneReceptionMvt-----------------------"+ ligneReceptionMvt.getLgnRecptQteDetailLivree()+"/"+ligneReceptionMvt.getLgnRecptQteDetailRecu());
					formulaireReception.getTabReceptionMvt().removeLigneReceptionById(idProdInt+numLot);
					formulaireReception.getTabReceptionMvt().addLigneReception(ligneReceptionMvt);		
	
					
				}
				formulaireReception.getTabReceptionMvt().removeLigneReceptionById(lngReception.getProduit().getProdId()+lngReception.getLgnRecptLot());
				formulaireReception.getTabReceptionMvt().addLigneReception(lngReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "valid");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
				
			} else {
				formulaireReception.setProduitDetail(null);
				formulaireReception.setProduit(null);
				formulaireReception.setLgnDatePerem(null);
				formulaireReception.setLgnRecptLot(null);
				formulaireReception.setLgnRecptQte(null);
				formulaireReception.setLgnRecptQteLivree(null);
				formulaireReception.setLgnRecptQteDetailRecu(null);
				formulaireReception.setLgnRecptQteDetailLivree(null);
				formulaireReception.setLgnRecptObserv(null);
				formulaireReception.setLgnDatePeremDetail(null);
				formulaireReception.setLgnRecptLotDetail(null);
				formulaireReception.setLgnRecptObservDetail(null);
				formulaireReception.setLgnRecptPrixAchatDetail(null);
				formulaireReception.setLgnRecptPrixAchat(null);
				
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("var", "0");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}

		/*} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	@RequestMapping(value = "/module/pharmagest/modifierReception.form",method = RequestMethod.GET, params = { "supprimId" })
	public void deleteLigneModif(@RequestParam(value = "supprimId") String supprimId,
			@ModelAttribute("formulaireReception") FormulaireReception formulaireReception, BindingResult result,
			HttpSession session, ModelMap model) {

		
			receptionValidator.validate(formulaireReception, result);
			List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
					.getAllFournisseurs();
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			if (!result.hasErrors()) {
				
				formulaireReception.getTabReceptionMvt().removeLigneReceptionById(supprimId);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("mess", "delete");
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "0");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
				
			} else {
				
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "0");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
				
			}

		

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
			//System.out.println("----------- peremption---------" +ligne.getPharmProduitAttribut().getProdDatePerem());
		}
		 return listMvm;
	}
	
	
	/**
	 * @throws ParseException *****************************************************************************************************/

	public Boolean autoriserOperation(PharmProgramme programme,Date dateOperation ){
		PharmInventaire inventaire=Context.getService(InventaireService.class).getPharmInventaireForAuthorize(programme);
		if(inventaire==null)return true ;
		if(inventaire.getInvDeb().after(dateOperation)){
			return false ;
		}
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/pharmagest/modifierReception.form",method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void save(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) throws ParseException {
		// try {
		receptionValidator.validate(formulaireReception, result);
		List<PharmFournisseur> fournisseurs = (List<PharmFournisseur>) Context.getService(ParametresService.class)
				.getAllFournisseurs();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		// save reception
		if (!result.hasErrors()) {
			
			if(this.autoriserOperation(formulaireReception.getPharmProgramme(),
						formulaireReception.getRecptDateRecept())){
					
				formulaireReception.setTypeSaisie(request.getParameter("choix_saisie"));
				String var=formulaireReception.getTypeReception();
				
				formulaireReception.getPharmReception().setRecptDateModif(new Date());
				formulaireReception.getPharmReception().setPharmGestionnaireModif(formulaireReception.getPharmGestionnairePharma());
				Context.getService(GestionReceptionService.class).updatePharmReception(formulaireReception.getPharmReception());
				
				Context.getService(GestionReceptionService.class).deletePharmLigneReceptionsByReception(formulaireReception.getPharmReception());
				
				int taille = formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection().size();
				for (int i = 1; i <= taille; i++) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
					String numLot = (var.equals("Detail")) ? request.getParameter("numLot" + i): request.getParameter("numLotGros" + i);
					String observ = (var.equals("Detail")) ?  request.getParameter("observ" + i): request.getParameter("observGros" + i);
					String peremption = (var.equals("Detail")) ? request.getParameter("peremption" + i): request.getParameter("peremptionGros" + i);	
					Date datePeremp=(peremption!=null && peremption!="") ? formatter.parse(peremption) : null ;
					String qteLivre = (var.equals("Detail")) ? request.getParameter("qtelivre" + i): request.getParameter("qtelivreGros" + i);	
					int qteLivreInt=(qteLivre!=null && qteLivre!="") ? Integer.parseInt(qteLivre) : 0 ;
					String qte = (var.equals("Detail"))? request.getParameter("qte" + i): request.getParameter("qteGros" + i);	
					int qteInt=(qte!=null) ? Integer.parseInt(qte) : 0 ;
					String prix = (var.equals("Detail")) ? request.getParameter("prix" + i): request.getParameter("prixGros" + i);					
					int prixInt=(prix!=null && prix!="") ? Integer.parseInt(prix) : 0 ;
					String idProd= (var.equals("Detail")) ? request.getParameter("idProd" + i): request.getParameter("idProdGros" + i);
					int idProdInt=(idProd!=null) ? Integer.parseInt(idProd) : 0 ;
					
					PharmProduit produit =Context.getService(ProduitService.class).getPharmProduitById(idProdInt);		
									
					// Gestion ProduitAttribut
					PharmProduitAttribut pharmProduitAttribut = new PharmProduitAttribut();
					PharmProduitAttribut varProd = Context.getService(OperationService.class)
							.getPharmProduitAttributByElement(produit, numLot);
					if (varProd != null) {
						pharmProduitAttribut = varProd;
						pharmProduitAttribut.setProdDatePerem(datePeremp);
						pharmProduitAttribut.setFlagValid(true);
						Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
						
					} else {
						//System.out.println("---------------------------------produit-----"+produit);
						pharmProduitAttribut.setPharmProduit(produit);
						pharmProduitAttribut.setProdAttriDate(new Date());
						pharmProduitAttribut.setProdDatePerem(datePeremp);
						pharmProduitAttribut.setProdLot(numLot);
						pharmProduitAttribut.setFlagValid(true);
						Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
					}
	
									
					// save PharmligneReception
					PharmLigneReception ld = new PharmLigneReception();
					PharmLigneReceptionId ldId = new PharmLigneReceptionId();
					ldId.setRecptId(formulaireReception.getPharmReception().getRecptId());
					ldId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
					ld.setId(ldId);
					ld.setPharmReception(formulaireReception.getPharmReception());
					ld.setPharmProduitAttribut(pharmProduitAttribut);				
					ld.setLgnRecptPrix(prixInt);
					ld.setLgnRecptQteLivre(qteLivreInt);
					ld.setLgnRecptQteRecu(qteInt);
					ld.setLgnRecptObserv(observ);
					
					/*LigneReceptionMvt ligne= formulaireReception.getTabReceptionMvt().getLigneReceptionById(pharmProduitAttribut.getPharmProduit().getProdId()+pharmProduitAttribut.getProdLot());
					if(ligne!=null){
					ld.setLgnRecptQteDetailLivre(ligne.getLgnRecptQteDetailLivree());
					ld.setLgnRecptQteDetailRecu(ligne.getLgnRecptQteDetailRecu());
					}*/
					
					
					//convertion en unité au detail
					
					if(var.equals("Gros")){
					//System.out.println("------------------ var --------- "+var);
					PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(produit);
					
						if(produitCompl!=null){
							int convers=produitCompl.getProdCplUnitConvers();
							ld.setLgnRecptQteDetailRecu(qteInt*convers);
							ld.setLgnRecptQteDetailLivre(qteLivreInt*convers);
							ld.setLgnRecptQteRecu(qteInt);
							ld.setLgnRecptQteLivre(qteLivreInt);
						} else{
							ld.setLgnRecptQteRecu(qteInt);
							ld.setLgnRecptQteLivre(qteLivreInt);
							ld.setLgnRecptQteDetailRecu(qteInt);
							ld.setLgnRecptQteDetailLivre(qteLivreInt);
							
						}
						
					} else {
						PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(produit);
						
						if(produitCompl!=null){
							int convers=produitCompl.getProdCplUnitConvers();
							ld.setLgnRecptQteRecu(qteInt/convers);
							ld.setLgnRecptQteLivre(qteLivreInt/convers);
							ld.setLgnRecptQteDetailRecu(qteInt);
							ld.setLgnRecptQteDetailLivre(qteLivreInt);
						} else{
							ld.setLgnRecptQteDetailRecu(qteInt);
							ld.setLgnRecptQteDetailLivre(qteLivreInt);
							ld.setLgnRecptQteRecu(qteInt);
							ld.setLgnRecptQteLivre(qteLivreInt);
							
						}
					}
					
					//System.out.println("------------------ ld.setLgnRecptQteDetailLivre --------- "+ld.getLgnRecptQteDetailLivre());
					
					
					/*PharmLigneReception ligneR =Context.getService(GestionReceptionService.class).getPharmLigneReception(ldId);
					if(ligneR!=null){
						ld.setId(ligneR.getId());
						Context.getService(GestionReceptionService.class).updatePharmLigneReception(ld);					
					}else {	
					Context.getService(GestionReceptionService.class).savePharmLigneReception(ld);
					}*/
					
					Context.getService(GestionReceptionService.class).savePharmLigneReception(ld);
									
				// maj du ligne reception mouvement
					LigneReceptionMvt ligneReceptionMvt= new LigneReceptionMvt();
					ligneReceptionMvt.setLgnDatePerem(datePeremp);
					ligneReceptionMvt.setLgnDatePeremDetail(datePeremp);
					ligneReceptionMvt.setLgnRecptLot(numLot);
					ligneReceptionMvt.setLgnRecptLotDetail(numLot);
					ligneReceptionMvt.setLgnRecptPrixAchat(prixInt);
					ligneReceptionMvt.setLgnRecptPrixAchatDetail(prixInt);
					ligneReceptionMvt.setLgnRecptQte(ld.getLgnRecptQteRecu());
					ligneReceptionMvt.setLgnRecptQteLivree(ld.getLgnRecptQteLivre());
					ligneReceptionMvt.setProduit(produit);
					ligneReceptionMvt.setProduitDetail(produit);
					ligneReceptionMvt.setReception(formulaireReception.getPharmReception());
					ligneReceptionMvt.setLgnRecptObserv(observ);
					ligneReceptionMvt.setLgnRecptObservDetail(observ);
					
					ligneReceptionMvt.setLgnRecptQteDetailRecu(ld.getLgnRecptQteDetailRecu());
					ligneReceptionMvt.setLgnRecptQteDetailLivree(ld.getLgnRecptQteDetailLivre());
					
					formulaireReception.getTabReceptionMvt().addLigneReception(ligneReceptionMvt);
					
								
				
				}
				
				model.addAttribute("mess", "success");
				model.addAttribute("var", "1");
				Context.getService(GestionReceptionService.class).updatePharmReception(formulaireReception.getPharmReception());
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}else{
				model.addAttribute("fournisseurs", fournisseurs);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits", this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
				model.addAttribute("formulaireReception", formulaireReception);
				model.addAttribute("ligneReceptions",
						formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
				model.addAttribute("var", "0");
				model.addAttribute("mess", "aut");
				model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
			}
			
		} else {
			model.addAttribute("fournisseurs", fournisseurs);
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits", this.transformToList(formulaireReception.getPharmProgramme().getPharmProduits()));
			model.addAttribute("formulaireReception", formulaireReception);
			model.addAttribute("ligneReceptions",
					formulaireReception.getTabReceptionMvt().getLigneReceptionsCollection());
			model.addAttribute("var", "0");
			model.addAttribute("totalGlobal", this.gestionPrix(formulaireReception.getTabReceptionMvt().getLigneReceptions()));
		}

		// } catch (Exception e) {
		// e.getMessage();
		// }

	}
	
	

	public void initialisation(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		FormulaireReception formulaireReception = new FormulaireReception();
		model.addAttribute("formulaireReception", formulaireReception);
		if(Context.hasPrivilege("pharmacie")) {
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
