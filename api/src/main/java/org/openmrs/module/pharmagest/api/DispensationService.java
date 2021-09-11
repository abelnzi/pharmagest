package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Date;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.OpenmrsService;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DispensationService extends OpenmrsService {
	/**
	 * Saves the saveOrdonnance to the DB for persistence
	 * 
	 * @param ordonnance
	 *            the ordonnance to save
	 */
	public void saveOrdonnance(Ordonnance ordonnance);

	/**
	 * Deletes (retires) a given Ordonnance
	 * 
	 * @param ordonnance
	 *            the object to delete
	 */
	public void deleteOrdonnance(Ordonnance ordonnance);

	/**
	 * Gets a Ordonnance from the DB by matching a given ID
	 * 
	 * @return Ordonnance that matches the conditions
	 */
	public Ordonnance getOrdonnanceById(Integer ordonnanceId);

	/**
	 * Gets a Ordonnance from the DB by matching a given Identifier
	 * 
	 * @return Ordonnance that matches the conditions
	 */
	public Ordonnance getOrdonnanceByIdentifier(String patientIdentifiant);

	/**
	 * Gets all existing or generated IDs (not retired)
	 * 
	 * @return collection of all GeneratedIds
	 */
	public Collection<Ordonnance> getAllOrdonnances();

	/**
	 * Updates or Edits a given Ordonnance
	 * 
	 * @param ordonnance
	 *            the object to edit
	 */
	public void updateOrdonnance(Ordonnance ordonnance);

	/**
	 * Returns the very latest Ordonnance
	 * 
	 * @return latest Ordonnance in DB
	 */

	public void saveLigneDispensation(LigneDispensation lignedispensation);

	public void saveLigneDispensations(Collection<LigneDispensation> lignedispensations);

	public Ordonnance getLastDispensation(PatientComplement patientComplement);

	public void savePharmOrdonnance(PharmOrdonnance pharmOrdonnance);

	public void deletePharmOrdonnance(PharmOrdonnance pharmOrdonnance);

	public PharmOrdonnance getPharmOrdonnanceById(Integer ordonnanceId);

	public PharmOrdonnance getPharmOrdonnanceByIdentifier(PatientIdentifier patientIdentifiant);

	public Collection<PharmOrdonnance> getAllPharmOrdonnances();

	public void updatePharmOrdonnance(PharmOrdonnance pharmOrdonnance);

	public void savePharmLigneDispensation(PharmLigneDispensation pharmLignedispensation);

	public void savePharmLigneDispensations(Collection<PharmLigneDispensation> pharmLignedispensations);

	public PharmOrdonnance findOrdonnanceByAttribut(PatientIdentifier patientIdentifiant, PharmRegime pharmRegime,
			Date ordDateDispen, PharmProgramme pharmProgramme, PharmMedecin pharmMedecin);

	public PharmOrdonnance getLastPharmOrdonnance(PatientIdentifier patientIdentifier);

	public Collection<PharmOrdonnance> getPharmOrdonnanceByPeriod(Date dateDebut, Date dateFin,String mode);
	
	public Collection<PharmOrdonnance> getPharmOrdonnanceByPeriod(Date dateDebut, Date dateFin,PharmProgramme programme,String mode);
	
	public Collection<PharmOrdonnance> getPharmOrdonnancesLibresByPeriod(Date dateDebut, Date dateFin);

	public PharmLigneDispensation getPharmLigneDispensation(PharmLigneDispensationId pharmLigneDispensationId);

	public void updatePharmLigneDispensation(PharmLigneDispensation pharmLigneDispensation);

	public Encounter getLastEncounter(Integer patientId, Date dateDispens);

	public Obs getObs(int encounterId, int conceptId, int personId);
	
	public void deleteObsByAtt(int personId, int conceptId, Date obsDate );
	
	public Date getEncounterByIdentifier(int type,String identifier);
	
	public void savePharmClient (PharmClient pharmClient);
	public void deletePharmClient (PharmClient pharmClient);
	public PharmClient getPharmClientById(Integer pharmClientId);
	public Collection<PharmClient> getAllPharmClients();
	public void updatePharmClient(PharmClient pharmClient);
	
	public void savePharmAssurance (PharmAssurance pharmAssurance);
	public void deletePharmAssurance (PharmAssurance pharmAssurance);
	public PharmAssurance getPharmAssuranceById(Integer pharmAssuranceId);
	public Collection<PharmAssurance> getAllPharmAssurances();
	public void updatePharmAssurance(PharmAssurance pharmAssurance);
	
	
	public void savePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance);
	public void deletePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance);
	public void savePharmLigneAssurances(Collection<PharmLigneAssurance> pharmLigneAssurances);
	public Collection<PharmLigneAssurance> getAllPharmLigneAssurances();
	public PharmLigneAssurance getPharmLigneAssurance(PharmLigneAssuranceId pharmLigneAssuranceId);
	public PharmLigneAssurance getPharmLigneAssuranceByAttri(PharmAssurance assurance, String numAssure);
	public void updatePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance);
	
}
