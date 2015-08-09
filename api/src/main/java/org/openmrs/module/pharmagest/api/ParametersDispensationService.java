package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Map;

import org.openmrs.PatientIdentifier;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.LigneDispensationId;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.RegimeTest;
import org.openmrs.module.pharmagest.TypeOperation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ParametersDispensationService extends OpenmrsService {
	public Collection<Regime> getAllRegimes();

	public Collection<Medecin> getAllMedecins();

	public Collection<Produit> getAllProduits();

	public Collection<Fournisseur> getAllFournisseurs();

	public Collection<Programme> getAllProgrammes();

	public Produit getProduitById(Integer produitId);

	public Regime getRegimeById(Integer regimeId);

	public Medecin getMedecinById(Integer medecinId);

	public Programme getProgrammeById(Integer programmeId);

	public Fournisseur getFournisseurById(Integer fournisseurId);

	public Collection<TypeOperation> getAllTypeOperation();

	public TypeOperation getTypeOperationById(Integer typeOperationId);

	public PatientIdentifier getPatientIdentifierByIdentifier(String identifier);

	public PatientComplement getPatientComplementByIdentifier(Integer patientIdentifierId);
}
