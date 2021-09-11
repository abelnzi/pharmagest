package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmSite;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SiteService extends OpenmrsService {

	public void savePharmSite(PharmSite pharmSite);

	public void deletePharmSite(PharmSite pharmSite);

	public PharmSite getPharmSiteById(Integer pharmSiteId);
	
	public PharmSite getPharmSiteByCode(String code);
	
	public PharmSite getPharmSiteByName(String sitLib);

	public Collection<PharmSite> getAllPharmSites();

	public void updatePharmSite(PharmSite pharmSite);

}
