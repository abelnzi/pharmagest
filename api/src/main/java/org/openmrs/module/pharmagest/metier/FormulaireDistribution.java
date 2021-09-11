package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;

public class FormulaireDistribution {
	private PharmSite site;
	private Date dateDistri;
	
	private PharmProgramme programme;

	public PharmSite getSite() {
		return site;
	}

	public void setSite(PharmSite site) {
		this.site = site;
	}

	public Date getDateDistri() {
		return dateDistri;
	}

	public void setDateDistri(Date dateDistri) {
		this.dateDistri = dateDistri;
	}

	public PharmProgramme getProgramme() {
		return programme;
	}

	public void setProgramme(PharmProgramme programme) {
		this.programme = programme;
	}
}
