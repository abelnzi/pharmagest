package org.openmrs.module.pharmagest.api;

import java.util.Collection;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
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

	public void savePharmOperation(PharmOperation operation);

	public void deletePharmOperation(PharmOperation operation);

	public PharmOperation getPharmOperationById(Integer operationId);

	public Collection<PharmOperation> getAllPharmOperations();

	public void updatePharmOperation(PharmOperation operation);

	public void savePharmLigneOperation(PharmLigneOperation ligneOperation);

	public void savePharmLigneOperations(Collection<PharmLigneOperation> ligneOperations);

	public void savePharmProduitAttribut(PharmProduitAttribut produitAttribut);

	public PharmProduitAttribut getPharmProduitAttributByElement(PharmProduit pharmProduit, String lot);
}
