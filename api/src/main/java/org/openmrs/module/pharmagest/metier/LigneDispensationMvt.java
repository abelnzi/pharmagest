package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmProduit;

public class LigneDispensationMvt {

	private PharmProduit pharmProduit;
	private Date lgnDatePerem;
	private String lgnRecptLot;
	private Integer ldQteDem;
	private Integer ldQteServi;
	private Integer ldPrixUnit;

	public LigneDispensationMvt() {
		super();
	}

	public LigneDispensationMvt(PharmProduit produit, Date lgnDatePerem, String lgnRecptLot, Integer ldQteDem,
			Integer ldQteServi, Integer ldPrixUnit) {
		super();
		this.pharmProduit = produit;
		this.lgnDatePerem = lgnDatePerem;
		this.lgnRecptLot = lgnRecptLot;
		this.ldQteDem = ldQteDem;
		this.ldQteServi = ldQteServi;
		this.ldPrixUnit = ldPrixUnit;
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

}
