package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmOrdonnance;

public interface OrdonnanceDAO {

	public void saveOrdonnance(Ordonnance ordonnance);

	public void retireOrdonnance(Ordonnance ordonnance);

	public Ordonnance getOrdonnanceById(Integer ordonnanceId);

	public Ordonnance getOrdonnanceByIdentifier(String patientIdentifiant);

	public Collection<Ordonnance> getAllOrdonnances();

	public void updateOrdonnance(Ordonnance ordonnance);

	public void saveLigneDispensation(LigneDispensation lignedispensation);

	public void saveLigneDispensations(Collection<LigneDispensation> lignedispensations);

	public Ordonnance getLastDispensation(PatientComplement patientComplement);

	public void savePharmOrdonnance(PharmOrdonnance pharmOrdonnance);

	public void retirePharmOrdonnance(PharmOrdonnance pharmOrdonnance);

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
