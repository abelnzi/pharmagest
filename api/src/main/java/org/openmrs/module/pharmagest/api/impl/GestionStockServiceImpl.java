package org.openmrs.module.pharmagest.api.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
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
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.db.GestionStockDAO;

public class GestionStockServiceImpl extends BaseOpenmrsService implements GestionStockService {
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
	public Collection<HistoMouvementStock> getHistoMvmStocksByProgramId(Integer programId) {

		return getDao().getHistoMvmStocksByProgramId(programId);
	}

	@Override
	public Collection<HistoMouvementStock> getHistoMvmStocksByProduit(Produit produit) {

		return getDao().getHistoMvmStocksByProduit(produit);
	}

	@Override
	public HistoMouvementStock getHistoMvmStockById(Integer histoMvmStockId) {

		return getDao().getHistoMvmStockById(histoMvmStockId);
	}

	@Override
	public void savePharmStocker(PharmStocker stocker) {
		getDao().savePharmStocker(stocker);

	}

	@Override
	public void deletePharmStocker(PharmStocker stocker) {
		getDao().retirePharmStocker(stocker);

	}

	@Override
	public PharmStocker getPharmStockerById(PharmStockerId stockerId) {

		return getDao().getPharmStockerById(stockerId);
	}

	@Override
	public Collection<PharmStocker> getAllPharmStockers() {
		// TODO Auto-generated method stub
		return getDao().getAllPharmStockers();
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByProgram(PharmProgramme program) {
		// TODO Auto-generated method stub
		return getDao().getPharmStockersByProgram(program);
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByProgram(PharmProgramme program, Date min, Date max) {
		// TODO Auto-generated method stub
		return getDao().getPharmStockersByProgram(program, min, max);
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByProduit(PharmProduit produit) {
		// TODO Auto-generated method stub
		return getDao().getPharmStockersByProduit(produit);
	}
	
	public PharmStocker getPharmStockersByProduitAttribut(PharmProduitAttribut produitAttribut,PharmProgramme program){
		return getDao().getPharmStockersByProduitAttribut(produitAttribut,program);
	}

	@Override
	public void updatePharmStocker(PharmStocker stocker) {
		// TODO Auto-generated method stub
		getDao().updatePharmStocker(stocker);

	}

	@Override
	public boolean savePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock) {
		// TODO Auto-generated method stub
		return getDao().savePharmHistoMvmStock(histoMouvementStock);
	}

	@Override
	public void deletePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock) {
		getDao().retirePharmHistoMvmStock(histoMouvementStock);

	}

	@Override
	public void updatePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock) {
		getDao().updatePharmHistoMvmStock(histoMouvementStock);

	}

	@Override
	public Collection<PharmHistoMouvementStock> getAllPharmHistoMvmStocks() {
		// TODO Auto-generated method stub
		return getDao().getAllPharmHistoMvmStocks();
	}

	@Override
	public Collection<PharmHistoMouvementStock> getPharmHistoMvmStocksByProgramId(Integer programId) {
		// TODO Auto-generated method stub
		return getDao().getPharmHistoMvmStocksByProgramId(programId);
	}

	@Override
	public Collection<PharmHistoMouvementStock> getPharmHistoMvmStocksByProduit(PharmProduit produit) {
		// TODO Auto-generated method stub
		return getDao().getPharmHistoMvmStocksByProduit(produit);
	}

	@Override
	public PharmHistoMouvementStock getPharmHistoMvmStockById(Integer histoMvmStockId) {
		// TODO Auto-generated method stub
		return getDao().getPharmHistoMvmStockById(histoMvmStockId);
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByPP(PharmProduit pharmProduit, PharmProgramme pharmProgramme) {

		return getDao().getPharmStockersByPP(pharmProduit, pharmProgramme);
	}

	@Override
	public Collection<PharmHistoMouvementStock> getPharmHistoMvmByPeriod(Date dateDebut, Date dateFin) {
		
		return getDao().getPharmHistoMvmByPeriod(dateDebut, dateFin);
	}

	@Override
	public Collection<PharmStocker> getPharmStockersSorti(PharmProduit pharmProduit,String numLot, PharmProgramme pharmProgramme,boolean var)  {
		
		return getDao().getPharmStockersSorti(pharmProduit, numLot, pharmProgramme, var);
	}

	
	public void deletePharmHistoMvmStockByOperId(Integer operationId) {
		
		getDao().retirePharmHistoMvmStockByOperId(operationId);
	}

}
