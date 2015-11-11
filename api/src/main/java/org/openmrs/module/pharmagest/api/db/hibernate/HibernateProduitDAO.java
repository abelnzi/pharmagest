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
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.api.db.ProduitDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateProduitDAO implements ProduitDAO {
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
	public void savePharmProduit(PharmProduit pharmProduit) {
		getSessionFactory().getCurrentSession().save(pharmProduit);
		
	}

	@Override
	public void retirePharmProduit(PharmProduit pharmProduit) {
		getSessionFactory().getCurrentSession().delete(pharmProduit);
		
	}

	@Override
	public PharmProduit getPharmProduitById(Integer pharmProduitId) {
		return (PharmProduit) getSessionFactory().getCurrentSession().get(PharmProduit.class, pharmProduitId);
	}

	@Override
	public Collection<PharmProduit> getAllPharmProduits() {
		return (Collection<PharmProduit>) getSessionFactory().getCurrentSession().createCriteria(PharmProduit.class).list();
	}

	@Override
	public void updatePharmProduit(PharmProduit pharmProduit) {
		getSessionFactory().getCurrentSession().update(pharmProduit);
		
	}

}