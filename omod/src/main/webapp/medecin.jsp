<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie parametres" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>

<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	<li class="first">
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Menu principal</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/fournisseur.form"/>">Fournisseur</a>
	</li>
    <li class="active">
		<a href="<c:url value="/module/pharmagest/medecin.form"/>">Prescripteur</a>
	</li>
   <!-- <li >
		<a href="<c:url value="/module/pharmagest/regime.form"/>">R&eacute;gime</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/produit.form"/>">Produit</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/programme.form"/>">Programme</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/classePharma.form"/>">Classe de produit</a>
	</li>-->
		<li >
                <a href="<c:url value="/module/pharmagest/site.form"/>">Site</a>
    </li>
    <li >
       <a href="<c:url value="/module/pharmagest/donneesBasesProduits.form"/>">Importation et configuration des produits et programme</a>
    </li>
	<!-- Add further links here -->
</ul></div>

<div>
<h3 ><span style="font-size:36px">.</span> Prescripteur</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/medecin.form"
	modelAttribute="formulaireMedecin" commandName="formulaireMedecin">
<br>
<input type="hidden" id="identifiant" name="identifiant" value="<c:out value="${identifiant}"/>">

<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><c:if test="${var =='2'}"><input type="submit" name="btn_modifier" id="btn_modifier" value="Modifier"></c:if>
      	  <c:if test="${var !='2'}"><input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Enregistrer"></c:if>
        <input type="reset" name="reset" id="reset" value="Annuler"></td>
      </tr>
  </tbody>
</table>

</div>
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="13%">Nom<span style="color:#F11A0C"> *</span></td>
		        <td width="36%"><form:input path="medNom" /> <form:errors path="medNom" cssClass="error" /></td>
		        <td width="1%">&nbsp;</td>
		        <td width="16%">Pr&eacute;nom <span style="color:#F11A0C">*</span></td>
		        <td width="34%"><form:input path="medPrenom" /> <form:errors path="medPrenom" cssClass="error" /></td>
	          </tr>
		     
		      <tr>
		        <td>Code </td>
		        <td><form:input path="medCode" /> <form:errors path="medCode" cssClass="error" /></td>
		        <td>&nbsp;</td>
		        <td>T&eacute;l&eacute;phone </td>
		        <td><form:input path="medTel" /> <form:errors path="medTel" cssClass="error" /></td>
	          </tr>
               <tr>
		        <td>Adresse</td>
		        <td><form:input path="medAdresse" /> <form:errors path="medAdresse" cssClass="error" /></td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
		        <td>&nbsp;</td>
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>
</div>

<br>
<c:if test="${!empty medecins}"> 
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="1" cellspacing="0" cellpadding="7">
		    <tbody>
		       <tr>  
   <th width="7%">Identifiant</th>  
   <th width="7%">Code</th>
   <th width="18%">Nom</th>
   <th width="20%">Pr&eacute;nom</th>  
   <th width="17%">T&eacute;l&eacute;phone</th>  
   <th width="16%">Adresse</th>  
   <th width="15%">Action</th>  
  </tr>  
              <c:forEach items="${medecins}" var="medecin">  
		      <tr>  
    <td><c:out value="${medecin.medId}"/></td>  
    <td><c:out value="${medecin.medCode}"/></td>
    <td><c:out value="${medecin.medNom}"/></td>
    <td><c:out value="${medecin.medPrenom}"/></td>  
    <td><c:out value="${medecin.medTel}"/></td>  
    <td><c:out value="${medecin.medAdresse}"/></td>  
    <td align="center"><a
									href="<c:url value="/module/pharmagest/medecin.form"><c:param name="editId" value="${medecin.medId}"/></c:url>">Editer</a> | <a
									href="<c:url value="/module/pharmagest/medecin.form"><c:param name="deleteId" value="${medecin.medId}"/></c:url>">Supprimer</a></td>  
   </tr>  
              </c:forEach>  
	        </tbody>
	      </table>
		</div>
	</div>
</div>
</c:if>  
<script type="text/javascript">
            $("#medNom").attr('required', true); 
			$("#medPrenom").attr('required', true); 
</script>
</form:form>