<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie vente saisie" 
        otherwise="/login.htm" redirect="/index.htm" />
        
<openmrs:require privilege="pharmacie dispensation" 
        otherwise="/login.htm" redirect="/index.htm" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<openmrs:htmlInclude file="/moduleResources/pharmagest/scripts/jquery.PrintArea.js" />

<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1560px;background-color:#FCD7DB" >
<ul id="menu">

	<li class=" first">
		<a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>">Menu principal</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/dispensationLibre.form"/>">Dispensation libre</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/dispensation.form"/>">Dispensation  patient enregistr&eacute;</a>
	</li>
     <li class="active">
				<a href="<c:url value="/module/pharmagest/vente.form"/>">Ventes</a>
	</li>
     <li >
		<a href="<c:url value="/module/pharmagest/histoDispensation.form"/>">Historique des Dispensations</a>
	</li>
	 <li>
	  <a href="<c:url value="/module/pharmagest/reverseDispensation.form"/>">Transformation des dispensations libres</a>
	</li>
	<!-- Add further links here -->
</ul></div>

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>
<script>

var $sig = jQuery.noConflict();

$sig(document).ready(function(){
	$sig("#print_button").click(function(){
		$sig("#print").printArea();
	});
});

</script>


<!-- Le JS... -->

<div>
	<h3> <span style="font-size:36px">.</span>Re&ccedil;u Ordonnance</h3>
</div>
<br>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/recuOrdonnance.form"
	modelAttribute="formulaireVente"
	commandName="formulaireVente">

<div> <b class="boxHeader"></b>    
<div class="box" id="print" style="padding:15px" >

                  <input id="print_button" value="Imprimer" type="button" /> <br>
                
<div class="searchWidgetContainer" id="findPatients" style="border:#000 solid 1px; width:50%; margin:auto; padding:15px" >
<center>
  <table border="0" width="100%">
    <tr>
      <td colspan="3" align="center"></td>
      <td align="center"></td>
      <td align="center"></td>
      <td colspan="2"></td>
      <td colspan="2" align="center">REPUBLIQUE DE CÔTE D'IVOIRE<br />
        Union-Discipline-Travail</td>
      </tr>
    <tr>
      <td width="2%" rowspan="2"></td>
      <td colspan="2" rowspan="2" valign="bottom" align="center">${site}</td>
      <td width="2%" rowspan="2"></td>
      <td rowspan="2"></td>
      <td colspan="4"></td>
      </tr>
    <tr>
      <td height="76" colspan="3" valign="bottom">
        <div style="border:#000 solid 1px; padding:8px; border-radius:10px">
          <h3 align="center">&nbsp;Ordonnance Facture</h3>
        </div></td>
      <td width="21%"></td>
      </tr>
    <tr>
      <td height="4" colspan="9" align="center" valign="top"><hr /></td>
      </tr>
    <tr>
      <td colspan="4" align="center" valign="top"><strong>SERVICE</strong></td>
      <td width="2%"></td>
      <td colspan="4" valign="top"><strong>MALADE</strong></td>
      </tr>
    <tr>
      <td colspan="4" align="center">${formulaireVente.ordService}</td>
      <td></td>
      <td width="14%">Nom:</td>
      <td width="14%">${formulaireVente.cliNom}</td>
      <td width="19%">Prenoms:</td>
      <td>${formulaireVente.cliPrenom}</td>
    </tr>
    <tr>
      <td colspan="4" align="center" valign="top">&nbsp;</td>
      <td></td>
      <td>Sexe:</td>
      <td><c:if test="${formulaireVente.cliGenre=='M'}">Masculin</c:if> 
                      <c:if test="${formulaireVente.cliGenre=='F'}">Féminin</c:if></td>
      <td>Age: </td>
      <td>Poids:  Kg</td>
    </tr>
    <tr>
      <td colspan="4" align="center">&nbsp;</td>
      <td></td>
      <td>Etage:</td>
      <td>&nbsp;</td>
      <td>Chambre N°:</td>
      <td>Lit N°:</td>
    </tr>
    <tr>
      <td colspan="2"></td>
      <td colspan="2"></td>
      <td></td>
      <td>Date d'entrée:</td>
      <td colspan="3"></td>
      </tr>
    <tr>
      <td colspan="2"></td>
      <td colspan="2"></td>
      <td></td>
      <td colspan="3"><strong>FACTURE N°</strong></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="9"><hr /></td>
      </tr>
    <tr>
      <td colspan="9" align="center">
      <table width="94%" height="344" border="1" class="ordonnance">
        <tr class="ordre1">
          <td colspan="3" align="center" class="ordre2"> <strong>ORDONNANCE MEDICALE</strong></td>
          <td colspan="3" align="center" class="ordre2"><strong>PHARMACIE</strong></td>
          </tr>
        <tr>
          <td width="15%" align="center" class="ordre2">CODE</td>
          <td align="center" class="ordre2">PRODUITS</td>
          <td width="12%" align="center" class="ordre2">Qté Cdée</td>
          <td width="12%" align="center" class="ordre2">Qté Livrée</td>
          <td width="12%" align="center" class="ordre2">Prix Unitaire</td>
          <td width="17%" align="center" class="ordre2">TOTAL</td>
        </tr>
        <c:forEach var="ld" items="${ligneDispensations}">
        <tr>
          <td class="ordre2">${ld.pharmProduit.prodCode}</td>
          <td class="ordre2">${ld.pharmProduit.prodLib}</td>
          <td class="ordre2">${ld.ldQteDem}</td>
          <td class="ordre2">${ld.ldQteServi}</td>
          <td class="ordre2">${ld.ldPrixUnit}</td>
          <td class="ordre2">${ld.ldQteServi * ld.ldPrixUnit}</td>
        </tr>
        </c:forEach>
        <tr>
          <td colspan="3" rowspan="2" class="ordre2">&nbsp;</td>
          <td height="30" colspan="3" align="left" class="ordre2">TOTAL: <strong>${totalGlobal}</strong></td>
          </tr>
        <tr>
          <td height="30" colspan="3" align="left" class="ordre2">Net à payer: <c:if test="${formulaireVente.typeVente =='I'}"><strong>${totalGlobal*(100-formulaireVente.taux)/100}</strong></c:if> <c:if test="${formulaireVente.typeVente =='D'}"><strong>${totalGlobal}</strong></c:if></td>
        </tr>
        <tr>
          <td height="46" colspan="3" align="center" class="ordre2">Date: 26-05-2017 <br />LE PRESCRIPTEUR <br /><br /><br /><br /></td>
          <td colspan="3" align="left" class="ordre2">Date: 26-05-2017<br />LE PHARMACIEN<br /><br /><br /><br /></td>
        </tr>
      </table>       </td>
      </tr>
  </table>
	

</center>
</div>
</div>
</div>

</form:form>