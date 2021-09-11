<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie" 
        otherwise="/login.htm" redirect="/index.htm" />
        
<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false; Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>

<%@ page import="java.util.List"%>

<!--<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/dist/css/bootstrap.min.css" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/assets/css/ie10-viewport-bug-workaround.css" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/assets/js/ie-emulation-modes-warning.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap//dist/css/carousel.css" />-->


<openmrs:htmlInclude file="/moduleResources/pharmagest/jquery/jquery-ui.css" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/jquery/jquery.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/jquery/jquery-ui.js" />


<script type="text/javascript">
var $j = jQuery.noConflict();
    $j(document).ready(function() {
        $j("#accordion").accordion({
			collapsible : true
			});
		
    } );
</script>

<!--<br>
<div> <b class="boxHeader"></b>
<div class="box">
<table width="100%" border="0">
              <tbody>
               <tr>
                  <td width="47%"><strong>Produits en sous stock
                  - stock min :</strong></td>
                  <td width="40%"><strong>Produits en sur stock - stock max :</strong></td>
                </tr>
                <tr>
                  <td>
                  	<table width="96%" border="1" align="center" cellpadding="7" cellspacing="0">
                      <tbody>
                        <tr>
                          <td width="51%"><strong>Produits</strong></td>
                          <td width="20%"><strong>Quantit&eacute; en stock</strong></td>
                          <td width="29%"><strong>Programmes</strong></td>
                        </tr>
                        <c:forEach var="lo" items="${listStockMin}">
                        <tr>
                          <td width="51%">${lo.produit.fullName}</td>
                          <td width="20%">${lo.qteStock}</td>
                          <td width="29%">${lo.programme.programLib}</td>
                        </tr>
                         </c:forEach>
                      </tbody>
                    </table>

                  </td>
                  <td><table width="96%" border="1" align="center" cellpadding="7" cellspacing="0">
                    <tbody>
                      <tr>
                        <td width="46%"><strong>Produits</strong></td>
                        <td width="23%"><strong>Quantit&eacute; en stock</strong></td>
                        <td width="31%"><strong>Programmes</strong></td>
                      </tr>
                      <c:forEach var="lo" items="${listStockMin}">
                        <tr>
                          <td width="46%">${lo.produit.fullName}</td>
                          <td width="23%">${lo.qteStock}</td>
                          <td width="31%">${lo.programme.programLib}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table></td>
                </tr>
              </tbody>
</table></div></div>-->


 <!-- Accordion -->

