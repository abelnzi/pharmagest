<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />


<spring:htmlEscape defaultHtmlEscape="true" />
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<ul id="menu">
	

	<li class="active first"><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li>
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">Entr&eacute;e fournisseur</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockEntree.form"/>">Autre mouvement d'entr&eacute;e de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockSortie.form"/>">Autre mouvement de sortie de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapportage sur le Stock</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	
	<!-- Add further links here -->
</ul>

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>

<script type="text/javascript">
	function daterdv() {
		var nbj = parseInt(document
				.getElementById("ordNbreJrsTrai").value);
		if(isNaN(nbj)){
			alert('indiquer le nombre de jour de traitement');
				}
		var d = document.getElementById("ordDateDispen").value;
		var j = d.substring(0, 2);
		var m = d.substring(3, 5);
		var a = d.substring(6, 11);
		var datenew = new Date(m + "/" + j + "/" + a);
		if(isNaN(datenew)){
			alert('indiquer une date de dispensation valide');
				}
		datenew.setDate(datenew.getDate() + nbj);

		var annee = datenew.getFullYear()

		var mois = datenew.getMonth() < 9 ? "0" + (datenew.getMonth() + 1)
				: (datenew.getMonth() + 1);
		var jour = datenew.getDate() < 10 ? "0" + datenew.getDate() : datenew
				.getDate();		
		if(!isNaN(nbj) && !isNaN(datenew)){document.getElementById("ordDateRdv").value = jour+ "/" + mois + "/" + annee;}
	}
</SCRIPT>


<div>
	<h3>Effectuer la dispensation</h3>
