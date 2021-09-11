package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireProduit;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class RapportMvmValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireProduit.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireProduit formulaire = (FormulaireProduit) target;

		PharmProgramme pharmProgramme = formulaire.getProgramme();
		String type=formulaire.getTypeRapport();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateDebut", "formulaireProduit.dateDebut",
				"indiquer la date de début");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateFin", "formulaireProduit.dateFin",
				"indiquer la date de fin");

		if (pharmProgramme == null)
			errors.rejectValue("programme", "formulaireProduit.programme", "choisir le programme ");
		if (type.equals("0") )
			errors.rejectValue("typeRapport", "formulaireProduit.typeRapport", "choisir le type de rapport ");

	}

}
