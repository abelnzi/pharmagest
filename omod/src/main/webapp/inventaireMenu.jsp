<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire" 
        otherwise="/login.htm" redirect="/index.htm" />
<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=true;Boolean activeRap=false;Boolean activeParam=false;Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>
<!--<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
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
	
	<li class="active ">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
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
			<li class=" firt">
				<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/listInventaire.form"/>">Validation d'inventaire</a>
			</li>
            <br>
            <li>
				<a href="<c:url value="/module/pharmagest/listHistoInventaire.form"/>">Historique des inventaires</a>
			</li>
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