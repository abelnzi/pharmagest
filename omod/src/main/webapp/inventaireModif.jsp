<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire modif" 
        otherwise="/login.htm" redirect="/index.htm" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    
			
	<li class="first">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>">Menu principal</a>
	</li>
	<li >
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire</a>
	</li>

	<li >
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
	</li>
    <li  class=" active">
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
	</li>
	<li>
				<a href="<c:url value="/module/pharmagest/listInventaire.form"/>">Validation d'inventaire</a>
	</li>
	<li>
				<a href="<c:url value="/module/pharmagest/listHistoInventaire.form"/>">Historique des inventaires</a>
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
	action="${pageContext.request.contextPath}/module/pharmagest/inventaireModif.form"
	modelAttribute="formulaireInventaire"
	commandName="formulaireInventaire"  >
<div>
<h3 ><span style="font-size:36px">.</span> Modification de l'inventaire N&deg; : ${formulaireInventaire.pharmInventaire.invId}</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
 <c:if test="${var =='1'}">
<br>
<br>

<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="btn_enregistrer"
						id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;"> 
          <input type="submit" name="reset" id="reset" value="Annuler"></td>
      </tr>
  </tbody>
</table>

</div>
</c:if>
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <c:if test="${var =='0'}">
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="13%">Date de l'inventaire<span style="color:#F11A0C">*</span></td>
		        <td width="16%">
                <form:input path="invDeb" /> <form:errors path="invDeb" cssClass="error" />
                <br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>
                </td>
		        <td width="1%">&nbsp;</td>
		        <td width="10%">Programme <span style="color:#F11A0C">*</span></td>
		        <td width="22%">
                 <input type="text" readonly=true value="${formulaireInventaire.getPharmProgramme().programLib}">
                 <!--<form:select path="pharmProgramme" >
                                      <option value="0">-- Choix --</option>
                                       <c:forEach var="item" items="${programmes}">
                                            <option <c:if test="${formulaireInventaire.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                                       </c:forEach>
                  </form:select>
                  
		          <form:errors path="pharmProgramme" cssClass="error" />--></td>
		        <td width="1%">&nbsp;</td>
		        <td width="37%"><input type="submit" name="btn_editer"
						id="btn_editer" value="Modifier les produits" ></td>
	          </tr>
	        </tbody>
	      </table>
          </c:if>
          <c:if test="${var !='0'}">
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="13%">Date de l'inventaire</td>
		        <td width="25%"><form:input path="invDeb" readonly="true" /> <form:errors path="invDeb" cssClass="error" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i></td>
		        <td width="1%">&nbsp;</td>
		        <td width="9%">Programme</td>
		        <td width="52%"><input type="text" readonly=true value="${formulaireInventaire.getPharmProgramme().programLib}"></td>
	          </tr>
	        </tbody>
	      </table>
          </c:if>
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

<c:if test="${tab =='1'}">
<div>
	<b class="boxHeader"></b>
	<div class="box">
  

