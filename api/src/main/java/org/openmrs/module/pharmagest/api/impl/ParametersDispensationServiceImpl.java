package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.TypeOperation;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;
import org.openmrs.module.pharmagest.api.db.ParametersDispensationDao;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ParametersDispensationServiceImpl extends BaseOpenmrsService implements ParametersDispensationService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private ParametersDispensationDao dao;

	@Override
	public Collection<Regime> getAllRegimes() {
		// TODO Auto-generated method stub
		return getDao().getAllRegimes();
	}

	@Override
	public Collection<Medecin> getAllMedecins() {
		// TODO Auto-generated method stub
		return getDao().getAllMedecins();
	}

	@Override
	public Collection<Produit> getAllProduits() {
		// TODO Auto-generated method stub
		return getDao().getAllProduits();
	}

	/**
	 * @return the dao
	 */
	public ParametersDispensationDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ParametersDispensationDao dao) {
		this.dao = dao;
	}

	@Override
	public Produit getProduitById(Integer produitId) {
		// TODO Auto-generated method stub
		return getDao().getProduitById(produitId);
	}

	@Override
	public Regime getRegimeById(Integer regimeId) {
		// TODO Auto-generated method stub
		return getDao().getRegimeById(regimeId);
	}

	@Override
	public Medecin getMedecinById(Integer medecinId) {
		// TODO Auto-generated method stub
		return getDao().getMedecinById(medecinId);
	}

	@Override
	public Programme getProgrammeById(Integer programmeId) {
		return getDao().getProgrammeById(programmeId);
	}

	@Override
	public Fournisseur getFournisseurById(Integer fournisseurId) {
		return getDao().getFournisseurById(fournisseurId);
	}

	@Override
	public Collection<Fournisseur> getAllFournisseurs() {
		return getDao().getAllFournisseurs();
	}

	@Override
	public Collection<Programme> getAllProgrammes() {
		return getDao().getAllProgrammes();
	}

	@Override
	public Collection<TypeOperation> getAllTypeOperation() {

		return getDao().getAllTypeOperation();
	}

	@Override
	public TypeOperation getTypeOperationById(Integer typeOperationId) {

		return getDao().getTypeOperationById(typeOperationId);
	}

	@Override
	public PatientIdentifier getPatientIdentifierByIdentifier(String identifier) {
		return getDao().getPatientIdentifierByIdentifier(identifier);
	}
	public PatientComplement getPatientComplementByIdentifier(Integer patientIdentifierId) {
		return getDao().getPatientComplementByIdentifier(patientIdentifierId);
	}

}
