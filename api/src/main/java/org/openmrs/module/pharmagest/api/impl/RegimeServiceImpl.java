package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.api.RegimeService;
import org.openmrs.module.pharmagest.api.db.RegimeDAO;

public class RegimeServiceImpl extends BaseOpenmrsService implements RegimeService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private RegimeDAO dao;

	

	public RegimeDAO getDao() {
		return dao;
	}

	public void setDao(RegimeDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmRegime(PharmRegime pharmRegime) {
		getDao().savePharmRegime(pharmRegime);
	}

	@Override
	public void deletePharmRegime(PharmRegime pharmRegime) {
		getDao().retirePharmRegime(pharmRegime);

	}

	@Override
	public PharmRegime getPharmRegimeById(Integer pharmRegimeId) {
		return getDao().getPharmRegimeById(pharmRegimeId);
	}

	@Override
	public Collection<PharmRegime> getAllPharmRegimes() {
		
		return getDao().getAllPharmRegimes();
	}

	@Override
	public void updatePharmRegime(PharmRegime pharmRegime) {
		getDao().updatePharmRegime(pharmRegime);

	}

}
