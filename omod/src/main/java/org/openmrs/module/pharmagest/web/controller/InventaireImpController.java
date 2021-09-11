package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
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
@RequestMapping(value = "/module/pharmagest/inventaireImp.form")
public class InventaireImpController {

	protected final Log log = LogFactory.getLog(getClass());

	public String init(ModelMap model) {

		ArrayList<PharmProduit> list = new ArrayList<PharmProduit>();
		Collection inventaireList = Context.getService(GestionStockService.class).getAllPharmStockers();

		Iterator li = inventaireList.iterator();

		while (li.hasNext()) {

			PharmStocker ligne = (PharmStocker) li.next();
			if (!list.contains(ligne.getPharmProduitAttribut().getPharmProduit())) {
				list.add(ligne.getPharmProduitAttribut().getPharmProduit());
			}

		}

		model.addAttribute("list", list);
		return "/module/pharmagest/inventaireImp";

	}

	@RequestMapping(method = RequestMethod.GET)
	public void initialisation(ModelMap model) {
		FormulaireTraitementsPPS formulaireTraitementsPPS = new FormulaireTraitementsPPS();
		model.addAttribute("formulaireTraitementsPPS", formulaireTraitementsPPS);

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);
		model.addAttribute("var", "0");

	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_editer" })
	public void recherche(@ModelAttribute("formulaireTraitementsPPS") FormulaireTraitementsPPS formulaireTraitementsPPS,
			BindingResult result, HttpSession session, ModelMap model) {

		ArrayList<PharmProduit> list = new ArrayList<PharmProduit>();
		Collection inventaireList = Context.getService(GestionStockService.class).getPharmStockersByProgram(formulaireTraitementsPPS.getProgramme());

		Iterator li = inventaireList.iterator();

		while (li.hasNext()) {

			PharmStocker ligne = (PharmStocker) li.next();
			if (!list.contains(ligne.getPharmProduitAttribut().getPharmProduit())) {
				list.add(ligne.getPharmProduitAttribut().getPharmProduit());
			}

		}

		model.addAttribute("list", list);
		model.addAttribute("var", "1");
		model.addAttribute("programme", formulaireTraitementsPPS.getProgramme().getProgramLib());

	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
				
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
