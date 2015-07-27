package org.openmrs.module.pharmagest.validator;

import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.TypeOperation;
import org.openmrs.module.pharmagest.metier.FormulaireOrdonnance;
import org.openmrs.module.pharmagest.metier.FormulaireStockFourni;
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
		FormulaireStockFourni formulaire = (FormulaireStockFourni) target;
		
		
		Programme programme = formulaire.getProgramme();
		Produit produit = formulaire.getProduit();
		TypeOperation typeOperation = formulaire.getTypeOperation();
		
		if (programme == null)
			errors.rejectValue("programme", "formulaireStockFourni.programme",
					"choisir le programme ");
		if (produit == null)
			errors.rejectValue("produit", "formulaireStockFourni.produit",
					"choisir le produit");
		if (typeOperation == null)
			errors.rejectValue("typeOperation", "formulaireStockFourni.typeOperation",
					"choisir le type d'opération");

		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recptDateRecept",
				"formulaireStockFourni.recptDateRecept", "indiquer la date de la reception");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePerem",
				"formulaireStockFourni.lgnDatePerem",
				"indiquer la date de péremption");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQte",
				"formulaireStockFourni.lgnRecptQte",
				"indiquer la quantité de produit");
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptPrixAchat",
				"formulaireStockFourni.lgnRecptPrixAchat",
				"indiquer le prix d'achat");*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptLot",
				"formulaireStockFourni.lgnRecptLot",
				"indiquer le numéro de lot");

	}

}
