<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception saisie" 
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

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
	
  			 <li class="first "><a href="<c:url value="/module/pharmagest/receptionMenu.form"/>">Menu principal</a></li>
			
    		<li class="active">
				<a href="<c:url value="/module/pharmagest/saisieReception.form"/>">Saisies des receptions</a>
			</li>
            <li >
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
	//alert("input choix dans detail : " + document.getElementById('choix').value);
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
	action="${pageContext.request.contextPath}/module/pharmagest/saisieReception.form"
	modelAttribute="formulaireReception"
	commandName="formulaireReception" >
    
<form:hidden path="choix" />

<div>
<h3 ><span style="font-size:36px">.</span> Réception de produits </h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<c:if test="${mess =='aut'}">
		<div id="openmrs_msg">Vous n'etes pas autorité à effectué cette réception à la date du ${dateAut}</div>
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
      <td width="12%" height="60">Fournisseur <span style="color:#F11A0C">*</span></td>
      <td width="18%">               
               
                <form:select path="pharmFournisseur">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${fournisseurs}">
                            <option <c:if test="${formulaireReception.getPharmFournisseur().getFourId()==item.getFourId()}">selected="selected"</c:if>    				  							value="${item.getFourId()}">${item.getFourDesign1()} </option>
                       </c:forEach>
                </form:select>
                <form:errors path="pharmFournisseur" cssClass="error"/>
      </td>
      <td width="1%">&nbsp;</td>
      <td width="13%">Programme <span style="color:#F11A0C">*</span></td>
      <td width="17%">
                 
                <form:select path="pharmProgramme">
                      <option value="0">-- Choix --</option>
                       <c:forEach var="item" items="${programmes}">
                            <option <c:if test="${formulaireReception.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
                       </c:forEach>
                </form:select> 
                <form:errors path="pharmProgramme" cssClass="error"/>                        
      </td>
      <td width="1%">&nbsp;</td>
      <td width="11%">Type de saisie</td>
      <td width="27%">
      	<form:radiobutton path="typeReception" value="Detail"/>Unité de dispensation
        <form:radiobutton path="typeReception" value="Gros"/>Unité de conditionnement
        <form:errors path="typeReception" cssClass="error"/> 
      &nbsp;</td>
    </tr>
    <tr>
      <td>Bordereau de livraison</td>
      <td><form:input path="recptNum" /> <form:errors
														path="recptNum" cssClass="error" /></td>
      <td>&nbsp;</td>
      <td>Date de la livraison <span style="color:#F11A0C">*</span></td>
      <td> <openmrs_tag:dateField formFieldName="recptDateRecept" startValue="${obsDate}" /><form:errors path="recptDateRecept" cssClass="error"/></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
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
      <td width="13%" height="33">Fournisseur</td>
      <td width="1%">:</td>
      <td width="25%">${formulaireReception.pharmFournisseur.fourDesign1}               
               
      </td>
      <td width="1%">&nbsp;</td>
      <td width="10%">Programme</td>
      <td width="1%">:</td>
      <td width="25%">${formulaireReception.pharmProgramme.programLib}
        
      </td>
      <td width="1%">&nbsp;</td>
      <td width="9%">Type de saisie</td>
      <td width="14%">
          <c:if test="${formulaireReception.typeReception=='Gros'}">Unité de conditionnement</c:if> 
          <c:if test="${formulaireReception.typeReception=='Detail'}">Unité de dispensation</c:if> 
      </td>
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
   <input type="radio" name="choix_saisie" id="radioDetail" value="Detail" onclick=" detail(this)"/><label for="radioDetail">Saisie au D&eacute;tail</label>
    <input type="radio" name="choix_saisie" id="radioGros" value="Gros" onclick=" detail(this)" /><label for="radioGros">Saisie en Unit&eacute; de r&eacute;ception</label>
