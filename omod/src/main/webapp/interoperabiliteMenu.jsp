<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie interoperabilite" 
        otherwise="/login.htm" redirect="/module/pharmagest/interoperabiliteMenu.htm" />

<% Boolean activeFour=false;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false;Boolean activeInter=true;
 %>
 
<%@ include file="template/localHeader.jsp"%>


<table width="319" border="0" cellspacing="8">
  <tbody>
    <tr>
      <td width="197"><b class="boxHeader"></b><div class="box adminMenuList">
		<ul id="menu">
			
			<li>
				<a href="<c:url value="/module/pharmagest/interoperabiliteConfig.form"/>">Param&eacute;trage FTP</a>
			</li>
            <br>
			<li>
				<a href="<c:url value="/module/pharmagest/interoperabilite.form"/>">Transfert de la commande</a>
			</li>
            

		</ul>
</div></td>
      <td width="80">&nbsp;</td>
      <td width="8">&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>