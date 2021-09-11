<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception" 
        otherwise="/login.htm" redirect="/index.htm" />

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>-->

<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery.1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery.autocomplete.min.js"></script>

<!--<script src="<c:url value="/resources/core/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/core/jquery.autocomplete.min.js" />"></script>
<link href="<c:url value="/resources/core/main.css" />" rel="stylesheet">-->

<!--<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>-->
<% Boolean activeFour=true;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false; Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>
<!--<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    <li  class="active first">
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">R&eacute;ception produits</a>
	</li>
	<li ><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>
    <li><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Distribution</a></li>
	
	<li >
		<a href="<c:url value="/module/pharmagest/mouvementStock.form"/>">Mouvement de stock</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapports</a>
	</li>
    <li>
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Fichiers de base</a>
	</li>
	
	
</ul>
</div>-->
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>


<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/stockFournisseur.form"
	modelAttribute="formulaireStockFourni"
	commandName="formulaireStockFourni"  >
<div>
<h3 ><span style="font-size:36px">.</span> Réception de produits </h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<br>

<c:if test="${var =='0'}">
<input type="submit" name="btn_editer" id="btn_editer" value="Saisie produits" >
<br><br>
<div>


	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="13%" height="60">Fournisseur <span style="color:#F11A0C">*</span></td>
      <td width="36%">               
               
                <form:select path="pharmFournisseur">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${fournisseurs}">
                            <option <c:if test="${formulaireStockFourni.getPharmFournisseur().getFourId()==item.getFourId()}">selected="selected"</c:if>    				  							value="${item.getFourId()}">${item.getFourDesign1()} </option>
                       </c:forEach>
                </form:select>
                <form:errors path="pharmFournisseur" cssClass="error"/>
      </td>
      <td width="1%">&nbsp;</td>
      <td width="16%">Programme <span style="color:#F11A0C">*</span></td>
      <td width="34%">
                 
                <form:select path="pharmProgramme">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireStockFourni.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/>                        
      </td>
    </tr>
    <tr>
      <td>Bordereau de livraison</td>
      <td><form:input path="operNum" /> <form:errors
														path="operNum" cssClass="error" /></td>
      <td>&nbsp;</td>
      <td>Date de la livraison <span style="color:#F11A0C">*</span></td>
      <td><!--<form:input path="operDateRecept" /> <form:errors
														path="operDateRecept" cssClass="error"/><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>--> <openmrs_tag:dateField formFieldName="operDateRecept" startValue="${obsDate}" /><form:errors path="operDateRecept" cssClass="error"/></td>
    </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
</c:if>

<c:if test="${var =='1'}">
<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;">
        <input type="submit" name="reset" id="reset" value="Annuler" > <!--<input type="text"  id="w-input-search" value="">--></td>
      </tr>
  </tbody>
</table>

