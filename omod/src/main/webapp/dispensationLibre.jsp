<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie dispensation" 
        otherwise="/login.htm" redirect="/index.htm" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />


<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1560px;background-color:#FCD7DB" >
<ul id="menu">

	<li class=" first">
		<a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>">Menu principal</a>
	</li>
	<li class="active">
		<a href="<c:url value="/module/pharmagest/dispensationLibre.form"/>">Dispensation libre</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/dispensation.form"/>">Dispensation  patient enregistr&eacute;</a>
	</li>
     <li>
				<a href="<c:url value="/module/pharmagest/vente.form"/>">Ventes</a>
	</li>
     <li >
		<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
	</li>
	 <li>
	  <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres</a>
	</li>
     <li >
                <a href="<c:url value="/module/pharmagest/listOrdonnance.form"/>">Annuler une vente </a>
            </li>
	<!-- Add further links here -->
</ul></div>

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
<script type="text/javascript">
	function attribut() {
document.getElementById("ordNum").value="P"+document.getElementById("num").value+"_"+"A"+document.getElementById("age").value+"_"+"S"+document.getElementById("sexe").value;
}
</SCRIPT>

<div>
	<h3> <span style="font-size:36px">.</span> Dispensation libre</h3>
</div>
<br>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/dispensationLibre.form"
	modelAttribute="formulaireOrdonnance"
	commandName="formulaireOrdonnance">
	
	<c:if test="${mess =='echec'}">
	  <div id="openmrs_msg">le patient ${formulaireOrdonnance.numPatient} est enregistr&eacute; dans la base , Veuillez dispenser en 
      <strong>patient enregistr&eacute; </strong></div>
	</c:if>
	<c:if test="${mess =='save'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
	</c:if>
	<c:if test="${mess =='find'}">
		<div id="openmrs_msg">Patient trouvé</div>
	</c:if>
     <c:if test="${mess =='aut'}">
		<div id="openmrs_msg">Vous n'etes pas autorit&eacute; à effectu&eacute; cette dispensation &Agrave; la date du ${dateAut} ant&eacute;rieur &Agrave; l'inventaire</div>
	</c:if>
    <c:if test="${mess =='aut2'}">
		<div id="openmrs_msg">Vous n'etes pas autorit&eacute; à effectu&eacute; cette dispensation &Agrave; la date du ${dateAut} sup&eacute;rieur &Agrave; la date du jour</div>
	</c:if>
    
    <c:if test="${var =='0'}">

		<div>
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td><input type="submit" name="btn_editer"
							id="btn_editer" value="Saisie produits" > </td>
					</tr>
				</tbody>
			</table>

		</div>
