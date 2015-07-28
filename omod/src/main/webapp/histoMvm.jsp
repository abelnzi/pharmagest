<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<div>
<h2 >Historique des op&eacute;rations sur le stock des produits</h2>
</div>

<br>
<br>
<br>

<div>
	<b class="boxHeader"></b>
	<div class="box">
	  <table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <th width="10%">Code article</th>
                  <th width="18%">D&eacute;signation</th>
                  <th width="13%">Num&eacute;ro du lot</th>
                  <th width="14%">P&eacute;remption</th>
                  <th width="9%">Quantit&eacute; </th>
                  <th width="11%">Quantit&eacute; en stock</th>
                  <th width="11%">Date de l'op&eacute;ration</th>
                  <th width="14%">Nature de l'op&eacute;ration</th>
                  <th width="14%">Observation</th>
                </tr>
                <c:forEach var="histo" items="${list}">
                <tr>
                  <td>${histo.histoMouvementStock.produit.prodId}</td>
                  <td>${histo.histoMouvementStock.produit.prodLib}</td>
                  <td>${histo.histoMouvementStock.mvtLot}</td>
                  <td>${histo.histoMouvementStock.produit.prodDateExp}</td>
                  <td>${histo.histoMouvementStock.mvtQte}</td>
                  <td>${histo.histoMouvementStock.mvtQteStock}</td>
                  <td>${histo.histoMouvementStock.mvtDate}</td>
                  <td>${histo.typeOperation.trecptLib}</td>
                  <td>&nbsp;</td>
                </tr>
                </c:forEach>
      </tbody>
</table>      
</div> 
	</div>

