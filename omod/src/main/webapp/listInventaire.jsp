<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire valide" 
        otherwise="/login.htm" redirect="/index.htm" />
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
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire</li>

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
	action="${pageContext.request.contextPath}/module/pharmagest/listInventaire.form"
	modelAttribute="formulaireInventaire"
	commandName="formulaireInventaire"  >
<div>
<h3 ><span style="font-size:36px">.</span> Choix de l'inventaire &agrave; traiter</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succ&egrave;s</div>
</c:if>
<c:if test="${mess =='vide'}">
		<div id="openmrs_msg">Aucun inventaire &agrave;  traiter</div>
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
      <td width="10%" height="60">Programme <span style="color:#F11A0C">*</span></td>
      <td width="20%"><form:select path="pharmProgramme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireInventaire.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="10%">Date de l'inventaire</td>
      <td width="10%"> <!--<form:input path="dateParam" />--><openmrs_tag:dateField formFieldName="dateParam" startValue="${obsDate}" />
</td>
      <td width="1%">&nbsp;</td>
      <td width="48%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
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
<p>${date}</p>

        <div>
	<b class="boxHeader"></b>
	<div class="box">
<table width="99%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="10%">Code Inventaire</td>
                  <td width="12%">Date de saisie</td>
                  <td width="13%">Mois</td>
                  <td width="13%">Statut</td> 
                  <td width="21%">Action</td>
                </tr>
                
                <c:forEach var="lo" items="${inventaires}">
								<tr>
									<td width="10%"><div align="left">${lo.invId}</div></td>
                                    <td width="12%">${lo.invDateCrea}</td>
									<td width="13%">${lo.invDeb}</td>
									<td width="13%"><div align="left">Non Valid&eacute;</div></td>
									<td width="21%"><a href="<c:url value="/module/pharmagest/inventaireValide.form"><c:param name="paramId" 
                                                  value="${lo.invId}"/></c:url>">Choix</a>
                                    </td>
								</tr>
                 
                
			   </c:forEach>
      </tbody>
</table>   

</div> 
	</div>

</form:form>