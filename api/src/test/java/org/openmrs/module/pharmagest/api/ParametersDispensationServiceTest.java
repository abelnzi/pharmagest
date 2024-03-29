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

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.Regime;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${ParametersDispensationService}}.
 */
public class  ParametersDispensationServiceTest extends BaseModuleContextSensitiveTest {
	
	@Test
	public void shouldSetupContext() {
		assertNotNull(Context.getService(ParametersDispensationService.class));
	}
	@Test
	public void regimesList(){
		Collection<Regime> list = Context.getService(ParametersDispensationService.class).getAllRegimes();
		assertNotNull(list);
	}
}
