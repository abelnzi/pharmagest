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
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.api.db.ProgrammeDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateProgrammeDAO implements ProgrammeDAO {
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
	public void savePharmProgramme(PharmProgramme pharmProgramme) {
		getSessionFactory().getCurrentSession().save(pharmProgramme);
		
	}

	@Override
	public void retirePharmProgramme(PharmProgramme pharmProgramme) {
		getSessionFactory().getCurrentSession().delete(pharmProgramme);
		
	}

	@Override
	public PharmProgramme getPharmProgrammeById(Integer pharmProgrammeId) {
		return (PharmProgramme) getSessionFactory().getCurrentSession().get(PharmProgramme.class, pharmProgrammeId);
	}

	@Override
	public Collection<PharmProgramme> getAllPharmProgrammes() {
		return (Collection<PharmProgramme>) getSessionFactory().getCurrentSession().createCriteria(PharmProgramme.class).list();
	}

	@Override
	public void updatePharmProgramme(PharmProgramme pharmProgramme) {
		getSessionFactory().getCurrentSession().update(pharmProgramme);
		
	}

}