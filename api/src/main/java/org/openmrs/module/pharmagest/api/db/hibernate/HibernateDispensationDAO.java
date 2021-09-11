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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.LigneDispensation;
import org.openmrs.module.pharmagest.Operation;
import org.openmrs.module.pharmagest.Ordonnance;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmAssurance;
import org.openmrs.module.pharmagest.PharmClient;
import org.openmrs.module.pharmagest.PharmHistoMouvementStock;
import org.openmrs.module.pharmagest.PharmLigneAssurance;
import org.openmrs.module.pharmagest.PharmLigneAssuranceId;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmLigneDispensationId;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
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
				.add(Restrictions.eq("patientIdentifiant", patientIdentifiant))
				.setProjection(Projections.max("ordDateDispen")).uniqueResult();
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
	public PharmOrdonnance getPharmOrdonnanceByIdentifier(PatientIdentifier patientIdentifiant) {
		return (PharmOrdonnance) getSessionFactory().getCurrentSession().createCriteria(PharmOrdonnance.class)
				.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("ordFlag"),Restrictions.ne("ordFlag", "AN") ),
						Restrictions.isNull("ordFlag")) )
				.add(Restrictions.eq("patientIdentifier", patientIdentifiant))
				.createAlias("pharmRegime", "pharmRegime")
				.add(Restrictions.not( Restrictions.in("pharmRegime.regimId", new Integer[] {32,33,36})) )
				.addOrder(Order.desc("ordDateDispen"))				
				.setMaxResults(1).uniqueResult();
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

	public Encounter getLastEncounter(Integer patientId, Date dateDispens) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		String sql= "SELECT * FROM encounter e WHERE encounter_datetime="+""
				 + "(SELECT MAX(e2.encounter_datetime) FROM encounter e2"+" "
				 + "WHERE e2.encounter_type=2 and e2.patient_id="+patientId+" "
				 		+ "and e2.encounter_datetime<='"+sf.format(dateDispens)+"') "
				 + "and e.patient_id="+patientId
				 +" and e.encounter_type=2 order by encounter_id desc";
		
		//System.out.println("--------------------sql----------------------- :  "+sql);
		
		return (Encounter) getSessionFactory().getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(Encounter.class).setMaxResults(1).uniqueResult();
	}
	
	public Obs getObs(int encounterId , int conceptId, int personId){
		String sql="SELECT o.* FROM obs o  WHERE obs_id ="+"(SELECT   Max(o.obs_id) FROM obs o  WHERE o.encounter_id ="+encounterId+" and o.concept_id="+conceptId+
				" and o.person_id="+personId+")" ;
		//System.out.println("============sql============"+sql);
		return (Obs) getSessionFactory().getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(Obs.class).uniqueResult();
	}
	
	public void deleteObsByAtt(int personId, int conceptId, Date obsDate ){		
		String sql="DELETE FROM obs WHERE person_id="+personId+" AND obs_datetime ='"+obsDate+"' AND concept_id="+conceptId ;
		//System.out.println("===========sql============="+sql);
		getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public Date getEncounterByIdentifier(int type,String identifier){
		String sql="SELECT   Max(e.encounter_datetime) FROM  encounter e , patient_identifier pi WHERE  e.patient_id=pi.patient_id "
				+ " AND e.encounter_type="+type+" AND e.voided=0 AND pi.identifier='"+identifier+"'";
		return  (Date) getSessionFactory().getCurrentSession()
				.createSQLQuery(sql).uniqueResult();
	}

	@Override
	public PharmOrdonnance findOrdonnanceByAttribut(PatientIdentifier patientIdentifiant, PharmRegime pharmRegime, Date ordDateDispen, PharmProgramme pharmProgramme,
			PharmMedecin pharmMedecin) {
		return (PharmOrdonnance) getSessionFactory().getCurrentSession().createCriteria(PharmOrdonnance.class)
				.add(Restrictions.eq("patientIdentifier", patientIdentifiant))
				.add(Restrictions.eq("pharmRegime", pharmRegime))
				.add(Restrictions.eq("ordDateDispen", ordDateDispen))
				.add(Restrictions.eq("pharmProgramme", pharmProgramme)).uniqueResult();
	}

	@Override
	public Collection<PharmOrdonnance> getPharmOrdonnanceByPeriod(Date dateDebut, Date dateFin,String mode) {
		//System.out.println("----------------mode--------------------"+mode);
		if(mode==null){
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.addOrder(Order.desc("ordId")).list();
		}else if(mode.equals("NA")){
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("ordFlag"),Restrictions.ne("ordFlag", "AN") ),
							Restrictions.isNull("ordFlag")) )
					.addOrder(Order.desc("ordId")).list();
		}else if(mode.equals("AN")){
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("ordFlag"),Restrictions.ne("ordFlag", "AN") ),
							Restrictions.isNull("ordFlag")) )
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.addOrder(Order.desc("ordId")).list();
		} else{
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.addOrder(Order.desc("ordId")).list();
		}
		
		
		
	}

	@Override
	public Collection<PharmOrdonnance> getPharmOrdonnancesLibresByPeriod(Date dateDebut, Date dateFin) {
		return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmOrdonnance.class)
				.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
				.add(Restrictions.isNull("patientIdentifier"))
				.addOrder(Order.desc("ordId")).list();
	}
	
	/********************************************************************************************/
	
	public void savePharmClient(PharmClient pharmClient) {
		getSessionFactory().getCurrentSession().save(pharmClient);		
	}
	
	public void retirePharmClient(PharmClient pharmClient) {
		getSessionFactory().getCurrentSession().delete(pharmClient);		
	}
	
	public PharmClient getPharmClientById(Integer pharmClientId) {
		return (PharmClient) getSessionFactory().getCurrentSession().get(PharmClient.class, pharmClientId);
	}

	
	public Collection<PharmClient> getAllPharmClients() {
		
		return (Collection<PharmClient>) getSessionFactory().getCurrentSession().createCriteria(PharmClient.class).list();
	}
	
	public void updatePharmClient(PharmClient pharmClient) {
		getSessionFactory().getCurrentSession().update(pharmClient);		
	}

	
	public void savePharmAssurance(PharmAssurance pharmAssurance) {
		getSessionFactory().getCurrentSession().save(pharmAssurance);
		
	}
	
	public void retirePharmAssurance(PharmAssurance pharmAssurance) {
		getSessionFactory().getCurrentSession().delete(pharmAssurance);
		
	}

	public PharmAssurance getPharmAssuranceById(Integer pharmAssuranceId) {
		return (PharmAssurance) getSessionFactory().getCurrentSession().get(PharmAssurance.class, pharmAssuranceId);
	}

	public Collection<PharmAssurance> getAllPharmAssurances() {
		return (Collection<PharmAssurance>) getSessionFactory().getCurrentSession().createCriteria(PharmAssurance.class).list();
	}

	public void updatePharmAssurance(PharmAssurance pharmAssurance) {
		getSessionFactory().getCurrentSession().update(pharmAssurance);
		
	}

	public void savePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance) {
		getSessionFactory().getCurrentSession().save(pharmLigneAssurance);
	}
	
	
	public void savePharmLigneAssurances(Collection<PharmLigneAssurance> pharmLigneAssurances) {
		// TODO Auto-generated method stub
		
	}

	
	public PharmLigneAssurance getPharmLigneAssurance(PharmLigneAssuranceId pharmLigneAssuranceId) {
		return (PharmLigneAssurance) getSessionFactory().getCurrentSession().get(PharmLigneAssurance.class,
				pharmLigneAssuranceId);
	}

	@Override
	public void updatePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance) {
		getSessionFactory().getCurrentSession().update(pharmLigneAssurance);
	}

	@Override
	public void retirePharmLigneAssurance(PharmLigneAssurance pharmLigneAssurance) {
		getSessionFactory().getCurrentSession().delete(pharmLigneAssurance);
		
	}

	@Override
	public Collection<PharmLigneAssurance> getAllPharmLigneAssurances() {
		return (Collection<PharmLigneAssurance>) getSessionFactory().getCurrentSession().createCriteria(PharmLigneAssurance.class).list();
	}

	
	public PharmLigneAssurance getPharmLigneAssuranceByAttri(PharmAssurance assurance, String numAssure) {
		
		return (PharmLigneAssurance) getSessionFactory().getCurrentSession()
				.createCriteria(PharmLigneAssurance.class)
				.add(Restrictions.eq("pharmAssurance", assurance))
				.add(Restrictions.eq("laNumAssur",numAssure)).uniqueResult();
	}

	@Override
	public Collection<PharmOrdonnance> getPharmOrdonnanceByPeriod(Date dateDebut, Date dateFin,
			PharmProgramme programme, String mode) {
		
		if(mode==null){
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.add(Restrictions.eq("pharmProgramme", programme))
					.addOrder(Order.desc("ordId")).list();
		}else if(mode.equals("NA")){
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("ordFlag"),Restrictions.ne("ordFlag", "AN") ),
							Restrictions.isNull("ordFlag")) )
					.addOrder(Order.desc("ordId")).list();
		}else if(mode.equals("AN")){
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("ordFlag"),Restrictions.ne("ordFlag", "AN") ),
							Restrictions.isNull("ordFlag")) )
					.add(Restrictions.eq("pharmProgramme", programme))
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.addOrder(Order.desc("ordId")).list();
		} else{
			return (Collection<PharmOrdonnance>) getSessionFactory().getCurrentSession()
					.createCriteria(PharmOrdonnance.class)
					.add(Restrictions.between("ordDateDispen", dateDebut, dateFin))
					.add(Restrictions.eq("pharmProgramme", programme))
					.addOrder(Order.desc("ordId")).list();
		}
		
		
		
	}

	

}