<form:hidden path="ordNum" />

		<div>

			<b class="boxHeader"></b>
			<div class="box">
				<div class="searchWidgetContainer" id="findPatients" align="center">
					<table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
							<tr>
								<td width="10%">Programme <span style="color:#F11A0C">*</span></td>
								<td width="16%"><!--<form:select path="pharmProgramme">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />-->
                                <form:select path="pharmProgramme">
                                      <option value="0">-- Choix --</option>
                                       <c:forEach var="item" items="${programmes}">
                                           <option <c:if test="${formulaireOrdonnance.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                                       </c:forEach>
                                </form:select> 
                                                    
													</form:select><form:errors path="pharmProgramme"
														cssClass="error" /></td>
								<td width="73%" rowspan="2"><table width="100%" cellpadding="7"
										cellspacing="0">
										<tbody>
											<tr>
												<td width="17%" rowspan="2">Régime actuel<span style="color:#F11A0C"> *</span></td>
												<td width="28%"><!--<form:select path="pharmRegime">
														<form:option value="0" label="---Choix---" />
														<form:options items="${regimes}" itemValue="regimId"
															itemLabel="regimLib" />
													</form:select>-->
                                                
                                                
                               <form:select path="pharmRegime">
                                      <option value="36">-- Choix --</option>
                                       <c:forEach var="item" items="${regimes}">
                                            <option <c:if test="${formulaireOrdonnance.getPharmRegime().getRegimId()==item.getRegimId()}">selected="selected"</c:if>    				  							value="${item.getRegimId()}">${item.getRegimLib()} </option>
                                       </c:forEach>
                                </form:select> 
                                                    
                                                </td>
												<td width="24%" rowspan="2"><form:label
														path="ordNbreJrsTrai">Nombre de jours de traitement<span style="color:#F11A0C"> *</span></form:label></td>
												<td width="29%"><form:input path="ordNbreJrsTrai" /></td>
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
							          <td><input name="num"  type="hidden" id="num"  /><form:input path="numPatient" /></td>
						            </tr>
							        <tr>
							          <td>Age</td>
							          <td>
						               <form:input path="age" /></td>
						            </tr>
							        <tr>
							          <td>Sexe</td>
							          <td><!--<select name="sexe" id="sexe">
                                          <option value="M">Masculin</option>
                                          <option value="F">F&eacute;minin</option>
                                      </select>-->
                                      <form:select		path="sexe">
											<form:option value="M" label="Masculin" />
                                            <form:option value="F" label="Féminin" />
														
										</form:select>
                                      
                                      </td>
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
												<td width="58%"><!--<form:select
														path="pharmMedecin">
														<form:option value="0" label="---Choix---" />
														<form:options items="${medecins}" itemValue="medId"
															itemLabel="medNom" />
													</form:select>-->
                                                  
                                <form:select path="pharmMedecin">
                                      <option value="0">-- Choix --</option>
                                       <c:forEach var="item" items="${medecins}">
                                            <option <c:if test="${formulaireOrdonnance.getPharmMedecin().getMedId()==item.getMedId()}">selected="selected"</c:if>    				  							value="${item.getMedId()}">${item.getMedNom()} </option>
                                       </c:forEach>
                                </form:select>
                                                  
                                                  
                                                  </td>
											</tr>
											<tr>
												<td><form:errors path="pharmMedecin"
														cssClass="error" /></td>
											</tr>
											<tr>
												<td rowspan="2"><form:label
														path="ordDatePrescrip">Date de prescription</form:label></td>
												<td><!--<form:input path="ordDatePrescrip" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>--> <openmrs_tag:dateField formFieldName="ordDatePrescrip" startValue="${obsDate}" /></td>
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
												<td width="43%">Dispensateur</td>
												<td width="57%"><input name="textfield2" type="text"
													disabled="disabled" id="textfield2"
													value="${formulaireOrdonnance.pharmGestionnairePharma.gestNom}  ${formulaireOrdonnance.pharmGestionnairePharma.gestPrenom}" size="35"></td>
											</tr>
											<tr>
												<td rowspan="2"><form:label
														path="ordDateDispen">Date de dispensation<span style="color:#F11A0C"> *</span></form:label></td>
												<td><!--<form:input path="ordDateDispen" onchange="javascript:daterdv();" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>--><openmrs_tag:dateField formFieldName="ordDateDispen" startValue="${obsDate}" /></td>
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
															<div align="left">Date de fin de traitement</div>
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
  </c:if>
  
        
        <!----------------------------------------------------------------------------------------------------- -->
        
    <c:if test="${var =='1'}">
        <div>
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td><input type="submit" name="btn_enregistrer"
							id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;"> <input
							type="submit" name="reset" id="reset" value="Annuler"></td>
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
								<td colspan="2">Programme</td>
								<td width="1%">:</td>
								<td width="14%">${formulaireOrdonnance.pharmProgramme.programLib}</td>
								<td width="1%">&nbsp;</td>
								<td width="16%">R&eacute;gime actuel</td>
								<td width="1%">:</td>
								<td width="18%">${formulaireOrdonnance.pharmRegime.regimLib}</td>
								<td width="1%">&nbsp;</td>
								<td width="12%">Prescripteur</td>
								<td width="1%">:</td>
								<td width="25%">${formulaireOrdonnance.pharmMedecin.medNom}${formulaireOrdonnance.pharmMedecin.medPrenom}</td>
							</tr>
							<tr>
							  <td width="4%" rowspan="3">Patient</td>
							  <td width="6%">N&deg;</td>
							  <td width="1%">:</td>
							  <td width="14%">${formulaireOrdonnance.numPatient}</td>
							  <td width="1%">&nbsp;</td>
							  <td width="16%">But</td>
							  <td width="1%">:</td>
							  <td width="18%">${formulaireOrdonnance.ordBut}</td>
							  <td width="1%">&nbsp;</td>
							  <td width="12%">Dispensateur</td>
							  <td width="1%">:</td>
							  <td width="25%">${formulaireOrdonnance.pharmGestionnairePharma.gestNom}  ${formulaireOrdonnance.pharmGestionnairePharma.gestPrenom}</td>
				          </tr>
							<tr>
							  <td>Age</td>
							  <td>:</td>
							  <td>${formulaireOrdonnance.age}</td>
							  <td width="1%">&nbsp;</td>
							  <td width="16%">Nombre de jours de traitement</td>
							  <td width="1%">:</td>
							  <td width="18%">${formulaireOrdonnance.ordNbreJrsTrai}</td>
							  <td width="1%">&nbsp;</td>
							  <td width="12%">Date de dispensation<span style="color:#F11A0C"> </span></td>
							  <td width="1%">:</td>
                              <c:set var="d1" value="${formulaireOrdonnance.ordDateDispen}" scope="request" />
                                    <% Date d1=  (Date) request.getAttribute("d1"); 
										SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s1 =  formatter1.format(d1); %>
							  <td width="25%"><%= s1 %></td>
					      </tr>
							<tr>
							  <td>Sexe</td>
							  <td>:</td>
							  <td>${formulaireOrdonnance.sexe}</td>
							  <td width="1%">&nbsp;</td>
							  <td width="16%">Date de prescription</td>
							  <td width="1%">:</td>
                              <c:set var="d2" value="${formulaireOrdonnance.ordDatePrescrip}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2"); 
										SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s2 =  formatter2.format(d2); %>
							  <td width="18%"><%= s2 %></td>
							  <td width="1%">&nbsp;</td>
							  <td width="12%">Date de fin de traitement</td>
							  <td width="1%">:</td>
                              <c:set var="d3" value="${formulaireOrdonnance.ordDateRdv}" scope="request" />
                                    <% Date d3=  (Date) request.getAttribute("d3"); 
										SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s3 =  formatter3.format(d3); %>
							  <td width="25%"><%= s3 %></td>
					      </tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br>
		
        <!----------------------------------------------------------------------------------------------------- -->
        
        
		<br>
		<c:if test="${mess =='accept'}">
			<div id="openmrs_msg">une ligne inserée</div>
		</c:if>
		<c:if test="${mess =='refuse'}">
			<div id="openmrs_msg">Stock insuffisant</div>
		</c:if>
        <c:if test="${mess =='delete'}">
			<div id="openmrs_msg">une ligne spprimée</div>
		</c:if>
		<div>
			<b class="boxHeader"></b>
			<div class="box">

				<table width="100%" border="0" cellpadding="7" cellspacing="0">
					<tbody>
						<tr>
							<td width="13%">&nbsp;</td>
							<td width="23%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">D&eacute;signation <span style="color:#F11A0C">*</span></div></td>
										</tr>
										<tr>
											<td><div align="center">
											
													<!--<form:select path="pharmProduit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
													</form:select>-->
                                                    
                       <form:select path="pharmProduit" cssStyle="width:200px">
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${produits}">
                                    <option <c:if test="${formulaireOrdonnance.getPharmProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										 value="${item.getProdId()}">${item.getFullName()} </option>
                               </c:forEach>
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
							<td width="12%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteDem">Quantité demandée <span style="color:#F11A0C">*</span></form:label>
										  </div></td>
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
							<td width="13%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteServi">Quantité dispensée <span style="color:#F11A0C">*</span></form:label>
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
												id="btn_valider" value="Ajouter" onClick="javascript:attribut();"></td>
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
								<c:set var="d4" value="${ld.lgnDatePerem}" scope="request" />
                                    <% Date d4=  (Date) request.getAttribute("d4"); 
										SimpleDateFormat formatter4 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s4 =  formatter4.format(d4); %>
								<td><%= s4 %></td>
								<td>${ld.ldPrixUnit}</td>
								<td>${ld.ldQteServi * ld.ldPrixUnit}</td>
								<td>
                                <a
										href="<c:url value="/module/pharmagest/dispensationLibre.form">
									  <c:param name="modifId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/>                                          
								    </c:url>">Modifier</a>|
                                <a
									href="<c:url value="/module/pharmagest/dispensationLibre.form"><c:param name="paramId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/></c:url>">Supprimer</a>

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>

	<!--<script type="text/javascript">
		$("#pharmRegime").attr('required', true);
		$("#ordNbreJrsTrai").attr('required', true);
		$("#ordDateDispen").attr('required', true);
		/*$("#pharmProduit").attr('required', true);
		$("#ldQteDem").attr('required', true);
		$("#ldQteServi").attr('required', true);*/
	</script>-->

</c:if>

<script type="text/javascript">
	document.getElementById("ordDateDispen").onchange = function() {daterdv()};
</SCRIPT>

</form:form>