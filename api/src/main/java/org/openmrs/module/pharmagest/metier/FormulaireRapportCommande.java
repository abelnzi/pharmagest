package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;

public class FormulaireRapportCommande {
	private PharmProgramme pharmProgramme;
	private PharmRapportCommande rapportCommande;
	
	private Map<PharmProduit, RapportCommandeBean> listRapCommande;
	
	private Date dateCommande;

	public FormulaireRapportCommande() {

		this.pharmProgramme = new PharmProgramme();
		this.listRapCommande=Collections.synchronizedMap(new HashMap());
		this.rapportCommande= new PharmRapportCommande();
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

	public PharmRapportCommande getRapportCommande() {
		return rapportCommande;
	}

	public void setRapportCommande(PharmRapportCommande rapportCommande) {
		this.rapportCommande = rapportCommande;
	}

	public Map<PharmProduit, RapportCommandeBean> getListRapCommande() {
		return listRapCommande;
	}

	public void setListRapCommande(Map<PharmProduit, RapportCommandeBean> listRapCommande) {
		this.listRapCommande = listRapCommande;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	

}
