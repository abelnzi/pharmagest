<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire" 
        otherwise="/login.htm" redirect="/index.htm" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/scripts/jquery.PrintArea.js" />

<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    
	<li class="first">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>">Menu principal</a>
	</li>
	<li class=" active">
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire
	</li>

	<li >
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
	</li>
    <li>
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
	</li>
	<li >
				<a href="<c:url value="/module/pharmagest/listInventaire.form"/>">Validation d'inventaire</a>
	</li>
    <li>
				<a href="<c:url value="/module/pharmagest/listHistoInventaire.form"/>">Historique des inventaires</a>
	</li>
	
	<!-- Add further links here -->
</ul>
</div>
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



<div>
<h3 ><span style="font-size:36px">.</span> Edition de la Fiche inventaire</h3>
</div>
<br>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/inventaireImp.form"
	modelAttribute="formulaireTraitementsPPS"
	commandName="formulaireTraitementsPPS"  >

<c:if test="${var =='0'}">
<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="12%" height="60">Programme <span style="color:#F11A0C">*</span></td>
      <td width="21%"><form:select path="programme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireTraitementsPPS.getProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="programme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="66%"><input name="btn_editer" type="submit"
												id="btn_editer" title="editer" value="Editer"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>

</c:if>

<br>

<c:if test="${var =='1'}">

<div>

	<b class="boxHeader">
    	
    </b>
	<div id="print" class="box">
    <div>
        <table width="1435" border="0" cellpadding="7" cellspacing="7">
          <tbody>
            <tr>
              <td width="262"><strong>Fiche d'inventaire du programme :</strong></td>
              <td width="1023">${programme}</td>
              <td width="80">
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
                  <td width="8%" rowspan="2"><strong>Code Produit</strong></td>
                  <td width="17%" rowspan="2"><strong>D&eacute;signation</strong></td>
                  <td width="7%" rowspan="2"><strong>Unit&eacute;</strong></td>
                  <td colspan="3"><div align="center"><strong>Lot 1</strong></div></td>
                  <td colspan="3"><div align="center"><strong>Lot 2</strong></div></td>
                  <td colspan="3"><div align="center"><strong>Lot 3</strong></div></td>
                  <td width="8%" colspan="3" rowspan="2"><div align="center"><strong>Totals</strong></div></td>
                </tr>
                
               
								<tr>
									<td width="5%">Quantit&eacute;</td>
									<td width="6%">N&deg; lot</td>
									<td width="9%">Date p&eacute;remption</td>
									<td width="6%">Quantit&eacute;</td>
									<td width="5%">N&deg; lot</td>
									<td width="9%">Date p&eacute;remption</td>
									<td width="5%">Quantit&eacute;</td>
									<td width="6%">N&deg; lot</td>
									<td width="9%">Date p&eacute;remption</td>
                                </tr>
                             	 <c:forEach var="li" items="${list}">
								<tr>
									<td width="8%"><div align="left">${li.prodCode}</div></td>
									<td width="17%"><div align="left">${li.prodLib}</div></td>
									<td width="7%">${li.prodUnite}</td>
									<td width="5%">&nbsp;</td>
									<td width="6%">&nbsp;</td>
									<td width="9%">&nbsp;</td>
									<td width="6%">&nbsp;</td>
									<td width="5%">&nbsp;</td>
									<td width="9%">&nbsp;</td>
									<td width="5%">&nbsp;</td>
                                    <td width="6%">&nbsp;</td>
                                    <td width="9%">&nbsp;</td>
                                    <td colspan="3">&nbsp;</td>
								</tr>
			  				 </c:forEach>
                 
                
			
      </tbody>
</table>   

</div> 
	</div>
</c:if>
</form:form>
