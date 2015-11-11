package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.metier.FormulaireRegime;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class RegimeValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireRegime.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireRegime formulaire = (FormulaireRegime) target;


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "regimLib",
				"formulaireRegime.regimLib",
				"indiquer la désignation du régime");	

	}

}
