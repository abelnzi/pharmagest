package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmProduit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProduitService extends OpenmrsService {
	public void savePharmProduit(PharmProduit pharmProduit);

	public void deletePharmProduit(PharmProduit pharmProduit);

	public PharmProduit getPharmProduitById(Integer pharmProduitId);

	public Collection<PharmProduit> getAllPharmProduits();

	public void updatePharmProduit(PharmProduit pharmProduit);
}
