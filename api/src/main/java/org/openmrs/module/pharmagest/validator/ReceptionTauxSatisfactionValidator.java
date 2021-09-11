package org.openmrs.module.pharmagest.validator;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class ReceptionTauxSatisfactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireReception.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireReception formulaire = (FormulaireReception) target;

		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		
		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireReception.pharmProgramme", "choisir le programme ");


			errors.rejectValue("dateDebut", "formulaireReception.dateDebut",
					"indiquer la période de la commande");
			errors.rejectValue( "recptDateRecept", "formulaireReception.recptDateRecept",
					"indiquer la date de reception");
			errors.rejectValue( "dateFin", "formulaireReception.dateFin",
					"indiquer la date de transmission du rapport");
			errors.rejectValue( "recptNum", "formulaireReception.recptNum",
					"indiquer le(s)  numéro(s) de BL");
		
	}

}
