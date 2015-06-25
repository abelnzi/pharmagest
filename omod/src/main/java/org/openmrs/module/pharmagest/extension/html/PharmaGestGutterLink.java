/**
 * 
 */
package org.openmrs.module.pharmagest.extension.html;

import org.openmrs.module.web.extension.LinkExt;

/**
 * @author Abel
 *
 */
public class PharmaGestGutterLink extends LinkExt {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.web.extension.LinkExt#getLabel()
	 */
	@Override
	public String getLabel() {

		return "pharmagest.generate.gutterlink";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.web.extension.LinkExt#getRequiredPrivilege()
	 */
	@Override
	public String getRequiredPrivilege() {

		return "Manage Generateur ID";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openmrs.module.web.extension.LinkExt#getUrl()
	 */
	@Override
	public String getUrl() {
		return "module/pharmagest/manage.form";
	}

}
