package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmProgramme;

public interface ProgrammeDAO {

	public void savePharmProgramme(PharmProgramme pharmProgramme);

	public void retirePharmProgramme(PharmProgramme pharmProgramme);

	public PharmProgramme getPharmProgrammeById(Integer pharmProgrammeId);

	public Collection<PharmProgramme> getAllPharmProgrammes();

	public void updatePharmProgramme(PharmProgramme pharmProgramme);
	
	public PharmProgramme getPharmProgrammeByLib(String programLib);

}
