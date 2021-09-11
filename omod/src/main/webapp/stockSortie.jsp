<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie mouvement" 
        otherwise="/login.htm" redirect="/index.htm" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">


	<li class="first ">
		<a href="<c:url value="/module/pharmagest/mouvementStock.form"/>">Menu principal</a>
	</li>
		
	<li >
		<a href="<c:url value="/module/pharmagest/stockEntree.form"/>">Entr&eacute;e de stock</a>
	</li>
	<li class="active">
		<a href="<c:url value="/module/pharmagest/stockSortie.form"/>">Sortie de stock</a>
	</li>
	
	
	<!-- Add further links here -->
</ul>
</div>

<SCRIPT type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/stockSortie.form"
	modelAttribute="formulaireStockFourni"
	commandName="formulaireStockFourni">
	<div>
		<h3><span style="font-size:36px">.</span> Sortie de stock</h3>
	</div>
        <c:if test="${mess =='success'}">
          <div id="openmrs_msg">Enregistrer avec succ&egrave;s</div>
        </c:if>
        <c:if test="${mess =='aut'}">
			<div id="openmrs_msg">Vous n'etes pas autorit&eacute; à effectu&eacute; cette op&eacute;ration &Agrave; la date du ${dateAut}</div>
	    </c:if>
        <br>
        <c:if test="${var =='0'}">
        <div>
          <table width="100%" border="0">
            <tbody>
              <tr>
                <td><input type="submit" name="btn_editer"
							id="btn_editer" value="Saisie produits" ></td>
              </tr>
            </tbody>
          </table>
        </div>
        <br>
        <div> <b class="boxHeader"></b>
          <div class="box">
            <div class="searchWidgetContainer" id="findPatients" align="center">
              <table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="14%">Programme<span style="color:#F11A0C"> *</span></td>
                    <td width="31%"><!--<form:select path="pharmProgramme">
                      <form:option value="0" label="---Choix---" />
                      <form:options items="${programmes}" itemValue="programId"
															itemLabel="programLib" />
                    </form:select>-->
                    
                     <form:select path="pharmProgramme" >
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${programmes}">
                                    <option <c:if test="${formulaireStockFourni.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  										 value="${item.getProgramId()}">${item.getProgramLib()} </option>
                               </c:forEach>
                	</form:select>
                    
                      <form:errors path="pharmProgramme" cssClass="error" /></td>
                    <td width="19%">Destinataire</td>
                    <td width="36%"><form:input path="operRef" />
                      <form:errors
									path="operRef" cssClass="error" /></td>
                  </tr>
                  <tr>
                    <td>Type de mouvement<span style="color:#F11A0C"> *</span></td>
                    <td><!--<form:select path="pharmTypeOperation">
									<form:option value="0" label="---Choix---" />
									<form:option value="5" label="transfert vers un autre site" />
									<form:option value="6" label="produits avari&eacute;s" />
                                    <form:option value="7" label="produits p&eacute;rim&eacute;s" />
								</form:select>-->
                                
                      <form:select path="pharmTypeOperation">
									<form:option value="0" label="---Choix---" />
                                    
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==6}">selected="selected"</c:if>    				  										                                            value="6">produits avari&eacute;s</option>
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==7}">selected="selected"</c:if>    				  										                                            value="7">produits p&eacute;rim&eacute;s</option>
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==10}">selected="selected"</c:if>    				  										                                            value="10">vol(s)</option>
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==11}">selected="selected"</c:if>    				  										                                            value="11">autre(s) perte(s)</option>
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==12}">selected="selected"</c:if>    				  										                                            value="12">endommag&eacute;(s)</option>
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==16}">selected="selected"</c:if>    				  										                                            value="16">Retour au fournisseur</option>
                                    <openmrs:hasPrivilege privilege="pharmacie DISTRICT">
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==5}">selected="selected"</c:if>    				  										                                            value="5">Transfert Sortant</option>
                                    <option <c:if test="${formulaireStockFourni.getPharmTypeOperation().getToperId()==15}">selected="selected"</c:if>    				  										                                            value="15">Correctif inventaire n&eacute;gatif</option>
                                    </openmrs:hasPrivilege>
									
								</form:select>           
                      <form:errors path="pharmTypeOperation" cssClass="error" /></td>
                    <td>Date de mouvement<span style="color:#F11A0C"> *</span></td>
                    <td><!--<form:input path="operDateRecept" />-->
                    <openmrs_tag:dateField formFieldName="operDateRecept" startValue="${obsDate}" />
                      <form:errors
									path="operDateRecept" cssClass="error" /><br><!--<i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>--></td>
                  </tr>
                  <tr>
                    <td>Motifs</td>
                    <td><form:textarea path="operObserv" rows="4"
									cols="40" />
                      <form:errors path="operObserv" cssClass="error" /></td>
                    <td>Bon de Livaison</td>
                    <td width="36%"><form:input path="operNum" />
                      <form:errors
									path="operNum" cssClass="error" /></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        </c:if>
        
        <br>
        
        <!---------------------------------------------------------------------------------------->
        
         <c:if test="${var =='1'}">
         
         <div>
		<table width="100%" border="0">
			<tbody>
				<tr>
					<td><input type="submit" name="btn_enregistrer"
						id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;"> <input
						type="submit" name="reset" id="reset" value="Annuler"></td>
				</tr>
			</tbody>
		</table>

	</div>
	<br>

	<div>

		<b class="boxHeader"></b>
		<div class="box">
			<div class="searchWidgetContainer" id="findPatients" align="center">
				<table width="100%" border="0" cellpadding="7" cellspacing="0">
					<tbody>
						<tr>
							<td width="15%">Programme</td>
							<td width="1%">:</td>
							<td width="22%">
                            ${formulaireStockFourni.pharmProgramme.programLib}
                            
                            </td>
							<td width="5%">&nbsp;</td>
							<td width="19%">Destinataire</td>
							<td width="1%">:</td>
							<td width="37%">${formulaireStockFourni.operRef}</td>
						</tr>
						<tr>
							<td>Type de mouvement</td>
							<td>:</td>
							<td>${formulaireStockFourni.pharmTypeOperation.toperLib}</td>
							<td>&nbsp;</td>
							<td>Date de mouvement</td>
							<td>:</td>
                            <c:set var="d" value="${formulaireStockFourni.operDateRecept}" scope="request" />
                                    <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
							<td> <%= s %> </td>
						</tr>
						<tr>
							<td>Motifs</td>
							<td>:
							 </td>
							<td>${formulaireStockFourni.operObserv}</td>
						  <td>&nbsp;</td>
							<td>Bon de Livaison</td>
							<td>:</td>
							<td>${formulaireStockFourni.operNum}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
         
         
        
        <!---------------------------------------------------------------------------------------->
        
        <c:if test="${mess =='valid'}">
          <div id="openmrs_msg">une ligne inser&eacute;e</div>
        </c:if>
        <c:if test="${mess =='delete'}">
          <div id="openmrs_msg">une ligne supprim&eacute;e</div>
        </c:if>
        <c:if test="${mess =='insufisant'}">
          <div id="openmrs_msg">stock insuffisant</div>
        </c:if>
        <c:if test="${mess =='indisponible'}">
          <div id="openmrs_msg">stock indisponible</div>
        </c:if>
        <c:if test="${mess =='echecRetour'}">
          <div id="openmrs_msg">Impossible de retourner cette quantit&eacute; pour ce  lot produit : ${qteRetour} / ${lotProd}</div>
        </c:if>
        <div> <b class="boxHeader"></b>
          <div class="box">
            <table width="100%" border="0" cellpadding="7" cellspacing="0" >
              <tbody>
                <tr>
                  <td width="12%">&nbsp;</td>
                  <td width="27%"><table width="100%" border="0" cellspacing="0">
                    <tbody>
                      <tr>
                        <td><div align="center">D&eacute;signation <span style="color:#F11A0C">*</span></div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <!--<form:select path="produit" cssStyle="width:200px">
                            <form:option value="0" label="---Choix---" />
                            <form:options items="${produits}" itemValue="prodId"
															itemLabel="prodLib" />
                          </form:select>-->
                          
                          <form:select path="produit" cssStyle="width:200px">
                      		<option value="0">-- Choix --</option>
                               <c:forEach var="item" items="${produits}">
                                    <option <c:if test="${formulaireStockFourni.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										 value="${item.getProdId()}">${item.getFullName()} </option>
                               </c:forEach>
                		</form:select>
                          
                        </div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <form:errors path="produit" cssClass="error" />
                        </div></td>
                      </tr>
                    </tbody>
                  </table></td>
                  <td width="15%"><table width="100%" border="0" cellspacing="0">
                    <tbody>
                      <tr>
                        <td><div align="center">Num&eacute;ro du lot <span style="color:#F11A0C">*</span></div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <form:input path="lgnRecptLot" />
                        </div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <form:errors path="lgnRecptLot" cssClass="error" />
                        </div></td>
                      </tr>
                    </tbody>
                  </table></td>
                  <td width="13%"><table width="100%" border="0" cellspacing="0">
                    <tbody>
                      <tr>
                        <td><div align="center">P&eacute;remption <span style="color:#F11A0C">*</span></div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          
                          <openmrs_tag:dateField formFieldName="lgnDatePerem" startValue="${obsDate}" />
                        </div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <form:errors path="lgnDatePerem" cssClass="error" />
                        </div></td>
                      </tr>
                    </tbody>
                  </table></td>
                  <td width="14%"><table width="100%" border="0" cellspacing="0">
                    <tbody>
                      <tr>
                        <td><div align="center">Quantit&eacute; <span style="color:#F11A0C">*</span></div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <form:input path="lgnRecptQte" />
                        </div></td>
                      </tr>
                      <tr>
                        <td><div align="center">
                          <form:errors path="lgnRecptQte" cssClass="error" />
                        </div></td>
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
                        <td><div align="center">
                          <form:errors path="lgnRecptPrixAchat" cssClass="error" />
                        </div></td>
                      </tr>
                      <tr>
                        <td></td>
                      </tr>
                    </tbody>
                  </table></td>
                  <td width="6%"><table width="100%" border="0" cellspacing="0">
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
                  <td width="27%">D&eacute;signation</td>
                  <td width="15%">Num&eacute;ro du lot</td>
                  <td width="13%">P&eacute;remption</td>
                  <td width="14%">Quantit&eacute;</td>
                  <td width="14%">Prix d'achat</td>
                  <td width="5%">Action</td>
                </tr>
                <c:forEach var="lo" items="${ligneOperations}">
                  <tr>
                    <td width="12%"><div align="left">${lo.produit.prodCode}</div></td>
                    <td width="27%"><div align="left">${lo.produit.prodLib}</div></td>
                    <td width="15%"><div align="left">${lo.lgnRecptLot}</div></td>
                    <c:set var="d3" value="${lo.lgnDatePerem}" scope="request" />
                                    <% Date d3=  (Date) request.getAttribute("d3"); 
										SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s3 =  formatter3.format(d3); %>
									<td width="13%"><div align="left"><%= s3 %></div></td>
                    <td width="14%"><div align="left">${lo.lgnRecptQte}</div></td>
                    <td width="14%"><div align="left">${lo.lgnRecptPrixAchat}</div></td>
                    <td width="5%">
                    <a
										href="<c:url value="/module/pharmagest/stockSortie.form">
									  <c:param name="modifId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								    </c:url>">Modifier</a>|
                    <a
										href="<c:url value="/module/pharmagest/stockSortie.form">
                      <c:param name="paramId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>          
                      </c:url>
                      ">Supprimer</a> </td>
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

