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
		<a href="<c:url value="/module/pharmagest/stockAchat.form"/>"><spring:message
			code="pharmagest.stockAchat" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
</ul>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>