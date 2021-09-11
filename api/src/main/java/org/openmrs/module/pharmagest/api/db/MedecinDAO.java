package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmMedecin;

public interface MedecinDAO {

	public void savePharmMedecin(PharmMedecin pharmMedecin);

	public void retirePharmMedecin(PharmMedecin pharmMedecin);

	public PharmMedecin getPharmMedecinById(Integer pharmMedecinId);

	public Collection<PharmMedecin> getAllPharmMedecins();

	public void updatePharmMedecin(PharmMedecin pharmMedecin);

}
