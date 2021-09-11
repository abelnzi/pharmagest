package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TabReceptionMvt {
	private Map ligneReceptions;

	public TabReceptionMvt() {
		ligneReceptions = Collections.synchronizedMap(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public void addLigneReception(LigneReceptionMvt lo) {

		if (this.ligneReceptions != null && lo != null) {
			this.ligneReceptions
					.put(lo.getProduit().getProdId()+lo.getLgnRecptLot(), lo);
		}
	}

	public void removeLigneReceptionById(String id) {
		if (this.ligneReceptions != null && id != null) {
			ligneReceptions.remove(id);
		}
	}

	public LigneReceptionMvt getLigneReceptionById(String id) {
		if (this.ligneReceptions != null && id != null) {
			return (LigneReceptionMvt) ligneReceptions.get(id);
		}
		return null;
	}

	public Collection getLigneReceptionsCollection() {
		return ligneReceptions.values();
	}

	public Map getLigneReceptions() {
		return ligneReceptions;
	}

	public void setLigneReceptions(Map ligneReceptions) {
		this.ligneReceptions = ligneReceptions;
	}
}
