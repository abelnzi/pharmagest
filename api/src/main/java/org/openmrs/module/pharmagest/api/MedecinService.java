package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MedecinService extends OpenmrsService {
	public void savePharmMedecin(PharmMedecin pharmMedecin);

	public void deletePharmMedecin(PharmMedecin pharmMedecin);

	public PharmMedecin getPharmMedecinById(Integer pharmMedecinId);

	public Collection<PharmMedecin> getAllPharmMedecins();

	public void updatePharmMedecin(PharmMedecin pharmMedecin);
}
