package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmRegime;

public interface RegimeDAO {

	public void savePharmRegime(PharmRegime pharmRegime);

	public void retirePharmRegime(PharmRegime pharmRegime);

	public PharmRegime getPharmRegimeById(Integer pharmRegimeId);

	public Collection<PharmRegime> getAllPharmRegimes();

	public void updatePharmRegime(PharmRegime pharmRegime);

}
