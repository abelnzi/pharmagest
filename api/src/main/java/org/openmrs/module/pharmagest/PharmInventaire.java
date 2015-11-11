package org.openmrs.module.pharmagest;

// Generated 16 ao�t 2015 03:07:54 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PharmInventaire generated by hbm2java
 */
public class PharmInventaire implements java.io.Serializable {

	private Integer invId;
	private Date invDateCrea;
	private Date invDeb;
	private Date invFin;
	private Set<PharmLigneInventaire> pharmLigneInventaires = new HashSet<PharmLigneInventaire>(
			0);

	public PharmInventaire() {
	}

	public PharmInventaire(Date invDateCrea, Date invDeb, Date invFin,
			Set<PharmLigneInventaire> pharmLigneInventaires) {
		this.invDateCrea = invDateCrea;
		this.invDeb = invDeb;
		this.invFin = invFin;
		this.pharmLigneInventaires = pharmLigneInventaires;
	}

	public Integer getInvId() {
		return this.invId;
	}

	public void setInvId(Integer invId) {
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

	public Set<PharmLigneInventaire> getPharmLigneInventaires() {
		return this.pharmLigneInventaires;
	}

	public void setPharmLigneInventaires(
			Set<PharmLigneInventaire> pharmLigneInventaires) {
		this.pharmLigneInventaires = pharmLigneInventaires;
	}

}
