package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.RegimeTest;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.db.ParametersDispensationDao;

public class ParametersDispensationServiceImpl extends BaseOpenmrsService implements
		ParametersDispensationService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private ParametersDispensationDao dao;

	@Override
	public Collection<Regime> getAllRegimes() {
		// TODO Auto-generated method stub
		return getDao().getAllRegimes();
	}

	@Override
	public Collection<Medecin> getAllMedecins() {
		// TODO Auto-generated method stub
		return getDao().getAllMedecins();
	}

	@Override
	public Collection<Produit> getAllProduits() {
		// TODO Auto-generated method stub
		return getDao().getAllProduits();
	}

	/**
	 * @return the dao
	 */
	public ParametersDispensationDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(ParametersDispensationDao dao) {
		this.dao = dao;
	}

}
