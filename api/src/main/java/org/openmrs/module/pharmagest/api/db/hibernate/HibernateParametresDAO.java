package org.openmrs.module.pharmagest.api.db.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.pharmagest.PatientComplement;
import org.openmrs.module.pharmagest.PharmFournisseur;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmTypeOperation;
import org.openmrs.module.pharmagest.api.db.ParametresDao;

public class HibernateParametresDAO implements ParametresDao {

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
	public Collection<PharmRegime> getAllRegimes() {
		
		return (Collection<PharmRegime>)  getSessionFactory().getCurrentSession().createCriteria(PharmRegime.class)
				.addOrder(Order.asc("regimLib")).list();

		/*return (Collection<PharmRegime>) getSessionFactory().getCurrentSession().createCriteria(PharmRegime.class)
				.list();*/
	}

	@SuppressWarnings("unchecked")
	public Collection<PharmMedecin> getAllMedecins() {
		return (Collection<PharmMedecin>) getSessionFactory().getCurrentSession().createCriteria(PharmMedecin.class)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PharmProduit> getAllProduits() {
		return (Collection<PharmProduit>) getSessionFactory().getCurrentSession().createCriteria(PharmProduit.class)
		.addOrder(Order.asc("prodLib")).list();

		/*return (Collection<PharmProduit>) getSessionFactory().getCurrentSession().createCriteria(PharmProduit.class)
				.list();*/
	}

	public PharmProduit getProduitById(Integer produitId) {
		// TODO Auto-generated method stub
		return (PharmProduit) getSessionFactory().getCurrentSession().get(PharmProduit.class, produitId);
	}

	public PharmRegime getRegimeById(Integer regimeId) {
		// TODO Auto-generated method stub
		return (PharmRegime) getSessionFactory().getCurrentSession().get(PharmRegime.class, regimeId);
	}

	public PharmMedecin getMedecinById(Integer medecinId) {
		// TODO Auto-generated method stub
		return (PharmMedecin) getSessionFactory().getCurrentSession().get(PharmMedecin.class, medecinId);
	}

	@Override
	public PharmProgramme getProgrammeById(Integer programmeId) {
		return (PharmProgramme) getSessionFactory().getCurrentSession().get(PharmProgramme.class, programmeId);
	}

	@Override
	public PharmFournisseur getFournisseurById(Integer fournisseurId) {
		return (PharmFournisseur) getSessionFactory().getCurrentSession().get(PharmFournisseur.class, fournisseurId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PharmFournisseur> getAllFournisseurs() {
		return (Collection<PharmFournisseur>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmFournisseur.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PharmProgramme> getAllProgrammes() {
		return (Collection<PharmProgramme>) getSessionFactory().getCurrentSession().createCriteria(PharmProgramme.class)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PharmTypeOperation> getAllTypeOperation() {
		return (Collection<PharmTypeOperation>) getSessionFactory().getCurrentSession()
				.createCriteria(PharmTypeOperation.class).list();
	}

	@Override
	public PharmTypeOperation getTypeOperationById(Integer typeOperationId) {
		return (PharmTypeOperation) getSessionFactory().getCurrentSession().get(PharmTypeOperation.class,
				typeOperationId);
	}

	public PatientIdentifier getPatientIdentifierByIdentifier(String identifier) {

		return (PatientIdentifier) getSessionFactory().getCurrentSession().createCriteria(PatientIdentifier.class)
				.add(Restrictions.eq("identifier", identifier)).uniqueResult();

	}

	public PatientComplement getPatientComplementByIdentifier(Integer patientIdentifierId) {

		return (PatientComplement) getSessionFactory().getCurrentSession().createCriteria(PatientComplement.class)
				.add(Restrictions.eq("patientIdentifierId", patientIdentifierId.longValue())).uniqueResult();

	}

	@Override
	public void saveOrUpdateGestionnaire(PharmGestionnairePharma pharmGestionnairePharma) {
		getSessionFactory().getCurrentSession().saveOrUpdate(pharmGestionnairePharma);

	}

}
