package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Date;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommandeSiteService extends OpenmrsService {

	public void savePharmCommandeSite(PharmCommandeSite pharmCommandeSite);

	public void deletePharmCommandeSite(PharmCommandeSite pharmCommandeSite);

	public PharmCommandeSite getPharmCommandeSiteById(Integer pharmCommandeSiteId);

	public Collection<PharmCommandeSite> getPharmCommandeSiteBySP(PharmSite site, PharmProgramme programme, String mode);

	public Collection<PharmCommandeSite> getAllPharmCommandeSites();

	public void updatePharmCommandeSite(PharmCommandeSite pharmCommandeSite);

	public void savePharmLigneCommandeSite(PharmLigneCommandeSite pharmLignedispensation);

	public void savePharmLigneCommandeSites(Collection<PharmLigneCommandeSite> pharmLignedispensations);

	public PharmLigneCommandeSite getPharmLigneCommandeSite(PharmLigneCommandeSiteId pharmLigneCommandeSiteId);

	public void updatePharmLigneCommandeSite(PharmLigneCommandeSite pharmLigneCommandeSite);
	
	public void deletePharmLigneCommandeSite(PharmLigneCommandeSite pharmLigneCommandeSite);

	public Collection<PharmCommandeSite> getPharmCommandeSiteByPeriod(PharmSite site, PharmProgramme programme, Date date,
			String mode);

	public Collection<PharmCommandeSite> getPharmCommandeByPeriod(PharmProgramme programme, Date date,String mode);

	public Collection<PharmLigneCommandeSite> getProduitRupture(Date dateDebut, Date dateFin);
	
	public Collection<PharmCommandeSite> getPharmCommandeByPeriod(PharmProgramme programme, Date dateDebut,Date dateFin,String mode);
	public Collection<PharmLigneCommandeSite> getPharmLigneCommandeByParams(PharmProgramme programme, Date dateDebut,Date dateFin);
	

}
