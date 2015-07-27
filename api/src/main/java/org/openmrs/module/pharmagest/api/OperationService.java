package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OperationService extends OpenmrsService {
	public void saveOperation(Operation operation);

	public void deleteOperation(Operation operation);

	public Operation getOperationById(Integer operationId);

	public Collection<Operation> getAllOperations();

	public void updateOperation(Operation operation);

	public void saveLigneOperation(LingeOperation ligneOperation);

	public void saveLigneOperations(Collection<LingeOperation> ligneOperations);
}
