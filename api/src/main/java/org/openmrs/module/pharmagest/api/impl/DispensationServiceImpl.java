package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;
import java.util.Date;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmAssurance;
import org.openmrs.module.pharmagest.PharmClient;
import org.openmrs.module.pharmagest.PharmLigneAssurance;
import org.openmrs.module.pharmagest.PharmLigneAssuranceId;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
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
	public PharmOrdonnance getPharmOrdonnanceByIdentifier(PatientIdentifier patientIdentifiant) {

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
	
	public Encounter getLastEncounter(Integer patientId, Date dateDispens){
		return getDao().getLastEncounter(patientId, dateDispens);
	}

	@Override
	public Obs getObs(int encounterId, int conceptId, int personId) {
		
		return getDao().getObs(encounterId, conceptId, personId);
	}
	
	public void deleteObsByAtt(int personId, int conceptId, Date obsDate ){		
		getDao().deleteObsByAtt(personId, conceptId, obsDate);
	}
	
	public Date getEncounterByIdentifier(int type,String identifier){
		return getDao().getEncounterByIdentifier(type, identifier);
	}

	@Override
	public PharmOrdonnance findOrdonnanceByAttribut(PatientIdentifier patientIdentifiant, PharmRegime pharmRegime,
			Date ordDateDispen, PharmProgramme pharmProgramme, PharmMedecin pharmMedecin) {
		return getDao().findOrdonnanceByAttribut(patientIdentifiant, pharmRegime, ordDateDispen, pharmProgramme, pharmMedecin);
	}

	@Override
	public Collection<PharmOrdonnance> getPharmOrdonnanceByPeriod(Date dateDebut, Date dateFin,String mode) {
		return getDao().getPharmOrdonnanceByPeriod(dateDebut, dateFin,mode);
	}

	@Override
	public Collection<PharmOrdonnance> getPharmOrdonnancesLibresByPeriod(Date dateDebut, Date dateFin) {
		// TODO Auto-generated method stub
		return getDao().getPharmOrdonnancesLibresByPeriod(dateDebut, dateFin);
	}

	@Override
	public void savePharmClient(PharmClient pharmClient) {
		getDao().savePharmClient(pharmClient);
		
	}

	@Override
	public void deletePharmClient(PharmClient pharmClient) {
		getDao().retirePharmClient(pharmClient);
		
	}

	@Override
	public PharmClient getPharmClientById(Integer pharmClientId) {
		
		return getDao().getPharmClientById(pharmClientId);
	}

	@Override
	public Collection<PharmClient> getAllPharmClients() {
		// TODO Auto-generated method stub
		return getDao().getAllPharmClients();
	}

	@Override
	public void updatePharmClient(PharmClient pharmClient) {
		getDao().updatePharmClient(pharmClient);
		
	}

	@Override
	public void savePharmAssurance(PharmAssurance pharmAssurance) {
		getDao().savePharmAssurance(pharmAssurance);
		
	}

	@Override
	public void deletePharmAssurance(PharmAssurance pharmAssurance) {
		getDao().retirePharmAssurance(pharmAssurance);
		
	}

	@Override
	public PharmAssurance getPharmAssuranceById(Integer pharmAssuranceId) {
		// TODO Auto-generated method stub
		return getDao().getPharmAssuranceById(pharmAssuranceId);
	}

	@Override
	public Collection<PharmAssurance> getAllPharmAssurances() {
		// TODO Auto-generated method stub
		return getDao().getAllPharmAssurances();
	}

	@Override
	public void updatePharmAssurance(PharmAssurance pharmAssurance) {
		getDao().updatePharmAssurance(pharmAssurance);
		
	}

	@Override
	public void savePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance) {
		getDao().savePharmLigneAssurance(pharmLigneAssurance);
		
	}

	@Override
	public void deletePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance) {
		getDao().updatePharmLigneAssurance(pharmLigneAssurance);
		
	}

	@Override
	public void savePharmLigneAssurances(Collection<PharmLigneAssurance> pharmLigneAssurances) {
		getDao().savePharmLigneAssurances(pharmLigneAssurances);
		
	}

	@Override
	public PharmLigneAssurance getPharmLigneAssurance(PharmLigneAssuranceId pharmLigneAssuranceId) {
		// TODO Auto-generated method stub
		return getDao().getPharmLigneAssurance(pharmLigneAssuranceId);
	}

	@Override
	public void updatePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance) {
		getDao().updatePharmLigneAssurance(pharmLigneAssurance);		
	}

	@Override
	public Collection<PharmLigneAssurance> getAllPharmLigneAssurances() {
		// TODO Auto-generated method stub
		return getDao().getAllPharmLigneAssurances();
	}

	@Override
	public PharmLigneAssurance getPharmLigneAssuranceByAttri(PharmAssurance assurance, String numAssure) {
		
		return getDao().getPharmLigneAssuranceByAttri(assurance, numAssure);
	}

	@Override
	public Collection<PharmOrdonnance> getPharmOrdonnanceByPeriod(Date dateDebut, Date dateFin,
			PharmProgramme programme, String mode) {
		return getDao().getPharmOrdonnanceByPeriod(dateDebut, dateFin, programme, mode);
	} 

}
