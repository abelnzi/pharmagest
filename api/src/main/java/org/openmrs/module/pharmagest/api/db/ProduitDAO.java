package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmPrixProduit;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;

public interface ProduitDAO {

	public void savePharmProduit(PharmProduit pharmProduit);

	public void retirePharmProduit(PharmProduit pharmProduit);

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
