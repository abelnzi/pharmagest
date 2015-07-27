package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.db.OrdonnanceDAO;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DispensationServiceImpl extends BaseOpenmrsService implements DispensationService{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private OrdonnanceDAO dao;

	/**
	 * @return the dao
	 */
	public OrdonnanceDAO getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(OrdonnanceDAO dao) {
		this.dao = dao;
	}

	@Override
	public void saveOrdonnance(Ordonnance ordonnance) {
		// TODO Auto-generated method stub
		getDao().saveOrdonnance(ordonnance);
	}

	@Override
	public void deleteOrdonnance(Ordonnance ordonnance) {
		// TODO Auto-generated method stub
		getDao().retireOrdonnance(ordonnance);
	}

	@Override
	public Ordonnance getOrdonnanceById(Integer ordonnanceId) {
		// TODO Auto-generated method stub
		return getDao().getOrdonnanceById(ordonnanceId);
	}

	@Override
	public Ordonnance getOrdonnanceByIdentifier(String patientIdentifiant) {
		// TODO Auto-generated method stub
		return getDao().getOrdonnanceByIdentifier(patientIdentifiant);
	}

	@Override
	public Collection<Ordonnance> getAllOrdonnances() {
		// TODO Auto-generated method stub
		return getDao().getAllOrdonnances();
	}

	@Override
	public void updateOrdonnance(Ordonnance ordonnance) {
		// TODO Auto-generated method stub
		getDao().updateOrdonnance(ordonnance);
	}

	@Override
	public void saveLigneDispensation(LigneDispensation lignedispensation) {
		// TODO Auto-generated method stub
		getDao().saveLigneDispensation(lignedispensation);
	}

	@Override
	public void saveLigneDispensations(
			Collection<LigneDispensation> lignedispensations) {
		// TODO Auto-generated method stub
		
	}

}
