/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pharmagest.api.db.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.PharmStockerId;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.StockerId;
import org.openmrs.module.pharmagest.api.db.GestionStockDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateGestionStockDAO implements GestionStockDAO {
	protected final Log log = LogFactory.getLog(this.getClass());

	private SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void saveStocker(Stocker stocker) {
		getSessionFactory().getCurrentSession().save(stocker);

	}

	@Override
	public void retireStocker(Stocker stocker) {
		getSessionFactory().getCurrentSession().delete(stocker);

	}

	@Override
	public Stocker getStockerById(StockerId stockerId) {
		return (Stocker) getSessionFactory().getCurrentSession().get(Stocker.class, stockerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Stocker> getAllStockers() {
		return (Collection<Stocker>) getSessionFactory().getCurrentSession().createCriteria(Stocker.class).list();
		/*
		 * return (Collection<Stocker>)
		 * getSessionFactory().getCurrentSession().createCriteria(Stocker.class)
		 * .setProjection(Projections.projectionList().add(Projections.property(
		 * "produit").as("produit")) .add(Projections.property("programme"))
		 * .add(Projections.property("stockQte"))
		 * .add(Projections.property("stockDateStock"))
		 * .add(Projections.property("stockDatePeremp"))
		 * .add(Projections.groupProperty("programme")))
		 * .addOrder(Order.desc("stockDateStock")).list();
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Stocker> getStockersByProgram(Programme program) {
		return getSessionFactory().getCurrentSession().createCriteria(Stocker.class)
				.add(Restrictions.eq("programme", program)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Stocker> getStockersByProduit(Produit produit) {
		return getSessionFactory().getCurrentSession().createCriteria(Stocker.class)
				.add(Restrictions.eq("produit", produit)).list();
	}

	@Override
	public void updateStocker(Stocker stocker) {
		getSessionFactory().getCurrentSession().update(stocker);
	}

	@Override
	public boolean saveHistoMvmStock(HistoMouvementStock histoMouvementStock) {
		getSessionFactory().getCurrentSession().save(histoMouvementStock);
		return true;

	}

	@Override
	public void retireHistoMvmStock(HistoMouvementStock histoMouvementStock) {
		getSessionFactory().getCurrentSession().delete(histoMouvementStock);

	}

	@Override
	public void updateHistoMvmStock(HistoMouvementStock histoMouvementStock) {
		getSessionFactory().getCurrentSession().update(histoMouvementStock);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HistoMouvementStock> getAllHistoMvmStocks() {
		return (Collection<HistoMouvementStock>) getSessionFactory().getCurrentSession()
				.createCriteria(HistoMouvementStock.class).addOrder(Order.desc("mvtId")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HistoMouvementStock> getHistoMvmStocksByProgramId(Integer programId) {
		return getSessionFactory().getCurrentSession().createCriteria(HistoMouvementStock.class)
				.add(Restrictions.eq("mvtProgramme", programId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HistoMouvementStock> getHistoMvmStocksByProduit(Produit produit) {
		return getSessionFactory().getCurrentSession().createCriteria(HistoMouvementStock.class)
				.add(Restrictions.eq("produit", produit)).list();
	}

	@Override
	public HistoMouvementStock getHistoMvmStockById(Integer histoMvmStockId) {
		return (HistoMouvementStock) getSessionFactory().getCurrentSession().get(HistoMouvementStock.class,
				histoMvmStockId);
	}

	@Override
	public void savePharmStocker(PharmStocker stocker) {
		getSessionFactory().getCurrentSession().save(stocker);

	}

	@Override
	public void retirePharmStocker(PharmStocker stocker) {
		getSessionFactory().getCurrentSession().delete(stocker);

	}

	@Override
	public PharmStocker getPharmStockerById(PharmStockerId stockerId) {
		return (PharmStocker) getSessionFactory().getCurrentSession().get(PharmStocker.class, stockerId);
	}

	@Override
	public Collection<PharmStocker> getAllPharmStockers() {
		return (Collection<PharmStocker>) getSessionFactory().getCurrentSession().createCriteria(PharmStocker.class)
				.list();
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByProgram(PharmProgramme program) {
		return getSessionFactory().getCurrentSession().createCriteria(PharmStocker.class)
				.add(Restrictions.eq("programme", program)).list();
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByProduit(PharmProduit produit) {
		return getSessionFactory().getCurrentSession().createCriteria(PharmStocker.class)
				.createAlias("pharmProduitAttribut", "produitAttribut")
				.add(Restrictions.eq("produitAttribut.pharmProduit", produit)).list();
	}

	@Override
	public void updatePharmStocker(PharmStocker stocker) {
		getSessionFactory().getCurrentSession().update(stocker);

	}

	@Override
	public boolean savePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock) {
		getSessionFactory().getCurrentSession().save(histoMouvementStock);
		return true;
	}

	@Override
	public void retirePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock) {
		getSessionFactory().getCurrentSession().delete(histoMouvementStock);

	}

	@Override
	public void updatePharmHistoMvmStock(PharmHistoMouvementStock histoMouvementStock) {
		getSessionFactory().getCurrentSession().update(histoMouvementStock);

	}

	@Override
	public Collection<PharmHistoMouvementStock> getAllPharmHistoMvmStocks() {
		return (Collection<PharmHistoMouvementStock>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmHistoMouvementStock.class).list();
	}

	@Override
	public Collection<PharmHistoMouvementStock> getPharmHistoMvmStocksByProgramId(Integer programId) {
		return getSessionFactory().getCurrentSession().createCriteria(PharmHistoMouvementStock.class)
				.add(Restrictions.eq("mvtProgramme", programId)).list();
	}

	@Override
	public Collection<PharmHistoMouvementStock> getPharmHistoMvmStocksByProduit(PharmProduit produit) {
		return getSessionFactory().getCurrentSession().createCriteria(PharmHistoMouvementStock.class)
				.add(Restrictions.eq("pharmProduit", produit)).list();
	}

	@Override
	public PharmHistoMouvementStock getPharmHistoMvmStockById(Integer histoMvmStockId) {
		return (PharmHistoMouvementStock) getSessionFactory().getCurrentSession().get(PharmHistoMouvementStock.class,
				histoMvmStockId);
	}

	@Override
	public Collection<PharmStocker> getPharmStockersByPP(PharmProduit pharmProduit, PharmProgramme pharmProgramme) {
		return getSessionFactory().getCurrentSession().createCriteria(PharmStocker.class)
				.createAlias("pharmProduitAttribut", "produitAttribut")
				.add(Restrictions.eq("produitAttribut.pharmProduit", pharmProduit))
				.add(Restrictions.eq("pharmProgramme", pharmProgramme)).list();
	}

}