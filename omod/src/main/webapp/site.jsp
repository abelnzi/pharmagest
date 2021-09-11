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
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">M&eacute;nu principal</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/fournisseur.form"/>">Fournisseur</a>
	</li>
     <li >
		<a href="<c:url value="/module/pharmagest/medecin.form"/>">Prescripteur</a>
	</li>
    <!--<li >
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
    <li class="active">
        <a href="<c:url value="/module/pharmagest/site.form"/>">Site</a>
    </li>
    <li >
       <a href="<c:url value="/module/pharmagest/donneesBasesProduits.form"/>">Importation et configuration des produits et programme</a>
    </li>
	
	
	<!-- Add further links here -->
</ul></div>

<div>
<h3 ><span style="font-size:36px">.</span> Site</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/site.form"
	modelAttribute="formulaireSite">
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
              	<td width="18%">Code du site <span style="color:#F11A0C">*</span></td>
		        <td width="18%"><form:input path="sitCode" /> <form:errors path="sitCode" cssClass="error" /></td>
		        <td width="18%">&nbsp;</td>
		        <td width="11%">Nom du site <span style="color:#F11A0C">*</span></td>
		        <td width="71%"><form:input path="sitLib" /> <form:errors path="sitLib" cssClass="error" /></td>
		        
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>
</div>

<br>
<c:if test="${!empty sites}"> 
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="1" cellspacing="0" cellpadding="7">
		    <tbody>
		       <tr>  
   <th width="23%">Code du site</th>  
   <th width="38%">Nom du site</th>
   <th width="39%">Action</th>  
  </tr>  
              <c:forEach items="${sites}" var="site">  
		      <tr>  
    <td><c:out value="${site.sitCode}"/></td>  
    <td><c:out value="${site.sitLib}"/></td>
    <td align="center"><a
									href="<c:url value="/module/pharmagest/site.form"><c:param name="editId" value="${site.sitId}"/></c:url>">Editer</a> | <a
									href="<c:url value="/module/pharmagest/site.form"><c:param name="deleteId" value="${site.sitId}"/></c:url>">Supprimer</a></td>  
   </tr>  
              </c:forEach>  
	        </tbody>
	      </table>
		</div>
	</div>
</div>
</c:if>  
<script type="text/javascript">
            $("#sitLib").attr('required', true); 
			 $("#sitCode").attr('required', true);
</script>

</form:form>