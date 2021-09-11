<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport stock" 
        otherwise="/login.htm" redirect="/index.htm" />
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
	<li>
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>
   <li class="active">
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
    </openmrs:hasPrivilege>
    
	</ul>
</div>

<div>
<h3 > <span style="font-size:36px">.</span> Stock des produits par lot  <strong>${dateRap}</strong></h3>
</div>
<br>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/rapportStockTotal.form"
	modelAttribute="formulaireProduit"
	commandName="formulaireProduit">

<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="12%">Programme <span style="color:#F11A0C">*</span></td>
            <td width="1%">:</td>
            <td width="20%"><form:select path="programme" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select>
              <form:errors path="programme" cssClass="error" /></td>
            <td width="67%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br><br>
<c:if test="${var ==1}">
<div>
	<b class="boxHeader"></b>
	<div class="box">
	  <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           <thead>
                <tr>
                  <th width="10%">Programme</th>
                  <th width="10%">Code article</th>
                  <th width="18%">D&eacute;signation</th>
                  <th width="13%">Date du dernier stock</th>
                  <th width="14%">N&deg; lot</th>
                  <th width="14%">P&eacute;remption</th>
                  <th width="11%">Quantit&eacute; en stock</th>
                </tr>
                <thead>
                <tbody>
                <c:forEach var="stock" items="${listStocks}">
                <tr>
                  <td>${stock.pharmProgramme.programLib}</td>
                  <td>${stock.pharmProduitAttribut.pharmProduit.prodCode}</td>
                  <td>${stock.pharmProduitAttribut.pharmProduit.prodLib}</td>
                  <td>${stock.stockDateStock}</td>
                  <td>${stock.pharmProduitAttribut.prodLot}</td>
                  <td>${stock.pharmProduitAttribut.prodDatePerem}</td>
                  <td>${stock.stockQte}</td>
                  </tr>
                </c:forEach>
      </tbody>
</table>      
</div> 
</div>
</c:if>
</form:form>
