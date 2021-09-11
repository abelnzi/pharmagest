<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie distribution" 
        otherwise="/login.htm" redirect="/index.htm" />

<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false; Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>

<%@ page import="java.util.List"%>

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>



<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/dispensDistrict.form"
	modelAttribute="formulaireRapportCommande"
	commandName="formulaireRapportCommande"   enctype="multipart/form-data">
<div>
<h3 ><span style="font-size:36px">.</span> Importation et configuration des produits et programme</h3>
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
      <td width="12%" height="60">Choix du fichier :</td>
      <td width="24%">  <input type="file" name="file" id="file" />
</td>
      <td width="1%">&nbsp;</td>
      <td width="63%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Configurer"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>

</form:form>