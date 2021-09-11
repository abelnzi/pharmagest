<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie distribution traitement" 
        otherwise="/login.htm" redirect="/index.htm" />

<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

 <%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

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
            
            <li class="active">
				<a href="<c:url value="/module/pharmagest/listCommandeSite.form"/>">Traitements</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/listHistoDistribution.form"/>">Historique des rapports mensuel de consommation</a>
			</li>
            
			<li>
				<a href="<c:url value="/module/pharmagest/listBorderoDistribution.form"/>">Historique des Bordereaux de transfert</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/ruptureESPC.form"/>">Liste des ESPC OU PPS ayant connu une rupture</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/promptitudeESPC.form"/>">Promptitude des Rapports de consommation</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/etatStockESPC.form"/>">Etat de Stock des ESPC/PPS</a>
			</li>
	
	<!-- Add further links here -->
</ul>
</div>
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/traitementsPPS.form"
	modelAttribute="formulaireTraitementsPPS"
	commandName="formulaireTraitementsPPS"  >
<div>
<h3 ><span style="font-size:36px">.</span>Traitement de la commande N&deg; : ${commandeSite.comSiteCode}</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<br>
<br>
<div>

<c:if test="${tab =='1'}">
<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input name="btn_valder" type="submit"
												id="btn_valder" title="valider" value="Valider" onClick="return confirm('Voulez vous valider la saisie?') ? true : false;">
      </td>
      </tr>
  </tbody>
</table>

</div>
</c:if>
	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="5%" height="60">Libell&eacute; ESPC/PPS</td>
      <td width="1%">:</td>
      <td width="17%">                  
        ${commandeSite.pharmSite.sitLib}        
      </td>
      <td width="1%">&nbsp;</td>
      <td width="10%">Programme </td>
      <td width="1%">:</td>
      <td width="18%"> ${commandeSite.pharmProgramme.programLib} </td>
      <td width="1%">&nbsp;</td>
      <td width="14%">Date de traitement<span style="color:#F11A0C"> *</span></td>
      <td width="1%">:</td>
      <td width="31%">
      	      
        
        <c:choose>
          <c:when test="${formulaireTraitementsPPS.dateParam !=null}">
          <c:set var="d" value="${formulaireTraitementsPPS.dateParam}" scope="request" />
          <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
           <%= s %>
          </c:when>
          
          <c:otherwise>          
            <openmrs_tag:dateField formFieldName="dateParam" startValue="${obsDate}" />
            <form:errors path="dateParam" cssClass="error"/>
          </c:otherwise>
        </c:choose>
              
      </td>
      <td width="31%"><div align="left"><strong style="color:#F7072C">${commandeSite.comSitePeriodLib}</strong></div></td>
      </tr>
    <tr>
      <td height="60">Mois de </td>
      <td>:</td>
      <td>${commandeSite.comSitePeriodDate}</td>
      <td>&nbsp;</td>
      <td>Stock maximum </td>
      <td>:</td>
      <td>${commandeSite.comStockMax}</td>
      <td>&nbsp;</td>
      <td>Date de  r&eacute;ception</td>
      <td>:</td>
      <td>${commandeSite.comSiteDateCom}</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">une ligne inserée</div>
</c:if>
<c:if test="${mess =='noValid'}">
		<div id="openmrs_msg">Impossible d'agir sur cette ligne de produit, elle figure dans le RM valid&eacute;</div>
</c:if>
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">une ligne supprim&eacute;e</div>
</c:if>
<c:if test="${mess =='noDelete'}">
		<div id="openmrs_msg">Impossible d'agir sur cette ligne de produit, elle figure dans le RM valid&eacute;</div>
</c:if>
<c:if test="${mess =='noModif'}">
		<div id="openmrs_msg">Impossible d'agir sur cette ligne de produit, elle figure dans le RM valid&eacute;</div>
</c:if>
<c:if test="${mess =='refuse'}">
		<div id="openmrs_msg">${info}</div>
</c:if>
<!--<div align="left">${info}</div>-->
<br>

        <div>
	<b class="boxHeader"></b>
	<div class="box">
    <c:if test="${tab =='1'}">

<table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="25%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">D&eacute;signation <span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                                             
                       <form:select path="produit" cssStyle="width:250px">
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${produits}">
                                 <option <c:if test="${formulaireTraitementsPPS.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										                                  			value="${item.getProdId()}">${item.getProdLib()} </option>
                               </c:forEach>
                		</form:select> 
													
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="produit" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="1%">&nbsp;</td>
              <td width="9%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Stock initial<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnComQteIni" cssStyle="width:100px"/>
											
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnComQteIni" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="8%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; re&ccedil;ue<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnComQteRecu" cssStyle="width:100px" />
											 
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnComQteRecu" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="7%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; utilis&eacute;e<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnComQteUtil" cssStyle="width:100px" />
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnComQteUtil" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="8%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Perte<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnComPertes" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:errors path="lgnComPertes" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="8%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Stock disponible <span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnComStockDispo" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:errors path="lgnComStockDispo" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="10%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Nombre de jour de rupture<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnComNbreJrsRup" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:errors path="lgnComNbreJrsRup" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="9%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; distribu&eacute;e M-1<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnQteDistriM1" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:errors path="lgnQteDistriM1" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="7%"><table width="100%" border="0" align="center" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; distribu&eacute;e M-2<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnQteDistriM2" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:errors path="lgnQteDistriM2" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="8%">
              <table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center"></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="submit" name="btn_ajouter"
												id="btn_ajouter" value="Ajouter">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
            </tr>
          </tbody>
        </table>

