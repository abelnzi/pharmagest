package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OrdonnanceValidator implements Validator {

	@Override
	public boolean supports(Class c) {
		return Ordonnance.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ordonnance ordonnance = (Ordonnance) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ordNbreJrsTrai", "");

		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "ordDatePrescrip", "");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ordDateDispen", "");

	}

}
