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
public class ReceptionValidatorAjoutDetail implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireReception.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireReception formulaire = (FormulaireReception) target;

		PharmFournisseur pharmFournisseur = formulaire.getPharmFournisseur();
		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
		PharmProduit produit = formulaire.getProduitDetail();
		if (pharmFournisseur == null)
			errors.rejectValue("pharmFournisseur", "formulaireReception.pharmFournisseur", "choisir le fournisseur");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recptDateRecept", "formulaireReception.recptDateRecept",
				"indiquer la date de la reception");

		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireReception.pharmProgramme", "choisir le programme ");
		if (produit == null)
			errors.rejectValue("produitDetail", "formulaireReception.produitDetail", "choisir le produit");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnDatePeremDetail", "formulaireReception.lgnDatePeremDetail",
				"indiquer la date de péremption");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQteDetailRecu", "formulaireReception.lgnRecptQteDetailRecu",
				"indiquer la quantité de produit reçu");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptQteDetailLivree", "formulaireReception.lgnRecptQteDetailLivree",
				"indiquer la quantité de produit livrée");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lgnRecptLotDetail", "formulaireReception.lgnRecptLotDetail",
				"indiquer le numéro de lot");
		
		Date lgnDatePeremDetail=formulaire.getLgnDatePeremDetail();
		
		if(lgnDatePeremDetail.before(new Date())||lgnDatePeremDetail.equals(new Date()))
			errors.rejectValue("lgnDatePerem", "formulaireReception.lgnDatePerem",
					"Indiquer une date de peremption valide");

	}

}
