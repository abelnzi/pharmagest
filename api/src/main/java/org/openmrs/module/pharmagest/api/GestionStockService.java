package org.openmrs.module.pharmagest.api;

import java.util.Collection;
import java.util.Date;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GestionStockService extends OpenmrsService {

	public void saveStocker(Stocker stocker);

	public void deleteStocker(Stocker stocker);

	public Stocker getStockerById(StockerId stockerId);

	public Collection<Stocker> getAllStockers();

	public Collection<Stocker> getStockersByProgram(Programme program);

	public Collection<Stocker> getStockersByProduit(Produit produit);

	public void updateStocker(Stocker stocker);

	public boolean saveHistoMvmStock(HistoMouvementStock histoMouvementStock);

	public void deleteHistoMvmStock(HistoMouvementStock histoMouvementStock);

	public void updateHistoMvmStock(HistoMouvementStock histoMouvementStock);

	public Collection<HistoMouvementStock> getAllHistoMvmStocks();

	public Collection<HistoMouvementStock> getHistoMvmStocksByProgramId(Integer programId);

	public Collection<HistoMouvementStock> getHistoMvmStocksByProduit(Produit produit);

	public HistoMouvementStock getHistoMvmStockById(Integer histoMvmStockId);

	public void savePharmStocker(PharmStocker stocker);

	public void deletePharmStocker(PharmStocker stocker);

	public PharmStocker getPharmStockerById(PharmStockerId stockerId);

	public Collection<PharmStocker> getAllPharmStockers();

	public Collection<PharmStocker> getPharmStockersByProgram(PharmProgramme program);

	public Collection<PharmStocker> getPharmStockersByProgram(PharmProgramme program, Date min, Date max);

	public Collection<PharmStocker> getPharmStockersByProduit(PharmProduit produit);

	public PharmStocker getPharmStockersByProduitAttribut(PharmProduitAttribut produitAttribut,PharmProgramme program);

	public void updatePharmStocker(PharmStocker stocker);

	public boolean savePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock);

	public void deletePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock);
	public void deletePharmHistoMvmStockByOperId(Integer operationId);

	public void updatePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock);

	public Collection<PharmHistoMouvementStock> getAllPharmHistoMvmStocks();

	public Collection<PharmHistoMouvementStock> getPharmHistoMvmByPeriod(Date dateDebut, Date dateFin);

	public Collection<PharmHistoMouvementStock> getPharmHistoMvmStocksByProgramId(Integer programId);

	public Collection<PharmHistoMouvementStock> getPharmHistoMvmStocksByProduit(PharmProduit produit);

	public PharmHistoMouvementStock getPharmHistoMvmStockById(Integer histoMvmStockId);

	public Collection<PharmStocker> getPharmStockersByPP(PharmProduit pharmProduit, PharmProgramme pharmProgramme);

	public Collection<PharmStocker> getPharmStockersSorti(PharmProduit pharmProduit,String numLot, PharmProgramme pharmProgramme,boolean var) ;
}
