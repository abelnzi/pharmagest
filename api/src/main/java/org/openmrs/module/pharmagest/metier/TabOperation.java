package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.LingeOperation;

public class TabOperation {
	private Map ligneOperations;

	public TabOperation() {
		ligneOperations = Collections.synchronizedMap(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public void addLigneOperation(LingeOperation lo) {
		if (this.ligneOperations != null && lo != null) {
			this.ligneOperations.put(""+lo.getProduit().getProdId(), lo);
		}
	}

	public void removeLigneOperationById(String id) {
		if (this.ligneOperations != null && id != null) {
			ligneOperations.remove(id);
		}
	}

	public LingeOperation getLigneOperationById(String id) {
		if (this.ligneOperations != null && id != null) {
			return (LingeOperation) ligneOperations.get(id);
		}
		return null;
	}

	public Collection getLigneOperationsCollection() {
		return ligneOperations.values();
	}

	public Map getLigneOperations() {
		return ligneOperations;
	}

	public void setLigneOperations(Map ligneOperations) {
		this.ligneOperations = ligneOperations;
	}
}
