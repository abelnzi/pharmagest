<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie parametres" 
        otherwise="/login.htm" redirect="/index.htm" />

<script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/view/module/pharmagest/scripts/jquery-1.11.1.js"></script>

<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false ;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=true;Boolean activeInter=false;
 %>

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>

<div class="box" style="margin:0px; width:1580px;background-color:#FCD7DB" >
<ul id="menu">
		
    
			<li class="first "><a href="<c:url value="/module/pharmagest/distributionMenu.form"/>">Menu principal</a></li>
			
    		<li> 
				<a href="<c:url value="/module/pharmagest/saisiesRPPS.form"/>">Saisies des rapports des ESPC/PPS</a>
			</li>
             <li class="active">
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
</ul>

</div>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/importRapportConso.form"
	modelAttribute="formulaireSaisiesPPS"
	commandName="formulaireSaisiesPPS"   enctype="multipart/form-data">
<div>
<h3 ><span style="font-size:36px">.</span> Importation et configuration des produits et programme</h3>
</div>
<br>
<c:if test="${mess =='success'}">
		<div id="openmrs_msg">Enregistrer avec succès</div>
</c:if>
<c:if test="${mess =='existe'}">
		<div id="openmrs_msg">Le rapport mensuel ESPC/PPS du mois de : ${moisDe} a &eacute;t&eacute; d&eacute;j&agrave; saisi</div>
</c:if>
<c:if test="${mess =='echec'}">
		<div id="openmrs_msg">Echec de l'op&eacute;ration</div>
</c:if>

<br>

<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Importer">
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
    <tr>
      <td>Choix du fichier<span style="color:#F11A0C"> *</span></td>
      <td><input type="file" name="file" id="file" /></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>

<br>
	  </div>
	</div>

</div>
<br>

</form:form>