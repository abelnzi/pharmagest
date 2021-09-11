  <%@ include file="/WEB-INF/template/include.jsp"%>
  <%@ include file="/WEB-INF/template/header.jsp"%>
  <openmrs:require privilege="pharmacie interoperabilite" 
		  otherwise="/login.htm" redirect="/module/pharmagest/interoperabilite.htm" />
		  
  <script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>
  
  <script type="text/css">
	  .error {
	  color: #ff0000;
  }
  </SCRIPT>
  
  <spring:htmlEscape defaultHtmlEscape="true" />
<h2 align="center">
	<spring:message code="pharmagest.title" />
</h2>
  
  
  <div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
	<ul id="menu">
	  <li class=" first"> <a href="<c:url value="/module/pharmagest/interoperabiliteMenu.form"/>">Menu principal</a> </li>
	  <li> <a href="<c:url value="/module/pharmagest/interoperabiliteConfig.form"/>">Param&eacute;trage FTP</a> </li>
	  <li class="active"> <a href="<c:url value="/module/pharmagest/interoperabilite.form"/>">Transfert de la commande</a> </li>
	  
	  <!-- Add further links here -->
	</ul>
  </div>
  
  
  <form:form method="post"
	  action="${pageContext.request.contextPath}/module/pharmagest/interoperabilite.form"
	  modelAttribute="formulaireRapportCommande"
	  commandName="formulaireRapportCommande"  >
	<div>
  <h3 ><span style="font-size:36px">.</span> Exportation du rapport commande</h3>
  </div>
  <br>
  <c:if test="${mess =='success'}">
		  <div id="openmrs_msg">Fichier sauvegard&eacute; avec succ&egrave;s</div>
  </c:if>
  <c:if test="${mess =='echec'}">
		  <div id="openmrs_msg">Echec de l'op&eacute;ration</div>
  </c:if>
  
  <br>
  <br>
  <div>
  
	  
	  <b class="boxHeader"></b>
	  <div class="box">
		  <div class="searchWidgetContainer" id="findPatients" align="center">
		  
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
	<tbody>
	  <tr>
		<td width="11%" height="60">Programme <span style="color:#F11A0C">*</span> : </td>
		<td width="18%"><form:select path="pharmProgramme" cssStyle="width:150px">
						<option value="0">-- Choix --</option>
						 <c:forEach var="item" items="${programmes}">
							  <option <c:if test="${formulaireRapportCommande.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
						 </c:forEach>
				  </form:select> 
				  <form:errors path="pharmProgramme" cssClass="error"/> </td>
		<td width="2%">&nbsp;</td>
		<td width="9%">Fin de p&eacute;riode :</td>
		<td width="18%"> <!--<form:input path="dateCommande" />--><openmrs_tag:dateField formFieldName="dateCommande" startValue="${obsDate}" />
  </td>
		<td width="2%">&nbsp;</td>
		<td width="40%"><input name="btn_rechercher" type="submit"
												  id="btn_rechercher" title="rechercher" value="Exporter"></td>
		</tr>
	</tbody>
  </table>
		</div>
	  </div>
  
  </div>
  <br>
  
  </form:form>