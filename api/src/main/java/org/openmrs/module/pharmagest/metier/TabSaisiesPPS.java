package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;

public class TabSaisiesPPS {
	private Map ligneCommandeSites;

	public TabSaisiesPPS() {
		ligneCommandeSites = Collections.synchronizedMap(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public void addLigneCommandeSite(LigneCommandeSite lo) {

		if (this.ligneCommandeSites != null && lo != null) {
			this.ligneCommandeSites
					.put(lo.getProduit().getProdId()+"", lo);
		}
	}

	public void removeLigneCommandeSiteById(String id) {
		if (this.ligneCommandeSites != null && id != null) {
			ligneCommandeSites.remove(id);
		}
	}

	public LigneCommandeSite getLigneCommandeSiteById(String id) {
		if (this.ligneCommandeSites != null && id != null) {
			return (LigneCommandeSite) ligneCommandeSites.get(id);
		}
		return null;
	}

	public Collection getLigneCommandeSitesCollection() {
		return ligneCommandeSites.values();
	}

	public Map getLigneCommandeSites() {
		return ligneCommandeSites;
	}

	public void setLigneCommandeSites(Map ligneCommandeSites) {
		this.ligneCommandeSites = ligneCommandeSites;
	}
}
