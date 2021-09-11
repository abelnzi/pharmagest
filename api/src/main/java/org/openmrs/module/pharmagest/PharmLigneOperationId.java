package org.openmrs.module.pharmagest;

// Generated 16 ao�t 2015 03:07:54 by Hibernate Tools 3.4.0.CR1

/**
 * PharmLigneOperationId generated by hbm2java
 */
public class PharmLigneOperationId implements java.io.Serializable {

	private int prodAttriId;
	private int operId;

	public PharmLigneOperationId() {
	}

	public PharmLigneOperationId(int prodAttriId, int operId) {
		this.prodAttriId = prodAttriId;
		this.operId = operId;
	}

	public int getProdAttriId() {
		return this.prodAttriId;
	}

	public void setProdAttriId(int prodAttriId) {
		this.prodAttriId = prodAttriId;
	}

	public int getOperId() {
		return this.operId;
	}

	public void setOperId(int operId) {
		this.operId = operId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PharmLigneOperationId))
			return false;
		PharmLigneOperationId castOther = (PharmLigneOperationId) other;

		return (this.getProdAttriId() == castOther.getProdAttriId())
				&& (this.getOperId() == castOther.getOperId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProdAttriId();
		result = 37 * result + this.getOperId();
		return result;
	}

}