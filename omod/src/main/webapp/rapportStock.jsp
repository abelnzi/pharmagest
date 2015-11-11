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
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>

	<li>
		<a href="<c:url value="/module/pharmagest/rapportStockTotal.form"/>">Stock des produits disponibles</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportStockProduit.form"/>">Stock par produit</a>
	</li>
	
	</ul>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>