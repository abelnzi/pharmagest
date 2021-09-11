<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie dispensation" 
        otherwise="/login.htm" redirect="/index.htm" />
 <openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.openmrs.PatientIdentifier"%>

<script type="text/javascript">
    var $j = jQuery.noConflict();
</script>
<script type="text/javascript">
    $j(document).ready(function() {
        $j('#myTable').dataTable();
    } );
</script>
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
    <li >
		<a href="<c:url value="/module/pharmagest/dispensation.form"/>">Dispensation  patient enregistr&eacute;</a>
	</li>
     <li>
				<a href="<c:url value="/module/pharmagest/vente.form"/>">Ventes</a>
	</li>
	<li class="active" >
		<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
	</li>
     <li>
	  <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres</a>
	</li>
     <li >
                <a href="<c:url value="/module/pharmagest/listOrdonnance.form"/>">Annuler une vente </a>
            </li>
    
	</ul>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/histoDispensation.form"
	modelAttribute="formulairePeriode"
	commandName="formulairePeriode">
<div>

<h3 > <span style="font-size:36px">.</span> Dispensation  <strong>${periode}</strong></h3>
</div>
<br>
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="10%">Programme <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="14%"><form:select path="programme" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select>
              <form:errors path="programme" cssClass="error" /></td>
            <td width="2%">&nbsp;</td>
            <td width="14%">D&eacute;but de p&eacute;riode <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="12%"><openmrs_tag:dateField formFieldName="dateDebut" startValue="${formulairePeriode.dateDebut}" /></td>
            <td width="12%">Fin de p&eacute;riode <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="13%"><openmrs_tag:dateField formFieldName="dateFin" startValue="${formulairePeriode.dateFin}" /></td>
            <td width="17%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br>
<br>
<c:if test="${var =='2'}">
<div id="openmrs_msg">Aucune dispensation &aacute; cette p&eacute;riode</div>
 
</c:if>
<c:if test="${var =='1'}">
<div> <b class="boxHeader"></b>
  <div class="box">
    <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
       <thead>
        <tr>
         <!--<th width="69">Programme</th> -->
          <th width="69">Identifiant</th>
          <th width="69">Sexe</th>
          <th width="69">Age</th>
          <th width="69">Poids</th>
          <th width="69">cat&eacute;gorie</th>
          <th width="203">R&eacute;gime</th>
          <th width="143">Date de dispensation</th>
          <th width="103">Date de RDV</th>
          <th width="143">Nombre de jours de traitement</th>
          <th width="143">Nombre de jours de traitement restant</th>
          <th width="537">Statut</th>
          <th width="537">Produit(s)</th>
        </tr>
     </thead>
     <tbody>
        <c:forEach var="conso" items="${listDispensation}">
        <tr>
        <!-- <td>${conso.pharmProgramme.programLib}</td> -->
          <c:set var="identifier" value="${conso.patientIdentifier}" scope="request" />
          <c:set var="num" value="${conso.ordNumeroPatient}" scope="request" />
          <% PatientIdentifier patientIdentifier =  (PatientIdentifier) request.getAttribute("identifier");
			String identifiant="";
			if(patientIdentifier!=null){
				 identifiant = patientIdentifier.getIdentifier();				 
				}else {
					identifiant = (String) request.getAttribute("num");
					}
										
		 %>
          
            <td><%= identifiant %></td>
            <td>${conso.ordGenre}</td>
            <td>${conso.ordAge}</td>
            <td>${conso.ordService}</td>
            <td>
            	<c:if test="${conso.ordAge <15}">Enfant</c:if> 
                <c:if test="${conso.ordAge >=15}">Adulte</c:if>            
            </td>
            <td>${conso.pharmRegime.regimLib}</td>
            <c:set var="d1" value="${conso.ordDateDispen}" scope="request" />
                                    <% Date d1=  (Date) request.getAttribute("d1"); 
										SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
										String s1="";
										if(d1!=null) s1 =  formatter1.format(d1); %>
            <td><%= s1 %></td>
             <c:set var="d2" value="${conso.ordDateRdv}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2");								
										String s2="";
										if(d2!=null) s2 =  formatter1.format(d2); %>
            <td><%= s2 %></td>
            
            <td>${conso.ordNbreJrsTrai}</td>
            
            <td>${conso.ordHospi}</td>
            
            
            <td>
            <span style="color:#000000"><c:if test="${conso.ordFlag!='AN'}">Dispens&eacute;</c:if> 
            <c:if test="${conso.ordFlag=='AN'}">Annul&eacute;</c:if> 
            </span>
            </td>
            <td>
            	
                <c:forEach var="var" items="${conso.pharmLigneDispensations}">
                
                	<table width="100%" border="1" align="left" cellpadding="7" cellspacing="0">
                      <tbody>
                        <tr>
                          <td width="10%" >${var.pharmProduit.prodCode}</td>
                          <td width="80%">${var.pharmProduit.prodLib}</td>
                          <td width="10%">${var.ldQteServi}</td>
                        </tr>
                      </tbody>
                    </table>

                    
                </c:forEach>
            	
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
</c:if>
<script type="text/javascript">
            $("#dateDebut").attr('required', true); 
			 $("#dateFin").attr('required', true); 
</script>
</form:form>