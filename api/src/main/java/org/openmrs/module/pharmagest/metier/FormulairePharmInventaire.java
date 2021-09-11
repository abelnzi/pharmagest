package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;

public class FormulairePharmInventaire {

	private PharmInventaire pharmInventaire;
	private PharmProgramme pharmProgramme;
	private TabInventaireMvt tabInventaire;
	private LigneInventaireMvt ligneInventaireMvt;
	private Date dateParam;
	private Date dateParam2;
	private PharmGestionnairePharma gestionnairePharma;

	public FormulairePharmInventaire() {
		this.pharmInventaire = new PharmInventaire();
		this.tabInventaire = new TabInventaireMvt();
		this.pharmProgramme = new PharmProgramme();
		this.ligneInventaireMvt = new LigneInventaireMvt();
	}

	public PharmInventaire getPharmInventaire() {
		return pharmInventaire;
	}

	public void setPharmInventaire(PharmInventaire pharmInventaire) {
		this.pharmInventaire = pharmInventaire;
	}

	public PharmProgramme getPharmProgramme() {
		return pharmProgramme;
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		this.pharmProgramme = pharmProgramme;
	}

	public TabInventaireMvt getTabInventaire() {
		return tabInventaire;
	}

	public void setTabInventaire(TabInventaireMvt tabInventaire) {
		this.tabInventaire = tabInventaire;
	}

	public LigneInventaireMvt getLigneInventaireMvt() {
		return ligneInventaireMvt;
	}

	public void setLigneInventaireMvt(LigneInventaireMvt ligneInventaireMvt) {
		this.ligneInventaireMvt = ligneInventaireMvt;
	}

	public Date getInvDateCrea() {
		return pharmInventaire.getInvDateCrea();
	}

	public void setInvDateCrea(Date invDateCrea) {
		pharmInventaire.setInvDateCrea(invDateCrea);
	}

	public Date getInvDeb() {
		return pharmInventaire.getInvDeb();
	}

	public void setInvDeb(Date invDeb) {
		pharmInventaire.setInvDeb(invDeb);
	}

	public Date getInvFin() {
		return pharmInventaire.getInvFin();
	}

	public void setInvFin(Date invFin) {
		pharmInventaire.setInvFin(invFin);
	}

	public PharmProduit getProduit() {
		return ligneInventaireMvt.getProduit();
	}

	public void setProduit(PharmProduit produit) {
		ligneInventaireMvt.setProduit(produit);
	}

	public Integer getQtePhysique() {
		return ligneInventaireMvt.getQtePhysique();
	}

	public void setQtePhysique(Integer qtePhysique) {
		ligneInventaireMvt.setQtePhysique(qtePhysique);
	}

	public Integer getQteLogique() {
		return ligneInventaireMvt.getQteLogique();
	}

	public void setQteLogique(Integer qteLogique) {
		ligneInventaireMvt.setQteLogique(qteLogique);
	}

	public String getObservation() {
		return ligneInventaireMvt.getObservation();
	}

	public void setObservation(String observation) {
		ligneInventaireMvt.setObservation(observation);
	}

	public Integer getEcart() {
		return ligneInventaireMvt.getEcart();
	}

	public void setEcart(Integer ecart) {
		ligneInventaireMvt.setEcart(ecart);
	}

	public Date getLgnDatePerem() {
		return ligneInventaireMvt.getLgnDatePerem();
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		ligneInventaireMvt.setLgnDatePerem(lgnDatePerem);
	}

	public String getLgnLot() {
		return ligneInventaireMvt.getLgnLot();
	}

	public void setLgnLot(String lgnLot) {
		ligneInventaireMvt.setLgnLot(lgnLot);
	}

	public Date getDateParam() {
		return dateParam;
	}

	public void setDateParam(Date dateParam) {
		this.dateParam = dateParam;
	}

	public Date getDateParam2() {
		return dateParam2;
	}

	public void setDateParam2(Date dateParam2) {
		this.dateParam2 = dateParam2;
	}

	public PharmGestionnairePharma getGestionnairePharma() {
		return gestionnairePharma;
	}

	public void setGestionnairePharma(PharmGestionnairePharma gestionnairePharma) {
		this.gestionnairePharma = gestionnairePharma;
	}

}
