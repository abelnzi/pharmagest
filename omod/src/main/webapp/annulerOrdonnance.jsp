<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie vente annule" 
        otherwise="/login.htm" redirect="/index.htm" />
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
	<li>
		<a href="<c:url value="/module/pharmagest/dispensationLibre.form"/>">Dispensation libre</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/dispensation.form"/>">Dispensation  patient enregistr&eacute;</a>
	</li>
     <li >
		<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
	</li>
	 <li>
	  <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres</a>
	</li>
     <li class="active">
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
<!-- Le JS... -->
<script type="text/javascript">


function afficher(form_element) { // On déclare la fonction toggle_div qui prend en param le bouton et un id
	if(form_element.id=="vente_direct") { // Si le div est masqué...
	document.getElementById("findassure").style.display="block";
	document.getElementById("findclient").style.display="none";
	}else 
	if(form_element.id=="vente_indirect") { // Si le div est masqué...
	document.getElementById("findclient").style.display="block";
	document.getElementById("findassure").style.display="none";
	} else {
		document.getElementById("findassure").style.display="none";
		document.getElementById("findclient").style.display="none";
		}
}
function verifier() {
    var numPatient = document.getElementById('numPatient').value;
    if(numPatient=="")
    {
         alert('Veuillez inscrire Le numéro de l\'assuré!');
        document.getElementById('numPatient').focus;
        return false;
             
    }
    else
        return true;
}
</script>
<div>
	<h3> <span style="font-size:36px">.</span>Vente</h3>
</div>
<br>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/annulerOrdonnance.form"
	modelAttribute="formulaireVente"
	commandName="formulaireVente">

      <div>
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td><input type="submit" name="reset"
							id="reset" value="Annuler l'ordonnance" onClick="return confirm('Voulez vous annuler?') ? true : false;"> </td>
					</tr>
				</tbody>
			</table>

	  </div>
      
	  <div>
			<b class="boxHeader"></b>
			<div class="box">
				<div class="searchWidgetContainer" id="findPatients" align="center"></div>
                
                <!----------------------------- affichage des informations ------------------------------------------------>
          <c:if test="${formulaireVente.getTypeVente() =='I'}">
            <div  id="findinfos" align="center">
           
       		  <table width="100%" border="0" cellpadding="7" cellspacing="0">
						<tbody>
					
					<tr>
					  <td colspan="6" align="center"><span style="color:#999">--------------------------Informations assuré---------------------------</span></td>
				  </tr>
					<tr>
					  <td width="16%">Numéro Assuré <span style="color:#F11A0C">*</span>
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
					  <td>${formulaireVente.cliDateNaiss}</td>
					  <td>&nbsp;Téléphone:</td>
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
					  <td>${formulaireVente.assurance}&nbsp; &nbsp; &nbsp;</td>
					  <td>&nbsp;Taux <span style="color:#F11A0C">*</span>
                      </td>
					  <td>${formulaireVente.taux} % </td>                      
					  <td></td>
					  <td></td>
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
               </c:if>
                 <!----------------------------- affichage des informations ------------------------------------------------>
             <c:if test="${formulaireVente.getTypeVente() =='D'}">
               <div  id="findinfos2" align="center">
           		<table width="100%" height="130" border="0" cellpadding="7" cellspacing="0">
						<tbody>
					
					<tr>
					  <td colspan="6" align="center"><span style="color:#999">--------------------------Informations Client---------------------------</span></td>
				  </tr>
					<tr>
					  <td width="16%">&nbsp;Nom: </td>
					  <td width="20%">${formulaireVente.cliNom}</td>
					  <td width="8%">&nbsp;Prenoms: </td>
					  <td width="15%">${formulaireVente.cliPrenom}</td>
					  <td width="7%">&nbsp;</td>
					  <td width="34%">&nbsp;</td>
			        </tr>
					<tr>
					  <td>
                      Date de naissance <span style="color:#F11A0C">*</span>                     
                      
                      </td>
					  <td>${formulaireVente.cliDateNaiss}</td>
					  <td>&nbsp;Téléphone:</td>
					  <td>${formulaireVente.cliTel1}</td>
					  <td></td>
					  <td></td>
					  </tr>
					<tr>
					  <td>&nbsp;Taux <span style="color:#F11A0C">*</span></td>
					  <td>&nbsp;${formulaireVente.taux} % </td>
					  <td>&nbsp;Genre :</td>
					  <td><c:if test="${formulaireVente.cliGenre=='M'}">Masculin</c:if> 
                      <c:if test="${formulaireVente.cliGenre=='F'}">Féminin</c:if></td>                      
					  <td></td>
					  <td></td>
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
           		      <td width="23%"><c:if test="${formulaireVente.ordHospi=='H'}">Oui</c:if> 
                      <c:if test="${formulaireVente.ordHospi=='NH'}">Non</c:if></td>
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
              </c:if>
              
              <c:if test="${formulaireVente.getTypeVente() =='NA'}">
              <table width="100%" border="0" cellpadding="7" cellspacing="0">
           		  <tbody>
           		    <tr>
           		      <td colspan="6" style="color:#999" align="center">--------------------------Informations g&eacute;n&eacute;rales---------------------------</td>
       		        </tr>
           		    <tr>
           		      <td width="6%" >N&deg; de l'ordonnance</td>
           		      <td width="15%" >${formulaireVente.ordNum}</td>
           		      <td width="6%" >Programme</td>
           		      <td width="21%" >${formulaireVente.pharmProgramme.programLib} </td>
           		      <td> Nombre de jours de traitement</td>
           		      <td>&nbsp;${formulaireVente.ordNbreJrsTrai} </td>
       		        </tr>
           		    <tr>
           		      <td>Dispensateur</td>
           		      <td colspan="3">&nbsp;
       		          <input name="textfield3" type="text"
													disabled="disabled" id="textfield3"
													value="${formulaireVente.pharmGestionnairePharma.gestNom}  ${formulaireVente.pharmGestionnairePharma.gestPrenom}" size="35"></td>
           		      <td>Prescripteur</td>
           		      <td>&nbsp;${formulaireVente.pharmMedecin.medNom}${formulaireOrdonnance.pharmMedecin.medPrenom}</td>
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
           		      <td><%= sr2 %></td>
       		        </tr>
       		      </tbody>
       		     </table>
              </c:if>
              
		</div>
		</div>
 
  
        
        <!----------------------------------------------------------------------------------------------------- -->
        
<br>
		
        <!----------------------------------------------------------------------------------------------------- -->
        <c:if test="${mess =='delete'}"> </c:if>
		<div>
	  <b class="boxHeader"></b></div>

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
							
							<td width="7%">Prix</td>
							<td width="9%">Total</td>
							
						</tr>
						<c:forEach var="ld" items="${ligneDispensations}">
							<tr>
								<td>${ld.pharmProduit.prodCode}</td>
								<td>${ld.pharmProduit.prodLib}</td>
								<td>${ld.ldQteDem}</td>
								<td>${ld.ldQteServi}</td>
								
								<td>${ld.ldPrixUnit}</td>
								<td>${ld.ldQteServi * ld.ldPrixUnit}</td>
								
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


<script type="text/javascript">
	document.getElementById("ordDateDispen").onchange = function() {daterdv()};
</SCRIPT>

</form:form>