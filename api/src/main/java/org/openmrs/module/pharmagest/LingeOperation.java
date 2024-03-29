package org.openmrs.module.pharmagest;

// Generated 2 juil. 2015 23:11:24 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * LingeOperation generated by hbm2java
 */
public class LingeOperation implements java.io.Serializable {

	private LingeOperationId id;
	private Produit produit;
	private Operation operation;
	private Integer lgnRecptQte;
	private Integer lgnRecptPrixAchat;
	private Date lgnDatePerem;
	private String lgnRecptLot;

	public LingeOperation() {
	}

	public LingeOperation(LingeOperationId id, Produit produit,
			Operation operation) {
		this.id = id;
		this.produit = produit;
		this.operation = operation;
	}

	public LingeOperation(LingeOperationId id, Produit produit,
			Operation operation, Integer lgnRecptQte,
			Integer lgnRecptPrixAchat, Date lgnDatePerem, String lgnRecptLot) {
		this.id = id;
		this.produit = produit;
		this.operation = operation;
		this.lgnRecptQte = lgnRecptQte;
		this.lgnRecptPrixAchat = lgnRecptPrixAchat;
		this.lgnDatePerem = lgnDatePerem;
		this.lgnRecptLot = lgnRecptLot;
	}

	public LingeOperationId getId() {
		return this.id;
	}

	public void setId(LingeOperationId id) {
		this.id = id;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(Operation operation) {
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
