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
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Fichiers de base</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/fournisseur.form"/>">Fournisseur</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/medecin.form"/>">Prescripteur</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/regime.form"/>">R&eacute;gime</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/produit.form"/>">Produit</a>
	</li>
    <li class="active">
		<a href="<c:url value="/module/pharmagest/programme.form"/>">Programme</a>
	</li>
    <li >
		<a href="<c:url value="/module/pharmagest/classePharma.form"/>">Classe de produit</a>
	</li>
	<li >
                <a href="<c:url value="/module/pharmagest/site.form"/>">Site</a>
    </li>
	
	<!-- Add further links here -->
</ul></div>

<div>
<h3 ><span style="font-size:36px">.</span> Programme</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/programme.form"
	modelAttribute="formulaireProgramme">
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
		        <td width="10%">Lib&eacute;ll&eacute;<span style="color:#F11A0C">*</span></td>
		        <td width="72%"><form:input path="programLib" /> <form:errors path="programLib" cssClass="error" /></td>
		        <td width="18%">&nbsp;</td>
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>
</div>

<br>
<c:if test="${!empty programmes}"> 
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="1" cellspacing="0" cellpadding="7">
		    <tbody>
		       <tr>  
   <th width="18%">Identifiant</th>  
   <th width="48%">Lib&eacute;ll&eacute;</th>
   <th width="34%">Action</th>  
  </tr>  
              <c:forEach items="${programmes}" var="programme">  
		      <tr>  
    <td><c:out value="${programme.programId}"/></td>  
    <td><c:out value="${programme.programLib}"/></td>
    <td align="center"><a
									href="<c:url value="/module/pharmagest/programme.form"><c:param name="editId" value="${programme.programId}"/></c:url>">Editer</a> | <a
									href="<c:url value="/module/pharmagest/programme.form"><c:param name="deleteId" value="${programme.programId}"/></c:url>">Supprimer</a></td>  
   </tr>  
              </c:forEach>  
	        </tbody>
	      </table>
		</div>
	</div>
</div>
</c:if>  
<script type="text/javascript">
            $("#programLib").attr('required', true); 
</script>

</form:form>