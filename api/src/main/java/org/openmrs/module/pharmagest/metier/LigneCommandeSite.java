package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmProduit;

public class LigneCommandeSite {

	private PharmProduit produit;
	private PharmCommandeSite commandeSite;
	private Integer stockIni;
	private Integer qteRecu;
	private Integer qteUtil;
	private Integer qtePerte;
	private Integer stockDispo;
	private Integer nbrJourRuptur;
	private Integer qteDistri1;
	private Integer qteDistri2;
	private Date datePerem;
	private String numLot;
	private Integer qtePro;
	private Integer sdu;
	private int cmm;
	private double mdu;
	private Integer sduN1;
	private Integer ql;

	public LigneCommandeSite() {
	}

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public PharmCommandeSite getCommandeSite() {
		return commandeSite;
	}

	public void setCommandeSite(PharmCommandeSite commandeSite) {
		this.commandeSite = commandeSite;
	}

	public Integer getStockIni() {
		return stockIni;
	}

	public void setStockIni(Integer stockIni) {
		this.stockIni = stockIni;
	}

	public Integer getQteRecu() {
		return qteRecu;
	}

	public void setQteRecu(Integer qteRecu) {
		this.qteRecu = qteRecu;
	}

	public Integer getQteUtil() {
		return qteUtil;
	}

	public void setQteUtil(Integer qteUtil) {
		this.qteUtil = qteUtil;
	}

	public Integer getQtePerte() {
		return qtePerte;
	}

	public void setQtePerte(Integer qtePerte) {
		this.qtePerte = qtePerte;
	}

	public Integer getStockDispo() {
		return stockDispo;
	}

	public void setStockDispo(Integer stockDispo) {
		this.stockDispo = stockDispo;
	}

	public Integer getNbrJourRuptur() {
		return nbrJourRuptur;
	}

	public void setNbrJourRuptur(Integer nbrJourRuptur) {
		this.nbrJourRuptur = nbrJourRuptur;
	}

	public Integer getQteDistri1() {
		return qteDistri1;
	}

	public void setQteDistri1(Integer qteDistri1) {
		this.qteDistri1 = qteDistri1;
	}

	public Integer getQteDistri2() {
		return qteDistri2;
	}

	public void setQteDistri2(Integer qteDistri2) {
		this.qteDistri2 = qteDistri2;
	}

	public Date getDatePerem() {
		return datePerem;
	}

	public void setDatePerem(Date datePerem) {
		this.datePerem = datePerem;
	}

	public String getNumLot() {
		return numLot;
	}

	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}

	public Integer getQtePro() {
		return qtePro;
	}

	public void setQtePro(Integer qtePro) {
		this.qtePro = qtePro;
	}

	public Integer getSdu() {
		return sdu;
	}

	public void setSdu(Integer sdu) {
		this.sdu = sdu;
	}

	public int getCmm() {
		return cmm;
	}

	public void setCmm(int cmm) {
		this.cmm = cmm;
	}

	public double getMdu() {
		return mdu;
	}

	public void setMdu(double mdu) {
		this.mdu = mdu;
	}

	public Integer getSduN1() {
		return sduN1;
	}

	public void setSduN1(Integer sduN1) {
		this.sduN1 = sduN1;
	}

	public Integer getQl() {
		return ql;
	}

	public void setQl(Integer ql) {
		this.ql = ql;
	}

}
