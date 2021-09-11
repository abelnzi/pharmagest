package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.RapportCommandeService;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.openmrs.module.pharmagest.metier.LigneReceptionMvt;
import org.openmrs.module.pharmagest.validator.ReceptionTauxSatisfactionValidator;
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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("formulaireReception")

public class RapportReceptionController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ReceptionTauxSatisfactionValidator receptionTauxValidator;
	
	@RequestMapping(value = "/module/pharmagest/satisfactionESPC.form", method = RequestMethod.GET)
	public String satisfactionParam(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/satisfactionESPC";
	}
	
	@RequestMapping(value = "/module/pharmagest/satisfactionESPC.form", method = RequestMethod.POST, params = {
		"btn_enregistrer" })
	public void satisfactionPost(@ModelAttribute("formulaireReception") FormulaireReception formulaireReception,
		BindingResult result, HttpSession session, ModelMap model) {
		
		//receptionTauxValidator.validate(formulaireReception, result);
		
		if (!result.hasErrors()) {
			
			List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
					.getAllProgrammes();
			model.addAttribute("programmes", programmes);
			
			int delai=(int) this.getDateDiff(formulaireReception.getDateFin(), formulaireReception.getRecptDateRecept(), TimeUnit.DAYS);
		
			Map<String, LigneReceptionMvt> mapData = new HashMap<String, LigneReceptionMvt>();
			
			//ArrayList<LigneReceptionMvt> listData=new ArrayList<LigneReceptionMvt>();
			Collection commandes =Context.getService(RapportCommandeService.class).getPharmRapportCommandeByPeriod(formulaireReception.getPharmProgramme(),
					formulaireReception.getDateDebut());
			
			int compteurCommande=0;
			int compteurReception=0;
			
			if(commandes.size()!=0) {
			PharmRapportCommande commande=(PharmRapportCommande) commandes.toArray()[0];
			
			String[] listBL=(formulaireReception.getRecptNum()!=null)?formulaireReception.getRecptNum().split(";"):null;
			
			//Les compteurs
			 compteurCommande=this.getNombreLigneC(commande.getPharmLigneRcs());
			 compteurReception=this.getNombreLigneR(listBL, formulaireReception.getPharmProgramme());
			
			for(int i=0; i < listBL.length; i++) {
				
			Collection recepts = Context.getService(GestionReceptionService.class).getPharmReceptionByBL(formulaireReception.getPharmProgramme(),
					listBL[i]);					
			if(recepts.size()!=0 ) {				
				
				PharmReception reception=(PharmReception) recepts.toArray()[0];
								
				Collection<PharmProduit> lisProduit = formulaireReception.getPharmProgramme().getPharmProduits();
				
				for (PharmProduit produit : lisProduit) {
					LigneReceptionMvt ligneData=new LigneReceptionMvt();
					boolean bRecept=false;
					boolean bComm=false;
					ligneData.setProduit(produit);
										
					int qteR=0;
					
					for (PharmLigneReception ligneR : reception.getPharmLigneReceptions()) {
						
						
						if(ligneR.getPharmProduitAttribut().getPharmProduit()==produit) {
							if(ligneR.getLgnRecptQteDetailRecu()==null) ligneR.setLgnRecptQteDetailRecu(0);
														
							qteR=qteR+ligneR.getLgnRecptQteDetailRecu();
							ligneData.setLgnRecptQteLivree(qteR);
							bRecept=true;
							
						}
						
					}
					
									
					
					for (PharmLigneRc ligneRc : commande.getPharmLigneRcs()) {
						if(ligneRc.getPharmProduit()==produit) {
							int qte=(ligneData.getLgnRecptQte()!=null && ligneRc.getLgnRcQteCom()!=null)?
									(ligneData.getLgnRecptQte()+ligneRc.getLgnRcQteCom()):ligneRc.getLgnRcQteCom();
							ligneData.setLgnRecptQte(ligneRc.getLgnRcQteCom());
							bComm=true;
						}
					}
					
					
					if(bRecept || bComm) {
						if(mapData.containsKey(produit.getProdCode())) {
						 LigneReceptionMvt varRecept=mapData.get(produit.getProdCode());
						 if(ligneData.getLgnRecptQteLivree()==null)ligneData.setLgnRecptQteLivree(0);
						 if(varRecept.getLgnRecptQteLivree()==null)varRecept.setLgnRecptQteLivree(0);
						 
						 Integer qte= ligneData.getLgnRecptQteLivree()+varRecept.getLgnRecptQteLivree();

												 						 
						 ligneData.setLgnRecptQteLivree(qte);							
						 mapData.remove(produit.getProdCode());
						 mapData.put(produit.getProdCode(),ligneData);
						} else {
							//System.out.println("=========map qte=============="+ligneData.getLgnRecptQteLivree());
							mapData.put(produit.getProdCode(),ligneData);
						}
						
					}
					
				}
			}
			}
		}
			
			
			int tauxSL=(compteurCommande!=0)?((int)( Math.round( (double)(compteurReception*100) / (double) compteurCommande ))):0;
			
			model.addAttribute("tauxSL", tauxSL);		
			model.addAttribute("list", mapData.values());
			model.addAttribute("var", 1);
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
			String periode= " du "+sf.format(formulaireReception.getDateDebut());
			model.addAttribute("periode", periode);
			model.addAttribute("delai", delai);		
		} else {
			model.addAttribute("formulaireReception", formulaireReception);	
		}
		
		
	}
	
	public int getNombreLigneR(String[] listBL,PharmProgramme programme) {
		
		int compteur=0;
		Map<PharmProduit, PharmLigneReception> mapData = new HashMap<PharmProduit, PharmLigneReception>();
		
		for(int i=0; i < listBL.length; i++) {
			//System.out.println("=========BL=============="+listBL[i]);
			Collection recepts = Context.getService(GestionReceptionService.class).getPharmReceptionByBL(programme,
					listBL[i]);	
			if(recepts.size()!=0) {				
				
				PharmReception reception=(PharmReception) recepts.toArray()[0];
				
				for (PharmLigneReception ligne : reception.getPharmLigneReceptions()) {
					if(!mapData.containsKey(ligne.getPharmProduitAttribut().getPharmProduit())) {
						//System.out.println("=========code=============="+ligne.getPharmProduitAttribut().getPharmProduit().getFullName());
						compteur=compteur+1;
						mapData.put(ligne.getPharmProduitAttribut().getPharmProduit(), ligne);
					}
				}
			}
					
		}
		
		//System.out.println("=========compteurReception=============="+compteur);
		return compteur;		
	}
	
	public int getNombreLigneC(Set<PharmLigneRc> list) {
		Map<PharmProduit, PharmLigneRc> mapData = new HashMap<PharmProduit, PharmLigneRc>();
		int compteur=0;
		for (PharmLigneRc ligne : list) {
			ligne.setLgnRcQteCom((ligne.getLgnRcQteCom()!=null)?ligne.getLgnRcQteCom():0);
			if(!mapData.containsKey(ligne.getPharmProduit()) && ligne.getLgnRcQteCom()!=0) {
				compteur=compteur+1;
			}
		}		
		//System.out.println("=========compteurCommande=============="+compteur);

		return compteur;		
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
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
