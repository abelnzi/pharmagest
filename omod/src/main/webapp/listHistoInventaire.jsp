<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<spring:htmlEscape defaultHtmlEscape="true" />
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

	<li  >
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
	</li>
	<li>
				<a href="<c:url value="/module/pharmagest/listInventaire.form"/>">Validation d'inventaire</a>
	</li>
	<li class=" active">
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
	action="${pageContext.request.contextPath}/module/pharmagest/listHistoInventaire.form"
	modelAttribute="formulaireInventaire"
	commandName="formulaireInventaire"  >
<div>
<h3 ><span style="font-size:36px">.</span> Historique des inventaire</h3>
</div>
<br>
<c:if test="${mess =='success'}"></c:if>
<c:if test="${mess =='vide'}">
		<div id="openmrs_msg">Aucun inventaire trouvé</div>
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
      <td width="9%" height="60">Programme <span style="color:#F11A0C">* :</span></td>
      <td width="15%"><form:select path="pharmProgramme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireTraitementsPPS.getProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="11%">Date de d&eacute;but <span style="color:#F11A0C">*</span> :</td>
      <td width="13%"> <!--<form:input path="dateParam" />--><openmrs_tag:dateField formFieldName="dateParam" startValue="${obsDate}" />
</td>
      <td width="1%">&nbsp;</td>
      <td width="11%">Date de fin <span style="color:#F11A0C">*</span> :</td>
      <td width="12%"><!--<form:input path="dateParam" />-->
        <openmrs_tag:dateField formFieldName="dateParam2" startValue="${obsDate}" /></td>
      <td width="1%">&nbsp;</td>
      <td width="26%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>
<c:if test="${mess =='valid'}"></c:if>
<c:if test="${mess =='delete'}"></c:if>
<!--<div align="left">${info}</div>-->
<br>


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
									<td width="13%"><div align="left">Valid&eacute;</div></td>
									<td width="21%"><a href="<c:url value="/module/pharmagest/histoInventaire.form"><c:param name="paramId" 
                                                  value="${lo.invId}"/></c:url>">Choix</a>
                                    </td>
								</tr>
                 
                
			   </c:forEach>
      </tbody>
</table>   

</div> 
	</div>
<script type="text/javascript">
            $("#dateParam").attr('required', true); 
			 $("#dateParam2").attr('required', true); 
</script>
</form:form>