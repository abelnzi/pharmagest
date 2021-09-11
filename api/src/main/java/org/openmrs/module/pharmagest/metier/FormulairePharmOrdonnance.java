package org.openmrs.module.pharmagest.metier;

import java.util.Date;
import java.util.Set;

import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;

public class FormulairePharmOrdonnance {

	/**
	 * 
	 */

	private String idParam;
	private TabDispensationMvt tabDispensationMvt;
	private PharmGestionnairePharma gestionnairePharma;
	private LigneDispensationMvt ligneDispensationMvt;
	private PharmProduit pharmProduit;
	private Date lgnDatePerem;
	private String lgnRecptLot;
	private Integer ldQteDem;
	private Integer ldQteServi;
	private Integer ldPrixUnit;
	private PharmOrdonnance pharmOrdonnance;
	private Integer age ;
	private String sexe ;
	private String numPatient ;
	private Integer jrsPerdu;

	public FormulairePharmOrdonnance() {

		this.tabDispensationMvt = new TabDispensationMvt();
		this.gestionnairePharma = new PharmGestionnairePharma();
		this.pharmOrdonnance = new PharmOrdonnance();
	}

	public String getIdParam() {
		return idParam;
	}

	public void setIdParam(String idParam) {
		this.idParam = idParam;
	}

	public TabDispensationMvt getTabDispensationMvt() {
		return tabDispensationMvt;
	}

	public void setTabDispensationMvt(TabDispensationMvt tabDispensationMvt) {
		this.tabDispensationMvt = tabDispensationMvt;
	}

	
	public PharmGestionnairePharma getGestionnairePharma() {
		return gestionnairePharma;
	}

	public void setGestionnairePharma(PharmGestionnairePharma gestionnairePharma) {
		this.gestionnairePharma = gestionnairePharma;
	}

	public LigneDispensationMvt getLigneDispensationMvt() {
		return ligneDispensationMvt;
	}

	public void setLigneDispensationMvt(LigneDispensationMvt ligneDispensationMvt) {
		this.ligneDispensationMvt = ligneDispensationMvt;
	}

	public PharmProduit getPharmProduit() {
		return pharmProduit;
	}

	public void setPharmProduit(PharmProduit pharmProduit) {
		this.pharmProduit = pharmProduit;
	}

	public Date getLgnDatePerem() {
		return lgnDatePerem;
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		this.lgnDatePerem = lgnDatePerem;
	}

	public String getLgnRecptLot() {
		return lgnRecptLot;
	}

	public void setLgnRecptLot(String lgnRecptLot) {
		this.lgnRecptLot = lgnRecptLot;
	}

	public Integer getLdQteDem() {
		return ldQteDem;
	}

	public void setLdQteDem(Integer ldQteDem) {
		this.ldQteDem = ldQteDem;
	}

	public Integer getLdQteServi() {
		return ldQteServi;
	}

	public void setLdQteServi(Integer ldQteServi) {
		this.ldQteServi = ldQteServi;
	}

	public Integer getLdPrixUnit() {
		return ldPrixUnit;
	}

	public void setLdPrixUnit(Integer ldPrixUnit) {
		this.ldPrixUnit = ldPrixUnit;
	}

	public PharmOrdonnance getPharmOrdonnance() {
		return pharmOrdonnance;
	}

	public void setPharmOrdonnance(PharmOrdonnance pharmOrdonnance) {
		this.pharmOrdonnance = pharmOrdonnance;
	}

	public Integer getOrdId() {
		return pharmOrdonnance.getOrdId();
	}

	public void setOrdId(Integer ordId) {
		pharmOrdonnance.setOrdId(ordId);
	}

	public PharmMedecin getPharmMedecin() {
		return pharmOrdonnance.getPharmMedecin();
	}

	public void setPharmMedecin(PharmMedecin pharmMedecin) {
		pharmOrdonnance.setPharmMedecin(pharmMedecin);
	}

	public PharmGestionnairePharma getPharmGestionnairePharma() {
		return pharmOrdonnance.getPharmGestionnairePharma();
	}

