package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;

public class FormulaireReception {

	private PharmReception pharmReception;
	private LigneReceptionMvt ligneReception;
	private TabReceptionMvt tabReceptionMvt;
	
	private String choix;
	private String typeSaisie;
	private Date dateDebut;
	private Date dateFin;
	PharmGestionnairePharma pharmGestionnairePharma;

	public FormulaireReception() {
		this.pharmReception = new PharmReception();
		this.ligneReception = new LigneReceptionMvt();
		this.tabReceptionMvt = new TabReceptionMvt();
	}

	public PharmProgramme getPharmProgramme() {
		return pharmReception.getPharmProgramme();
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		pharmReception.setPharmProgramme(pharmProgramme);
	}

	public PharmFournisseur getPharmFournisseur() {
		return pharmReception.getPharmFournisseur();
	}

	public void setPharmFournisseur(PharmFournisseur pharmFournisseur) {
		pharmReception.setPharmFournisseur(pharmFournisseur);
	}

	public Integer getLgnRecptQte() {
		return ligneReception.getLgnRecptQte();
	}

	public void setLgnRecptQte(Integer lgnRecptQte) {
		ligneReception.setLgnRecptQte(lgnRecptQte);
	}

	public Integer getLgnRecptPrixAchat() {
		return ligneReception.getLgnRecptPrixAchat();
	}

	public void setLgnRecptPrixAchat(Integer lgnRecptPrixAchat) {
		ligneReception.setLgnRecptPrixAchat(lgnRecptPrixAchat);
	}

	public Date getLgnDatePerem() {
		return ligneReception.getLgnDatePerem();
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		ligneReception.setLgnDatePerem(lgnDatePerem);
	}

	public String getLgnRecptLot() {
		return ligneReception.getLgnRecptLot();
	}

	public void setLgnRecptLot(String lgnRecptLot) {
		ligneReception.setLgnRecptLot(lgnRecptLot);
	}

	public PharmReception getPharmReception() {
		return pharmReception;
	}

	public void setPharmReception(PharmReception pharmReception) {
		this.pharmReception = pharmReception;
	}

	public LigneReceptionMvt getLigneReception() {
		return ligneReception;
	}

	public void setLigneReception(LigneReceptionMvt ligneReception) {
		this.ligneReception = ligneReception;
	}

	public TabReceptionMvt getTabReceptionMvt() {
		return tabReceptionMvt;
	}

	public void setTabReceptionMvt(TabReceptionMvt tabReceptionMvt) {
		this.tabReceptionMvt = tabReceptionMvt;
	}


	public int getRecptId() {
		return pharmReception.getRecptId();
	}

	public void setRecptId(int recptId) {
		pharmReception.setRecptId(recptId);
	}

	public PharmGestionnairePharma getPharmGestionnairePharma() {
		return pharmGestionnairePharma;
	}

	public void setPharmGestionnairePharma(PharmGestionnairePharma gestionnairePharma) {
		pharmGestionnairePharma=gestionnairePharma;
	}

	public String getRecptNum() {
		return pharmReception.getRecptNum();
	}

	public void setRecptNum(String recptNum) {
		pharmReception.setRecptNum(recptNum);
	}

	public Date getRecptDateRecept() {
		return pharmReception.getRecptDateRecept();
	}

	public void setRecptDateRecept(Date recptDateRecept) {
		pharmReception.setRecptDateRecept(recptDateRecept);
	}

	public Date getRecptDateSaisi() {
		return pharmReception.getRecptDateSaisi();
	}

	public void setRecptDateSaisi(Date recptDateSaisi) {
		pharmReception.setRecptDateSaisi(recptDateSaisi);
	}

	public Date getRecptDateModif() {
		return pharmReception.getRecptDateModif();
	}

	public void setRecptDateModif(Date recptDateModif) {
		pharmReception.setRecptDateModif(recptDateModif);
	}

