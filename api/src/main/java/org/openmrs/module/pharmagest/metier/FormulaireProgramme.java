package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmProgramme;

public class FormulaireProgramme {
	private PharmProgramme pharmProgramme;

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

}
