<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<script type="text/javascript">
    var $j = jQuery.noConflict();
</script>
<script type="text/javascript">
    $j(document).ready(function() {
        $j('#myTable').dataTable();
    } );
</script>

<div style="border: 1px dashed black; padding: 5px;">
<ul id="menu">
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapportage sur le Stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/histoMvm.form"/>">Historique des mouvements de stock</a>
	</li>
	<li class="active">
		<a href="<c:url value="/module/pharmagest/rapportStockTotal.form"/>">Stock des produits disponibles</a>
	</li>
     <li>
		<a href="<c:url value="/module/pharmagest/rapportStockProduit.form"/>">Stock par produit</a>
	</li>
	</ul>
</div>

<div>
<h3 >Stock des produits</h3>
</div>
<br>


<div>
	<b class="boxHeader"></b>
	<div class="box">
	  <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           <thead>
                <tr>
                  <th width="10%">Programme</th>
                  <th width="10%">Code article</th>
                  <th width="18%">D&eacute;signation</th>
                  <th width="13%">Date du dernier stock</th>
                  <th width="14%">P&eacute;remption</th>
                  <th width="11%">Quantit&eacute; en stock</th>
                </tr>
                <thead>
                <tbody>
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

