package org.openmrs.module.pharmagest.validator;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.FormulaireStockFournisseur;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class Stock2Validator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireStockFourni.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireStockFournisseur formulaire = (FormulaireStockFournisseur) target;

		PharmFournisseur pharmFournisseur = formulaire.getPharmFournisseur();
		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		
		if (pharmFournisseur == null)
			errors.rejectValue("pharmFournisseur", "formulaireStockFourni.pharmFournisseur", "choisir le fournisseur");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "operDateRecept", "formulaireStockFourni.operDateRecept",
				"indiquer la date de la reception");

		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireStockFourni.pharmProgramme", "choisir le programme ");



	}

}
