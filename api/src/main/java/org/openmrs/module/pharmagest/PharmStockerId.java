package org.openmrs.module.pharmagest;


/**
 * PharmStockerId generated by hbm2java
 */
public class PharmStockerId implements java.io.Serializable {

	private int prodAttriId;
	private int programId;

	public PharmStockerId() {
	}

	public PharmStockerId(int prodAttriId, int programId) {
		this.prodAttriId = prodAttriId;
		this.programId = programId;
	}

	public int getProdAttriId() {
		return this.prodAttriId;
	}

	public void setProdAttriId(int prodAttriId) {
		this.prodAttriId = prodAttriId;
	}

	public int getProgramId() {
		return this.programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PharmStockerId))
			return false;
		PharmStockerId castOther = (PharmStockerId) other;

		return (this.getProdAttriId() == castOther.getProdAttriId())
				&& (this.getProgramId() == castOther.getProgramId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProdAttriId();
		result = 37 * result + this.getProgramId();
		return result;
	}

}
