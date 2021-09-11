package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;

public class FormulaireSaisiesPPS {

	private PharmCommandeSite commandeSite;
	private PharmProduit produit;
	private PharmLigneCommandeSite ligneCommandeSite;
	private PharmLigneCommandeSiteId ligneCommandeSiteId;
	private TabSaisiesPPS tabSaisiesPPS;

	public FormulaireSaisiesPPS() {
		this.commandeSite = new PharmCommandeSite();
		this.ligneCommandeSite = new PharmLigneCommandeSite();
		this.ligneCommandeSiteId = new PharmLigneCommandeSiteId();
		this.setTabSaisiesPPS(new TabSaisiesPPS());
	}

	public PharmCommandeSite getCommandeSite() {
		return commandeSite;
	}

	public void setCommandeSite(PharmCommandeSite commandeSite) {
		this.commandeSite = commandeSite;
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

	public PharmLigneCommandeSiteId getId() {
		return ligneCommandeSite.getId();
	}

	public void setId(PharmLigneCommandeSiteId id) {
		ligneCommandeSite.setId(id);
	}

	public PharmProgramme getPharmProgramme() {
		return commandeSite.getPharmProgramme();
	}

	public void setPharmProgramme(PharmProgramme pharmProgramme) {
		commandeSite.setPharmProgramme(pharmProgramme);
	}

	public PharmSite getPharmSite() {
		return commandeSite.getPharmSite();
	}

	public void setPharmSite(PharmSite pharmSite) {
		commandeSite.setPharmSite(pharmSite);
	}

	public String getComSiteCode() {
		return commandeSite.getComSiteCode();
	}

	public void setComSiteCode(String comSiteCode) {
		commandeSite.setComSiteCode(comSiteCode);
	}

	public Date getComSiteDateCom() {
		return commandeSite.getComSiteDateCom();
	}

	public void setComSiteDateCom(Date comSiteDateCom) {
		commandeSite.setComSiteDateCom(comSiteDateCom);
	}

	public String getComSitePeriodLib() {
		return commandeSite.getComSitePeriodLib();
	}

	public void setComSitePeriodLib(String comSitePeriodLib) {
		commandeSite.setComSitePeriodLib(comSitePeriodLib);
	}

	public Date getComSitePeriodDate() {
		return commandeSite.getComSitePeriodDate();
	}

	public void setComSitePeriodDate(Date comSitePeriodDate) {
		commandeSite.setComSitePeriodDate(comSitePeriodDate);
	}

	public Integer getComSiteFlag() {
		return commandeSite.getComSiteFlag();
	}

	public void setComSiteFlag(Integer comSiteFlag) {
		commandeSite.setComSiteFlag(comSiteFlag);
	}

	public Integer getComStockMax() {
		return commandeSite.getComStockMax();
	}

	public void setComStockMax(Integer comStockMax) {
		commandeSite.setComStockMax(comStockMax);
	}

	public Date getComSiteDateSaisi() {
		return commandeSite.getComSiteDateSaisi();
	}

	public void setComSiteDateSaisi(Date comSiteDateSaisi) {
		commandeSite.setComSiteDateSaisi(comSiteDateSaisi);
	}

	public Date getComSiteDateModif() {
		return commandeSite.getComSiteDateModif();
	}

	public void setComSiteDateModif(Date comSiteDateModif) {
		commandeSite.setComSiteDateModif(comSiteDateModif);
	}

	public PharmProduit getProduit() {
		return produit;
	}

	public void setProduit(PharmProduit produit) {
		this.produit = produit;
	}

	public Integer getLgnComQteIni() {
		return ligneCommandeSite.getLgnComQteIni();
	}

	public void setLgnComQteIni(Integer lgnComQteIni) {
		ligneCommandeSite.setLgnComQteIni(lgnComQteIni);
	}

	public Integer getLgnComQteRecu() {
		return ligneCommandeSite.getLgnComQteRecu();
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
	
	

	/*public PharmLigneCommandeSiteId addLigneCommandeSiteId() {
		this.ligneCommandeSiteId.setProdId(this.getProduit().getProdId());
		this.ligneOperationId.setRecptId(this.getOperation().getRecptId());
		this.getLigneOperation().setId(this.ligneOperationId);
		return this.ligneOperationId;
	}*/

}