</div>-->
<br>
<c:if test="${formulaireReception.typeReception =='Detail'}">
<div name="divDetail" id="divDetail">
	<b class="boxHeader"></b>
	<div class="box">
        
        <table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
        
                <tr style="border:0px">
                  <td colspan="3">
                   <form:select path="produitDetail" cssStyle="width:300px">
                        <option value="0">-- Choix --</option>
                        <c:forEach var="item" items="${produits}">
                          <option <c:if test="${formulaireReception.getProduit().getProdId()==item.getPharmProduit().getProdId()}">selected="selected"</c:if>    				  										                                value="${item.getPharmProduit().getProdId()}">${item.getFullGrosName()} </option>
                          </c:forEach>
                        </form:select> 
                        <form:errors path="produitDetail" cssClass="error" />
                  &nbsp;</td>
                  <td style="border-left-color:#fff">&nbsp;
                  <div align="center">Num&eacute;ro du lot<span style="color:#F11A0C"> *</span><br />
                   <form:input path="lgnRecptLotDetail" cssStyle="width:100px"/> </div>
                  </td>
                  <td style="border-left-color:#fff; border:0px">&nbsp;
                  <div align="center">P&eacute;remption<span style="color:#F11A0C"> *</span><br />
                    <!--<form:input path="lgnDatePerem" />-->
					<openmrs_tag:dateField formFieldName="lgnDatePeremDetail" startValue="${obsDate}" />	</div>
                  </td>
                  <td>&nbsp;
                  <div align="center">Quantit&eacute; d&eacute;tail livr&eacute;e <span style="color:#F11A0C"> *</span><br />
                 <form:input path="lgnRecptQteDetailLivree" cssStyle="width:100px"/></div>
                  </td>
                  <td>&nbsp;
                  <div align="center">Quantit&eacute; d&eacute;tail re&ccedil;ue<span style="color:#F11A0C"> *</span><br />
                   <form:input path="lgnRecptQteDetailRecu" cssStyle="width:100px"/></div>
                  </td>
                  <td>&nbsp;
                  <div align="center">Prix d'achat<br />
                   <form:input path="lgnRecptPrixAchatDetail" cssStyle="width:100px"/></div>
                  </td>
                  <td>&nbsp;
                  <div align="center">Observation<br />
                  <form:textarea path="lgnRecptObservDetail" rows="4" cols="20" /></div>
                  </td>
                  <td>&nbsp;<input type="submit" name="btn_valider_detail" id="btn_valider_detail" value="Ajouter" ></td>
                </tr>
                
                <tr>
                  <td width="12%">Code article</td>
                  <td width="20%">D&eacute;signation</td>
                  <td width="11%">Unit&eacute;</td>
                  <td width="16%">Num&eacute;ro du lot</td>
                  <td width="10%">P&eacute;remption</td>
                  <td width="10%">Quantit&eacute; livr&eacute;e</td>
                  <td width="10%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="12%">Prix d'achat</td>
                  <td width="14%">Observation</td>
                  <td width="14%">Action</td>
                </tr>
                <c:forEach var="lo" items="${ligneReceptions}">
								<tr>
									<td width="12%"><div align="left">${lo.produit.prodCode}</div></td>
									<td width="20%"><div align="left">${lo.produit.prodLib}</div></td>
									<td width="11%">${lo.produit.prodUnite}</td>
									<td width="16%"><div align="left">${lo.lgnRecptLotDetail}</div></td>
                                    <c:set var="d2" value="${lo.lgnDatePeremDetail}" scope="request" />
                                    <% Date d2=  (Date) request.getAttribute("d2"); 
										SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
										String s2="";
										if(d2!=null) s2 =  formatter2.format(d2); 
									%>
									<td width="10%"><div align="left"><%= s2 %></div></td>
									<td width="10%"><div align="left">${lo.lgnRecptQteDetailLivree}</div></td>
									<td width="10%"><div align="left">${lo.lgnRecptQteDetailRecu}</div></td>
									<td width="12%"><div align="left">${lo.lgnRecptPrixAchatDetail}</div></td>
									<td width="14%"><div align="left">${lo.lgnRecptObservDetail}</div></td>
									<td width="14%">
                                    <a
										href="<c:url value="/module/pharmagest/saisieReception.form">
									  <c:param name="modifId" value="${lo.produit.prodId}${lo.lgnRecptLotDetail}"/>                                          
								    </c:url>" name="btn_valider_detail" onClick=" detail()">Modifier</a>|
                                    <a
										href="<c:url value="/module/pharmagest/saisieReception.form">
									  <c:param name="paramId" value="${lo.produit.prodId}${lo.lgnRecptLotDetail}"/>                                          
								    </c:url>"  name="btn_valider_detail" onClick="detail()">Supprimer</a>
                                   
									 
								    </td>
								</tr>
			   </c:forEach>
      </tbody>
