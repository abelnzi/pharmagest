package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.db.OperationDAO;
import org.openmrs.module.pharmagest.api.db.OrdonnanceDAO;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class OperationServiceImpl extends BaseOpenmrsService implements OperationService{
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private OperationDAO dao;

	/**
	 * @return the dao
	 */
	public OperationDAO getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
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
		// TODO Auto-generated method stub
		
	}

	
}
