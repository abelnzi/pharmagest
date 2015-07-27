package org.openmrs.module.pharmagest.api.db;

import java.util.Collection;

import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;

public interface GestionStockDAO {

	public void saveStocker(Stocker stocker);

	public void retireStocker(Stocker stocker);

	public Stocker getStockerById(StockerId stockerId);

	public Collection<Stocker> getAllStockers();

	public Collection<Stocker> getStockersByProgram(Programme program);

	public Collection<Stocker> getStockersByProduit(Produit produit);

	public void updateStocker(Stocker stocker);

	public boolean saveHistoMvmStock(HistoMouvementStock histoMouvementStock);

	public void retireHistoMvmStock(HistoMouvementStock histoMouvementStock);

	public void updateHistoMvmStock(HistoMouvementStock histoMouvementStock);

	public Collection<HistoMouvementStock> getAllHistoMvmStocks();

	public Collection<HistoMouvementStock> getHistoMvmStocksByProgramId(Integer programId);

	public Collection<HistoMouvementStock> getHistoMvmStocksByProduit(Produit produit);

	public HistoMouvementStock getHistoMvmStockById(Integer histoMvmStockId);

}
