package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.api.db.ProgrammeDAO;

public class ProgrammeServiceImpl extends BaseOpenmrsService implements ProgrammeService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private ProgrammeDAO dao;

	

	public ProgrammeDAO getDao() {
		return dao;
	}

	public void setDao(ProgrammeDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmProgramme(PharmProgramme pharmProgramme) {
		getDao().savePharmProgramme(pharmProgramme);
	}

	@Override
	public void deletePharmProgramme(PharmProgramme pharmProgramme) {
		getDao().retirePharmProgramme(pharmProgramme);

	}

	@Override
	public PharmProgramme getPharmProgrammeById(Integer pharmProgrammeId) {
		return getDao().getPharmProgrammeById(pharmProgrammeId);
	}

	@Override
	public Collection<PharmProgramme> getAllPharmProgrammes() {
		
		return getDao().getAllPharmProgrammes();
	}

	@Override
	public void updatePharmProgramme(PharmProgramme pharmProgramme) {
		getDao().updatePharmProgramme(pharmProgramme);

	}

}
