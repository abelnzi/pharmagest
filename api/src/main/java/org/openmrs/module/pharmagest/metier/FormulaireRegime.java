package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;

public class FormulaireRegime {
	private PharmRegime pharmRegime;

	public FormulaireRegime() {
		this.setPharmRegime(new PharmRegime());
	}

	public PharmRegime getPharmRegime() {
		return pharmRegime;
	}

	public void setPharmRegime(PharmRegime pharmRegime) {
		this.pharmRegime = pharmRegime;
	}

	public Integer getRegimId() {
		return pharmRegime.getRegimId();
	}

	public void setRegimId(Integer regimId) {
		pharmRegime.setRegimId(regimId);
	}

	public String getRegimLib() {
		return pharmRegime.getRegimLib();
	}

	public void setRegimLib(String regimLib) {
		pharmRegime.setRegimLib(regimLib);
	}

	public PharmProgramme getPharmProgramme() {
		return pharmRegime.getPharmProgramme();
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		pharmRegime.setPharmProgramme(pharmProgramme);
	}

	
}
