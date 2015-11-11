package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.Inventaire;
import org.openmrs.module.pharmagest.LigneInventaire;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.db.InventaireDAO;

public class InventaireServiceImpl extends BaseOpenmrsService implements InventaireService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private InventaireDAO dao;

	public InventaireDAO getDao() {
		return dao;
	}

	public void setDao(InventaireDAO dao) {
		this.dao = dao;
	}

	@Override
	public void saveInventaire(Inventaire inventaire) {
		getDao().saveInventaire(inventaire);

	}

	@Override
	public void deleteInventaire(Inventaire inventaire) {
		getDao().retireInventaire(inventaire);
	}

	@Override
	public Inventaire getInventaireById(Integer inventaireId) {

		return getDao().getInventaireById(inventaireId);
	}

	@Override
	public Collection<Inventaire> getAllInventaires() {

		return getDao().getAllInventaires();
	}

	@Override
	public void updateInventaire(Inventaire inventaire) {
		getDao().updateInventaire(inventaire);

	}

	@Override
	public void saveLigneInventaire(LigneInventaire ligneInventaire) {
		getDao().saveLigneInventaire(ligneInventaire);

	}

	@Override
	public void saveLigneInventaires(Collection<LigneInventaire> ligneInventaires) {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePharmInventaire(PharmInventaire pharmInventaire) {
		getDao().savePharmInventaire(pharmInventaire);

	}

	@Override
	public void deletePharmInventaire(PharmInventaire pharmInventaire) {
		getDao().retirePharmInventaire(pharmInventaire);

	}

	@Override
	public PharmInventaire getPharmInventaireById(Integer pharmInventaireId) {

		return getDao().getPharmInventaireById(pharmInventaireId);
	}

	@Override
	public Collection<PharmInventaire> getAllPharmInventaires() {

		return getDao().getAllPharmInventaires();
	}

	@Override
	public void updatePharmInventaire(PharmInventaire pharmInventaire) {
		getDao().updatePharmInventaire(pharmInventaire);

	}

	@Override
	public void savePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire) {
		getDao().savePharmLigneInventaire(pharmLigneInventaire);

	}

	@Override
	public void savePharmLigneInventaires(Collection<PharmLigneInventaire> pharmLigneInventaires) {
		getDao().savePharmLigneInventaires(pharmLigneInventaires);

	}

}
