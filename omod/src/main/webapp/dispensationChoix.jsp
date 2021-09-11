<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie dispensation" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/javascript">
function showurl(){
			//window.location.href="http://www.google.com";
			}
//document.getElementById("radio").onclick=showurl();
		
		//$("#radio2").onclick('window.location.href="http://www.google.com"');
		
</script>
<!--<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>-->
<% Boolean activeFour=false;Boolean activeDisp=true;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false;Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>
<!--<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
	<li class=" first">
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">R&eacute;ception produits</a>
	</li>
	<li class="active "><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>
<li><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Distribution</a></li>
	
	<li >
		<a href="<c:url value="/module/pharmagest/mouvementStock.form"/>">Mouvement de stock</a>
	</li>
	<li>
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
<!--<br>
<div> <b class="boxHeader">Choix du type de dispensation</b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellpadding="7" cellspacing="0">
        <tbody>
          <tr>
          	<td width="22%"><label for="radio">Dispensation libre</label>
            <input type="radio" name="radio" id="radio" value="radio" onClick='window.location.href="<c:url value="/module/pharmagest/dispensationLibre.form"/>"'></td>
             <td width="78%"><label for="radio2">Dispensation  patient enregistr&eacute;</label>
            <input type="radio" name="radio2" id="radio2" value="radio2" onClick='window.location.href="<c:url value="/module/pharmagest/dispensation.form"/>"'></td>
           
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>-->

<table width="319" border="0" cellspacing="8">
  <tbody>
    <tr>
      <td width="197"><b class="boxHeader"></b><div class="box adminMenuList">
		<ul id="menu">
			
			<li>
				<a href="<c:url value="/module/pharmagest/dispensationLibre.form"/>">Dispensation libre</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/dispensation.form"/>">Dispensation  patient enregistr&eacute;</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/vente.form"/>">Ventes</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
			</li>
            <br>
             <li >
                <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres </a>
            </li>
            <br>
             <li >
                <a href="<c:url value="/module/pharmagest/listOrdonnance.form"/>">Annuler une vente </a>
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