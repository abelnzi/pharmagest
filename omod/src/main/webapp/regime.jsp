<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>
<script type="text/javascript">
    $j(document).ready(function() {
        $j('#myTable').dataTable();
    } );
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
    <li class="active">
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
	
		<li >
                <a href="<c:url value="/module/pharmagest/site.form"/>">Site</a>
    </li>
	<!-- Add further links here -->
</ul></div>

<div>
<h3 ><span style="font-size:36px">.</span> Regime</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/regime.form"
	modelAttribute="formulaireRegime">
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
		        <td width="13%">Lib&eacute;ll&eacute;<span style="color:#F11A0C">*</span></td>
		        <td width="36%"><form:input path="regimLib" /> <form:errors path="regimLib" cssClass="error" /></td>
		        <td width="1%">&nbsp;</td>
		        <td width="16%">Programme</td>
		        <td width="34%"><form:select path="pharmProgramme">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select><form:errors path="pharmProgramme"
														cssClass="error" /></td>
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>
</div>

<br>
<c:if test="${!empty regimes}"> 
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table id="myTable" width="100%" border="1" cellspacing="0" cellpadding="7">
		   <thead>
		       <tr>  
   <th width="13%">Identifiant</th>  
   <th width="29%">Lib&eacute;ll&eacute;</th>
   <th width="30%">Programme</th>
   <th width="28%">Action</th>  
  </tr>  </thead>
   <tbody>
              <c:forEach items="${regimes}" var="regime">  
		      <tr>  
    <td><c:out value="${regime.regimId}"/></td>  
    <td><c:out value="${regime.regimLib}"/></td>
    <td><c:out value="${regime.pharmProgramme.programLib}"/></td>
    <td align="center"><a
									href="<c:url value="/module/pharmagest/regime.form"><c:param name="editId" value="${regime.regimId}"/></c:url>">Editer</a> | <a
									href="<c:url value="/module/pharmagest/regime.form"><c:param name="deleteId" value="${regime.regimId}"/></c:url>">Supprimer</a></td>  
   </tr>  
              </c:forEach>  
	        </tbody>
	      </table>
		</div>
	</div>
</div>
</c:if>  
<script type="text/javascript">
            $("#regimLib").attr('required', true); 
</script>

</form:form>