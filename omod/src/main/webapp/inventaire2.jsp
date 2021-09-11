<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">

	<li class="first">
		<a href="<c:url value="/module/pharmagest/inventaireMenu.form"/>"><spring:message
			code="pharmagest.inventaire" /></a>
	</li>
	<li>
		<a href="<c:url value="/module/pharmagest/inventaireImp.form"/>">Impression de l'inventaire</a>
	</li>

	<li class=" active">
		<a href="<c:url value="/module/pharmagest/inventaire.form"/>">Saisie d'inventaire</a>
	</li>
	<li>
				<a href="<c:url value="/module/pharmagest/inventaireValide.form"/>">Validation d'inventaire</a>
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
		        <td width="11%">Date de l'inventaire <span style="color:#F11A0C">*</span></td>
		        <td width="16%"> <openmrs_tag:dateField formFieldName="invDeb" startValue="${obsDate}" /> </td>
		        <td width="1%">&nbsp;</td>
		        <td width="8%">Programme <span style="color:#F11A0C">*</span></td>
		        <td width="23%"><!--<form:select path="pharmProgramme">
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
		        <td width="40%"><input type="submit" name="btn_editer"
						id="btn_editer" value="Editer" ></td>
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
		<div id="openmrs_msg">Une ligne retir&eacute;e</div>
</c:if>
<br>
<c:if test="${var =='1'}">
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
                     <!--<form:select path="produit" cssStyle="width:200px">
														<form:option value="0" label="---Choix---" />
														<form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
						</form:select>-->
                      
                      <form:select path="produit" cssStyle="width:250px">
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${produits}">
                                    <option <c:if test="${formulaireInventaire.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										 value="${item.getProdId()}">${item.getProdLib()} </option>
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
              <td width="6%"><table width="98%" border="0" cellspacing="0">
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
              <td width="11%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">P&eacute;remption<span style="color:#F11A0C">*</span></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="lgnDatePerem" cssStyle="width:100px"/>
											 
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="center"><form:errors path="lgnDatePerem" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="8%"><!--<table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute; th&eacute;orique<span style="color:#F11A0C">*</span></div></td>
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
              </table>--></td>
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
              <td width="8%">&nbsp;</td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Observation</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                       <form:input path="observation" cssStyle="width:300px" />
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
              <td width="13%">
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
                  <td width="8%">Quantit&eacute; th&eacute;orique</td>
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
</c:if>
</form:form>