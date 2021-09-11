package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.api.FournisseurService;
import org.openmrs.module.pharmagest.api.db.FournisseurDAO;

public class FournisseurServiceImpl extends BaseOpenmrsService implements FournisseurService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private FournisseurDAO dao;

	

	public FournisseurDAO getDao() {
		return dao;
	}

	public void setDao(FournisseurDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmFournisseur(PharmFournisseur pharmFournisseur) {
		getDao().savePharmFournisseur(pharmFournisseur);
	}

	@Override
	public void deletePharmFournisseur(PharmFournisseur pharmFournisseur) {
		getDao().retirePharmFournisseur(pharmFournisseur);

	}

	@Override
	public PharmFournisseur getPharmFournisseurById(Integer pharmFournisseurId) {
		return getDao().getPharmFournisseurById(pharmFournisseurId);
	}

	@Override
	public Collection<PharmFournisseur> getAllPharmFournisseurs() {
		
		return getDao().getAllPharmFournisseurs();
	}

	@Override
	public void updatePharmFournisseur(PharmFournisseur pharmFournisseur) {
		getDao().updatePharmFournisseur(pharmFournisseur);

	}

}
