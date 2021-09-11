<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie distribution traitement" 
        otherwise="/login.htm" redirect="/index.htm" />
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<script type="text/javascript">
	var $ = jQuery.noConflict();
</script>
<!--<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>-->


<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>

<div class="box" style="margin:0px; width:100%;background-color:#FCD7DB" >
<ul id="menu">
	
    
			<li class="first "><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Menu principal</a></li>
			
    		<li >
				<a href="<c:url value="/module/pharmagest/saisiesRPPS.form"/>">Saisies des rapports des PPS</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/importRapportConso.form"/>">Importation des rapports des ESPC/PPS</a>
            </li>
             <li class="active">
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


<form:form method="post" name="myForm"
	action="${pageContext.request.contextPath}/module/pharmagest/modifierPPS.form"
	modelAttribute="formulaireModif"
	commandName="formulaireModif"  >
<div>
<h3 ><span style="font-size:36px">.</span>Modification de la commande N&deg; : ${commandeSite.comSiteCode}</h3>
</div>
<br>
<c:if test="${mess =='save'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<c:if test="${mess =='valid'}">
		<div id="openmrs_msg">Valider avec succès</div>
</c:if>
<br>
<br>
<!----------------------------------entête-------------------------------------------------------------------->

 

<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;" ${validAt}>
 <input type="submit" name="btn_valider" id="btn_valider" value="Valider" onClick="return confirm('Voulez vous Valider?') ? true : false;" ${validAt}>
 <input type="submit" name="btn_supprimer" id="btn_supprimer" value="Supprimer" onClick="return confirm('Voulez vous Supprimer?') ? true : false;" ${validAt}>
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
               ${formulaireModif.pharmSite.sitLib}
                
      </td>
      <td width="1%">&nbsp;</td>
      <td width="12%">Programme</td>
      <td width="1%">:</td>
      <td width="25%">${formulaireModif.pharmProgramme.programLib}
                </td>
      <td width="1%">&nbsp;</td>
      <td width="12%">Niveau de stock Max</td>
      <td width="1%">:</td>
      <td width="19%">${formulaireModif.comStockMax}
                 
                                     
      </td>
    </tr>
    <tr>
      <td>Mois de</td>
      <td>:</td>
      <c:set var="d" value="${formulaireModif.comSitePeriodDate}" scope="request" />
                                    <% Date d=  (Date) request.getAttribute("d"); 
										SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										
										String s =  formatter.format(d);	%>
      <td><%= s %></td>
      <td>&nbsp;</td>
      <td>Date de r&eacute;ception</td>
      <td>:</td>
       <c:set var="d1" value="${formulaireModif.comSiteDateCom}" scope="request" />
                                    <% Date d1=  (Date) request.getAttribute("d1"); 
										SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
										
										String s1 =  formatter1.format(d1);	%>
      <td><%= s1 %></td>
      <td>&nbsp;</td>
      <td><strong style="color:#FB0808"> ${formulaireModif.comSitePeriodLib}</strong></td>
      <td>&nbsp;</td>
      <td></td>
    </tr>
  </tbody>
</table>
	  </div>
	</div>

</div>

