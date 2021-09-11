package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;

public class ConsoByProduit {
	private PharmProgramme programme;
	private PharmProduit produit;
	private int qteStock;

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public int getQteStock() {
		return qteStock;
	}

	public void setQteStock(int qteStock) {
		this.qteStock = qteStock;
	}

	public int getQteConso() {
		return qteConso;
	}

	public void setQteConso(int qteConso) {
		this.qteConso = qteConso;
	}

	public PharmProgramme getProgramme() {
		return programme;
	}

	public void setProgramme(PharmProgramme programme) {
		this.programme = programme;
	}

	private int qteConso;
}
