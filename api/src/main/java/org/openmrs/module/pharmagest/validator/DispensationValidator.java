package org.openmrs.module.pharmagest.validator;

import java.util.Date;

import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class DispensationValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireOrdonnance.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireOrdonnance formulaire = (FormulaireOrdonnance) target;
		Medecin medecin = formulaire.getOrdonnance().getMedecin();
		Regime regime = formulaire.getRegime();
		Programme programme = formulaire.getProgramme();
		Produit produit = formulaire.getProduit();
		String ordBut = formulaire.getOrdBut();
		Integer ordNbreJrsTrai = formulaire.getOrdNbreJrsTrai();
		Date ordDatePrescrip = formulaire.getOrdDatePrescrip();
		Date ordDateDispen = formulaire.getOrdDateDispen();
		Integer servQteDem = formulaire.getServQteDem();
		Integer servQteServi = formulaire.getServQteServi();

		if (medecin == null)
			errors.rejectValue("medecin", "formulaireOrdonnance.medecin", "choisir le prescripteur");
		if (regime == null)
			errors.rejectValue("regime", "formulaireOrdonnance.regime", "choisir le régime du patient");
		if (programme == null)
			errors.rejectValue("programme", "formulaireOrdonnance.programme", "choisir le programme du patient");
		if (produit == null)
			errors.rejectValue("produit", "formulaireOrdonnance.produit", "choisir le produit");
		if (ordBut == null)
			errors.rejectValue("ordBut", "formulaireOrdonnance.ordBut", "choisir le but");

		if (ordNbreJrsTrai == null)
			errors.rejectValue("ordNbreJrsTrai", "formulaireOrdonnance.ordNbreJrsTrai",
					"indiquer le nombre de jour de traitement");
		if (ordDatePrescrip == null)
			errors.rejectValue("ordDatePrescrip", "formulaireOrdonnance.ordDatePrescrip",
					"indiquer la date de prescription");
		if (ordDateDispen == null)
			errors.rejectValue("ordDateDispen", "formulaireOrdonnance.ordDateDispen",
					"indiquer la date de dispensation");
		if (servQteDem == null)
			errors.rejectValue("servQteDem", "formulaireOrdonnance.servQteDem",
					"indiquer la quantité de produit demandée");
		if (servQteServi == null)
			errors.rejectValue("servQteServi", "formulaireOrdonnance.servQteServi",
					"indiquer la quantité de produit servie");
	}

}
