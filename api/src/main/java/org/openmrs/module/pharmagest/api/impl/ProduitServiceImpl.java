package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmPrixProduit;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.db.ProduitDAO;

public class ProduitServiceImpl extends BaseOpenmrsService implements ProduitService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private ProduitDAO dao;

	public ProduitDAO getDao() {
		return dao;
	}

	public void setDao(ProduitDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmProduit(PharmProduit pharmProduit) {
		getDao().savePharmProduit(pharmProduit);
	}

	@Override
	public void deletePharmProduit(PharmProduit pharmProduit) {
		getDao().retirePharmProduit(pharmProduit);

	}

	
	public PharmProduit getPharmProduitById(Integer pharmProduitId) {
		return getDao().getPharmProduitById(pharmProduitId);
	}

	
	public Collection<PharmProduit> getAllPharmProduits() {

		return getDao().getAllPharmProduits();
	}

	
	public void updatePharmProduit(PharmProduit pharmProduit) {
		getDao().updatePharmProduit(pharmProduit);

	}

	
	public PharmProduit findProduitByCode(String code) {
		return getDao().findProduitByCode(code);
	}

	
	public PharmProduitCompl getProduitComplByProduit(PharmProduit produit) {
		return getDao().getProduitComplByProduit(produit);
	}

	@Override
	public PharmPrixProduit getPharmPrixProduitByProduit(PharmProduit produit) {
		// TODO Auto-generated method stub
		return getDao().getPharmPrixProduitByProduit(produit);
	}

	@Override
	public void savePharmProduitCompl(PharmProduitCompl pharmProduitCompl) {
		getDao().savePharmProduitCompl(pharmProduitCompl);
		
	}

	@Override
	public PharmPrixProduit getPharmPrixProduitByPP(PharmProduit produit, int programId) {
		
		return getDao().getPharmPrixProduitByPP(produit, programId);
	}

	@Override
	public void savePharmPrixProduit(PharmPrixProduit pharmPrixProduit) {
		getDao().savePharmPrixProduit(pharmPrixProduit);
		
	}

	@Override
	public void savePharmProgrammeProduit(Integer programId, Integer prodId) {
		getDao().savePharmProgrammeProduit(programId, prodId);
		
	}

}
