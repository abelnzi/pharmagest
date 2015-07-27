<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<div>
	<h2>Effectuer la dispensation</h2>
</div>
<br>

<!-- <div style="border: 1px dashed black; padding: 10px;">
		<table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="19%">
                <label>
                  <input name="RadioGroup1" type="radio" id="RadioGroup1_0" onClick="block1()" value="bouton radio"  checked >
                  Patient sous traitement ARV
                </label></td>
                <td width="81%">
                <label>
                  <input type="radio" name="RadioGroup1" value="bouton radio" id="RadioGroup1_1" onClick="block2()">
                  Autres dispensation</label></td>
            </tr>
          </tbody>
        </table>
</div>-->
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/dispensation.form"
	modelAttribute="formulaireOrdonnance"
	commandName="formulaireOrdonnance">
	<!--<form action="${pageContext.request.contextPath}/module/pharmagest/dispensation.form" method="post">-->
	<div>

		<b class="boxHeader">Rechercher le patient</b>
		<div class="box">
			<div class="searchWidgetContainer" id="findPatients" align="center">
				<table width="100%" border="0" cellpadding="7" cellspacing="0">
					<tbody>
						<tr>
							<td width="10%">N° Patient</td>
							<td width="15%"><input type="number" name="numPatient"
								id="numPatient" /></td>
							<td width="73%"><input type="submit" name="btn_recherche"
								id="btn_recherche" value="Rechercher"></td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</div>
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
		<br>
		<hr>
		<br>
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
								<td width="10%">Patient N°</td>
								<td width="16%"><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.idParam}" readonly /></td>
								<td width="73%"><table width="100%" cellpadding="7"
										cellspacing="0">
										<tbody>
											<tr>
												<td width="17%">Dernier régime</td>
												<td width="28%"><input type="text" disabled="disabled"
													readonly /></td>
												<td width="24%">Date dernier régime</td>
												<td width="29%"><input type="text" disabled="disabled"
													readonly /></td>
											</tr>
											<tr>
												<td rowspan="2">Régime actuel</td>
												<td><form:select path="regime">
														<form:option value="0" label="---Choix---" />
														<form:options items="${regimes}" itemValue="regimId"
															itemLabel="regimLib" />
													</form:select> </td>
												<td rowspan="2"><form:label path="nbreJrsTrai">Nombre de jours de traitement</form:label></td>
												<td><form:input path="nbreJrsTrai" /> </td>
											</tr>
											<tr>
											  <td><form:errors path="regime" cssClass="error"/></td>
											  <td><form:errors
														path="nbreJrsTrai" cssClass="error"/></td>
									      </tr>
											<tr>
												<td>But</td>
												<td colspan="3"><table width="100%">
														<tr>
															<td width="10%">PEC: <form:radiobutton path="ordBut" value="PEC" />
															</td>

															<td width="13%">PTME: <form:radiobutton path="ordBut"
																	value="PTME" />
															</td>

															<td width="11%">AES: <form:radiobutton path="ordBut" value="AES" />
																
															</td>
															<td width="66%"><form:errors path="ordBut"  cssClass="error" /></td>
														</tr>
													</table></td>
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
												<td width="58%"><form:select path="medecin">
														<form:option value="0" label="---Choix---" />
														<form:options items="${medecins}" itemValue="medId"
															itemLabel="medNom" />
													</form:select> </td>
											</tr>
											<tr>
											  <td><form:errors path="medecin" cssClass="error" /></td>
										  </tr>
											<tr>
												<td rowspan="2"><form:label path="ordDatePrescrip">Date de prescription</form:label></td>
												<td><form:input path="ordDatePrescrip" /> </td>
											</tr>
											<tr>
											  <td><form:errors
														path="ordDatePrescrip"  cssClass="error" /></td>
										  </tr>
										</tbody>
									</table></td>
								<td width="36%"><table width="100%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="41%">Pharmacien</td>
												<td width="59%"><input name="textfield2" type="text"
													disabled="disabled" id="textfield2"
													value="${formulaireOrdonnance.gestionnairePharma.prepNom}  ${formulaireOrdonnance.gestionnairePharma.prepPrenom}"></td>
											</tr>
											<tr>
												<td rowspan="2"><form:label path="ordDateDispen">Date de dispensation</form:label></td>
												<td><form:input path="ordDateDispen" /> </td>
											</tr>
											<tr>
											  <td><form:errors
														path="ordDateDispen"  cssClass="error" /></td>
										  </tr>
										</tbody>
									</table></td>
								<td width="32%"><table width="92%" border="0" cellpadding="7"
										cellspacing="0">
										<tbody>
											<tr>
												<td width="52%" rowspan="2"><div align="center">
														<form:label path="ordDateRdv">
														  <div align="left">Date du prochain RDV</div>
														</form:label>
													</div></td>
												<td width="48%"><div align="left">
												  <form:input path="ordDateRdv" />
												  
												  </div></td>
											</tr>
											<tr>
											  <td><form:errors path="ordDateRdv"  cssClass="error" /></td>
										  </tr>
											<tr>
												<td height="34" rowspan="2">Programme</td>
												<td><form:select path="programme">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
											  </form:select> </td>
											</tr>
											<tr>
											  <td><form:errors path="programme"  cssClass="error" /></td>
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
        <c:if test="${mess =='valid'}">
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
															value="${formulaireOrdonnance.produit.prodId}" readonly />
													</div></td>
											</tr>
										</tbody>
									</table>
								</div></td>
							<td width="23%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">D&eacute;signation</div></td>
										</tr>
										<tr>
											<td ><div align="center">
													<form:select path="produit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
													</form:select>
													
												</div></td>

										</tr>
										<tr>
										  <td ><div align="center">
										    <form:errors path="produit"  cssClass="error" />
									      </div></td>
									  </tr>
									</tbody>
						  </table></td>
							<td width="14%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="servQteDem">Quantité demandée</form:label>
												</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:input path="servQteDem" />
											  
												</div></td>
										</tr>
										<tr>
										  <td><div align="center"><form:errors path="servQteDem"  cssClass="error" /></div></td>
									  </tr>
									</tbody>
								</table></td>
							<td width="14%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="servQteServi">Quantitée dispensée</form:label>
												</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:input path="servQteServi" />
											  
												</div></td>
										</tr>
										<tr>
										  <td><div align="center"><form:errors path="servQteServi"  cssClass="error"/></div></td>
									  </tr>
									</tbody>
								</table></td>
							<td width="14%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">Prix unitaire</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<form:input path="servPrixUnit" />
												</div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="14%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">Total</div></td>
										</tr>
										<tr>
											<td><div align="center">
													<input name="textfield6" type="text" disabled="disabled"
														id="textfield6" value="0">
												</div></td>
										</tr>
									</tbody>
								</table></td>
							<td width="8%">
								<table width="100%" border="0" cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center"></div></td>
										</tr>
										<tr>
											<td><input type="submit" name="btn_valider"
												id="btn_valider" value="Valider"></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--<div>
			<b class="boxHeader"></b>
			<div class="box">

					<table width="100%" border="1" align="left" cellpadding="7"
						cellspacing="0">
						<tbody>
							<tr>
								<td width="14%">Code article</td>
								<td width="23%">DCI Article</td>
								<td width="13%">Quantité demandée</td>
								<td width="15%">Quantitée dispensée</td>
								<td width="13%">Prix unitaire</td>
								<td width="14%">Total</td>
								<td width="8%">Action</td>
							</tr>

							<c:forEach var="ld" items="${ligneDispensations}">
								<tr>
									<td>${ld.produit.prodId}</td>
									<td>${ld.produit.prodLib}</td>
									<td>${ld.servQteDem}</td>
									<td>${ld.servQteServi}</td>
									<td>${ld.servPrixUnit}</td>
									<td>${ld.servQteServi * ld.servPrixUnit}</td>
									<td><a
										href="<c:url value="/module/pharmagest/dispensation.form"><c:param name="paramId" value="${ld.produit.prodId}"/></c:url>">X</a>

									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
                    </div></div>-->
		<br>
		<div>
			<b class="boxHeader"></b>
			<div class="box">
				<table width="100%" border="1" cellspacing="0" cellpadding="7">
					<tbody>
						<tr>
							<td width="14%">Code article</td>
							<td width="23%">DCI Article</td>
							<td width="13%">Quantité demandée</td>
							<td width="15%">Quantitée dispensée</td>
							<td width="13%">Prix unitaire</td>
							<td width="14%">Total</td>
							<td width="8%">Action</td>
						</tr>
						<c:forEach var="ld" items="${ligneDispensations}">
								<tr>
									<td>${ld.produit.prodId}</td>
									<td>${ld.produit.prodLib}</td>
									<td>${ld.servQteDem}</td>
									<td>${ld.servQteServi}</td>
									<td>${ld.servPrixUnit}</td>
									<td>${ld.servQteServi * ld.servPrixUnit}</td>
									<td><a
										href="<c:url value="/module/pharmagest/dispensation.form"><c:param name="paramId" value="${ld.produit.prodId}"/></c:url>">X</a>

									</td>
								</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>


	</c:if>

</form:form>