<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<div style="border: 1px dashed black; padding: 10px;">
<ul id="menu">
	<li class="first active"><a href="<c:url value="/module/pharmagest/dispensation.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li>
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">Entr�e fournisseur</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockEntree.form"/>">Autre mouvement d'entr�e de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
</ul>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>