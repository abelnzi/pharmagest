<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception modif" 
        otherwise="/login.htm" redirect="/module/pharmagest/modifierReception.form" />

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

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
  			 <li class="first "><a href="<c:url value="/module/pharmagest/receptionMenu.form"/>">Menu principal</a></li>
			
    		<li >
				<a href="<c:url value="/module/pharmagest/saisieReception.form"/>">Saisies des receptions</a>
			</li>
            <li class="active">
				<a href="<c:url value="/module/pharmagest/listReceptionModif.form"/>">Modification des receptions
            </li>
            
            <li >
				<a href="<c:url value="/module/pharmagest/listReceptionValid.form"/>">Validation des receptions</a>
			</li>
            <li >
				<a href="<c:url value="/module/pharmagest/listReceptionHisto.form"/>">Historique des receptions</a>
			</li>
	
	
</ul>
</div>

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>

<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>

<script type="text/javascript">
function detail(form_element){
	setChoix(form_element);
	//alert("input choix dans detail : " + form_element.value);
	 if(form_element!=null && form_element!="") {
		 var form_element_name = document.getElementById('choix').value;
	 	 var form_element_id = document.getElementById('choix').value;
	 } else {
		 var form_element_name = "vide";
	 	 var form_element_id = "vide";
	 }
	 
	 if(form_element_name =='btn_valider_detail' || form_element_id=='radioDetail'){
		 document.getElementById('radioDetail').checked=true;
		 document.getElementById('divDetail').style.display = "block";	
		 document.getElementById('divGros').style.display = "none";	
	  } else  if(form_element_name =='btn_valider_gros' || form_element_id=='radioGros'){
		 document.getElementById('radioGros').checked=true;
		 document.getElementById('divGros').style.display = "block";
		 document.getElementById('divDetail').style.display = "none";	
	  } else {
		  document.getElementById('radioDetail').checked=true;
		  document.getElementById('divDetail').style.display = "block";	
		  document.getElementById('divGros').style.display = "none";
		  }	 
  	//alert("L’élément portant l'ID " + form_element_id + " à été cliqué !"); 
	 
}

function setChoix(form_element){
	//alert("form_element value : " + form_element.value);
   	 if(form_element!=null && form_element!="") {
		 var form_element_name = form_element.name;
	 	 var form_element_id = form_element.id;
	 } else {
		 var form_element_name = "vide";
	 	 var form_element_id = "vide";
	 }
	 if(form_element_name!="vide" && form_element.name!="choix" )document.getElementById('choix').value=form_element_name;
	 if(form_element_id!="vide" && form_element.name!="choix")document.getElementById('choix').value=form_element_id;
	 
	 //alert("Choix : " + document.getElementById('choix').value);
}

</script>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/modifierReception.form"
	modelAttribute="formulaireReception"
	commandName="formulaireReception"  >
    
<form:hidden path="choix" />

<div>
<h3 ><span style="font-size:36px">.</span> Modification de la réception de produits </h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<br>

<c:if test="${var =='0'}">

<c:if test="${mess =='aut'}">
		<div id="openmrs_msg">Vous n'etes pas autorité à effectué cette réception à la date du ${dateAut}</div>
