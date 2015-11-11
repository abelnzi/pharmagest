<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<spring:htmlEscape defaultHtmlEscape="true" />
<h2>
	<spring:message code="pharmagest.title" />
</h2>
<ul id="menu">
	

	<li class="first"><a href="<c:url value="/module/pharmagest/dispensationChoix.form"/>"><spring:message
			code="pharmagest.dispensation" /></a></li>

	<li>
		<a href="<c:url value="/module/pharmagest/stockFournisseur.form"/>">Entr&eacute;e fournisseur</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockEntree.form"/>">Autre mouvement d'entr&eacute;e de stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/stockSortie.form"/>">Autre mouvement de sortie de stock</a>
	</li>
	<li class="active">
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/rapportStock.form"/>">Rapportage sur le Stock</a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/parametrage.form"/>">Param&eacute;trage</a>
	</li>
	
	<!-- Add further links here -->
</ul>

<div>
<h3 >Effectuer l'inventaire</h3>
</div>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/inventaire.form"
	modelAttribute="formulaireInventaire"
	commandName="formulaireInventaire">
<br>
<c:if test="${mess =='save'}">
		<div id="openmrs_msg">Enregistrer avec succ&egrave;s</div>
</c:if>
<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Enregistrer">
        <input type="reset" name="reset" id="reset" value="Annuler"></td>
      </tr>
  </tbody>
</table>

</div>
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="13%">Date de l'inventaire<span style="color:#F11A0C">*</span></td>
		        <td width="36%"><form:input path="invDeb" /> <form:errors path="invDeb" cssClass="error" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i></td>
		        <td width="1%">&nbsp;</td>
		        <td width="16%">Programme</td>
		        <td width="34%"><form:select path="pharmProgramme">
		          <form:option value="0" label="---Choix---" />
		          <form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
		          </form:select>
		          <form:errors path="pharmProgramme" cssClass="error" /></td>
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>
</div>
<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">Une ligne inser&eacute;e</div>
</c:if>
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">Une ligne retir&eacute;e</div>
</c:if>
<br>
<div>
	<b class="boxHeader"></b>
	<div class="box">
      <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="9%"><div align="center">
                <table width="100%" border="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td><div align="center">Code article</div></td>
                    </tr>
                    <tr>
                      <td><div align="center">
                        <input type="text" disabled="disabled"
															value="${formulaireInventaire.produit.prodCode}" size="15" readonly />
                      </div></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div></td>
              <td width="21%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">D&eacute;signation<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                     <form:select path="produit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
						</form:select>
													
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="produit" cssClass="error" />
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="6%"><table width="98%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnLot" size="10" />
											
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnLot" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="11%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">P&eacute;remption<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnDatePerem" size="10"/>
											 
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnDatePerem" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="11%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; virtuelle<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="qteLogique" size="10" />
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="qteLogique" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="9%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; physique<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="qtePhysique" size="10" />
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
              <td width="11%">&nbsp;</td>
              <td width="11%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Observation</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="observation" />
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
              <td width="11%">
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
                  <td width="8%">Code article</td>
                  <td width="20%">D&eacute;signation</td>
                  <td width="8%">Num&eacute;ro du lot</td>
                  <td width="10%">P&eacute;remption</td>
                  <td width="8%">Quantit&eacute; Vituelle</td>
                  <td width="8%">Quantit&eacute; physique</td>
                  <td width="8%">Ecart</td>
                  <td width="15%">Observation</td>
                  <td width="10%">Action</td>
                </tr>
                <c:forEach var="li" items="${ligneInventaires}">
								<tr>
									<td width="12%"><div align="left">${li.produit.prodCode}</div></td>
									<td width="16%"><div align="left">${li.produit.prodLib}</div></td>
									<td width="8%"><div align="left">${li.lgnLot}</div></td>
									<td width="11%"><div align="left">${li.lgnDatePerem}</div></td>
									<td width="10%"><div align="left">${li.qteLogique}</div></td>
									<td width="11%">${li.qtePhysique}</td>
									<td width="9%">${li.qteLogique-li.qtePhysique}</td>
									<td width="12%"><div align="left">${li.observation}</div></td>
									<td width="11%"><div align="left"><a
										href="<c:url value="/module/pharmagest/inventaire.form">
									  <c:param name="paramId" value="${li.produit.prodId}${li.lgnLot}"/>                                          
								    </c:url>">Supprimer</a>
									  
								    </div></td>
								</tr>
			   </c:forEach>
      </tbody>
</table>      
</div> 
</div>
</form:form>