package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FournisseurService extends OpenmrsService {
	public void savePharmFournisseur(PharmFournisseur pharmFournisseur);

	public void deletePharmFournisseur(PharmFournisseur pharmFournisseur);

	public PharmFournisseur getPharmFournisseurById(Integer pharmFournisseurId);

	public Collection<PharmFournisseur> getAllPharmFournisseurs();

	public void updatePharmFournisseur(PharmFournisseur pharmFournisseur);
}
