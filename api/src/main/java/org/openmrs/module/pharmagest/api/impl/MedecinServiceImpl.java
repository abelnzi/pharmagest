package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.api.MedecinService;
import org.openmrs.module.pharmagest.api.db.MedecinDAO;

public class MedecinServiceImpl extends BaseOpenmrsService implements MedecinService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private MedecinDAO dao;

	

	public MedecinDAO getDao() {
		return dao;
	}

	public void setDao(MedecinDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmMedecin(PharmMedecin pharmMedecin) {
		getDao().savePharmMedecin(pharmMedecin);
	}

	@Override
	public void deletePharmMedecin(PharmMedecin pharmMedecin) {
		getDao().retirePharmMedecin(pharmMedecin);

	}

	@Override
	public PharmMedecin getPharmMedecinById(Integer pharmMedecinId) {
		return getDao().getPharmMedecinById(pharmMedecinId);
	}

	@Override
	public Collection<PharmMedecin> getAllPharmMedecins() {
		
		return getDao().getAllPharmMedecins();
	}

	@Override
	public void updatePharmMedecin(PharmMedecin pharmMedecin) {
		getDao().updatePharmMedecin(pharmMedecin);

	}

}
