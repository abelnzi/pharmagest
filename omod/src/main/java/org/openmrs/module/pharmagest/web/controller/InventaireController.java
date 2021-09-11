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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaireId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.metier.CoutAchatProduit;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.openmrs.module.pharmagest.metier.LigneInventaireMvt;
import org.openmrs.module.pharmagest.metier.LigneReceptionMvt;
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

/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulaireInventaire")
@RequestMapping(value = "/module/pharmagest/inventaire.form")
public class InventaireController {

	@Autowired
	InventaireValidator inventaireValidator;

	@Autowired
	Inventaire2Validator inventaire2Validator;

	@Autowired
	InventaireAjoutValidator inventaireAjoutValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String initStockFour(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/inventaire";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "reset" })
	public String annuler(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/inventaire";
	}

		
	@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void editer(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		inventaire2Validator.validate(formulaireInventaire, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		// String invDev = request.getParameter("invDeb");
		// formulaireInventaire.setInvDeb(new Date(invDev));
		if (!result.hasErrors()) {
			ArrayList<PharmInventaire> inventaires = (ArrayList<PharmInventaire>) Context
					.getService(InventaireService.class).getPharmInventaireByPeriod(
							formulaireInventaire.getPharmProgramme(), formulaireInventaire.getInvDeb());
			if (inventaires.isEmpty()) {
				model.addAttribute("formulaireInventaire", formulaireInventaire);
				model.addAttribute("programmes", programmes);
				model.addAttribute("produits",
						this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));
				model.addAttribute("var", "1");
			}else{
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				model.addAttribute("mess", "IE");
				model.addAttribute("DateInv",formatter.format(formulaireInventaire.getInvDeb()));
				this.initialisation(model);
			}
		}

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

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valider" })
	public void addLigneDispensation(
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
		//try {
			inventaireAjoutValidator.validate(formulaireInventaire, result);
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();

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
					//System.out.println("===============pharmProduitAttribut.getProdAttriId()=================="+pharmProduitAttribut.getProdAttriId());
					//System.out.println("===============coutProduitActuel=================="+coutProduitActuel.getPrixAchat());
					CoutAchatProduit coutProduitPrecedent=Context.getService(GestionReceptionService.class)
									.getCoutProduit(pharmProduitAttribut.getProdAttriId(), coutProduitActuel.getDateCout());
					int prixA=(coutProduitActuel.getPrixAchat()!=null)?coutProduitActuel.getPrixAchat():0;
					//System.out.println("===============prixA=================="+prixA);
					
					int prixP=(coutProduitPrecedent.getPrixAchat()!=null)?coutProduitPrecedent.getPrixAchat():0;
					
					//System.out.println("===============prixP=================="+prixP);
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

				//gerer les lignes de même numéro de lot
				LigneInventaireMvt ligneNew=formulaireInventaire.getTabInventaire().getLigneInventaireById(ligneInventaire.getProduit().getProdId()+ligneInventaire.getLgnLot());
				if(ligneNew!=null){
					ligneInventaire.setQtePhysique(ligneInventaire.getQtePhysique()+ligneNew.getQtePhysique());
					ligneInventaire
					.setEcart(ligneInventaire.getQtePhysique()-ligneInventaire.getQteLogique());
				}
				
				if(ligneInventaire.getPrixInv()==null)ligneInventaire.setPrixInv(0);
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
			model.addAttribute("totalGlobal",this.calculTotalPrix(formulaireInventaire.getTabInventaire().getLigneInventaires()));
			model.addAttribute("programmes", programmes);
			model.addAttribute("produits",
					this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));
			model.addAttribute("var", "1");
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}

	}
	
