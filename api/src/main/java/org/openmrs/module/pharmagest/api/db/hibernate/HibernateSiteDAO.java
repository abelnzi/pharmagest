package org.openmrs.module.pharmagest.api.db.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.db.SiteDAO;

public class HibernateSiteDAO implements SiteDAO {

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

	public void savePharmSite(PharmSite pharmSite) {
		getSessionFactory().getCurrentSession().save(pharmSite);
	}

	public void retirePharmSite(PharmSite pharmSite) {
		getSessionFactory().getCurrentSession().delete(pharmSite);
	}

	public PharmSite getPharmSiteById(Integer pharmSiteId) {
		return (PharmSite) getSessionFactory().getCurrentSession().get(PharmSite.class, pharmSiteId);
	}
	
	public PharmSite getPharmSiteByCode(String code) {
		return (PharmSite) getSessionFactory().getCurrentSession().createCriteria(PharmSite.class)
				.add(Restrictions.eq("sitCode", code))
				.uniqueResult();
	}
	
	public PharmSite getPharmSiteByName(String sitLib) {
		return (PharmSite) getSessionFactory().getCurrentSession().createCriteria(PharmSite.class)
				.add(Restrictions.eq("sitLib", sitLib))
				.uniqueResult();
	}

	public Collection<PharmSite> getAllPharmSites() {
		return (Collection<PharmSite>) getSessionFactory().getCurrentSession().createCriteria(PharmSite.class).list();
	}

	public void updatePharmSite(PharmSite pharmSite) {
		getSessionFactory().getCurrentSession().update(pharmSite);
	}

}
