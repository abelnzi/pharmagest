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
	<openmrs:hasPrivilege privilege="pharmacie rapport">
	<li class="first">
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Menu principal</a>
	</li>
	</openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="pharmacie rapport mensuel">
    		<li>
				<a href="<c:url value="/module/pharmagest/rapportConso.form"/>">Rapport de consommation</a>
			</li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie rapport commande">
        	<li >
				<a href="<c:url value="/module/pharmagest/simulationCommande.form"/>">Simulation du Rapport commande</a>
			</li>
        	<li>
				<a href="<c:url value="/module/pharmagest/rapportCommande.form"/>">Rapport commande</a>
			</li>
   </openmrs:hasPrivilege>
   <openmrs:hasPrivilege privilege="pharmacie rapport stock">
	<li class="active">
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>
   <li>
		<a href="<c:url value="/module/pharmagest/rapportStockTotal.form"/>">Stock des produits par lot</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportStockProduit.form"/>">Stock des produits</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportConsoProdJour.form"/>">Consommation par Produit par p&eacute;riode</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportPPI.form"/>">Rapport des mouvements de produits </a>
	</li>
    </openmrs:hasPrivilege>	</ul>
</div>

<div>
<h3><span style="font-size:36px">.</span> Historique des op&eacute;rations sur le stock des produits <strong>${periode}</strong></h3>
</div>
<br>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/histoMvm.form"
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
      <!--<input type="submit" name="btn_imprime"	id="btn_imprime" value="Imprimer">-->
	  <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           
            <thead>
                <tr>
                  <th width="10%">N&deg; Ordre</th>
                  <th width="10%">Programme</th>
                  <th width="10%">Code article</th>
                  <th width="18%">D&eacute;signation</th>
                  <th width="13%">Num&eacute;ro du lot</th>
                  <th width="9%">Quantit&eacute; </th>
                  <th width="11%">Quantit&eacute; en stock</th>
                  <th width="11%">Date de l'op&eacute;ration</th>
                  <th width="14%">Nature de l'op&eacute;ration</th>
                 
                  <th width="14%">Num&eacute;ro de la pi&egrave;ce</th>
                  <th width="14%">Operateur</th>
                  
                  <th width="14%">Observation</th>
                </tr>
              </thead>
              <tbody>
              <% 
			  		int i = 0;
			  %>
                <c:forEach var="histo" items="${list}">
                 <% 
			  		 i = i+1;
			  	%>
                <tr>
                  <td><%= i %></td>
                  <td>${histo.programme.programLib}</td>
                  <td>${histo.histoMouvementStock.pharmProduit.prodCode}</td>
                  <td>${histo.histoMouvementStock.pharmProduit.prodLib}</td>
                  <td>${histo.histoMouvementStock.mvtLot}</td>
                  <td>${histo.histoMouvementStock.mvtQte}</td>
                  <td>${histo.histoMouvementStock.mvtQteStock}</td>
                  <td>${histo.histoMouvementStock.mvtDate}</td>
                  <td>${histo.typeOperation.toperLib}</td>
                   <c:set var="operation" value="${histo.histoMouvementStock.pharmOperation}" scope="request" />
                     <%  PharmOperation operation=  (PharmOperation) request.getAttribute("operation"); 
						String reference="";
						if(operation!=null){							
							
							 if (operation.getPharmTypeOperation().getToperId()==1 || operation.getPharmTypeOperation().getToperId()==2) {	 
								 reference=operation.getOperNum();								 
								 } else {
									 reference=operation.getOperId()+"";
									 }
							 
							}
													
					 %>
                  <td><%= reference %></td>
                  <td><c:if test="${histo.histoMouvementStock.pharmOperation !=null}">
                  ${histo.histoMouvementStock.pharmOperation.pharmGestionnairePharma.gestNom}  
                  ${histo.histoMouvementStock.pharmOperation.pharmGestionnairePharma.gestPrenom}</c:if></td>
                  <td>${histo.histoMouvementStock.mvtMotif}</td>
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
