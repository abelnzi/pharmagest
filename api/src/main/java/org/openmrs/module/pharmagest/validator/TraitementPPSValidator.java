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
public class TraitementPPSValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireTraitementsPPS.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireTraitementsPPS formulaire = (FormulaireTraitementsPPS) target;

		
		ValidationUtils
		.rejectIfEmptyOrWhitespace(errors, "dateParam", "formulaireTraitementsPPS.dateParam","Indiquer la date de traitement");
	}

}
