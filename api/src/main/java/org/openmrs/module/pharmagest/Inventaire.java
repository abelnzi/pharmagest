package org.openmrs.module.pharmagest;

// Generated 2 juil. 2015 23:11:24 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Inventaire generated by hbm2java
 */
public class Inventaire implements java.io.Serializable {

	private int invId;
	private Date invDateCrea;
	private Date invDeb;
	private Date invFin;
	private Set<LigneInventaire> ligneInventaires = new HashSet<LigneInventaire>(
			0);

	public Inventaire() {
	}

	public Inventaire(int invId) {
		this.invId = invId;
	}

	public Inventaire(int invId, Date invDateCrea, Date invDeb, Date invFin,
			Set<LigneInventaire> ligneInventaires) {
		this.invId = invId;
		this.invDateCrea = invDateCrea;
		this.invDeb = invDeb;
		this.invFin = invFin;
		this.ligneInventaires = ligneInventaires;
	}

	public int getInvId() {
		return this.invId;
	}

	public void setInvId(int invId) {
		this.invId = invId;
	}

	public Date getInvDateCrea() {
		return this.invDateCrea;
	}

	public void setInvDateCrea(Date invDateCrea) {
		this.invDateCrea = invDateCrea;
	}

	public Date getInvDeb() {
		return this.invDeb;
	}

	public void setInvDeb(Date invDeb) {
		this.invDeb = invDeb;
	}

	public Date getInvFin() {
		return this.invFin;
	}

	public void setInvFin(Date invFin) {
		this.invFin = invFin;
	}

	public Set<LigneInventaire> getLigneInventaires() {
		return this.ligneInventaires;
	}

	public void setLigneInventaires(Set<LigneInventaire> ligneInventaires) {
		this.ligneInventaires = ligneInventaires;
	}

}
