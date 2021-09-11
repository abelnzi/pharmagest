package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.PharmSite;

public interface SiteDAO {

	public void savePharmSite(PharmSite pharmSite);

	public void retirePharmSite(PharmSite pharmSite);

	public PharmSite getPharmSiteById(Integer pharmSiteId);

	public PharmSite getPharmSiteByCode(String code);
	
	public PharmSite getPharmSiteByName(String sitLib);

	public Collection<PharmSite> getAllPharmSites();

	public void updatePharmSite(PharmSite pharmSite);

}
