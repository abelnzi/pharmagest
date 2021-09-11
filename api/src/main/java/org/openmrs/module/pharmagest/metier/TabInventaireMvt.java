package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.pharmagest.LigneInventaire;

public class TabInventaireMvt {
	@SuppressWarnings("rawtypes")
	private Map ligneInventaires;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TabInventaireMvt() {
		this.ligneInventaires = Collections.synchronizedMap(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public void addLigneInventaire(LigneInventaireMvt li) {
		if (this.ligneInventaires != null && li != null) {
			this.ligneInventaires.put(li.getProduit().getProdId()+li.getLgnLot(), li);
		}
	}

	public void removeLigneInventaireById(String id) {
		if (this.ligneInventaires != null && id != null) {
			this.ligneInventaires.remove(id);
		}
	}

	public LigneInventaireMvt getLigneInventaireById(String id) {
		if (this.ligneInventaires != null && id != null) {
			return  (LigneInventaireMvt) ligneInventaires.get(id);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Collection getLigneInventaireCollection() {
		return this.ligneInventaires.values();
	}

	@SuppressWarnings("rawtypes")
	public Map getLigneInventaires() {
		return this.ligneInventaires;
	}

	@SuppressWarnings("rawtypes")
	public void setLigneInventaires(Map ligneInventaires) {
		this.ligneInventaires = ligneInventaires;
	}
}
