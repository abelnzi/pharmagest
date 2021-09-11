<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport mensuel" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>
<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	<openmrs:hasPrivilege privilege="pharmacie rapport">
	<li class="first">
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Menu principal</a>
	</li>
</openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="pharmacie rapport mensuel">
    		<li class="active">
				<a href="<c:url value="/module/pharmagest/rapportConso.form"/>">Rapport de consommation</a>
			</li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie rapport commande">
        	<li >
				<a href="<c:url value="/module/pharmagest/simulationCommande.form"/>">Simulation du Rapport commande</a>
			</li>
        	<li>
				<a href="<c:url value="/module/pharmagest/rapportCommande.form"/>">Rapport commande</a>
			</li>
    </openmrs:hasPrivilege>
   <openmrs:hasPrivilege privilege="pharmacie rapport stock">
	<li>
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>
   <li>
		<a href="<c:url value="/module/pharmagest/rapportStockTotal.form"/>">Stock des produits par lot</a>
	</li>
     <li>
		<a href="<c:url value="/module/pharmagest/rapportStockProduit.form"/>">Stock des produits</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportConsoProdJour.form"/>">Consommation par Produit par p&eacute;riode</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportPPI.form"/>">Rapport des mouvements de produits </a>
	</li>
    </openmrs:hasPrivilege>
   
	</ul>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/rapportConso.form"
	modelAttribute="formulaireProgramme"
	commandName="formulaireProgramme">
<div>

<h3 > <span style="font-size:36px">.</span> G&eacute;n&eacute;rer le rapport mensuel de consommation ${dateMois}</h3>
</div>
<br>
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>

            <td width="9%">Programme <span style="color:#F11A0C">*</span></td>
            <td width="1%">:</td>
            <td width="17%"><form:select path="pharmProgramme" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select>
              <form:errors path="pharmProgramme" cssClass="error" /></td>
            <td width="1%">&nbsp;</td>
            <td width="8%">Mois de <span style="color:#F11A0C">*</span></td>
            <td width="1%">:</td>
            <td width="19%"><openmrs_tag:dateField formFieldName="dateConso" startValue="${obsDate}" /></td>
            <td width="44%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="G&eacute;n&eacute;rer"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br>
<br>

<c:if test="${var =='0'}">
<div id="openmrs_msg" align="center">Impossible de g&eacute;n&eacute;rer le rapport de consommation du mois de : <strong>${mess}</strong><br> V&eacute;rifier que l'inventaire a &eacute;t&eacute; renseign&eacute;</div>
</c:if>
<c:if test="${var =='1'}">
<div id="openmrs_msg" align="center">${mess}</div>
</c:if>
<c:if test="${var =='2'}">
<!--<table width="100%" border="0">
  <tbody>
    <tr>
      <td width="20%">&nbsp;</td>
      <td width="15%">&nbsp;</td>
      <td width="18%">&nbsp;</td>
      <td width="40%">&nbsp;</td>
      <td width="4%"><input name="print_pdf" type="submit"
												id="print_pdf" title="PDF" value="PDF"></td>
      <td width="3%"><input name="print_excel" type="submit"
												id="print_excel" title="Excel" value="Excel"></td>
    </tr>
  </tbody>
</table>-->

