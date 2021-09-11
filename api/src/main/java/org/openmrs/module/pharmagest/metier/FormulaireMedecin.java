package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmMedecin;

public class FormulaireMedecin {
	private PharmMedecin pharmMedecin;

	public FormulaireMedecin() {
		this.pharmMedecin = new PharmMedecin();
	}

	public PharmMedecin getPharmMedecin() {
		return pharmMedecin;
	}

	public void setPharmMedecin(PharmMedecin pharmMedecin) {
		this.pharmMedecin = pharmMedecin;
	}

	public Integer getMedId() {
		return pharmMedecin.getMedId();
	}

	public void setMedId(Integer medId) {
		pharmMedecin.setMedId(medId);
	}

	public String getMedCode() {
		return pharmMedecin.getMedCode();
	}

	public void setMedCode(String medCode) {
		pharmMedecin.setMedCode(medCode);
	}

	public String getMedNom() {
		return pharmMedecin.getMedNom();
	}

	public void setMedNom(String medNom) {
		pharmMedecin.setMedNom(medNom);
	}

	public String getMedPrenom() {
		return pharmMedecin.getMedPrenom();
	}

	public void setMedPrenom(String medPrenom) {
		pharmMedecin.setMedPrenom(medPrenom);
	}

	public String getMedAdresse() {
		return pharmMedecin.getMedAdresse();
	}

	public void setMedAdresse(String medAdresse) {
		pharmMedecin.setMedAdresse(medAdresse);
	}

	public String getMedTel() {
		return pharmMedecin.getMedTel();
	}

	public void setMedTel(String medTel) {
		pharmMedecin.setMedTel(medTel);
	}

}
