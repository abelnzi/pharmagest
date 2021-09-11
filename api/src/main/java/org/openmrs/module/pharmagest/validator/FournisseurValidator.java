package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.metier.FormulaireFournisseur;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class FournisseurValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireFournisseur.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireFournisseur formulaire = (FormulaireFournisseur) target;


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fourDesign1","formulaireFournisseur.fourDesign1","indiquer la désignation du fournisseur");	

	}

}
