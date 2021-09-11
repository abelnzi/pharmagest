package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmClassePharma;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ClassePharmaService extends OpenmrsService {
	public void savePharmClassePharma(PharmClassePharma pharmClassePharma);

	public void deletePharmClassePharma(PharmClassePharma pharmClassePharma);

	public PharmClassePharma getPharmClassePharmaById(Integer pharmClassePharmaId);

	public Collection<PharmClassePharma> getAllPharmClassePharmas();

	public void updatePharmClassePharma(PharmClassePharma pharmClassePharma);
}
