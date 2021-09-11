<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport" 
        otherwise="/login.htm" redirect="/index.htm" />
<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=true;Boolean activeParam=false; Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>

<!--<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>-->

<!--<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
	<li class=" first">
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">R&eacute;ception produits</a>
	</li>
	<li ><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Distribution</a></li>
	<li >
		<a href="<c:url value="/module/pharmagest/mouvementStock.form"/>">Mouvement de stock</a>
	</li>
	
	<li>
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li  class="active ">
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapports</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Fichiers de base</a>
	</li>
	
</ul>
</div>-->

<table width="319" border="0" cellspacing="8">
  <tbody>
    <tr>
      <td width="197"><b class="boxHeader"></b><div class="box adminMenuList">
		<ul id="menu">
        	

    <openmrs:hasPrivilege privilege="pharmacie rapport mensuel">
    		<li>
				<a href="<c:url value="/module/pharmagest/rapportConso.form"/>">Rapport de consommation</a>
			</li>
            <br>
    </openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="pharmacie rapport commande">
        	<li >
				<a href="<c:url value="/module/pharmagest/simulationCommande.form"/>">Simulation du Rapport commande</a>
			</li>
        	<li>
				<a href="<c:url value="/module/pharmagest/rapportCommande.form"/>">Rapport commande</a>
			</li>
            <br>
    </openmrs:hasPrivilege>
   <openmrs:hasPrivilege privilege="pharmacie rapport stock">
	<li>
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>
    <br>
   <li>
		<a href="<c:url value="/module/pharmagest/rapportStockTotal.form"/>">Stock des produits par lot</a>
	</li>
    <br>
     <li>
		<a href="<c:url value="/module/pharmagest/rapportStockProduit.form"/>">Stock des produits</a>
	</li>
    <br>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportConsoProdJour.form"/>">Consommation par Produit par p&eacute;riode</a>
	</li>
    <br>
     <li>
		<a href="<c:url value="/module/pharmagest/rapportPPI.form"/>">Rapport des mouvements de produits </a>
	</li>
    </openmrs:hasPrivilege>
            
            
            <br>
<!--    		<li>
				<a href="<c:url value=""/>">Rapports financiers</a>
			</li>-->
		</ul>
</div></td>
      <td width="80">&nbsp;</td>
      <td width="8">&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>


<%@ include file="/WEB-INF/template/footer.jsp"%>