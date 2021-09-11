package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Date;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmLigneRcId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RapportCommandeService extends OpenmrsService {

	public void savePharmRapportCommande(PharmRapportCommande pharmRapportCommande);

	public void deletePharmRapportCommande(PharmRapportCommande pharmRapportCommande);

	public PharmRapportCommande getPharmRapportCommandeById(Integer pharmRapportCommandeId);

	public Collection<PharmRapportCommande> getPharmRapportCommandeByProgram(PharmProgramme programme);

	public Collection<PharmRapportCommande> getPharmRapportCommandeByPeriod(PharmProgramme programme, Date date);

	public Collection<PharmRapportCommande> getAllPharmRapportCommandes();

	public void updatePharmRapportCommande(PharmRapportCommande pharmRapportCommande);

	public void savePharmLigneRc(PharmLigneRc pharmLigneRc);

	public void savePharmLigneRcs(Collection<PharmLigneRc> pharmLigneRcs);

	public PharmLigneRc getPharmLigneRc(PharmLigneRcId pharmLigneRcId);

	public void updatePharmLigneRc(PharmLigneRc pharmLigneRc);
	
	public PharmRapportCommande getPharmRapportCommande(PharmProgramme programme);

}