</div>
<br>
<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="14%" height="33">Fournisseur</td>
      <td width="1%">:</td>
      <td width="29%">${formulaireStockFourni.pharmFournisseur.fourDesign1}               
               
      </td>
      <td width="1%">&nbsp;</td>
      <td width="13%">Programme</td>
      <td width="1%">:</td>
      <td width="41%">${formulaireStockFourni.pharmProgramme.programLib}
                 
      </td>
    </tr>
    <tr>
      <td>Bordereau de livraison</td>
      <td>:</td>
      <td>${formulaireStockFourni.operNum}</td>
      <td>&nbsp;</td>
      <td>Date de la livraison</td>
      <td>:</td>
       <c:set var="d" value="${formulaireStockFourni.operDateRecept}" scope="request" />
                <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);
		%>
      <td><%= s %></td>
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
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">une ligne supprim&eacute;e</div>
</c:if>
<!--<div align="left">${info}</div>-->
<br>
        <div>
	<b class="boxHeader"></b>
	<div class="box">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="1%"><!--<div align="center">
                <table width="100%" border="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td><div align="center">Code article</div></td>
                    </tr>
                    <tr>
                      <td><div align="center">
                        <input type="text" disabled="disabled"
															value="${formulaireStockFourni.produit.prodCode}" size="15" readonly />
                      </div></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div>--></td>
              <td width="39%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">D&eacute;signation <span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                                             
                       <form:select path="produit" cssStyle="width:300px">
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${produits}">
                                    <option <c:if test="${formulaireStockFourni.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										                                  			value="${item.getProdId()}">${item.getProdLib()} </option>
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
              <td width="2%">&nbsp;</td>
              <td width="15%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnRecptLot" cssStyle="width:100px"/>
											
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnRecptLot" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="9%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">P&eacute;remption<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <!--<form:input path="lgnDatePerem" />-->
						<openmrs_tag:dateField formFieldName="lgnDatePerem" startValue="${obsDate}" />					 
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnDatePerem" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="9%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute;<span style="color:#F11A0C"> *</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnRecptQte" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnRecptQte" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="11%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Prix d'achat</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnRecptPrixAchat" cssStyle="width:100px"/>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnRecptPrixAchat" cssClass="error" /></div></td>
                  </tr>
                  <tr>
                    <td></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%">
              <table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center"></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="submit" name="btn_valider"
												id="btn_valider" value="Ajouter">
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
                  <td width="12%">Code article</td>
                  <td width="20%">D&eacute;signation</td>
                  <td width="10%">Unit&eacute;</td>
                  <td width="10%">Num&eacute;ro du lot</td>
                  <td width="10%">P&eacute;remption</td>
                  <td width="10%">Quantit&eacute;</td>
                  <td width="10%">Prix d'achat</td>
                  <td width="14%">Action</td>
                </tr>
                <c:forEach var="lo" items="${ligneOperations}">
								<tr>
									<td width="12%"><div align="left">${lo.produit.prodCode}</div></td>
									<td width="16%"><div align="left">${lo.produit.prodLib}</div></td>
									<td width="11%">${lo.produit.prodUnite}</td>
									<td width="16%"><div align="left">${lo.lgnRecptLot}</div></td>
                                    <c:set var="d2" value="${lo.lgnDatePerem}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2"); 
										SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s2 =  formatter2.format(d2); %>
									<td width="10%"><div align="left"><%= s2 %></div></td>
									<td width="9%"><div align="left">${lo.lgnRecptQte}</div></td>
									<td width="12%"><div align="left">${lo.lgnRecptPrixAchat}</div></td>
									<td width="14%">
                                    <a
										href="<c:url value="/module/pharmagest/stockFournisseur.form">
									  <c:param name="modifId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								    </c:url>">Modifier</a>|
                                    <a
										href="<c:url value="/module/pharmagest/stockFournisseur.form">
									  <c:param name="paramId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								    </c:url>">Supprimer</a>
                                   
									 
								    </td>
								</tr>
			   </c:forEach>
      </tbody>
</table>      
</div> 
	</div>
</c:if>
<script type="text/javascript">
	var $ = jQuery.noConflict();
	document.getElementById("lgnDatePerem").value="${datePerem}";
</script>
</form:form>

<!--<script>
	$(document).ready(function() {

		$('#w-input-search').autocomplete({
			serviceUrl: '${pageContext.request.contextPath}/module/pharmagest/stockFournisseur/autocomplete',
			paramName: "fourDesign1",
			delimiter: ",",
		    transformResult: function(response) {
		        return { 	
		            suggestions: $.map($.parseJSON(response), function(item) {
		                return { value: item.fourDesign1, data: item.fourId };
		            })
		            
		        };
		        
		    }
		    
		});
		
		
	});
</script>-->

<!--<script type="text/javascript">
$(document).ready(function() {

	$( "#w-input-search" ).autocomplete({
		source: '${pageContext.request.contextPath}/module/pharmagest/stockFournisseur/autocomplete'
	});
	
});
</script>-->