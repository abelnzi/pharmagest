package org.openmrs.module.pharmagest;

// Generated 2 juil. 2015 23:11:24 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Produit generated by hbm2java
 */
public class Produit implements java.io.Serializable {

	private int prodId;
	private ClassePharma classePharma;
	private String prodLib;
	private Date prodDateExp;
	private Set<PrixProduit> prixProduits = new HashSet<PrixProduit>(0);
	private Set<HistoMouvementStock> histoMouvementStocks = new HashSet<HistoMouvementStock>(
			0);
	private Set<Stocker> stockers = new HashSet<Stocker>(0);
	private Set<LingeOperation> lingeOperations = new HashSet<LingeOperation>(0);
	private Set<Programme> programmes = new HashSet<Programme>(0);
	private Set<LigneInventaire> ligneInventaires = new HashSet<LigneInventaire>(
			0);
	private Set<Regime> regimes = new HashSet<Regime>(0);
	private Set<LigneDispensation> ligneDispensations = new HashSet<LigneDispensation>(
			0);

	public Produit() {
	}

	public Produit(int prodId) {
		this.prodId = prodId;
	}

	public Produit(int prodId, ClassePharma classePharma, String prodLib,
			Date prodDateExp, Set<PrixProduit> prixProduits,
			Set<HistoMouvementStock> histoMouvementStocks,
			Set<Stocker> stockers, Set<LingeOperation> lingeOperations,
			Set<Programme> programmes, Set<LigneInventaire> ligneInventaires,
			Set<Regime> regimes, Set<LigneDispensation> ligneDispensations) {
		this.prodId = prodId;
		this.classePharma = classePharma;
		this.prodLib = prodLib;
		this.prodDateExp = prodDateExp;
		this.prixProduits = prixProduits;
		this.histoMouvementStocks = histoMouvementStocks;
		this.stockers = stockers;
		this.lingeOperations = lingeOperations;
		this.programmes = programmes;
		this.ligneInventaires = ligneInventaires;
		this.regimes = regimes;
		this.ligneDispensations = ligneDispensations;
	}

	public int getProdId() {
		return this.prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public ClassePharma getClassePharma() {
		return this.classePharma;
	}

	public void setClassePharma(ClassePharma classePharma) {
		this.classePharma = classePharma;
	}

	public String getProdLib() {
		return this.prodLib;
	}

	public void setProdLib(String prodLib) {
		this.prodLib = prodLib;
	}

	public Date getProdDateExp() {
		return this.prodDateExp;
	}

	public void setProdDateExp(Date prodDateExp) {
		this.prodDateExp = prodDateExp;
	}

	public Set<PrixProduit> getPrixProduits() {
		return this.prixProduits;
	}

	public void setPrixProduits(Set<PrixProduit> prixProduits) {
		this.prixProduits = prixProduits;
	}

	public Set<HistoMouvementStock> getHistoMouvementStocks() {
		return this.histoMouvementStocks;
	}

	public void setHistoMouvementStocks(
			Set<HistoMouvementStock> histoMouvementStocks) {
		this.histoMouvementStocks = histoMouvementStocks;
	}

	public Set<Stocker> getStockers() {
		return this.stockers;
	}

	public void setStockers(Set<Stocker> stockers) {
		this.stockers = stockers;
	}

	public Set<LingeOperation> getLingeOperations() {
		return this.lingeOperations;
	}

	public void setLingeOperations(Set<LingeOperation> lingeOperations) {
		this.lingeOperations = lingeOperations;
	}

	public Set<Programme> getProgrammes() {
		return this.programmes;
	}

	public void setProgrammes(Set<Programme> programmes) {
		this.programmes = programmes;
	}

	public Set<LigneInventaire> getLigneInventaires() {
		return this.ligneInventaires;
	}

	public void setLigneInventaires(Set<LigneInventaire> ligneInventaires) {
		this.ligneInventaires = ligneInventaires;
	}

	public Set<Regime> getRegimes() {
		return this.regimes;
	}

	public void setRegimes(Set<Regime> regimes) {
		this.regimes = regimes;
	}

	public Set<LigneDispensation> getLigneDispensations() {
		return this.ligneDispensations;
	}

	public void setLigneDispensations(Set<LigneDispensation> ligneDispensations) {
		this.ligneDispensations = ligneDispensations;
	}

}