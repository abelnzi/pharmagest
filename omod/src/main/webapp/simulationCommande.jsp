<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport commande" 
        otherwise="/login.htm" redirect="/index.htm" />
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>
<div class="box" style="margin:0px; width:100%;background-color:#FCD7DB" >
<ul id="menu">
<openmrs:hasPrivilege privilege="pharmacie rapport">
	<li class="first">
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Menu principal</a>
	</li>
</openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="pharmacie rapport mensuel">
    		<li>
				<a href="<c:url value="/module/pharmagest/rapportConso.form"/>">Rapport de consommation</a>
			</li>
    </openmrs:hasPrivilege>
    <openmrs:hasPrivilege privilege="pharmacie rapport commande">
   			<li  class="active">
				<a href="<c:url value="/module/pharmagest/simulationCommande.form"/>">Simulation du Rapport commande</a>
			</li>
        	<li  >
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
		<a href="<c:url value="/module/pharmagest/rapportPPI.form"/>">Rapport des mouvements de produits</a>
	</li>
    </openmrs:hasPrivilege>
	</ul>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/simulationCommande.form"
	modelAttribute="formulaireRapportCommande"
	commandName="formulaireRapportCommande">
<div>

<h3 > <span style="font-size:36px">.</span> G&eacute;n&eacute;rer le rapport commande  ${dateMois}</h3>
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
            <td width="18%"><form:select path="pharmProgramme" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select>
              <form:errors path="pharmProgramme" cssClass="error" /></td>
            <td width="1%">&nbsp;</td>
            <td width="6%">Mois <span style="color:#F11A0C">*</span></td>
            <td width="1%">:</td>
            <td width="20%"><openmrs_tag:dateField formFieldName="dateCommande" startValue="${obsDate}" /></td>
            <td width="44%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="Generer" value="G&eacute;n&eacute;rer"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br>
<br>

<c:if test="${var =='0'}">
		<div id="openmrs_msg" align="center">Impossible de g&eacute;n&eacute;rer le rapport commande du mois de : <strong>${mess}</strong><br> V&eacute;rifier que l'inventaire ou les rapport mensuels des sites ont &eacute;t&eacute; renseign&eacute;s</div>
</c:if>

