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
//@RequestMapping(value = "/module/pharmagest/inventaireValide.form")
public class _InventaireValideController {

	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET)
	public String initSaisiesPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/inventaireValide";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void recherche(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {
			PharmInventaire inventaire = Context.getService(InventaireService.class)
					.getPharmInventaireById(Integer.parseInt(paramId));
			formulaireInventaire.setPharmInventaire(inventaire);
			model.addAttribute("inventaire", inventaire);
			model.addAttribute("tab", "1");
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = { "btn_valder" })
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
			formulaireInventaire.getPharmInventaire().setInvFlag("VA");
			Context.getService(InventaireService.class).updatePharmInventaire(formulaireInventaire.getPharmInventaire());

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
				stockerId.setProgramId(formulaireInventaire.getPharmProgramme().getProgramId());
				PharmStocker stocker= Context.getService(GestionStockService.class).getPharmStockerById(stockerId);
				
				if(stocker!=null){
					stocker.setStockQte(qteProInt);
					stocker.setStockQteIni(qteProInt);
					stocker.setStockDateStock(formulaireInventaire.getPharmInventaire().getInvDeb());// Date du stock ?
					Context.getService(GestionStockService.class).updatePharmStocker(stocker);
				} else {
					PharmStocker stockerNew= new PharmStocker();
					stockerNew.setId(stockerId);
					stockerNew.setPharmProduitAttribut(param.getPharmProduitAttribut());
					stockerNew.setPharmProgramme(formulaireInventaire.getPharmProgramme());
					stockerNew.setStockQte(qteProInt);
					stockerNew.setStockQteIni(qteProInt);
					stockerNew.setStockDateStock(formulaireInventaire.getPharmInventaire().getInvDeb());// Date du stock ?
					Context.getService(GestionStockService.class).savePharmStocker(stockerNew);
				}
				
				// insertion dans histoMvm
				PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
				histoMouvementStock.setMvtDate(formulaireInventaire.getPharmInventaire().getInvDeb());
				histoMouvementStock.setMvtLot(param.getPharmProduitAttribut().getProdLot());

				histoMouvementStock.setMvtProgramme(formulaireInventaire.getPharmProgramme().getProgramId());
				histoMouvementStock.setMvtQte(qteProInt);
				histoMouvementStock.setMvtQteStock(qteProInt);
				histoMouvementStock.setMvtTypeMvt(
						Context.getService(ParametresService.class).getTypeOperationById(8).getToperId());
				histoMouvementStock.setMvtMotif(param.getObservation());
				histoMouvementStock.setPharmProduit(param.getPharmProduitAttribut().getPharmProduit());
				Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);
					
				//System.out.println("Voici la ligne QtePro : " + param.getQtePro());
			}
			
			PharmInventaire inventaire = Context.getService(InventaireService.class)
					.getPharmInventaireById(formulaireInventaire.getPharmInventaire().getInvId());
			model.addAttribute("inventaire", inventaire);
			model.addAttribute("tab", "2");

		}

	}

	public void initialisation(ModelMap model) {
		FormulairePharmInventaire formulaireInventaire = new FormulairePharmInventaire();
		model.addAttribute("formulaireInventaire", formulaireInventaire);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		
		
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
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
