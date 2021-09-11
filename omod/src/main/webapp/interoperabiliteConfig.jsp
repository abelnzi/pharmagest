<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="pharmacie interoperabilite ftp" 
		  otherwise="/login.htm" redirect="/module/pharmagest/interoperabiliteConfig.form" />

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
	<ul id="menu">
	  <li class=" first"> <a href="<c:url value="/module/pharmagest/interoperabiliteMenu.form"/>">Menu principal</a> </li>
	  <li class="active"> <a href="<c:url value="/module/pharmagest/interoperabiliteConfig.form"/>">Param&eacute;trage FTP</a> </li>
	  <li> <a href="<c:url value="/module/pharmagest/interoperabilite.form"/>">Transfert de la commande</a> </li>
	  
	  <!-- Add further links here -->
	</ul>
  </div>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/interoperabiliteConfig.form"
	modelAttribute="formulaireParametrage"
	commandName="formulaireParametrage"  >
    
      <h3 ><span style="font-size:36px">.</span>Param&eacute;trage FTP</h3>
    
    <div>
       <table width="100%" border="0">
          <tbody>
            <tr>
              <td><input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Modifier" onClick="return confirm('Voulez vous modifier?') ? true : false;">
              </tr>
          </tbody>
        </table>
	</div>
    <br>
    
    <c:if test="${mess =='success'}">
		<div id="openmrs_msg">Modifier avec succ&egrave;s</div>
	</c:if>
    <c:if test="${mess =='echec'}">
		<div id="openmrs_msg">Le chemin sp&eacute;cifi&eacute; <strong>${formulaireParametrage.repExtract}</strong> n'existe pas </div>
	</c:if>
    
<b class="boxHeader">Configuration de la connexion FTP</b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
  <table width="100%" border="0" cellspacing="0" cellpadding="7">
    <tbody>
     
      <tr>
        <td width="9%"><strong>H&ocirc;te</strong></td>
        <td width="1%"><strong>:</strong></td>
        <td width="18%"><form:input path="hote" cssStyle="width:150px"/></td>
        <td width="1%">&nbsp;</td>
        <td width="10%"><strong>Port</strong></td>
        <td width="1%"><strong>:</strong></td>
        <td width="60%" height="38"><form:input path="port" cssStyle="width:150px"/></td>
      </tr>
      <tr>
        <td><strong>Identifiant</strong></td>
        <td><strong>:</strong></td>
        <td><form:input path="login" cssStyle="width:150px"/></td>
        <td>&nbsp;</td>
        <td><strong>Mot de passe</strong></td>
        <td><strong>:</strong></td>
        <td height="38"><form:input path="mdp" cssStyle="width:150px"/></td>
      </tr>
    </tbody>
  </table>
  </div>
  </div>
  
  <br>
  <b class="boxHeader">Configuration du repertoire d'extraction</b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
  <table width="100%" border="0" cellspacing="0" cellpadding="7">
    <tbody>
      
      <tr>
        <td width="14%"><strong>Repertoire d'extraction</strong></td>
        <td width="1%"><strong>:</strong></td>
        <td width="85%" height="38"><form:input path="repExtract" cssStyle="width:250px"/></td>
        </tr>
    </tbody>
  </table>
  </div>
  </div>
	
</form:form>
