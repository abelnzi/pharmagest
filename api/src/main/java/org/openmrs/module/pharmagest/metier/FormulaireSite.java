package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmSite;

public class FormulaireSite {
	private PharmSite pharmSite;

	public FormulaireSite() {
	
		this.setPharmSite(new PharmSite());
	}

	public Integer getSitId() {
		return pharmSite.getSitId();
	}

	public void setSitId(Integer sitId) {
		pharmSite.setSitId(sitId);
	}

	public String getSitCode() {
		return pharmSite.getSitCode();
	}

	public void setSitCode(String sitCode) {
		pharmSite.setSitCode(sitCode);
	}

	public String getSitLib() {
		return pharmSite.getSitLib();
	}

	public void setSitLib(String sitLib) {
		pharmSite.setSitLib(sitLib);
	}

	public PharmSite getPharmSite() {
		return pharmSite;
	}

	public void setPharmSite(PharmSite pharmSite) {
		this.pharmSite = pharmSite;
	}


}
