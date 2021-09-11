package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.Inventaire;
import org.openmrs.module.pharmagest.LigneInventaire;
import org.openmrs.module.pharmagest.LigneInventaireId;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.Programme;

public class FormulaireInventaire {

	private Inventaire inventaire;
	private LigneInventaire ligneInventaire;
	private LigneInventaireId ligneInventaireId;
	private TabInventaire tabInventaire;

	public FormulaireInventaire() {
		this.inventaire = new Inventaire();
		this.ligneInventaire = new LigneInventaire();
		this.ligneInventaireId = new LigneInventaireId();
		this.tabInventaire = new TabInventaire();
	}

	public Inventaire getInventaire() {
		return inventaire;
	}

	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

	public LigneInventaire getLigneInventaire() {
		return ligneInventaire;
	}

	public void setLigneInventaire(LigneInventaire ligneInventaire) {
		this.ligneInventaire = ligneInventaire;
	}

	public LigneInventaireId getLigneInventaireId() {
		return ligneInventaireId;
	}

	public void setLigneInventaireId(LigneInventaireId ligneInventaireId) {
		this.ligneInventaireId = ligneInventaireId;
	}

	public TabInventaire getTabInventaire() {
		return tabInventaire;
	}

	public void setTabInventaire(TabInventaire tabInventaire) {
		this.tabInventaire = tabInventaire;
	}

	public int getInvId() {
		return inventaire.getInvId();
	}

	public void setInvId(int invId) {
		inventaire.setInvId(invId);
	}

	public Date getInvDateCrea() {
		return inventaire.getInvDateCrea();
	}

	public void setInvDateCrea(Date invDateCrea) {
		inventaire.setInvDateCrea(invDateCrea);
	}

	public Date getInvDeb() {
		return inventaire.getInvDeb();
	}

	public void setInvDeb(Date invDeb) {
		inventaire.setInvDeb(invDeb);
	}

	public Date getInvFin() {
		return inventaire.getInvFin();
	}

	public void setInvFin(Date invFin) {
		inventaire.setInvFin(invFin);
	}

	public LigneInventaireId getId() {
		return ligneInventaire.getId();
	}

	public void setId(LigneInventaireId id) {
		ligneInventaire.setId(id);
	}

	public Programme getProgramme() {
		return ligneInventaire.getProgramme();
	}

	public void setProgramme(Programme programme) {
		ligneInventaire.setProgramme(programme);
	}

	public Produit getProduit() {
		return ligneInventaire.getProduit();
	}

	public void setProduit(Produit produit) {
		ligneInventaire.setProduit(produit);
	}

	public Integer getQtePhysique() {
		return ligneInventaire.getQtePhysique();
	}

	public void setQtePhysique(Integer qtePhysique) {
		ligneInventaire.setQtePhysique(qtePhysique);
	}

	public Integer getQteLogique() {
		return ligneInventaire.getQteLogique();
	}

	public void setQteLogique(Integer qteLogique) {
		ligneInventaire.setQteLogique(qteLogique);
	}

	public String getObservation() {
		return ligneInventaire.getObservation();
	}

	public void setObservation(String observation) {
		ligneInventaire.setObservation(observation);
	}

	public Integer getEcart() {
		return ligneInventaire.getEcart();
	}

	public void setEcart(Integer ecart) {
		ligneInventaire.setEcart(ecart);
	}

	public LigneInventaireId addLigneInventaireId() {
		this.ligneInventaireId.setProdId(this.getProduit().getProdId());
		this.ligneInventaireId.setProgramId(this.getProgramme().getProgramId());
		this.ligneInventaireId.setInvId(this.getInventaire().getInvId());
		this.getLigneInventaire().setId(this.ligneInventaireId);
		return this.ligneInventaireId;
	}

}
