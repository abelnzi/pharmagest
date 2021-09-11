<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie parametres" 
        otherwise="/login.htm" redirect="/index.htm" />
<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=true; Boolean activeInter=false;
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
	<li  ><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li ><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Distribution</a></li>
	<li >
		<a href="<c:url value="/module/pharmagest/mouvementStock.form"/>">Mouvement de stock</a>
	</li>
	
	<li >
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapports</a>
	</li>
    <li class="active ">
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Fichiers de base</a>
	</li>
	
	
</ul>
</div>-->

<table width="319" border="0" cellspacing="8">
  <tbody>
    <tr>
      <td width="197"><b class="boxHeader"></b><div class="box adminMenuList">
		<ul id="menu">
			
            <li>
                <a href="<c:url value="/module/pharmagest/fournisseur.form"/>">Fournisseur</a>
            </li>
            <br>
            <li >
                <a href="<c:url value="/module/pharmagest/medecin.form"/>">Prescripteur</a>
            </li>
           <!-- <br>
            <li >
                <a href="<c:url value="/module/pharmagest/regime.form"/>">R&eacute;gime</a>
            </li>
            <br>
            <li >
                <a href="<c:url value="/module/pharmagest/produit.form"/>">Produit</a>
            </li>
            <br>
            <li >
                <a href="<c:url value="/module/pharmagest/programme.form"/>">Programme</a>
            </li>
            <br>
            <li >
                <a href="<c:url value="/module/pharmagest/classePharma.form"/>">Classe de produit</a>
            </li>-->
            <br>
            <li >
                <a href="<c:url value="/module/pharmagest/site.form"/>">Site</a>
            </li>
            <br>
            <li >
                <a href="<c:url value="/module/pharmagest/donneesBasesProduits.form"/>">Importation et configuration des produits et programme</a>
            </li>
			<li >
                <a href="<c:url value="/module/pharmagest/importDispensations.form"/>">Importation des dispensations</a>
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