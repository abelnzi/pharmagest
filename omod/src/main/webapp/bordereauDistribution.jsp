<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport stock" 
        otherwise="/login.htm" redirect="/index.htm" />
  
  <openmrs:htmlInclude file="/moduleResources/pharmagest/scripts/jquery.PrintArea.js" />
  
 <%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script>

var $sig = jQuery.noConflict();

$sig(document).ready(function(){
	$sig("#print_button").click(function(){
		$sig("#print").printArea();
	});
});

</script>
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>
<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	<li class="first "><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Menu principal</a></li>
			
    		<li >
				<a href="<c:url value="/module/pharmagest/saisiesRPPS.form"/>">Saisies des rapports des PPS</a>
			</li>
             <li>
				<a href="<c:url value="/module/pharmagest/importRapportConso.form"/>">Importation des rapports des ESPC/PPS</a>
            </li>
            <li>
				<a href="<c:url value="/module/pharmagest/listPPSModification.form"/>">Modification/Validation des rapports ESPC/PPS
            </li>
            <li>
				<a href="<c:url value="/module/pharmagest/listCommandeSite.form"/>">Traitements</a>
			</li>
             <li>
				<a href="<c:url value="/module/pharmagest/listHistoDistribution.form"/>">Historique des rapports mensuel de consommation</a>
			</li>
            <li class="active">
				<a href="<c:url value="/module/pharmagest/listBorderoDistribution.form"/>">Historique des Bordereaux de transfert</a>
			</li>
	</ul>
</div>

<div>

<h3 > <span style="font-size:36px">.</span> Historique de Bordereau de livraison </h3>
</div>
<br>

<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="7%">ESPC / PPS</td>
            <td width="1%">:</td>
            <td width="18%"> ${consommation.pharmSite.sitLib} </td>
            <td width="1%">&nbsp;</td>
            <td width="11%">Mois de distribution</td>
            <td width="1%">:</td>
            <c:set var="d" value="${consommation.comSitePeriodDate}" scope="request" />
            <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
            <td width="7%"><%= s %></td>
            <td width="1%">&nbsp;</td>
            <td width="7%">Programme</td>
            <td width="1%">:</td>
            <td width="25%">${consommation.pharmProgramme.programLib} </td>
            <td width="1%">&nbsp;</td>
            <td width="5%">Statut :</td>
            <c:set var="flag" value="${consommation.comSiteFlag}" scope="request" />
            <% Integer flag=  (Integer) request.getAttribute("flag");
										String statut="";
										if(flag == 0){
											statut="INITIE";
											} else if(flag == 1){
											statut="VALIDE";
											} else if(flag == 2){
											statut="TRAITE";
											}
																	
									 %>
            <td width="14%"><strong style="color:#F7072C"> <%= statut %> </strong></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<br>
<br>
<div> <b class="boxHeader"></b>
  <div id="print" class="box">
  <div>
  <table width="1164" border="0" cellpadding="7" cellspacing="7">
          <tbody>
            <tr>
              <td width="280"><strong>Bordereau de livraison :</strong></td>
              <td width="455">${consommation.pharmSite.sitLib} -- ${consommation.pharmProgramme.programLib} -- <%= s %></td>
              <td width="69">
                <div align="right">
                  <input id="print_button" value="Imprimer" type="button" />
                </div>
              </td>
              </tr>
          </tbody>
        </table>
  </div>
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <td width="17%"><strong>Code</strong></td>
          <td width="17%"><strong>D&eacute;signation</strong></td>
          <td width="12%"><strong>Unit&eacute;</strong></td>
          <td width="9%"><strong>Quantit&eacute; demand&eacute;e</strong></td>
          <td width="10%"><strong>Quantit&eacute; accord&eacute;e</strong></td>
          <td width="10%"><strong>Num&eacute;ro de lot</strong></td>
          <td width="9%"><strong>Date de p&eacute;remption</strong></td>
          <td width="12%"><strong>Observations</strong></td>
          </tr>
        <c:forEach var="lo" items="${bordereaux}">
          <tr>
            <td width="17%">${lo.ligneOperation.pharmProduitAttribut.pharmProduit.prodCode}</td>
            <td width="17%"><div align="left">${lo.ligneOperation.pharmProduitAttribut.pharmProduit.prodLib}</div></td>
            <td width="12%">${lo.ligneOperation.pharmProduitAttribut.pharmProduit.prodUnite}</td>
            <td width="9%">${lo.ligneOperation.lgnOperQte}</td>
            <td width="10%"><div align="left">${lo.ligneOperation.lgnOperQte}</div></td>
            <td width="10%"><div align="left">${lo.ligneOperation.pharmProduitAttribut.prodLot}</div></td>
            <td width="9%"><div align="left">${lo.ligneOperation.pharmProduitAttribut.prodDatePerem}</div></td>
            <td width="12%"><div align="left"></div></td>
            </tr> 
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
 