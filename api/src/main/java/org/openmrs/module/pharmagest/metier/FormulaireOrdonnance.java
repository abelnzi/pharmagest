package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.GestionnairePharma;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;

public class FormulaireOrdonnance {

	private Ordonnance ordonnance;
	private LigneDispensation ligneDispensation;

	public FormulaireOrdonnance() {
		this.ordonnance=new Ordonnance();
		this.ligneDispensation=new LigneDispensation();
	}

	/**
	 * @return the ligneDispensation
	 */
	public LigneDispensation getLigneDispensation() {
		return ligneDispensation;
	}

	/**
	 * @param ligneDispensation
	 *            the ligneDispensation to set
	 */
	public void setLigneDispensation(LigneDispensation ligneDispensation) {
		this.ligneDispensation = ligneDispensation;
	}

	/**
	 * @return the ordonnance
	 */
	public Ordonnance getOrdonnance() {
		return ordonnance;
	}

	/**
	 * @param ordonnance
	 *            the ordonnance to set
	 */
	public void setOrdonnance(Ordonnance ordonnance) {
		this.ordonnance = ordonnance;
	}

	public Medecin getMedecin() {
		return ordonnance.getMedecin();
	}

	public void setMedecin(Medecin medecin) {
		ordonnance.setMedecin(medecin);
	}

	public Programme getProgramme() {
		return ordonnance.getProgramme();
	}

	public void setProgramme(Programme programme) {
		ordonnance.setProgramme(programme);
	}

	public GestionnairePharma getGestionnairePharma() {
		return ordonnance.getGestionnairePharma();
	}

	public void setGestionnairePharma(GestionnairePharma gestionnairePharma) {
		ordonnance.setGestionnairePharma(gestionnairePharma);
	}

	public PatientComplement getPatientComplement() {
		return ordonnance.getPatientComplement();
	}

	public void setPatientComplement(PatientComplement patientComplement) {
		ordonnance.setPatientComplement(patientComplement);
	}

	public Regime getRegime() {
		return ordonnance.getRegime();
	}

	public void setRegime(Regime regime) {
		ordonnance.setRegime(regime);
	}

	public Date getOrdDatePrescrip() {
		return ordonnance.getOrdDatePrescrip();
	}

	public void setOrdDatePrescrip(Date ordDatePrescrip) {
		ordonnance.setOrdDatePrescrip(ordDatePrescrip);
	}

	public String getOrdBut() {
		return ordonnance.getOrdBut();
	}

	public void setOrdBut(String ordBut) {
		ordonnance.setOrdBut(ordBut);
	}

	public Date getOrdDateDispen() {
		return ordonnance.getOrdDateDispen();
	}

	public void setOrdDateDispen(Date ordDateDispen) {
		ordonnance.setOrdDateDispen(ordDateDispen);
	}

	public Date getOrdDateSaisi() {
		return ordonnance.getOrdDateSaisi();
	}

	public void setOrdDateSaisi(Date ordDateSaisi) {
		ordonnance.setOrdDateSaisi(ordDateSaisi);
	}

	public Integer getOrdMntTotal() {
		return ordonnance.getOrdMntTotal();
	}

	public void setOrdMntTotal(Integer ordMntTotal) {
		ordonnance.setOrdMntTotal(ordMntTotal);
	}

	public Integer getOrdNbreJrsTrai() {
		return ordonnance.getOrdNbreJrsTrai();
	}

	public void setOrdNbreJrsTrai(Integer ordNbreJrsTrai) {
		ordonnance.setOrdNbreJrsTrai(ordNbreJrsTrai);
	}

	public String getOrdNum() {
		return ordonnance.getOrdNum();
	}

	public void setOrdNum(String ordNum) {
		ordonnance.setOrdNum(ordNum);
	}

	public Date getOrdDateRdv() {
		return ordonnance.getOrdDateRdv();
	}

	public void setOrdDateRdv(Date ordDateRdv) {
		ordonnance.setOrdDateRdv(ordDateRdv);
	}

	public Produit getProduit() {
		return ligneDispensation.getProduit();
	}

	public void setProduit(Produit produit) {
		ligneDispensation.setProduit(produit);
	}

	public Integer getServQteDem() {
		return ligneDispensation.getServQteDem();
	}

	public void setServQteDem(Integer servQteDem) {
		ligneDispensation.setServQteDem(servQteDem);
	}

	public Integer getServQteServi() {
		return ligneDispensation.getServQteServi();
	}

	public void setServQteServi(Integer servQteServi) {
		ligneDispensation.setServQteServi(servQteServi);
	}

	public Integer getServPrixUnit() {
		return ligneDispensation.getServPrixUnit();
	}

	public void setServPrixUnit(Integer servPrixUnit) {
		ligneDispensation.setServPrixUnit(servPrixUnit);
	}
}
