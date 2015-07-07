package org.openmrs.module.pharmagest;



import java.util.HashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or
 * {@link BaseOpenmrsMetadata}.
 */
public class RegimeTest  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int regimId;
	private String regimLib;
	

	public RegimeTest() {
	}

	public RegimeTest(int regimId) {
		this.regimId = regimId;
		
	}

	public RegimeTest(int regimId, String regimLib) {
		this.regimId = regimId;
		
		this.regimLib = regimLib;

	}

	public int getRegimId() {
		return this.regimId;
	}

	public void setRegimId(int regimId) {
		this.regimId = regimId;
	}

	

	public String getRegimLib() {
		return this.regimLib;
	}

	public void setRegimLib(String regimLib) {
		this.regimLib = regimLib;
	}

	


}
