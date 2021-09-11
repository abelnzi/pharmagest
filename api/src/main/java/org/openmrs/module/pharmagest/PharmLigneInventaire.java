package org.openmrs.module.pharmagest;

// Generated 16 ao�t 2015 03:07:54 by Hibernate Tools 3.4.0.CR1

/**
 * PharmLigneInventaire generated by hbm2java
 */
public class PharmLigneInventaire implements java.io.Serializable {

	private PharmLigneInventaireId id;
	private PharmInventaire pharmInventaire;
	private PharmProduitAttribut pharmProduitAttribut;
	private Integer qtePhysique;
	private Integer qteLogique;
	private String observation;
	private Integer ecart;
	private Integer qtePro;
	private Integer prixInv;

	public PharmLigneInventaire() {
	}

	public PharmLigneInventaire(PharmLigneInventaireId id, PharmInventaire pharmInventaire,
			PharmProduitAttribut pharmProduitAttribut) {
		this.id = id;
		this.pharmInventaire = pharmInventaire;
		this.pharmProduitAttribut = pharmProduitAttribut;
	}

	public PharmLigneInventaire(PharmLigneInventaireId id, PharmInventaire pharmInventaire,
			PharmProduitAttribut pharmProduitAttribut, Integer qtePhysique, Integer qteLogique, String observation,
			Integer ecart) {
		this.id = id;
		this.pharmInventaire = pharmInventaire;
		this.pharmProduitAttribut = pharmProduitAttribut;
		this.qtePhysique = qtePhysique;
		this.qteLogique = qteLogique;
		this.observation = observation;
		this.ecart = ecart;
	}

	public PharmLigneInventaireId getId() {
		return this.id;
	}

	public void setId(PharmLigneInventaireId id) {
		this.id = id;
	}

	public PharmInventaire getPharmInventaire() {
		return this.pharmInventaire;
	}

	public void setPharmInventaire(PharmInventaire pharmInventaire) {
		this.pharmInventaire = pharmInventaire;
	}

	public PharmProduitAttribut getPharmProduitAttribut() {
		return this.pharmProduitAttribut;
	}

	public void setPharmProduitAttribut(PharmProduitAttribut pharmProduitAttribut) {
		this.pharmProduitAttribut = pharmProduitAttribut;
	}

	public Integer getQtePhysique() {
		return this.qtePhysique;
	}

	public void setQtePhysique(Integer qtePhysique) {
		this.qtePhysique = qtePhysique;
	}

	public Integer getQteLogique() {
		return this.qteLogique;
	}

	public void setQteLogique(Integer qteLogique) {
		this.qteLogique = qteLogique;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getEcart() {
		return this.ecart;
	}

	public void setEcart(Integer ecart) {
		this.ecart = ecart;
	}

	public Integer getQtePro() {
		return qtePro;
	}

	public void setQtePro(Integer qtePro) {
		this.qtePro = qtePro;
	}

	public Integer getPrixInv() {
		return prixInv;
	}

	public void setPrixInv(Integer prixInv) {
		this.prixInv = prixInv;
	}

}