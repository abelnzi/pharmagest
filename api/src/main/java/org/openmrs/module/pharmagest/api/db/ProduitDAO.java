package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmProduit;

public interface ProduitDAO {

	public void savePharmProduit(PharmProduit pharmProduit);

	public void retirePharmProduit(PharmProduit pharmProduit);

	public PharmProduit getPharmProduitById(Integer pharmProduitId);

	public Collection<PharmProduit> getAllPharmProduits();

	public void updatePharmProduit(PharmProduit pharmProduit);

}
