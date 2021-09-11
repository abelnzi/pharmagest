package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;

public class RapportConsoBean {
	private int stockIni;
	private int qteRecu;
	private int qteUtil;
	private int pertes;
	private int stockDispo;
	private int nbrJrsRup;
	private int qteDistM1;
	private int qteDistM2;
	
	private PharmProduit produit ;

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

	public int getQteDistM1() {
		return qteDistM1;
	}

	public void setQteDistM1(int qteDistM1) {
		this.qteDistM1 = qteDistM1;
	}

	public int getQteDistM2() {
		return qteDistM2;
	}

	public void setQteDistM2(int qteDistM2) {
		this.qteDistM2 = qteDistM2;
	}

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

}
