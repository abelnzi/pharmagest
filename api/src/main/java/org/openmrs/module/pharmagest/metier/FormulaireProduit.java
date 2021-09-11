package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;

public class FormulaireProduit {
	private PharmProduit pharmProduit;
	private Date dateConso;
	private PharmProgramme programme;
	private Date dateDebut ;
	private Date dateFin;
	private String typeRapport ;

	public FormulaireProduit(PharmProduit pharmProduit) {

		this.pharmProduit = new PharmProduit();
	}

	public Integer getProdId() {
		return pharmProduit.getProdId();
	}

	public void setProdId(Integer prodId) {
		pharmProduit.setProdId(prodId);
	}

	public PharmClassePharma getPharmClassePharma() {
		return pharmProduit.getPharmClassePharma();
	}

	public void setPharmClassePharma(PharmClassePharma pharmClassePharma) {
		pharmProduit.setPharmClassePharma(pharmClassePharma);
	}

	public String getProdLib() {
		return pharmProduit.getProdLib();
	}

	public void setProdLib(String prodLib) {
		pharmProduit.setProdLib(prodLib);
	}

	public Date getProdDateExp() {
		return pharmProduit.getProdDateExp();
	}

	public void setProdDateExp(Date prodDateExp) {
		pharmProduit.setProdDateExp(prodDateExp);
	}

	public String getFullName() {
		return pharmProduit.getFullName();
	}

	public FormulaireProduit() {
		this.pharmProduit = new PharmProduit();
	}

	public PharmProduit getPharmProduit() {
		return pharmProduit;
	}

	public void setPharmProduit(PharmProduit pharmProduit) {
		this.pharmProduit = pharmProduit;
	}

	public String getProdCode() {
		return pharmProduit.getProdCode();
	}

	public void setProdCode(String prodCode) {
		pharmProduit.setProdCode(prodCode);
	}

	public Date getDateConso() {
		return dateConso;
	}

	public void setDateConso(Date dateConso) {
		this.dateConso = dateConso;
	}

	public PharmProgramme getProgramme() {
		return programme;
	}

	public void setProgramme(PharmProgramme programme) {
		this.programme = programme;
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

	public String getTypeRapport() {
		return typeRapport;
	}

	public void setTypeRapport(String typeRapport) {
		this.typeRapport = typeRapport;
	}

}
