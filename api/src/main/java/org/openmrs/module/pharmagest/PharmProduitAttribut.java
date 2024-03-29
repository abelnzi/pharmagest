package org.openmrs.module.pharmagest;

// Generated 16 ao�t 2015 03:07:54 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PharmProduitAttribut generated by hbm2java
 */
public class PharmProduitAttribut implements java.io.Serializable {

	private Integer prodAttriId;
	private PharmProduit pharmProduit;
	private Date prodDatePerem;
	private String prodLot;
	private Boolean flagValid;
	private Date prodAttriDate;
	private Set<PharmStocker> pharmStockers = new HashSet<PharmStocker>(0);
	private Set<PharmLigneInventaire> pharmLigneInventaires = new HashSet<PharmLigneInventaire>(
			0);
	private Set<PharmLigneOperation> pharmLigneOperations = new HashSet<PharmLigneOperation>(
			0);

	public PharmProduitAttribut() {
	}

	public PharmProduitAttribut(PharmProduit pharmProduit) {
		this.pharmProduit = pharmProduit;
	}

	public PharmProduitAttribut(PharmProduit pharmProduit, Date prodDatePerem,
			String prodLot, Boolean flagValid, Date prodAttriDate,
			Set<PharmStocker> pharmStockers,
			Set<PharmLigneInventaire> pharmLigneInventaires,
			Set<PharmLigneOperation> pharmLigneOperations) {
		this.pharmProduit = pharmProduit;
		this.prodDatePerem = prodDatePerem;
		this.prodLot = prodLot;
		this.flagValid = flagValid;
		this.prodAttriDate = prodAttriDate;
		this.pharmStockers = pharmStockers;
		this.pharmLigneInventaires = pharmLigneInventaires;
		this.pharmLigneOperations = pharmLigneOperations;
	}

	public Integer getProdAttriId() {
		return this.prodAttriId;
	}

	public void setProdAttriId(Integer prodAttriId) {
		this.prodAttriId = prodAttriId;
	}

	public PharmProduit getPharmProduit() {
		return this.pharmProduit;
	}

	public void setPharmProduit(PharmProduit pharmProduit) {
		this.pharmProduit = pharmProduit;
	}

	public Date getProdDatePerem() {
		return this.prodDatePerem;
	}

	public void setProdDatePerem(Date prodDatePerem) {
		this.prodDatePerem = prodDatePerem;
	}

	public String getProdLot() {
		return this.prodLot;
	}

	public void setProdLot(String prodLot) {
		this.prodLot = prodLot;
	}

	public Boolean getFlagValid() {
		return this.flagValid;
	}

	public void setFlagValid(Boolean flagValid) {
		this.flagValid = flagValid;
	}

	public Date getProdAttriDate() {
		return this.prodAttriDate;
	}

	public void setProdAttriDate(Date prodAttriDate) {
		this.prodAttriDate = prodAttriDate;
	}

	public Set<PharmStocker> getPharmStockers() {
		return this.pharmStockers;
	}

	public void setPharmStockers(Set<PharmStocker> pharmStockers) {
		this.pharmStockers = pharmStockers;
	}

	public Set<PharmLigneInventaire> getPharmLigneInventaires() {
		return this.pharmLigneInventaires;
	}

	public void setPharmLigneInventaires(
			Set<PharmLigneInventaire> pharmLigneInventaires) {
		this.pharmLigneInventaires = pharmLigneInventaires;
	}

	public Set<PharmLigneOperation> getPharmLigneOperations() {
		return this.pharmLigneOperations;
	}

	public void setPharmLigneOperations(
			Set<PharmLigneOperation> pharmLigneOperations) {
		this.pharmLigneOperations = pharmLigneOperations;
	}

}
