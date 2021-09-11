<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie distribution saisies" 
        otherwise="/login.htm" redirect="/index.htm" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
    
			<li class="first "><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Menu principal</a></li>
			
    		<li class="active">
				<a href="<c:url value="/module/pharmagest/saisiesRPPS.form"/>">Saisies des rapports des ESPC/PPS</a>
			</li>
             <li>
				<a href="<c:url value="/module/pharmagest/importRapportConso.form"/>">Importation des rapports des ESPC/PPS</a>
            </li>
            <li>
				<a href="<c:url value="/module/pharmagest/listPPSModification.form"/>">Modification/Validation des rapports ESPC/PPS
            </li>            
            <li>
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
	action="${pageContext.request.contextPath}/module/pharmagest/saisiesRPPS.form"
	modelAttribute="formulaireSaisiesPPS"
	commandName="formulaireSaisiesPPS"  >
<div>
<h3 ><span style="font-size:36px">.</span> Saisie des rapports des ESPC/PPS</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<c:if test="${mess =='existe'}">
		<div id="openmrs_msg">Le rapport mensuel ESPC/PPS du mois de : ${moisDe} a &eacute;t&eacute; d&eacute;j&agrave; saisi</div>
</c:if>
<br>

<c:if test="${var =='0'}">
<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="btn_editer"
							id="btn_editer" value="Saisie produits" >
      <!--<a href="<c:url value="/module/pharmagest/saisiesRPPS.form">
			 <c:param name="pdf" value="pdf"/> </c:url>">PDF</a>-->
      </td>
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
      <td width="10%" height="60">Libell&eacute; ESPC/PPS <span style="color:#F11A0C">*</span></td>
      <td width="17%">               
               
                <form:select path="pharmSite" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${sites}">
                            <option <c:if test="${formulaireSaisiesPPS.getPharmSite().getSitId()==item.getSitId()}">selected="selected"</c:if>    				  							value="${item.getSitId()}">${item.getSitLib()} </option>
                       </c:forEach>
                </form:select>
                <form:errors path="pharmSite" cssClass="error"/>
      </td>
      <td width="1%">&nbsp;</td>
      <td width="12%">Programme <span style="color:#F11A0C">*</span></td>
      <td width="21%"><form:select path="pharmProgramme" cssStyle="width:150px">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireSaisiesPPS.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/> </td>
      <td width="1%">&nbsp;</td>
      <td width="11%">Niveau de stock Max <span style="color:#F11A0C">*</span></td>
      <td width="27%">
                 
                  <form:input path="comStockMax" /> <form:errors
														path="comStockMax" cssClass="error"/>                     
      </td>
    </tr>
    <tr>
      <td>Mois de <span style="color:#F11A0C">*</span></td>
      <td><!--<form:input path="comSitePeriodDate" /> <form:errors
														path="comSitePeriodDate" cssClass="error" /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>--><openmrs_tag:dateField formFieldName="comSitePeriodDate" startValue="${obsDate}" /></td>
      <td>&nbsp;</td>
      <td>Date de r&eacute;ception <span style="color:#F11A0C">*</span></td>
      <td><!--<form:input path="comSiteDateCom" /> <form:errors
														path="comSiteDateCom" cssClass="error"/><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>--><openmrs_tag:dateField formFieldName="comSiteDateCom" startValue="${obsDate}" /></td>
      <td>&nbsp;</td>
      <td><input type="checkbox" name="urgent" id="urgent" value="URGENT" />
        <label for="urgent"><strong>URGENT</strong></label></td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>
</c:if>

<!------------------------------------------------------------------------------------------------------->
<c:if test="${var =='1'}">
<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;">
        <input type="submit" name="reset" id="reset" value="Annuler" >
      
      </td>
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
      <td width="8%" height="60">Libell&eacute; ESPC/PPS</td>
      <td width="1%">:</td>
      <td width="19%">               
               ${formulaireSaisiesPPS.pharmSite.sitLib}
                
      </td>
      <td width="1%">&nbsp;</td>
      <td width="12%">Programme</td>
      <td width="1%">:</td>
      <td width="25%">${formulaireSaisiesPPS.pharmProgramme.programLib}
                </td>
      <td width="1%">&nbsp;</td>
      <td width="12%">Niveau de stock Max</td>
      <td width="1%">:</td>
      <td width="19%">${formulaireSaisiesPPS.comStockMax}
                 
                                     
      </td>
    </tr>
    <tr>
      <td>Mois de</td>
      <td>:</td>
      <c:set var="d" value="${formulaireSaisiesPPS.comSitePeriodDate}" scope="request" />
                                    <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
      <td><%= s %></td>
      <td>&nbsp;</td>
      <td>Date de r&eacute;ception</td>
      <td>:</td>
       <c:set var="d1" value="${formulaireSaisiesPPS.comSiteDateCom}" scope="request" />
                                    <% Date d1=  (Date) request.getAttribute("d1"); 
										SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s1 =  formatter1.format(d1);	%>
      <td><%= s1 %></td>
      <td>&nbsp;</td>
      <td><strong style="color:#FB0808"> ${formulaireSaisiesPPS.comSitePeriodLib}</strong> </td>
      <td>&nbsp;</td>
      <td></td>
    </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>

