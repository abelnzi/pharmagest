<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/stockFournisseur.form"
	modelAttribute="formulaireStockFourni"
	commandName="formulaireStockFourni">
<div>
<h2 >Livraison du fournisseur</h2>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succ�s</div>
</c:if>
<br>
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
<br>
<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td width="13%">Fournisseur</td>
      <td width="36%"><form:select path="fournisseur">
														<form:option value="0" label="---Choix---" />
														<form:options items="${fournisseurs}" itemValue="fourId"
															itemLabel="fourDesign1" />
													</form:select> <form:errors path="fournisseur" cssClass="error" /></td>
      <td width="1%">&nbsp;</td>
      <td width="16%">Programme</td>
      <td width="34%"><form:select path="programme">
														<form:option value="0" label="---Choix---" />
														<form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
													</form:select> <form:errors path="programme" cssClass="error" /></td>
    </tr>
    <tr>
      <td>N&deg; de livraison</td>
      <td><form:input path="recptNum" /> <form:errors
														path="recptNum" cssClass="error" /></td>
      <td>&nbsp;</td>
      <td>Date de la livraison</td>
      <td><form:input path="recptDateRecept" /> <form:errors
														path="recptDateRecept" cssClass="error"/></td>
    </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">une ligne inser�e</div>
</c:if>
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">une ligne inser�e</div>
</c:if>
<br>
        <div>
	<b class="boxHeader"></b>
	<div class="box">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="12%"><div align="center">
                <table width="100%" border="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td><div align="center">Code article</div></td>
                    </tr>
                    <tr>
                      <td><div align="center">
                        <input type="text" disabled="disabled"
															value="${formulaireStockFourni.produit.prodId}" size="15" readonly />
                      </div></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div></td>
              <td width="27%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">D&eacute;signation</div></td>
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
              <td width="15%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <form:input path="lgnRecptLot" />
											
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnRecptLot" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="13%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">P&eacute;remption</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnDatePerem" />
											 
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnDatePerem" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute;</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnRecptQte" />
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnRecptQte" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="13%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Prix d'achat</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnRecptPrixAchat" />
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
              <td width="6%">
              <table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center"></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="submit" name="btn_valider"
												id="btn_valider" value="Valider">
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
                  <td width="27%">D&eacute;signation</td>
                  <td width="15%">Num&eacute;ro du lot</td>
                  <td width="13%">P&eacute;remption</td>
                  <td width="14%">Quantit&eacute;</td>
                  <td width="14%">Prix d'achat</td>
                  <td width="5%">Action</td>
                </tr>
                <c:forEach var="lo" items="${ligneOperations}">
								<tr>
									<td width="12%"><div align="left">${lo.produit.prodId}</div></td>
									<td width="27%"><div align="left">${lo.produit.prodLib}</div></td>
									<td width="15%"><div align="left">${lo.lgnRecptLot}</div></td>
									<td width="13%"><div align="left">${lo.lgnDatePerem}</div></td>
									<td width="14%"><div align="left">${lo.lgnRecptQte}</div></td>
									<td width="14%"><div align="left">${lo.lgnRecptPrixAchat}</div></td>
									<td width="5%"><div align="left"><a
										href="<c:url value="/module/pharmagest/stockFournisseur.form">
									  <c:param name="paramId" value="${lo.produit.prodId}"/>                                          
								    </c:url>">X</a>
									  
								    </div></td>
								</tr>
			   </c:forEach>
      </tbody>
</table>      
</div> 
	</div>

</form:form>