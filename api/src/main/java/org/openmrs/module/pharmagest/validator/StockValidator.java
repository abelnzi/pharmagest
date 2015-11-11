package org.openmrs.module.pharmagest.validator;

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
public class StockValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireStockFourni.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireStockFournisseur formulaire = (FormulaireStockFournisseur) target;

		PharmFournisseur pharmFournisseur = formulaire.getPharmFournisseur();
		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		PharmProduit produit = formulaire.getProduit();
		if (pharmFournisseur == null)
			errors.rejectValue("pharmFournisseur", "formulaireStockFourni.pharmFournisseur", "choisir le fournisseur");
		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireStockFourni.pharmProgramme", "choisir le programme ");
		if (produit == null)
			errors.rejectValue("produit", "formulaireStockFourni.produit", "choisir le produit");

		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "operNum", "formulaireStockFourni.operNum",
				"indiquer le numéro de la livraison");*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "operDateRecept", "formulaireStockFourni.operDateRecept",
				"indiquer la date de la reception");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePerem", "formulaireStockFourni.lgnDatePerem",
				"indiquer la date de péremption");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQte", "formulaireStockFourni.lgnRecptQte",
				"indiquer la quantité de produit");
		/*
		 * ValidationUtils.rejectIfEmptyOrWhitespace(errors,
		 * "lgnRecptPrixAchat", "formulaireStockFourni.lgnRecptPrixAchat",
		 * "indiquer le prix d'achat");
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptLot", "formulaireStockFourni.lgnRecptLot",
				"indiquer le numéro de lot");

	}

}
