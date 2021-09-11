package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;

public class ContaintStock {
	private PharmProduit produit;
	private PharmProgramme programme;
	private Integer qteStock;
	private Integer cmm;
	private Double msd;
	private Date datePerem;
	private PharmProduitAttribut pharmProduitAttribut;
	private Integer prixVente ;

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public PharmProgramme getProgramme() {
		return programme;
	}

	public void setProgramme(PharmProgramme programme) {
		this.programme = programme;
	}

	public Integer getQteStock() {
		return qteStock;
	}

	public void setQteStock(Integer qteStock) {
		this.qteStock = qteStock;
	}

	public PharmProduitAttribut getPharmProduitAttribut() {
		return pharmProduitAttribut;
	}

	public void setPharmProduitAttribut(PharmProduitAttribut pharmProduitAttribut) {
		this.pharmProduitAttribut = pharmProduitAttribut;
	}

	public Integer getCmm() {
		return cmm;
	}

	public void setCmm(Integer cmm) {
		this.cmm = cmm;
	}

	public Double getMsd() {
		return msd;
	}

	public void setMsd(Double msd) {
		this.msd = msd;
	}

	public Date getDatePerem() {
		return datePerem;
	}

	public void setDatePerem(Date datePerem) {
		this.datePerem = datePerem;
	}

	public Integer getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(Integer prixVente) {
		this.prixVente = prixVente;
	}

}
