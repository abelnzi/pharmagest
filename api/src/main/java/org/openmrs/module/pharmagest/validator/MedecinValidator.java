package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.metier.FormulaireMedecin;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class MedecinValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireMedecin.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireMedecin formulaire = (FormulaireMedecin) target;


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medNom","formulaireMedecin.medNom","indiquer le nom du m&eacute;decin");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "medPrenom","formulaireMedecin.medPrenom","indiquer le pr&eacute;nom du medecin");

	}

}
