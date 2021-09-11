package org.openmrs.module.pharmagest.validator;

import java.util.Date;

import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulairePharmOrdonnance;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class DispensationAjoutValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulairePharmOrdonnance.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulairePharmOrdonnance formulaire = (FormulairePharmOrdonnance) target;
		PharmMedecin medecin = formulaire.getPharmOrdonnance().getPharmMedecin();
		PharmRegime pharmRegime = formulaire.getPharmRegime();
		//PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		PharmProduit pharmProduit = formulaire.getPharmProduit();
		String ordBut = formulaire.getOrdBut();
		Integer ordNbreJrsTrai = formulaire.getOrdNbreJrsTrai();
		Date ordDatePrescrip = formulaire.getOrdDatePrescrip();
		Date ordDateDispen = formulaire.getOrdDateDispen();
		Integer ldQteDem = formulaire.getLdQteDem();
		Integer ldQteServi = formulaire.getLdQteServi();

		/*if (medecin == null)
			errors.rejectValue("medecin", "formulaireOrdonnance.medecin", "choisir le prescripteur");*/
		if (pharmRegime == null)
			errors.rejectValue("pharmRegime", "formulaireOrdonnance.pharmRegime", "choisir le régime du patient");
		/*if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireOrdonnance.pharmProgramme", "choisir le programme du patient");*/
		if (pharmProduit == null)
			errors.rejectValue("pharmProduit", "formulaireOrdonnance.pharmProduit", "choisir le produit");
		/*if (ordBut == null)
			errors.rejectValue("ordBut", "formulaireOrdonnance.ordBut", "choisir le but");*/

		if (ordNbreJrsTrai == null)
			errors.rejectValue("ordNbreJrsTrai", "formulaireOrdonnance.ordNbreJrsTrai",
					"indiquer le nombre de jour de traitement");
		/*if (ordDatePrescrip == null)
			errors.rejectValue("ordDatePrescrip", "formulaireOrdonnance.ordDatePrescrip",
					"indiquer la date de prescription");*/
		if (ordDateDispen == null)
			errors.rejectValue("ordDateDispen", "formulaireOrdonnance.ordDateDispen",
					"indiquer la date de dispensation");
		if (ldQteDem == null)
			errors.rejectValue("ldQteDem", "formulaireOrdonnance.ldQteDem",
					"indiquer la quantité de produit demandée");
		if (ldQteServi == null)
			errors.rejectValue("ldQteServi", "formulaireOrdonnance.ldQteServi",
					"indiquer la quantité de produit servie");
	}

}
