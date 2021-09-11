package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;

public class LigneInventaireMvt {

	private PharmProduit produit;
	private Integer qtePhysique;
	private Integer qteLogique;
	private String observation;
	private Integer ecart;
	private Date lgnDatePerem;
	private String lgnLot;
	private Integer prixInv;

	public LigneInventaireMvt() {
	}

	public LigneInventaireMvt(PharmProgramme programme, PharmInventaire inventaire, PharmProduit produit) {

		this.produit = produit;
	}

	public LigneInventaireMvt(PharmProgramme programme, PharmInventaire inventaire, PharmProduit produit,
			Integer qtePhysique, Integer qteLogique, String observation, Integer ecart) {

		this.produit = produit;
		this.qtePhysique = qtePhysique;
		this.qteLogique = qteLogique;
		this.observation = observation;
		this.ecart = ecart;
	}

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public Integer getQtePhysique() {
		return qtePhysique;
	}

	public void setQtePhysique(Integer qtePhysique) {
		this.qtePhysique = qtePhysique;
	}

	public Integer getQteLogique() {
		return qteLogique;
	}

	public void setQteLogique(Integer qteLogique) {
		this.qteLogique = qteLogique;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getEcart() {
		return ecart;
	}

	public void setEcart(Integer ecart) {
		this.ecart = ecart;
	}

	public Date getLgnDatePerem() {
		return lgnDatePerem;
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		this.lgnDatePerem = lgnDatePerem;
	}

	public String getLgnLot() {
		return lgnLot;
	}

	public void setLgnLot(String lgnLot) {
		this.lgnLot = lgnLot;
	}

	public Integer getPrixInv() {
		return prixInv;
	}

	public void setPrixInv(Integer prixInv) {
		this.prixInv = prixInv;
	}

}
