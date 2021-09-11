package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.PharmProduit;

public class LigneReceptionMvt {

	private PharmProduit produit;
	private PharmProduit produitDetail;
	private PharmReception reception;
	private Integer lgnRecptQte;
	private Integer lgnRecptQteLivree;
	private Integer lgnRecptQteDetailRecu;
	private Integer lgnRecptQteDetailLivree;
	private Integer lgnRecptPrixAchat;
	private Date lgnDatePerem;
	private String lgnRecptLot;
	private String lgnRecptObserv;
	private Date lgnDatePeremDetail;
	private String lgnRecptLotDetail;
	private String lgnRecptObservDetail;
	private Integer lgnRecptPrixAchatDetail;
	
	public LigneReceptionMvt() {
	}

	public LigneReceptionMvt(PharmProduit produit, PharmReception reception) {
		this.produit = produit;
		this.reception = reception;
	}

	public LigneReceptionMvt(PharmProduit produit, PharmReception reception, Integer lgnRecptQte,
			Integer lgnRecptPrixAchat, Date lgnDatePerem, String lgnRecptLot) {

		this.produit = produit;
		this.reception = reception;
		this.lgnRecptQte = lgnRecptQte;
		this.lgnRecptPrixAchat = lgnRecptPrixAchat;
		this.lgnDatePerem = lgnDatePerem;
		this.lgnRecptLot = lgnRecptLot;
	}

	public PharmProduit getProduit() {
		return this.produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public PharmReception getReception() {
		return this.reception;
	}

	public void setReception(PharmReception reception) {
		this.reception = reception;
	}

	public Integer getLgnRecptQte() {
		return this.lgnRecptQte;
	}

	public void setLgnRecptQte(Integer lgnRecptQte) {
		this.lgnRecptQte = lgnRecptQte;
	}

	public Integer getLgnRecptPrixAchat() {
		return this.lgnRecptPrixAchat;
	}

	public void setLgnRecptPrixAchat(Integer lgnRecptPrixAchat) {
		this.lgnRecptPrixAchat = lgnRecptPrixAchat;
	}

	public Date getLgnDatePerem() {
		return this.lgnDatePerem;
	}

	public void setLgnDatePerem(Date lgnDatePerem) {
		this.lgnDatePerem = lgnDatePerem;
	}

	public String getLgnRecptLot() {
		return this.lgnRecptLot;
	}

	public void setLgnRecptLot(String lgnRecptLot) {
		this.lgnRecptLot = lgnRecptLot;
	}

	public Integer getLgnRecptQteLivree() {
		return lgnRecptQteLivree;
	}

	public void setLgnRecptQteLivree(Integer lgnRecptQteLivree) {
		this.lgnRecptQteLivree = lgnRecptQteLivree;
	}

	public String getLgnRecptObserv() {
		return lgnRecptObserv;
	}

	public void setLgnRecptObserv(String lgnRecptObserv) {
		this.lgnRecptObserv = lgnRecptObserv;
	}

	public Integer getLgnRecptQteDetailRecu() {
		return lgnRecptQteDetailRecu;
	}

	public void setLgnRecptQteDetailRecu(Integer lgnRecptQteDetailrecu) {
		this.lgnRecptQteDetailRecu = lgnRecptQteDetailrecu;
	}

	public Integer getLgnRecptQteDetailLivree() {
		return lgnRecptQteDetailLivree;
	}

	public void setLgnRecptQteDetailLivree(Integer lgnRecptQteDetailLivree) {
		this.lgnRecptQteDetailLivree = lgnRecptQteDetailLivree;
	}

	public Date getLgnDatePeremDetail() {
		return lgnDatePeremDetail;
	}

	public void setLgnDatePeremDetail(Date lgnDatePeremDetail) {
		this.lgnDatePeremDetail = lgnDatePeremDetail;
	}

	public String getLgnRecptLotDetail() {
		return lgnRecptLotDetail;
	}

	public void setLgnRecptLotDetail(String lgnRecptLotDetail) {
		this.lgnRecptLotDetail = lgnRecptLotDetail;
	}

	public String getLgnRecptObservDetail() {
		return lgnRecptObservDetail;
	}

	public void setLgnRecptObservDetail(String lgnRecptObservDetail) {
		this.lgnRecptObservDetail = lgnRecptObservDetail;
	}

	public Integer getLgnRecptPrixAchatDetail() {
		return lgnRecptPrixAchatDetail;
	}

	public void setLgnRecptPrixAchatDetail(Integer lgnRecptPrixAchatDetail) {
		this.lgnRecptPrixAchatDetail = lgnRecptPrixAchatDetail;
	}

	public PharmProduit getProduitDetail() {
		return produitDetail;
	}

	public void setProduitDetail(PharmProduit produitDetail) {
		this.produitDetail = produitDetail;
	}

}
