package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmTypeOperation;

public class HistoMvmType implements java.lang.Comparable{

	PharmHistoMouvementStock histoMouvementStock;
	PharmTypeOperation typeOperation;

	public HistoMvmType() {
		this.histoMouvementStock = new PharmHistoMouvementStock();
		this.typeOperation = new PharmTypeOperation();
	}

	public PharmHistoMouvementStock getHistoMouvementStock() {
		return histoMouvementStock;
	}

	public void setHistoMouvementStock(PharmHistoMouvementStock histoMouvementStock) {
		this.histoMouvementStock = histoMouvementStock;
	}

	public PharmTypeOperation getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(PharmTypeOperation typeOperation) {
		this.typeOperation = typeOperation;
	}
	
	@Override
	public int compareTo(Object o) {
		int datePerem1 = ((HistoMvmType) o).getHistoMouvementStock().getMvtId();
		int datePerem2 = this.getHistoMouvementStock().getMvtId();
	
			if (datePerem1<datePerem2)
				return 1;
			else if (datePerem1>datePerem2)
				return -1;
			else if (datePerem1==datePerem2)
				return 0;
			return 0;
			
	}

	
}
