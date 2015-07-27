package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.db.GestionStockDAO;

public class GestionStockServiceImpl extends BaseOpenmrsService implements
		GestionStockService {
	protected final Log log = LogFactory.getLog(this.getClass());

	private GestionStockDAO dao;

	/**
	 * @return the dao
	 */
	public GestionStockDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(GestionStockDAO dao) {
		this.dao = dao;
	}

	@Override
	public void saveStocker(Stocker stocker) {
		getDao().saveStocker(stocker);

	}

	@Override
	public void deleteStocker(Stocker stocker) {
		getDao().retireStocker(stocker);

	}

	@Override
	public Stocker getStockerById(StockerId stockerId) {
		return getDao().getStockerById(stockerId);
	}

	@Override
	public Collection<Stocker> getAllStockers() {

		return getDao().getAllStockers();
	}

	@Override
	public Collection<Stocker> getStockersByProgram(Programme program) {

		return getDao().getStockersByProgram(program);
	}

	@Override
	public Collection<Stocker> getStockersByProduit(Produit produit) {
		return getDao().getStockersByProduit(produit);
	}

	@Override
	public void updateStocker(Stocker stocker) {
		getDao().updateStocker(stocker);

	}

	@Override
	public boolean saveHistoMvmStock(HistoMouvementStock histoMouvementStock) {
		return getDao().saveHistoMvmStock(histoMouvementStock);

	}

	@Override
	public void deleteHistoMvmStock(HistoMouvementStock histoMouvementStock) {
		getDao().retireHistoMvmStock(histoMouvementStock);

	}

	@Override
	public void updateHistoMvmStock(HistoMouvementStock histoMouvementStock) {
		getDao().updateHistoMvmStock(histoMouvementStock);

	}

	@Override
	public Collection<HistoMouvementStock> getAllHistoMvmStocks() {

		return getDao().getAllHistoMvmStocks();
	}

	@Override
	public Collection<HistoMouvementStock> getHistoMvmStocksByProgramId(
			Integer programId) {

		return getDao().getHistoMvmStocksByProgramId(programId);
	}

	@Override
	public Collection<HistoMouvementStock> getHistoMvmStocksByProduit(
			Produit produit) {

		return getDao().getHistoMvmStocksByProduit(produit);
	}

	@Override
	public HistoMouvementStock getHistoMvmStockById(Integer histoMvmStockId) {

		return getDao().getHistoMvmStockById(histoMvmStockId);
	}

}
