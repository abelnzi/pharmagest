<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript">
function showurl(){
			//window.location.href="http://www.google.com";
			}
//document.getElementById("radio").onclick=showurl();
		
		//$("#radio2").onclick('window.location.href="http://www.google.com"');
		
</script>
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<div style="border: 1px dashed black; padding: 10px;">
<ul id="menu">
	

	<li class="active first"><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li>
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">Entr&eacute;e fournisseur</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockEntree.form"/>">Autre mouvement d'entr&eacute;e de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockSortie.form"/>">Autre mouvement de sortie de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapportage sur le Stock</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	
	<!-- Add further links here -->
</ul>
</div>
<br>
<div> <b class="boxHeader">Choix du type de dispensation</b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellpadding="7" cellspacing="0">
        <tbody>
          <tr>
          	<td width="22%"><label for="radio">Dispensation libre</label>
            <input type="radio" name="radio" id="radio" value="radio" onClick='window.location.href="<c:url value="/module/pharmagest/dispensationLibre.form"/>"'></td>
             <td width="78%"><label for="radio2">Dispensation pour un patient</label>
            <input type="radio" name="radio2" id="radio2" value="radio2" onClick='window.location.href="<c:url value="/module/pharmagest/dispensation.form"/>"'></td>
           
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>