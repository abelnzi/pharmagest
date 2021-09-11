package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Date;

import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.metier.CoutAchatProduit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GestionReceptionService {

	public void savePharmReception(PharmReception pharmReception);

	public void deletePharmReception(PharmReception pharmReception);

	public PharmReception getPharmReceptionById(Integer receptionId);

	public Collection<PharmReception> getPharmReceptionByBL(PharmProgramme programme, String bl);

	public Collection<PharmReception> getAllPharmReceptions();

	public void updatePharmReception(PharmReception pharmReception);

	public void savePharmLigneReception(PharmLigneReception pharmLigneReception);

	public void savePharmLigneReceptions(Collection<PharmLigneReception> pharmLigneReceptions);

	public PharmLigneReception getPharmLigneReception(PharmLigneReceptionId pharmLigneReceptionId);

	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date date, String mode);

	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date dateDebut, Date dateFin,
			String mode);

	public void deletePharmLigneReceptionsByReception(PharmReception reception);

	public void updatePharmLigneReception(PharmLigneReception pharmLigneReception);

	public CoutAchatProduit getCoutProduit(int prodAtId, Date dateCout);

}
