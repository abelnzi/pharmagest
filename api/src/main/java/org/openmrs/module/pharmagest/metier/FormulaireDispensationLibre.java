package org.openmrs.module.pharmagest.metier;

import java.util.Collection;
import java.util.Date;

import org.openmrs.module.pharmagest.PharmOrdonnance;

public class FormulaireDispensationLibre {
	private Date dateDebut;
	private Date dateFin;
	private Collection<PharmOrdonnance> listDispensation ;
	

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Collection<PharmOrdonnance> getListDispensation() {
		return listDispensation;
	}

	public void setListDispensation(Collection<PharmOrdonnance> listDispensation) {
		this.listDispensation = listDispensation;
	}

	
}
