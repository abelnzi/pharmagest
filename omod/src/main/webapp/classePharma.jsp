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
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	<li >
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
    <li class="active">
		<a href="<c:url value="/module/pharmagest/classePharma.form"/>">Classe de produit</a>
	</li>
		<li >
                <a href="<c:url value="/module/pharmagest/site.form"/>">Site</a>
    </li>
	
	<!-- Add further links here -->
</ul></div>

<div>
<h3 ><span style="font-size:36px">.</span> ClassePharma</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/classePharma.form"
	modelAttribute="formulaireClassePharma">
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
		        <td width="11%">Lib&eacute;ll&eacute;<span style="color:#F11A0C">*</span></td>
		        <td width="71%"><form:input path="classPharmNom" /> <form:errors path="classPharmNom" cssClass="error" /></td>
		        <td width="18%">&nbsp;</td>
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>
</div>

<br>
<c:if test="${!empty classePharmas}"> 
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="1" cellspacing="0" cellpadding="7">
		    <tbody>
		       <tr>  
   <th width="23%">Identifiant</th>  
   <th width="38%">Lib&eacute;ll&eacute;</th>
   <th width="39%">Action</th>  
  </tr>  
              <c:forEach items="${classePharmas}" var="classePharma">  
		      <tr>  
    <td><c:out value="${classePharma.classPharmId}"/></td>  
    <td><c:out value="${classePharma.classPharmNom}"/></td>
    <td align="center"><a
									href="<c:url value="/module/pharmagest/classePharma.form"><c:param name="editId" value="${classePharma.classPharmId}"/></c:url>">Editer</a> | <a
									href="<c:url value="/module/pharmagest/classePharma.form"><c:param name="deleteId" value="${classePharma.classPharmId}"/></c:url>">Supprimer</a></td>  
   </tr>  
              </c:forEach>  
	        </tbody>
	      </table>
		</div>
	</div>
</div>
</c:if>  
<script type="text/javascript">
            $("#classPharmNom").attr('required', true); 
</script>

</form:form>