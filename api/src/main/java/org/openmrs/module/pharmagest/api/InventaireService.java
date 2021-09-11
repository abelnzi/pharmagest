package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Date;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.Inventaire;
import org.openmrs.module.pharmagest.LigneInventaire;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaireId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface InventaireService extends OpenmrsService {
	public void saveInventaire(Inventaire inventaire);

	public void deleteInventaire(Inventaire inventaire);

	public Inventaire getInventaireById(Integer inventaireId);

	public Collection<Inventaire> getAllInventaires();

	public void updateInventaire(Inventaire inventaire);

	public void saveLigneInventaire(LigneInventaire ligneInventaire);

	public void saveLigneInventaires(Collection<LigneInventaire> ligneInventaires);

	public void savePharmInventaire(PharmInventaire pharmInventaire);

	public void deletePharmInventaire(PharmInventaire pharmInventaire);

	public PharmInventaire getPharmInventaireById(Integer pharmInventaireId);

	public Collection<PharmInventaire> getAllPharmInventaires();

	public void updatePharmInventaire(PharmInventaire pharmInventaire);

	public void savePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire);

	public void savePharmLigneInventaires(Collection<PharmLigneInventaire> pharmLigneInventaires);

	Collection<PharmInventaire> getPharmInventaireByProgram(PharmProgramme programme);

	public Collection<PharmInventaire> getPharmInventaireByPeriod(PharmProgramme programme, Date date, Boolean valid);

	public Collection<PharmInventaire> getPharmInventaireByPeriod(PharmProgramme programme, Date date);

	public PharmInventaire getPharmInventaireForAuthorize(PharmProgramme programme);

	PharmLigneInventaire getPharmLigneInventaire(PharmLigneInventaireId pharmLigneInventaireId);

	void updatePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire);

	public void deletePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire);

	public void saveOrUpdatePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire);

	public Collection<PharmInventaire> getPharmInventairesByPP(PharmProgramme programme, Date dateDebut, Date dateFin,
			String mode);
	
	public void deletePharmLigneInventairesByInventaire(PharmInventaire inventaire);

}
