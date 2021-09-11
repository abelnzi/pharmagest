package org.openmrs.module.pharmagest.api.impl;

import java.beans.PropertyEditorSupport;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.module.pharmagest.api.ParametersDispensationService;

public class ProduitPropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Produit custom = Context.getService(
				ParametersDispensationService.class).getProduitById(Integer.parseInt(text));
        this.setValue(custom);
	}
}