<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire valide" 
        otherwise="/login.htm" redirect="/module/pharmagest/inventaireValide.form" />
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    
			
	<li class="first">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>">Menu principal</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire</a>
	</li>

	<li >
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
	</li>
    <li>
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
	</li>
	<li class=" active">
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


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/inventaireValide.form"
	modelAttribute="formulaireInventaire"
	commandName="formulaireInventaire"  >
<div>
<h3 ><span style="font-size:36px">.</span>Traitement de l'inventaire N&deg; : ${inventaire.invId}</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<br>
<br>
<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="5%">Programme</td>
      <td width="5%">:</td>
      <td width="5%">${inventaire.pharmProgramme.programLib}</td>
      <td width="5%">&nbsp;</td>
      <td width="5%" height="60">Code </td>
      <td width="1%">:</td>
      <td width="17%">                  
        ${inventaire.invId}        
      </td>
      <td width="1%">&nbsp;</td>
      <td width="12%">Date de l'inventaire</td>
      <td width="1%">:</td>
      <td width="18%"> ${inventaire.invDeb} </td>
      <td width="1%">&nbsp;</td>
      <td width="6%">Date saisie </td>
      <td width="1%">:</td>
      <td width="16%">${inventaire.invDateCrea}</td>
      <td width="1%">&nbsp;</td>
      <td width="6%">Statut</td>
      <td width="1%">:</td>
      <td width="13%"><c:if test="${tab =='1'}">
                        <div id="openmrs_msg">Non valide</div>
                </c:if>
                <c:if test="${tab =='2'}">
                        <div id="openmrs_msg">valide</div>
                </c:if>
       </td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">une ligne inserée</div>
</c:if>
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">une ligne supprim&eacute;e</div>
</c:if>
<!--<div align="left">${info}</div>-->
<br>

        <div>
	<b class="boxHeader"></b>
	<div class="box">
    <c:if test="${tab =='1'}">
<table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td>Code article</td>
                  <td>D&eacute;signation</td>
                  <td>Unit&eacute;</td>
                  <td>Num&eacute;ro du lot</td>
                  <td>P&eacute;remption</td>
                  <td>Quantit&eacute; th&eacute;orique</td>
                  <td>Quantit&eacute; physique</td>
                  <td>Prix moyen pond&eacute;r&eacute;</td>
                  <td>Total th&eacute;orique</td>
                  <td>Total physique</td>
                  <td>Ecart</td>
                  <td>Observation</td>
                  <td width="7%">Quantit&eacute; approuv&eacute;e</td>
                </tr>
                <% int x = 1;  %>	
                <c:forEach var="lo" items="${inventaire.pharmLigneInventaires}">
                
                <c:set var="ecart" value="${lo.ecart}" scope="request" />
                
                 <% Integer ecart=  (Integer) request.getAttribute("ecart");       
                    String attri ="";
                    if(!(ecart==0)){
                         attri = "color:#F3F3F3;background:#F70606";
                        }
                                                
                 %>
                
								<tr  style="<%= attri %>">
									<td width="17%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodCode}</div></td>
									<td width="12%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodLib}</div></td>
									<td width="9%">${lo.pharmProduitAttribut.pharmProduit.prodUnite}</td>
									<td width="9%">${lo.pharmProduitAttribut.prodLot}</td>
                                    <td width="12%"><div align="left">${lo.pharmProduitAttribut.prodDatePerem}</div></td>
									<td width="10%"><div align="left">${lo.qteLogique}</div></td>
									<td width="10%"><div align="left">${lo.qtePhysique}</div></td>
									<td width="9%">${lo.prixInv}</td>
									<td width="9%">${lo.qteLogique*lo.prixInv}</td>
									<td width="9%">${lo.qtePhysique*lo.prixInv}</td>
									<td width="9%"><div align="left">${lo.ecart}</div></td>								
									<td width="7%">${lo.observation}</td>
                                    
									<td width="7%"><input type="number" name="qtePro<%= x %>" id="qtePro<%= x %>" 
                                    				value="${lo.qtePhysique}"></td>
									<input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${lo.pharmProduitAttribut.prodAttriId}">
                                    
								</tr>
								
                <% x = x+1;  %> 
                
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
<table width="2000" border="0" align="right" cellpadding="7" cellspacing="7">
  <tbody>
    <tr>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="26">&nbsp;</td>
      <td width="108" align="right"><input name="btn_valder" type="submit" onClick="return confirm('Voulez vous valider?') ? true : false;" 
												id="btn_valder" title="valider" value="Valider"></td>
    </tr>
  </tbody>
</table>
</c:if>
</div></div>

<c:if test="${tab =='2'}">
<div>
	<b class="boxHeader"></b>
	<div class="box">

<table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                 <td>Code article</td>
                  <td>D&eacute;signation</td>
                  <td>Unit&eacute;</td>
                  <td>Num&eacute;ro du lot</td>
                  <td>P&eacute;remption</td>
                  <td>Quantit&eacute; th&eacute;orique</td>
                  <td>Quantit&eacute; physique</td>
                  <td>Prix moyen pond&eacute;r&eacute;</td>
                  <td>Total th&eacute;orique</td>
                  <td>Total physique</td>
                  <td>Ecart</td>
                  <td>Observation</td>
                  <td width="7%">Quantit&eacute; approuv&eacute;e</td>
                </tr>
                
                <c:forEach var="lo" items="${inventaire.pharmLigneInventaires}">
								<tr>
									<td width="17%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodCode}</div></td>
									<td width="12%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodLib}</div></td>
									<td width="9%">${lo.pharmProduitAttribut.pharmProduit.prodUnite}</td>
									<td width="9%">${lo.pharmProduitAttribut.prodLot}</td>
                                    <td width="12%"><div align="left">${lo.pharmProduitAttribut.prodDatePerem}</div></td>
									<td width="10%"><div align="left">${lo.qteLogique}</div></td>
									<td width="10%"><div align="left">${lo.qtePhysique}</div></td>
									<td width="9%">${lo.prixInv}</td>
									<td width="9%">${lo.qteLogique*lo.prixInv}</td>
									<td width="9%">${lo.qtePhysique*lo.prixInv}</td>
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
</c:if>
</form:form>