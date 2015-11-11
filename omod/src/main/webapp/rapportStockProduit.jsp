<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<div style="border: 1px dashed black; padding: 5px;">
<ul id="menu">
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapportage sur le Stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/rapportStockTotal.form"/>">Stock des produits disponibles</a>
	</li>
     <li class="active">
		<a href="<c:url value="/module/pharmagest/rapportStockProduit.form"/>">Stock par produit</a>
	</li>
	</ul>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/rapportStockProduit.form"
	modelAttribute="formulaireProduit"
	commandName="formulaireProduit">
<div>
<h3 >Stock par produit</h3>
</div>
<br>
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <!--<td width="13%">Code article</td>
            <td width="36%"><form:select path="pharmProduit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="fullName" />
													</form:select>
              <form:errors path="pharmProduit" cssClass="error" /></td>
            <td width="1%">&nbsp;</td>-->
            <td width="16%">D&eacute;signation</td>
            <td width="34%"><form:select path="pharmProduit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
													</form:select>
              <form:errors path="pharmProduit" cssClass="error" /></td>
            <td width="34%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br>
<br>
<div> <b class="boxHeader"></b>
  <div class="box">
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <th width="10%">Programme</th>
          <th width="10%">Code article</th>
          <th width="18%">D&eacute;signation</th>
          <th width="13%">Date du dernier stock</th>
          <th width="14%">P&eacute;remption</th>
          <th width="11%">Quantit&eacute; en stock</th>
        </tr>
        <c:forEach var="stock" items="${listStocks}">
          <tr>
            <td>${stock.pharmProgramme.programLib}</td>
            <td>${stock.pharmProduitAttribut.pharmProduit.prodCode}</td>
            <td>${stock.pharmProduitAttribut.pharmProduit.prodLib}</td>
            <td>${stock.stockDateStock}</td>
            <td>${stock.pharmProduitAttribut.prodDatePerem}</td>
            <td>${stock.stockQte}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
</form:form>