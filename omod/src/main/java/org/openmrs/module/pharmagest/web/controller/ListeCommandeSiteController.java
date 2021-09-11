package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
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
@SessionAttributes("formulaireTraitementsPPS")
@RequestMapping(value = "/module/pharmagest/listCommandeSite.form")
public class ListeCommandeSiteController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String initSaisiesPPS(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/listCommandeSite";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_rechercher" })
	public void recherche(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		listRPPSValidator.validate(formulaireTraitementsPPS, result);

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
			

		if (!result.hasErrors()) {
			
			Collection<PharmCommandeSite> commandeSites;
			if (formulaireTraitementsPPS.getDateParam() != null) {
				//System.out.println("---------------------la 1------------------------------");
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteByPeriod(
						formulaireTraitementsPPS.getSite(), formulaireTraitementsPPS.getProgramme(),
						formulaireTraitementsPPS.getDateParam(),"VA");
			} else {
				commandeSites = Context.getService(CommandeSiteService.class).getPharmCommandeSiteBySP(
						formulaireTraitementsPPS.getSite(), formulaireTraitementsPPS.getProgramme(),"VA");
			}
			
			if(!commandeSites.isEmpty()){
				model.addAttribute("var", "1");
				model.addAttribute("commandeSites", commandeSites);
			}else {
				model.addAttribute("var", "2");
			}

		}

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_valder" })
	public void valider(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model, HttpServletRequest request) {

		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {
			String id = request.getParameter("idProd");
			model.addAttribute("code", id);
			int taille = formulaireTraitementsPPS.getCommandeSite().getPharmLigneCommandeSites().size();
			for (int i = 1; i <= taille; i++) {
				String idProd = request.getParameter("idProd" + i);
				String qtePro = request.getParameter("qtePro" + i);
				System.out.println("Voici la ligne " + i);
			}
		}

	}

	public void initialisation(ModelMap model) {
		FormulaireTraitementsPPS formulaireTraitementsPPS = new FormulaireTraitementsPPS();
		model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		// formulaireSaisiesPPS.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("sites", sites);
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
