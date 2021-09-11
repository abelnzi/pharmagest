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

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
	<li class=" first">
		<a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>">Menu principal</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/dispensationLibre.form"/>">Dispensation libre</a>
	</li>
    <li class="active">
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
	
	<c:set var="finTraitementAnterieur" value="${finTraitementAnterieur}" scope="request" />
    <% String finTraitementAnterieur= (String) request.getAttribute("finTraitementAnterieur"); 
	   System.out.println("------------------finTraitementAnterieur ici----------- "+finTraitementAnterieur);
	 %>

<script type="text/javascript">
	function daterdv() {
		var nbjRest = parseFloat(document
				.getElementById("jourRestant").value);
		console.log('nbjRest 1 '+nbjRest);
		var nbjPerdu = parseInt(document
				.getElementById("jrsPerdu").value);
		if(isNaN(nbjPerdu))nbjPerdu=0;
		if(isNaN(nbjRest))nbjRest=0;
				
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
		if(nbjRest>=0){

			datenew.setDate(datenew.getDate() + nbj+nbjRest-nbjPerdu);
		}else{
			datenew.setDate(datenew.getDate() + nbj);
			}
		console.log('datenew add'+datenew);
		var annee = datenew.getFullYear()

		var mois = datenew.getMonth() < 9 ? "0" + (datenew.getMonth() + 1)
				: (datenew.getMonth() + 1);
		var jour = datenew.getDate() < 10 ? "0" + datenew.getDate() : datenew
				.getDate();		
		if(!isNaN(nbj) && !isNaN(datenew)){document.getElementById("ordDateRdv").value = jour+ "/" + mois + "/" + annee;}
	}
	
	function getJoursRestant() {
		//var d = document.getElementById("dernierRDV").value;
		//var d=String(<%= finTraitementAnterieur %>);
		var d='${finTraitementAnterieur}';
		//alert("d: " + d);
		if( !(d === undefined || d === null || d === '') ){
			var j = d.substring(0, 2);
			var m = d.substring(3, 5);
			var a = d.substring(6, 11);
			var dernierRDV = new Date(m + "/" + j + "/" + a);
 			
			var d2 = document.getElementById("ordDateDispen").value;
			var j2 = d2.substring(0, 2);
			var m2 = d2.substring(3, 5);
			var a2 = d2.substring(6, 11);
			var dispens = new Date(m2 + "/" + j2 + "/" + a2);
			
			var time_diff = dernierRDV.getTime() - dispens.getTime();
			var days_Diff = Math.ceil(time_diff / (1000 * 3600 * 24));
						//alert("days_Diff: " + days_Diff);
			if(days_Diff>0){
				document.getElementById("jourRestant").value=days_Diff;
				document.getElementById("jourRestant").style="color:#F3F3F3;background:#F70606";
			}else{
				document.getElementById("jourRestant").value=0;
			}
			
		}
		
		
	}
	
</SCRIPT>


<div>
	<h3><span style="font-size:36px">.</span> Dispensation pour patient enregistré</h3>
