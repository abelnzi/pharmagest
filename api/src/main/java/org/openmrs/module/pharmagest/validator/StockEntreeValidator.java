package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.TypeOperation;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
import org.openmrs.module.pharmagest.metier.FormulaireStockFournisseur;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class StockEntreeValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireStockFourni.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireStockFournisseur formulaire = (FormulaireStockFournisseur) target;

		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		PharmProduit produit = formulaire.getProduit();

		PharmTypeOperation pharmTypeOperation = formulaire.getPharmTypeOperation();

		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireStockFourni.pharmProgramme", "choisir le programme ");

		if (pharmTypeOperation == null)
			errors.rejectValue("pharmTypeOperation", "formulaireStockFourni.pharmTypeOperation",
					"choisir le type d'opération");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "operDateRecept", "formulaireStockFourni.operDateRecept",
				"indiquer la date de la reception");

		if (formulaire.getTabOperationMvt().getLigneOperationsCollection() == null) {
			if (produit == null)
				errors.rejectValue("produit", "formulaireStockFourni.produit", "choisir le produit");

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePerem", "formulaireStockFourni.lgnDatePerem",
					"indiquer la date de péremption");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQte", "formulaireStockFourni.lgnRecptQte",
					"indiquer la quantité de produit");

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptLot", "formulaireStockFourni.lgnRecptLot",
					"indiquer le numéro de lot");
		} else if (formulaire.getTabOperationMvt().getLigneOperationsCollection() != null) {
			if (formulaire.getTabOperationMvt().getLigneOperationsCollection().isEmpty()) {
				if (produit == null)
					errors.rejectValue("produit", "formulaireStockFourni.produit", "choisir le produit");

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePerem", "formulaireStockFourni.lgnDatePerem",
						"indiquer la date de péremption");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQte", "formulaireStockFourni.lgnRecptQte",
						"indiquer la quantité de produit");

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptLot", "formulaireStockFourni.lgnRecptLot",
						"indiquer le numéro de lot");
			}
		}

	}

}
