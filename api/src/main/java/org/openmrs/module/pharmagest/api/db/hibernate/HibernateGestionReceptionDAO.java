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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.openmrs.Encounter;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneOperation;
import org.openmrs.module.pharmagest.PharmLigneReception;
import org.openmrs.module.pharmagest.PharmLigneReceptionId;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmReception;
import org.openmrs.module.pharmagest.api.db.GestionReceptionDAO;
import org.openmrs.module.pharmagest.api.db.pharmagestDAO;
import org.openmrs.module.pharmagest.metier.CoutAchatProduit;

/**
 * It is a default implementation of {@link pharmagestDAO}.
 */

public class HibernateGestionReceptionDAO implements GestionReceptionDAO {
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

	public void savePharmReception(PharmReception pharmReception) {
		getSessionFactory().getCurrentSession().save(pharmReception);

	}

	public void retirePharmReception(PharmReception pharmReception) {
		getSessionFactory().getCurrentSession().delete(pharmReception);

	}

	public PharmReception getPharmReceptionById(Integer receptionId) {

		return (PharmReception) getSessionFactory().getCurrentSession().get(PharmReception.class, receptionId);
	}
	
	
	public Collection<PharmReception> getPharmReceptionByBL(PharmProgramme programme, String bl) {
		return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
				.add(Restrictions.eq("recptNum", bl))
				.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.eq("recptFlag", "VA") ),
						Restrictions.isNull("recptFlag")) ).list();
	}

	@SuppressWarnings("unchecked")
	public Collection<PharmReception> getAllPharmReceptions() {

		return (Collection<PharmReception>) getSessionFactory().getCurrentSession().createCriteria(PharmReception.class)
				.list();
	}

	public void updatePharmReception(PharmReception pharmReception) {
		getSessionFactory().getCurrentSession().update(pharmReception);

	}

	public PharmLigneReception getPharmLigneReception(PharmLigneReceptionId pharmLigneReceptionId) {

		return (PharmLigneReception) getSessionFactory().getCurrentSession().get(PharmLigneReception.class,
				pharmLigneReceptionId);
	}

	public void updatePharmLigneReception(PharmLigneReception pharmLigneReception) {
		getSessionFactory().getCurrentSession().merge(pharmLigneReception);

	}

	public void savePharmLigneReception(PharmLigneReception pharmLigneReception) {
		getSessionFactory().getCurrentSession().save(pharmLigneReception);

	}

	@Override
	public void savePharmLigneReceptions(Collection<PharmLigneReception> pharmLigneReceptions) {
		// TODO Auto-generated method stub

	}

	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date date, String mode) {
		
		if(mode.equals("NV")&& date== null ){
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.ne("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) ).list();
		}
		if (mode.equals("NV") && date!= null) {
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.ne("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) )
					.add(Restrictions.eq("recptDateRecept", date)).list();
		} else if(mode.equals("VA")&& date==null) {
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.eq("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) ).list();
		}else if(mode.equals("VA")&& date!=null){
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.eq("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) )
					.add(Restrictions.eq("recptDateRecept", date)).list();
		}
		
		else {
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.ne("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) ).list();
		}
	}
	
	public Collection<PharmReception> getPharmReceptionsByAttri(PharmProgramme programme, Date dateDebut,Date dateFin, String mode) {
		
		if(mode.equals("NV")&& dateDebut== null && dateFin== null ){
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.ne("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) ).list();
		}
		if (mode.equals("NV") && dateDebut!= null && dateFin!= null) {
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.ne("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) )
					.add(Restrictions.between("recptDateRecept", dateDebut, dateFin)).list();
		} else if(mode.equals("VA")&& dateDebut== null && dateFin== null) {
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.eq("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) ).list();
		}else if(mode.equals("VA")&& dateDebut!= null && dateFin!= null){
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.eq("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) )
					.add(Restrictions.between("recptDateRecept", dateDebut, dateFin)).list();
		}
		
		else {
			
			return (Collection<PharmReception>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmReception.class).add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("recptFlag"),Restrictions.ne("recptFlag", "VA") ),
							Restrictions.isNull("recptFlag")) ).list();
		}
	}

	
	public void retirePharmLigneReceptionsByReception(PharmReception reception) {
		
		 /*for (Iterator iterator = reception.getPharmLigneReceptions().iterator(); iterator.hasNext();) {
			 PharmLigneReception ligne = (PharmLigneReception) iterator.next();
			 System.out.println("------------ligne--------------"+ligne.getPharmProduitAttribut().getPharmProduit().getFullName());
			 getSessionFactory().getCurrentSession().delete(ligne);
			
		}*/
		 
		 String sql="delete from pharm_ligne_reception where recpt_id="+reception.getRecptId();
		 getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public CoutAchatProduit getCoutProduit(int prodAtId, Date dateCout) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		CoutAchatProduit coutAchatProduit=new CoutAchatProduit();
		
		if(dateCout==null) {
			String sql1="SELECT l.prod_attri_id ,l.lgn_recpt_prix prix, r.recpt_date_recept dateN FROM pharm_reception r,pharm_ligne_reception l " + 
					"  WHERE " + 
					"  r.recpt_id=l.recpt_id " + 
					"  AND r.recpt_date_recept=( " + 
					"    SELECT MAX(recpt_date_recept) FROM pharm_reception r1,pharm_ligne_reception l1 " + 
					"    WHERE r1.recpt_id=l1.recpt_id AND l1.prod_attri_id=" + prodAtId+
					"  ) " + 
					" AND r.recpt_flag='VA' "+
					"  AND l.prod_attri_id="+prodAtId ;
			
			Object[] row = (Object[]) getSessionFactory().getCurrentSession()
					 .createSQLQuery(sql1)
					 .addScalar("prod_attri_id",IntegerType.INSTANCE)
					 .addScalar("prix",IntegerType.INSTANCE)
					 .addScalar("dateN",DateType.INSTANCE)
					 .setMaxResults(1).uniqueResult();
			if(row!=null) {
				coutAchatProduit.setProdAtId(new Integer(row[0].toString()));
				coutAchatProduit.setPrixAchat(new Integer(row[1].toString()));
				
			
			try {
				coutAchatProduit.setDateCout(format.parse(row[2].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
			
		}else {
			
			String sql2="SELECT l.prod_attri_id ,l.lgn_recpt_prix prix, r.recpt_date_recept dateN FROM pharm_reception r,pharm_ligne_reception l " + 
					"  WHERE " + 
					"  r.recpt_id=l.recpt_id " + 
					"  AND r.recpt_date_recept=( " + 
					"    SELECT MAX(r1.recpt_date_recept) FROM pharm_reception r1,pharm_ligne_reception l1 " + 
					"    WHERE r1.recpt_id=l1.recpt_id AND l1.prod_attri_id="+prodAtId+" AND r1.recpt_date_recept <'" +format.format(dateCout)+
					"'  ) " + 
					" AND r.recpt_flag='VA' "+
					"  AND l.prod_attri_id="+prodAtId;
			
			Object[] row = (Object[]) getSessionFactory().getCurrentSession()
					 .createSQLQuery(sql2)
					 .addScalar("prod_attri_id",IntegerType.INSTANCE)
					 .addScalar("prix",IntegerType.INSTANCE)
					 .addScalar("dateN",DateType.INSTANCE)
					 .setMaxResults(1).uniqueResult();
			if(row!=null) {
			coutAchatProduit.setProdAtId(new Integer(row[0].toString()));
			coutAchatProduit.setPrixAchat(new Integer(row[1].toString()));
			
			
			try {
				coutAchatProduit.setDateCout(format.parse(row[2].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 }
		}
		
		return coutAchatProduit;
	}

	

}