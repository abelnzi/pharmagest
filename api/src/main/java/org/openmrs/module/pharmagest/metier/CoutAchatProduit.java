package org.openmrs.module.pharmagest.metier;

import java.util.Date;

public class CoutAchatProduit {
	Integer ProdAtId;
	Integer prixAchat;
	Date dateCout;

	public CoutAchatProduit() {

	}

	public Integer getProdAtId() {
		return ProdAtId;
	}

	public void setProdAtId(Integer prodAtId) {
		ProdAtId = prodAtId;
	}

	public Integer getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(Integer prixAchat) {
		this.prixAchat = prixAchat;
	}

	public Date getDateCout() {
		return dateCout;
	}

	public void setDateCout(Date dateCout) {
		this.dateCout = dateCout;
	}

}
