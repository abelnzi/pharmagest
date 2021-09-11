package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.api.ClassePharmaService;
import org.openmrs.module.pharmagest.api.db.ClassePharmaDAO;

public class ClassePharmaServiceImpl extends BaseOpenmrsService implements ClassePharmaService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private ClassePharmaDAO dao;

	

	public ClassePharmaDAO getDao() {
		return dao;
	}

	public void setDao(ClassePharmaDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmClassePharma(PharmClassePharma pharmClassePharma) {
		getDao().savePharmClassePharma(pharmClassePharma);
	}

	@Override
	public void deletePharmClassePharma(PharmClassePharma pharmClassePharma) {
		getDao().retirePharmClassePharma(pharmClassePharma);

	}

	@Override
	public PharmClassePharma getPharmClassePharmaById(Integer pharmClassePharmaId) {
		return getDao().getPharmClassePharmaById(pharmClassePharmaId);
	}

	@Override
	public Collection<PharmClassePharma> getAllPharmClassePharmas() {
		
		return getDao().getAllPharmClassePharmas();
	}

	@Override
	public void updatePharmClassePharma(PharmClassePharma pharmClassePharma) {
		getDao().updatePharmClassePharma(pharmClassePharma);

	}

}
