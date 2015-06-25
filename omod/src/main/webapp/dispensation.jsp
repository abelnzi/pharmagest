<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<div>
<h2 >Effectuer la dispensation </h2>
</div>
<br>
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
                    <td><input type="text" /></td>
                    <td>Nombre de jours de traitement</td>
                    <td><input type="text" /></td>
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
                    <td width="44%">Prescripteur</td>
                    <td width="56%"><select name="select" id="select">
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
                    <td>Date de vente</td>
                    <td><input type="date" name="date2" id="date2"></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="32%"><table width="100%" border="0" cellspacing="0">
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
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="15%"><div align="center">
                <table width="100%" border="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td><div align="center">Code article</div></td>
                    </tr>
                    <tr>
                      <td>
                      <input type="text" name="textfield" id="textfield"></td>
                    </tr>
                  </tbody>
                </table>
              </div></td>
              <td width="16%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">DCI Article</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield2" id="textfield2">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="17%"><table width="100%" border="0" cellspacing="0">
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
              <td width="16%"><table width="100%" border="0" cellspacing="0">
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
              <td width="16%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Prix unitaire</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield5" id="textfield5">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="20%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Total</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield6" id="textfield6">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td>
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
        <table width="100%" border="1" cellpadding="7" cellspacing="0">
  <tbody>
    <tr>
      <td width="11%">Code article</td>
      <td width="37%">DCI Article</td>
      <td width="14%">Quantité demandée</td>
      <td width="14%">Quantitée dispensée</td>
      <td width="13%">Prix unitaire</td>
      <td width="11%">Total</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </tbody>
</table>

        </div>
	</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>