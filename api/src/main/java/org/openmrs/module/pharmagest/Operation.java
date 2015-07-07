package org.openmrs.module.pharmagest;

// Generated 2 juil. 2015 23:11:24 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Operation generated by hbm2java
 */
public class Operation implements java.io.Serializable {

	private int recptId;
	private Programme programme;
	private Fournisseur fournisseur;
	private GestionnairePharma gestionnairePharma;
	private TypeOperation typeOperation;
	private String recptNum;
	private Date recptDateRecept;
	private Date recptDateSaisi;
	private String recptObserv;
	private Set<LingeOperation> lingeOperations = new HashSet<LingeOperation>(0);

	public Operation() {
	}

	public Operation(int recptId, Programme programme,
			GestionnairePharma gestionnairePharma, TypeOperation typeOperation) {
		this.recptId = recptId;
		this.programme = programme;
		this.gestionnairePharma = gestionnairePharma;
		this.typeOperation = typeOperation;
	}

	public Operation(int recptId, Programme programme, Fournisseur fournisseur,
			GestionnairePharma gestionnairePharma, TypeOperation typeOperation,
			String recptNum, Date recptDateRecept, Date recptDateSaisi,
			String recptObserv, Set<LingeOperation> lingeOperations) {
		this.recptId = recptId;
		this.programme = programme;
		this.fournisseur = fournisseur;
		this.gestionnairePharma = gestionnairePharma;
		this.typeOperation = typeOperation;
		this.recptNum = recptNum;
		this.recptDateRecept = recptDateRecept;
		this.recptDateSaisi = recptDateSaisi;
		this.recptObserv = recptObserv;
		this.lingeOperations = lingeOperations;
	}

	public int getRecptId() {
		return this.recptId;
	}

	public void setRecptId(int recptId) {
		this.recptId = recptId;
	}

	public Programme getProgramme() {
		return this.programme;
	}

	public void setProgramme(Programme programme) {
		this.programme = programme;
	}

	public Fournisseur getFournisseur() {
		return this.fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public GestionnairePharma getGestionnairePharma() {
		return this.gestionnairePharma;
	}

	public void setGestionnairePharma(GestionnairePharma gestionnairePharma) {
		this.gestionnairePharma = gestionnairePharma;
	}

	public TypeOperation getTypeOperation() {
		return this.typeOperation;
	}

	public void setTypeOperation(TypeOperation typeOperation) {
		this.typeOperation = typeOperation;
	}

	public String getRecptNum() {
		return this.recptNum;
	}

	public void setRecptNum(String recptNum) {
		this.recptNum = recptNum;
	}

	public Date getRecptDateRecept() {
		return this.recptDateRecept;
	}

	public void setRecptDateRecept(Date recptDateRecept) {
		this.recptDateRecept = recptDateRecept;
	}

	public Date getRecptDateSaisi() {
		return this.recptDateSaisi;
	}

	public void setRecptDateSaisi(Date recptDateSaisi) {
		this.recptDateSaisi = recptDateSaisi;
	}

	public String getRecptObserv() {
		return this.recptObserv;
	}

	public void setRecptObserv(String recptObserv) {
		this.recptObserv = recptObserv;
	}

	public Set<LingeOperation> getLingeOperations() {
		return this.lingeOperations;
	}

	public void setLingeOperations(Set<LingeOperation> lingeOperations) {
		this.lingeOperations = lingeOperations;
	}

}
