package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.RegimeTest;

public interface ParametersDispensationDao {

	public Collection<Regime> getAllRegimes();

	public Collection<Medecin> getAllMedecins();

	public Collection<Produit> getAllProduits();

}
