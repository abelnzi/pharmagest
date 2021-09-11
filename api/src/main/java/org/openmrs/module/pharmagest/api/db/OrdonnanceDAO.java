package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;
import java.util.Date;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
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
	public void retirePharmClient (PharmClient pharmClient);
	public PharmClient getPharmClientById(Integer pharmClientId);
	public Collection<PharmClient> getAllPharmClients();
	public void updatePharmClient(PharmClient pharmClient);
	
	public void savePharmAssurance (PharmAssurance pharmAssurance);
	public void retirePharmAssurance (PharmAssurance pharmAssurance);
	public PharmAssurance getPharmAssuranceById(Integer pharmAssuranceId);
	public Collection<PharmAssurance> getAllPharmAssurances();
	public void updatePharmAssurance(PharmAssurance pharmAssurance);
	
	
	public void savePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance);
	public void retirePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance);
	public void savePharmLigneAssurances(Collection<PharmLigneAssurance> pharmLigneAssurances);
	public Collection<PharmLigneAssurance> getAllPharmLigneAssurances();
	public PharmLigneAssurance getPharmLigneAssurance(PharmLigneAssuranceId pharmLigneAssuranceId);
	public PharmLigneAssurance getPharmLigneAssuranceByAttri(PharmAssurance assurance, String numAssure);
	public void updatePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance);
	

}
