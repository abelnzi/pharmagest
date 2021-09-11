package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmClassePharma;

public class FormulaireClassePharma {
	private PharmClassePharma pharmClassePharma;

	public FormulaireClassePharma() {
	
		this.pharmClassePharma = new PharmClassePharma();
	}

	public Integer getClassPharmId() {
		return pharmClassePharma.getClassPharmId();
	}

	public void setClassPharmId(Integer classPharmId) {
		pharmClassePharma.setClassPharmId(classPharmId);
	}

	public String getClassPharmNom() {
		return pharmClassePharma.getClassPharmNom();
	}

	public void setClassPharmNom(String classPharmNom) {
		pharmClassePharma.setClassPharmNom(classPharmNom);
	}

	public PharmClassePharma getPharmClassePharma() {
		return pharmClassePharma;
	}

	public void setPharmClassePharma(PharmClassePharma pharmClassePharma) {
		this.pharmClassePharma = pharmClassePharma;
	}

}
