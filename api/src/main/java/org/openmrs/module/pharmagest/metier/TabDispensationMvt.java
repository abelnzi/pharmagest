package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.pharmagest.LigneDispensation;

public class TabDispensationMvt {
	private Map ligneDispensations;

	public TabDispensationMvt() {
		ligneDispensations = Collections.synchronizedMap(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public void addLigneDispensation(LigneDispensationMvt ld) {
		if (this.ligneDispensations != null && ld != null) {
			this.ligneDispensations.put(ld.getPharmProduit().getProdId()+ld.getLgnRecptLot(), ld);
		}
	}

	public void removeLigneDispensationById(String id) {
		if (this.ligneDispensations != null && id != null) {
			ligneDispensations.remove(id);
		}
	}

	public LigneDispensationMvt getLigneDispensationById(String id) {
		if (this.ligneDispensations != null && id != null) {
			return (LigneDispensationMvt) ligneDispensations.get(id);
		}
		return null;
	}

	public Collection getLigneDispensationsCollection() {
		return ligneDispensations.values();
	}

	public Map getLigneDispensations() {
		return ligneDispensations;
	}

	public void setLigneDispensations(Map ligneDispensations) {
		this.ligneDispensations = ligneDispensations;
	}
}
