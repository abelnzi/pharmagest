/**
 * 
 */
package org.openmrs.module.pharmagest.metier;

import java.util.Date;

import org.openmrs.Location;

/**
 * @author Abel
 *
 */
public class FormulaireParametrage {
	private String hote;
	private String port;
	private String login;
	private String mdp;
	private String repExtract;
	private Date dateRef;

	public String getHote() {
		return hote;
	}

	public void setHote(String hote) {
		this.hote = hote;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getRepExtract() {
		return repExtract;
	}

	public void setRepExtract(String repExtract) {
		this.repExtract = repExtract;
	}

	public Date getDateRef() {
		return dateRef;
	}

	public void setDateRef(Date dateRef) {
		this.dateRef = dateRef;
	}



}