</c:if>

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
      <td width="6%" height="33">Fournisseur</td>
      <td width="1%">:</td>
      <td width="12%"><form:select path="pharmFournisseur">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${fournisseurs}">
                            <option <c:if test="${formulaireReception.getPharmFournisseur().getFourId()==item.getFourId()}">selected="selected"</c:if>    				  							value="${item.getFourId()}">${item.getFourDesign1()} </option>
                       </c:forEach>
                </form:select>
                <form:errors path="pharmFournisseur" cssClass="error"/>
      </td>
      <td width="1%">&nbsp;</td>
      <td width="6%">Programme</td>
      <td width="1%">:</td>
      <td width="38%">${formulaireReception.pharmProgramme.programLib}
                 
      </td>
      <td width="2%">&nbsp;</td>
      <td>Type de saisie</td>
      <td><c:if test="${formulaireReception.typeReception=='Gros'}">Unité de conditionnement</c:if> 
          <c:if test="${formulaireReception.typeReception=='Detail'}">Unité de dispensation</c:if> </td>
      </tr>
    <tr>
      <td>Bordereau de livraison</td>
      <td>:</td>
      <td><form:input path="recptNum" /> <form:errors
														path="recptNum" cssClass="error" /></td>
      <td>&nbsp;</td>
      <td>Date de la livraison</td>
      <td>:</td>
       
      <td>
      
        <form:input path="recptDateRecept"  /><br><i style="font-weight: normal; font-size: 0.8em;">(Format: jj/mm/aaaa)</i>
        <form:errors path="recptDateRecept" cssClass="error" />
       
	  
	  </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
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
<c:if test="${mess =='delete'}">
		<div id="openmrs_msg">une ligne supprim&eacute;e</div>
</c:if>


<!--<div align="center">
   <input type="radio" name="choix_saisie" id="radioDetail" value="detail" onclick=" detail(this)"/><label for="radioDetail">Saisie au D&eacute;tail</label>
    <input type="radio" name="choix_saisie" id="radioGros" value="gros" onclick=" detail(this)" /><label for="radioGros">Saisie en Unit&eacute; de r&eacute;ception</label>
</div>-->
<br>
<c:if test="${formulaireReception.typeReception =='Detail'}">
   <div name="divDetail" id="divDetail">
	<b class="boxHeader"></b>
	<div class="box">
	  <table width="100%" border="0" cellpadding="7" cellspacing="0" >
	    <tbody>
	      <tr>
	        <td width="1%"></td>
	        <td width="44%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">D&eacute;signation <span style="color:#F11A0C">*</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:select path="produitDetail" cssStyle="width:300px">
	                  <option value="0">-- Choix --</option>
	                  <c:forEach var="item" items="${produits}">
                          <option <c:if test="${formulaireReception.getProduit().getProdId()==item.getPharmProduit().getProdId()}">selected="selected"</c:if>    				  										                                value="${item.getPharmProduit().getProdId()}">${item.getFullGrosName()} </option>
                          </c:forEach>
	                  </form:select>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="produitDetail" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="10%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Num&eacute;ro du lot<span style="color:#F11A0C"> *</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptLotDetail" cssStyle="width:100px"/>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptLotDetail" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="8%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">P&eacute;remption<span style="color:#F11A0C"> *</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <!--<form:input path="lgnDatePerem" />-->
	                <openmrs_tag:dateField formFieldName="lgnDatePeremDetail" startValue="${obsDate}" />
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnDatePeremDetail" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="10%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Quantit&eacute; d&eacute;tail livr&eacute;e <span style="color:#F11A0C">*</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptQteDetailLivree" cssStyle="width:100px"/>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptQteDetailLivree" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="10%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Quantit&eacute; d&eacute;tail re&ccedil;ue<span style="color:#F11A0C"> *</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptQteDetailRecu" cssStyle="width:100px"/>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptQteDetailRecu" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="9%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Prix unitaire bordereau</div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptPrixAchatDetail" cssStyle="width:100px"/>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptPrixAchatDetail" cssClass="error" />
	                </div></td>
	              </tr>
	            <tr>
	              <td></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="11%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Observation</div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:textarea path="lgnRecptObservDetail" rows="4"
									cols="20" />
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptObservDetail" cssClass="error" />
	                </div></td>
	              </tr>
	            <tr>
	              <td></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="8%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center"></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <input type="submit" name="btn_valider_detail"
												id="btn_valider_detail" value="Ajouter" onclick="setChoix(this)">
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
                  <td width="20%">Code article</td>
                   <td width="7%">D&eacute;signation</td>
                  <td width="7%">Unit&eacute;</td>
                  <td width="7%">Num&eacute;ro du lot</td>
                  <td width="7%">P&eacute;remption</td>
                  <td width="7%">Quantit&eacute; d&eacute;tail livr&eacute;e</td>
                  <td width="7%">Quantit&eacute; d&eacute;tail re&ccedil;ue</td>
                  <td width="7%">Prix d'achat</td>
                  <td width="7%">Observation</td>
                  <!--<td width="7%">Quantit&eacute; propos&eacute;e</td>-->
                  <td width="7%">Action</td>
                </tr>
                <% int x = 1;  %>	
             <c:forEach var="lo" items="${ligneReceptions}">
								<tr id="tr<%= x %>">
									<td><div align="left">${lo.produit.prodCode}</div></td>
                                    <td><div align="left">${lo.produit.prodLib}</div></td>
									<td><div align="left">${lo.produit.prodUnite}</div>
                                    </td>
									<td><input type="text" name="numLot<%= x %>" id="numLot<%= x %>" style="width:100px"
                                    				value="${lo.lgnRecptLotDetail}" readonly=true ></td>
                                                    
                                    <c:set var="d2" value="${lo.lgnDatePeremDetail}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2"); 
										SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");	
										//System.out.println("----------- peremption 2---------" +d2);
										String s2="";						
										if(d2!=null)  s2 =  formatter2.format(d2); %>
                                        
									<td><div align="left"><input type="text" name="peremption<%= x %>" id="peremption<%= x %>"  style="width:100px"
                                    				value="<%= s2 %>"></div>
                                    
                                    </td>
									<td><input type="number" name="qtelivre<%= x %>" id="qtelivre<%= x %>" style="width:100px"
                                    				value="${lo.lgnRecptQteDetailLivree}"></td>
									<td><div align="left"><input type="number" name="qte<%= x %>" id="qte<%= x %>" style="width:100px"
                                    				value="${lo.lgnRecptQteDetailRecu}"></div></td>
									<td><div align="left"><input type="number" name="prix<%= x %>" id="prix<%= x %>"  style="width:100px"
                                    				value="${lo.lgnRecptPrixAchatDetail}"></div></td>
									<td><div align="left">
									  								 
                                      <textarea name="observ<%= x %>" id="observ<%= x %>" rows="2" cols="20" >${lo.lgnRecptObservDetail}</textarea>
									  </div></td>
									<td>
                                    	<a
										href="<c:url value="/module/pharmagest/modifierReception.form">
									  <c:param name="supprimId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								      </c:url>" onClick=" detail()">Supprimer</a>
                                    </td>
									<input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${lo.produit.prodId}">
                                    
								</tr>
            
                <% x = x+1;  %> 
                
			   </c:forEach>
      </tbody>
