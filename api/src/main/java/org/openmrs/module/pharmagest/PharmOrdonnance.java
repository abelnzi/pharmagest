package org.openmrs.module.pharmagest;

// Generated 16 ao�t 2015 03:07:54 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.openmrs.PatientIdentifier;

/**
 * PharmOrdonnance generated by hbm2java
 */
public class PharmOrdonnance implements java.io.Serializable {

	private Integer ordId;
	private PharmMedecin pharmMedecin;
	private PharmGestionnairePharma pharmGestionnairePharma;
	private PharmProgramme pharmProgramme;
	private PatientIdentifier patientIdentifier;
	private PharmRegime pharmRegime;
	private Date ordDatePrescrip;
	private String ordBut;
	private Date ordDateDispen;
	private Date ordDateSaisi;
	private Integer ordMntTotal;
	private Integer ordNbreJrsTrai;
	private String ordNum;
	private Date ordDateRdv;
	private Set<PharmLigneDispensation> pharmLigneDispensations = new HashSet<PharmLigneDispensation>(
			0);

	public PharmOrdonnance() {
	}

	public PharmOrdonnance(PharmGestionnairePharma pharmGestionnairePharma) {
		this.pharmGestionnairePharma = pharmGestionnairePharma;
	}

	public PharmOrdonnance(PharmMedecin pharmMedecin,
			PharmGestionnairePharma pharmGestionnairePharma,
			PharmProgramme pharmProgramme, PatientIdentifier patientIdentifier,
			PharmRegime pharmRegime, Date ordDatePrescrip, String ordBut,
			Date ordDateDispen, Date ordDateSaisi, Integer ordMntTotal,
			Integer ordNbreJrsTrai, String ordNum, Date ordDateRdv,
			Set<PharmLigneDispensation> pharmLigneDispensations) {
		this.pharmMedecin = pharmMedecin;
		this.pharmGestionnairePharma = pharmGestionnairePharma;
		this.pharmProgramme = pharmProgramme;
		this.patientIdentifier = patientIdentifier;
		this.pharmRegime = pharmRegime;
		this.ordDatePrescrip = ordDatePrescrip;
		this.ordBut = ordBut;
		this.ordDateDispen = ordDateDispen;
		this.ordDateSaisi = ordDateSaisi;
		this.ordMntTotal = ordMntTotal;
		this.ordNbreJrsTrai = ordNbreJrsTrai;
		this.ordNum = ordNum;
		this.ordDateRdv = ordDateRdv;
		this.pharmLigneDispensations = pharmLigneDispensations;
	}

	public Integer getOrdId() {
		return this.ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public PharmMedecin getPharmMedecin() {
		return this.pharmMedecin;
	}

	public void setPharmMedecin(PharmMedecin pharmMedecin) {
		this.pharmMedecin = pharmMedecin;
	}

	public PharmGestionnairePharma getPharmGestionnairePharma() {
		return this.pharmGestionnairePharma;
	}

	public void setPharmGestionnairePharma(
			PharmGestionnairePharma pharmGestionnairePharma) {
		this.pharmGestionnairePharma = pharmGestionnairePharma;
	}

	public PharmProgramme getPharmProgramme() {
		return this.pharmProgramme;
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		this.pharmProgramme = pharmProgramme;
	}

	public PatientIdentifier getPatientIdentifier() {
		return this.patientIdentifier;
	}

	public void setPatientIdentifier(PatientIdentifier patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}

	public PharmRegime getPharmRegime() {
		return this.pharmRegime;
	}

	public void setPharmRegime(PharmRegime pharmRegime) {
		this.pharmRegime = pharmRegime;
	}

	public Date getOrdDatePrescrip() {
		return this.ordDatePrescrip;
	}

	public void setOrdDatePrescrip(Date ordDatePrescrip) {
		this.ordDatePrescrip = ordDatePrescrip;
	}

	public String getOrdBut() {
		return this.ordBut;
	}

	public void setOrdBut(String ordBut) {
		this.ordBut = ordBut;
	}

	public Date getOrdDateDispen() {
		return this.ordDateDispen;
	}

	public void setOrdDateDispen(Date ordDateDispen) {
		this.ordDateDispen = ordDateDispen;
	}

	public Date getOrdDateSaisi() {
		return this.ordDateSaisi;
	}

	public void setOrdDateSaisi(Date ordDateSaisi) {
		this.ordDateSaisi = ordDateSaisi;
	}

	public Integer getOrdMntTotal() {
		return this.ordMntTotal;
	}

	public void setOrdMntTotal(Integer ordMntTotal) {
		this.ordMntTotal = ordMntTotal;
	}

	public Integer getOrdNbreJrsTrai() {
		return this.ordNbreJrsTrai;
	}

	public void setOrdNbreJrsTrai(Integer ordNbreJrsTrai) {
		this.ordNbreJrsTrai = ordNbreJrsTrai;
	}

	public String getOrdNum() {
		return this.ordNum;
	}

	public void setOrdNum(String ordNum) {
		this.ordNum = ordNum;
	}

	public Date getOrdDateRdv() {
		return this.ordDateRdv;
	}

	public void setOrdDateRdv(Date ordDateRdv) {
		this.ordDateRdv = ordDateRdv;
	}

	public Set<PharmLigneDispensation> getPharmLigneDispensations() {
		return this.pharmLigneDispensations;
	}

	public void setPharmLigneDispensations(
			Set<PharmLigneDispensation> pharmLigneDispensations) {
		this.pharmLigneDispensations = pharmLigneDispensations;
	}

}
