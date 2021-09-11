<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport stock" 
        otherwise="/login.htm" redirect="/index.htm" />
 <%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
 <script type="text/javascript">
	var $ = jQuery.noConflict();
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
             <li class="active">
				<a href="<c:url value="/module/pharmagest/listHistoDistribution.form"/>">Historique des rapports mensuel de consommation</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/listBorderoDistribution.form"/>">Historique des Bordereaux de transfert</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/ruptureESPC.form"/>">Liste des ESPC OU PPS ayant connu une rupture</a>
			</li>
	</ul>
</div>

<div>

<h3 > <span style="font-size:36px">.</span> Historique du rapport mensuel de consommation </h3>
</div>

<br><br>



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
            <td width="7%"> <%= s %> </td>
            <td width="1%">&nbsp;</td>
            <td width="7%">Programme</td>
            <td width="1%">:</td>
            <td width="25%">${consommation.pharmProgramme.programLib}  </td>
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
            <td width="14%"><strong style="color:#F7072C">
                                    <%= statut %>
                                    </strong></td>
            
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
          <td width="17%"><strong>Code</strong></td>
          <td width="17%"><strong>D&eacute;signation</strong></td>
          <td width="12%"><strong>Stock initial</strong></td>
          <td width="9%"><strong>Quantit&eacute; re&ccedil;ue</strong></td>
          <td width="10%"><strong>Quantit&eacute; utilis&eacute;e</strong></td>
          <td width="10%"><strong>Perte</strong></td>
          <td width="9%"><strong>Stock disponible</strong></td>
          <td width="12%"><strong>Nombre de jour de rupture</strong></td>
          <td width="7%"><strong>Quantit&eacute; distribu&eacute;e M-1</strong></td>
          <td width="7%"><strong>Quantit&eacute; distribu&eacute;e M-2</strong></td>
        </tr>
        <c:forEach var="lo" items="${consommation.pharmLigneCommandeSites}">
          <tr>
            <td width="17%"><div align="left">${lo.pharmProduit.prodCode}</div></td>
            <td width="17%"><div align="left">${lo.pharmProduit.prodLib}</div></td>
            <td width="12%">${lo.lgnComQteIni}</td>
            <td width="9%">${lo.lgnComQteRecu}</td>
            <td width="10%"><div align="left">${lo.lgnComQteUtil}</div></td>
            <td width="10%"><div align="left">${lo.lgnComPertes}</div></td>
            <td width="9%"><div align="left">${lo.lgnComStockDispo}</div></td>
            <td width="12%"><div align="left">${lo.lgnComNbreJrsRup}</div></td>
            <td width="7%">${lo.lgnQteDistriM1}</td>
            <td width="7%">${lo.lgnQteDistriM2}</td>
          </tr> 
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

