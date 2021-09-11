<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire" 
        otherwise="/login.htm" redirect="/index.htm" />
 <openmrs:htmlInclude file="/moduleResources/pharmagest/scripts/jquery.PrintArea.js" />
 <%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
 <script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script>

var $sig = jQuery.noConflict();

$sig(document).ready(function(){
	$sig("#print_button").click(function(){
		$sig("#print").printArea();
	});
});

</script>
<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>
<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
	<li class="first">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>">Menu principal</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire</a> </li>

	<li>
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
	</li>
    <li>
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
	</li>
	<li >
				<a href="<c:url value="/module/pharmagest/listInventaire.form"/>">Validation d'inventaire</a>
	</li>
    <li class="active">
				<a href="<c:url value="/module/pharmagest/listHistoInventaire.form"/>">Historique des inventaires</a>
	</li>
            
	</ul>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/histoInventaire.form"
	modelAttribute="formulaireInven"	commandName="formulaireInven">
<div>

<h3 > <span style="font-size:36px">.</span> Historique de l'inventaire </h3>
</div>
<br>

<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="18%">Date d'inventaire</td>
            <td width="1%">:</td>
            <c:set var="d" value="${formulaireInven.invDeb}" scope="request" />
                                    <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
            <td width="26%"> <%= s %></td>
            <td width="1%">&nbsp;</td>
            <td width="17%">Programme</td>
            <td width="1%">:</td>
            <td width="36%">${formulaireInven.pharmProgramme.programLib}</td>
            
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<br>
<br>
<div> <b class="boxHeader"></b>
  <div id="print" class="box">
  <div>
  <table width="874" border="0" cellpadding="7" cellspacing="7">
          <tbody>
            <tr>
              <td width="153"></td>
              <td width="353"> </td>
              <td width="230">
                <div align="right">
                  <input id="print_button" value="Imprimer" type="button" />
                </div>
              </td>
            </tr>
          </tbody>
        </table>
  </div>
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <td><strong>Code article</strong></td>
          <td><strong>D&eacute;signation</strong></td>
          <td><strong>Unit&eacute;</strong></td>
          <td><strong>Num&eacute;ro du lot</strong></td>
          <td><strong>P&eacute;remption</strong></td>
          <td><strong>Quantit&eacute; th&eacute;orique</strong></td>
          <td><strong>Quantit&eacute; physique</strong></td>
          <td><strong>Prix moyen pond&eacute;r&eacute;</strong></td>
          <td><strong>Total th&eacute;orique</strong></td>
          <td><strong>Total physique</strong></td>
          <td><strong>Ecart</strong></td>
          <td><strong>Observation</strong></td>
          <td width="7%"><strong>Quantit&eacute; approuv&eacute;e</strong></td>
        </tr>
        <c:forEach var="lo" items="${inventaires}">
          <tr>
            <td width="17%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodCode}</div></td>
            <td width="12%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodLib}</div></td>
            <td width="9%">${lo.pharmProduitAttribut.pharmProduit.prodUnite}</td>
            <td width="9%">${lo.pharmProduitAttribut.prodLot}</td>
            <td width="12%"><div align="left">${lo.pharmProduitAttribut.prodDatePerem}</div></td>
            <td width="10%"><div align="left">${lo.qteLogique}</div></td>
            <td width="10%"><div align="left">${lo.qtePhysique}</div></td>
            <td>${lo.prixInv}</td>
            <td>${lo.qteLogique*lo.prixInv}</td>
            <td>${lo.qtePhysique*lo.prixInv}</td>
            <td width="9%"><div align="left">${lo.ecart}</div></td>
            <td width="7%">${lo.observation}</td>
            <td width="7%">${lo.qtePro}</td>
          </tr>
           </c:forEach>
          <tr>
            <td colspan="8"><div align="right"><strong>Total global</strong></div></td>
            <td><strong>${totalTheo}</strong></td>
            <td><strong>${totalPhys}</strong></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </tbody>
    </table>
  </div>
</div>

</form:form>