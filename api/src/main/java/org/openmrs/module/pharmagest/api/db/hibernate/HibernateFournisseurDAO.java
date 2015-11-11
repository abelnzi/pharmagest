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
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.api.db.FournisseurDAO;
import org.openmrs.module.pharmagest.api.db.OperationDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateFournisseurDAO implements FournisseurDAO {
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
	public void savePharmFournisseur(PharmFournisseur pharmFournisseur) {
		getSessionFactory().getCurrentSession().save(pharmFournisseur);
		
	}

	@Override
	public void retirePharmFournisseur(PharmFournisseur pharmFournisseur) {
		getSessionFactory().getCurrentSession().delete(pharmFournisseur);
		
	}

	@Override
	public PharmFournisseur getPharmFournisseurById(Integer pharmFournisseurId) {
		return (PharmFournisseur) getSessionFactory().getCurrentSession().get(PharmFournisseur.class, pharmFournisseurId);
	}

	@Override
	public Collection<PharmFournisseur> getAllPharmFournisseurs() {
		return (Collection<PharmFournisseur>) getSessionFactory().getCurrentSession().createCriteria(PharmFournisseur.class).list();
	}

	@Override
	public void updatePharmFournisseur(PharmFournisseur pharmFournisseur) {
		getSessionFactory().getCurrentSession().update(pharmFournisseur);
		
	}

}