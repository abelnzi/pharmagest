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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Obs;
import org.openmrs.module.pharmagest.Inventaire;
import org.openmrs.module.pharmagest.LigneInventaire;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaireId;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.db.InventaireDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateInventaireDAO implements InventaireDAO {
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
	public void saveInventaire(Inventaire inventaire) {
		getSessionFactory().getCurrentSession().save(inventaire);
	}

	@Override
	public void retireInventaire(Inventaire inventaire) {
		getSessionFactory().getCurrentSession().delete(inventaire);

	}

	@Override
	public Inventaire getInventaireById(Integer inventaireId) {
		return (Inventaire) getSessionFactory().getCurrentSession().get(
				Inventaire.class, inventaireId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Inventaire> getAllInventaires() {
		return (Collection<Inventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(Inventaire.class).list();
	}

	@Override
	public void updateInventaire(Inventaire operation) {
		getSessionFactory().getCurrentSession().update(operation);

	}

	@Override
	public void saveLigneInventaire(LigneInventaire ligneInventaire) {
		getSessionFactory().getCurrentSession().save(ligneInventaire);

	}

	@Override
	public void saveLigneInventaires(Collection<LigneInventaire> ligneInventaires) {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePharmInventaire(PharmInventaire pharmInventaire) {
		getSessionFactory().getCurrentSession().save(pharmInventaire);
		
	}

	@Override
	public void retirePharmInventaire(PharmInventaire pharmInventaire) {
		getSessionFactory().getCurrentSession().delete(pharmInventaire);
	}

	@Override
	public PharmInventaire getPharmInventaireById(Integer pharmInventaireId) {
		return (PharmInventaire) getSessionFactory().getCurrentSession().get(
				PharmInventaire.class, pharmInventaireId);
	}
	
	public Collection<PharmInventaire> getPharmInventaireByProgram(PharmProgramme programme) {

		return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme))
				.add(Restrictions.ne("invFlag", "VA")).list();
	}
	@Override
	public Collection<PharmInventaire> getPharmInventaireByPeriod(PharmProgramme programme,Date date,Boolean valid) {
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		//int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String mois ;
		if(month<10){
			mois="0"+month;
		}else{
			mois=month+"";
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date minDate = null;
		Date maxDate = null;
		try {
			minDate = df.parse(year+"-"+mois+"-01");
			maxDate = df.parse(year+"-"+mois+"-"+maxDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		 
		 Boolean testvar =valid;
		 //System.out.println("testvar : "+testvar);
		 //System.out.println("valid : "+testvar);
		 if(testvar==null){
			 return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.between("invDeb", minDate, maxDate)).list();
		 }
		
		 if(valid==true){
			 return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.eq("invFlag","VA"))
						.add(Restrictions.between("invDeb", minDate, maxDate)).list();
		 }else if(valid==false) {
			 return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.ne("invFlag","VA"))
						.add(Restrictions.between("invDeb", minDate, maxDate)).list();
		 }else {
			 return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
						.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme))
						.add(Restrictions.between("invDeb", minDate, maxDate)).list();
		 }
		 
	}
	
	public Collection<PharmInventaire>  getPharmInventairesByPP(PharmProgramme programme , Date dateDebut , Date dateFin,String mode){
		if(mode.equals("VA")){
		return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.eq("invFlag","VA"))
				.add(Restrictions.between("invDeb", dateDebut, dateFin)).list();
		}else if (mode.equals("NV")){
			return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme)).add(Restrictions.ne("invFlag","VA"))
					.add(Restrictions.between("invDeb", dateDebut, dateFin)).list();
		}else{
			return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.between("invDeb", dateDebut, dateFin)).list();
		}
	} 

	@Override
	public Collection<PharmInventaire> getAllPharmInventaires() {
		return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmInventaire.class).list();
	}

	@Override
	public void updatePharmInventaire(PharmInventaire pharmInventaire) {
		getSessionFactory().getCurrentSession().merge(pharmInventaire);

	}

	@Override
	public void savePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire) {
		getSessionFactory().getCurrentSession().save(pharmLigneInventaire);
		
	}
	
	@Override
	public void saveOrUpdatePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire) {
		getSessionFactory().getCurrentSession().saveOrUpdate(pharmLigneInventaire);
		
	}

	@Override
	public void savePharmLigneInventaires(Collection<PharmLigneInventaire> pharmLigneInventaires) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void retirePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire) {
		getSessionFactory().getCurrentSession().delete(pharmLigneInventaire);
	}
	@Override
	public PharmLigneInventaire getPharmLigneInventaire(PharmLigneInventaireId pharmLigneInventaireId) {
		return (PharmLigneInventaire) getSessionFactory().getCurrentSession().get(
				PharmLigneInventaire.class, pharmLigneInventaireId);
		
	}
	@Override
	public void updatePharmLigneInventaire(PharmLigneInventaire pharmLigneInventaire) {
		
		getSessionFactory().getCurrentSession().merge(pharmLigneInventaire);
	}

	
	public Collection<PharmInventaire> getPharmInventaireByPeriod(PharmProgramme programme, Date date) {
		return (Collection<PharmInventaire>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmInventaire.class).add(Restrictions.eq("pharmProgramme", programme))
				.add(Restrictions.eq("invDeb",date)).list();
	}

	
	public PharmInventaire getPharmInventaireForAuthorize(PharmProgramme programme) {
		String sql="SELECT inv_id, program_id, inv_date_crea, max(inv_deb) inv_deb, inv_fin, inv_flag, inv_date_modif, gest_id_saisi, gest_id_modif FROM pharm_inventaire where program_id="+programme.getProgramId()+"  AND inv_flag='VA' ";
		return (PharmInventaire) getSessionFactory().getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(PharmInventaire.class).uniqueResult();		
	}

	@Override
	public void retirePharmLigneInventairesByInventaire(PharmInventaire inventaire) {
		for (Iterator iterator = inventaire.getPharmLigneInventaires().iterator(); iterator.hasNext();) {
			PharmLigneInventaire ligne = (PharmLigneInventaire) iterator.next();
			 getSessionFactory().getCurrentSession().delete(ligne);
			
		}
		
	}
}