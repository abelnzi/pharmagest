<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie inventaire saisie" 
        otherwise="/login.htm" redirect="/module/pharmagest/inventaire.form" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<spring:htmlEscape defaultHtmlEscape="true" />



<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">

	<li class="first">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>">Menu principal</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Edition de la Fiche inventaire</li>

	<li class=" active">
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
	</li>
     <li>
				<a href="<c:url value="/module/pharmagest/listInventaireModif.form"/>">Modification d'inventaire</a>
	</li>
	<li >
				<a href="<c:url value="/module/pharmagest/listInventaire.form"/>">Validation d'inventaire</a>
	</li>
	<li>
				<a href="<c:url value="/module/pharmagest/listHistoInventaire.form"/>">Historique des inventaires</a>
	</li>
	
	
	<!-- Add further links here -->
</ul>
</div>
<div>
<h3 ><span style="font-size:36px">.</span> Effectuer l'inventaire</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/inventaire.form"
	modelAttribute="formulaireInventaire"
	commandName="formulaireInventaire">
<br>
<c:if test="${mess =='save'}">
		<div id="openmrs_msg">Enregistrer avec succ&egrave;s</div>
</c:if>
<c:if test="${mess =='IE'}">
		<div id="openmrs_msg">L'inventaire du mois de : ${DateInv} indiqu&eacute; a &eacute;t&eacute; d&eacute;j&agrave; enregistr&eacute; </div>
</c:if>
 <c:if test="${var =='1'}">
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
		        <td width="13%">Date de l'inventaire <span style="color:#F11A0C">*</span></td>
		        <td width="16%"> <openmrs_tag:dateField formFieldName="invDeb" startValue="${obsDate}" /> </td>
		        <td width="1%">&nbsp;</td>
		        <td width="10%">Programme <span style="color:#F11A0C">*</span></td>
		        <td width="22%"><!--<form:select path="pharmProgramme">
		          <form:option value="0" label="---Choix---" />
		          <form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
		          </form:select>-->
                  
                  <form:select path="pharmProgramme">
                                      <option value="0">-- Choix --</option>
                                       <c:forEach var="item" items="${programmes}">
                                            <option <c:if test="${formulaireInventaire.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                                       </c:forEach>
                  </form:select> 
                  
		          <form:errors path="pharmProgramme" cssClass="error" /></td>
		        <td width="1%">&nbsp;</td>
		        <td width="37%"><input type="submit" name="btn_editer"
						id="btn_editer" value="Saisie produits" ></td>
	          </tr>
	        </tbody>
	      </table>
          </c:if>
          <c:if test="${var =='1'}">
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="13%">Date de l'inventaire</td>
		        <td width="25%"><form:input path="invDeb" disabled="true" /> <form:errors path="invDeb" cssClass="error" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i></td>
		        <td width="1%">&nbsp;</td>
		        <td width="9%">Programme</td>
		        <td width="52%"><input type="text" disabled="disabled" value="${formulaireInventaire.getPharmProgramme().programLib}"></td>
	          </tr>
	        </tbody>
	      </table>
          </c:if>
		</div>
	</div>
</div>
<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">Une ligne inser&eacute;e</div>
</c:if>
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">Une ligne suprim&eacute;e</div>
</c:if>
<br>
<c:if test="${var =='1'}">
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
                  <td width="11%">Code article</td>
                  <td width="17%">D&eacute;signation</td>
                  <td width="11%">Unit&eacute;</td>
                  <td width="12%">Num&eacute;ro du lot</td>
                  <td width="10%">P&eacute;remption</td>
                  <td width="10%">Quantit&eacute; physique</td>
                  <td width="17%">Prix moyen pond&eacute;r&eacute;</td>
                  <td width="17%">Total</td>
                  <td width="17%">Observation</td>
                  <td width="12%">Action</td>
                </tr>
                <c:forEach var="li" items="${ligneInventaires}">
								<tr>
									<td width="11%"><div align="left">${li.produit.prodCode}</div></td>
									<td width="17%"><div align="left">${li.produit.prodLib}</div></td>
									<td width="11%"><div align="left">${li.produit.prodUnite}</div></td>
									<td width="12%"><div align="left">${li.lgnLot}</div></td>
                                    <c:set var="d3" value="${li.lgnDatePerem}" scope="request" />
                                    <% Date d3=  (Date) request.getAttribute("d3"); 
										SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s3 =  formatter3.format(d3); %>
									<td width="10%"><div align="left"><%= s3 %></div></td>
									<td width="10%">${li.qtePhysique}</td>
									<td width="17%">${li.prixInv}</td>
									<td width="17%">${li.qtePhysique*li.prixInv}</td>
									<td width="17%"><div align="left">${li.observation}</div></td>
									<td width="12%">
                                    <a
										href="<c:url value="/module/pharmagest/inventaire.form">
									  <c:param name="modifId" value="${li.produit.prodId}${li.lgnLot}"/>                                          
								    </c:url>">Modifier</a>|
                                    <a
										href="<c:url value="/module/pharmagest/inventaire.form">
									  <c:param name="paramId" value="${li.produit.prodId}${li.lgnLot}"/>                                          
								    </c:url>">Supprimer</a>
									  
								    </td>
								</tr>
                                </c:forEach>
								<tr>
								  <td colspan="7"><div align="right"><strong>Total global</strong></div></td>
								  <td><strong>${totalGlobal}</strong></td>
								  <td>&nbsp;</td>
								  <td>&nbsp;</td>
				  </tr>
		       
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