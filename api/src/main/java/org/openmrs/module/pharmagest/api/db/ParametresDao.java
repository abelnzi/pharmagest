package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmTypeOperation;

public interface ParametresDao {

	public Collection<PharmRegime> getAllRegimes();

	public Collection<PharmMedecin> getAllMedecins();

	public Collection<PharmFournisseur> getAllFournisseurs();

	public Collection<PharmProgramme> getAllProgrammes();

	public Collection<PharmProduit> getAllProduits();

	public Collection<PharmTypeOperation> getAllTypeOperation();

	public PharmProduit getProduitById(Integer produitId);

	public PharmRegime getRegimeById(Integer regimeId);

	public PharmMedecin getMedecinById(Integer medecinId);

	public PharmProgramme getProgrammeById(Integer programmeId);

	public PharmFournisseur getFournisseurById(Integer fournisseurId);

	public PharmTypeOperation getTypeOperationById(Integer typeOperationId);

	public PatientIdentifier getPatientIdentifierByIdentifier(String identifier);

	public PatientComplement getPatientComplementByIdentifier(Integer patientIdentifierId);
	
	public void saveOrUpdateGestionnaire(PharmGestionnairePharma pharmGestionnairePharma);

}