	public void setPharmGestionnairePharma(PharmGestionnairePharma pharmGestionnairePharma) {
		pharmOrdonnance.setPharmGestionnairePharma(pharmGestionnairePharma);
	}

	public PharmProgramme getPharmProgramme() {
		return pharmOrdonnance.getPharmProgramme();
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		pharmOrdonnance.setPharmProgramme(pharmProgramme);
	}

	public PatientIdentifier getPatientIdentifier() {
		return pharmOrdonnance.getPatientIdentifier();
	}

	public void setPatientIdentifier(PatientIdentifier patientIdentifier) {
		pharmOrdonnance.setPatientIdentifier(patientIdentifier);
	}

	public PharmRegime getPharmRegime() {
		return pharmOrdonnance.getPharmRegime();
	}

	public void setPharmRegime(PharmRegime pharmRegime) {
		pharmOrdonnance.setPharmRegime(pharmRegime);
	}

	public Date getOrdDatePrescrip() {
		return pharmOrdonnance.getOrdDatePrescrip();
	}

	public void setOrdDatePrescrip(Date ordDatePrescrip) {
		pharmOrdonnance.setOrdDatePrescrip(ordDatePrescrip);
	}

	public String getOrdBut() {
		return pharmOrdonnance.getOrdBut();
	}

	public void setOrdBut(String ordBut) {
		pharmOrdonnance.setOrdBut(ordBut);
	}

	public Date getOrdDateDispen() {
		return pharmOrdonnance.getOrdDateDispen();
	}

	public void setOrdDateDispen(Date ordDateDispen) {
		pharmOrdonnance.setOrdDateDispen(ordDateDispen);
	}

	public Date getOrdDateSaisi() {
		return pharmOrdonnance.getOrdDateSaisi();
	}

	public void setOrdDateSaisi(Date ordDateSaisi) {
		pharmOrdonnance.setOrdDateSaisi(ordDateSaisi);
	}

	public Integer getOrdMntTotal() {
		return pharmOrdonnance.getOrdMntTotal();
	}

	public void setOrdMntTotal(Integer ordMntTotal) {
		pharmOrdonnance.setOrdMntTotal(ordMntTotal);
	}

	public Integer getOrdNbreJrsTrai() {
		return pharmOrdonnance.getOrdNbreJrsTrai();
	}

	public void setOrdNbreJrsTrai(Integer ordNbreJrsTrai) {
		pharmOrdonnance.setOrdNbreJrsTrai(ordNbreJrsTrai);
	}

	public String getOrdNum() {
		return pharmOrdonnance.getOrdNum();
	}

	public void setOrdNum(String ordNum) {
		pharmOrdonnance.setOrdNum(ordNum);
	}

	public Date getOrdDateRdv() {
		return pharmOrdonnance.getOrdDateRdv();
	}

	public void setOrdDateRdv(Date ordDateRdv) {
		pharmOrdonnance.setOrdDateRdv(ordDateRdv);
	}

	public Set<PharmLigneDispensation> getPharmLigneDispensations() {
		return pharmOrdonnance.getPharmLigneDispensations();
	}

	public void setPharmLigneDispensations(Set<PharmLigneDispensation> pharmLigneDispensations) {
		pharmOrdonnance.setPharmLigneDispensations(pharmLigneDispensations);
	}

	
	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNumPatient() {
		return numPatient;
	}

	public void setNumPatient(String numPatient) {
		this.numPatient = numPatient;
	}

	public Integer getJrsPerdu() {
		return jrsPerdu;
	}

	public void setJrsPerdu(Integer jrsPerdu) {
		this.jrsPerdu = jrsPerdu;
	}

	/*
	 * public LigneDispensationId addLigneDispensationId() {
	 * this.pharmLigneDispensationId.setProdId(this.getProduit().getProdId());
	 * this.pharmLigneDispensationId.setOrdId(this.getOrdonnance().getOrdId());
	 * this.getLigneDispensation().setId(this.ligneDispensationId); return
	 * ligneDispensationId; }
	 */

}
