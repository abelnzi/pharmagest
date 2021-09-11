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
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmAssurance;
import org.openmrs.module.pharmagest.PharmClient;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneAssurance;
import org.openmrs.module.pharmagest.PharmLigneAssuranceId;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.metier.FormulaireDispensationLibre;
import org.openmrs.module.pharmagest.metier.FormulaireModifPPS;
import org.openmrs.module.pharmagest.metier.FormulairePharmOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.openmrs.module.pharmagest.metier.FormulaireVente;
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
@SessionAttributes("formulaireVente")

public class AnnulationOrdonnanceController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ReceptionValidator receptionValidator;
	@Autowired
	ReceptionValidatorAjout receptionValidatorAjout;
	@Autowired
	ReceptionEditeValidator receptionEditeValidator;
	
	/**********************************************génerer la Liste************************************************/
	
	@RequestMapping(value = "/module/pharmagest/listOrdonnance.form",method = RequestMethod.GET)
	public String initListPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listOrdonnance";
	}

	@RequestMapping(value = "/module/pharmagest/listOrdonnance.form",method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {

		//stock2Validator.validate(formulaireReception, result);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();		
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();
		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		
		model.addAttribute("assurances", assurances);
		model.addAttribute("programmes", programmes);
			

		if (!result.hasErrors()) {
			
			Collection<PharmOrdonnance> ordonnances= Context.getService(DispensationService.class).getPharmOrdonnanceByPeriod(formulaireVente.getDebut(), formulaireVente.getFin(),"NA");
			model.addAttribute("ordonnances", ordonnances);
			model.addAttribute("formulaireVente", formulaireVente);
			model.addAttribute("var", "1");

		}else{
			
			model.addAttribute("formulaireVente", formulaireVente);
		}

	}
	/*******************************************Voir l'ordonnance**************************************************/
	@RequestMapping(value = "/module/pharmagest/annulerOrdonnance.form",method = RequestMethod.GET, params = { "paramId" })
	public void initAnnulation(@RequestParam(value = "paramId") String paramId,
			@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model) {
		
		if (!result.hasErrors()) {
			
			PharmOrdonnance ordonnance=Context.getService(DispensationService.class).getPharmOrdonnanceById(Integer.parseInt(paramId));
			formulaireVente.setPharmOrdonnance(ordonnance);
			
			if(ordonnance.getPharmClient()!=null){ //si le client n'est pas null
				
			PharmClient client =Context.getService(DispensationService.class).getPharmClientById(ordonnance.getPharmClient().getCliId());
			formulaireVente.setClient(client);
			
			if(client.getPharmLigneAssurances()!=null && !(client.getPharmLigneAssurances().isEmpty())){
				formulaireVente.setTypeVente("I");
				Iterator it= client.getPharmLigneAssurances().iterator();
				while (it.hasNext()) {		
					PharmLigneAssurance ligne= (PharmLigneAssurance)it.next();
					formulaireVente.setTaux(ligne.getLaTaux());
					formulaireVente.setNumPatient(ligne.getLaNumAssur());
				}
			
			}else {
				formulaireVente.setTypeVente("D");
			}

			}else{
				formulaireVente.setTypeVente("NA");
			}
			
			model.addAttribute("ligneDispensations",ordonnance.getPharmLigneDispensations());
			//model.addAttribute("var", "1");

		}

		model.addAttribute("formulaireVente", formulaireVente);	

	}
	
	
	@RequestMapping(value = "/module/pharmagest/annulerOrdonnance.form",method = RequestMethod.POST, params = { "reset" })
	public String annuler(@ModelAttribute("formulaireVente") FormulaireVente formulaireVente,
			BindingResult result, HttpSession session, ModelMap model)  {
				
		// annuler ordonnance
		if (!result.hasErrors()) {
			PharmOperation operationAnnul = new PharmOperation();
			operationAnnul.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(19));
			operationAnnul.setOperDateRecept(formulaireVente.getPharmOrdonnance().getOrdDateDispen());
			operationAnnul.setOperDateSaisi(new Date());
			
			operationAnnul.setPharmProgramme(formulaireVente.getPharmOrdonnance().getPharmProgramme());
			operationAnnul.setOperRef(formulaireVente.getPharmOrdonnance().getOrdId()+"");
			// gestion du gestionnaire
			PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
			gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
			gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
			gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
			Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
			operationAnnul.setPharmGestionnairePharma(gestionnairePharma);
			Context.getService(OperationService.class).savePharmOperation(operationAnnul);
			
			
			
			PharmTypeOperation typeOperation= Context.getService(ParametresService.class).getTypeOperationById(2);
			PharmOperation operation = Context.getService(OperationService.class).getPharmOperationsByRef(formulaireVente.getPharmProgramme(),
					formulaireVente.getPharmOrdonnance().getOrdId()+"" , typeOperation);
			
			Set<PharmLigneDispensation> ligneDispensation=formulaireVente.getPharmOrdonnance().getPharmLigneDispensations();
			
			Iterator it= ligneDispensation.iterator();
			while (it.hasNext()) {		
				PharmLigneDispensation ligne= (PharmLigneDispensation)it.next();				
				  Collection<PharmLigneOperation> ligneOperation=Context.getService(OperationService.class).getPharmLigneOperationByProduit(operation, ligne.getPharmProduit());
				  //restocker les quantités
				  Iterator itOp= ligneOperation.iterator();
					while (itOp.hasNext()) {		
						PharmLigneOperation ligneOp= (PharmLigneOperation)itOp.next();
						PharmStocker stock=Context.getService(GestionStockService.class).getPharmStockersByProduitAttribut(
								ligneOp.getPharmProduitAttribut(),formulaireVente.getPharmProgramme());
						stock.setStockQte(stock.getStockQte()+ligneOp.getLgnOperQte());
						Context.getService(GestionStockService.class).updatePharmStocker(stock);
						
						
						// insertion dans histoMvm
						PharmHistoMouvementStock histoMouvementStock = new PharmHistoMouvementStock();
						histoMouvementStock.setMvtDate(new Date());
						histoMouvementStock.setMvtLot(ligneOp.getPharmProduitAttribut().getProdLot());

						histoMouvementStock.setMvtProgramme(formulaireVente.getPharmProgramme().getProgramId());
						histoMouvementStock.setMvtQte(ligneOp.getLgnOperQte());
						histoMouvementStock.setMvtQteStock(stock.getStockQte());
						histoMouvementStock.setMvtTypeMvt(operationAnnul.getPharmTypeOperation().getToperId());
						histoMouvementStock.setPharmProduit(ligneOp.getPharmProduitAttribut().getPharmProduit());
						histoMouvementStock.setPharmOperation(operationAnnul);
						Context.getService(GestionStockService.class).savePharmHistoMvmStock(histoMouvementStock);

						// save PharmligneOperation
						PharmLigneOperation lo = new PharmLigneOperation();
						PharmLigneOperationId loId = new PharmLigneOperationId();
						loId.setOperId(operationAnnul.getOperId());
						loId.setProdAttriId(ligneOp.getPharmProduitAttribut().getProdAttriId());
						lo.setId(loId);
						lo.setPharmOperation(operationAnnul);
						lo.setPharmProduitAttribut(ligneOp.getPharmProduitAttribut());
						lo.setLgnOperDatePerem(ligneOp.getPharmProduitAttribut().getProdDatePerem());
						lo.setLgnOperLot(ligneOp.getPharmProduitAttribut().getProdLot());
						lo.setLgnOperQte(ligneOp.getLgnOperQte());
						Context.getService(OperationService.class).savePharmLigneOperation(lo);						
						
						}
				
				}
			
			formulaireVente.getPharmOrdonnance().setOrdFlag("AN");
			//System.out.println("-------------------------ordonnanceID----------------"+formulaireVente.getPharmOrdonnance().getOrdId());
			Context.getService(DispensationService.class).updatePharmOrdonnance(formulaireVente.getPharmOrdonnance());
			
			// annuler les obs
			PharmOrdonnance ordonnance=formulaireVente.getPharmOrdonnance();
			PatientIdentifier pi=ordonnance.getPatientIdentifier();
			if(pi!=null){
				System.out.println("===========pi============="+pi.getIdentifier());
				Context.getService(DispensationService.class).deleteObsByAtt(pi.getPatient().getId(), 165010, ordonnance.getOrdDateDispen());
				Context.getService(DispensationService.class).deleteObsByAtt(pi.getPatient().getId(), 165011, ordonnance.getOrdDateDispen());
				Context.getService(DispensationService.class).deleteObsByAtt(pi.getPatient().getId(), 165033, ordonnance.getOrdDateDispen());
				Context.getService(DispensationService.class).deleteObsByAtt(pi.getPatient().getId(), 165040, ordonnance.getOrdDateDispen());
			}
			
		} 
		
		return "/module/pharmagest/listOrdonnance";
		

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
			//System.out.println("-----------------------------------------------------------------");
		}
		 return listMvm;
	}
	
	

	public void initialisation(ModelMap model) {
		FormulaireVente formulaireVente = new FormulaireVente();
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);

		formulaireVente.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		
		List<PharmAssurance> assurances = (List<PharmAssurance>) Context.getService(DispensationService.class).getAllPharmAssurances();

		List<PharmMedecin> medecins = (List<PharmMedecin>) Context.getService(ParametresService.class).getAllMedecins();
		model.addAttribute("programmes", programmes);
		model.addAttribute("formulaireVente", formulaireVente);
		model.addAttribute("produits", produits);
		model.addAttribute("medecins", medecins);
		model.addAttribute("assurances", assurances);
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

		binder.registerCustomEditor(PharmRegime.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmRegime regime = Context.getService(ParametresService.class).getRegimeById(Integer.parseInt(text));
				this.setValue(regime);
			}
		});
		binder.registerCustomEditor(PharmMedecin.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmMedecin medecin = Context.getService(ParametresService.class)
						.getMedecinById(Integer.parseInt(text));
				this.setValue(medecin);
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
		
		binder.registerCustomEditor(PharmAssurance.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmAssurance assurance = Context.getService(DispensationService.class)
						.getPharmAssuranceById(Integer.parseInt(text));
				this.setValue(assurance);
			}
		});

	}

}
