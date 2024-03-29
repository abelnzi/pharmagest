<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    
			<li class="first "><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Menu principal</a></li>
			
    		<li >
				<a href="<c:url value="/module/pharmagest/saisiesRPPS.form"/>">Saisies des rapports des PPS</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/importRapportConso.form"/>">Importation des rapports des ESPC/PPS</a>
            </li>
            <li class="active">
				<a href="<c:url value="/module/pharmagest/listPPSModification.form"/>">Modification/Validation des rapports ESPC/PPS
            </li>
            
            <li >
				<a href="<c:url value="/module/pharmagest/listCommandeSite.form"/>">Traitements</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/listHistoDistribution.form"/>">Historique des rapports mensuel de consommation</a>
			</li>
            
			<li>
				<a href="<c:url value="/module/pharmagest/listBorderoDistribution.form"/>">Historique des Bordereaux de transfert</a>
			</li>
			<li>
				<a href="<c:url value="/module/pharmagest/ruptureESPC.form"/>">Liste des ESPC OU PPS ayant connu une rupture</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/promptitudeESPC.form"/>">Promptitude des Rapports de consommation</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/etatStockESPC.form"/>">Etat de Stock des ESPC/PPS</a>
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
	action="${pageContext.request.contextPath}/module/pharmagest/listPPSModification.form"
	modelAttribute="formulaireModif"
	commandName="formulaireModif"  >
<div>
<h3 ><span style="font-size:36px">.</span> Choix des rapport des ESPC/PPS � modifier</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succ�s</div>
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
      <td width="10%" height="60">Libell&eacute; ESPC/PPS<span style="color:#F11A0C">*</span></td>
      <td width="14%">               
               
                <form:select path="site" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${sites}">
                            <option <c:if test="${formulaireModif.getSite().getSitId()==item.getSitId()}">selected="selected"</c:if>    				  							value="${item.getSitId()}">${item.getSitLib()} </option>
                       </c:forEach>
                </form:select>
                <form:errors path="site" cssClass="error"/>
      </td>
      <td width="1%">&nbsp;</td>
      <td width="7%">Programme <span style="color:#F11A0C">*</span></td>
      <td width="14%"><form:select path="programme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireModif.getProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="programme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="5%">Mois de</td>
      <td width="6%"> <!--<form:input path="dateParam" />--><openmrs_tag:dateField formFieldName="dateParam" startValue="${obsDate}" />
</td>
      <td width="1%">&nbsp;</td>
      <td width="41%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">une ligne inser�e</div>
</c:if>
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">une ligne supprim&eacute;e</div>
</c:if>
<!--<div align="left">${info}</div>-->
<br>
<!--<p>${date}</p>-->

        <div>
	<b class="boxHeader"></b>
	<div class="box">
<table width="99%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="10%">Code Commande</td>
                  <td width="14%">Libell&eacute; ESPC/PPS</td>
                  <td width="17%">Programme</td>
                  <td width="12%">Date commande</td>
                  <td width="13%">P&eacute;riode</td>
                  <td width="13%">Stock maximum</td>
                  <td width="13%">Type rapport</td> 
                  <td width="21%">Action</td>
                </tr>
                
                <c:forEach var="lo" items="${commandeSites}">
								<tr>
									<td width="10%"><div align="left">${lo.comSiteCode}</div></td>
                                    <td width="14%">${lo.pharmSite.sitLib}</td>
                                    <td width="17%">${lo.pharmProgramme.programLib}</td>
									<td width="12%">${lo.comSiteDateCom}</td>
									<td width="13%">${lo.comSitePeriodDate}</td>
									<td width="13%"><div align="left">${lo.comStockMax}</div></td>
									<td width="13%"><strong style="color:#F7072C">${lo.comSitePeriodLib}</strong></td>
									<td width="21%"><a href="<c:url value="/module/pharmagest/modifierPPS.form"><c:param name="paramId" 
                                                  value="${lo.comSiteId}"/></c:url>">Choix</a>
                                    </td>
								</tr>
                 
                
			   </c:forEach>
      </tbody>
</table>   

</div> 
	</div>

</form:form>