package org.openmrs.module.pharmagest;
// Generated 18 janv. 2016 14:23:09 by Hibernate Tools 3.5.0.Final

import java.util.HashSet;
import java.util.Set;

/**
 * PharmSite generated by hbm2java
 */
public class PharmSite implements java.io.Serializable, java.lang.Comparable  {

	private Integer sitId;
	private String sitCode;
	private String sitLib;
	private String sitFlag;
	private Set<PharmCommandeSite> pharmCommandeSites = new HashSet<PharmCommandeSite>(0);

	public PharmSite() {
	}

	public PharmSite(String sitCode, String sitLib, String sitFlag,Set<PharmCommandeSite> pharmCommandeSites) {
		this.sitCode = sitCode;
		this.sitLib = sitLib;
		this.setSitFlag(sitFlag);
		this.pharmCommandeSites = pharmCommandeSites;
	}

	public Integer getSitId() {
		return this.sitId;
	}

	public void setSitId(Integer sitId) {
		this.sitId = sitId;
	}

	public String getSitCode() {
		return this.sitCode;
	}

	public void setSitCode(String sitCode) {
		this.sitCode = sitCode;
	}

	public String getSitLib() {
		return this.sitLib;
	}

	public void setSitLib(String sitLib) {
		this.sitLib = sitLib;
	}

	public Set<PharmCommandeSite> getPharmCommandeSites() {
		return this.pharmCommandeSites;
	}

	public void setPharmCommandeSites(Set<PharmCommandeSite> pharmCommandeSites) {
		this.pharmCommandeSites = pharmCommandeSites;
	}

	public String getSitFlag() {
		return sitFlag;
	}

	public void setSitFlag(String sitFlag) {
		this.sitFlag = sitFlag;
	}
	
	@Override
	public int compareTo(Object o) {
		String s1 = ((PharmSite) o).getSitLib().trim();
		String s2 = this.getSitLib().trim();
		
		if (s1 != null && s2 != null) {
			if (s1.compareTo(s2) >= 1)
				return 1;
			else if (s1.compareTo(s2) <= -1)
				return -1;
			else if (s1.compareTo(s2) == 0)
				return 0;
			return 0;
		} else {
			return 0;
		}
	}

}