<table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="17%">Code</td>
                  <td width="17%">D&eacute;signation</td>
                  <td width="7%">Stock initial</td>
                  <td width="7%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="7%">Quantit&eacute; utilis&eacute;e</td>
                  <td width="7%">Perte</td>
                  <td width="7%">Stock disponible</td>
                  <td width="7%">Nombre de jour de rupture</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-1</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-2</td>
                  <td width="7%">Quantit&eacute; propos&eacute;e</td>
                  <td width="7%">Quantit&eacute; en stock</td>
                   <td width="7%">CMM</td>
                    <td width="7%">MSD</td>
                  <td width="7%">Quantit&eacute; approuv&eacute;e</td>
                  <td width="7%">Action</td>
                </tr>
                <% int x = 1;  %>	
                <c:forEach var="lo" items="${ligneCommandeSites}">
								<tr>
                                	<td width="17%"><div align="left">${lo.produit.prodCode}</div></td>
									<td width="17%"><div align="left">${lo.produit.prodLib}</div></td>
									<td width="12%">${lo.stockIni}</td>
									<td width="9%">${lo.qteRecu}</td>
									<td width="10%"><div align="left">${lo.qteUtil}</div></td>
									<td width="10%"><div align="left">${lo.qtePerte}</div></td>
									<td width="9%"><div align="left">${lo.stockDispo}</div></td>
									<td width="12%"><div align="left">${lo.nbrJourRuptur}</div></td>
									<td width="7%">${lo.qteDistri1}</td>
									<td width="7%">${lo.qteDistri2}</td>
                                    <c:set var="n" value="${((lo.qteDistri2+lo.qteDistri1+lo.qteUtil)/3)*commandeSite.comStockMax-(lo.stockDispo)}" scope="request" />
                                    <% double n= ((Double) request.getAttribute("n")).doubleValue(); 
									 int qteProInt= (int) Math.round(n);
									%>
                                    
									<td width="7%"><%= qteProInt %></td>
                                    <td width="7%">${lo.sdu}</td>
                                    <td width="7%">${lo.cmm}</td>
                                    <td width="7%">${lo.mdu}</td>
									<td width="7%"><input type="number" name="qtePro<%= x %>" id="qtePro<%= x %>" 
                                    				value="<%= qteProInt %>"></td>
									<td width="7%"><a
										href="<c:url value="/module/pharmagest/traitementsPPS.form">
									  <c:param name="modifId" value="${lo.produit.prodId}"/>                                    
									  </c:url>
									  ">Modifier</a>| <a
										href="<c:url value="/module/pharmagest/traitementsPPS.form">
									    <c:param name="supprimId" value="${lo.produit.prodId}"/>                                    
									    </c:url>
									    ">Supprimer</a></td>
									<input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${lo.produit.prodId}">
                                    
								</tr>
                <% x = x+1;  %> 
                
			   </c:forEach>
      </tbody>
</table>

</c:if>
<c:if test="${tab =='2'}">
<table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="11%">Code</td>
                  <td>D&eacute;signation</td>
                  <td>Stock initial</td>
                  <td>Quantit&eacute; re&ccedil;ue</td>
                  <td>Quantit&eacute; utilis&eacute;e</td>
                  <td>Perte</td>
                  <td>Stock disponible</td>
                  <td>Nombre de jour de rupture</td>
                  <td>Quantit&eacute; distribu&eacute;e M-1</td>
                  <td>Quantit&eacute; distribu&eacute;e M-2</td>
                  <td>Quantit&eacute; approuv&eacute;e</td>
                </tr>
                
                <c:forEach var="lo" items="${ligneCommandeSites}">
								<tr>
									<td width="11%"><div align="left">${lo.produit.prodCode}</div></td>
									<td width="16%"><div align="left">${lo.produit.prodLib}</div></td>
									<td width="9%">${lo.stockIni}</td>
									<td width="7%">${lo.qteRecu}</td>
									<td width="7%"><div align="left">${lo.qteUtil}</div></td>
									<td width="7%"><div align="left">${lo.qtePerte}</div></td>
									<td width="9%"><div align="left">${lo.stockDispo}</div></td>
									<td width="10%"><div align="left">${lo.nbrJourRuptur}</div></td>
									<td width="8%">${lo.qteDistri1}</td>
									<td width="8%">${lo.qteDistri2}</td>
									<td width="8%">${lo.qtePro}</td>                               
								</tr>
                
                
			   </c:forEach>
      </tbody>
</table>
</c:if>
	</div> 
	</div>
<script type="text/javascript">
            $("#dateTraitement").attr('required', true); 			
</script>

</form:form>