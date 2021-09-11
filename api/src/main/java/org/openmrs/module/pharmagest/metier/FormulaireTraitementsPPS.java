package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;

public class FormulaireTraitementsPPS {
	PharmSite site;
	PharmProgramme programme;
	PharmCommandeSite commandeSite;
	Date dateParam;
	
	private PharmProduit produit;
	private PharmLigneCommandeSite ligneCommandeSite;
	public Integer getLgnComQteIni() {
		return ligneCommandeSite.getLgnComQteIni();
	}

	public void setLgnComQteIni(Integer lgnComQteIni) {
		ligneCommandeSite.setLgnComQteIni(lgnComQteIni);
	}

	public Integer getLgnComQteRecu() {
		return ligneCommandeSite.getLgnComQteRecu();
	}

	public PharmProgramme getPharmProgramme() {
		return commandeSite.getPharmProgramme();
	}

	public PharmSite getPharmSite() {
		return commandeSite.getPharmSite();
	}

	public int hashCode() {
		return ligneCommandeSite.hashCode();
	}

	public PharmLigneCommandeSiteId getId() {
		return ligneCommandeSite.getId();
	}

	public void setId(PharmLigneCommandeSiteId id) {
		ligneCommandeSite.setId(id);
	}

	public PharmCommandeSite getPharmCommandeSite() {
		return ligneCommandeSite.getPharmCommandeSite();
	}

	public void setPharmCommandeSite(PharmCommandeSite pharmCommandeSite) {
		ligneCommandeSite.setPharmCommandeSite(pharmCommandeSite);
	}

	public PharmProduit getPharmProduit() {
		return ligneCommandeSite.getPharmProduit();
	}

	public void setPharmProduit(PharmProduit pharmProduit) {
		ligneCommandeSite.setPharmProduit(pharmProduit);
	}

	public boolean equals(Object obj) {
		return ligneCommandeSite.equals(obj);
	}

	public String toString() {
		return ligneCommandeSite.toString();
	}

	public void setLgnComQteRecu(Integer lgnComQteRecu) {
		ligneCommandeSite.setLgnComQteRecu(lgnComQteRecu);
	}

	public Integer getLgnComQteUtil() {
		return ligneCommandeSite.getLgnComQteUtil();
	}

	public void setLgnComQteUtil(Integer lgnComQteUtil) {
		ligneCommandeSite.setLgnComQteUtil(lgnComQteUtil);
	}

	public Integer getLgnComPertes() {
		return ligneCommandeSite.getLgnComPertes();
	}

	public void setLgnComPertes(Integer lgnComPertes) {
		ligneCommandeSite.setLgnComPertes(lgnComPertes);
	}

	public Integer getLgnComStockDispo() {
		return ligneCommandeSite.getLgnComStockDispo();
	}

	public void setLgnComStockDispo(Integer lgnComStockDispo) {
		ligneCommandeSite.setLgnComStockDispo(lgnComStockDispo);
	}

	public Integer getLgnComNbreJrsRup() {
		return ligneCommandeSite.getLgnComNbreJrsRup();
	}

	public void setLgnComNbreJrsRup(Integer lgnComNbreJrsRup) {
		ligneCommandeSite.setLgnComNbreJrsRup(lgnComNbreJrsRup);
	}

	public Integer getLgnQteDistriM1() {
		return ligneCommandeSite.getLgnQteDistriM1();
	}

	public void setLgnQteDistriM1(Integer lgnQteDistriM1) {
		ligneCommandeSite.setLgnQteDistriM1(lgnQteDistriM1);
	}

	public Integer getLgnQteDistriM2() {
		return ligneCommandeSite.getLgnQteDistriM2();
	}

	public void setLgnQteDistriM2(Integer lgnQteDistriM2) {
		ligneCommandeSite.setLgnQteDistriM2(lgnQteDistriM2);
	}

	public Integer getLgnQtePro() {
		return ligneCommandeSite.getLgnQtePro();
	}

	public void setLgnQtePro(Integer lgnQtePro) {
		ligneCommandeSite.setLgnQtePro(lgnQtePro);
	}

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public PharmLigneCommandeSite getLigneCommandeSite() {
		return ligneCommandeSite;
	}

	public void setLigneCommandeSite(PharmLigneCommandeSite ligneCommandeSite) {
		this.ligneCommandeSite = ligneCommandeSite;
	}

	public PharmLigneCommandeSiteId getLigneCommandeSiteId() {
		return ligneCommandeSiteId;
	}

	public void setLigneCommandeSiteId(PharmLigneCommandeSiteId ligneCommandeSiteId) {
		this.ligneCommandeSiteId = ligneCommandeSiteId;
	}

	public TabSaisiesPPS getTabSaisiesPPS() {
		return tabSaisiesPPS;
	}

	public void setTabSaisiesPPS(TabSaisiesPPS tabSaisiesPPS) {
		this.tabSaisiesPPS = tabSaisiesPPS;
	}

	private PharmLigneCommandeSiteId ligneCommandeSiteId;
	private TabSaisiesPPS tabSaisiesPPS;
	
	public FormulaireTraitementsPPS() {
		this.commandeSite = new PharmCommandeSite();
		this.ligneCommandeSite = new PharmLigneCommandeSite();
		this.ligneCommandeSiteId = new PharmLigneCommandeSiteId();
		this.setTabSaisiesPPS(new TabSaisiesPPS());
	}

	public PharmProgramme getProgramme() {
		return programme;
	}

	public void setProgramme(PharmProgramme programme) {
		this.programme = programme;
	}

	public PharmSite getSite() {
		return site;
	}

	public void setSite(PharmSite site) {
		this.site = site;
	}

	public PharmCommandeSite getCommandeSite() {
		return commandeSite;
	}

	public void setCommandeSite(PharmCommandeSite commandeSite) {
		this.commandeSite = commandeSite;
	}

	public Date getDateParam() {
		return dateParam;
	}

	public void setDateParam(Date dateParam) {
		this.dateParam = dateParam;
	}

	
	
	
}