<c:if test="${var =='1'}">
<div> <b class="boxHeader"></b>
  <div class="box">
  <c:if test="${tab =='1'}">
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <td width="11%">Code</td>
          <td width="11%">D&eacute;signation</td>
          <td width="7%">Stock initial</td>
          <td width="8%">Quantit&eacute;s re&ccedil;ues</td>
          <td width="9%">Quantit&eacute;s utilis&eacute;es</td>
          <td width="6%">Pertes</td>
          <td width="6%">Ajustements</td>
          <td width="10%">Stock disponible</td>
          <td width="9%">Nombre de jour de rupture</td>
          <td width="8%">Nombre de site ayant connu une rupture</td>
          <td width="10%">Consommation Mensuelle Moyenne (CMM)</td>
          <td width="8%">Mois de Stock Disponible (MSD)</td>
          <td width="8%">Quantit&eacute;s &aacute; commander</td>
        </tr>
        <c:forEach var="lo" items="${listRapCommande}">
          <tr>
            <td width="11%"><div align="left">${lo.produit.prodCode}</div></td>
           
            <td width="11%"><div align="left">${lo.produit.prodLib}</div></td>
            <td width="7%"><div align="left">${lo.stockIni}</div></td>
            <td width="8%"><div align="left">${lo.qteRecu}</div></td>
            <td width="9%"><div align="left">${lo.qteUtil}</div></td>
            <td width="6%"><div align="left">${lo.pertes}</div></td>
            <td width="6%"><div align="left">${lo.ajustements}</div></td>
            <td width="10%"><div align="left">${lo.stockDispo}</div></td>
            <td width="9%"><div align="left">${lo.nbrJrsRup}</div></td>
            <td width="8%"><div align="left">${lo.nbrSiteRup}</div></td>
            <td width="10%"><div align="left">${lo.cmm}</div></td>
            <td width="8%"><div align="left">${lo.moisStock}</div></td>
            <td width="8%">${lo.qteCom}</td>
          </tr> 
        </c:forEach>
      </tbody>
    </table>
   </c:if>
   <!---------------------------------------------------------------------------------------------------------------->
 <c:if test="${tab =='2'}">
    <table width="100%" border="1"  cellpadding="7" cellspacing="0">
      <tbody>
        <tr>
          <td width="11%">Code</td>
          <td width="11%">D&eacute;signation</td>
          <td width="7%">Stock initial</td>
          <td width="8%">Quantit&eacute;s re&ccedil;ues</td>
          <td width="9%">Quantit&eacute;s utilis&eacute;es</td>
          <td width="6%">Pertes</td>
          <td width="6%">Ajustements</td>
          <td width="10%">Stock disponible</td>
          <td width="9%">Nombre de jour de rupture</td>
          <td width="8%">Nombre de site ayant connu une rupture</td>
          <td width="10%">Consommation Mensuelle Moyenne (CMM)</td>
          <td width="8%">Mois de Stock Disponible (MSD)</td>
          <td width="8%">Quantit&eacute;s &aacute; commander</td>
        </tr>
        <% int x = 1;  %>	
        <c:forEach var="lo" items="${listRapCommande}">
        <c:set var="cal" value="${lo.stockIni+lo.qteRecu+lo.ajustements-lo.qteUtil-lo.pertes}" scope="request" />
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
            <td width="11%"><div align="left">${lo.produit.prodCode}</div></td>
           
            <td width="11%"><div align="left">${lo.produit.prodLib}</div></td>
            <td width="7%">${lo.stockIni}</td>
            <td width="8%">${lo.qteRecu}</td>
            <td width="9%"><div align="left">${lo.qteUtil}</div></td>
            <td width="6%"><div align="left">${lo.pertes}</div></td>
            <td width="6%">${lo.ajustements}</td>
            <td width="10%"><div align="left">${lo.stockDispo}</div></td>
            <td width="9%"><div align="left">${lo.nbrJrsRup}</div></td>
            <td width="8%"><input type="number" name="siteRup<%= x %>" id="siteRup<%= x %>" 
                                    				value="${lo.nbrSiteRup}"></td>
            <td width="10%"><input type="number" name="cmm<%= x %>" id="cmm<%= x %>" 
                                    				value="${lo.cmm}"  ></td>
            
            <td width="8%"><input type="text" name="moisStock<%= x %>" id="moisStock<%= x %>" 
                                    				value="${lo.moisStock}"></td>
            <td width="8%"><input type="number" name="qteACom<%= x %>" id="qteACom<%= x %>" 
                                    				value="${lo.qteCom}"></td>
           <input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${lo.produit.prodId}">
           
          </tr> 
         	
			<script type="text/javascript">
			$("#moisStock<%= x %>").attr('readonly', 'readonly');
			//$("#qteACom<%= x %>").attr('disabled', 'disabled');
			function clickAction<%= x %>() {
				var cmmInt=parseInt(document.getElementById("cmm<%= x %>").value );
				//alert('cmmInt : '+document.getElementById("cmm<%= x %>").value );
			if(cmmInt!=0) {
				document.getElementById("moisStock<%= x %>").value = Math.round( (${lo.stockDispo}/cmmInt)*10)/10 ;
				if((cmmInt*4)-(${lo.stockDispo})>=0)	{
				document.getElementById("qteACom<%= x %>").value = (cmmInt*4)-(${lo.stockDispo}) ;
				} else {
					document.getElementById("qteACom<%= x %>").value =0;
					}
				//alert('moisStock'+document.getElementById("qteACom<%= x %>").value);
				}
			
			}
			document.getElementById("cmm<%= x %>").onchange = function() {clickAction<%= x %>()};
			</script>
          
        <% x = x+1;  %> 
        </c:forEach>
      </tbody>
    </table>
 </c:if>
    
    
  </div>
</div>
</c:if>
<script type="text/javascript">
            //$("#pharmProgramme").attr('required', true); 
			// $("#dateCommande").attr('required', true);
			
</script>
</form:form>