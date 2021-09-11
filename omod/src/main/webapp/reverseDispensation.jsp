<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie dispensation transforme" 
        otherwise="/login.htm" redirect="/index.htm" />
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.openmrs.PatientIdentifier"%>

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />

<openmrs:htmlInclude file="/moduleResources/pharmagest/jquery/jquery-ui.css" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/jquery/jquery.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/jquery/jquery-ui.js" />

<script type="text/javascript">
var $j = jQuery.noConflict();
    $j(document).ready(function() {
        $j("#accordion").accordion({
			collapsible : true
			});
		
    } );
</script>


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
	<li >
		<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
	</li>
    <li class="active" >
	  <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres</a>
	</li>
     <li >
                <a href="<c:url value="/module/pharmagest/listOrdonnance.form"/>">Annuler une vente </a>
            </li>
    
	</ul>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/reverseDispensation.form"
	modelAttribute="formulairePeriode"
	commandName="formulairePeriode">
<div>

<h3 > <span style="font-size:36px">.</span> Transformation des dispensations libres en dispensation enregistr&eacute;es  <strong>${periode}</strong></h3>
</div>
<br>
<c:if test="${var =='0'}">
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="14%">D&eacute;but de p&eacute;riode <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="15%"><openmrs_tag:dateField formFieldName="dateDebut" startValue="${obsDate}" /></td>
            <td width="12%">Fin de p&eacute;riode <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="24%"><openmrs_tag:dateField formFieldName="dateFin" startValue="${obsDate}" /></td>
            <td width="31%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
</c:if>
<br>
<br>
<c:if test="${mess =='1'}">
<div id="openmrs_msg">Aucune dispensation &aacute; cette p&eacute;riode</div>
 
</c:if>
<c:if test="${var =='1'}">
<div> <b class="boxHeader"></b>
  <div class="box">
    <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
       <thead>
        <tr>
          <th width="69">Identifiant</th>
          <th width="203">R&eacute;gime</th>
          <th width="143">Date de dispensation</th>
          <th width="103">Date de RDV</th>
          <th width="143">Nombre de jours de traitement </th>
          <th width="537">Produit(s)</th>
        </tr>
     </thead>
     <tbody>
     <% int x = 1;  %>
        <c:forEach var="conso" items="${listDispensation}">
          <tr>
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
          
            <td><input type="text" name="id<%= x %>" id="id<%= x %>" 
                                    				value="<%= identifiant %>" style="color:#F50911"> </td>
            <input type="hidden" name="dispensId<%= x %>" id="dispensId<%= x %>" value="${conso.ordId}">
            <td>${conso.pharmRegime.regimLib}</td>
            <c:set var="d1" value="${conso.ordDateDispen}" scope="request" />
                                    <% Date d1=  (Date) request.getAttribute("d1"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s1 =  formatter.format(d1); %>
            <td><%= s1 %></td>
             <c:set var="d2" value="${conso.ordDateRdv}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2");								
										
										String s2 =  formatter.format(d2); %>
            <td><%= s2 %></td>
            
            <td>${conso.ordNbreJrsTrai}</td>
            <td>
            	
                <c:forEach var="var" items="${conso.pharmLigneDispensations}">
                
                	<table width="100%" border="1" align="left" cellpadding="7" cellspacing="0" style="background-color:#D6E3F7">
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
            <% x = x+1;  %>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<table width="100%" border="0" align="" cellpadding="7" cellspacing="7">
  <tbody>
    <tr>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="108" align="right"><input name="btn_valider" type="submit" 
												id="btn_valider" title="valider" value="Valider" ></td>
    </tr>
  </tbody>
</table>
</c:if>