<br/>
<br/>
<h3 class="boxHeader"><div align="center"> Alerte sur le stock</div></h3>
<br/>
<div id="accordion">
<c:set var="listMin" value="${listStockMin}" scope="request" />
    <% List listMin=  (List) request.getAttribute("listMin");
    int size=0;
	if(!listMin.isEmpty()){
		size=listMin.size();
		}
	%>
	<h3>Produits en sous stock
                  - nombre : <strong style="color:#F10E15"><%= size %></strong></h3>
	<div>
    
    <table width="96%" border="1" align="center" cellpadding="7" cellspacing="0">
                      <tbody>
                        <tr>
                          <td width="51%"><strong>Code</strong></td>
                          <td width="51%"><strong>Produits</strong></td>
                          <td width="20%"><strong>CMM</strong></td>
                          <td width="20%"><strong>MSD</strong></td>
                          <td width="20%"><strong>Quantit&eacute; en stock</strong></td>
                          <td width="29%"><strong>Programmes</strong></td>
                        </tr>
                        <c:forEach var="lo" items="${listStockMin}">
                        <tr>
                          <td width="51%">${lo.produit.prodCode}</td>
                          <td width="51%">${lo.produit.fullName}</td>
                          <td width="20%">${lo.cmm}</td>
                          <td width="20%">${lo.msd}</td>
                          <td width="20%">${lo.qteStock}</td>
                          <td width="29%">${lo.programme.programLib}</td>
                        </tr>
                        </c:forEach>
                      </tbody>
    </table>
    </div>
    <c:set var="listMax" value="${listStockMax}" scope="request" />
    <% List listMax=  (List) request.getAttribute("listMax");
    int sizeMax=0;
	if(!listMax.isEmpty()){
		sizeMax=listMax.size();
		}
	%>
    
	<h3>Produits en sur stock - nombre : <strong style="color:#F10E15"><%= sizeMax %></strong></h3>
	<div>
    	<table width="96%" border="1" align="center" cellpadding="7" cellspacing="0">
                    <tbody>
                      <tr>
                        <td width="51%"><strong>Code</strong></td>
                        <td width="51%"><strong>Produits</strong></td>
                          <td width="20%"><strong>CMM</strong></td>
                          <td width="20%"><strong>MSD</strong></td>
                          <td width="20%"><strong>Quantit&eacute; en stock</strong></td>
                          <td width="29%"><strong>Programmes</strong></td>
                      </tr>
                      <c:forEach var="lo" items="${listStockMax}">
                         <tr>
                           <td width="51%">${lo.produit.prodCode}</td>
                           <td width="51%">${lo.produit.fullName}</td>
                          <td width="20%">${lo.cmm}</td>
                          <td width="20%">${lo.msd}</td>
                          <td width="20%">${lo.qteStock}</td>
                          <td width="29%">${lo.programme.programLib}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
      </table></td>
                </tr>
              </tbody>
		</table>
    </div>
    
    <c:set var="listAlert" value="${listProdAlert}" scope="request" />
    <% List listAlert=  (List) request.getAttribute("listAlert");
    int sizeAlert=0;
	if(!listAlert.isEmpty()){
		sizeAlert=listAlert.size();
		}
	%>
    
	<h3>Produits presque p&eacute;rim&eacute;s - nombre : <strong style="color:#F10E15"><%= sizeAlert %></strong></h3>
	<div>
    	<table width="96%" border="1" align="center" cellpadding="7" cellspacing="0">
                    <tbody>
                      <tr>
                        <td width="51%"><strong>Code</strong></td>
                        <td width="26%"><strong>Produits</strong></td>
                        <td width="19%"><strong>Num&eacute;ro du lot</strong></td>
                        <td width="23%"><strong>Date de p&eacute;remption</strong></td>
                        <td width="13%"><strong>CMM</strong></td>
                        <td width="13%"><strong>MSD</strong></td>
                        <td width="13%"><strong>Quantit&eacute; en stock</strong></td>
                        <td width="19%"><strong>Programmes</strong></td>
                      </tr>
                      <c:forEach var="lo" items="${listProdAlert}">
                        <tr>
                          <td width="51%">${lo.pharmProduitAttribut.pharmProduit.prodCode}</td>
                          <td width="26%">${lo.pharmProduitAttribut.pharmProduit.fullName}</td>
                          <td width="19%">${lo.pharmProduitAttribut.prodLot}</td>
                          <td width="23%">${lo.pharmProduitAttribut.prodDatePerem}</td>
                          <td width="13%">${lo.cmm}</td>
                          <td width="13%">${lo.msd}</td>
                          <td width="13%">${lo.qteStock}</td>
                          <td width="19%">${lo.programme.programLib}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
      </table></td>
                </tr>
              </tbody>
		</table>
    </div>
    
	<c:set var="listProdPerim" value="${listProdPerim}" scope="request" />
    <% List listProdPerim=  (List) request.getAttribute("listProdPerim");
    int sizePerim=0;
	if(!listProdPerim.isEmpty()){
		sizePerim=listProdPerim.size();
		}
	%>
    
	<h3>Produits  p&eacute;rim&eacute;s - nombre : <strong style="color:#F10E15"><%= sizePerim %></strong></h3>
	<div>
    	<table width="96%" border="1" align="center" cellpadding="7" cellspacing="0">
                    <tbody>
                      <tr>
                        <td width="51%"><strong>Code</strong></td>
                        <td width="26%"><strong>Produits</strong></td>
                        <td width="19%"><strong>Num&eacute;ro du lot</strong></td>
                        <td width="23%"><strong>Date de p&eacute;remption</strong></td>
                        <td width="12%"><strong>Quantit&eacute; en stock</strong></td>
                        <td width="20%"><strong>Programmes</strong></td>
                      </tr>
                      <c:forEach var="lo" items="${listProdPerim}">
                        <tr>
                          <td width="51%">${lo.pharmProduitAttribut.pharmProduit.prodCode}</td>
                          <td width="26%">${lo.pharmProduitAttribut.pharmProduit.fullName}</td>
                          <td width="19%">${lo.pharmProduitAttribut.prodLot}</td>
                          <td width="23%">${lo.pharmProduitAttribut.prodDatePerem}</td>
                          <td width="12%">${lo.qteStock}</td>
                          <td width="20%">${lo.programme.programLib}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
      </table></td>
                </tr>
              </tbody>
		</table>
    </div>
</div>

<!--<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/assets/js/vendor/jquery.min.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/dist/js/bootstrap.min.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/assets/js/vendor/holder.min.js" />
<openmrs:htmlInclude file="/moduleResources/pharmagest/bootstrap/assets/js/ie10-viewport-bug-workaround.js" />-->


<%@ include file="/WEB-INF/template/footer.jsp"%>