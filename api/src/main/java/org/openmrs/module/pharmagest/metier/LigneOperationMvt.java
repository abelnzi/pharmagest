package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;

public class LigneOperationMvt {

	private PharmProduit produit;
	private PharmOperation operation;
	private Integer lgnRecptQte;
	private Integer lgnRecptPrixAchat;
	private Date lgnDatePerem;
	private String lgnRecptLot;

	public LigneOperationMvt() {
	}

	public LigneOperationMvt(PharmProduit produit, PharmOperation operation) {
		this.produit = produit;
		this.operation = operation;
	}

	public LigneOperationMvt(PharmProduit produit, PharmOperation operation, Integer lgnRecptQte,
			Integer lgnRecptPrixAchat, Date lgnDatePerem, String lgnRecptLot) {

		this.produit = produit;
		this.operation = operation;
		this.lgnRecptQte = lgnRecptQte;
		this.lgnRecptPrixAchat = lgnRecptPrixAchat;
		this.lgnDatePerem = lgnDatePerem;
		this.lgnRecptLot = lgnRecptLot;
	}

	public PharmProduit getProduit() {
		return this.produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public PharmOperation getOperation() {
		return this.operation;
	}

	public void setOperation(PharmOperation operation) {
		this.operation = operation;
	}

	public Integer getLgnRecptQte() {
		return this.lgnRecptQte;
	}

	public void setLgnRecptQte(Integer lgnRecptQte) {
		this.lgnRecptQte = lgnRecptQte;
	}

	public Integer getLgnRecptPrixAchat() {
		return this.lgnRecptPrixAchat;
	}

	public void setLgnRecptPrixAchat(Integer lgnRecptPrixAchat) {
		this.lgnRecptPrixAchat = lgnRecptPrixAchat;
	}

	public Date getLgnDatePerem() {
		return this.lgnDatePerem;
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		this.lgnDatePerem = lgnDatePerem;
	}

	public String getLgnRecptLot() {
		return this.lgnRecptLot;
	}

	public void setLgnRecptLot(String lgnRecptLot) {
		this.lgnRecptLot = lgnRecptLot;
	}

}
