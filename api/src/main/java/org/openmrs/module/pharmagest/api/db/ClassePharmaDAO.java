package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmClassePharma;

public interface ClassePharmaDAO {

	public void savePharmClassePharma(PharmClassePharma pharmClassePharma);

	public void retirePharmClassePharma(PharmClassePharma pharmClassePharma);

	public PharmClassePharma getPharmClassePharmaById(Integer pharmClassePharmaId);

	public Collection<PharmClassePharma> getAllPharmClassePharmas();

	public void updatePharmClassePharma(PharmClassePharma pharmClassePharma);

}
