package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmPrixProduit;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProduitService extends OpenmrsService {
	public void savePharmProduit(PharmProduit pharmProduit);

	public void deletePharmProduit(PharmProduit pharmProduit);

	public PharmProduit getPharmProduitById(Integer pharmProduitId);

	public PharmProduit findProduitByCode(String code);

	public Collection<PharmProduit> getAllPharmProduits();

	public void updatePharmProduit(PharmProduit pharmProduit);
	
	public PharmProduitCompl getProduitComplByProduit(PharmProduit produit);
	
	public void savePharmProduitCompl(PharmProduitCompl pharmProduitCompl);
	
	public PharmPrixProduit getPharmPrixProduitByProduit(PharmProduit produit);
	
	public PharmPrixProduit getPharmPrixProduitByPP(PharmProduit produit, int programId);
	
	public void savePharmPrixProduit (PharmPrixProduit pharmPrixProduit);
	public void savePharmProgrammeProduit(Integer programId, Integer prodId);
}
