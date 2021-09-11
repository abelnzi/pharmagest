package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;
import java.util.Date;

import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.metier.CoutAchatProduit;

public interface GestionReceptionDAO {

	public void savePharmReception(PharmReception pharmReception);

	public void retirePharmReception(PharmReception pharmReception);

	public PharmReception getPharmReceptionById(Integer receptionId);

	public Collection<PharmReception> getPharmReceptionByBL(PharmProgramme programme, String bl);

	public Collection<PharmReception> getAllPharmReceptions();

	public void updatePharmReception(PharmReception pharmReception);

	public PharmLigneReception getPharmLigneReception(PharmLigneReceptionId pharmLigneReceptionId);

	public void updatePharmLigneReception(PharmLigneReception pharmLigneReception);

	public void savePharmLigneReception(PharmLigneReception pharmLigneReception);

	public void savePharmLigneReceptions(Collection<PharmLigneReception> pharmLigneReceptions);

	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date date, String mode);

	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date dateDebut, Date dateFin,
			String mode);

	public void retirePharmLigneReceptionsByReception(PharmReception reception);

	public CoutAchatProduit getCoutProduit(int prodAtId,Date dateCout);

}
