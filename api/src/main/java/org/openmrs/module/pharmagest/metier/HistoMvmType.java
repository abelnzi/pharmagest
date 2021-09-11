package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmTypeOperation;

public class HistoMvmType implements java.lang.Comparable{

	PharmHistoMouvementStock histoMouvementStock;
	PharmTypeOperation typeOperation;
	PharmProgramme programme;

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
	
	public PharmProgramme getProgramme() {
		return programme;
	}

	public void setProgramme(PharmProgramme programme) {
		this.programme = programme;
	}

	@Override
	public int compareTo(Object o) {
		int id1 = ((HistoMvmType) o).getHistoMouvementStock().getMvtId();
		int id2 = this.getHistoMouvementStock().getMvtId();
	
			if (id1<id2)
				return 1;
			else if (id1>id2)
				return -1;
			else if (id1==id2)
				return 0;
			return 0;
			
	}

	
}
