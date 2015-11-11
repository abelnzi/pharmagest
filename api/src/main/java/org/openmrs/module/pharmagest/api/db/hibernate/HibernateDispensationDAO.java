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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmOrdonnance;
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
		return (Ordonnance) getSessionFactory().getCurrentSession().get(Ordonnance.class, ordonnanceId);
	}

	@Override
	public Ordonnance getOrdonnanceByIdentifier(String patientIdentifiant) {
		// TODO Auto-generated method stub

		return (Ordonnance) getSessionFactory().getCurrentSession().createCriteria(Ordonnance.class)
				.add(Restrictions.eq("patientIdentifiant", patientIdentifiant)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Ordonnance> getAllOrdonnances() {
		// TODO Auto-generated method stub
		return (Collection<Ordonnance>) getSessionFactory().getCurrentSession().createCriteria(Ordonnance.class).list();
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
	public void saveLigneDispensations(Collection<LigneDispensation> lignedispensations) {
		// TODO Auto-generated method stub

	}

	public Ordonnance getLastDispensation(PatientComplement patientComplement) {
		return (Ordonnance) getSessionFactory().getCurrentSession().createCriteria(Ordonnance.class)
				.add(Restrictions.eq("patientComplement", patientComplement))
				.setProjection(Projections.max("ordDateDispen")).uniqueResult();

	}

	@Override
	public void savePharmOrdonnance(PharmOrdonnance pharmOrdonnance) {
		getSessionFactory().getCurrentSession().save(pharmOrdonnance);

	}

	@Override
	public void retirePharmOrdonnance(PharmOrdonnance pharmOrdonnance) {
		getSessionFactory().getCurrentSession().delete(pharmOrdonnance);

	}

	@Override
	public PharmOrdonnance getPharmOrdonnanceById(Integer ordonnanceId) {
		return (PharmOrdonnance) getSessionFactory().getCurrentSession().get(PharmOrdonnance.class, ordonnanceId);
	}

	@Override
	public PharmOrdonnance getPharmOrdonnanceByIdentifier(String patientIdentifiant) {
		return (PharmOrdonnance) getSessionFactory().getCurrentSession().createCriteria(PharmOrdonnance.class)
				.add(Restrictions.eq("patientIdentifiant", patientIdentifiant)).uniqueResult();
	}

	@Override
	public Collection<PharmOrdonnance> getAllPharmOrdonnances() {
		return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmOrdonnance.class).list();
	}

	@Override
	public void updatePharmOrdonnance(PharmOrdonnance pharmOrdonnance) {
		getSessionFactory().getCurrentSession().update(pharmOrdonnance);

	}

	@Override
	public void savePharmLigneDispensation(PharmLigneDispensation pharmLignedispensation) {
		getSessionFactory().getCurrentSession().save(pharmLignedispensation);

	}

	@Override
	public void savePharmLigneDispensations(Collection<PharmLigneDispensation> pharmLignedispensations) {
		// TODO Auto-generated method stub

	}

	@Override
	public PharmOrdonnance getLastPharmOrdonnance(PatientIdentifier patientIdentifier) {
		List pharmOrd = getSessionFactory().getCurrentSession().createCriteria(PharmOrdonnance.class)
				.addOrder(Order.desc("ordDateSaisi")).add(Restrictions.eq("patientIdentifier", patientIdentifier))
				.list();
		if (!pharmOrd.isEmpty()) {
			return (PharmOrdonnance) pharmOrd.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PharmLigneDispensation getPharmLigneDispensation(PharmLigneDispensationId pharmLigneDispensationId) {
		return (PharmLigneDispensation) getSessionFactory().getCurrentSession().get(PharmLigneDispensation.class,
				pharmLigneDispensationId);
	}

	@Override
	public void updatePharmLigneDispensation(PharmLigneDispensation pharmLigneDispensation) {
		getSessionFactory().getCurrentSession().update(pharmLigneDispensation);

	}
}