<div> <b class="boxHeader"></b>
<!-- <a href="<c:url value="/module/pharmagest/rapportConso.form">
    <c:param name="pdfRConso" value="pdfRConso"/> </c:url>">PDF</a>-->
  <div class="box">
  
 <c:if test="${tab =='1'}">
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <td width="11%">Code</td>
          <td width="14%">D&eacute;signation</td>
          <td width="9%">Stock initial</td>
          <td width="8%">Quantit&eacute; re&ccedil;ue</td>
          <td width="8%">Quantit&eacute; utilis&eacute;e</td>
          <td width="8%">Perte</td>
          <td width="9%">Stock disponible</td>
          <td width="11%">Nombre de jour de rupture</td>
          <td width="11%">Quantit&eacute; distribu&eacute;e M-1</td>
          <td width="11%">Quantit&eacute; distribu&eacute;e M-2</td>
        </tr>
        <c:forEach var="lo" items="${listRapConso}">
          <tr>
            <td width="11%"><div align="left">${lo.produit.prodCode}</div></td>
            <td width="14%"><div align="left">${lo.produit.prodLib}</div></td>
            <td width="9%">${lo.stockIni}</td>
            <td width="8%">${lo.qteRecu}</td>
            <td width="8%"><div align="left">${lo.qteUtil}</div></td>
            <td width="8%"><div align="left">${lo.pertes}</div></td>
            <td width="9%"><div align="left">${lo.stockDispo}</div></td>
            <td width="11%"><div align="left">${lo.nbrJrsRup}</div></td>
            <td width="11%">${lo.qteDistM1}</td>
            <td width="11%">${lo.qteDistM2}</td>
          </tr> 
        </c:forEach>
      </tbody>
    </table>
 </c:if>
 
 <!-------------------------------------------------------------------------------->
 
  <c:if test="${tab =='2'}">
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <td width="17%">Code</td>
          <td width="17%">D&eacute;signation</td>
          <td width="12%">Stock initial</td>
          <td width="9%">Quantit&eacute; re&ccedil;ue</td>
          <td width="10%">Quantit&eacute; utilis&eacute;e</td>
          <td width="10%">Perte</td>
          <td width="9%">Stock disponible</td>
          <td width="12%">Nombre de jour de rupture</td>
          <td width="7%">Quantit&eacute; distribu&eacute;e M-1</td>
          <td width="7%">Quantit&eacute; distribu&eacute;e M-2</td>
        </tr>
         <% int x = 1;  %>
        <c:forEach var="lo" items="${listRapConso}">
        <c:set var="cal" value="${lo.stockIni+lo.qteRecu-lo.qteUtil-lo.pertes}" scope="request" />
        <c:set var="dispo" value="${lo.stockDispo}" scope="request" />
        <% Long cal=  (Long) request.getAttribute("cal");
			Integer dispo=  (Integer) request.getAttribute("dispo"); 

			String attri ="";
			String button ="";
			if(!(cal==dispo.longValue())){
				 attri = "color:#F3F3F3;background:#F70606";
				 button="disabled";
				 pageContext.setAttribute("button", button);
				}
										
		 %>
          <tr style="<%= attri %>">
            <td width="17%"><div align="left">${lo.produit.prodCode}</div></td>
            <td width="17%"><div align="left">${lo.produit.prodLib}</div></td>
            <td width="12%">${lo.stockIni}</td>
            <td width="9%">${lo.qteRecu}</td>
            <td width="10%"><div align="left">${lo.qteUtil}</div></td>
            <td width="10%"><div align="left">${lo.pertes}</div></td>
            <td width="9%"><div align="left">${lo.stockDispo}</div></td>
             <c:set var="attribut" value="${attribut}" scope="request" />
             <%	 String attribut=  (String) request.getAttribute("attribut"); %>
            <td width="12%">
            <input type="number" name="nbrJrsRup<%= x %>" id="nbrJrsRup<%= x %>"  
                                    				value="${lo.nbrJrsRup}">
            </td>
            <td width="7%"><input type="number" name="qteDistM1<%= x %>" id="qteDistM1<%= x %>"  <%= attribut %>
                                    				value="${lo.qteDistM1}"></td>
            <td width="7%"><input type="number" name="qteDistM2<%= x %>" id="qteDistM2<%= x %>"  <%= attribut %>
                                    				value="${lo.qteDistM2}"></td>
          <input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${lo.produit.prodId}">
          </tr> 
           <% x = x+1;  %> 
        </c:forEach>
      </tbody>
    </table>
    <table width="100%" border="0" align="" cellpadding="7" cellspacing="7">
  <tbody>
    <tr>
      <td width="97">&nbsp;</td>
      <td width="97">&nbsp;</td>
      <td width="97">&nbsp;</td>
      <td width="97">&nbsp;</td>
      <td width="97">&nbsp;</td>
      <td width="97">&nbsp;</td>
      <td width="77">&nbsp;</td>
      <td width="140">&nbsp;</td>
      
      <td width="289" align="right"><input name="btn_valder" type="submit" <%= pageContext.getAttribute("button") %>
												id="btn_valder" title="valider" value="Valider"></td>
    </tr>
  </tbody>
</table>
 </c:if>
 
 
  </div>
</div>
</c:if>
<script type="text/javascript">
            //$("#dateConso").attr('required', true); 
</script>
</form:form>