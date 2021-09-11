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
import org.openmrs.module.pharmagest.PharmClassePharma;
import org.openmrs.module.pharmagest.api.db.ClassePharmaDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateClassePharmaDAO implements ClassePharmaDAO {
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
	public void savePharmClassePharma(PharmClassePharma pharmClassePharma) {
		getSessionFactory().getCurrentSession().save(pharmClassePharma);
		
	}

	@Override
	public void retirePharmClassePharma(PharmClassePharma pharmClassePharma) {
		getSessionFactory().getCurrentSession().delete(pharmClassePharma);
		
	}

	@Override
	public PharmClassePharma getPharmClassePharmaById(Integer pharmClassePharmaId) {
		return (PharmClassePharma) getSessionFactory().getCurrentSession().get(PharmClassePharma.class, pharmClassePharmaId);
	}

	@Override
	public Collection<PharmClassePharma> getAllPharmClassePharmas() {
		return (Collection<PharmClassePharma>) getSessionFactory().getCurrentSession().createCriteria(PharmClassePharma.class).list();
	}

	@Override
	public void updatePharmClassePharma(PharmClassePharma pharmClassePharma) {
		getSessionFactory().getCurrentSession().update(pharmClassePharma);
		
	}

}