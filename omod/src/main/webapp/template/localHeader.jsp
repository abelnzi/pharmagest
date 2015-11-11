<spring:htmlEscape defaultHtmlEscape="true" />
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<ul id="menu">
	

	<li class="first"><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li>
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">Entr&eacute;e fournisseur</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockEntree.form"/>">Autre mouvement d'entr&eacute;e de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockSortie.form"/>">Autre mouvement de sortie de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapportage sur le Stock</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	
	<!-- Add further links here -->
</ul>

