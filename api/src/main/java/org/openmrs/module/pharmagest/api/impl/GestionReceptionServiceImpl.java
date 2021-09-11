package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.api.GestionReceptionService;
import org.openmrs.module.pharmagest.api.db.GestionReceptionDAO;
import org.openmrs.module.pharmagest.metier.CoutAchatProduit;

public class GestionReceptionServiceImpl extends BaseOpenmrsService implements GestionReceptionService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private GestionReceptionDAO dao;

	/**
	 * @return the dao
	 */
	public GestionReceptionDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(GestionReceptionDAO dao) {
		this.dao = dao;
	}

	public void savePharmReception(PharmReception pharmReception) {
		getDao().savePharmReception(pharmReception);

	}

	public void deletePharmReception(PharmReception pharmReception) {
		getDao().retirePharmReception(pharmReception);

	}

	public PharmReception getPharmReceptionById(Integer receptionId) {

		return getDao().getPharmReceptionById(receptionId);
	}
	
	
	public Collection<PharmReception> getPharmReceptionByBL(PharmProgramme programme, String bl) {
		
		return getDao().getPharmReceptionByBL(programme, bl);
	}

	public Collection<PharmReception> getAllPharmReceptions() {
		// TODO Auto-generated method stub
		return getDao().getAllPharmReceptions();
	}

	public void updatePharmReception(PharmReception pharmReception) {
		// TODO Auto-generated method stub
		getDao().updatePharmReception(pharmReception);

	}

	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date date, String mode) {
		// TODO Auto-generated method stub
		return getDao().getPharmReceptionsByAttri(programme, date, mode);
	}

	public void deletePharmLigneReceptionsByReception(PharmReception reception) {
		getDao().retirePharmLigneReceptionsByReception(reception);

	}

	@Override
	public void savePharmLigneReception(PharmLigneReception pharmLigneReception) {
		getDao().savePharmLigneReception(pharmLigneReception);

	}

	@Override
	public void savePharmLigneReceptions(Collection<PharmLigneReception> pharmLigneReceptions) {
		getDao().savePharmLigneReceptions(pharmLigneReceptions);

	}

	@Override
	public PharmLigneReception getPharmLigneReception(PharmLigneReceptionId pharmLigneReceptionId) {
		// TODO Auto-generated method stub
		return getDao().getPharmLigneReception(pharmLigneReceptionId);
	}

	@Override
	public void updatePharmLigneReception(PharmLigneReception pharmLigneReception) {
		// TODO Auto-generated method stub
		getDao().updatePharmLigneReception(pharmLigneReception);
	}


	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date dateDebut, Date dateFin,
			String mode) {

		return getDao().getPharmReceptionsByAttri(programme, dateDebut, dateFin, mode);
	}

	@Override
	public CoutAchatProduit getCoutProduit(int prodAtId, Date dateCout) {
		
		return getDao().getCoutProduit(prodAtId, dateCout);
	}

	

}
