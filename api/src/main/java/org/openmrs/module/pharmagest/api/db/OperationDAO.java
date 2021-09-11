package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;
import java.util.Date;

import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneOperationId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmTypeOperation;

public interface OperationDAO {

	public void saveOperation(Operation operation);

	public void retireOperation(Operation operation);

	public Operation getOperationById(Integer operationId);

	public Collection<Operation> getAllOperations();

	public void updateOperation(Operation operation);

	public void saveLigneOperation(LingeOperation ligneOperation);

	public void saveLigneOperations(Collection<LingeOperation> ligneOperations);

	public void savePharmOperation(PharmOperation operation);

	public void retirePharmOperation(PharmOperation operation);

	public PharmOperation getPharmOperationById(Integer operationId);

	public Collection<PharmOperation> getAllPharmOperations();

	public void updatePharmOperation(PharmOperation operation);

	public void savePharmLigneOperation(PharmLigneOperation ligneOperation);

	public void savePharmLigneOperations(Collection<PharmLigneOperation> ligneOperations);

	public void savePharmProduitAttribut(PharmProduitAttribut produitAttribut);

	public PharmProduitAttribut getPharmProduitAttributByElement(PharmProduit pharmProduit, String lot);

	public PharmLigneOperation getPharmLigneOperation(PharmOperation operation, PharmProduitAttribut produitAttribut);
	
	public PharmLigneOperation getPharmLigneOperationByID(PharmLigneOperationId ligneOperationId );

	public Collection<PharmOperation> getPharmOperationsByPeriod(PharmProgramme programme, Date min, Date max);

	public Collection<PharmOperation> getPharmOperationsByDateConso(Date date);
	
	public Collection<PharmOperation> getPharmOperationsByPeriodConso(Date dateDebut, Date dateFin);

	public PharmOperation getPharmOperationsByRef(PharmProgramme programme, String operRef, PharmTypeOperation typeOperation);
	
	public Collection<PharmOperation> getPharmOperationsByBL(PharmProgramme programme, String operNum, PharmTypeOperation typeOperation);
	
	public Collection<PharmOperation> getPharmOperationsByAttri(PharmProgramme programme, Date date,PharmTypeOperation typeOperation);
	
	public void retirePharmLigneOperationsByOperation(PharmOperation operation);
	
	public Collection<PharmLigneOperation>  getPharmLigneOperationByProduit(PharmOperation operation,PharmProduit produit);
	
	public Collection<PharmOperation> getPharmOperationsByAttri(PharmProgramme programme, Date dateDebut,Date dateFin,
			PharmTypeOperation typeOperation) ;
}
