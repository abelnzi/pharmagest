<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<div>
<h2 >Effectuer l'inventaire</h2>
</div>

<br>
<div>
  <table width="100%" border="0">
  <tbody>
    <tr>
      <td><input type="submit" name="submit2" id="submit2" value="Enregistrer">
        <input type="reset" name="reset" id="reset" value="Annuler"></td>
      </tr>
  </tbody>
</table>

</div>
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="47%"><table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="25%">Type d'inventaire</td>
                    <td width="75%"><table width="200">
                      <tr>
                        <td><label>
                          <input name="RadioGroup1" type="radio" id="RadioGroup1_0" value="ARV" checked="checked">
                          ARV</label></td>
                      </tr>
                      <tr>
                        <td><label>
                          <input type="radio" name="RadioGroup1" value="IOS" id="RadioGroup1_1">
                          IOS</label></td>
                      </tr>
                    </table></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="53%"><table width="100%" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="26%">Date de l'inventaitre</td>
                    <td width="74%"><input type="date" name="date4" id="date4"></td>
                  </tr>
                  <tr>
                    <td>Nom du site</td>
                    <td><select name="select3" id="select3">
                    </select></td>
                  </tr>
                </tbody>
              </table></td>
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
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="8%"><div align="center">
                <table width="100%" border="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td><div align="center">Code article</div></td>
                    </tr>
                    <tr>
                      <td><div align="center">
                        <input type="text" disabled="disabled" size="10" readonly />
                      </div></td>
                    </tr>
                  </tbody>
                </table>
              </div></td>
              <td width="19%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">D&eacute;signation</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <select name="select5" id="select5">
                      </select>
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="11%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield3" type="text" id="textfield3" size="10">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">P&eacute;remption</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="date" name="date" id="date" >
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="12%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Stock th&eacute;orique</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield5" type="text" id="textfield5" size="10">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="10%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Stock physique</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield6" type="text" id="textfield6" size="10">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="7%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Ecart</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield2" type="text" id="textfield2" size="10">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="13%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Observation</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield" id="textfield">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="6%">
              <table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center"></div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="submit" value="Valider">
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
                  <td width="9%">Code article</td>
                  <td width="17%">D&eacute;signation</td>
                  <td width="12%">Num&eacute;ro du lot</td>
                  <td width="13%">P&eacute;remption</td>
                  <td width="12%">Stock th&eacute;orique</td>
                  <td width="11%">Stock physique</td>
                  <td width="7%">Ecart</td>
                  <td width="13%">Observation</td>
                  <td width="6%">&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td><div align="center">
                    <input type="submit" name="submit3" id="submit3" value="X" size="5">
                  </div></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td><div align="center">
                    <input type="submit" name="submit4" id="submit4" value="X" size="5">
                  </div></td>
                </tr>
      </tbody>
</table>      
</div> 
	</div>

