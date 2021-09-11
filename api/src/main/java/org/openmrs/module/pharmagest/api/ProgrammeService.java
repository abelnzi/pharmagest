package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProgrammeService extends OpenmrsService {
	public void savePharmProgramme(PharmProgramme pharmProgramme);

	public void deletePharmProgramme(PharmProgramme pharmProgramme);

	public PharmProgramme getPharmProgrammeById(Integer pharmProgrammeId);
	
	public PharmProgramme getPharmProgrammeByLib(String programLib);

	public Collection<PharmProgramme> getAllPharmProgrammes();

	public void updatePharmProgramme(PharmProgramme pharmProgramme);
}
