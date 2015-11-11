package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.PatientIdentifier;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmOrdonnance;
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

	public PharmOrdonnance getPharmOrdonnanceByIdentifier(String patientIdentifiant);

	public Collection<PharmOrdonnance> getAllPharmOrdonnances();

	public void updatePharmOrdonnance(PharmOrdonnance pharmOrdonnance);

	public void savePharmLigneDispensation(PharmLigneDispensation pharmLignedispensation);

	public void savePharmLigneDispensations(Collection<PharmLigneDispensation> pharmLignedispensations);

	public PharmOrdonnance getLastPharmOrdonnance(PatientIdentifier patientIdentifier);

	public PharmLigneDispensation getPharmLigneDispensation(PharmLigneDispensationId pharmLigneDispensationId);

	public void updatePharmLigneDispensation(PharmLigneDispensation pharmLigneDispensation);

}
