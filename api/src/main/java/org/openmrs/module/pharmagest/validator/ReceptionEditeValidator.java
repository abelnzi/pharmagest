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
public class ReceptionEditeValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireReception.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireReception formulaire = (FormulaireReception) target;

		PharmFournisseur pharmFournisseur = formulaire.getPharmFournisseur();
		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		PharmProduit produit = formulaire.getProduit();
		if (pharmFournisseur == null)
			errors.rejectValue("pharmFournisseur", "formulaireReception.pharmFournisseur", "choisir le fournisseur");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recptDateRecept", "formulaireReception.recptDateRecept",
				"indiquer la date de la reception");

		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireReception.pharmProgramme", "choisir le programme ");
		if (formulaire.getTypeReception() == null)
			errors.rejectValue("typeReception", "formulaireReception.typeReception", "choisir le type de reception ");

	}

}
