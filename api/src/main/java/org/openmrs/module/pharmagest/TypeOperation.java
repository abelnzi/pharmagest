package org.openmrs.module.pharmagest;

// Generated 2 juil. 2015 23:11:24 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TypeOperation generated by hbm2java
 */
public class TypeOperation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int trecptId;
	private String trecptLib;
	private Boolean toperFlagSens;
	private Set<Operation> operations = new HashSet<Operation>(0);

	public TypeOperation() {
	}

	public TypeOperation(int trecptId) {
		this.trecptId = trecptId;
	}

	public TypeOperation(int trecptId, String trecptLib, Boolean toperFlagSens,
			Set<Operation> operations) {
		this.trecptId = trecptId;
		this.trecptLib = trecptLib;
		this.toperFlagSens = toperFlagSens;
		this.operations = operations;
	}

	public int getTrecptId() {
		return this.trecptId;
	}

	public void setTrecptId(int trecptId) {
		this.trecptId = trecptId;
	}

	public String getTrecptLib() {
		return this.trecptLib;
	}

	public void setTrecptLib(String trecptLib) {
		this.trecptLib = trecptLib;
	}

	public Boolean getToperFlagSens() {
		return this.toperFlagSens;
	}

	public void setToperFlagSens(Boolean toperFlagSens) {
		this.toperFlagSens = toperFlagSens;
	}

	public Set<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

}
