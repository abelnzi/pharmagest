package org.openmrs.module.pharmagest.metier;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.pharmagest.PharmHistoMouvementStock;

public class HistoMvmResponseJson {

	/* Paramètre de controle */
	private Integer sEcho;

	/* Nombre total de résultats correspondant aux critères de recherche */
	private Integer iTotalDisplayRecords;

	/*
	 * Liste des resultats Par défaut dataTable cherche les résultats dans une
	 * propriété nommée aaData. On peut configurer le nom de la propriété.
	 */
	private List<PharmHistoMouvementStock> aaData = new ArrayList<PharmHistoMouvementStock>();

	public HistoMvmResponseJson() {
        super();
    }

	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}

	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<PharmHistoMouvementStock> getAaData() {
		return aaData;
	}

	public void setAaData(List<PharmHistoMouvementStock> aaData) {
		this.aaData = aaData;
	}
}