</table>      
</div> 
	</div>
</c:if>
<!------------------------------------------------------- Gestion des gros------------------------------------------------------------>

<c:if test="${formulaireReception.typeReception =='Gros'}">
<div name="divGros" id="divGros">
	<b class="boxHeader"></b>
	<div class="box">
	  <table width="100%" border="0" cellpadding="7" cellspacing="0" >
	    <tbody>
	      <tr>
	        <td width="1%"></td>
	        <td width="44%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">D&eacute;signation <span style="color:#F11A0C">*</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:select path="produit" cssStyle="width:300px">
	                  <option value="0">-- Choix --</option>
	                  <c:forEach var="item" items="${produits}">
                          <option <c:if test="${formulaireReception.getProduit().getProdId()==item.getPharmProduit().getProdId()}">selected="selected"</c:if>    				  										                                value="${item.getPharmProduit().getProdId()}">${item.getFullGrosName()} </option>
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
	        <td width="10%"><table width="100%" border="0" cellspacing="0">
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
	              <td><div align="center">
	                <form:errors path="lgnRecptLot" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="8%"><table width="100%" border="0" cellspacing="0">
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
	              <td><div align="center">
	                <form:errors path="lgnDatePerem" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="10%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Quantit&eacute; livr&eacute;e <span style="color:#F11A0C">*</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptQteLivree" cssStyle="width:100px"/>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptQteLivree" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="10%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Quantit&eacute; re&ccedil;ue<span style="color:#F11A0C"> *</span></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptQte" cssStyle="width:100px"/>
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptQte" cssClass="error" />
	                </div></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="9%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Prix unitaire bordereau</div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:input path="lgnRecptPrixAchat" cssStyle="width:100px"/>
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
	        <td width="11%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center">Observation</div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:textarea path="lgnRecptObserv" rows="4"
									cols="20" />
	                </div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <form:errors path="lgnRecptObserv" cssClass="error" />
	                </div></td>
	              </tr>
	            <tr>
	              <td></td>
	              </tr>
	            </tbody>
	          </table></td>
	        <td width="8%"><table width="100%" border="0" cellspacing="0">
	          <tbody>
	            <tr>
	              <td><div align="center"></div></td>
	              </tr>
	            <tr>
	              <td><div align="center">
	                <input type="submit" name="btn_valider_gros"
												id="btn_valider_gros" value="Ajouter" onclick="setChoix(this)">
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
                  <td width="20%">Code article</td>
                   <td width="7%">D&eacute;signation</td>
                  <td width="7%">Unit&eacute;</td>
                  <td width="7%">Num&eacute;ro du lot</td>
                  <td width="7%">P&eacute;remption</td>
                  <td width="7%">Quantit&eacute; livr&eacute;e</td>
                  <td width="10%">Quantit&eacute; d&eacute;tail livr&eacute;e</td>
                  <td width="7%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="10%">Quantit&eacute; d&eacute;tail re&ccedil;ue</td>
                  <td width="7%">Prix d'achat</td>
                  <td width="7%">Observation</td>
                  <!--<td width="7%">Quantit&eacute; propos&eacute;e</td>-->
                  <td width="7%">Action</td>
                </tr>
                <% int y = 1;  %>	
             <c:forEach var="lo" items="${ligneReceptions}">
								<tr id="tr<%= y %>">
									<td><div align="left">${lo.produit.prodCode}</div></td>
                                    <td><div align="left">${lo.produit.prodLib}</div></td>
									<td><div align="left">${lo.produit.prodUnite}</div>
                                    </td>
									<td><input type="text" name="numLotGros<%= y %>" id="numLotGros<%= y %>" style="width:100px"
                                    				value="${lo.lgnRecptLot}" readonly=true></td>
                                                    
                                    <c:set var="d2" value="${lo.lgnDatePerem}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2"); 
										SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");	
										String s2="";						
										if(d2!=null)  s2 =  formatter2.format(d2); %>
                                        
									<td><div align="left"><input type="text" name="peremptionGros<%= y %>" id="peremptionGros<%= y %>"  style="width:100px"
                                    				value="<%= s2 %>"></div>
                                    
                                    </td>
									<td><input type="number" name="qtelivreGros<%= y %>" id="qtelivreGros<%= y %>" style="width:100px"
                                    				value="${lo.lgnRecptQteLivree}"></td>
									<td width="9%"><div align="left">${lo.lgnRecptQteDetailLivree}</div></td>
									<td><div align="left"><input type="number" name="qteGros<%= y %>" id="qteGros<%= y %>" style="width:100px"
                                    				value="${lo.lgnRecptQte}"></div></td>
									<td width="9%"><div align="left">${lo.lgnRecptQteDetailRecu}</div></td>
									<td><div align="left"><input type="number" name="prixGros<%= y %>" id="prixGros<%= y %>"  style="width:100px"
                                    				value="${lo.lgnRecptPrixAchat}"></div></td>
									<td><div align="left">
									  								 
                                      <textarea name="observGros<%= y %>" id="observGros<%= y %>" rows="2" cols="20" >${lo.lgnRecptObserv}</textarea>
									  </div></td>
									<td>
                                    	<a
										href="<c:url value="/module/pharmagest/modifierReception.form">
									  <c:param name="supprimId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								      </c:url>">Supprimer</a>
                                    </td>
									<input type="hidden" name="idProdGros<%= y %>" id="idProdGros<%= y %>" value="${lo.produit.prodId}">
                                    
								</tr>
            
                <% y = y+1;  %> 
                
			   </c:forEach>
      </tbody>
