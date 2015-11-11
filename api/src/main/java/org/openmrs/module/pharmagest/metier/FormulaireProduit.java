package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.PharmProduit;

public class FormulaireProduit {
	private PharmProduit pharmProduit;

	public FormulaireProduit(PharmProduit pharmProduit) {

		this.pharmProduit = new PharmProduit();
	}

	public Integer getProdId() {
		return pharmProduit.getProdId();
	}

	public void setProdId(Integer prodId) {
		pharmProduit.setProdId(prodId);
	}

	public PharmClassePharma getPharmClassePharma() {
		return pharmProduit.getPharmClassePharma();
	}

	public void setPharmClassePharma(PharmClassePharma pharmClassePharma) {
		pharmProduit.setPharmClassePharma(pharmClassePharma);
	}

	public String getProdLib() {
		return pharmProduit.getProdLib();
	}

	public void setProdLib(String prodLib) {
		pharmProduit.setProdLib(prodLib);
	}

	public Date getProdDateExp() {
		return pharmProduit.getProdDateExp();
	}

	public void setProdDateExp(Date prodDateExp) {
		pharmProduit.setProdDateExp(prodDateExp);
	}

	public String getFullName() {
		return pharmProduit.getFullName();
	}

	public FormulaireProduit() {
		this.pharmProduit = new PharmProduit();
	}

	public PharmProduit getPharmProduit() {
		return pharmProduit;
	}

	public void setPharmProduit(PharmProduit pharmProduit) {
		this.pharmProduit = pharmProduit;
	}

	public String getProdCode() {
		return pharmProduit.getProdCode();
	}

	public void setProdCode(String prodCode) {
		pharmProduit.setProdCode(prodCode);
	}

}
