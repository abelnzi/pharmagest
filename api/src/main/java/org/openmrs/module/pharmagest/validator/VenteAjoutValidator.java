package org.openmrs.module.pharmagest.validator;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmAssurance;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.metier.FormulaireVente;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class VenteAjoutValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireVente.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireVente formulaire = (FormulaireVente) target;
		
		
		PharmProduit pharmProduit = formulaire.getPharmProduit();
		Integer ldQteDem = formulaire.getLdQteDem();
		Integer ldQteServi = formulaire.getLdQteServi();		
		
		if (pharmProduit == null)
			errors.rejectValue("pharmProduit", "formulaireOrdonnance.pharmProduit", "choisir le produit");

		if (ldQteDem == null)
			errors.rejectValue("ldQteDem", "formulaireOrdonnance.ldQteDem",
					"indiquer la quantité de produit demandée");
		if (ldQteServi == null)
			errors.rejectValue("ldQteServi", "formulaireOrdonnance.ldQteServi",
					"indiquer la quantité de produit servie");		

	}

}