<table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="11%">&nbsp;</td>
              <td width="29%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">D&eacute;signation<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                     <!--<form:select path="produit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
						</form:select>-->
                      
                      <form:select path="produit" cssStyle="width:250px">
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${produits}">
                                    <option <c:if test="${formulaireInventaire.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										 value="${item.getProdId()}">${item.getFullName()} </option>
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
              <td width="12%"><table width="98%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnLot" cssStyle="width:100px" />
											
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnLot" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="10%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">P&eacute;remption<span style="color:#F11A0C">*</span></div></td>
                    </tr>
                  <tr>
                    <td><div align="center">
                      
                       <openmrs_tag:dateField formFieldName="lgnDatePerem" startValue="${obsDate}" />
                      
                      </div></td>
                    </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnDatePerem" cssClass="error" /></div></td>
                    </tr>
                  </tbody>
              </table></td>
              <td width="10%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; physique<span style="color:#F11A0C">*</span></div></td>
                    </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="qtePhysique" cssStyle="width:100px" />
                      </div></td>
                    </tr>
                  <tr>
                    <td><div align="center">
                      <form:errors path="qtePhysique" cssClass="error" />
                      </div></td>
                    </tr>
                  <tr>
                    <td></td>
                    </tr>
                  </tbody>
              </table></td>
              <td width="16%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Observation</div></td>
                    </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="observation" cssStyle="width:300px;heigth:50px" />
                      </div></td>
                    </tr>
                  <tr>
                    <td><div align="center"><form:errors path="observation" cssClass="error" /></div></td>
                    </tr>
                  <tr>
                    <td></td>
                    </tr>
                  </tbody>
              </table></td>
              <td width="12%">
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
                  <td>Code article</td>
                  <td>D&eacute;signation</td>
                  <td>Unit&eacute;</td>
                  <td>Num&eacute;ro du lot</td>
                  <td>P&eacute;remption</td>
                  
                  <td>Quantit&eacute; physique</td>
                  <td>Prix moyen pond&eacute;r&eacute;</td>
                  
                  <td>Observation</td>
                  <td>Action</td>
              
                </tr>
                <% int x = 1;  %>	
                <c:forEach var="li" items="${ligneInventaires}">
								<tr>
									<td width="11%"><div align="left">${li.produit.prodCode}</div></td>
									<td width="17%"><div align="left">${li.produit.prodLib}</div></td>
									<td width="11%"><div align="left">${li.produit.prodUnite}</div></td>
									<td width="12%">
                                    <input type="texte" name="lot<%= x %>" id="lot<%= x %>" 
                                    				value="${li.lgnLot}" readonly=true >
                                    </td>
                                    <c:set var="d3" value="${li.lgnDatePerem}" scope="request" />
                                    <% Date d3=  (Date) request.getAttribute("d3"); 
										SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
										String s3 =null;
										if(d3!=null){
										 s3 =  formatter3.format(d3); 
										} 
										%>
									<td width="10%">
                                    <input type="text" name="datePerem<%= x %>" id="datePerem<%= x %>" 
                                    				value="<%= s3 %>">

                                    </td>
									<td width="10%">
                                     <input type="number" name="qtePhys<%= x %>" id="qtePhys<%= x %>" 
                                    				value="${li.qtePhysique}">
                                    </td>
									<td width="17%">${li.prixInv}</td>
									<td width="17%">
                                   	<input type="text" name="observ<%= x %>" id="observ<%= x %>" 
                                    				value="${li.observation}">

                                    </td>
                                    
									<input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${li.produit.prodId}${li.lgnLot}">
                                    <td width="12%">
                                    
                                    <a
										href="<c:url value="/module/pharmagest/inventaireModif.form">
									  <c:param name="supprimId" value="${li.produit.prodId}${li.lgnLot}"/>                                          
								    </c:url>">Supprimer</a>
									  
								    </td>
                                    
								</tr>
                <% x = x+1;  %> 
                
			   </c:forEach>
      </tbody>
</table>


</div></div></c:if>

<c:if test="${tab =='2'}">
<div>
	<b class="boxHeader"></b>
	<div class="box">

<table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                 <td>Code article</td>
                  <td>D&eacute;signation</td>
                  <td>Unit&eacute;</td>
                  <td>Num&eacute;ro du lot</td>
                  <td>P&eacute;remption</td>
                  <td>Quantit&eacute; th&eacute;orique</td>
                  <td>Prix moyen pond&eacute;r&eacute;</td>
                  <td>Total</td>
                  <td>Observation</td>
                  
                </tr>
                
                <c:forEach var="lo" items="${inventaires}">
								<tr>
									<td width="17%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodCode}</div></td>
									<td width="12%"><div align="left">${lo.pharmProduitAttribut.pharmProduit.prodLib}</div></td>
									<td width="9%">${lo.pharmProduitAttribut.pharmProduit.prodUnite}</td>
									<td width="9%">${lo.pharmProduitAttribut.prodLot}</td>
                                    <c:set var="d" value="${lo.pharmProduitAttribut.prodDatePerem}" scope="request" />
                                    <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
                                    <td width="12%"><div align="left"><%= s %></div></td>
									
									<td width="10%"><div align="left">${lo.qtePhysique}</div></td>
									<td width="7%">${lo.prixInv}</td>
									<td width="7%">${lo.qtePhysique*lo.prixInv}</td>
																
									<td width="7%">${lo.observation}</td>
								
                                    
								</tr>
                                </c:forEach>
								<tr>
								  <td colspan="7"><div align="right"><strong>Total global</strong></div></td>
								  <td><strong>${totalGlobal}</strong></td>
								  <td>&nbsp;</td>
				  </tr>
                
                
		       
      </tbody>
</table>

	</div> 
	</div>
</c:if>

</form:form>