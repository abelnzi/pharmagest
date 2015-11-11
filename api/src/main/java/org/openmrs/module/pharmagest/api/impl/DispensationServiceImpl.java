package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.openmrs.PatientIdentifier;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.db.OrdonnanceDAO;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DispensationServiceImpl extends BaseOpenmrsService implements DispensationService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private OrdonnanceDAO dao;

	/**
	 * @return the dao
	 */
	public OrdonnanceDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
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
	public void saveLigneDispensations(Collection<LigneDispensation> lignedispensations) {
		// TODO Auto-generated method stub

	}

	@Override
	public Ordonnance getLastDispensation(PatientComplement patientComplement) {

		return getDao().getLastDispensation(patientComplement);
	}

	@Override
	public void savePharmOrdonnance(PharmOrdonnance pharmOrdonnance) {

		getDao().savePharmOrdonnance(pharmOrdonnance);
	}

	@Override
	public void deletePharmOrdonnance(PharmOrdonnance pharmOrdonnance) {
		getDao().retirePharmOrdonnance(pharmOrdonnance);

	}

	@Override
	public PharmOrdonnance getPharmOrdonnanceById(Integer ordonnanceId) {

		return getDao().getPharmOrdonnanceById(ordonnanceId);
	}

	@Override
	public PharmOrdonnance getPharmOrdonnanceByIdentifier(String patientIdentifiant) {

		return getDao().getPharmOrdonnanceByIdentifier(patientIdentifiant);
	}

	@Override
	public Collection<PharmOrdonnance> getAllPharmOrdonnances() {

		return getDao().getAllPharmOrdonnances();
	}

	@Override
	public void updatePharmOrdonnance(PharmOrdonnance pharmOrdonnance) {
		getDao().updatePharmOrdonnance(pharmOrdonnance);

	}

	@Override
	public void savePharmLigneDispensation(PharmLigneDispensation pharmLignedispensation) {
		getDao().savePharmLigneDispensation(pharmLignedispensation);

	}

	@Override
	public void savePharmLigneDispensations(Collection<PharmLigneDispensation> pharmLignedispensations) {
		getDao().savePharmLigneDispensations(pharmLignedispensations);

	}

	@Override
	public PharmOrdonnance getLastPharmOrdonnance(PatientIdentifier patientIdentifier) {

		return getDao().getLastPharmOrdonnance(patientIdentifier);
	}

	@Override
	public PharmLigneDispensation getPharmLigneDispensation(PharmLigneDispensationId pharmLigneDispensationId) {

		return getDao().getPharmLigneDispensation(pharmLigneDispensationId);
	}

	@Override
	public void updatePharmLigneDispensation(PharmLigneDispensation pharmLigneDispensation) {
		getDao().updatePharmLigneDispensation(pharmLigneDispensation);
		
	}

}