<c:if test="${var =='2'}">
<h3 class="boxHeader"><div align="center"> </div></h3>
<div id="accordion">
<h3><strong>Dispensations transform&eacute;es en dispensation pour patient enregistr&eacute;
                  </strong></h3>
	<div>
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
       <thead>
        <tr>
          <th width="69">Identifiant</th>
          <th width="203">R&eacute;gime</th>
          <th width="143">Date de dispensation</th>
          <th width="103">Date de RDV</th>
          <th width="143">Nombre de jours de traitement</th>
          <th width="537">Produit(s)</th>
        </tr>
     </thead>
     <tbody>
     
        <c:forEach var="conso" items="${listDispenEnreg}">
          <tr>
          
          <c:set var="identifier" value="${conso.patientIdentifier}" scope="request" />
          <c:set var="num" value="${conso.ordNumeroPatient}" scope="request" />
          <% PatientIdentifier patientIdentifier1 =  (PatientIdentifier) request.getAttribute("identifier");
			String identifiant1="";
			if(patientIdentifier1!=null){
				 identifiant1 = patientIdentifier1.getIdentifier();				 
				}else {
					identifiant1 = (String) request.getAttribute("num");
					}
										
		 %>
          
            <td><%= identifiant1 %></td>
           
            <td>${conso.pharmRegime.regimLib}</td>
            <c:set var="d10" value="${conso.ordDateDispen}" scope="request" />
                                    <% 
									SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
									String s10 =null;
									if(request.getAttribute("d10")!=null){
									Date d10=  (Date) request.getAttribute("d10"); 
																			
										 s10 =  formatter1.format(d10);
										} 						
									%>
            <td><%= s10 %></td>
             <c:set var="d20" value="${conso.ordDateRdv}" scope="request" />
                                    <% 
									String s20 =null;
									if(request.getAttribute("d20")!=null){
									Date d20=  (Date) request.getAttribute("d20");								
										//System.out.println("+++++++++++++++++++++++++++++++++++++++++++"+d20);
										 s20 =  formatter1.format(d20); 
										//System.out.println("+++++++++++++++++++++++++++++++++++++++++++"+s20);
										} ;	
									%>
            <td><%= s20 %></td>
            
            <td>${conso.ordNbreJrsTrai}</td>
            <td>
            	
                <c:forEach var="var" items="${conso.pharmLigneDispensations}">
                
                	<table width="100%" border="1" align="left" cellpadding="7" cellspacing="0" style="background-color:#D6E3F7">
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
    
<h3><strong>Dispensations non transform&eacute;es</strong></h3>
	<div>
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
       <thead>
        <tr>
          <th width="69">Identifiant</th>
          <th width="203">R&eacute;gime</th>
          <th width="143">Date de dispensation</th>
          <th width="103">Date de RDV</th>
          <th width="143">Nombre de jours de traitement restant</th>
          <th width="537">Produit(s)</th>
        </tr>
     </thead>
     <tbody>
     
        <c:forEach var="conso" items="${listDispenLibre}">
          <tr>
          <c:set var="identifier" value="${conso.patientIdentifier}" scope="request" />
          <c:set var="num" value="${conso.ordNumeroPatient}" scope="request" />
          <% PatientIdentifier patientIdentifier2 =  (PatientIdentifier) request.getAttribute("identifier");
			String identifiant2="";
			if(patientIdentifier2!=null){
				 identifiant2 = patientIdentifier2.getIdentifier();				 
				}else {
					identifiant2 = (String) request.getAttribute("num");
					}
										
		 %>
          
            <td><%= identifiant2 %></td>
           
            <td>${conso.pharmRegime.regimLib}</td>
            <c:set var="d11" value="${conso.ordDateDispen}" scope="request" />
                                    <% 
									SimpleDateFormat formatter11 = new SimpleDateFormat("dd/MM/yyyy");
									String s11 =null;
									if(request.getAttribute("d11")!=null){
										Date d11=  (Date) request.getAttribute("d11"); 										
										s11 =  formatter11.format(d11);
										}
									
									 %>
            <td><%= s11 %></td>
             <c:set var="d22" value="${conso.ordDateRdv}" scope="request" />
                                    <% 									
									String s22 =null;
									if(request.getAttribute("d11")!=null){
										Date d22=  (Date) request.getAttribute("d22"); 										
										s22 =  formatter11.format(d22);
										}
										 %>
            <td><%= s22 %></td>            
            <td>${conso.ordNbreJrsTrai}</td>
            <td>
            	
                <c:forEach var="var" items="${conso.pharmLigneDispensations}">
                
                	<table width="100%" border="1" align="left" cellpadding="7" cellspacing="0" style="background-color:#D6E3F7">
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

</form:form>
<script type="text/javascript">
            $("#dateDebut").attr('required', true); 
			 $("#dateFin").attr('required', true); 
</script>
