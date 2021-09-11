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
public class VenteEditValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return FormulaireVente.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormulaireVente formulaire = (FormulaireVente) target;
		
		
		PharmProgramme pharmProgramme = formulaire.getPharmProgramme();
			
		Date ordDateDispen = formulaire.getOrdDateDispen();
		
		
		
		
		
		if(formulaire.getTypeVente().equals("I")){
			PharmAssurance assurance=formulaire.getAssurance();
			String numPatient=formulaire.getNumPatient();
			Date cliDateNaiss=formulaire.getCliDateNaiss();
			String cliNom=formulaire.getCliNom();
			String cliPrenom=formulaire.getCliPrenom();
			Integer taux=formulaire.getTaux();
			//System.out.println("--------------formulaire.getTaux(--------------"+formulaire.getTaux());
			String cliGenre=formulaire.getCliGenre();
			
			//System.out.println("--------------formulaire.getCliNom()--------------"+formulaire.getCliNom());
			//System.out.println("--------------formulaire.getCliPrenom()--------------"+formulaire.getCliPrenom());
			//System.out.println("--------------formulaire.getNumPatient()--------------"+formulaire.getNumPatient());
			
			if(formulaire.getNewAssur().equals("O")){
				if (assurance == null)
					errors.rejectValue("assurance", "formulaireOrdonnance.assurance", "choisir l\'assurance du patient");
				if (numPatient == null || numPatient=="")
					errors.rejectValue("numPatient", "formulaireOrdonnance.numPatient",	"indiquer le numero du patient");
				if (cliDateNaiss == null)
					errors.rejectValue("cliDateNaiss", "formulaireOrdonnance.cliDateNaiss", "indiquer la date de naissance du patient");
				if (cliNom == null || cliNom=="")
					errors.rejectValue("cliNom", "formulaireOrdonnance.cliNom",	"indiquer le nom du patient");
				if (cliPrenom == null || cliPrenom=="")
					errors.rejectValue("cliPrenom", "formulaireOrdonnance.cliPrenom", "indiquer le prénom du patient");
				if (taux ==null)
					errors.rejectValue("taux", "formulaireOrdonnance.taux",	"indiquer le taux de couverture");			
				if (cliGenre == null)
					errors.rejectValue("cliGenre", "formulaireOrdonnance.cliGenre",	"indiquer le genre du patient");
			}else{
				if (assurance == null)
					errors.rejectValue("assurance", "formulaireOrdonnance.assurance", "choisir l\'assurance du patient");
				if (numPatient == null || numPatient=="")
					errors.rejectValue("numPatient", "formulaireOrdonnance.numPatient",	"indiquer le numero du patient");
			}
		}
		
		
		if (ordDateDispen == null)
			errors.rejectValue("ordDateDispen", "formulaireOrdonnance.ordDateDispen",
					"indiquer la date de dispensation");		
		if (pharmProgramme == null)
			errors.rejectValue("pharmProgramme", "formulaireOrdonnance.pharmProgramme", "choisir le programme du patient");
				
		

	}

}
