package org.openmrs.module.pharmagest.validator;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.metier.FormulaireInventaire;
import org.openmrs.module.pharmagest.metier.FormulairePharmInventaire;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class InventaireAjoutValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulairePharmInventaire.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulairePharmInventaire formulaire = (FormulairePharmInventaire) target;
		
		
		PharmProgramme programme = formulaire.getPharmProgramme();
		PharmProduit produit = formulaire.getProduit();
		
		if (programme == null)
			errors.rejectValue("phamProgramme", "formulaireInventaire.phamProgramme",
					"choisir le programme ");
		if (produit == null)
			errors.rejectValue("produit", "formulaireInventaire.produit",
					"choisir le produit");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "invDeb",
				"formulaireInventaire.invDeb",
				"indiquer la date de début de l'inventaire");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qtePhysique",
				"formulaireInventaire.qtePhysique",
				"indiquer la quantité physique du produit");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePerem",
				"formulaireInventaire.lgnDatePerem",
				"indiquer la date de peremption");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnLot",
				"formulaireInventaire.lgnLot",
				"indiquer le numero de lot");
		
		Date lgnDatePerem=formulaire.getLgnDatePerem();
		
		if(lgnDatePerem.before(new Date())||lgnDatePerem.equals(new Date()))
			errors.rejectValue("lgnDatePerem", "formulaireInventaire.lgnDatePerem",
					"Indiquer une date de peremption valide");

	}

}
