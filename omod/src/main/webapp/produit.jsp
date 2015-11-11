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
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<ul id="menu">
	<li class="first"><a
		href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	<li><a
		href="<c:url value="/module/pharmagest/fournisseur.form"/>">Fournisseur</a>
	</li>
	<li><a href="<c:url value="/module/pharmagest/regime.form"/>">R&eacute;gime</a>
	</li>
	<li class="active"><a
		href="<c:url value="/module/pharmagest/produit.form"/>">Produit</a></li>
	<li><a href="<c:url value="/module/pharmagest/programme.form"/>">Programme</a>
	</li>
	<li><a
		href="<c:url value="/module/pharmagest/classePharma.form"/>">Classe
			de produit</a></li>


	<!-- Add further links here -->
</ul>

<div>
	<h3>Produit</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/produit.form"
	modelAttribute="formulaireProduit">
	<br>
	<input type="hidden" id="identifiant" name="identifiant"
		value="<c:out value="${identifiant}"/>">

	<div>
		<table width="100%" border="0">
			<tbody>
				<tr>
					<td><c:if test="${var =='2'}">
							<input type="submit" name="btn_modifier" id="btn_modifier"
								value="Modifier">
						</c:if> <c:if test="${var !='2'}">
							<input type="submit" name="btn_enregistrer" id="btn_enregistrer"
								value="Enregistrer">
						</c:if> <input type="reset" name="reset" id="reset" value="Annuler"></td>
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
							<td width="7%">Lib&eacute;ll&eacute;<span style="color:#F11A0C">*</span></td>
							<td width="19%"><form:input path="prodLib" /> <form:errors
									path="prodLib" cssClass="error" /></td>
							<td width="1%">&nbsp;</td>
							<td width="8%">Code</td>
							<td width="16%"><form:input path="prodCode" /> <form:errors
									path="prodCode" cssClass="error" /></td>
							<td width="1%">&nbsp;</td>
							<td width="15%">Classe</td>
							<td width="33%"><form:select path="pharmClassePharma">
									<form:option value="0" label="---Choix---" />
									<form:options items="${classePharmas}" itemValue="classPharmId"
										itemLabel="classPharmLib" />
								</form:select>
								<form:errors path="pharmClassePharma" cssClass="error" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<br>
	<c:if test="${!empty produits}">
		<div>

			<b class="boxHeader"></b>
			<div class="box">
				<div class="searchWidgetContainer" id="findPatients" align="center">
					<table id="myTable" width="100%" border="1" cellspacing="0" cellpadding="7">
						<thead>

							<tr>
								<th width="13%">Code</th>
								<th width="29%">Lib&eacute;ll&eacute;</th>
								<th width="30%">Classe</th>
								<th width="28%">Action</th>
							</tr>
                            </thead>
                            <tbody>
							<c:forEach items="${produits}" var="produit">
								<tr>
									<td><c:out value="${produit.prodCode}" /></td>
									<td><c:out value="${produit.prodLib}" /></td>
									<td><c:out
											value="${produit.pharmClassePharma.classPharmNom}" /></td>
									<td align="center"><a
										href="<c:url value="/module/pharmagest/produit.form"><c:param name="editId" value="${produit.prodId}"/></c:url>">Editer</a>
										| <a
										href="<c:url value="/module/pharmagest/produit.form"><c:param name="deleteId" value="${produit.prodId}"/></c:url>">Supprimer</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
	<script type="text/javascript">
		$("#prodLib").attr('required', true);
		$("#prodCode").attr('required', true);
	</script>
</form:form>