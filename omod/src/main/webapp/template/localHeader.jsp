<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>
 
<div class="box" style="margin:0px; width:100%;background-color:#FCD7DB" >
<ul id="menu">
	<openmrs:hasPrivilege privilege="pharmacie reception">
	<li class="first <% if(activeFour==true){ out.println("active");} %>">
		<a href="<c:url value="/module/pharmagest/receptionMenu.form"/>">R&eacute;ception produits</a>
	</li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie dispensation">
	<li class="<% if(activeDisp==true){ out.println("active");} %>"><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>	
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie distribution">
    	<li class="<% if(activeDist==true){ out.println("active");} %>"><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Distribution</a></li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie mouvement">
	<li class="<% if(activeMvt==true){ out.println("active");} %>">
		<a href="<c:url value="/module/pharmagest/mouvementStock.form"/>">Pertes et Ajustements</a>
	</li>
	</openmrs:hasPrivilege>
     <openmrs:hasPrivilege privilege="pharmacie inventaire">
	<li class="<% if(activeInv==true){ out.println("active");} %>">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie rapport">
	<li class="<% if(activeRap==true){ out.println("active");} %>">
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapports</a>
	</li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie parametres">
    <li class="<% if(activeParam==true){ out.println("active");} %>">
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Fichiers de base</a>
	</li>
	 </openmrs:hasPrivilege>
     <openmrs:hasPrivilege privilege="pharmacie interoperabilite">
<!--    <li class="<% //if(activeInter==true){ out.println("active");} %>">
            <a href="<c:url value="/module/pharmagest/interoperabiliteMenu.form"/>">Interoperabilit&eacute;</a>
        </li>-->
        
        <li	<c:if test='<%= request.getRequestURI().contains("/interoperabiliteMenu") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/pharmagest/interoperabiliteMenu.form">Interoperabilit&eacute;</a>
	</li>
        
	 </openmrs:hasPrivilege>
     
     <openmrs:hasPrivilege privilege="pharmacie distribution">        
        <li	<c:if test='<%= request.getRequestURI().contains("/dispensDistrict") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/pharmagest/dispensDistrict.form">Dispensation approche en district</a>
	</li>
        
	 </openmrs:hasPrivilege>
	<!-- Add further links here -->
</ul></div>

