<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie vente saisie" 
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
	<li >
		<a href="<c:url value="/module/pharmagest/dispensationLibre.form"/>">Dispensation libre</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/dispensation.form"/>">Dispensation  patient enregistr&eacute;</a>
	</li>
     <li class="active">
				<a href="<c:url value="/module/pharmagest/vente.form"/>">Ventes</a>
	</li>
     <li >
		<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
	</li>
	 <li>
	  <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres</a>
	</li>
    <li>
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

<!-- Le JS... -->
<script type="text/javascript">

function afficher() { // On d&eacute;clare la fonction toggle_div qui prend en param le bouton et un id
	
	document.getElementById("findassure").style.display="none";
	document.getElementById("findclient").style.display="none";
	document.getElementById("info_generale").style.display="none";
	
	if(document.getElementById("typeVente2").checked) { 
		document.getElementById("findassure").style.display="block";
		document.getElementById("findclient").style.display="none";
		document.getElementById("info_generale").style.display="block";
	}else if(document.getElementById("typeVente1").checked) { 
		document.getElementById("findclient").style.display="block";
		document.getElementById("findassure").style.display="none";
		document.getElementById("info_generale").style.display="block";
	} 
}

</script>
<div>
	<h3> <span style="font-size:36px">.</span>Vente</h3>
</div>
<br>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/vente.form"
	modelAttribute="formulaireVente"
	commandName="formulaireVente">
	
	<c:if test="${mess =='echec'}"></c:if>
	<c:if test="${mess =='save'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
	</c:if>
	<c:if test="${mess =='IC'}">
		<div id="openmrs_msg">Patient non trouv&eacute;</div>
	</c:if>
     <c:if test="${mess =='aut'}">
		<div id="openmrs_msg">Vous n'etes pas autorit&eacute; à effectu&eacute; cette dispensation &Agrave; la date du ${dateAut}</div>
	</c:if>
    <c:if test="${var =='0'}">

		<div>
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td width="100%"><input type="submit" name="btn_editer"
							id="btn_editer" value="Saisie produits" > </td>
					</tr>
					<tr>
					  <td align="center">
                     Vente directe : 
                      <!--<input type="radio" name="typeVente" value="D" onclick="afficher(this)" id="vente_direct" />-->
                     <form:radiobutton path="typeVente" value="D" onclick="afficher(this)" />
                      Vente indirecte :
                      <!--<input type="radio" name="typeVente" value="I" id="vente_indirect" onclick="afficher(this)" />-->
                      <form:radiobutton path="typeVente" value="I" onclick="afficher(this)" />
                      </td>
				  </tr>
				</tbody>
			</table>

		</div><br>


		<div>

		  <b class="boxHeader"></b>
		  <div class="box">
				<div class="searchWidgetContainer" id="findassure" align="center">
				  <table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
					
					<tr>
					  <td colspan="6" align="center"><span style="color:#999">--------------------------Informations assur&eacute;---------------------------</span></td>
				  </tr>
					<tr>
					  <td>&nbsp;</td>
					  <td>Est un nouvel assur&eacute; ?</td>
					  <td colspan="4">Oui
					   <!-- <input type="radio" name="new_assur" value="O" onclick="new_assurer(this)" id="id_oui" />-->
                        <form:radiobutton path="newAssur" value="O" onclick="new_assurer(this)" />
                        &nbsp;&nbsp;Non
                      <!--<input type="radio" name="new_assur" value="N" id="id_non" onclick="new_assurer(this)" />
