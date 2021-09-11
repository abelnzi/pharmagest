package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.db.CommandeSiteDAO;

public class CommandeSiteServiceImpl extends BaseOpenmrsService implements CommandeSiteService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private CommandeSiteDAO dao;

	/**
	 * @return the dao
	 */
	public CommandeSiteDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(CommandeSiteDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		getDao().savePharmCommandeSite(pharmCommandeSite);

	}

	@Override
	public void deletePharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		getDao().retirePharmCommandeSite(pharmCommandeSite);

	}

	@Override
	public PharmCommandeSite getPharmCommandeSiteById(Integer pharmCommandeSiteId) {

		return getDao().getPharmCommandeSiteById(pharmCommandeSiteId);
	}

	@Override
	public Collection<PharmCommandeSite> getPharmCommandeSiteBySP(PharmSite site, PharmProgramme programme,String mode) {
		// TODO Auto-generated method stub
		return getDao().getPharmCommandeSiteBySP(site, programme,mode);
	}

	@Override
	public Collection<PharmCommandeSite> getPharmCommandeSiteByPeriod(PharmSite site, PharmProgramme programme,
			Date date,String mode) {

		return getDao().getPharmCommandeSiteByPeriod(site, programme, date,mode);
	}

	@Override
	public Collection<PharmCommandeSite> getPharmCommandeByPeriod(PharmProgramme programme, Date date,String mode) {

		return getDao().getPharmCommandeByPeriod(programme, date,mode);
	}

	@Override
	public Collection<PharmCommandeSite> getAllPharmCommandeSites() {

		return getDao().getAllPharmCommandeSites();
	}

	@Override
	public void updatePharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		getDao().updatePharmCommandeSite(pharmCommandeSite);

	}

	@Override
	public void savePharmLigneCommandeSite(PharmLigneCommandeSite pharmLignedispensation) {
		getDao().savePharmLigneCommandeSite(pharmLignedispensation);

	}

	@Override
	public void savePharmLigneCommandeSites(Collection<PharmLigneCommandeSite> pharmLignedispensations) {
		getDao().savePharmLigneCommandeSites(pharmLignedispensations);

	}

	@Override
	public PharmLigneCommandeSite getPharmLigneCommandeSite(PharmLigneCommandeSiteId pharmLigneCommandeSiteId) {

		return getDao().getPharmLigneCommandeSite(pharmLigneCommandeSiteId);
	}

	@Override
	public void updatePharmLigneCommandeSite(PharmLigneCommandeSite pharmLigneCommandeSite) {
		getDao().updatePharmLigneCommandeSite(pharmLigneCommandeSite);

	}

	@Override
	public void deletePharmLigneCommandeSite(PharmLigneCommandeSite pharmLigneCommandeSite) {
		getDao().retirePharmLigneCommandeSite(pharmLigneCommandeSite);
		
	}

	@Override
	public Collection<PharmLigneCommandeSite> getProduitRupture(Date dateDebut, Date dateFin) {
		// TODO Auto-generated method stub
		return getDao().getProduitRupture(dateDebut, dateFin);
	}

	@Override
	public Collection<PharmCommandeSite> getPharmCommandeByPeriod(PharmProgramme programme, Date dateDebut,
			Date dateFin, String mode) {
		return getDao().getPharmCommandeByPeriod(programme, dateDebut, dateFin, mode);
	}

	@Override
	public Collection<PharmLigneCommandeSite> getPharmLigneCommandeByParams(PharmProgramme programme, Date dateDebut,
			Date dateFin) {
		
		return getDao().getPharmLigneCommandeByParams(programme, dateDebut, dateFin);
	}

}
