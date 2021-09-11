package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Date;

import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProgramme;

public class FormulaireProgramme {
	private PharmProgramme pharmProgramme;
	private Collection<RapportConsoBean> listConso;
	private Date dateConso ;
	private PharmCommandeSite commandeSite;

	public FormulaireProgramme() {

		this.pharmProgramme = new PharmProgramme();
	}

	public Integer getProgramId() {
		return pharmProgramme.getProgramId();
	}

	public void setProgramId(Integer programId) {
		pharmProgramme.setProgramId(programId);
	}

	public String getProgramLib() {
		return pharmProgramme.getProgramLib();
	}

	public void setProgramLib(String programLib) {
		pharmProgramme.setProgramLib(programLib);
	}

	public PharmProgramme getPharmProgramme() {
		return pharmProgramme;
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		this.pharmProgramme = pharmProgramme;
	}

	public Collection<RapportConsoBean> getListConso() {
		return listConso;
	}

	public void setListConso(Collection<RapportConsoBean> listConso) {
		this.listConso = listConso;
	}

	public Date getDateConso() {
		return dateConso;
	}

	public void setDateConso(Date dateConso) {
		this.dateConso = dateConso;
	}

	public PharmCommandeSite getCommandeSite() {
		return commandeSite;
	}

	public void setCommandeSite(PharmCommandeSite commandeSite) {
		this.commandeSite = commandeSite;
	}

}
