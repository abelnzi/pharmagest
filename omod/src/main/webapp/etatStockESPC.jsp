<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie rapport stock" 
        otherwise="/login.htm" redirect="/index.htm" />

<%@ page import="java.util.Date"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<script type="text/javascript">
    var $j = jQuery.noConflict();
</script>
<script type="text/javascript">
    $j(document).ready(function() {
        $j('#myTable').dataTable();
    } );
</script>
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
            <li>
				<a href="<c:url value="/module/pharmagest/listPPSModification.form"/>">Modification/Validation des rapports ESPC/PPS
            </li>
            <li>
				<a href="<c:url value="/module/pharmagest/listCommandeSite.form"/>">Traitements</a>
			</li>
             <li >
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
            <li class="active">
				<a href="<c:url value="/module/pharmagest/etatStockESPC.form"/>">Etat de Stock des ESPC/PPS</a>
			</li>
	</ul>
</div>

<div>
<h3><span style="font-size:36px">.</span> Etat de Stock des ESPC/PPS </h3>
</div>
<br>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/etatStockESPC.form"
	modelAttribute="formulairePeriode"
	commandName="formulairePeriode">
    
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="11%">Programme <span style="color:#F11A0C">*  </span> : </td>
            <td width="18%"><form:select path="programme">
				            <option value="0">-- Choix --</option>
				            <c:forEach var="item" items="${programmes}">
				              <option <c:if test="${formulairePeriode.getProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
			                </c:forEach>
				          </form:select>
				          <form:errors path="programme"
														cssClass="error" /></td>
            <td width="2%">&nbsp;</td>
            <td width="6%">Date <span style="color:#F11A0C">*</span> : </td>
            <td width="10%"><openmrs_tag:dateField formFieldName="dateDebut" startValue="${obsDate}" /></td>
            <td width="2%">&nbsp;</td>
            <td width="10%">Stock Min <span style="color:#F11A0C"> *</span> : </td>
            <td width="9%"> <form:input path="min" cssStyle="width:100px"/></td>
            <td width="2%">&nbsp;</td>
            <td width="11%">Stock Max <span style="color:#F11A0C">*</span> : </td>
            <td width="9%"> <form:input path="max" cssStyle="width:100px"/></td>
            <td width="10%"><input name="btn_rechercher" type="submit"
												id="btn_rechercher" title="rechercher" value="Rechercher"></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<br>
<br>
<c:if test="${var ==1}">
<div>
	<b class="boxHeader"></b>
	<div class="box">
     
	  <table id="myTable" width="100%" border="1"  cellpadding="7" cellspacing="0">
           
            <thead>
                <tr>
                  <th width="10%">N&deg; Ordre</th>
                  <th width="10%">Nom ESPC/PPS</th>
                  <th width="10%">Programme</th>
                   <th width="18%">Code Produit</th>
                  <th width="10%">Nom du Produit</th>
                  <th width="10%">CMM</th>  
                  <th width="10%">MSD</th>  
                  <th width="10%">Stock disponible</th>                   
                  <th width="9%">Statut</th>                 
                </tr>
              </thead>
              <tbody>
              <% 
			  		int i = 0;
			  %>
                <c:forEach var="ligne" items="${list}">
                 <% 
			  		 i = i+1;
			  	%>
                <c:set var="sdu" value="${ligne.stockDispo}" scope="request" />
                 <c:set var="cmm" value="${ligne.cmm}" scope="request" />
                   <c:set var="mdu" value="${ligne.mdu}" scope="request" />
                   <c:set var="min" value="${formulairePeriode.min}" scope="request" />
                   <c:set var="max" value="${formulairePeriode.max}" scope="request" />
                    
                   <% Integer sdu=  (Integer) request.getAttribute("sdu");
				   Integer cmm=  (Integer) request.getAttribute("cmm");
				   	  Double mdu=  (Double) request.getAttribute("mdu");					  
					Double min =  (Double) request.getAttribute("min");
					Double max =  (Double) request.getAttribute("max");
			
					  String statut="";
					  String attri ="";
					  
			
			
			if(sdu!=0 && cmm!=0) {
					  if(mdu < min) {
						  attri = "color:#F3F3F3;background:#F39C12";
						  statut="sous-stock";
					  } else if(min<=mdu && mdu<=max) {
						    attri = "color:#F3F3F3;background:#58D68D";
						  statut="bien stock&eacute;";
						  } else if(mdu>max){
							attri = "color:#F3F3F3;background:#5DADE2";
						  statut="surstock";
							  }
			} else if(sdu==0 && cmm!=0){
				attri = "color:#F3F3F3;background:#E74C3C";
						  statut="rupture";
				} else if(cmm==0 && sdu!=0) {
					attri = "color:#F3F3F3;background:#BB8FCE";
						  statut="stock dormant";
					
					}else if(cmm==0 && sdu==0) {
						attri = "color:#F3F3F3;background:#D6DBDF";
						statut="Produit non g&eacuter&eacute";						
						} 				
		 			%>
                    
                <tr style="<%= attri %>">
                  <td><%= i %></td>
                  <td>${ligne.commandeSite.pharmSite.sitLib}</td>
                  <td>${ligne.commandeSite.pharmProgramme.programLib}</td>
                  <td>${ligne.produit.prodCode}</td>
                  <td>${ligne.produit.prodLib}</td>
                  <td>${ligne.cmm}</td>
                  <td>${ligne.mdu}</td>
                  <td>${ligne.stockDispo}</td>  
                  <td><%= statut %></td>                  
                </tr>
                </c:forEach>
      		</tbody>
</table>      
</div> 
</div>
 </c:if>
 
 <script type="text/javascript">
  //$("#programme").attr('required', true); 
            $("#dateDebut").attr('required', true); 

            $("#min").attr('required', true); 
			 $("#max").attr('required', true); 
			
</script>
</form:form>
