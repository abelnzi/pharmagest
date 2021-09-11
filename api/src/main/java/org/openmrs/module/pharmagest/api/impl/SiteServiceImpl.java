package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.api.db.SiteDAO;

public class SiteServiceImpl extends BaseOpenmrsService implements SiteService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private SiteDAO dao;

	/**
	 * @return the dao
	 */
	public SiteDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(SiteDAO dao) {
		this.dao = dao;
	}

	@Override
	public void savePharmSite(PharmSite pharmSite) {
		getDao().savePharmSite(pharmSite);
		
	}

	@Override
	public void deletePharmSite(PharmSite pharmSite) {
		getDao().retirePharmSite(pharmSite);
		
	}

	@Override
	public PharmSite getPharmSiteById(Integer pharmSiteId) {
		
		return getDao().getPharmSiteById(pharmSiteId);
	}
	
	@Override
	public PharmSite getPharmSiteByCode(String code) {
		
		return getDao().getPharmSiteByCode(code);
	}

	@Override
	public Collection<PharmSite> getAllPharmSites() {
		
		return getDao().getAllPharmSites();
	}

	@Override
	public void updatePharmSite(PharmSite pharmSite) {
		getDao().updatePharmSite(pharmSite);
		
	}

	@Override
	public PharmSite getPharmSiteByName(String sitLib) {
		// TODO Auto-generated method stub
		return getDao().getPharmSiteByName(sitLib);
	}

	

	

}
