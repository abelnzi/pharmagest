package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStockerId;

public class StockBean {

	private PharmProgramme pharmProgramme;
	private PharmProduit pharmProduit;
	private Integer stockQte;
	private Integer stockQteMin;
	private Integer stockQteMax;
	private Date stockDateStock;
	private Integer stockQteIni;
	
	public PharmProgramme getPharmProgramme() {
		return pharmProgramme;
	}
	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		this.pharmProgramme = pharmProgramme;
	}
	public PharmProduit getPharmProduit() {
		return pharmProduit;
	}
	public void setPharmProduit(PharmProduit pharmProduit) {
		this.pharmProduit = pharmProduit;
	}
	public Integer getStockQte() {
		return stockQte;
	}
	public void setStockQte(Integer stockQte) {
		this.stockQte = stockQte;
	}
	public Integer getStockQteMin() {
		return stockQteMin;
	}
	public void setStockQteMin(Integer stockQteMin) {
		this.stockQteMin = stockQteMin;
	}
	public Integer getStockQteMax() {
		return stockQteMax;
	}
	public void setStockQteMax(Integer stockQteMax) {
		this.stockQteMax = stockQteMax;
	}
	public Date getStockDateStock() {
		return stockDateStock;
	}
	public void setStockDateStock(Date stockDateStock) {
		this.stockDateStock = stockDateStock;
	}
	
	public Integer getStockQteIni() {
		return stockQteIni;
	}
	public void setStockQteIni(Integer stockQteIni) {
		this.stockQteIni = stockQteIni;
	}

}