<!-------------------------------------------------------------------------------------------------->


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
    <c:if test="${tab =='1'}">

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
                                 <option <c:if test="${formulaireModif.getProduit().getProdId()==item.getProdId()}">selected="selected"</c:if>    				  										                                  			value="${item.getProdId()}">${item.getProdLib()} </option>
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
                      <input type="submit" name="btn_ajouter"
												id="btn_ajouter" value="Ajouter">
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
                  <td width="20%">D&eacute;signation</td>
                   <td width="7%">Unit&eacute;</td>
                  <td width="7%">Stock initial</td>
                  <td width="7%">SDU N-1</td>
                  <td width="7%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="7%">Quantit&eacute; livr&eacute;e</td>
                  <td width="7%">Quantit&eacute; utilis&eacute;e</td>
                  <td width="7%">Perte</td>
                  <td width="7%">Stock disponible</td>
                  <td width="7%">Nombre de jour de rupture</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-1</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-2</td>
                  <!--<td width="7%">Quantit&eacute; propos&eacute;e</td>-->
                  <td width="7%">Action</td>
                </tr>
                <% int x = 1;  %>	
                <c:forEach var="lo" items="${ligneCommandeSites}">
                
                 <c:set var="cal" value="${lo.stockIni+lo.qteRecu-lo.qteUtil-lo.qtePerte}" scope="request" />
                 <c:set var="dispo" value="${lo.stockDispo}" scope="request" />
                 <c:set var="sduN1" value="${lo.sduN1}" scope="request" />
                 <c:set var="stockIni" value="${lo.stockIni}" scope="request" />
                  <c:set var="ql" value="${lo.ql}" scope="request" />
                  <c:set var="qteRecu" value="${lo.qteRecu}" scope="request" />
                  
                    <% Long cal=  (Long) request.getAttribute("cal");
                        Integer dispo=  (Integer) request.getAttribute("dispo"); 
            
                        String attri ="";
                       
                        if(!(cal==dispo.longValue())){
                             attri = "color:#F3F3F3;background:#F70606";
                             
                           }
						  
						  Integer sduN1=  (Integer) request.getAttribute("sduN1");
						  Integer ql=  (Integer) request.getAttribute("ql");
						  sduN1=(sduN1==null)?0:sduN1;
						  ql=(ql==null)?0:ql;
						  Integer stockIni=  (Integer) request.getAttribute("stockIni");
						  Integer qteRecu=  (Integer) request.getAttribute("qteRecu");
						  
						  String tdColor="";
						  if( (sduN1-stockIni)!=0 || (ql-qteRecu)!=0){
                             tdColor = "color:#F3F3F3;background:#F70606";                             
                           }else{
							    tdColor = ""; 
							   }
						   
                                                    
                     %>
                
								<tr id="tr<%= x %>" style="<%= attri %>">
									<td><div align="left">${lo.produit.fullName}</div></td>
                                    <td>${lo.produit.prodUnite}</td>
									<td>
                                    <input type="number" name="qteIni<%= x %>" id="qteIni<%= x %>"  style="width:100px"
                                    				value="${lo.stockIni}">
                                    </td>
                                    <td id="tdSDU<%= x %>" style="<%= tdColor %>">${lo.sduN1}</td>
									<td><input type="number" name="qteRecu<%= x %>" id="qteRecu<%= x %>" style="width:100px"
                                    				value="${lo.qteRecu}"></td>
                                    <td id="tdQL<%= x %>" style="<%= tdColor %>">${lo.ql}</td>
									<td><div align="left"><input type="number" name="qteUtil<%= x %>" id="qteUtil<%= x %>"  style="width:100px"
                                    				value="${lo.qteUtil}"></div></td>
									<td><div align="left"><input type="number" name="qtePerte<%= x %>" id="qtePerte<%= x %>" style="width:100px"
                                    				value="${lo.qtePerte}"></div></td>
									<td><div align="left"><input type="number" name="stockDispo<%= x %>" id="stockDispo<%= x %>"  style="width:100px"
                                    				value="${lo.stockDispo}"></div></td>
									<td><div align="left"><input type="number" name="nbrJourRuptur<%= x %>" id="nbrJourRuptur<%= x %>"  style="width:100px"
                                    				value="${lo.nbrJourRuptur}"></div></td>
									<td><input type="number" name="qteDistri1<%= x %>" id="qteDistri1<%= x %>"  style="width:100px"
                                    				value="${lo.qteDistri1}"></td>
									<td><input type="number" name="qteDistri2<%= x %>" id="qteDistri2<%= x %>" style="width:100px"
                                    				value="${lo.qteDistri2}"></td>
                                    <c:set var="n" value="${((lo.qteDistri2+lo.qteDistri1+lo.qteUtil)/3)*2-(lo.stockDispo)}" scope="request" />
                                    <% double n= ((Double) request.getAttribute("n")).intValue(); 
									 int qteProInt= (int) Math.ceil(n);
									%>
                                    
									<!--<td ><%= qteProInt %></td>-->
									<td>
                                    	<a
										href="<c:url value="/module/pharmagest/modifierPPS.form">
									  <c:param name="supprimId" value="${lo.produit.prodId}"/>                                          
								      </c:url>">Supprimer</a>
                                    </td>
									<input type="hidden" name="idProd<%= x %>" id="idProd<%= x %>" value="${lo.produit.prodId}">
                                    
								</tr>
          <script type="text/javascript">
						
			function clickAction<%= x %>() {
				var qteIni=parseInt(document.getElementById("qteIni<%= x %>").value );
				var qteRecu=parseInt(document.getElementById("qteRecu<%= x %>").value );
				var qteUtil=parseInt(document.getElementById("qteUtil<%= x %>").value );
				var qtePerte=parseInt(document.getElementById("qtePerte<%= x %>").value );
				var stockDispo=parseInt(document.getElementById("stockDispo<%= x %>").value );
				//alert('avant le if');
			if((qteIni+qteRecu-qteUtil-qtePerte)!=stockDispo) {
				//alert('apres le if');
				//document.getElementById("btn_enregistrer").setAttribute("disabled","true");
				//document.getElementById("btn_valider").setAttribute("disabled","true");
				
				document.getElementById("tr<%= x %>").setAttribute("style","color:#F3F3F3;background:#F70606");
					
				}else{
				
				document.getElementById("tr<%= x %>").setAttribute("style","color:inherit");
				}
				
				 var sduN1 = parseInt(<%= sduN1 %>);
				 var ql = parseInt(<%= ql %>);
				  
				 if( (sduN1-qteIni)!=0||(ql-qteRecu)!=0 ){
					 document.getElementById("tdSDU<%= x %>").setAttribute("style","color:#F3F3F3;background:#F70606");
					 document.getElementById("tdQL<%= x %>").setAttribute("style","color:#F3F3F3;background:#F70606");
				}
				  
			
			}
			
			document.getElementById("stockDispo<%= x %>").onchange = function() {clickAction<%= x %>()};
			document.getElementById("qteIni<%= x %>").onchange = function() {clickAction<%= x %>()};
			document.getElementById("qteRecu<%= x %>").onchange = function() {clickAction<%= x %>()};
			document.getElementById("qteUtil<%= x %>").onchange = function() {clickAction<%= x %>()};
			document.getElementById("qtePerte<%= x %>").onchange = function() {clickAction<%= x %>()};
		  </script>
            
                <% x = x+1;  %> 
                
			   </c:forEach>
      </tbody>
