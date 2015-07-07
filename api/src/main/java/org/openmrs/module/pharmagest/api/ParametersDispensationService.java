package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.RegimeTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ParametersDispensationService extends OpenmrsService {
	public Collection<Regime> getAllRegimes();

	public Collection<Medecin> getAllMedecins();

	public Collection<Produit> getAllProduits();
}
