package org.openmrs.module.pharmagest.api.db.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmLigneRcId;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.api.db.RapportCommandeDAO;

public class HibernateRapportCommandeDAO implements RapportCommandeDAO {

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
	public void savePharmRapportCommande(PharmRapportCommande pharmRapportCommande) {
		getSessionFactory().getCurrentSession().save(pharmRapportCommande);

	}

	@Override
	public void retirePharmRapportCommande(PharmRapportCommande pharmRapportCommande) {
		getSessionFactory().getCurrentSession().delete(pharmRapportCommande);

	}

	@Override
	public PharmRapportCommande getPharmRapportCommande(PharmProgramme programme) {
		return (PharmRapportCommande) getSessionFactory().getCurrentSession().createCriteria(PharmRapportCommande.class)
				.add(Restrictions.eq("pharmProgramme", programme))
				.addOrder(Order.desc("rapComPeriodDate")).setMaxResults(1).uniqueResult();
	}

	@Override
	public PharmRapportCommande getPharmRapportCommandeById(Integer pharmRapportCommandeId) {
		return (PharmRapportCommande) getSessionFactory().getCurrentSession().get(PharmRapportCommande.class,
				pharmRapportCommandeId);
	}

	@Override
	public Collection<PharmRapportCommande> getPharmRapportCommandeByProgram(PharmProgramme programme) {
		return (Collection<PharmRapportCommande>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmRapportCommande.class).add(Restrictions.eq("pharmProgramme", programme)).list();
	}

	@Override
	public Collection<PharmRapportCommande> getPharmRapportCommandeByPeriod(PharmProgramme programme, Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		String mois;
		if (month < 10) {
			mois = "0" + month;
		} else {
			mois = month + "";
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date minDate = null;
		Date maxDate = null;
		try {
			minDate = df.parse(year + "-" + mois + "-01");
			maxDate = df.parse(year + "-" + mois + "-" + maxDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (Collection<PharmRapportCommande>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmRapportCommande.class).add(Restrictions.eq("pharmProgramme", programme))
				.add(Restrictions.between("rapComPeriodDate", minDate, maxDate)).list();

	}

	@Override
	public Collection<PharmRapportCommande> getAllPharmRapportCommandes() {
		return (Collection<PharmRapportCommande>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmRapportCommande.class).list();
	}

	@Override
	public void updatePharmRapportCommande(PharmRapportCommande pharmRapportCommande) {
		getSessionFactory().getCurrentSession().update(pharmRapportCommande);

	}

	@Override
	public void savePharmLigneRc(PharmLigneRc pharmLigneRc) {
		getSessionFactory().getCurrentSession().save(pharmLigneRc);
	}

	@Override
	public void savePharmLigneRcs(Collection<PharmLigneRc> pharmLigneRcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public PharmLigneRc getPharmLigneRc(PharmLigneRcId pharmLigneRcId) {
		return (PharmLigneRc) getSessionFactory().getCurrentSession().get(PharmLigneRc.class, pharmLigneRcId);
	}

	@Override
	public void updatePharmLigneRc(PharmLigneRc pharmLigneRc) {
		getSessionFactory().getCurrentSession().update(pharmLigneRc);

	}

}
