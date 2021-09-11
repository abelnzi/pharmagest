package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class TraitementPPSValidatorAjout implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireTraitementsPPS.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireTraitementsPPS formulaire = (FormulaireTraitementsPPS) target;

		
		PharmProduit produit = formulaire.getProduit();
		

		if (produit == null)
			errors.rejectValue("produit", "formulaireStockFourni.produit", "choisir le produit");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnComQteIni", "formulaireTraitementsPPS.lgnComQteIni",
				"indiquer le stock initiale");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnComQteRecu", "formulaireTraitementsPPS.lgnComQteRecu",
				"indiquer la quantité de produit réçue");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnComQteUtil", "formulaireTraitementsPPS.lgnComQteUtil",
				"indiquer la quantité utilisée");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnComPertes", "formulaireTraitementsPPS.lgnComPertes",
				"indiquer la quantité perdue");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnComStockDispo", "formulaireTraitementsPPS.lgnComStockDispo",
				"indiquer le stock disponible");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnComNbreJrsRup", "formulaireTraitementsPPS.lgnComNbreJrsRup",
				"indiquer le nombre de jour de rupture");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnQteDistriM1", "formulaireTraitementsPPS.lgnQteDistriM1",
				"indiquer la quantité distribuée M-1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnQteDistriM2", "formulaireTraitementsPPS.lgnQteDistriM2",
				"indiquer la quantité distribuée M-2");

	}

}
