<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>

<spring:htmlEscape defaultHtmlEscape="true" />
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<ul id="menu">
	<li class="first">
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	<li class="active">
		<a href="<c:url value="/module/pharmagest/fournisseur.form"/>">Fournisseur</a>
	</li>
    <li >
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
	</li>
	
	<!-- Add further links here -->
</ul>

<div>
<h3 >Fournisseur</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/fournisseur.form"
	modelAttribute="formulaireFournisseur" commandName="formulaireFournisseur">
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
		        <td width="13%">D&eacute;signation 1<span style="color:#F11A0C">*</span></td>
		        <td width="36%"><form:input path="fourDesign1" /> <form:errors path="fourDesign1" cssClass="error" /></td>
		        <td width="1%">&nbsp;</td>
		        <td width="16%">D&eacute;signation 2</td>
		        <td width="34%"><form:input path="fourDesign2" /> <form:errors path="fourDesign2" cssClass="error" /></td>
	          </tr>
		     
		      <tr>
		        <td>T&eacute;l&eacute;phone 1</td>
		        <td><form:input path="fourTel1" /> <form:errors path="fourTel1" cssClass="error" /></td>
		        <td>&nbsp;</td>
		        <td>T&eacute;l&eacute;phone 2</td>
		        <td><form:input path="fourTel2" /> <form:errors path="fourTel2" cssClass="error" /></td>
	          </tr>
               <tr>
		        <td>Adresse</td>
		        <td><form:input path="fourAdresse" /> <form:errors path="fourAdresse" cssClass="error" /></td>
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
<c:if test="${!empty fournisseurs}"> 
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="1" cellspacing="0" cellpadding="7">
		    <tbody>
		       <tr>  
   <th>Identifiant</th>  
   <th>D&eacute;signation 1</th>
   <th>D&eacute;signation 2</th>
   <th>Adresse</th>  
   <th>T&eacute;l&eacute;phone 1</th>  
   <th>T&eacute;l&eacute;phone 2</th>  
   <th>Action</th>  
  </tr>  
              <c:forEach items="${fournisseurs}" var="fournisseur">  
		      <tr>  
    <td><c:out value="${fournisseur.fourId}"/></td>  
    <td><c:out value="${fournisseur.fourDesign1}"/></td>
    <td><c:out value="${fournisseur.fourDesign2}"/></td>
    <td><c:out value="${fournisseur.fourAdresse}"/></td>  
    <td><c:out value="${fournisseur.fourTel1}"/></td>  
    <td><c:out value="${fournisseur.fourTel2}"/></td>  
    <td align="center"><a
									href="<c:url value="/module/pharmagest/fournisseur.form"><c:param name="editId" value="${fournisseur.fourId}"/></c:url>">Editer</a> | <a
									href="<c:url value="/module/pharmagest/fournisseur.form"><c:param name="deleteId" value="${fournisseur.fourId}"/></c:url>">Supprimer</a></td>  
   </tr>  
              </c:forEach>  
	        </tbody>
	      </table>
		</div>
	</div>
</div>
</c:if>  
<script type="text/javascript">
            $("#fourDesign1").attr('required', true); 
</script>
</form:form>