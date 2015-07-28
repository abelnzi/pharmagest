package org.openmrs.module.pharmagest.metier;

import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.TypeOperation;

public class HistoMvmType {

	HistoMouvementStock histoMouvementStock;
	TypeOperation typeOperation;

	public HistoMvmType() {
		this.histoMouvementStock = new HistoMouvementStock();
		this.typeOperation = new TypeOperation();
	}

	public HistoMouvementStock getHistoMouvementStock() {
		return histoMouvementStock;
	}

	public void setHistoMouvementStock(HistoMouvementStock histoMouvementStock) {
		this.histoMouvementStock = histoMouvementStock;
	}

	public TypeOperation getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(TypeOperation typeOperation) {
		this.typeOperation = typeOperation;
	}
}
