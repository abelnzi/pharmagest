package org.openmrs.module.pharmagest.validator;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.metier.FormulaireSaisiesPPS;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.FormulaireStockFournisseur;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class SaisiePPS2Validator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireSaisiesPPS.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireSaisiesPPS formulaire = (FormulaireSaisiesPPS) target;

		PharmSite pharmSite = formulaire.getPharmSite();
		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		
		if (pharmSite == null)
			errors.rejectValue("pharmSite", "formulaireSaisiesPPS.pharmSite", "choisir le site");

		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireSaisiesPPS.pharmProgramme", "choisir le programme ");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comStockMax", "formulaireSaisiesPPS.comStockMax",
				"indiquer le stock maximum");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comSitePeriodDate", "formulaireSaisiesPPS.comSitePeriodDate",
				"indiquer la période");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comSiteDateCom", "formulaireSaisiesPPS.comSiteDateCom",
				"indiquer la date de la commande");

	}

}
