package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;
import java.util.Date;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.db.OperationDAO;
import org.openmrs.module.pharmagest.api.db.OrdonnanceDAO;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class OperationServiceImpl extends BaseOpenmrsService implements OperationService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private OperationDAO dao;

	/**
	 * @return the dao
	 */
	public OperationDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(OperationDAO dao) {
		this.dao = dao;
	}

	@Override
	public void saveOperation(Operation operation) {
		getDao().saveOperation(operation);

	}

	@Override
	public void deleteOperation(Operation operation) {
		getDao().retireOperation(operation);

	}

	@Override
	public Operation getOperationById(Integer operationId) {

		return getDao().getOperationById(operationId);
	}

	@Override
	public Collection<Operation> getAllOperations() {

		return getDao().getAllOperations();
	}

	@Override
	public void updateOperation(Operation operation) {
		getDao().updateOperation(operation);

	}

	@Override
	public void saveLigneOperation(LingeOperation ligneOperation) {
		getDao().saveLigneOperation(ligneOperation);

	}

	@Override
	public void saveLigneOperations(Collection<LingeOperation> ligneOperations) {

	}

	@Override
	public void savePharmOperation(PharmOperation operation) {
		getDao().savePharmOperation(operation);

	}

	@Override
	public void deletePharmOperation(PharmOperation operation) {
		getDao().retirePharmOperation(operation);

	}

	@Override
	public PharmOperation getPharmOperationById(Integer operationId) {

		return getDao().getPharmOperationById(operationId);
	}

	@Override
	public Collection<PharmOperation> getAllPharmOperations() {

		return getDao().getAllPharmOperations();
	}

	@Override
	public void updatePharmOperation(PharmOperation operation) {
		getDao().updatePharmOperation(operation);

	}

	@Override
	public void savePharmLigneOperation(PharmLigneOperation ligneOperation) {
		getDao().savePharmLigneOperation(ligneOperation);

	}

	@Override
	public void savePharmLigneOperations(Collection<PharmLigneOperation> ligneOperations) {
		getDao().savePharmLigneOperations(ligneOperations);

	}

	@Override
	public void savePharmProduitAttribut(PharmProduitAttribut produitAttribut) {
		getDao().savePharmProduitAttribut(produitAttribut);

	}

	@Override
	public PharmProduitAttribut getPharmProduitAttributByElement(PharmProduit pharmProduit, String lot) {

		return getDao().getPharmProduitAttributByElement(pharmProduit, lot);
	}

	@Override
	public Collection<PharmOperation> getPharmOperationsByPeriod(PharmProgramme programme, Date min, Date max) {

		return getDao().getPharmOperationsByPeriod(programme, min, max);
	}

	@Override
	public PharmLigneOperation getPharmLigneOperation(PharmOperation operation, PharmProduitAttribut produitAttribut) {

		return getDao().getPharmLigneOperation(operation, produitAttribut);
	}

	@Override
	public Collection<PharmOperation> getPharmOperationsByDateConso(Date date) {
		
		return getDao().getPharmOperationsByDateConso(date);
	}

	@Override
	public Collection<PharmOperation> getPharmOperationsByPeriodConso(Date dateDebut, Date dateFin) {
		// TODO Auto-generated method stub
		return getDao().getPharmOperationsByPeriodConso(dateDebut, dateFin);
	}

	@Override
	public PharmOperation getPharmOperationsByRef(PharmProgramme programme, String operRef,
			PharmTypeOperation typeOperation) {
		
		return getDao().getPharmOperationsByRef(programme, operRef, typeOperation);
	}
	
	
	public Collection<PharmOperation> getPharmOperationsByBL(PharmProgramme programme, String operNum,
			PharmTypeOperation typeOperation) {
		
		return getDao().getPharmOperationsByBL(programme, operNum, typeOperation);
	}

	@Override
	public Collection<PharmOperation> getPharmOperationsByAttri(PharmProgramme programme, Date date,
			PharmTypeOperation typeOperation) {
		
		return getDao().getPharmOperationsByAttri(programme, date, typeOperation);
	}
	
	public void deletePharmLigneOperationsByOperation(PharmOperation operation){
		getDao().retirePharmLigneOperationsByOperation(operation);
	}

	
	public Collection<PharmLigneOperation> getPharmLigneOperationByProduit(PharmOperation operation,
			PharmProduit produit) {
		return getDao().getPharmLigneOperationByProduit(operation, produit);
	}

	
	public PharmLigneOperation getPharmLigneOperationByID(PharmLigneOperationId ligneOperationId) {
		
		return getDao().getPharmLigneOperationByID(ligneOperationId);
	}

	@Override
	public Collection<PharmOperation> getPharmOperationsByAttri(PharmProgramme programme, Date dateDebut, Date dateFin,
			PharmTypeOperation typeOperation) {
		// TODO Auto-generated method stub
		return getDao().getPharmOperationsByAttri(programme, dateDebut, dateFin, typeOperation);
	}

	

}
