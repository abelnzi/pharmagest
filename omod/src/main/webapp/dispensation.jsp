<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<div>
<h2 >Effectuer la dispensation </h2>
</div>
<br>

<!-- <div style="border: 1px dashed black; padding: 10px;">
		<table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="19%">
                <label>
                  <input name="RadioGroup1" type="radio" id="RadioGroup1_0" onClick="block1()" value="bouton radio"  checked >
                  Patient sous traitement ARV
                </label></td>
                <td width="81%">
                <label>
                  <input type="radio" name="RadioGroup1" value="bouton radio" id="RadioGroup1_1" onClick="block2()">
                  Autres dispensation</label></td>
            </tr>
          </tbody>
        </table>
</div>-->

<div>

	<b class="boxHeader">Rechercher le patient</b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="10%">N° Patient</td>
              <td width="15%"><input type="text" /></td>
              <td width="73%"><input type="submit" name="submit" id="submit" value="Rechercher"></td>
            </tr>
          </tbody>
        </table>
        </div>
  </div>
</div>
<br>
<hr>
<br>
<div id="openmrs_msg">Patient trouvé</div>
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
              <td width="10%">Patient N°</td>
              <td width="16%"><input type="text" disabled="disabled" readonly /></td>
              <td width="73%"><table width="100%" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="17%">Dernier régime</td>
                    <td width="28%"><input type="text" disabled="disabled" readonly /></td>
                    <td width="24%">Date dernier régime</td>
                    <td width="29%"><input type="text" disabled="disabled" readonly /></td>
                  </tr>
                  <tr>
                    <td>Régime actuel</td>
                    <td><select name="select4" id="select4">
                    </select></td>
                    <td>Nombre de jours de traitement</td>
                    <td><input type="text" /></td>
                  </tr>
                  <tr>
                    <td height="96">But</td>
                    <td colspan="3"><table width="200">
                      <tr>
                        <td><label>
                          <input type="radio" name="RadioGroup2" value="PEC" id="RadioGroup2_0">
                          PEC</label></td>
                     
                        <td><label>
                          <input type="radio" name="RadioGroup2" value="PTME" id="RadioGroup2_1">
                          PTME</label></td>
                      
                        <td><label>
                          <input type="radio" name="RadioGroup2" value="AES" id="RadioGroup2_2">
                          AES</label></td>
                      </tr>
                    </table></td>
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
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="32%"><table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="42%">Prescripteur</td>
                    <td width="58%"><select name="select" id="select">
                    </select></td>
                  </tr>
                  <tr>
                    <td>Date de prescription</td>
                    <td>
                    <input type="date" name="date" id="date"></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="36%"><table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="41%">Pharmacien</td>
                    <td width="59%"><select name="select2" id="select2">
                    </select></td>
                  </tr>
                  <tr>
                    <td>Date de dispensation</td>
                    <td><input type="date" name="date2" id="date2"></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="32%"><table width="67%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Date du prochain RDV</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="date" name="date3" id="date3">
                    </div></td>
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

<div>
	<b class="boxHeader"></b>
	<div class="box">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="13%"><div align="center">
                <table width="100%" border="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td><div align="center">Code article</div></td>
                    </tr>
                    <tr>
                      <td><div align="center">
                        <input type="text" disabled="disabled" readonly />
                      </div></td>
                    </tr>
                  </tbody>
                </table>
              </div></td>
              <td width="23%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">DCI Article</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <select name="select5" id="select5">
                      </select>
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantité demandée</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield3" id="textfield3">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantitée dispensée</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield4" id="textfield4">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Prix unitaire</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield5" type="text" id="textfield5" value="0">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Total</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield6" type="text" id="textfield6" value="0">
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
                      <input type="submit" value="Valider">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
            </tr>
          </tbody>
        </table>
        <table width="100%" border="1" align="left" cellpadding="7" cellspacing="0">
             <tbody>
                <tr>
                  <td width="14%">Code article</td>
                  <td width="23%">DCI Article</td>
                  <td width="13%">Quantité demandée</td>
                  <td width="15%">Quantitée dispensée</td>
                  <td width="13%">Prix unitaire</td>
                  <td width="14%">Total</td>
                  <td width="8%">Action</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td><input type="submit" name="submit3" id="submit3" value="X"></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td><input type="submit" name="submit4" id="submit4" value="X"></td>
                </tr>
      </tbody>
</table>       
	</div>
</div>
