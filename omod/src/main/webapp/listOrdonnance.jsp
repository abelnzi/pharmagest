<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie vente annule" 
        otherwise="/login.htm" redirect="/index.htm" />

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.openmrs.PatientIdentifier"%>

<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
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
     <li >
				<a href="<c:url value="/module/pharmagest/vente.form"/>">Ventes</a>
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
</ul>
</div>
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script type="text/javascript">
    var $j = jQuery.noConflict();
</script>
<script type="text/javascript">
    $j(document).ready(function() {
        $j('#myTable').dataTable();
    } );
</script>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/listOrdonnance.form"
	modelAttribute="formulaireVente"
	commandName="formulaireVente"  >
<div>
<h3 ><span style="font-size:36px">.</span> Choix des ventes</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<br>
<br>
<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="7%" height="60">Date d&eacute;but</td>
      <td width="20%"><openmrs_tag:dateField formFieldName="debut" startValue="${obsDate}" /> </td>
      <td width="6%"> 
        Date fin
</td>
      <td width="22%"><openmrs_tag:dateField formFieldName="fin" startValue="${obsDate}" /> </td>
      <td width="45%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>

<br>
<!--<p>${date}</p>-->
<c:if test="${var =='1'}">
        <div>
	<b class="boxHeader"></b>
	<div class="box">
 <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           <thead>
                <tr>
                  <th width="10%">N&deg; Ordonnance</th>
                  <th width="14%">Date de la vente</th>
                  <th width="17%">Client</th>
                  <th width="12%">Programme</th>
                  <th width="21%">Action</th>
                </tr>
               </thead>
                <tbody> 
                <c:forEach var="lo" items="${ordonnances}">
								<tr>
                                 <c:set var="identifier" value="${lo.patientIdentifier}" scope="request" />
          <c:set var="num" value="${lo.ordNumeroPatient}" scope="request" />
          <% PatientIdentifier patientIdentifier =  (PatientIdentifier) request.getAttribute("identifier");
			String identifiant="";
			if(patientIdentifier!=null){
				 identifiant = patientIdentifier.getIdentifier();				 
				}else {
					identifiant = (String) request.getAttribute("num");
					}
										
		 %>
									<td width="10%"><%= identifiant %> </td>
                                    <c:set var="dd" value="${lo.ordDateDispen}" scope="request" />
                                    <% Date dd=  (Date) request.getAttribute("dd"); 
										SimpleDateFormat formatterdd = new SimpleDateFormat("dd/MM/yyyy");
										String sd="";
										if(dd!=null) sd =  formatterdd.format(dd); %>
								
                                  <td width="14%"><%= sd %></td>
                                    <td width="17%">${lo.pharmClient.cliNom} ${lo.pharmClient.cliPrenom}</td>
									<td width="12%">${lo.pharmProgramme.programLib}</td>
									<td width="21%"><a href="<c:url value="/module/pharmagest/annulerOrdonnance.form"><c:param name="paramId" 
                                                  value="${lo.ordId}"/></c:url>">Choix</a>
                                    </td>
								</tr>
                 
                
			   </c:forEach>
          </tbody>
</table>   

</div> 
	</div>
    </c:if>

</form:form>