</table>          
</div> 
</div>
</c:if>
<c:if test="${formulaireReception.typeReception =='Gros'}">
<div name="divGros" id="divGros">
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
															value="${formulaireReception.produit.prodCode}" size="15" readonly />
                      </div></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div>--></td>
              <td width="36%"><table width="100%" border="0" cellspacing="0">
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
                    <td><div align="center"><form:errors path="produit" cssClass="error" />
                      </div></td>
                    </tr>
                  </tbody>
              </table></td>
              <td width="9%"><table width="100%" border="0" cellspacing="0">
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
              <td width="7%"><table width="100%" border="0" cellspacing="0">
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
              <td width="9%"><table width="100%" border="0" cellspacing="0">
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
                    <td><div align="center"><form:errors path="lgnRecptQte" cssClass="error" /></div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="10%"><table width="100%" border="0" cellspacing="0">
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
              <td width="8%">
              <table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center"></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="submit" name="btn_valider_gros"
												id="btn_valider_gros" value="Ajouter" onClick=" setChoix(this)">
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
                  <td width="10%">Quantit&eacute; livr&eacute;e</td>
                  <td width="10%">Quantit&eacute; d&eacute;tail livr&eacute;e</td>
                  <td width="10%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="10%">Quantit&eacute; d&eacute;tail re&ccedil;ue</td>
                  <td width="10%">Prix d'achat</td>
                  <td width="14%">Observation</td>
                  <td width="14%">Action</td>
                </tr>
                <c:forEach var="lo" items="${ligneReceptions}">
								<tr>
									<td width="12%"><div align="left">${lo.produit.prodCode}</div></td>
									<td width="16%"><div align="left">${lo.produit.prodLib}</div></td>
									<td width="11%">${lo.produit.prodUnite}</td>
									<td width="16%"><div align="left">${lo.lgnRecptLot}</div></td>
                                    <c:set var="d3" value="${lo.lgnDatePerem}" scope="request" />
                                    <% Date d3=  (Date) request.getAttribute("d3"); 
										SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy");
										String s3="";
										if(d3!=null) s3 =  formatter3.format(d3); 
									 %>
									<td width="10%"><div align="left"><%= s3 %></div></td>
									<td width="9%"><div align="left">${lo.lgnRecptQteLivree}</div></td>
									<td width="9%"><div align="left">${lo.lgnRecptQteDetailLivree}</div></td>
									<td width="9%"><div align="left">${lo.lgnRecptQte}</div></td>
									<td width="9%"><div align="left">${lo.lgnRecptQteDetailRecu}</div></td>
									<td width="12%"><div align="left">${lo.lgnRecptPrixAchat}</div></td>
									<td width="14%"><div align="left">${lo.lgnRecptObserv}</div></td>
									<td width="14%">
                                    <a
										href="<c:url value="/module/pharmagest/saisieReception.form">
									  <c:param name="modifId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								    </c:url>" name="btn_valider_gros" onClick=" setChoix(this)">Modifier</a>|
                                    <a
										href="<c:url value="/module/pharmagest/saisieReception.form">
									  <c:param name="paramId" value="${lo.produit.prodId}${lo.lgnRecptLot}"/>                                          
								    </c:url>" name="btn_valider_gros" onClick="setChoix(this)">Supprimer</a>
                                   
									 
								    </td>
								</tr>
			   </c:forEach>
      </tbody>
</table>      
</div> 
</div>
</c:if>

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

