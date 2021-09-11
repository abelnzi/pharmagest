<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception" 
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
             <li >
				<a href="<c:url value="/module/pharmagest/listReceptionHisto.form"/>">Historique des receptions</a>
			</li>
            <li class="active">
				<a href="<c:url value="/module/pharmagest/satisfactionESPC.form"/>">Taux de satisfaction</a>
			</li>
	</ul>
</div>

<div>
<h3><span style="font-size:36px">.</span> Taux de satisfaction en ligne de produit <strong>${periode}</strong></h3>
</div>
<br>

<form:form method="post"
	action="${pageContext.request.contextPath}/module/pharmagest/satisfactionESPC.form"
	modelAttribute="formulaireReception"
	commandName="formulaireReception">
    
    <div>
    <input type="submit" name="btn_enregistrer" id="btn_enregistrer" value="Enregistrer" onClick="return confirm('Voulez vous enregistrer?') ? true : false;">
    </div>
    <br>
    
<div> <b class="boxHeader"></b>
  <div class="box">
    <div class="searchWidgetContainer" id="findPatients" align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
        <tbody>
          <tr>
            <td width="13%">Programme <span style="color:#F11A0C">*  </span></td>
            <td width="2%">:</td>
            <td width="21%"><form:select path="pharmProgramme">
				            <option value="0">-- Choix --</option>
				            <c:forEach var="item" items="${programmes}">
				              <option <c:if test="${formulaireReception.getPharmProgramme().getProgramId()==item.getProgramId()}">selected="selected"</c:if>    				  							value="${item.getProgramId()}">${item.getProgramLib()} </option>
			                </c:forEach>
				          </form:select>
				          <form:errors path="pharmProgramme"
														cssClass="error" /></td>
            <td width="2%">&nbsp;</td>
            <td width="14%">Mois du rapport <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="12%"><openmrs_tag:dateField formFieldName="dateDebut" startValue="${formulaireReception.getDateDebut()}" /> <form:errors path="dateDebut"	cssClass="error" /></td>
            <td width="2%">&nbsp;</td>
            <td width="17%">Date de transmission du rapport <span style="color:#F11A0C">*</span></td>
            <td width="2%">:</td>
            <td width="13%"><openmrs_tag:dateField formFieldName="dateFin" startValue="${formulaireReception.getDateFin()}" /><form:errors path="dateFin"	cssClass="error" /></td>
            </tr>
          <tr>
            <td>N&deg;  bordereau de livraison <span style="color:#F11A0C">*</span></td>
            <td>:</td>
            <td> <form:input path="recptNum" size="40px"/> <form:errors path="recptNum"	cssClass="error" /></td>
            <td>&nbsp;</td>
            <td>Date de r&eacute;ception <span style="color:#F11A0C">*</span></td>
            <td>:</td>
            <td><openmrs_tag:dateField formFieldName="recptDateRecept" startValue="${formulaireReception.getRecptDateRecept()}" /><form:errors path="recptDateRecept"	cssClass="error" /></td>
            <td>&nbsp;</td>
            <td>D&eacute;lai de livraison</td>
            <td>:</td>
            <td><strong>${delai} j</strong></td>
          </tr>
          <c:if test="${tauxSL !=null}">
          <tr>
            <td colspan="11">Taux de satisfaction en ligne de produit : <strong>${tauxSL}%</strong></td>
          </tr>
          </c:if>
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
                  <th width="10%">Code Produit</th>
                  <th width="10%">D&eacute;signation produit</th>
                   <th width="18%">Quantit&eacute; command&eacute;e</th>
                  <th width="10%">Quantit&eacute; livr&eacute;e</th>                 
                  <th width="9%">Taux &#37; </th>                 
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
                
                    
                <tr>
                  <td><%= i %></td>
                  <td>${ligne.produit.prodCode}</td>
                  <td>${ligne.produit.prodLib}</td>
                  <td>${ligne.lgnRecptQte}</td>
                  <td>${ligne.lgnRecptQteLivree}</td>               
                   <c:set var="livree" value="${ligne.lgnRecptQteLivree}" scope="request" />
                   <c:set var="commande" value="${ligne.lgnRecptQte}" scope="request" />
                   <% Integer livree=  (Integer) request.getAttribute("livree");
						Integer commande=  (Integer) request.getAttribute("commande"); 
			
						String taux ="";
						
						if(livree!=null && commande!=null && commande!=0){
							int tauxCal = (int)( Math.round( (double)(livree*100) / (double) commande ));
							taux=tauxCal+" &#37;" ;
							} else if(livree==null && commande!=null && commande!=0){
								taux=" ";
								} else {
									taux="Non applicable";
									}
													
					 %>
                  <td><%= taux %></td>                  
                </tr>
                </c:forEach>
      		</tbody>
</table>      
</div> 
</div>
 </c:if>
 
 <script type="text/javascript">

     $("#dateDebut").attr('required', true); 

     $("#dateFin").attr('required', true); 

			
</script>
</form:form>
