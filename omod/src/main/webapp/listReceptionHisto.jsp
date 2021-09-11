<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />

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
            
            <li>
				<a href="<c:url value="/module/pharmagest/listReceptionValid.form"/>">Validation des receptions</a>
			</li>
            <li  class="active">
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
	action="${pageContext.request.contextPath}/module/pharmagest/listReceptionHisto.form"
	modelAttribute="formulaireReception"
	commandName="formulaireReception"  >
<div>
<h3 ><span style="font-size:36px">.</span>  Historique de la réception de produits</h3>
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
      <td width="12%" height="60">Programme <span style="color:#F11A0C">*</span></td>
      <td width="18%"><form:select path="pharmProgramme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireReception.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/> </td>
      <td width="2%">&nbsp;</td>
      <td width="11%">Date d&eacute;but :</td>
      <td width="11%"> <openmrs_tag:dateField formFieldName="dateDebut" startValue="${obsDate}" />
</td>
      <td width="2%">&nbsp;</td>
      <td width="9%">Date fin :</td>
      <td width="11%"><openmrs_tag:dateField formFieldName="dateFin" startValue="${obsDate}" /></td>
      <td width="24%"><input name="btn_rechercher" type="submit"
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
<table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           <thead>
          
                <tr>
                  <th width="10%">Bordereau de livraison</th>
                  <th width="14%">Fournisseur</th>
                  <th width="17%">Programme</th>
                  <th width="12%">Date de la reception</th>
                  <th width="21%">Action</th>
                </tr>
                </thead>
                <tbody> 
                <c:forEach var="lo" items="${receptions}">
								<tr>
									<td width="10%"><div align="left">${lo.recptNum}</div></td>
                                    <td width="14%">${lo.pharmFournisseur.fourDesign1}</td>
                                    <td width="17%">${lo.pharmProgramme.programLib}</td>
									<td width="12%">${lo.recptDateRecept}</td>
									<td width="21%"><a href="<c:url value="/module/pharmagest/histoReception.form"><c:param name="paramId" 
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