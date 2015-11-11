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
import org.openmrs.module.pharmagest.Inventaire;
import org.openmrs.module.pharmagest.LigneInventaire;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.api.db.InventaireDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateInventaireDAO implements InventaireDAO {
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
	public void saveInventaire(Inventaire inventaire) {
		getSessionFactory().getCurrentSession().save(inventaire);
	}

	@Override
	public void retireInventaire(Inventaire inventaire) {
		getSessionFactory().getCurrentSession().delete(inventaire);

	}

	@Override
	public Inventaire getInventaireById(Integer inventaireId) {
		return (Inventaire) getSessionFactory().getCurrentSession().get(
				Inventaire.class, inventaireId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Inventaire> getAllInventaires() {
		return (Collection<Inventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(Inventaire.class).list();
	}

	@Override
	public void updateInventaire(Inventaire operation) {
		getSessionFactory().getCurrentSession().update(operation);

	}

	@Override
	public void saveLigneInventaire(LigneInventaire ligneInventaire) {
		getSessionFactory().getCurrentSession().save(ligneInventaire);

	}

	@Override
	public void saveLigneInventaires(Collection<LigneInventaire> ligneInventaires) {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePharmInventaire(PharmInventaire pharmInventaire) {
		getSessionFactory().getCurrentSession().save(pharmInventaire);
		
	}

	@Override
	public void retirePharmInventaire(PharmInventaire pharmInventaire) {
		getSessionFactory().getCurrentSession().delete(pharmInventaire);
	}

	@Override
	public PharmInventaire getPharmInventaireById(Integer pharmInventaireId) {
		return (PharmInventaire) getSessionFactory().getCurrentSession().get(
				PharmInventaire.class, pharmInventaireId);
	}

	@Override
	public Collection<PharmInventaire> getAllPharmInventaires() {
		return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmInventaire.class).list();
	}

	@Override
	public void updatePharmInventaire(PharmInventaire pharmInventaire) {
		getSessionFactory().getCurrentSession().update(pharmInventaire);

	}

	@Override
	public void savePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire) {
		getSessionFactory().getCurrentSession().save(pharmLigneInventaire);
		
	}

	@Override
	public void savePharmLigneInventaires(Collection<PharmLigneInventaire> pharmLigneInventaires) {
		// TODO Auto-generated method stub
		
	}
}