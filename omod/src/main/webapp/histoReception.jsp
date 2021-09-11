<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception" 
        otherwise="/login.htm" redirect="/index.htm" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/scripts/jquery.PrintArea.js" />

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
			
    		<li>
				<a href="<c:url value="/module/pharmagest/saisieReception.form"/>">Saisies des receptions</a>
			</li>
            <li>
				<a href="<c:url value="/module/pharmagest/listReceptionModif.form"/>">Modification des receptions
            </li>
            
            <li>
				<a href="<c:url value="/module/pharmagest/listReceptionValid.form"/>">Validation des receptions</a>
			</li>
             <li  class="active">
				<a href="<c:url value="/module/pharmagest/listReceptionHisto.form"/>">Historique des receptions</a>
			</li>
            <li >
				<a href="<c:url value="/module/pharmagest/satisfactionESPC.form"/>">Taux de satisfaction</a>
			</li>
	
	
</ul>
</div>

<script type="text/css">
	.error {
    color: #ff0000;
}
</SCRIPT>
<script>

var $sig = jQuery.noConflict();

$sig(document).ready(function(){
	$sig("#print_button").click(function(){
		$sig("#print").printArea();
	});
});

</script>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/histoReception.form"
	modelAttribute="formulaireReception"
	commandName="formulaireReception"  >
<div>
<h3 ><span style="font-size:36px">.</span> Historique de la réception de produits </h3>
</div>




<br>


<div id="print">
<div>
  <table width="100%" border="0">
    <tbody>
      <tr>
        <td><input id="print_button" value="Imprimer" type="button" />         
      </tr>
    </tbody>
  </table>
</div>
<div>
  
  
  <b class="boxHeader"></b>
  <div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
		  <table width="100%" border="0" cellspacing="0" cellpadding="7">
		    <tbody>
		      <tr>
		        <td width="22%" height="33">Fournisseur</td>
		        <td width="1%">:</td>
		        <td width="26%">${formulaireReception.pharmFournisseur.fourDesign1} </td>
		        <td width="2%">&nbsp;</td>
		        <td width="18%">Programme</td>
		        <td width="1%">:</td>
		        <td width="30%">${formulaireReception.pharmProgramme.programLib} </td>
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
										String s="";						
										if(d!=null)  s =  formatter.format(d);
										
		%>
		        <td><%= s %></td>
	          </tr>
		      <tr>
		        <td>Op&eacute;rateur de saisie</td>
		        <td>:</td>
		        <td>${formulaireReception.pharmReception.pharmGestionnairePharma.gestNom}  
                  ${formulaireReception.pharmReception.pharmGestionnairePharma.gestPrenom}</td>
		        <td>&nbsp;</td>
		        <td>Date de saisie</td>
		        <td>:</td>
                <c:set var="d1" value="${formulaireReception.recptDateSaisi}" scope="request" />
		        <% Date d1=  (Date) request.getAttribute("d1"); 
										//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										String s1="";						
										if(d1!=null)  s1 =  formatter.format(d1);
										
				%>
		        <td><%= s1 %></td>
	          </tr>
		      <tr>
		        <td>Op&eacute;rateur de validation</td>
		        <td>:</td>
		        <td>${formulaireReception.pharmReception.pharmGestionnaireModif.gestNom}  
                  ${formulaireReception.pharmReception.pharmGestionnaireModif.gestPrenom}</td>
		        <td>&nbsp;</td>
		        <td>Date de validation</td>
		        <td>:</td>
                <c:set var="d2" value="${formulaireReception.recptDateModif}" scope="request" />
		        <% Date d2=  (Date) request.getAttribute("d2"); 
										//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
										String s2="";						
										if(d2!=null)  s2 =  formatter.format(d2);
										
				%>
		        <td><%= s2 %></td>
	          </tr>
	        </tbody>
	      </table>
		</div>
	</div>

</div>
<br>

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
                                                    
                                    <c:set var="d3" value="${lo.lgnDatePerem}" scope="request" />
                                    <% Date d3=  (Date) request.getAttribute("d3"); 
										//SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");	
										String s3="";						
										if(d3!=null)  s3 =  formatter.format(d3); %>
                                        
									<td><div align="left"><%= s3 %></div>
                                    
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
								  <td colspan="10"><div align="right"><strong>Total</strong></div></td>
								  <td><strong>${totalGlobal}</strong></td>
								  <td>&nbsp;</td>
                                </tr>
            
                               
		     
      </tbody>
</table>      
</div> 
	</div>
</div>

</form:form>