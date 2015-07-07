package org.openmrs.module.pharmagest.api.db.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.RegimeTest;
import org.openmrs.module.pharmagest.api.db.ParametersDispensationDao;

public class HibernateParametersDispensationDAO implements
		ParametersDispensationDao {

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

	@SuppressWarnings("unchecked")
	public Collection<RegimeTest> getAllRegimes() {
		return (Collection<RegimeTest>) getSessionFactory().getCurrentSession().createQuery("from org.openmrs.module.pharmagest.RegimeTest").list();
	}

	@SuppressWarnings("unchecked")
	public Collection<Medecin> getAllMedecins() {
		return (Collection<Medecin>) getSessionFactory().getCurrentSession()
				.createCriteria(Medecin.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Produit> getAllProduits() {
		return (Collection<Produit>) getSessionFactory().getCurrentSession()
				.createCriteria(Produit.class).list();
	}

}
