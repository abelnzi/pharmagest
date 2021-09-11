package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.api.InventaireService;

public class GestionContrainte {

	public Boolean autoriserOperation(PharmProgramme programme, Date dateOperation) {
		PharmInventaire inventaire = Context.getService(InventaireService.class)
				.getPharmInventaireForAuthorize(programme);
		if (inventaire == null)
			return true;
		if (inventaire.getInvDeb().after(dateOperation)) {
			return false;
		}
		return true;

	}
}
