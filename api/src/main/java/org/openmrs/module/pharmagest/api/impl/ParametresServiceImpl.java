package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.db.ParametresDao;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ParametresServiceImpl extends BaseOpenmrsService implements ParametresService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private ParametresDao dao;

	public ParametresDao getDao() {
		return dao;
	}

	public void setDao(ParametresDao dao) {
		this.dao = dao;
	}

	@Override
	public Collection<PharmRegime> getAllRegimes() {
		// TODO Auto-generated method stub
		return getDao().getAllRegimes();
	}

	@Override
	public Collection<PharmMedecin> getAllMedecins() {
		// TODO Auto-generated method stub
		return getDao().getAllMedecins();
	}

	@Override
	public Collection<PharmProduit> getAllProduits() {
		// TODO Auto-generated method stub
		return getDao().getAllProduits();
	}

	

	@Override
	public PharmProduit getProduitById(Integer produitId) {
		// TODO Auto-generated method stub
		return getDao().getProduitById(produitId);
	}

	@Override
	public PharmRegime getRegimeById(Integer regimeId) {
		// TODO Auto-generated method stub
		return getDao().getRegimeById(regimeId);
	}

	@Override
	public PharmMedecin getMedecinById(Integer medecinId) {
		// TODO Auto-generated method stub
		return getDao().getMedecinById(medecinId);
	}

	@Override
	public PharmProgramme getProgrammeById(Integer programmeId) {
		return getDao().getProgrammeById(programmeId);
	}

	@Override
	public PharmFournisseur getFournisseurById(Integer fournisseurId) {
		return getDao().getFournisseurById(fournisseurId);
	}

	@Override
	public Collection<PharmFournisseur> getAllFournisseurs() {
		return getDao().getAllFournisseurs();
	}

	@Override
	public Collection<PharmProgramme> getAllProgrammes() {
		return getDao().getAllProgrammes();
	}

	@Override
	public Collection<PharmTypeOperation> getAllTypeOperation() {

		return getDao().getAllTypeOperation();
	}

	@Override
	public PharmTypeOperation getTypeOperationById(Integer typeOperationId) {

		return getDao().getTypeOperationById(typeOperationId);
	}

	@Override
	public Collection<PatientIdentifier> getPatientIdentifierByIdentifier(String identifier) {
		return getDao().getPatientIdentifierByIdentifier(identifier);
	}
	
	@Override
	public PatientIdentifier getPatientIdentifierByIdentifierType(int patientId, int typeIdentifier) {
		return getDao().getPatientIdentifierByIdentifierType(patientId,typeIdentifier);
	}
	
	public PatientComplement getPatientComplementByIdentifier(Integer patientIdentifierId) {
		return getDao().getPatientComplementByIdentifier(patientIdentifierId);
	}

	@Override
	public void saveOrUpdateGestionnaire(PharmGestionnairePharma pharmGestionnairePharma) {
		getDao().saveOrUpdateGestionnaire(pharmGestionnairePharma);
		
	}

	@Override
	public void saveTypeOperation(PharmTypeOperation typeOperation) {
		getDao().saveTypeOperation(typeOperation);
		
	}

}
