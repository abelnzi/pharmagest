package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmLigneRcId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.api.RapportCommandeService;
import org.openmrs.module.pharmagest.api.db.RapportCommandeDAO;

public class RapportCommandeServiceImpl extends BaseOpenmrsService implements RapportCommandeService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private RapportCommandeDAO dao;

	/**
	 * @return the dao
	 */
	public RapportCommandeDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(RapportCommandeDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmRapportCommande(PharmRapportCommande pharmRapportCommande) {
		getDao().savePharmRapportCommande(pharmRapportCommande);

	}

	@Override
	public void deletePharmRapportCommande(PharmRapportCommande pharmRapportCommande) {
		getDao().retirePharmRapportCommande(pharmRapportCommande);

	}

	public PharmRapportCommande getPharmRapportCommande(PharmProgramme programme) {
		return getDao().getPharmRapportCommande(programme);
	}

	@Override
	public PharmRapportCommande getPharmRapportCommandeById(Integer pharmRapportCommandeId) {

		return getDao().getPharmRapportCommandeById(pharmRapportCommandeId);
	}

	@Override
	public Collection<PharmRapportCommande> getPharmRapportCommandeByProgram(PharmProgramme programme) {

		return getDao().getPharmRapportCommandeByProgram(programme);
	}

	@Override
	public Collection<PharmRapportCommande> getPharmRapportCommandeByPeriod(PharmProgramme programme, Date date) {

		return getDao().getPharmRapportCommandeByPeriod(programme, date);
	}

	@Override
	public Collection<PharmRapportCommande> getAllPharmRapportCommandes() {

		return getDao().getAllPharmRapportCommandes();
	}

	@Override
	public void updatePharmRapportCommande(PharmRapportCommande pharmRapportCommande) {
		getDao().updatePharmRapportCommande(pharmRapportCommande);

	}

	@Override
	public void savePharmLigneRc(PharmLigneRc pharmLigneRc) {
		getDao().savePharmLigneRc(pharmLigneRc);

	}

	@Override
	public void savePharmLigneRcs(Collection<PharmLigneRc> pharmLigneRcs) {
		getDao().savePharmLigneRcs(pharmLigneRcs);

	}

	@Override
	public PharmLigneRc getPharmLigneRc(PharmLigneRcId pharmLigneRcId) {

		return getDao().getPharmLigneRc(pharmLigneRcId);
	}

	@Override
	public void updatePharmLigneRc(PharmLigneRc pharmLigneRc) {
		getDao().updatePharmLigneRc(pharmLigneRc);

	}

}
