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
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.RapportCommandeService;
import org.openmrs.module.pharmagest.metier.FormulaireModifPPS;
import org.openmrs.module.pharmagest.metier.FormulairePeriode;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.openmrs.module.pharmagest.metier.LigneOperationMvt;
import org.openmrs.module.pharmagest.metier.LigneReceptionMvt;
import org.openmrs.module.pharmagest.metier.RapportCommandeBean;
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

public class RapportReceptionCopyController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ReceptionValidator receptionValidator;
	@Autowired
	ReceptionValidatorAjout receptionValidatorAjout;
	@Autowired
	ReceptionEditeValidator receptionEditeValidator;
	
	
	@RequestMapping(value = "/module/pharmagest/satisfactionESPC2.form", method = RequestMethod.GET)
	public String satisfactionParam(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/satisfactionESPC2";
	}
	
	@RequestMapping(value = "/module/pharmagest/satisfactionESPCZ.form", method = RequestMethod.POST, params = {
		"btn_enregistrer" })
	public void satisfactionPost(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
		BindingResult result, HttpSession session, ModelMap model) {
		
		if (!result.hasErrors()) {
			
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);
		
			Map<String, LigneReceptionMvt> mapData = new HashMap<String, LigneReceptionMvt>();
			
			//ArrayList<LigneReceptionMvt> listData=new ArrayList<LigneReceptionMvt>();
			Collection commandes =Context.getService(RapportCommandeService.class).getPharmRapportCommandeByPeriod(formulaireReception.getPharmProgramme(),
					formulaireReception.getDateDebut());
			
			String[]listBL=(formulaireReception.getRecptNum()!=null)?formulaireReception.getRecptNum().split(";"):null;
			
			for(int i=0; i < listBL.length; i++) {
				
			Collection recepts = Context.getService(GestionReceptionService.class).getPharmReceptionByBL(formulaireReception.getPharmProgramme(),
					listBL[i]);			
			
			if(recepts.size()!=0 && commandes.size()!=0) {
				
				PharmReception reception=(PharmReception) recepts.toArray()[0];
				PharmRapportCommande commande=(PharmRapportCommande) commandes.toArray()[0];
				Collection<PharmProduit> lisProduit = formulaireReception.getPharmProgramme().getPharmProduits();
				for (PharmProduit produit : lisProduit) {
					LigneReceptionMvt ligneData=new LigneReceptionMvt();
					boolean bRecept=false;
					boolean bComm=false;
					ligneData.setProduit(produit);
					
					for (PharmLigneReception ligneR : reception.getPharmLigneReceptions()) {
						if(ligneR.getPharmProduitAttribut().getPharmProduit()==produit) {
							int qte=(ligneData.getLgnRecptQteLivree()!=null)?ligneData.getLgnRecptQteLivree()+ligneR.getLgnRecptQteDetailRecu():ligneR.getLgnRecptQteDetailRecu();
							ligneData.setLgnRecptQteLivree(qte);
							bRecept=true;
						}
					}
					
					for (PharmLigneRc ligneRc : commande.getPharmLigneRcs()) {
						if(ligneRc.getPharmProduit()==produit) {
							int qte=(ligneData.getLgnRecptQte()!=null && ligneRc.getLgnRcQteCom()!=null)?
									ligneData.getLgnRecptQte()+ligneRc.getLgnRcQteCom():ligneRc.getLgnRcQteCom();
							ligneData.setLgnRecptQte(ligneRc.getLgnRcQteCom());
							bComm=true;
						}
					}
					
					if(bRecept || bComm) {
						if(mapData.containsKey(produit.getProdCode())) {
						 LigneReceptionMvt varRecept=mapData.get(produit.getProdCode());
						 //System.out.println("=====varRecept====="+varRecept);
						 //System.out.println("=====ligneData====="+ligneData);
						 Integer qte=(ligneData.getLgnRecptQte()!=null && varRecept.getLgnRecptQteLivree()!=null)?
								 ligneData.getLgnRecptQte()+varRecept.getLgnRecptQte():0;
						 if(qte==null && ligneData.getLgnRecptQte()==null)qte=varRecept.getLgnRecptQte();
						 if(qte==null && varRecept.getLgnRecptQte()==null)qte=ligneData.getLgnRecptQte();
						 
						 ligneData.setLgnRecptQteLivree(qte);							
						 mapData.remove(produit.getProdCode());
						 mapData.put(produit.getProdCode(),ligneData);
						} else {
							mapData.put(produit.getProdCode(),ligneData);
						}
						
					}
					
				}
			}
			}
			
			model.addAttribute("list", mapData.values());
			model.addAttribute("var", 1);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulaireReception.getDateDebut())+" au "+sf.format(formulaireReception.getDateFin());
			model.addAttribute("periode", periode);
			model.addAttribute("formulaireReception", formulaireReception);			
		
		}
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
	
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);
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
