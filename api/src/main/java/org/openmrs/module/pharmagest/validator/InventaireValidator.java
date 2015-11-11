package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.metier.FormulaireInventaire;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class InventaireValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireInventaire.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireInventaire formulaire = (FormulaireInventaire) target;
		
		
		Programme programme = formulaire.getProgramme();
		Produit produit = formulaire.getProduit();
		
		if (programme == null)
			errors.rejectValue("programme", "formulaireInventaire.programme",
					"choisir le programme ");
		if (produit == null)
			errors.rejectValue("produit", "formulaireInventaire.produit",
					"choisir le produit");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "invDeb",
				"formulaireInventaire.lgnDatePerem",
				"indiquer la date de début de l'inventaire");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qtePhysique",
				"formulaireInventaire.qtePhysique",
				"indiquer la quantité physique du produit");
		

	}

}
