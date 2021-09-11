package org.openmrs.module.pharmagest.metier;

import java.util.Date;
import java.util.Set;

import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.PharmAssurance;
import org.openmrs.module.pharmagest.PharmClient;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;

public class FormulaireVente {

	/**
	 * 
	 */

	private String idParam;
	private TabDispensationMvt tabDispensationMvt;
	private PharmGestionnairePharma gestionnairePharma;
	private LigneDispensationMvt ligneDispensationMvt;
	private PharmProduit pharmProduit;
	private PharmOrdonnance pharmOrdonnance;
	private PharmClient client;
	private PharmClient clientClient;
	private PharmAssurance assurance;
	private Integer taux;
	private Integer age ;
	private String sexe ;
	private String numPatient ;
	private Integer jrsPerdu;
	private String typeVente;
	private String newAssur;
    private Date debut;
    private Date fin;
    
	public FormulaireVente() {

		this.tabDispensationMvt = new TabDispensationMvt();
		this.gestionnairePharma = new PharmGestionnairePharma();
		this.pharmOrdonnance = new PharmOrdonnance();
		this.ligneDispensationMvt=new LigneDispensationMvt();
		this.client=new PharmClient();
		this.clientClient=new PharmClient();
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

	public PharmClient getClient() {
		return client;
	}

	public void setClient(PharmClient client) {
		this.client = client;
	}

	public String getTypeVente() {
		return typeVente;
	}

	public void setTypeVente(String typeVente) {
		this.typeVente = typeVente;
	}

	public Date getLgnDatePerem() {
		return ligneDispensationMvt.getLgnDatePerem();
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		ligneDispensationMvt.setLgnDatePerem(lgnDatePerem);
	}

	public String getLgnRecptLot() {
		return ligneDispensationMvt.getLgnRecptLot();
	}

	public void setLgnRecptLot(String lgnRecptLot) {
		ligneDispensationMvt.setLgnRecptLot(lgnRecptLot);
	}

	public Integer getLdQteDem() {
		return ligneDispensationMvt.getLdQteDem();
	}

	public void setLdQteDem(Integer ldQteDem) {
		ligneDispensationMvt.setLdQteDem(ldQteDem);
	}

	public Integer getLdQteServi() {
		return ligneDispensationMvt.getLdQteServi();
	}

	public void setLdQteServi(Integer ldQteServi) {
		ligneDispensationMvt.setLdQteServi(ldQteServi);
	}

	public Integer getLdPrixUnit() {
		return ligneDispensationMvt.getLdPrixUnit();
	}

	public void setLdPrixUnit(Integer ldPrixUnit) {
		ligneDispensationMvt.setLdPrixUnit(ldPrixUnit);
	}

	public String getOrdHospi() {
		return pharmOrdonnance.getOrdHospi();
	}

	public void setOrdHospi(String ordHospi) {
		pharmOrdonnance.setOrdHospi(ordHospi);
	}

	public String getOrdService() {
		return pharmOrdonnance.getOrdService();
	}

	public void setOrdService(String ordService) {
		pharmOrdonnance.setOrdService(ordService);
	}

	public String getCliNom() {
		return client.getCliNom();
	}

	public void setCliNom(String cliNom) {
		client.setCliNom(cliNom);
	}

	public String getCliPrenom() {
		return client.getCliPrenom();
	}

	public void setCliPrenom(String cliPrenom) {
		client.setCliPrenom(cliPrenom);
	}

	public Date getCliDateNaiss() {
		return client.getCliDateNaiss();
	}

	public void setCliDateNaiss(Date cliDateNaiss) {
		client.setCliDateNaiss(cliDateNaiss);
	}

	public String getCliGenre() {
		return client.getCliGenre();
	}

	public void setCliGenre(String cliGenre) {
		client.setCliGenre(cliGenre);
	}

	public String getCliTel1() {
		return client.getCliTel1();
	}

	public void setCliTel1(String cliTel1) {
		client.setCliTel1(cliTel1);
	}

	public String getCliType() {
		return client.getCliType();
	}

	public void setCliType(String cliType) {
		client.setCliType(cliType);
	}
	
	

	public PharmAssurance getAssurance() {
		return assurance;
	}

	public void setAssurance(PharmAssurance assurance) {
		this.assurance = assurance;
	}

	public Integer getTaux() {
		return taux;
	}

	public void setTaux(Integer taux) {
		this.taux = taux;
	}

	public String getCliNomClient() {
		return clientClient.getCliNom();
	}

	public void setCliNomClient(String cliNomClient) {
		clientClient.setCliNom(cliNomClient);
	}

	public String getCliPrenomClient() {
		return clientClient.getCliPrenom();
		
	}

	public void setCliPrenomClient(String cliPrenomClient) {
		clientClient.setCliPrenom(cliPrenomClient);		
	}

	public Date getCliDateNaissClient() {
		return clientClient.getCliDateNaiss();
	}

	public void setCliDateNaissClient(Date cliDateNaissClient) {
		clientClient.setCliDateNaiss(cliDateNaissClient);	
	}

	public String getCliGenreClient() {
		return clientClient.getCliGenre();

	}

	public void setCliGenreClient(String cliGenreClient) {
		clientClient.setCliGenre(cliGenreClient);
	}

	public String getCliTel1Client() {
		return clientClient.getCliTel1();
	}

	public void setCliTel1Client(String cliTel1Client) {
		clientClient.setCliTel1(cliTel1Client);
	}

	public String getCliTypeClient() {
		return clientClient.getCliType();
	}

	public void setCliTypeClient(String cliTypeClient) {
		clientClient.setCliType(cliTypeClient);
	}

	public String getNewAssur() {
		return newAssur;
	}

	public void setNewAssur(String newAssur) {
		this.newAssur = newAssur;
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public PharmClient getClientClient() {
		return clientClient;
	}

	public void setClientClient(PharmClient clientClient) {
		this.clientClient = clientClient;
	}

	/*
	 * public LigneDispensationId addLigneDispensationId() {
	 * this.pharmLigneDispensationId.setProdId(this.getProduit().getProdId());
	 * this.pharmLigneDispensationId.setOrdId(this.getOrdonnance().getOrdId());
	 * this.getLigneDispensation().setId(this.ligneDispensationId); return
	 * ligneDispensationId; }
	 */

}
