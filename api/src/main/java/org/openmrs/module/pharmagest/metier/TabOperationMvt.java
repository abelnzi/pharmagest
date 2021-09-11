package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmLigneOperation;

public class TabOperationMvt {
	private Map ligneOperations;

	public TabOperationMvt() {
		ligneOperations = Collections.synchronizedMap(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public void addLigneOperation(LigneOperationMvt lo) {

		if (this.ligneOperations != null && lo != null) {
			this.ligneOperations
					.put(lo.getProduit().getProdId()+lo.getLgnRecptLot(), lo);
		}
	}

	public void removeLigneOperationById(String id) {
		if (this.ligneOperations != null && id != null) {
			ligneOperations.remove(id);
		}
	}

	public LigneOperationMvt getLigneOperationById(String id) {
		if (this.ligneOperations != null && id != null) {
			return (LigneOperationMvt) ligneOperations.get(id);
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
