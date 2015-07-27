package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;

public interface OperationDAO {

	public void saveOperation(Operation operation);

	public void retireOperation(Operation operation);

	public Operation getOperationById(Integer operationId);

	public Collection<Operation> getAllOperations();

	public void updateOperation(Operation operation);

	public void saveLigneOperation(LingeOperation ligneOperation);

	public void saveLigneOperations(Collection<LingeOperation> ligneOperations);

}