-->                      <form:radiobutton path="newAssur" value="N" onclick="new_assurer(this)" />
                      </td>
					  </tr>
					<tr>
					  <td> Assurance <span style="color:#F11A0C">*</span></td>
					  <td><form:select path="assurance"  >
					    <option value="0">-- Choix --</option>
					    <c:forEach var="item" items="${assurances}">
					      <option <c:if test="${formulaireVente.getAssurance().getAssurId()==item.getAssurId()}">selected="selected"</c:if>    value="${item.getAssurId()}">${item.getAssurLib()} </option>
				        </c:forEach>
					    </form:select>
					    <form:errors path="assurance" cssClass="error" />
					    &nbsp; &nbsp; &nbsp;</td>
					  <td>&nbsp;Taux <span style="color:#F11A0C">*</span></td>
					  <td><form:input path="taux" size="10"  />
					    %
					    <form:errors path="taux" cssClass="error" /></td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  </tr>
					<tr>
					  <td width="16%">Num&eacute;ro Assur&eacute; <span style="color:#F11A0C">*</span>
                      </td>
					  <td width="20%"><form:input path="numPatient" size="20" /><form:errors path="numPatient" cssClass="error"   /></td>
					  <td width="8%">&nbsp;Nom:<span style="color:#F11A0C">*</span> </td>
					  <td width="15%"><form:input path="cliNom" size="20"  /><form:errors path="cliNom" cssClass="error"   /></td>
					  <td width="7%">&nbsp;Prenoms:<span style="color:#F11A0C">*</span> </td>
					  <td width="34%"><form:input path="cliPrenom" size="40"  /><form:errors path="cliPrenom" cssClass="error"  /></td>
			        </tr>
					<tr>
					  <td>
                      Date de naissance <span style="color:#F11A0C">*</span>                     
                      
                      </td>
					  <td><openmrs_tag:dateField formFieldName="cliDateNaiss" startValue="${obsDate}" /><form:errors path="cliDateNaiss" cssClass="error"   /></td>
					  <td>&nbsp;T&eacute;l&eacute;phone:</td>
					  <td><form:input path="cliTel1" size="20" /><form:errors path="cliTel1" cssClass="error"   /></td>
					  <td>Genre :<span style="color:#F11A0C">*</span></td>
					  <td><form:radiobutton path="cliGenre" value="M" label="Masculin" />
					    <form:radiobutton path="cliGenre" value="F" label="Féminin" />
                          <form:errors path="cliGenre" cssClass="error" />
                      </td>
					  </tr>
			        </tbody>
		          </table>
            </div><br />
              <div class="searchWidgetContainer" id="findclient" align="center">
                 <table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
					
					<tr>
					  <td colspan="6" align="center"><span style="color:#999">--------------------------Informations Client---------------------------</span></td>
				  </tr>
					<tr>
					  <td width="9%">&nbsp;Nom:  &nbsp;&nbsp;&nbsp;  
                      </td>
					  <td width="15%"><form:input path="cliNomClient" size="20"   />
					    <form:errors path="cliNomClient" cssClass="error" /></td>
					  <td width="10%">&nbsp;Prenoms: </td>
					  <td width="22%"><form:input path="cliPrenomClient" size="40"   />
					    <form:errors path="cliPrenomClient" cssClass="error" /></td>
					  <td width="14%"> Date de naissance 
                      </td>
					  <td width="30%"><openmrs_tag:dateField formFieldName="cliDateNaissClient" startValue="${obsDate}"   />
					    <form:errors path="cliDateNaissClient" cssClass="error" /></td>
					  </tr>
					<tr>
					  <td>
                      &nbsp;T&eacute;l&eacute;phone: &nbsp;&nbsp;&nbsp;
                      </td>
					  <td><form:input path="cliTel1Client" size="20"  />
					    <form:errors path="cliTel1Client" cssClass="error" /></td>
					  <td>
                      &nbsp;Type de client 
                      </td>
					  <td><form:select path="cliType">
					    <option value="0">-- Choix --</option>
					    <option value="Entreprise">Entreprise</option>
                        <option value="Particulier">Particulier</option>
					    </form:select>
					    <form:errors path="cliType" cssClass="error" />
  &nbsp; &nbsp; &nbsp;</td>
					  <td>&nbsp;Genre :</td>
					  <td>&nbsp;
					    <form:radiobutton path="cliGenreClient" value="M" label="Masculin" />
					    <form:radiobutton path="cliGenreClient" value="F" label="Féminin" />
					    <form:errors path="cliGenre" cssClass="error" /></td>
					  </tr>
		           </tbody>
	            </table>
            </div>
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
				<div class="searchWidgetContainer" id="info_generale" align="center">
					<table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
                        <tr>
                       	  <td colspan="8" style="color:#999" align="center">--------------------------Informations g&eacute;n&eacute;rales---------------------------</td>
                       	  </tr>
                        <tr>
                          <td width="8%" >N&deg; de l'ordonnance</td>
                          <td width="21%" ><form:input path="ordNum" />
                            <form:errors path="ordNum" cssClass="error" /></td>
                          <td width="9%" >&nbsp;Programme <span style="color:#F11A0C">*</span></td>
                          <td width="22%" ><form:select path="pharmProgramme">
                            <option value="0">-- Choix --</option>
                            <c:forEach var="item" items="${programmes}">
                              <option <c:if test="${formulaireVente.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                            </c:forEach>
                          </form:select>
                            <form:errors path="pharmProgramme" cssClass="error" /></td>
                          <td width="10%"> Service de provenance </td>
                          <td width="9%">&nbsp;
                            <form:input path="ordService" />
                            <form:errors path="ordService" cssClass="error" /></td>
                          <td width="16%"> Nombre de jours de traitement</td>
                          <td width="5%">&nbsp;
                            <form:input path="ordNbreJrsTrai" />
                            <form:errors path="ordNbreJrsTrai" cssClass="error" /></td>
                          </tr>
							<tr>
								<td colspan="2"><table width="94%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="30%" rowspan="2">Prescripteur</td>
												<td width="70%">
                                                  
                                <form:select path="pharmMedecin">
                                      <option value="0">-- Choix --</option>
                                       <c:forEach var="item" items="${medecins}">
                                            <option <c:if test="${formulaireVente.getPharmMedecin().getMedId()==item.getMedId()}">selected="selected"</c:if>    				  							value="${item.getMedId()}">${item.getMedNom()} </option>
                                       </c:forEach>
                                </form:select>                                                     
                                              </td>
											</tr>
											<tr>
												<td><form:errors path="pharmMedecin" cssClass="error" /></td>
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
								<td colspan="2"><table width="102%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="45%">Dispensateur</td>
												<td width="55%"><input name="textfield2" type="text"
													disabled="disabled" id="textfield2"
													value="${formulaireVente.pharmGestionnairePharma.gestNom}  ${formulaireVente.pharmGestionnairePharma.gestPrenom}" size="35"></td>
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
								<td colspan="4"><table width="101%" border="0"
										cellpadding="7" cellspacing="0">
										<tbody>
											<tr>
												<td width="41%" rowspan="2"><div align="center">
														<form:label path="ordDateRdv">
															<div align="left">Date de fin de traitement</div>
														</form:label>
													</div></td>
												<td width="59%"><div align="left">
														<form:input path="ordDateRdv"
															readonly="true"  />

													</div></td>
											</tr>
											<tr>
												<td><form:errors path="ordDateRdv"
														cssClass="error" /></td>
											</tr>
											<tr>
												<td height="34" rowspan="2">&nbsp;Hospitalis&eacute;:</td>
												<td></td>
											</tr>
											<tr>
												<td><form:radiobutton path="ordHospi" value="H" label="Oui" />
					    <form:radiobutton path="ordHospi" value="NH" label="Non" />
                          <form:errors path="ordHospi" cssClass="error" /></td>
											</tr>
										</tbody>
									</table></td>
							</tr>
						</tbody>
					</table>
				</div>
           </div></div>
                
                
             
  </c:if>
         
    <!----------------------------------------------------------------------------------------------------- -->
        
    <c:if test="${var =='1'}"><br>
		
        <!----------------------------------------------Entête en lecture------------------------------------------------------- -->
        
           
     
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
       
        <c:if test="${formulaireVente.getTypeVente() =='I'}">
		       
        <div>
			<b class="boxHeader"></b>
		<div class="box">
       
       <div class="searchWidgetContainer" id="findinfos" align="center">
   		  <table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
					
					<tr>
					  <td colspan="6" align="center"><span style="color:#999">--------------------------Informations assur&eacute;---------------------------</span></td>
				  </tr>
					<tr>
					  <td width="16%">Num&eacute;ro Assur&eacute; <span style="color:#F11A0C">*</span>
                      </td>
					  <td width="20%">${formulaireVente.numPatient}</td>
					  <td width="8%">&nbsp;Nom: </td>
					  <td width="15%">${formulaireVente.cliNom}</td>
					  <td width="7%">&nbsp;Prenoms: </td>
					  <td width="34%">${formulaireVente.cliPrenom}</td>
			        </tr>
					<tr>
					  <td>
                      Date de naissance <span style="color:#F11A0C">*</span>                     
                      
                      </td>
                      <c:set var="dn" value="${formulaireVente.cliDateNaiss}" scope="request" />
           		      <% Date dn=  (Date) request.getAttribute("dn"); 
										SimpleDateFormat formatterdn = new SimpleDateFormat("dd/MM/yyyy");
										String sn="";
										if(dn!=null) sn =  formatterdn.format(dn); %>
					  
					  <td><%= sn %></td>
					  <td>&nbsp;T&eacute;l&eacute;phone:</td>
					  <td>${formulaireVente.cliTel1}</td>
					  <td>Genre :</td>
					  <td>
                      <c:if test="${formulaireVente.cliGenre=='M'}">Masculin</c:if> 
                      <c:if test="${formulaireVente.cliGenre=='F'}">Féminin</c:if>
                      
                      </td>
					  </tr>
					<tr>
					  <td>
                      Assurance <span style="color:#F11A0C">*</span>
                            
                      </td>
					  <td>${formulaireVente.assurance.assurLib}&nbsp; &nbsp; &nbsp;</td>
					  <td>&nbsp;Taux <span style="color:#F11A0C">*</span>
                      </td>
					  <td>${formulaireVente.taux} % </td>                      
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  </tr>
			        </tbody>
	            </table>
           		<table width="100%" border="0" cellpadding="7" cellspacing="0">
           		  <tbody>
           		    <tr>
           		      <td colspan="8" style="color:#999" align="center">--------------------------Informations g&eacute;n&eacute;rales---------------------------</td>
       		        </tr>
           		    <tr>
           		      <td width="6%" >N&deg; de l'ordonnance</td>
           		      <td width="15%" >${formulaireVente.ordNum}</td>
           		      <td width="6%" >Programme</td>
           		      <td width="21%" >${formulaireVente.pharmProgramme.programLib} </td>
           		      <td width="6%">Service de provenance</td>
           		      <td width="15%">&nbsp;${formulaireVente.ordService}</td>
           		      <td width="8%">&nbsp;Hospitalis&eacute;:</td>
           		      <td width="23%">
                       <c:if test="${formulaireVente.ordHospi=='H'}">Oui</c:if> 
                      <c:if test="${formulaireVente.ordHospi=='NH'}">Non</c:if>
                      
                      </td>
       		        </tr>
           		    <tr>
           		      <td> Nombre de jours de traitement</td>
           		      <td>&nbsp;${formulaireVente.ordNbreJrsTrai} </td>
           		      <td>Dispensateur</td>
           		      <td>&nbsp;
           		        <input name="textfield" type="text"
													disabled="disabled" id="textfield"
													value="${formulaireVente.pharmGestionnairePharma.gestNom}  ${formulaireVente.pharmGestionnairePharma.gestPrenom}" size="35"></td>
           		      <td>Prescripteur</td>
           		      <td colspan="3">&nbsp;${formulaireVente.pharmMedecin.medNom}${formulaireOrdonnance.pharmMedecin.medPrenom}</td>
       		        </tr>
           		    <tr>
           		      <td><form:label path="ordDatePrescrip">Date de prescription</form:label></td>
                      <c:set var="dp" value="${formulaireVente.ordDatePrescrip}" scope="request" />
                      <% Date dp=  (Date) request.getAttribute("dp"); 
										SimpleDateFormat formatterdp = new SimpleDateFormat("dd/MM/yyyy");
										String sp="";
										if(dp!=null) sp =  formatterdp.format(dp); %>
								
           		      <td><%= sp %></td>
           		      <td><form:label path="ordDateDispen">Date de dispensation</form:label></td>
                          <c:set var="dd" value="${formulaireVente.ordDateDispen}" scope="request" />
                      <% Date dd=  (Date) request.getAttribute("dd"); 
										SimpleDateFormat formatterdd = new SimpleDateFormat("dd/MM/yyyy");
										String sd="";
										if(dd!=null) sd =  formatterdd.format(dd); %>
								

           		      <td><%= sd %></td>
           		      <td>&nbsp;
           		        <form:label path="ordDateRdv">
           		          <div align="left">Date de fin de traitement</div>
       		            </form:label></td>
                        <c:set var="dr" value="${formulaireVente.ordDateRdv}" scope="request" />
                      <% Date dr=  (Date) request.getAttribute("dr"); 
										SimpleDateFormat formatterdr = new SimpleDateFormat("dd/MM/yyyy");
										String sr="";
										if(dr!=null)  sr =  formatterdr.format(dr); %>
           		      <td colspan="3"><%= sr %></td>
       		        </tr>
       		      </tbody>
   		  </table>
       </div></div></div> 
       
       </c:if>
              
                 <!----------------------------- affichage des informations ------------------------------------------------>
       <c:if test="${formulaireVente.getTypeVente() =='D'}">
           <div>
			<b class="boxHeader"></b>
		    <div class="box">
                 <div class="searchWidgetContainer" id="findinfos2" align="center">
           		<table width="100%" height="130" border="0" cellpadding="7" cellspacing="0">
						<tbody>
					
					<tr>
					  <td colspan="6" align="center"><span style="color:#999">--------------------------Informations Client---------------------------</span></td>
				  </tr>
					<tr>
					  <td width="13%">&nbsp;Nom: </td>
					  <td width="18%">${formulaireVente.cliNomClient}</td>
					  <td width="15%">&nbsp;Prenoms: </td>
					  <td width="22%">${formulaireVente.cliPrenomClient}</td>
					  <td width="16%">&nbsp;Genre :</td>
					  <td width="16%">
                      <c:if test="${formulaireVente.cliGenreClient=='M'}">Masculin</c:if> 
                      <c:if test="${formulaireVente.cliGenreClient=='F'}">Féminin</c:if>
                      
                      </td>
					  </tr>
					<tr>
					  <td>
                      Date de naissance</td>
                      <c:set var="dn2" value="${formulaireVente.cliDateNaissClient}" scope="request" />
           		      <% Date dn2=  (Date) request.getAttribute("dn2"); 
										SimpleDateFormat formatterdn2 = new SimpleDateFormat("dd/MM/yyyy");
										String sn2="";
										if(dn2!=null)  sn2 =  formatterdn2.format(dn2); %>
					  <td><%= sn2 %></td>
					  <td>&nbsp;T&eacute;l&eacute;phone:</td>
					  <td>${formulaireVente.cliTel1Client}</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  </tr>
			        </tbody>
	            </table>
           		<table width="100%" border="0" cellpadding="7" cellspacing="0">
           		  <tbody>
           		    <tr>
           		      <td colspan="8" style="color:#999" align="center">--------------------------Informations g&eacute;n&eacute;rales---------------------------</td>
       		        </tr>
           		    <tr>
           		      <td width="6%" >N&deg; de l'ordonnance</td>
           		      <td width="15%" >${formulaireVente.ordNum}</td>
           		      <td width="6%" >Programme</td>
           		      <td width="21%" >${formulaireVente.pharmProgramme.programLib} </td>
           		      <td width="6%">Service de provenance</td>
           		      <td width="15%">&nbsp;${formulaireVente.ordService}</td>
           		      <td width="8%">&nbsp;Hospitalis&eacute;:</td>
           		      <td width="23%">
                      <c:if test="${formulaireVente.ordHospi=='H'}">Oui</c:if> 
                      <c:if test="${formulaireVente.ordHospi=='NH'}">Non</c:if>
                      </td>
       		        </tr>
           		    <tr>
           		      <td> Nombre de jours de traitement</td>
           		      <td>&nbsp;${formulaireVente.ordNbreJrsTrai} </td>
           		      <td>Dispensateur</td>
           		      <td>&nbsp;
           		        <input name="textfield3" type="text"
													disabled="disabled" id="textfield3"
													value="${formulaireVente.pharmGestionnairePharma.gestNom}  ${formulaireVente.pharmGestionnairePharma.gestPrenom}" size="35"></td>
           		      <td>Prescripteur</td>
           		      <td colspan="3">&nbsp;${formulaireVente.pharmMedecin.medNom}${formulaireOrdonnance.pharmMedecin.medPrenom}</td>
       		        </tr>
           		    <tr>
           		      <td><form:label path="ordDatePrescrip">Date de prescription</form:label></td>
           		      <c:set var="dp2" value="${formulaireVente.ordDatePrescrip}" scope="request" />
           		      <% Date dp2=  (Date) request.getAttribute("dp2"); 
										SimpleDateFormat formatterdp2 = new SimpleDateFormat("dd/MM/yyyy");
										String sp2="";
										if(dp2!=null)  sp2 =  formatterdp2.format(dp2); %>
           		      <td><%= sp2 %></td>
           		      <td><form:label path="ordDateDispen">Date de dispensation</form:label></td>
           		      <c:set var="dd2" value="${formulaireVente.ordDateDispen}" scope="request" />
           		      <% Date dd2=  (Date) request.getAttribute("dd2"); 
										SimpleDateFormat formatterdd2 = new SimpleDateFormat("dd/MM/yyyy");
										String sd2="";
										if(dd2!=null) sd2 =  formatterdd2.format(dd2); %>
           		      <td><%= sd2 %></td>
           		      <td>&nbsp;
           		        <form:label path="ordDateRdv">
           		          <div align="left">Date de fin de traitement</div>
       		            </form:label></td>
           		      <c:set var="dr2" value="${formulaireVente.ordDateRdv}" scope="request" />
           		      <% Date dr2=  (Date) request.getAttribute("dr2"); 
										SimpleDateFormat formatterdr2 = new SimpleDateFormat("dd/MM/yyyy");
										String sr2="";
										if(dr2!=null) sr2 =  formatterdr2.format(dr2); %>
           		      <td colspan="3"><%= sr2 %></td>
       		        </tr>
       		      </tbody>
         		  </table>
                </div>       
        </div></div>
       </c:if>
        <!----------------------------------------------------------------------------------------------------- -->
        
        
		<br>
		<c:if test="${mess =='accept'}">
			<div id="openmrs_msg">une ligne inser&eacute;e</div>
		</c:if>
		<c:if test="${mess =='refuse'}">
			<div id="openmrs_msg">Stock insuffisant</div>
		</c:if>
        <c:if test="${mess =='delete'}">
			<div id="openmrs_msg">une ligne spprim&eacute;e</div>
		</c:if>
		<div>
			<b class="boxHeader"></b>
			<div class="box">

				<table width="100%" border="0" cellpadding="7" cellspacing="0">
					<tbody>
						<tr>
							<td width="7%">&nbsp;</td>
							<td width="31%"><table width="100%" border="0"
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
                                    <option <c:if test="${formulaireVente.getPharmProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										 value="${item.getProdId()}">${item.getFullName()} </option>
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
							<td width="11%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteDem">Quantit&eacute; demand&eacute;e <span style="color:#F11A0C">*</span></form:label>
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
							<td width="36%"><table width="100%" border="0"
									cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center">
													<form:label path="ldQteServi">Quantit&eacute; dispens&eacute;e <span style="color:#F11A0C">*</span></form:label>
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
							<td width="15%">
								<table width="100%" border="0" cellspacing="0">
									<tbody>
										<tr>
											<td><div align="center"></div></td>
										</tr>
										<tr>
											<td><input type="submit" name="btn_valider"
												id="btn_valider" value="Ajouter" ></td>
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
							<td width="15%">DCI Article</td>
							<td width="10%">Quantit&eacute; demand&eacute;e</td>
							<td width="10%">Quantit&eacute; dispens&eacute;e</td>
							<td width="9%">N&deg; lot</td>
							<td width="7%">Date de p&eacute;remption</td>
							<td width="8%">Prix</td>
							<td width="8%">Total</td>
