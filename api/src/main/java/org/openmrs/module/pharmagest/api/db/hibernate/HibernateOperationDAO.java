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
import org.openmrs.module.pharmagest.LingeOperation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.api.db.OperationDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateOperationDAO implements OperationDAO {
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
	public void saveOperation(Operation operation) {
		getSessionFactory().getCurrentSession().save(operation);
	}

	@Override
	public void retireOperation(Operation operation) {
		getSessionFactory().getCurrentSession().delete(operation);

	}

	@Override
	public Operation getOperationById(Integer operationId) {
		return (Operation) getSessionFactory().getCurrentSession().get(
				Operation.class, operationId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Operation> getAllOperations() {
		return (Collection<Operation>) getSessionFactory().getCurrentSession()
				.createCriteria(Operation.class).list();
	}

	@Override
	public void updateOperation(Operation operation) {
		getSessionFactory().getCurrentSession().update(operation);

	}

	@Override
	public void saveLigneOperation(LingeOperation ligneOperation) {
		getSessionFactory().getCurrentSession().save(ligneOperation);

	}

	@Override
	public void saveLigneOperations(Collection<LingeOperation> ligneOperations) {
		// TODO Auto-generated method stub

	}
}