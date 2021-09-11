<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport stock" 
        otherwise="/login.htm" redirect="/index.htm" />

<%@ page import="org.openmrs.module.pharmagest.PharmOperation"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
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
	<li class="first "><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Menu principal</a></li>
			
    		<li >
				<a href="<c:url value="/module/pharmagest/saisiesRPPS.form"/>">Saisies des rapports des PPS</a>
			</li>
             <li>
				<a href="<c:url value="/module/pharmagest/importRapportConso.form"/>">Importation des rapports des ESPC/PPS</a>
            </li>
            <li>
				<a href="<c:url value="/module/pharmagest/listPPSModification.form"/>">Modification/Validation des rapports ESPC/PPS
            </li>
            <li>
				<a href="<c:url value="/module/pharmagest/listCommandeSite.form"/>">Traitements</a>
			</li>
             <li >
				<a href="<c:url value="/module/pharmagest/listHistoDistribution.form"/>">Historique des rapports mensuel de consommation</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/listBorderoDistribution.form"/>">Historique des Bordereaux de transfert</a>
			</li>
            <li class="active">
				<a href="<c:url value="/module/pharmagest/ruptureESPC.form"/>">Liste des ESPC OU PPS ayant connu une rupture</a>
			</li>
            
            <li>
				<a href="<c:url value="/module/pharmagest/promptitudeESPC.form"/>">Promptitude des Rapports de consommation</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/etatStockESPC.form"/>">Etat de Stock des ESPC/PPS</a>
			</li>
	</ul>
</div>

<div>
<h3><span style="font-size:36px">.</span> Liste des ESPC OU PPS ayant connu une rupture <strong>${periode}</strong></h3>
</div>
<br>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/ruptureESPC.form"
	modelAttribute="formulairePeriode"
	commandName="formulairePeriode">
    
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
           
            <td width="15%">D&eacute;but de p&eacute;riode <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="14%"><openmrs_tag:dateField formFieldName="dateDebut" startValue="${obsDate}" /></td>
            <td width="13%">Fin de p&eacute;riode <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="18%"><openmrs_tag:dateField formFieldName="dateFin" startValue="${obsDate}" /></td>
            <td width="36%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br>
<br>
<c:if test="${var ==1}">
<div>
	<b class="boxHeader"></b>
	<div class="box">
     
	  <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           
            <thead>
                <tr>
                  <th width="10%">N&deg; Ordre</th>
                  <th width="10%">Nom ESPC/PPS</th>
                  <th width="10%">Programme</th>
                  <th width="10%">Code Produit</th>
                  <th width="18%">D&eacute;signation</th>
                  <th width="9%">Nombre de jour de rupture </th>                 
                </tr>
              </thead>
              <tbody>
              <% 
			  		int i = 0;
			  %>
                <c:forEach var="ligne" items="${list}">
                 <% 
			  		 i = i+1;
			  	%>
                <tr>
                  <td><%= i %></td>
                  <td>${ligne.pharmCommandeSite.pharmSite.sitLib}</td>
                  <td>${ligne.pharmCommandeSite.pharmProgramme.programLib}</td>
                  <td>${ligne.pharmProduit.prodCode}</td>
                  <td>${ligne.pharmProduit.prodLib}</td>
                  <td>${ligne.lgnComNbreJrsRup}</td>                  
                </tr>
                </c:forEach>
      		</tbody>
</table>      
</div> 
</div>
 </c:if>
 
 <script type="text/javascript">
            $("#dateDebut").attr('required', true); 
</script>
<script type="text/javascript">
            $("#dateFin").attr('required', true); 
</script>
</form:form>
