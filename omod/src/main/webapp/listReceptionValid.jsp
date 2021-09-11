<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception valide" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    
			<li class="first "><a href="<c:url value="/module/pharmagest/receptionMenu.form"/>">Menu principal</a></li>
			
    		<li >
				<a href="<c:url value="/module/pharmagest/saisieReception.form"/>">Saisies des receptions</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/listReceptionModif.form"/>">Modification des receptions
            </li>
            
            <li  class="active">
				<a href="<c:url value="/module/pharmagest/listReceptionValid.form"/>">Validation des receptions</a>
			</li>
            <li >
				<a href="<c:url value="/module/pharmagest/listReceptionHisto.form"/>">Historique des receptions</a>
			</li>
            <li >
				<a href="<c:url value="/module/pharmagest/satisfactionESPC.form"/>">Taux de satisfaction</a>
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
	action="${pageContext.request.contextPath}/module/pharmagest/listReceptionValid.form"
	modelAttribute="formulaireReception"
	commandName="formulaireReception"  >
<div>
<h3 ><span style="font-size:36px">.</span>  Validation de la réception de produits</h3>
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
      <td width="10%" height="60">Programme <span style="color:#F11A0C">*</span></td>
      <td width="18%"><form:select path="pharmProgramme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireReception.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="5%">Date</td>
      <td width="16%"> <openmrs_tag:dateField formFieldName="recptDateRecept" startValue="${obsDate}" />
</td>
      <td width="1%">&nbsp;</td>
      <td width="49%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>

<br>
<!--<p>${date}</p>-->
<c:if test="${var =='1'}">
        <div>
	<b class="boxHeader"></b>
	<div class="box">
<table width="99%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="10%">Bordereau de livraison</td>
                  <td width="14%">Fournisseur</td>
                  <td width="17%">Programme</td>
                  <td width="12%">Date de la reception</td>
                  <td width="21%">Action</td>
                </tr>
                
                <c:forEach var="lo" items="${receptions}">
								<tr>
									<td width="10%"><div align="left">${lo.recptNum}</div></td>
                                    <td width="14%">${lo.pharmFournisseur.fourDesign1}</td>
                                    <td width="17%">${lo.pharmProgramme.programLib}</td>
									<td width="12%">${lo.recptDateRecept}</td>
									<td width="21%"><a href="<c:url value="/module/pharmagest/validerReception.form"><c:param name="paramId" 
                                                  value="${lo.recptId}"/></c:url>">Choix</a>
                                    </td>
								</tr>
                 
                
			   </c:forEach>
      </tbody>
</table>   

</div> 
	</div>
    </c:if>

</form:form>