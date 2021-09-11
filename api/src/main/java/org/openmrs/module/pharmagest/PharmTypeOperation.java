package org.openmrs.module.pharmagest;

// Generated 16 ao�t 2015 03:07:54 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * PharmTypeOperation generated by hbm2java
 */
public class PharmTypeOperation implements java.io.Serializable {

	private Integer toperId;
	private String toperLib;
	private Boolean toperFlagSens;
	private Set<PharmOperation> pharmOperations = new HashSet<PharmOperation>(0);

	public PharmTypeOperation() {
	}

	public PharmTypeOperation(Integer toperId,String toperLib, Boolean toperFlagSens,
			Set<PharmOperation> pharmOperations) {
		this.toperId=toperId;
		this.toperLib = toperLib;
		this.toperFlagSens = toperFlagSens;
		this.pharmOperations = pharmOperations;
	}

	public Integer getToperId() {
		return this.toperId;
	}

	public void setToperId(Integer toperId) {
		this.toperId = toperId;
	}

	public String getToperLib() {
		return this.toperLib;
	}

	public void setToperLib(String toperLib) {
		this.toperLib = toperLib;
	}

	public Boolean getToperFlagSens() {
		return this.toperFlagSens;
	}

	public void setToperFlagSens(Boolean toperFlagSens) {
		this.toperFlagSens = toperFlagSens;
	}

	public Set<PharmOperation> getPharmOperations() {
		return this.pharmOperations;
	}

	public void setPharmOperations(Set<PharmOperation> pharmOperations) {
		this.pharmOperations = pharmOperations;
	}

}