<c:if test="${formulaireVente.typeVente =='I'}">	<td width="3%">Part Assur&eacute;(e)</td></c:if>
<c:if test="${formulaireVente.typeVente =='I'}">	<td width="3%">Part Assurance</td> </c:if>
							<td width="13%">Action</td>
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
<c:if test="${formulaireVente.typeVente =='I'}"><td>${ld.ldQteServi * ld.ldPrixUnit*(100-formulaireVente.taux)/100}</td></c:if>
<c:if test="${formulaireVente.typeVente =='I'}"><td>${ld.ldQteServi * ld.ldPrixUnit*formulaireVente.taux/100}</td></c:if>
								<td>
                                <a
										href="<c:url value="/module/pharmagest/vente.form">
									  <c:param name="modifId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/>                                          
								    </c:url>">Modifier</a>|
                                <a
									href="<c:url value="/module/pharmagest/vente.form"><c:param name="paramId" value="${ld.pharmProduit.prodId}${ld.lgnRecptLot}"/></c:url>">Supprimer</a>

								</td>
							</tr>
							
						</c:forEach>
                        <tr>
							  <td colspan="7"><div align="right"><strong>Total global</strong></div></td>
							  <td><strong>${totalGlobal}</strong></td>
<c:if test="${formulaireVente.typeVente =='I'}"><td><strong>${totalGlobal*(100-formulaireVente.taux)/100}</strong></td></c:if>
<c:if test="${formulaireVente.typeVente =='I'}"><td><strong>${totalGlobal*formulaireVente.taux/100}</strong></td></c:if>
							  <td>&nbsp;</td>
						  </tr>
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
	function new_assurer() {
	if(document.getElementById("newAssur2").checked){
		document.getElementById("cliNom").disabled=true;
		document.getElementById("cliPrenom").disabled=true;
		document.getElementById("cliDateNaiss").disabled=true;
		//document.getElementByName("cliGenre").disabled=true;
		document.getElementById("taux").disabled=true;		
		document.getElementById("cliTel1").disabled=true;
		document.getElementById("cliGenre1").disabled=true;
		document.getElementById("cliGenre2").disabled=true;
	}if(document.getElementById("newAssur1").checked){
		document.getElementById("cliNom").disabled=false;
		document.getElementById("cliPrenom").disabled=false;
		document.getElementById("cliDateNaiss").disabled=false;
		document.getElementById("cliGenre1").disabled=false;
		document.getElementById("cliGenre2").disabled=false;
		document.getElementById("taux").disabled=false;		
		document.getElementById("cliTel1").disabled=false;

		}
	}	
	window.onload=afficher();
</SCRIPT>

</form:form>