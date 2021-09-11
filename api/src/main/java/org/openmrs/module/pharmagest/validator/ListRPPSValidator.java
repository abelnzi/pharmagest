package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.metier.FormulaireTraitementsPPS;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ListRPPSValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireTraitementsPPS.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireTraitementsPPS formulaire = (FormulaireTraitementsPPS) target;

		PharmSite site = formulaire.getSite();
		PharmProgramme programme = formulaire.getProgramme();
		
		if (site == null)
			errors.rejectValue("site", "formulaireSaisiesPPS.site", "choisir le site");

		if (programme == null)
			errors.rejectValue("programme", "formulaireSaisiesPPS.programme", "choisir le programme ");

	}

}
