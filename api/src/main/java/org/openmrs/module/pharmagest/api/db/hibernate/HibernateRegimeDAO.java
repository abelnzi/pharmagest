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
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.api.db.RegimeDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateRegimeDAO implements RegimeDAO {
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
	public void savePharmRegime(PharmRegime pharmRegime) {
		getSessionFactory().getCurrentSession().save(pharmRegime);
		
	}

	@Override
	public void retirePharmRegime(PharmRegime pharmRegime) {
		getSessionFactory().getCurrentSession().delete(pharmRegime);
		
	}

	@Override
	public PharmRegime getPharmRegimeById(Integer pharmRegimeId) {
		return (PharmRegime) getSessionFactory().getCurrentSession().get(PharmRegime.class, pharmRegimeId);
	}

	@Override
	public Collection<PharmRegime> getAllPharmRegimes() {
		return (Collection<PharmRegime>) getSessionFactory().getCurrentSession().createCriteria(PharmRegime.class).list();
	}

	@Override
	public void updatePharmRegime(PharmRegime pharmRegime) {
		getSessionFactory().getCurrentSession().update(pharmRegime);
		
	}

}