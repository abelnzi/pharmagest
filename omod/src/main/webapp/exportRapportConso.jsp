<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false ;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false;Boolean activeInter=true;
 %>
<%@ include file="template/localHeader.jsp"%> 

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/exportRapportConso.form"
	modelAttribute="formulaireRapportCommande"
	commandName="formulaireRapportCommande"  >
<div>
<h3 ><span style="font-size:36px">.</span> Exportation du rapport de consommation</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Fichier sauvegard&eacute; avec succ&egrave;s</div>
</c:if>
<c:if test="${mess =='echec'}">
		<div id="openmrs_msg">Echec de l'op&eacute;ration</div>
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
                            <option <c:if test="${formulaireRapportCommande.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="9%">Mois de</td>
      <td width="11%"> <!--<form:input path="dateCommande" />--><openmrs_tag:dateField formFieldName="dateCommande" startValue="${obsDate}" />
</td>
      <td width="1%">&nbsp;</td>
      <td width="48%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Exporter"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>

</form:form>