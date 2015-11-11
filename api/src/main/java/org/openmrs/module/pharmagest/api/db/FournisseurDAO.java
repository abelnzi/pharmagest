package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmFournisseur;

public interface FournisseurDAO {

	public void savePharmFournisseur(PharmFournisseur pharmFournisseur);

	public void retirePharmFournisseur(PharmFournisseur pharmFournisseur);

	public PharmFournisseur getPharmFournisseurById(Integer pharmFournisseurId);

	public Collection<PharmFournisseur> getAllPharmFournisseurs();

	public void updatePharmFournisseur(PharmFournisseur pharmFournisseur);

}
