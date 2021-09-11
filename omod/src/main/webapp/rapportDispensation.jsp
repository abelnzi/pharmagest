<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport stock" 
        otherwise="/login.htm" redirect="/index.htm" />
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
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	<li class="first">
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">M&eacute;nu principal</a>
	</li>
    <li>
				<a href="<c:url value="/module/pharmagest/rapportConso.form"/>">Rapport de consommation</a>
			</li>
          
        	<li>
				<a href="<c:url value="/module/pharmagest/rapportCommande.form"/>">Rapport commande</a>
			</li>
			
	<li class="active">
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

<div>
<h3><span style="font-size:36px">.</span> Rapport mensuel de dispensation des ARV/IO</h3>
</div>


<div>
	<b class="boxHeader"></b>
	<div class="box">
    <table width="100%" border="1" cellpadding="7" cellspacing="0">
  <tbody>
    <tr>
      <td rowspan="2"><div align="center"><strong>R&eacute;gimes</strong></div>        <div align="center"></div></td>
      <td colspan="2"><div align="center"><strong>Nombre de patients re&ccedil;us</strong></div></td>
      <td colspan="2"><div align="center"><strong>Inclusion (Na&iuml;fs)</strong></div></td>
      </tr>
    <tr>
      <td><div align="center"><strong>Adulte  (&gt;=15 ans)</strong></div></td>
      <td><div align="center"><strong>Enfant (&lt; 15 ans)</strong></div></td>
      <td><div align="center"><strong>Adulte  (&gt;=15 ans)</strong></div></td>
      <td><div align="center"><strong>Enfant (&lt; 15 ans)</strong></div></td>
    </tr>
    <tr>
      <td><div align="center"><strong>1&egrave;re  ligne</strong></div></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>AZT/3TC/NVP</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>AZT/3TC/EFV</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>AZT/3TC/LPV/r_1</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>AZT/3TC/TDF</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/3TC/EFV</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/3TC/LPV/r_1</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/FTC/EFV</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/FTC/LPV/r_1</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>AZT/3TC/ABC</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>ABC/3TC/NVP</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>ABC/3TC/LPV/r_1</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="5">&nbsp;</td>
      </tr>
    <tr>
      <td><div align="center"><strong>2&egrave;me ligne</strong></div></td>
      <td colspan="4">&nbsp;</td>
      </tr>
    <tr>
      <td>AZT/3TC/LPV/r_2</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>AZT/3TC/ATV/r</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/3TC/LPV/r_2</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/3TC/ATV/r</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/FTC/LPV/r_2</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>TDF/FTC/ATV/r</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>ABC/3TC/LPV/r_2</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><div align="center"><strong>IO</strong></div></td>
      <td colspan="4">&nbsp;</td>
      </tr>
    <tr>
      <td>Cotrimoxazole</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>

    
	</div> 
</div>

