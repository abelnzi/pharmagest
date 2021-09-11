<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire" 
        otherwise="/login.htm" redirect="/index.htm" />
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/scripts/jquery.PrintArea.js" />

<spring:htmlEscape defaultHtmlEscape="true" />
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
    <li  class="active">
		<a href="<c:url value="/module/pharmagest/rapportPPI.form"/>">Rapport des mouvements de produits </a>
	</li>
    </openmrs:hasPrivilege>
	</ul>
</div>


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


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/rapportPPI.form"
	modelAttribute="formulaireProduit"
	commandName="formulaireProduit"  >
<div>
<h3 ><span style="font-size:36px">.</span> Rapport sur les mouvements de stock<br>

<c:if test="${mess =='vide'}">
		</c:if></h3></div><c:if test="${mess =='vide'}"><div id="openmrs_msg">Aucun produit trouv&eacute;</div>
</c:if>
<br>


<c:if test="${var =='0'}">
<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="10%">Type de rapport <span style="color:#F11A0C">* :</span> </td>
      <td width="11%"><form:select path="typeRapport" cssStyle="width:150px">
                      <option value="0" >-- Choix --</option>
                      <option value="ajustement" >Ajustement</option>
                      <option value="perime" >P&eacute;rim&eacute;s</option>
                      <option value="perte" >Pertes</option>
                       
                </form:select> 
                <form:errors path="typeRapport" cssClass="error"/>
                </td>
      <td width="1%">&nbsp;</td>
      <td width="9%" height="60">Programme <span style="color:#F11A0C">* :</span></td>
      <td width="17%"><form:select path="programme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireProduit.getProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="programme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="8%">Date debut <span style="color:#F11A0C">* :</span></td>
      <td width="8%"><openmrs_tag:dateField formFieldName="dateDebut" startValue="${obsDate}" />
      <form:errors path="dateDebut" cssClass="error"/>
      </td>
      <td width="7%">Date Fin <span style="color:#F11A0C">* :</span></td>
      <td width="9%"><openmrs_tag:dateField formFieldName="dateFin" startValue="${obsDate}" />
      <form:errors path="dateFin" cssClass="error"/>
      </td>
      <td width="1%">&nbsp;</td>
      <td width="18%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
      </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
</c:if>
<br>

<c:if test="${var =='1'}">



<div>
	<b class="boxHeader"></b>
	<div id="print" class="box">
        <div>
                  <input id="print_button" value="Imprimer" type="button" />
        </div>
                
                 <div style="border:#000 solid 1px; width:100%; margin:auto; ">
<center>
  <table border="0" width="100%">
    <tr>
      <td colspan="3" valign="top"><br /></td>
      <td width="9%"><div title="Page 1">
        <div>
          
          </div>
      </div></td>
      <td width="16%" valign="top" align="right">&nbsp;</td>
      </tr>
    <tr>
      <td colspan="5"><div>
        <h3 align="center" style="font-size:20px">FICHE D'INVENTAIRE DES PRODUITS PHARMACEUTIQUES : ${formulaireProduit.typeRapport}</h3>
      </div></td>
      </tr>
    <tr>
      <td>Nom de l'&eacute;tablissement:</td>
      <td>${site.name}</td>
      <td align="right">Programme:</td>
      <td colspan="2" align="left">${formulaireProduit.programme.programLib}</td>
    </tr>
    <tr>
      <td>Code:</td>
      <td>${site.postalCode}</td>
      <td align="right">&nbsp;</td>
      <td colspan="2" align="left">&nbsp;</td>
      </tr>
    <tr>
      <td width="16%"></td>
      <td width="45%">&nbsp;</td>
      <td width="14%">&nbsp;</td>
      <td align="right">&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="5">
      <table id="myTable" border="1" width="100%" >
        <thead>
        <tr>
          <th width="5%" align="center"  ><strong>Date</strong></th>
          <th width="8%" align="center"  ><strong>Code Produit</strong></th>
          <th width="11%" align="center"  ><strong>D&eacute;signation</strong></th>
          <th width="7%" align="center"  ><strong>Unit&eacute;</strong></th>
          <th width="9%" align="center"  ><strong>PU</strong></th>
          <th width="9%" align="center"  ><strong>P&eacute;remption</strong></th>
          <th width="9%" align="center"  ><strong>Lot</strong></th>
          <th width="8%" align="center"  ><strong>Qt&eacute;</strong></th>
          <th width="10%" align="center"  ><strong>Libell&eacute; de l'operation</strong></th>
          <th width="8%" align="center"  ><strong> Origine</strong></th>
          <!--<td align="center"  >&nbsp;</td>-->
          <th width="14%" align="center"  ><strong>Destination</strong></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="lo" items="${listProduits}">
          <tr>
            <td   >${lo.pharmOperation.operDateRecept}</td>
            <td   >${lo.pharmProduitAttribut.pharmProduit.prodCode}</td>
            <td    >${lo.pharmProduitAttribut.pharmProduit.prodLib}</td>
            <td    >${lo.pharmProduitAttribut.pharmProduit.prodUnite}</td>
            <td    >${lo.lgnOperPrixAchat}</td>
            <td    >${lo.pharmProduitAttribut.prodDatePerem}</td>
            <td    >${lo.pharmProduitAttribut.prodLot}</td>
            <td    >${lo.lgnOperQte}</td>
            <td    >${lo.pharmOperation.pharmTypeOperation.toperLib}</td>
            <c:set var="id" value="${lo.pharmOperation.pharmTypeOperation.toperId}" scope="request" />
             <c:set var="ref" value="${lo.pharmOperation.operRef}" scope="request" />
            
            <% Integer id=  (Integer) request.getAttribute("id"); 
			   String ref=  (String) request.getAttribute("ref"); 
			   
			   String origine="";
			   String destination="";
				if(id==3 || id==4 || id==13 ) { origine=ref;}
				if(id==5 || id==16) { destination=ref;}				
				%>
            <td> <%= origine %></td>
            <!--<td    >&nbsp;</td>-->
            <td><%= destination %></td>
            </tr>
        </c:forEach>
        </tbody>
</table></td>
      </tr>
  </table>
</center>
</div>

	</div> 
	</div>
</c:if>

</form:form>
