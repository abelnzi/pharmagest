package org.openmrs.module.pharmagest.metier;

import java.util.Date;
import java.util.Set;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmTypeOperation;

public class FormulaireStockFournisseur {

	private PharmOperation pharmOperation;
	private LigneOperationMvt ligneOperation;
	private TabOperationMvt tabOperationMvt;

	public FormulaireStockFournisseur() {
		this.pharmOperation = new PharmOperation();
		this.ligneOperation = new LigneOperationMvt();
		this.tabOperationMvt = new TabOperationMvt();
	}

	public Integer getOperId() {
		return pharmOperation.getOperId();
	}

	public void setOperId(Integer operId) {
		pharmOperation.setOperId(operId);
	}

	public PharmGestionnairePharma getPharmGestionnairePharma() {
		return pharmOperation.getPharmGestionnairePharma();
	}

	public void setPharmGestionnairePharma(PharmGestionnairePharma pharmGestionnairePharma) {
		pharmOperation.setPharmGestionnairePharma(pharmGestionnairePharma);
	}

	public PharmTypeOperation getPharmTypeOperation() {
		return pharmOperation.getPharmTypeOperation();
	}

	public void setPharmTypeOperation(PharmTypeOperation pharmTypeOperation) {
		pharmOperation.setPharmTypeOperation(pharmTypeOperation);
	}

	public PharmProgramme getPharmProgramme() {
		return pharmOperation.getPharmProgramme();
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		pharmOperation.setPharmProgramme(pharmProgramme);
	}

	public PharmFournisseur getPharmFournisseur() {
		return pharmOperation.getPharmFournisseur();
	}

	public void setPharmFournisseur(PharmFournisseur pharmFournisseur) {
		pharmOperation.setPharmFournisseur(pharmFournisseur);
	}

	public String getOperNum() {
		return pharmOperation.getOperNum();
	}

	public void setOperNum(String operNum) {
		pharmOperation.setOperNum(operNum);
	}

	public Date getOperDateRecept() {
		return pharmOperation.getOperDateRecept();
	}

	public void setOperDateRecept(Date operDateRecept) {
		pharmOperation.setOperDateRecept(operDateRecept);
	}

	public Date getOperDateSaisi() {
		return pharmOperation.getOperDateSaisi();
	}

	public void setOperDateSaisi(Date operDateSaisi) {
		pharmOperation.setOperDateSaisi(operDateSaisi);
	}

	public String getOperObserv() {
		return pharmOperation.getOperObserv();
	}

	public void setOperObserv(String operObserv) {
		pharmOperation.setOperObserv(operObserv);
	}

	public String getOperRef() {
		return pharmOperation.getOperRef();
	}

	public void setOperRef(String operRef) {
		pharmOperation.setOperRef(operRef);
	}

	public Set<PharmLigneOperation> getPharmLigneOperations() {
		return pharmOperation.getPharmLigneOperations();
	}

	public void setPharmLigneOperations(Set<PharmLigneOperation> pharmLigneOperations) {
		pharmOperation.setPharmLigneOperations(pharmLigneOperations);
	}

	public PharmProduit getProduit() {
		return ligneOperation.getProduit();
	}

	public void setProduit(PharmProduit produit) {
		ligneOperation.setProduit(produit);
	}

	public PharmOperation getOperation() {
		return ligneOperation.getOperation();
	}

	public void setOperation(PharmOperation operation) {
		ligneOperation.setOperation(operation);
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

	public PharmOperation getPharmOperation() {
		return pharmOperation;
	}

	public void setPharmOperation(PharmOperation pharmOperation) {
		this.pharmOperation = pharmOperation;
	}

	public LigneOperationMvt getLigneOperation() {
		return ligneOperation;
	}

	public void setLigneOperation(LigneOperationMvt ligneOperation) {
		this.ligneOperation = ligneOperation;
	}

	public TabOperationMvt getTabOperationMvt() {
		return tabOperationMvt;
	}

	public void setTabOperationMvt(TabOperationMvt tabOperationMvt) {
		this.tabOperationMvt = tabOperationMvt;
	}

	/*
	 * public PharmLigneOperationId addLigneOperationId (){
	 * this.pharmLigneOperationId.setProdAttriId(this.getProdAttriId());
	 * this.pharmLigneOperationId.setOperId(this.getPharmOperation().getOperId()
	 * ); this.getPharmLigneOperation().setId(this.pharmLigneOperationId);
	 * return this.pharmLigneOperationId; }
	 */

}
