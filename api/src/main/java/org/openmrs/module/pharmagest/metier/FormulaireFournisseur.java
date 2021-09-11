package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmFournisseur;

public class FormulaireFournisseur {
	private PharmFournisseur pharmFournisseur;

	public FormulaireFournisseur() {
		this.pharmFournisseur = new PharmFournisseur();
	}

	public PharmFournisseur getPharmFournisseur() {
		return pharmFournisseur;
	}

	public void setPharmFournisseur(PharmFournisseur pharmFournisseur) {
		this.pharmFournisseur = pharmFournisseur;
	}

	public Integer getFourId() {
		return pharmFournisseur.getFourId();
	}

	public void setFourId(Integer fourId) {
		pharmFournisseur.setFourId(fourId);
	}

	public String getFourDesign1() {
		return pharmFournisseur.getFourDesign1();
	}

	public void setFourDesign1(String fourDesign1) {
		pharmFournisseur.setFourDesign1(fourDesign1);
	}

	public String getFourDesign2() {
		return pharmFournisseur.getFourDesign2();
	}

	public void setFourDesign2(String fourDesign2) {
		pharmFournisseur.setFourDesign2(fourDesign2);
	}

	public String getFourAdresse() {
		return pharmFournisseur.getFourAdresse();
	}

	public void setFourAdresse(String fourAdresse) {
		pharmFournisseur.setFourAdresse(fourAdresse);
	}

	public String getFourTel1() {
		return pharmFournisseur.getFourTel1();
	}

	public void setFourTel1(String fourTel1) {
		pharmFournisseur.setFourTel1(fourTel1);
	}

	public String getFourTel2() {
		return pharmFournisseur.getFourTel2();
	}

	public void setFourTel2(String fourTel2) {
		pharmFournisseur.setFourTel2(fourTel2);
	}
}
