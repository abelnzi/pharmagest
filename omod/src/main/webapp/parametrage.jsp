<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>
	<spring:message code="pharmagest.title" />
</h2>
<div style="border: 1px dashed black; padding: 10px;">
<ul id="menu">
	<li class="first active">
		<a href="<c:url value="/module/pharmagest/manage.form"/>">Menu principal</a>
	</li>
	<li>
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
	</ul>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>