package org.openmrs.module.pharmagest.api.db.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.api.db.CommandeSiteDAO;
import org.openmrs.module.pharmagest.metier.LigneCommandeSite;

public class HibernateCommandeSiteDAO implements CommandeSiteDAO {

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

	public void savePharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		getSessionFactory().getCurrentSession().save(pharmCommandeSite);
	}

	public void retirePharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		
		Collection<PharmLigneCommandeSite> ligneMvms = pharmCommandeSite.getPharmLigneCommandeSites();
		
		Iterator it= ligneMvms.iterator();
		while (it.hasNext()) {
			PharmLigneCommandeSite ligne= (PharmLigneCommandeSite)it.next();
			getSessionFactory().getCurrentSession().delete(ligne);
		}	
		
		getSessionFactory().getCurrentSession().delete(pharmCommandeSite);
	}

	public PharmCommandeSite getPharmCommandeSiteById(Integer pharmCommandeSiteId) {
		return (PharmCommandeSite) getSessionFactory().getCurrentSession().get(PharmCommandeSite.class,
				pharmCommandeSiteId);
	}

	@Override
	public Collection<PharmCommandeSite> getPharmCommandeSiteBySP(PharmSite site, PharmProgramme programme,String mode) {

		
		
		 
		 if(mode==null){
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme)).list();
			 
		 }
		
		 if(mode.equals("NV")){
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site)).add(Restrictions.eq("comSiteFlag",0))
						.add(Restrictions.eq("pharmProgramme", programme)).list();
			 			 
		 }else if(mode.equals("VA")) {
			 //System.out.println("---------------------jsuis dans le false--------------------------------");
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site)).add(Restrictions.eq("comSiteFlag",1))
						.add(Restrictions.eq("pharmProgramme", programme)).list();
			 
			 
		 }
		 else if(mode.equals("TR")) {
			 //System.out.println("---------------------jsuis dans le false--------------------------------");
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site)).add(Restrictions.eq("comSiteFlag",2))
						.add(Restrictions.eq("pharmProgramme", programme)).list();
			 
			 
		 }
		 else {
			
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme)).list();
		 }
		 
	}

	@Override
	public Collection<PharmCommandeSite> getPharmCommandeSiteByPeriod(PharmSite site, PharmProgramme programme,
			Date date,String mode) {

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

		// System.out.println("Date :"+year+"-"+mois+"-01");
		
		
		 
		 if(mode==null){
			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme))
						//.add(Restrictions.ne("comSitePeriodLib", "urgent"))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }
		
		 if(mode.equals("NV")){
						 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.eq("comSiteFlag",0))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
			 
		 }else if(mode.equals("VA")) {
			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.eq("comSiteFlag",1))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }else if(mode.equals("TR")) {
			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.eq("comSiteFlag",2))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }else if(mode.equals("UR")) {			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.eq("comSitePeriodLib", "urgent"))
						.add(Restrictions.eq("comSitePeriodDate", date)).list();
		 }else if(mode.equals("NUR")) {	
			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class).add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("comSitePeriodLib"),Restrictions.ne("comSitePeriodLib", "urgent") ),
								Restrictions.isNull("comSitePeriodLib")) )
						//.add(Restrictions.ne("comSitePeriodLib", "urgent"))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }				 
		 else {
			
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmSite", site))
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }

		
	}

	public Collection<PharmCommandeSite> getPharmCommandeByPeriod(PharmProgramme programme, Date date,String mode) {

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

		//System.out.println("-------------maxDate : "+df.format(maxDate));
		//System.out.println("-------------programme : "+programme.getProgramLib());
		
 if(mode==null){
			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }
		
		 if(mode.equals("NV")){
						 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.eq("comSiteFlag",0))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
			 
		 }else if(mode.equals("VA")) {
			 //System.out.println("---------------------jsuis dans le false--------------------------------");
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.eq("comSiteFlag",1))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }else if(mode.equals("TR")) {
			 //System.out.println("---------------------jsuis dans le false--------------------------------");
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.eq("comSiteFlag",2))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }else if(mode.equals("VATR")){
			 //System.out.println("---------------------jsuis dans le VATR--------------------------------");
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.ne("comSiteFlag",0))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }else if(mode.equals("VATR_NUR")) {	
			 //System.out.println("---------------------jsuis dans le VATR_NUR--------------------------------");
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.ne("comSiteFlag",0))
						.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("comSitePeriodLib"),Restrictions.ne("comSitePeriodLib", "urgent") ),
								Restrictions.isNull("comSitePeriodLib")) )
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }	else if(mode.equals("VATR_NUR_Prompt")) {	
			 //System.out.println("---------------------date--------------------------------"+df.format(date));
			 //System.out.println("---------------------minDate--------------------------------"+df.format(minDate));
			 //System.out.println("---------------------maxDate--------------------------------"+df.format(maxDate));
			 
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.ne("comSiteFlag",0))
						.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("comSitePeriodLib"),Restrictions.ne("comSitePeriodLib", "urgent") ),
								Restrictions.isNull("comSitePeriodLib")) )
						.add(Restrictions.between("comSiteDateCom", minDate, maxDate))
						//.add(Restrictions.le("comSiteDateCom", date))
						.list();
		 }	
		 
		 else {
			
			 return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmCommandeSite.class)
						.add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.between("comSitePeriodDate", minDate, maxDate)).list();
		 }

		
	}
	
	public Collection<PharmCommandeSite> getPharmCommandeByPeriod(PharmProgramme programme, Date dateDebut,Date dateFin,String mode){
		
		return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmCommandeSite.class)
				.add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.ne("comSiteFlag",0))
				.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("comSitePeriodLib"),Restrictions.ne("comSitePeriodLib", "urgent") ),
						Restrictions.isNull("comSitePeriodLib")) )
				.add(Restrictions.between("comSitePeriodDate", dateDebut, dateFin))
				//.add(Restrictions.le("comSiteDateCom", date))
				.list();
		
	} 
	
	public Collection<PharmLigneCommandeSite> getPharmLigneCommandeByParams(PharmProgramme programme, Date dateDebut,Date dateFin){
		
		return (Collection<PharmLigneCommandeSite>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmLigneCommandeSite.class)
				.createAlias("pharmCommandeSite","commandeSite")
				.add(Restrictions.between("commandeSite.comSitePeriodDate", dateDebut, dateFin))
				.add(Restrictions.eq("commandeSite.pharmProgramme", programme)).add(Restrictions.ne("commandeSite.comSiteFlag",0))
				.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("commandeSite.comSitePeriodLib"),Restrictions.ne("commandeSite.comSitePeriodLib", "urgent") ),
						Restrictions.isNull("commandeSite.comSitePeriodLib")) )
				.add(Restrictions.between("commandeSite.comSitePeriodDate", dateDebut, dateFin))
				//.add(Restrictions.and(Restrictions.isNotNull("lgnComStockDispo"),Restrictions.ne("lgnComStockDispo", 0) ) )
				.list();
		
	} 

	public Collection<PharmCommandeSite> getAllPharmCommandeSites() {
		return (Collection<PharmCommandeSite>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmCommandeSite.class).list();
	}

	public void updatePharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		getSessionFactory().getCurrentSession().merge(pharmCommandeSite);
	}

	public void savePharmLigneCommandeSite(PharmLigneCommandeSite pharmLignedispensation) {
		getSessionFactory().getCurrentSession().save(pharmLignedispensation);
	}

	public void savePharmLigneCommandeSites(Collection<PharmLigneCommandeSite> pharmLignedispensations) {
	}

	public PharmLigneCommandeSite getPharmLigneCommandeSite(PharmLigneCommandeSiteId pharmLigneCommandeSiteId) {
		return (PharmLigneCommandeSite) getSessionFactory().getCurrentSession().get(PharmLigneCommandeSite.class,
				pharmLigneCommandeSiteId);
	}

	public void updatePharmLigneCommandeSite(PharmLigneCommandeSite pharmLigneCommandeSite) {
		getSessionFactory().getCurrentSession().update(pharmLigneCommandeSite);
	}

	@Override
	public void retirePharmLigneCommandeSite(PharmLigneCommandeSite pharmLigneCommandeSite) {
		getSessionFactory().getCurrentSession().delete(pharmLigneCommandeSite);
		
	}

	@Override
	public Collection<PharmLigneCommandeSite> getProduitRupture(Date dateDebut, Date dateFin) {
		 return (Collection<PharmLigneCommandeSite>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmLigneCommandeSite.class)
					.createAlias("pharmCommandeSite","commandeSite")
					.add(Restrictions.between("commandeSite.comSitePeriodDate", dateDebut, dateFin))
					.add(Restrictions.ne("commandeSite.comSiteFlag",0))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("commandeSite.comSitePeriodLib"),Restrictions.ne("commandeSite.comSitePeriodLib", "urgent") ),
							Restrictions.isNull("commandeSite.comSitePeriodLib")) )
					.add(Restrictions.isNotNull("lgnComNbreJrsRup"))
					.add(Restrictions.ne("lgnComNbreJrsRup", 0))
					//.add(Restrictions.and(Restrictions.isNotNull("lgnComNbreJrsRup"),Restrictions.ne("lgnComNbreJrsRup", 0) ) )					
					.list();
	}

}
