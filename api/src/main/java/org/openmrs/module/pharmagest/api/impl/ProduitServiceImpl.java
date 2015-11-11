package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmProduit;
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

	@Override
	public PharmProduit getPharmProduitById(Integer pharmProduitId) {
		return getDao().getPharmProduitById(pharmProduitId);
	}

	@Override
	public Collection<PharmProduit> getAllPharmProduits() {
		
		return getDao().getAllPharmProduits();
	}

	@Override
	public void updatePharmProduit(PharmProduit pharmProduit) {
		getDao().updatePharmProduit(pharmProduit);

	}

}