</div>
<br>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/dispensation.form"
	modelAttribute="formulaireOrdonnance"
	commandName="formulaireOrdonnance">
	<!--<form action="${pageContext.request.contextPath}/module/pharmagest/dispensation.form" method="post">-->
	<c:if test="${bar !=1}">
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
		<div id="openmrs_msg">${msgBuffer}</div>
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
	<c:if test="${bar ==1}">
	 <c:if test="${var =='0'}">
		<div>
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td><input type="submit" name="btn_editer"
							id="btn_editer" value="Saisie produits" ></td>
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
				        <td width="13%"><!--<form:select path="pharmProgramme">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />-->
				          <form:select path="pharmProgramme">
				            <option value="0">-- Choix --</option>
				            <c:forEach var="item" items="${programmes}">
				              <option <c:if test="${formulaireOrdonnance.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
			                </c:forEach>
			              </form:select>
				          </form:select>
				          <form:errors path="pharmProgramme"
														cssClass="error" /></td>
				        <td width="12%">Dernier r&eacute;gime</td>
				        <td width="13%"><input type="text" disabled="disabled"
													value="${regime}" readonly /></td>
				        <td colspan="2">Date de la derni&egrave;re dispensation</td>
				        <td width="31%"><input type="text" id="dernierRDV"
													value="${rdv}" readonly /></td>
			          </tr>
				      <tr>
				        <td width="4%" rowspan="4">Patient</td>
				        <td width="3%">N&deg;</td>
				        <td width="13%"><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.idParam}" readonly /></td>
				        <td width="12%" rowspan="2">Dispensation actuelle<span style="color:#F11A0C"> *</span></td>
				        <td width="13%" rowspan="2"><form:select path="pharmRegime">
				          <option value="36">-- Choix --</option>
				          <c:forEach var="item" items="${regimes}">
				            <option <c:if test="${formulaireOrdonnance.getPharmRegime().getRegimId()==item.getRegimId()}">selected="selected"</c:if>    				  							value="${item.getRegimId()}">${item.getRegimLib()} </option>
			              </c:forEach>
				          </form:select>
				          <form:errors path="pharmRegime"
														cssClass="error" /></td>
				        <td colspan="2" rowspan="2">Nombre de jours de traitement restant <span style="color:#F11A0C">*</span></td>
				        <td width="31%" rowspan="2">
							<input type="text" readonly id="jourRestant" style="color:#F3F3F3;background:#F70606" />
						</td>
                         <c:set var="jr" value="${jourRestant}" scope="request" />
                         <% Long jr=  (Long) request.getAttribute("jr");	  %>
                         <script type="text/javascript">
						 function jourReste() {
							 
							 var jr = parseInt(<%= jr %>);
							 //alert('-------------'+jr);
							 if(isNaN(jr))jr=0;
							 if(jr>30) {
								 alert('Client dispose plus de 30 j de traitement, voulez vous continuer la saisie');
								 }
							 }	
						//document.getElementById("jourRestant").onchange = function() {jourReste()};					   
						 </script>
			          </tr>
				      <tr>
				        <td>N&deg; UPID</td>
				        <td width="13%"><input type="text" disabled="disabled"
													value="${UPID}" readonly /></td>
			          </tr>
				      <tr>
				        <td>Age</td>
				        <td width="13%"><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.age}" readonly /></td>
				        <td width="12%" rowspan="2">But</td>
				        <td colspan="2" rowspan="2"><table width="100%">
				          <tr>
				            <td width="38%">Non applicable:
				              <form:radiobutton
																	path="ordBut" value="NA" /></td>
				            <td width="17%">PEC:
				              <form:radiobutton
																	path="ordBut" value="PEC" /></td>
				            <td width="20%">PTME:
				              <form:radiobutton
																	path="ordBut" value="PTME" /></td>
				            <td width="18%">AES:
				              <form:radiobutton
																	path="ordBut" value="AES" /></td>
				            <td width="7%"><form:errors
																	path="ordBut" cssClass="error" /></td>
			              </tr>
				          </table></td>
				        <td width="8%" rowspan="2">Dispensateur</td>
				        <td width="31%" rowspan="2"><input name="textfield" type="text"
													disabled="disabled" id="textfield"
													value="${formulaireOrdonnance.pharmGestionnairePharma.gestNom}  ${formulaireOrdonnance.pharmGestionnairePharma.gestPrenom}" size="35"></td>
			          </tr>
				      <tr>
				        <td>Sexe</td>
				        <td width="13%"><input type="text" disabled="disabled"
									value="${formulaireOrdonnance.sexe}" readonly /></td>
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
				        <td>Prescripteur</td>
				        <td>&nbsp;</td>
				        <td width="21%"><!--<form:select
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
			              </form:select>				          <form:errors path="pharmMedecin"
														cssClass="error" /></td>
				        <td>Nombre de jour de produits perdu</td>
				        <td>&nbsp;</td>
				        <td width="15%"><form:input path="jrsPerdu" />				          <form:errors path="jrsPerdu"
														cssClass="error" /></td>
				        <td>Nombre de jours de traitement<span style="color:#F11A0C"> *</span></td>
				        <td>&nbsp;</td>
				        <td><form:input path="ordNbreJrsTrai" />				          <form:errors path="ordNbreJrsTrai"
														cssClass="error" /></td>
			          </tr>
				      <tr>
				        <td width="11%">Date de prescription</td>
				        <td width="1%">&nbsp;</td>
				        <td><!--<form:input path="ordDatePrescrip" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>-->
				          <openmrs_tag:dateField formFieldName="ordDatePrescrip" startValue="${obsDate}" />				          <form:errors path="ordDatePrescrip"
														cssClass="error" /></td>
				        <td width="18%">Date de dispensation<span style="color:#F11A0C">*</span></td>
				        <td width="1%">&nbsp;</td>
				        <td><!--<form:input path="ordDateDispen"  onchange="javascript:daterdv();" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>-->
				          <openmrs_tag:dateField formFieldName="ordDateDispen" startValue="${obsDate}" />				          <form:errors path="ordDateDispen"
														cssClass="error" /></td>
				        <td width="17%">Date de fin de traitement</td>
				        <td width="1%">&nbsp;</td>
				        <td width="15%"><div align="left">
				          <form:input path="ordDateRdv"
															readonly="true"  />
				          </div>				          <form:errors path="ordDateRdv"
														cssClass="error" /></td>
			          </tr>
			        </tbody>
			      </table>
				</div>
			</div>
		</div>
		<br>
        </c:if>
        <!--------------------------------------------------------------------------------------------------------------->
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
							  <td width="4%" rowspan="4">Patient</td>
							  <td width="6%">N&deg;</td>
							  <td width="1%">:</td>
							  <td width="14%">${formulaireOrdonnance.numPatient}</td>
							  <td width="1%" rowspan="2">&nbsp;</td>
							  <td width="16%" rowspan="2">But</td>
							  <td width="1%" rowspan="2">:</td>
							  <td width="18%" rowspan="2">${formulaireOrdonnance.ordBut}</td>
							  <td width="1%" rowspan="2">&nbsp;</td>
							  <td width="12%" rowspan="2">Dispensateur</td>
							  <td width="1%" rowspan="2">:</td>
							  <td width="25%" rowspan="2">${formulaireOrdonnance.pharmGestionnairePharma.gestNom}  ${formulaireOrdonnance.pharmGestionnairePharma.gestPrenom}</td>
				          </tr>
							<tr>
							  <td>N&deg; UPID</td>
							  <td width="1%">:</td>
							  <td width="14%">${UPID}</td>
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
        
        <!--------------------------------------------------------------------------------------------------------------->
        
        
        
        
		<c:if test="${mess =='accept'}">
			<div id="openmrs_msg">une ligne inserée</div>
		</c:if>
		<c:if test="${mess =='refuse'}">
			<div id="openmrs_msg">Stock insuffisant</div>
		</c:if>
        <c:if test="${mess =='supprim'}">
			<div id="openmrs_msg">une ligne supprimée</div>
		</c:if>
		 <c:if test="${mess =='notsave'}">
			<div id="openmrs_msg">Selectionner un produit ARV</div>
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
											<td><div align="center">D&eacute;signation<span style="color:#F11A0C">*</span></div></td>
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
							<td width="11%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteServi">Quantité dispensée<span style="color:#F11A0C"> *</span></form:label>
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
							<td width="28%"><table width="100%" border="0"
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
							<td width="6%"><table width="100%" border="0"
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
							<td width="14%">Code article</td>
							<td width="21%">DCI Article</td>
							<td width="13%">Quantité demandée</td>
							<td width="11%">Quantité dispensée</td>
							<td width="9%">N&deg; lot</td>
							<td width="10%">Date de p&eacute;remption</td>
							<td width="8%">Prix</td>
							<td width="8%">Total</td>
							<td width="6%">Action</td>
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
										href="<c:url value="/module/pharmagest/dispensation.form">
									  <c:param name="modifId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/>                                          
								    </c:url>">Modifier</a>|
                                <a
									href="<c:url value="/module/pharmagest/dispensation.form"><c:param name="paramId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/></c:url>">Supprimer</a>

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>

		</c:if>
	</c:if>

<script type="text/javascript">
	document.getElementById("ordDateDispen").onchange = function() {getJoursRestant();daterdv()};
	document.getElementById("ordNbreJrsTrai").onchange = function() {daterdv()};
	document.getElementById("jrsPerdu").onchange = function() {daterdv()};
</SCRIPT>
</form:form>