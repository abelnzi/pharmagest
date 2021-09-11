<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="pharmacie reception" 
        otherwise="/login.htm" redirect="/module/pharmagest/receptionMenu.form" />
<% Boolean activeFour=true;Boolean activeDisp=false;Boolean activeDist=false;Boolean activeMvt=false;
Boolean activeInv=false;Boolean activeRap=false;Boolean activeParam=false;Boolean activeInter=false;
 %>
<%@ include file="template/localHeader.jsp"%>

<table width="319" border="0" cellspacing="8">
  <tbody>
    <tr>
      <td width="197"><b class="boxHeader"></b><div class="box adminMenuList">
		<ul id="menu">
			
			<li>
				<a href="<c:url value="/module/pharmagest/saisieReception.form"/>">Saisies des receptions</a>
            </li>
            <br>
            <li>
				<a href="<c:url value="/module/pharmagest/listReceptionModif.form"/>">Modification des receptions</a>
            </li>
            <br>
            <li>
				<a href="<c:url value="/module/pharmagest/listReceptionValid.form"/>">Validation des receptions</a>
            </li>
            <br>
            <li >
				<a href="<c:url value="/module/pharmagest/listReceptionHisto.form"/>">Historique des receptions</a>
			</li>
            <br>
            <li >
				<a href="<c:url value="/module/pharmagest/satisfactionESPC.form"/>">Taux de satisfaction</a>
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