</table>      
</div> 
</div>
</c:if>


</c:if>

<!--------------------------------------------------------------------------------------------------------------------------------------->

<c:if test="${var =='1'}">

<div>

	
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="14%" height="33">Fournisseur</td>
		        <td width="1%">:</td>
		        <td width="29%">${formulaireReception.pharmFournisseur.fourDesign1} </td>
		        <td width="1%">&nbsp;</td>
		        <td width="13%">Programme</td>
		        <td width="1%">:</td>
		        <td width="41%">${formulaireReception.pharmProgramme.programLib} </td>
	          </tr>
		      <tr>
		        <td>Bordereau de livraison</td>
		        <td>:</td>
		        <td>${formulaireReception.recptNum}</td>
		        <td>&nbsp;</td>
		        <td>Date de la livraison</td>
		        <td>:</td>
		        <c:set var="d" value="${formulaireReception.recptDateRecept}" scope="request" />
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
<c:if test="${mess =='valid'}"></c:if>
<c:if test="${mess =='delete'}"></c:if>
<!--<div align="left">${info}</div>-->
<br>
        <div>
	<b class="boxHeader"></b>
	<div class="box">
	  <table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="20%">Code article</td>
                   <td width="7%">D&eacute;signation</td>
                  <td width="7%">Unit&eacute;</td>
                  <td width="7%">Num&eacute;ro du lot</td>
                  <td width="7%">P&eacute;remption</td>
                  <td width="7%">Quantit&eacute; livr&eacute;e</td>
                  <td width="10%">Quantit&eacute; d&eacute;tail livr&eacute;e</td>
                  <td width="7%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="10%">Quantit&eacute; d&eacute;tail re&ccedil;ue</td>
                  <td width="7%">Prix unitaire bordereau</td>
                  <td width="14%">Total</td>
                  <td width="14%">Observation</td>
                  <!--<td width="7%">Quantit&eacute; propos&eacute;e</td>-->                </tr>
               	
             <c:forEach var="lo" items="${ligneReceptions}">
								<tr>
									<td><div align="left">${lo.produit.prodCode}</div></td>
                                    <td><div align="left">${lo.produit.prodLib}</div></td>
									<td><div align="left">${lo.produit.prodUnite}</div>
                                    </td>
									<td>${lo.lgnRecptLot}</td>
                                                    
                                    <c:set var="d2" value="${lo.lgnDatePerem}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2"); 
										SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");	
										String s2="";						
										if(d2!=null)  s2 =  formatter2.format(d2); %>
                                        
									<td><div align="left"><%= s2 %></div>
                                    
                                    </td>
									<td>${lo.lgnRecptQteLivree}</td>
									<td width="10%"><div align="left">${lo.lgnRecptQteDetailLivree}</div></td>
									<td><div align="left">${lo.lgnRecptQte}</div></td>
									<td width="10%"><div align="left">${lo.lgnRecptQteDetailRecu}</div></td>
									<td><div align="left">${lo.lgnRecptPrixAchat}</div></td>
									<td width="14%">${lo.lgnRecptQte*lo.lgnRecptPrixAchat}</td>
									<td width="14%"><div align="left">${lo.lgnRecptObserv}</div></td>
									
                                    
								</tr>
                                 </c:forEach>
								<tr>
								  <td colspan="10"><div align="right"><strong>Total global</strong></div></td>
								  <td><strong>${totalGlobal}</strong></td>
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
	document.getElementById("lgnDatePeremDetail").value="${datePerem}";
		
	//var form_element = document.getElementById('choix');	
	//var result=(form_element==null || form_element=="undefined") ? null: form_element;
	//alert("resultat "+ result.value);	
	//window.onload=detail(document.getElementById('choix'));
</script>

</form:form>