</div>
<br>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/dispensation.form"
	modelAttribute="formulaireOrdonnance"
	commandName="formulaireOrdonnance">
	<!--<form action="${pageContext.request.contextPath}/module/pharmagest/dispensation.form" method="post">-->
	<c:if test="${var !=1}">
    <div>

		<b class="boxHeader">Rechercher le patient</b>
		<div class="box">
			<div class="searchWidgetContainer" id="findPatients" align="center">
				<table width="100%" border="0" cellpadding="7" cellspacing="0">
					<tbody>
						<tr>
							<td width="10%">N° Patient</td>
							<td width="15%"><input type="text" name="numPatient"
								id="numPatient" /></td>
							<td width="73%"><input type="submit" name="btn_recherche"
								id="btn_recherche" value="Rechercher"></td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</div>
    <br>
		<hr>
		<br>
    </c:if>
    
	<c:if test="${mess =='echec'}">
		<div id="openmrs_msg">Aucun patient trouver</div>
	</c:if>
	<c:if test="${mess =='save'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
	</c:if>
	<c:if test="${mess =='find'}">
		<div id="openmrs_msg">Patient trouvé</div>
	</c:if>
	<c:if test="${var ==1}">
		
		<div>
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td><input type="submit" name="btn_enregistrer"
							id="btn_enregistrer" value="Enregistrer"> <input
							type="reset" name="reset" id="reset" value="Annuler"></td>
					</tr>
				</tbody>
			</table>

		</div>


		<div>

			<b class="boxHeader"></b>
			<div class="box">
				<div class="searchWidgetContainer" id="findPatients" align="center">
					<table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
							<tr>
								<td width="10%">Programme</td>
								<td width="16%"><form:select path="pharmProgramme">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select><form:errors path="pharmProgramme"
														cssClass="error" /></td>
								<td width="73%" rowspan="2"><table width="100%" cellpadding="7"
										cellspacing="0">
										<tbody>
											<tr>
												<td width="17%">Dernier régime</td>
												<td width="28%"><input type="text" disabled="disabled"
													value="${regime}" readonly /></td>
												<td width="24%">Date dernier régime</td>
												<td width="29%"><input type="text" disabled="disabled"
													value="${rdv}" readonly /></td>
											</tr>
											<tr>
												<td rowspan="2">Régime actuel<span style="color:#F11A0C">*</span></td>
												<td><form:select path="pharmRegime">
														<form:option value="0" label="---Choix---" />
														<form:options items="${regimes}" itemValue="regimId"
															itemLabel="regimLib" />
													</form:select></td>
												<td rowspan="2"><form:label
														path="ordNbreJrsTrai">Nombre de jours de traitement<span style="color:#F11A0C">*</span></form:label></td>
												<td><form:input path="ordNbreJrsTrai" /></td>
											</tr>
											<tr>
												<td><form:errors path="pharmRegime"
														cssClass="error" /></td>
												<td><form:errors path="ordNbreJrsTrai"
														cssClass="error" /></td>
											</tr>
											<tr>
												<td>But</td>
												<td colspan="3"><table width="100%">
														<tr>
															<td width="19%">Non applicable: <form:radiobutton
																	path="ordBut" value="NA" />
															</td>
                                                            <td width="10%">PEC: <form:radiobutton
																	path="ordBut" value="PEC" />
															</td>

															<td width="10%">PTME: <form:radiobutton
																	path="ordBut" value="PTME" />
															</td>

															<td width="10%">AES: <form:radiobutton
																	path="ordBut" value="AES" />

															</td>
															<td width="51%"><form:errors
																	path="ordBut" cssClass="error" /></td>
														</tr>
													</table></td>
											</tr>
										</tbody>
									</table></td>
							</tr>
							<tr>
							  <td>Patient</td>
							  <td width="16%"><table width="100" border="0" cellspacing="0" cellpadding="7">
						        <tbody>
							        <tr>
							          <td>N&deg;</td>
							          <td><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.idParam}" readonly /></td>
						            </tr>
							        <tr>
							          <td>Age</td>
							          <td><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.age}" readonly /></td>
						            </tr>
							        <tr>
							          <td>Sexe</td>
							          <td><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.sexe}" readonly /></td>
						            </tr>
					            </tbody>
					          </table></td>
					      </tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br>
		<div>

			<b class="boxHeader"></b>
			<div class="box">
				<div class="searchWidgetContainer" id="findPatients" align="center">
					<table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
							<tr>
								<td width="32%"><table width="100%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="42%" rowspan="2">Prescripteur</td>
												<td width="58%"><form:select
														path="pharmMedecin">
														<form:option value="0" label="---Choix---" />
														<form:options items="${medecins}" itemValue="medId"
															itemLabel="medNom" />
													</form:select></td>
											</tr>
											<tr>
												<td><form:errors path="pharmMedecin"
														cssClass="error" /></td>
											</tr>
											<tr>
												<td rowspan="2"><form:label
														path="ordDatePrescrip">Date de prescription</form:label></td>
												<td><form:input path="ordDatePrescrip" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i></td>
											</tr>
											<tr>
												<td><form:errors path="ordDatePrescrip"
														cssClass="error" /></td>
											</tr>
										</tbody>
									</table></td>
								<td width="36%"><table width="100%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="43%">Pharmacien</td>
												<td width="57%"><input name="textfield2" type="text"
													disabled="disabled" id="textfield2"
													value="${formulaireOrdonnance.pharmGestionnairePharma.gestNom}  ${formulaireOrdonnance.pharmGestionnairePharma.gestPrenom}" size="35"></td>
											</tr>
											<tr>
												<td rowspan="2"><form:label
														path="ordDateDispen">Date de dispensation<span style="color:#F11A0C">*</span></form:label></td>
												<td><form:input path="ordDateDispen"  onchange="javascript:daterdv();" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i></td>
											</tr>
											<tr>
												<td><form:errors path="ordDateDispen"
														cssClass="error" /></td>
											</tr>
										</tbody>
									</table></td>
								<td width="32%"><table width="92%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="52%" rowspan="2"><div align="center">
														<form:label path="ordDateRdv">
															<div align="left">Date du prochain RDV</div>
														</form:label>
													</div></td>
												<td width="48%"><div align="left">
														<form:input path="ordDateRdv"
															readonly="true"  />

													</div></td>
											</tr>
											<tr>
												<td><form:errors path="ordDateRdv"
														cssClass="error" /></td>
											</tr>
											<tr>
												<td height="34" rowspan="2">&nbsp;</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
											</tr>
										</tbody>
									</table></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br>
		<c:if test="${mess =='accept'}">
			<div id="openmrs_msg">une ligne inserée</div>
		</c:if>
		<c:if test="${mess =='refuse'}">
			<div id="openmrs_msg">Stock insuffisant</div>
		</c:if>
        
		<div>
			<b class="boxHeader"></b>
			<div class="box">

				<table width="100%" border="0" cellpadding="7" cellspacing="0">
					<tbody>
						<tr>
							<td width="13%"><div align="center">
									<table width="100%" border="0" cellspacing="0">
										<tbody>
											<tr>
												<td><div align="center">Code article</div></td>
											</tr>
											<tr>
												<td><div align="center">
                                                            <input type="text" disabled="disabled"
															value="${formulaireOrdonnance.pharmProduit.prodCode}" size="15" readonly />
													</div></td>
											</tr>
										</tbody>
									</table>
								</div></td>
							<td width="23%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">D&eacute;signation<span style="color:#F11A0C">*</span></div></td>
										</tr>
										<tr>
											<td><div align="center">
											
													<form:select path="pharmProduit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
													</form:select>

												</div></td>

										</tr>
										<tr>
											<td><div align="center">
													<form:errors path="pharmProduit" cssClass="error" />
												</div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="13%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteDem">Quantité demandée</form:label>
													<span style="color:#F11A0C">*</span></div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:input path="ldQteDem"  size="10"/>

												</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:errors path="ldQteDem" cssClass="error" />
												</div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="12%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteServi">Quantité dispensée<span style="color:#F11A0C">*</span></form:label>
										  </div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:input path="ldQteServi" size="10" />

												</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:errors path="ldQteServi" cssClass="error" />
												</div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="23%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">Prix unitaire</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:input path="ldPrixUnit" />
												</div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="9%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">Total</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<input name="textfield6" type="text" disabled="disabled"
														id="textfield6" value="0" size="10">
										  </div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="7%">
								<table width="100%" border="0" cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center"></div></td>
										</tr>
										<tr>
											<td><input type="submit" name="btn_valider"
												id="btn_valider" value="Ajouter" onClick="javascript:daterdv();"></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<br>
		<div>
			<b class="boxHeader"></b>
			<div class="box">
				<table width="100%" border="1" cellspacing="0" cellpadding="7">
					<tbody>
						<tr>
							<td width="13%">Code article</td>
							<td width="24%">DCI Article</td>
							<td width="11%">Quantité demandée</td>
							<td width="11%">Quantité dispensée</td>
							<td width="10%">N&deg; lot</td>
							<td width="8%">Date de p&eacute;remption</td>
							<td width="7%">Prix</td>
							<td width="9%">Total</td>
							<td width="7%">Action</td>
						</tr>
						<c:forEach var="ld" items="${ligneDispensations}">
							<tr>
								<td>${ld.pharmProduit.prodCode}</td>
								<td>${ld.pharmProduit.prodLib}</td>
								<td>${ld.ldQteDem}</td>
								<td>${ld.ldQteServi}</td>
								<td>${ld.lgnRecptLot}</td>
								<td>${ld.lgnDatePerem}</td>
								<td>${ld.ldPrixUnit}</td>
								<td>${ld.ldQteServi * ld.ldPrixUnit}</td>
								<td><a
									href="<c:url value="/module/pharmagest/dispensation.form"><c:param name="paramId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/></c:url>">X</a>

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>


	</c:if>

</form:form>