package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmRegime;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RegimeService extends OpenmrsService {
	public void savePharmRegime(PharmRegime pharmRegime);

	public void deletePharmRegime(PharmRegime pharmRegime);

	public PharmRegime getPharmRegimeById(Integer pharmRegimeId);

	public Collection<PharmRegime> getAllPharmRegimes();

	public void updatePharmRegime(PharmRegime pharmRegime);
	
	public PharmRegime findRegimeByName(String regime);
}
