package org.openmrs.module.pharmagest.api.db.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.Fournisseur;
import org.openmrs.module.pharmagest.Medecin;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.module.pharmagest.TypeOperation;
import org.openmrs.module.pharmagest.api.db.ParametersDispensationDao;

public class HibernateParametersDispensationDAO implements ParametersDispensationDao {

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
	public Collection<Regime> getAllRegimes() {
		/*
		 * return (Collection<RegimeTest>)
		 * getSessionFactory().getCurrentSession() .createSQLQuery(
		 * "select * from regime_test") .addEntity(RegimeTest.class).list();
		 */
		/*
		 * return (Collection<RegimeTest>)
		 * getSessionFactory().getCurrentSession() .createQuery(
		 * "from org.openmrs.module.pharmagest.RegimeTest") .list();
		 */
		return (Collection<Regime>) getSessionFactory().getCurrentSession().createCriteria(Regime.class).list();
	}

	@SuppressWarnings("unchecked")
	public Collection<Medecin> getAllMedecins() {
		return (Collection<Medecin>) getSessionFactory().getCurrentSession().createCriteria(Medecin.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Produit> getAllProduits() {

		return (Collection<Produit>) getSessionFactory().getCurrentSession().createCriteria(Produit.class).list();
	}

	public Produit getProduitById(Integer produitId) {
		// TODO Auto-generated method stub
		return (Produit) getSessionFactory().getCurrentSession().get(Produit.class, produitId);
	}

	public Regime getRegimeById(Integer regimeId) {
		// TODO Auto-generated method stub
		return (Regime) getSessionFactory().getCurrentSession().get(Regime.class, regimeId);
	}

	public Medecin getMedecinById(Integer medecinId) {
		// TODO Auto-generated method stub
		return (Medecin) getSessionFactory().getCurrentSession().get(Medecin.class, medecinId);
	}

	@Override
	public Programme getProgrammeById(Integer programmeId) {
		return (Programme) getSessionFactory().getCurrentSession().get(Programme.class, programmeId);
	}

	@Override
	public Fournisseur getFournisseurById(Integer fournisseurId) {
		return (Fournisseur) getSessionFactory().getCurrentSession().get(Fournisseur.class, fournisseurId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Fournisseur> getAllFournisseurs() {
		return (Collection<Fournisseur>) getSessionFactory().getCurrentSession().createCriteria(Fournisseur.class)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Programme> getAllProgrammes() {
		return (Collection<Programme>) getSessionFactory().getCurrentSession().createCriteria(Programme.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TypeOperation> getAllTypeOperation() {
		return (Collection<TypeOperation>) getSessionFactory().getCurrentSession().createCriteria(TypeOperation.class)
				.list();
	}

	@Override
	public TypeOperation getTypeOperationById(Integer typeOperationId) {
		return (TypeOperation) getSessionFactory().getCurrentSession().get(TypeOperation.class, typeOperationId);
	}

	public PatientIdentifier getPatientIdentifierByIdentifier(String identifier) {

		return (PatientIdentifier) getSessionFactory().getCurrentSession().createCriteria(PatientIdentifier.class)
				.add(Restrictions.eq("identifier", identifier)).uniqueResult();

	}

	public PatientComplement getPatientComplementByIdentifier(Integer patientIdentifierId) {

		return (PatientComplement) getSessionFactory().getCurrentSession().createCriteria(PatientComplement.class)
				.add(Restrictions.eq("patientIdentifierId", patientIdentifierId.longValue())).uniqueResult();

	}

}