	@RequestMapping(method = RequestMethod.GET, params = { "modifId" })
	public void modifLigneOperation(@RequestParam(value = "modifId") String modifId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {

		try {

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			model.addAttribute("programmes", programmes);
			model.addAttribute("produits",
					this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));
			LigneInventaireMvt lignInv=formulaireInventaire.getTabInventaire().getLigneInventaireById(modifId);
			
			formulaireInventaire.getTabInventaire().removeLigneInventaireById(modifId);
			model.addAttribute("ligneInventaires",
					formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("mess", "delete");
			model.addAttribute("var", "1");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			model.addAttribute("datePerem",formatter.format(lignInv.getLgnDatePerem()));
			formulaireInventaire.setProduit(lignInv.getProduit());
			formulaireInventaire.setQtePhysique(lignInv.getQtePhysique());
			formulaireInventaire.setLgnLot(lignInv.getLgnLot());
			formulaireInventaire.setLgnDatePerem(lignInv.getLgnDatePerem());
			formulaireInventaire.setObservation(lignInv.getObservation());
			model.addAttribute("totalGlobal",this.calculTotalPrix(formulaireInventaire.getTabInventaire().getLigneInventaires()));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.GET, params = { "paramId" })
	public void deleteLigneOperation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {

		try {

			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class)
					.getAllProduits();

			model.addAttribute("programmes", programmes);
			model.addAttribute("produits",
					this.transformToList(formulaireInventaire.getPharmProgramme().getPharmProduits()));

			formulaireInventaire.getTabInventaire().removeLigneInventaireById(paramId);
			model.addAttribute("ligneInventaires",
					formulaireInventaire.getTabInventaire().getLigneInventaireCollection());
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("mess", "delete");
			model.addAttribute("var", "1");
			formulaireInventaire.setProduit(null);
			formulaireInventaire.setQtePhysique(null);
			formulaireInventaire.setLgnLot(null);
			formulaireInventaire.setLgnDatePerem(null);
			formulaireInventaire.setObservation(null);
			
			model.addAttribute("totalGlobal",this.calculTotalPrix(formulaireInventaire.getTabInventaire().getLigneInventaires()));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void saveDispensation(@ModelAttribute("formulaireInventaire") FormulairePharmInventaire formulaireInventaire,
			BindingResult result, HttpSession session, ModelMap model) {
		// try {
		inventaireValidator.validate(formulaireInventaire, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		if (!result.hasErrors()) {
			PharmInventaire pharmInventaire = new PharmInventaire();
			pharmInventaire = formulaireInventaire.getPharmInventaire();
			pharmInventaire.setInvDateCrea(new Date());
			pharmInventaire.setPharmProgramme(formulaireInventaire.getPharmProgramme());
			pharmInventaire.setInvFlag("NV");
			pharmInventaire.setPharmGestionnaireSaisi(formulaireInventaire.getGestionnairePharma());

			Context.getService(InventaireService.class).savePharmInventaire(pharmInventaire);
			// save ligne
			Map lignes = formulaireInventaire.getTabInventaire().getLigneInventaires();
			for (Iterator i = lignes.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				LigneInventaireMvt ligne = (LigneInventaireMvt) lignes.get(key);

				// determiner le stock logique du produit
				PharmProduitAttribut pharmProduitAttribut = Context.getService(OperationService.class)
						.getPharmProduitAttributByElement(ligne.getProduit(), ligne.getLgnLot());
				if (pharmProduitAttribut == null) {
					pharmProduitAttribut = new PharmProduitAttribut();
					pharmProduitAttribut.setPharmProduit(ligne.getProduit());
					pharmProduitAttribut.setProdAttriDate(new Date());
					pharmProduitAttribut.setProdDatePerem(ligne.getLgnDatePerem());
					pharmProduitAttribut.setProdLot(ligne.getLgnLot());
					pharmProduitAttribut.setFlagValid(true);
					//System.out.println("test : " + ligne.getProduit().getFullName());
					Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
				}else {
					pharmProduitAttribut.setProdDatePerem(ligne.getLgnDatePerem());
					Context.getService(OperationService.class).savePharmProduitAttribut(pharmProduitAttribut);
				}

				PharmLigneInventaireId liId = new PharmLigneInventaireId();
				liId.setInvId(pharmInventaire.getInvId());
				liId.setProdAttriId(pharmProduitAttribut.getProdAttriId());
				// liId.setProgramId(formulaireInventaire.getPharmProgramme().getProgramId());

				PharmLigneInventaire li = new PharmLigneInventaire();
				li.setId(liId);
				li.setObservation(ligne.getObservation());
				li.setQteLogique(ligne.getQteLogique());
				li.setQtePhysique(ligne.getQtePhysique());
				li.setEcart(ligne.getEcart());
				li.setPharmInventaire(pharmInventaire);
				li.setPharmProduitAttribut(pharmProduitAttribut);
				li.setPrixInv(ligne.getPrixInv());
				// li.setPharmProgramme(formulaireInventaire.getPharmProgramme());
				Context.getService(InventaireService.class).savePharmLigneInventaire(li);

			}
			model.addAttribute("mess", "save");
			// model.addAttribute("var", "0");
			this.initialisation(model);
		} else {

			model.addAttribute("programmes", programmes);
			model.addAttribute("formulaireInventaire", formulaireInventaire);
			model.addAttribute("totalGlobal",this.calculTotalPrix(formulaireInventaire.getTabInventaire().getLigneInventaires()));
			model.addAttribute("var", "1");
		}
		// model.addAttribute("formulaireInventaire", formulaireInventaire);

		// } catch (Exception e) {
		// e.getStackTrace();
		// }

	}

	@SuppressWarnings("unchecked")
	public List<PharmProduit> transformToList(Set<PharmProduit> set) {
		List<PharmProduit> list = new ArrayList<PharmProduit>(set);
		Collections.sort(list, Collections.reverseOrder());
		return list;
	}

	public void initialisation(ModelMap model) {

		model.addAttribute("user", Context.getAuthenticatedUser());
		if(Context.hasPrivilege("pharmacie")) {
			// gestion du gestionnaire
			PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
			gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
			gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
			gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
			Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
			FormulairePharmInventaire formulaireInventaire = new FormulairePharmInventaire();
			formulaireInventaire.setGestionnairePharma(gestionnairePharma);
			model.addAttribute("formulaireInventaire", formulaireInventaire);

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
