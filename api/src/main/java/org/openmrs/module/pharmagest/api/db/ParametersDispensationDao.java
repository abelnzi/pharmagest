package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.TypeOperation;

public interface ParametersDispensationDao {

	public Collection<Regime> getAllRegimes();

	public Collection<Medecin> getAllMedecins();

	public Collection<Fournisseur> getAllFournisseurs();

	public Collection<Programme> getAllProgrammes();

	public Collection<Produit> getAllProduits();

	public Collection<TypeOperation> getAllTypeOperation();

	public Produit getProduitById(Integer produitId);

	public Regime getRegimeById(Integer regimeId);

	public Medecin getMedecinById(Integer medecinId);

	public Programme getProgrammeById(Integer programmeId);

	public Fournisseur getFournisseurById(Integer fournisseurId);

	public TypeOperation getTypeOperationById(Integer typeOperationId);

}
