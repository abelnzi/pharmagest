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
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.api.db.OrdonnanceDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateDispensationDAO implements OrdonnanceDAO {
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
	public void saveOrdonnance(Ordonnance ordonnance) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().save(ordonnance);
	}

	@Override
	public void retireOrdonnance(Ordonnance ordonnance) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().delete(ordonnance);
	}

	@Override
	public Ordonnance getOrdonnanceById(Integer ordonnanceId) {
		// TODO Auto-generated method stub
		return (Ordonnance) getSessionFactory().getCurrentSession().get(
				Ordonnance.class, ordonnanceId);
	}

	@Override
	public Ordonnance getOrdonnanceByIdentifier(String patientIdentifiant) {
		// TODO Auto-generated method stub

		return (Ordonnance) getSessionFactory().getCurrentSession()
				.createCriteria(Ordonnance.class)
				.add(Restrictions.eq("patientIdentifiant", patientIdentifiant))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Ordonnance> getAllOrdonnances() {
		// TODO Auto-generated method stub
		return (Collection<Ordonnance>) getSessionFactory().getCurrentSession()
				.createCriteria(Ordonnance.class).list();
	}

	@Override
	public void updateOrdonnance(Ordonnance ordonnance) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().update(ordonnance);
	}

	@Override
	public void saveLigneDispensation(LigneDispensation lignedispensation) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().save(lignedispensation);
	}

	@Override
	public void saveLigneDispensations(
			Collection<LigneDispensation> lignedispensations) {
		// TODO Auto-generated method stub

	}
}