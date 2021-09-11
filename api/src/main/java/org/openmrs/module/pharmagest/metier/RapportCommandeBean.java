package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;

public class RapportCommandeBean {
	private int stockIni;
	private int qteRecu;
	private int qteUtil;
	private int pertes;
	private int ajustements;
	private int stockDispo;
	private int nbrJrsRup;
	private int nbrSiteRup;
	private int cmm;
	private double moisStock;
	private Integer qteCom;
	private PharmProduit produit;

	public int getStockIni() {
		return stockIni;
	}

	public void setStockIni(int stockIni) {
		this.stockIni = stockIni;
	}

	public int getQteRecu() {
		return qteRecu;
	}

	public void setQteRecu(int qteRecu) {
		this.qteRecu = qteRecu;
	}

	public int getQteUtil() {
		return qteUtil;
	}

	public void setQteUtil(int qteUtil) {
		this.qteUtil = qteUtil;
	}

	public int getPertes() {
		return pertes;
	}

	public void setPertes(int pertes) {
		this.pertes = pertes;
	}

	public int getAjustements() {
		return ajustements;
	}

	public void setAjustements(int ajustements) {
		this.ajustements = ajustements;
	}

	public int getStockDispo() {
		return stockDispo;
	}

	public void setStockDispo(int stockDispo) {
		this.stockDispo = stockDispo;
	}

	public int getNbrJrsRup() {
		return nbrJrsRup;
	}

	public void setNbrJrsRup(int nbrJrsRup) {
		this.nbrJrsRup = nbrJrsRup;
	}

	public int getNbrSiteRup() {
		return nbrSiteRup;
	}

	public void setNbrSiteRup(int nbrSiteRup) {
		this.nbrSiteRup = nbrSiteRup;
	}

	public int getCmm() {
		return cmm;
	}

	public void setCmm(int cmm) {
		this.cmm = cmm;
	}

	public double getMoisStock() {
		return moisStock;
	}

	public void setMoisStock(double moisStock) {
		this.moisStock = moisStock;
	}

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public Integer getQteCom() {
		return qteCom;
	}

	public void setQteCom(Integer qteCom) {
		this.qteCom = qteCom;
	}

}
