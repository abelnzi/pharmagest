package org.openmrs.module.pharmagest.metier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.GestionnairePharma;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LigneDispensationId;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.LingeOperationId;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.TypeOperation;

public class FormulaireStockFourni {

	private Operation operation;
	private LingeOperation ligneOperation;
	private LingeOperationId ligneOperationId;
	private TabOperation tabOperation;

	public FormulaireStockFourni() {
		this.operation = new Operation();
		this.ligneOperation = new LingeOperation();
		this.ligneOperationId = new LingeOperationId();
		this.setTabOperation(new TabOperation());
	}

	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}

	public int getRecptId() {
		return operation.getRecptId();
	}

	public void setRecptId(int recptId) {
		operation.setRecptId(recptId);
	}

	public Programme getProgramme() {
		return operation.getProgramme();
	}

	public void setProgramme(Programme programme) {
		operation.setProgramme(programme);
	}

	public Fournisseur getFournisseur() {
		return operation.getFournisseur();
	}

	public void setFournisseur(Fournisseur fournisseur) {
		operation.setFournisseur(fournisseur);
	}

	public GestionnairePharma getGestionnairePharma() {
		return operation.getGestionnairePharma();
	}

	public void setGestionnairePharma(GestionnairePharma gestionnairePharma) {
		operation.setGestionnairePharma(gestionnairePharma);
	}

	public TypeOperation getTypeOperation() {
		return operation.getTypeOperation();
	}

	public void setTypeOperation(TypeOperation typeOperation) {
		operation.setTypeOperation(typeOperation);
	}

	public String getRecptNum() {
		return operation.getRecptNum();
	}

	public void setRecptNum(String recptNum) {
		operation.setRecptNum(recptNum);
	}

	public Date getRecptDateRecept() {
		return operation.getRecptDateRecept();
	}

	public void setRecptDateRecept(Date recptDateRecept) {
		operation.setRecptDateRecept(recptDateRecept);
	}

	public Date getRecptDateSaisi() {
		return operation.getRecptDateSaisi();
	}

	public void setRecptDateSaisi(Date recptDateSaisi) {
		operation.setRecptDateSaisi(recptDateSaisi);
	}

	public String getRecptObserv() {
		return operation.getRecptObserv();
	}

	public void setRecptObserv(String recptObserv) {
		operation.setRecptObserv(recptObserv);
	}

	public void setLingeOperations(Set<LingeOperation> lingeOperations) {
		operation.setLingeOperations(lingeOperations);
	}

	public LingeOperationId getId() {
		return ligneOperation.getId();
	}

	public void setId(LingeOperationId id) {
		ligneOperation.setId(id);
	}

	public Produit getProduit() {
		return ligneOperation.getProduit();
	}

	public void setProduit(Produit produit) {
		ligneOperation.setProduit(produit);
	}

	public Integer getLgnRecptQte() {
		return ligneOperation.getLgnRecptQte();
	}

	public void setLgnRecptQte(Integer lgnRecptQte) {
		ligneOperation.setLgnRecptQte(lgnRecptQte);
	}

	public Integer getLgnRecptPrixAchat() {
		return ligneOperation.getLgnRecptPrixAchat();
	}

	public void setLgnRecptPrixAchat(Integer lgnRecptPrixAchat) {
		ligneOperation.setLgnRecptPrixAchat(lgnRecptPrixAchat);
	}

	public Date getLgnDatePerem() {
		return ligneOperation.getLgnDatePerem();
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		ligneOperation.setLgnDatePerem(lgnDatePerem);
	}

	public String getLgnRecptLot() {
		return ligneOperation.getLgnRecptLot();
	}

	public void setLgnRecptLot(String lgnRecptLot) {
		ligneOperation.setLgnRecptLot(lgnRecptLot);
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * @return the ligneOperation
	 */
	public LingeOperation getLigneOperation() {
		return ligneOperation;
	}

	/**
	 * @param ligneOperation
	 *            the ligneOperation to set
	 */
	public void setLigneOperation(LingeOperation ligneOperation) {
		this.ligneOperation = ligneOperation;
	}

	public LingeOperationId getLigneOperationId() {
		return ligneOperationId;
	}

	public void setLigneOperationId(LingeOperationId ligneOperationId) {
		this.ligneOperationId = ligneOperationId;
	}
	
	public LingeOperationId addLigneOperationId (){
		this.ligneOperationId.setProdId(this.getProduit().getProdId());
		this.ligneOperationId.setRecptId(this.getOperation().getRecptId());
		this.getLigneOperation().setId(this.ligneOperationId);
		return this.ligneOperationId;
	}

	/**
	 * @return the tabOperation
	 */
	public TabOperation getTabOperation() {
		return tabOperation;
	}

	/**
	 * @param tabOperation the tabOperation to set
	 */
	public void setTabOperation(TabOperation tabOperation) {
		this.tabOperation = tabOperation;
	}

	public String getRecptRef() {
		return operation.getRecptRef();
	}

	public void setRecptRef(String recptRef) {
		operation.setRecptRef(recptRef);
	}
}
