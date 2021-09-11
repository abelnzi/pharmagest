package org.openmrs.module.pharmagest.validator;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.metier.FormulaireReception;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class ReceptionValidatorAjout implements Validator {

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
		if (produit == null)
			errors.rejectValue("produit", "formulaireReception.produit", "choisir le produit");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePerem", "formulaireReception.lgnDatePerem",
				"indiquer la date de péremption");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQte", "formulaireReception.lgnRecptQte",
				"indiquer la quantité de produit reçu");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQteLivree", "formulaireReception.lgnRecptQteLivree",
				"indiquer la quantité de produit livrée");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptLot", "formulaireReception.lgnRecptLot",
				"indiquer le numéro de lot");
		
		Date lgnDatePerem=formulaire.getLgnDatePerem();
		
		if(lgnDatePerem.before(new Date())||lgnDatePerem.equals(new Date()))
			errors.rejectValue("lgnDatePerem", "formulaireReception.lgnDatePerem",
					"Indiquer une date de peremption valide");

	}

}
