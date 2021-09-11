package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.openmrs.module.pharmagest.PharmInventaire;
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
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.Stocker;
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
@RequestMapping(value = "/module/pharmagest/inventaireValide.form")
public class InventaireValideController {

	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET)
	public String initSaisiesPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/inventaireValide";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void recherche(@RequestParam(value = "paramId") String paramId,ModelMap model) {
		
		model.addAttribute("user", Context.getAuthenticatedUser());			
					
		if(Context.hasPrivilege("pharmacie")) {
			FormulairePharmInventaire formulaireInventaire = new FormulairePharmInventaire();
					// gestion du gestionnaire
					PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
					gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
					gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
					gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
					Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
					formulaireInventaire.setGestionnairePharma(gestionnairePharma);
					
					List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
							.getAllProgrammes();					
					model.addAttribute("programmes", programmes);
				

				//System.out.println("==================recherche gestionnaire============"+formulaireInventaire.getGestionnairePharma());
					
						PharmInventaire inventaire = Context.getService(InventaireService.class)
								.getPharmInventaireById(Integer.parseInt(paramId));
						formulaireInventaire.setPharmInventaire(this.findStock(inventaire));
						model.addAttribute("inventaire", inventaire);
						model.addAttribute("totalTheo",this.calculTotalPrix(formulaireInventaire.getPharmInventaire().getPharmLigneInventaires(), "theo"));
						model.addAttribute("totalPhys",this.calculTotalPrix(formulaireInventaire.getPharmInventaire().getPharmLigneInventaires(), "phys"));
						model.addAttribute("formulaireInventaire", formulaireInventaire);
						model.addAttribute("tab", "1");		

		
		}
	}
	
	public PharmInventaire findStock(PharmInventaire inventaire){
		
		Map<String, PharmLigneInventaire> collectInventaires =  Collections.synchronizedMap(new HashMap());
		Iterator it= inventaire.getPharmLigneInventaires().iterator();
		while (it.hasNext()) {
			PharmLigneInventaire ligne= (PharmLigneInventaire)it.next();
			
			
			// determiner le stock logique du produit
			PharmProduitAttribut pharmProduitAttribut = ligne.getPharmProduitAttribut();

				PharmStockerId pharmStockerId = new PharmStockerId();
				pharmStockerId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
				pharmStockerId.setProgramId(inventaire.getPharmProgramme().getProgramId());
				PharmStocker pharmStocker = Context.getService(GestionStockService.class)
						.getPharmStockerById(pharmStockerId);
				if (pharmStocker != null) {
					ligne.setQteLogique(pharmStocker.getStockQte());
					ligne
							.setEcart(ligne.getQtePhysique()-pharmStocker.getStockQte());
				} else {
					ligne.setQteLogique(0);
					ligne.setEcart(ligne.getQtePhysique());
				}
				collectInventaires.put(pharmProduitAttribut.getPharmProduit().getProdCode()
						+ pharmProduitAttribut.getProdLot(), ligne);							
				
			
		}
		
		//determier les stocks non compté
		Collection<PharmStocker> listStocks=Context.getService(GestionStockService.class).getPharmStockersByProgram(inventaire.getPharmProgramme());
		
		Iterator it2 = listStocks.iterator();
		while (it2.hasNext()) {
			PharmStocker stock=(PharmStocker)it2.next();
			//s'il contient pas le lot du produit
			if(!collectInventaires.containsKey(stock.getPharmProduitAttribut().getPharmProduit().getProdCode()+stock.getPharmProduitAttribut().getProdLot())){
				
				PharmLigneInventaire ligneInv=new PharmLigneInventaire();
				ligneInv.setPharmInventaire(inventaire);
				ligneInv.setPharmProduitAttribut(stock.getPharmProduitAttribut());
				ligneInv.setQteLogique(stock.getStockQte());
				ligneInv.setQtePhysique(0);
				//ligneInv.setQtePro(0);
				ligneInv.setEcart(-stock.getStockQte());
				
				PharmLigneInventaireId liId = new PharmLigneInventaireId();
				liId.setInvId(inventaire.getInvId());
				liId.setProdAttriId(stock.getPharmProduitAttribut().getProdAttriId());
				ligneInv.setId(liId);
				PharmLigneInventaire lgn =Context.getService(InventaireService.class).getPharmLigneInventaire(liId);
				if(lgn==null){						
				Context.getService(InventaireService.class).savePharmLigneInventaire(ligneInv);
				}else{
					Context.getService(InventaireService.class).updatePharmLigneInventaire(ligneInv);
				}
				
				
				collectInventaires.put(stock.getPharmProduitAttribut().getPharmProduit().getProdCode()
						+ stock.getPharmProduitAttribut().getProdLot(), ligneInv);
			}
		}
		
		inventaire.setPharmLigneInventaires(new HashSet<PharmLigneInventaire>(collectInventaires.values()));
		//System.out.println("=============find stock programme==================="+inventaire.getPharmProgramme().getProgramLib());
		return inventaire;
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
		//System.out.println("====================total==================="+total);
		return total;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = { "btn_valder" })
	public void valider(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);
		//System.out.println("=============inventaire======================"+formulaireInventaire.getPharmInventaire());
		//System.out.println("=============programme======================"+formulaireInventaire.getPharmInventaire().getPharmProgramme());
		//System.out.println("==================valider gestionnaire============"+formulaireInventaire.getGestionnairePharma());

		if (!result.hasErrors()) {

			// save operation
			PharmOperation operation = new PharmOperation();
			operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(8));
			operation.setOperDateRecept(formulaireInventaire.getPharmInventaire().getInvDeb());
			operation.setOperDateSaisi(new Date());
			

			operation.setPharmGestionnairePharma(formulaireInventaire.getGestionnairePharma());
			operation.setPharmProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme());
			operation.setOperNum(formulaireInventaire.getPharmInventaire().getInvId()+"");
			Context.getService(OperationService.class).savePharmOperation(operation);
			
			//Gestion des ajustements sur inventaire
			PharmOperation operationPerteInv = new PharmOperation();
			operationPerteInv.setOperDateRecept(formulaireInventaire.getPharmInventaire().getInvDeb());
			operationPerteInv.setOperDateSaisi(new Date());
			operationPerteInv.setPharmGestionnairePharma(formulaireInventaire.getGestionnairePharma());
			operationPerteInv.setPharmProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme());
			operationPerteInv.setOperNum(formulaireInventaire.getPharmInventaire().getInvId()+"");
			operationPerteInv.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(17));
			
			PharmOperation operationExceInv = new PharmOperation();
			operationExceInv.setOperDateRecept(formulaireInventaire.getPharmInventaire().getInvDeb());
			operationExceInv.setOperDateSaisi(new Date());
			operationExceInv.setPharmGestionnairePharma(formulaireInventaire.getGestionnairePharma());
			operationExceInv.setPharmProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme());
			operationExceInv.setOperNum(formulaireInventaire.getPharmInventaire().getInvId()+"");
			operationExceInv.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(18));
			
			//update inventaire
			formulaireInventaire.getPharmInventaire().setInvFlag("VA");
			formulaireInventaire.getPharmInventaire().setInvDateModif(new Date());
			formulaireInventaire.getPharmInventaire().setPharmGestionnaireModif(formulaireInventaire.getGestionnairePharma());
			//System.out.println("=============programme======================"+formulaireInventaire.getPharmInventaire().getPharmProgramme().getProgramLib());
			Context.getService(InventaireService.class).updatePharmInventaire(formulaireInventaire.getPharmInventaire());
			
			//vider le stock de produit
			Collection<PharmStocker> stockers=Context.getService(GestionStockService.class).getPharmStockersByProgram(formulaireInventaire.getPharmInventaire().getPharmProgramme());
			Iterator it= stockers.iterator();
			while (it.hasNext()) {
				PharmStocker stocker= (PharmStocker)it.next();
				stocker.setStockQte(0);
				Context.getService(GestionStockService.class).updatePharmStocker(stocker);
			}
						

			int taille = formulaireInventaire.getPharmInventaire().getPharmLigneInventaires().size();
			for (int i = 1; i <= taille; i++) {
				String idProd = request.getParameter("idProd" + i);
				int idProdInt=Integer.parseInt(idProd);
				String qtePro = request.getParameter("qtePro" + i);
				int qteProInt=Integer.parseInt(qtePro);
				
				PharmLigneInventaireId pharmLigneInventaireId = new PharmLigneInventaireId();

				pharmLigneInventaireId.setProdAttriId(idProdInt);
				pharmLigneInventaireId.setInvId(formulaireInventaire.getPharmInventaire().getInvId());

				PharmLigneInventaire param = Context.getService(InventaireService.class)
						.getPharmLigneInventaire(pharmLigneInventaireId);
				param.setQtePro(qteProInt);
				
				Context.getService(InventaireService.class).updatePharmLigneInventaire(param);
				
				//gestion des lignes d'opération
				PharmLigneOperationId ligneOperationId= new PharmLigneOperationId();
				ligneOperationId.setOperId(operation.getOperId());
				ligneOperationId.setProdAttriId(idProdInt);
				PharmLigneOperation ligneOperation = new PharmLigneOperation();
				ligneOperation.setLgnOperQte(qteProInt);
				ligneOperation.setPharmOperation(operation);
				ligneOperation.setPharmProduitAttribut(param.getPharmProduitAttribut());
				ligneOperation.setId(ligneOperationId);
				Context.getService(OperationService.class).savePharmLigneOperation(ligneOperation);

				// contrôle du Stock
				PharmStockerId stockerId= new PharmStockerId();
				stockerId.setProdAttriId(idProdInt);
				stockerId.setProgramId(formulaireInventaire.getPharmInventaire().getPharmProgramme().getProgramId());
				PharmStocker stockerNew=Context.getService(GestionStockService.class).getPharmStockerById(stockerId);
				if(stockerNew!=null){
					stockerNew.setStockQte(qteProInt);
					stockerNew.setStockQteIni(qteProInt);
					stockerNew.setStockDateStock(formulaireInventaire.getPharmInventaire().getInvDeb());// Date du stock ?
					Context.getService(GestionStockService.class).updatePharmStocker(stockerNew);
				} else {
					stockerNew= new PharmStocker();
					stockerNew.setId(stockerId);
					stockerNew.setPharmProduitAttribut(param.getPharmProduitAttribut());
					stockerNew.setPharmProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme());
					stockerNew.setStockQte(qteProInt);
					stockerNew.setStockQteIni(qteProInt);
					stockerNew.setStockDateStock(formulaireInventaire.getPharmInventaire().getInvDeb());// Date du stock ?
					Context.getService(GestionStockService.class).savePharmStocker(stockerNew);
				
				}
				
				// insertion dans histoMvm
				PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
				histoMouvementStock.setMvtDate(formulaireInventaire.getPharmInventaire().getInvDeb());
				histoMouvementStock.setMvtLot(param.getPharmProduitAttribut().getProdLot());

				histoMouvementStock.setMvtProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme().getProgramId());
				histoMouvementStock.setMvtQte(qteProInt);
				histoMouvementStock.setMvtQteStock(qteProInt);
				histoMouvementStock.setMvtTypeMvt(
						Context.getService(ParametresService.class).getTypeOperationById(8).getToperId());
				histoMouvementStock.setMvtMotif(param.getObservation());
				histoMouvementStock.setPharmProduit(param.getPharmProduitAttribut().getPharmProduit());
				histoMouvementStock.setPharmOperation(operation);
				//System.out.println("----------------histoMouvementStock.getPharmOperation-----------------"+histoMouvementStock.getPharmOperation());
				Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
					
				//Gestion des ajustements automatiques d'inventaire
				if(param.getEcart()<0){
					Context.getService(OperationService.class).savePharmOperation(operationPerteInv);//sauvegarder l'entete des pertes
					//gestion des lignes d'opération
					PharmLigneOperationId ligneOperationPerteId= new PharmLigneOperationId();
					ligneOperationPerteId.setOperId(operationPerteInv.getOperId());
					ligneOperationPerteId.setProdAttriId(idProdInt);
					PharmLigneOperation ligneOperationPerte = new PharmLigneOperation();
					ligneOperationPerte.setLgnOperQte(Math.abs(param.getEcart()));
					ligneOperationPerte.setPharmOperation(operationPerteInv);
					ligneOperationPerte.setPharmProduitAttribut(param.getPharmProduitAttribut());
					ligneOperationPerte.setId(ligneOperationPerteId);
					Context.getService(OperationService.class).savePharmLigneOperation(ligneOperationPerte);
					// insertion dans histoMvm
					PharmHistoMouvementStock histoMouvementStockPerte = new PharmHistoMouvementStock();
					histoMouvementStockPerte.setMvtDate(formulaireInventaire.getPharmInventaire().getInvDeb());
					histoMouvementStockPerte.setMvtLot(param.getPharmProduitAttribut().getProdLot());

					histoMouvementStockPerte.setMvtProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme().getProgramId());
					histoMouvementStockPerte.setMvtQte(Math.abs(param.getEcart()));
					histoMouvementStockPerte.setMvtQteStock(qteProInt+Math.abs(param.getEcart()) );
					histoMouvementStockPerte.setMvtTypeMvt(17);
					histoMouvementStockPerte.setMvtMotif(param.getObservation());
					histoMouvementStockPerte.setPharmProduit(param.getPharmProduitAttribut().getPharmProduit());
					histoMouvementStockPerte.setPharmOperation(operationPerteInv);
					//System.out.println("----------------histoMouvementStockPerte.getPharmOperation-----------------"+histoMouvementStockPerte.getPharmOperation());
					Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStockPerte);
				}else if(param.getEcart()>0){
					Context.getService(OperationService.class).savePharmOperation(operationExceInv);//sauvegarder l'entete des pertes
					//gestion des lignes d'opération
					PharmLigneOperationId ligneOperationExceId= new PharmLigneOperationId();
					ligneOperationExceId.setOperId(operationExceInv.getOperId());
					ligneOperationExceId.setProdAttriId(idProdInt);
					PharmLigneOperation ligneOperationExce = new PharmLigneOperation();
					ligneOperationExce.setLgnOperQte(Math.abs(param.getEcart()));
					ligneOperationExce.setPharmOperation(operationExceInv);
					ligneOperationExce.setPharmProduitAttribut(param.getPharmProduitAttribut());
					ligneOperationExce.setId(ligneOperationExceId);
					Context.getService(OperationService.class).savePharmLigneOperation(ligneOperationExce);
					// insertion dans histoMvm
					PharmHistoMouvementStock histoMouvementStockExce = new PharmHistoMouvementStock();
					histoMouvementStockExce.setMvtDate(formulaireInventaire.getPharmInventaire().getInvDeb());
					histoMouvementStockExce.setMvtLot(param.getPharmProduitAttribut().getProdLot());

					histoMouvementStockExce.setMvtProgramme(formulaireInventaire.getPharmInventaire().getPharmProgramme().getProgramId());
					histoMouvementStockExce.setMvtQte(Math.abs(param.getEcart()));
					histoMouvementStockExce.setMvtQteStock(qteProInt-Math.abs(param.getEcart()) );
					histoMouvementStockExce.setMvtTypeMvt(18);
					histoMouvementStockExce.setMvtMotif(param.getObservation());
					histoMouvementStockExce.setPharmProduit(param.getPharmProduitAttribut().getPharmProduit());
					histoMouvementStockExce.setPharmOperation(operationExceInv);
					//System.out.println("----------------histoMouvementStockExce.getPharmOperation-----------------"+histoMouvementStockExce.getPharmOperation());
					Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStockExce);
				}
				
			}
			
			PharmInventaire inventaire = Context.getService(InventaireService.class)
					.getPharmInventaireById(formulaireInventaire.getPharmInventaire().getInvId());
			model.addAttribute("inventaire", inventaire);
			model.addAttribute("totalTheo",this.calculTotalPrix(formulaireInventaire.getPharmInventaire().getPharmLigneInventaires(), "theo"));
			model.addAttribute("totalPhys",this.calculTotalPrix(formulaireInventaire.getPharmInventaire().getPharmLigneInventaires(), "phys"));
			
			model.addAttribute("tab", "2");

		}

	}

	public void initialisation(ModelMap model) {
		FormulairePharmInventaire formulaireInventaire = new FormulairePharmInventaire();
		
		model.addAttribute("user", Context.getAuthenticatedUser());
		if(Context.hasPrivilege("pharmacie")) {
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
		model.addAttribute("formulaireInventaire", formulaireInventaire);
		//System.out.println("==================init gestionnaire============"+formulaireInventaire.getGestionnairePharma());
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