<!------------------------------------------------------------------------------------------------------->


<br>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">une ligne inser&eacute;e</div>
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
                                 <option <c:if test="${formulaireSaisiesPPS.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										                                  			value="${item.getProdId()}">${item.getProdLib()} </option>
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
                      <input type="submit" name="btn_valider"
												id="btn_valider" value="Ajouter">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
            </tr>
          </tbody>
        </table>
        <!--<table width="100%" border="0"  cellpadding="7" cellspacing="0">
             <tbody>
                <tr>
                  <td width="26%"><div align="center">D&eacute;signation</div></td>
                  <td width="10%"><div align="center">Stock initial</div></td>
                  <td width="7%"><div align="center">Quantit&eacute; re&ccedil;ue</div></td>
                  <td width="7%"><div align="center">Quantit&eacute; utilis&eacute;e</div></td>
                  <td width="8%"><div align="center">Perte</div></td>
                  <td width="8%"><div align="center">Stock disponible</div></td>
                  <td width="10%"><div align="center">Nombre de jour de rupture</div></td>
                  <td width="8%"><div align="center">Quantit&eacute; distribu&eacute;e M-1</div></td>
                  <td width="9%"><div align="center">Quantit&eacute; distribu&eacute;e M-2</div></td>
                  <td width="7%"><div align="center">Action</div></td>
                </tr>
               
								<tr>
									<td><div align="center"><form:select path="produit" cssStyle="width:250px">
									  
									    <option value="0">-- Choix --</option>
									    <c:forEach var="item" items="${produits}">
									      <option <c:if test="${formulaireSaisiesPPS.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										                                  			value="${item.getProdId()}">${item.getProdLib()} </option>
								        </c:forEach>
								      
									</form:select></div></td>
                                    <td width="10%"><div align="center">
									  <form:input path="lgnComQteIni" cssStyle="width:100px"/>
								    </div></td>
									<td width="7%"><div align="center">
									  <form:input path="lgnComQteRecu" cssStyle="width:100px" />
								    </div></td>
									<td width="7%"><div align="center">
									  <form:input path="lgnComQteUtil" cssStyle="width:100px" />
								    </div></td>
									<td width="8%"><div align="center">
									  <form:input path="lgnComPertes" cssStyle="width:100px"/>
								    </div></td>
									<td width="8%"><div align="center">
									  <form:input path="lgnComStockDispo" cssStyle="width:100px"/>
								    </div></td>
									<td width="10%"><div align="center">
									  <form:input path="lgnComNbreJrsRup" cssStyle="width:100px"/>
								    </div></td>
									<td width="8%"><div align="center">
									  <form:input path="lgnQteDistriM1" cssStyle="width:100px"/>
								    </div></td>
									<td width="9%"><div align="center">
									  <form:input path="lgnQteDistriM2" cssStyle="width:100px"/>
								    </div></td>
									<td width="7%"><div align="center">
									  <input type="submit" name="btn_valider"
												id="btn_valider" value="Ajouter">
								    </div></td>
								</tr>
			  
      </tbody>
</table>-->    
        <table width="100%" border="1"  cellpadding="7" cellspacing="0">
             <tbody>
                <tr>
                  <td width="17%">D&eacute;signation</td>
                  <td width="12%">Unit&eacute;</td>
                  <td width="12%">Stock initial</td>
                  <td width="9%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="10%">Quantit&eacute; utilis&eacute;e</td>
                  <td width="10%">Perte</td>
                  <td width="9%">Stock disponible</td>
                  <td width="12%">Nombre de jour de rupture</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-1</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-2</td>
                  <td width="7%">Action</td>
                </tr>
               <c:forEach var="lo" items="${ligneCommandeSites}">
               
                              <c:set var="cal" value="${lo.stockIni+lo.qteRecu-lo.qteUtil-lo.qtePerte}" scope="request" />
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
									<td width="17%"><div align="left">${lo.produit.prodLib}</div></td>
                                    <td width="12%">${lo.produit.prodUnite}</td>
									<td width="12%">${lo.stockIni}</td>
									<td width="9%">${lo.qteRecu}</td>
									<td width="10%"><div align="left">${lo.qteUtil}</div></td>
									<td width="10%"><div align="left">${lo.qtePerte}</div></td>
									<td width="9%"><div align="left">${lo.stockDispo}</div></td>
									<td width="12%"><div align="left">${lo.nbrJourRuptur}</div></td>
									<td width="7%">${lo.qteDistri1}</td>
									<td width="7%">${lo.qteDistri2}</td>
									<td width="7%">
                                     <a
										href="<c:url value="/module/pharmagest/saisiesRPPS.form">
									  <c:param name="modifId" value="${lo.produit.prodId}"/>                                          
								    </c:url>">Modifier</a>|
                                    <a
										href="<c:url value="/module/pharmagest/saisiesRPPS.form">
									  <c:param name="paramId" value="${lo.produit.prodId}"/>                                          
								    </c:url>">Supprimer</a>
                                   
									 
								    </td>
								</tr>
			   </c:forEach>
      </tbody>
</table>    
</div> 
	</div>
</c:if>
</form:form>