</table>

</c:if>
<c:if test="${tab =='2'}">
<table width="100%" border="1"  cellpadding="7" cellspacing="0">
           <tbody>
                <tr>
                  <td width="17%">D&eacute;signation</td>
                  <td width="7%">Stock initial</td>
                  <td width="7%">Quantit&eacute; re&ccedil;ue</td>
                  <td width="7%">Quantit&eacute; utilis&eacute;e</td>
                  <td width="7%">Perte</td>
                  <td width="7%">Stock disponible</td>
                  <td width="7%">Nombre de jour de rupture</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-1</td>
                  <td width="7%">Quantit&eacute; distribu&eacute;e M-2</td>
                  <!--<td width="7%">Quantit&eacute; approuv&eacute;e</td>-->
                </tr>
                
                <c:forEach var="lo" items="${pharmLigneCommandeSites}">
								<tr>
									<td width="17%"><div align="left">${lo.pharmProduit.prodLib}</div></td>
									<td width="12%">${lo.lgnComQteIni}</td>
									<td width="9%">${lo.lgnComQteRecu}</td>
									<td width="10%"><div align="left">${lo.lgnComQteUtil}</div></td>
									<td width="10%"><div align="left">${lo.lgnComPertes}</div></td>
									<td width="9%"><div align="left">${lo.lgnComStockDispo}</div></td>
									<td width="12%"><div align="left">${lo.lgnComNbreJrsRup}</div></td>
									<td width="7%">${lo.lgnQteDistriM1}</td>
									<td width="7%">${lo.lgnQteDistriM2}</td>                                   
									<!--<td width="7%">${lo.lgnQtePro}</td>-->
									
                                    
								</tr>
                
                
			   </c:forEach>
      </tbody>
</table>
</c:if>
	</div> 
	</div>
    
</form:form>