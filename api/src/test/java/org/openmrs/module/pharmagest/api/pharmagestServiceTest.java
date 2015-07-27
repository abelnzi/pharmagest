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
package org.openmrs.module.pharmagest.api;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.HistoMouvementStock;
import org.openmrs.module.pharmagest.Produit;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${pharmagestService}}.
 */
public class  pharmagestServiceTest extends BaseModuleContextSensitiveTest {
	
	@Test
	public void shouldSetupContext() {
		assertNotNull(Context.getService(pharmagestService.class));
	}
	/*@Test
	public void histoMvmTest() {
		Context
		.getService(ParametersDispensationService.class)
		.getTypeOperationById(1);
		Produit produit=Context.getService(ParametersDispensationService.class).getProduitById(8174);
		
		HistoMouvementStock histoMouvementStock = new HistoMouvementStock();
		//histoMouvementStock.setMvtDate(operation.getRecptDateRecept());
		// histoMouvementStock.setMvtLot();
		// histoMouvementStock.setMvtMotif(mvtMotif);
		histoMouvementStock.setMvtProgramme(1);
		histoMouvementStock.setMvtQte(10);
		histoMouvementStock.setMvtQteStock(100);
		histoMouvementStock.setMvtTypeMvt(Context
				.getService(ParametersDispensationService.class)
				.getTypeOperationById(1).getTrecptId());
		histoMouvementStock.setProduit(produit);
		assertTrue(Context.getService(GestionStockService.class).saveHistoMvmStock(histoMouvementStock));
	}*/
}
