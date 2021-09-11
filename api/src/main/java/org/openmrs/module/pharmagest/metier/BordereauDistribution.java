package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;

public class BordereauDistribution {

	PharmLigneOperation ligneOperation;
	PharmLigneCommandeSite ligneCommandeSite;

	public PharmLigneCommandeSite getLigneCommandeSite() {
		return ligneCommandeSite;
	}

	public void setLigneCommandeSite(PharmLigneCommandeSite ligneCommandeSite) {
		this.ligneCommandeSite = ligneCommandeSite;
	}

	public PharmLigneOperation getLigneOperation() {
		return ligneOperation;
	}

	public void setLigneOperation(PharmLigneOperation ligneOperation) {
		this.ligneOperation = ligneOperation;
	}

}