	public String getRecptFlag() {
		return pharmReception.getRecptFlag();
	}

	public void setRecptFlag(String recptFlag) {
		pharmReception.setRecptFlag(recptFlag);
	}

	public String getRecptRef() {
		return pharmReception.getRecptRef();
	}

	public void setRecptRef(String recptRef) {
		pharmReception.setRecptRef(recptRef);
	}

	public Set<PharmLigneReception> getPharmLigneReceptions() {
		return pharmReception.getPharmLigneReceptions();
	}

	public void setPharmLigneReceptions(Set<PharmLigneReception> pharmLigneReceptions) {
		pharmReception.setPharmLigneReceptions(pharmLigneReceptions);
	}

	public PharmProduit getProduit() {
		return ligneReception.getProduit();
	}

	public void setProduit(PharmProduit produit) {
		ligneReception.setProduit(produit);
	}
	
	public PharmProduit getProduitDetail() {
		return ligneReception.getProduitDetail();
	}

	public void setProduitDetail(PharmProduit produitDetail) {
		ligneReception.setProduitDetail(produitDetail);
	}

	public void setReception(PharmReception reception) {
		ligneReception.setReception(reception);
	}

	public Integer getLgnRecptQteLivree() {
		return ligneReception.getLgnRecptQteLivree();
	}

	public void setLgnRecptQteLivree(Integer lgnRecptQteLivree) {
		ligneReception.setLgnRecptQteLivree(lgnRecptQteLivree);
	}

	public String getLgnRecptObserv() {
		return ligneReception.getLgnRecptObserv();
	}

	public void setLgnRecptObserv(String lgnRecptObserv) {
		ligneReception.setLgnRecptObserv(lgnRecptObserv);
	}

	public Integer getLgnRecptQteDetailRecu() {
		return ligneReception.getLgnRecptQteDetailRecu();
	}

	public void setLgnRecptQteDetailRecu(Integer lgnRecptQteDetailrecu) {
		ligneReception.setLgnRecptQteDetailRecu(lgnRecptQteDetailrecu);
	}

	public Integer getLgnRecptQteDetailLivree() {
		return ligneReception.getLgnRecptQteDetailLivree();
	}

	public void setLgnRecptQteDetailLivree(Integer lgnRecptQteDetailLivree) {
		ligneReception.setLgnRecptQteDetailLivree(lgnRecptQteDetailLivree);
	}

	public Date getLgnDatePeremDetail() {
		return ligneReception.getLgnDatePeremDetail();
	}

	public void setLgnDatePeremDetail(Date lgnDatePeremDetail) {
		ligneReception.setLgnDatePeremDetail(lgnDatePeremDetail);
	}

	public String getLgnRecptLotDetail() {
		return ligneReception.getLgnRecptLotDetail();
	}

	public void setLgnRecptLotDetail(String lgnRecptLotDetail) {
		ligneReception.setLgnRecptLotDetail(lgnRecptLotDetail);
	}

	public String getLgnRecptObservDetail() {
		return ligneReception.getLgnRecptObservDetail();
	}

	public void setLgnRecptObservDetail(String lgnRecptObservDetail) {
		ligneReception.setLgnRecptObservDetail(lgnRecptObservDetail);
	}

	public Integer getLgnRecptPrixAchatDetail() {
		return ligneReception.getLgnRecptPrixAchatDetail();
	}

	public void setLgnRecptPrixAchatDetail(Integer lgnRecptPrixAchatDetail) {
		ligneReception.setLgnRecptPrixAchatDetail(lgnRecptPrixAchatDetail);
	}

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	public String getTypeSaisie() {
		return typeSaisie;
	}

	public void setTypeSaisie(String typeSaisie) {
		this.typeSaisie = typeSaisie;
	}

	public String getTypeReception() {
		return pharmReception.getTypeReception();
	}

	public void setTypeReception(String typeReception) {
		pharmReception.setTypeReception(typeReception);
	}

	

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

}
