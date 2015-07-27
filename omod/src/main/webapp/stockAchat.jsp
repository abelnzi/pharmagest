<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">
function block2() {
document.getElementById("block1").style.display='none';
document.getElementById("block2").style.display='block';
}
function block1() {
document.getElementById("block1").style.display='block';
document.getElementById("block2").style.display='none';
}
function entree() {
document.getElementById("sortie").style.display='none';
document.getElementById("entree").style.display='block';
}
function sortie() {
document.getElementById("entree").style.display='none';
document.getElementById("sortie").style.display='block';
}

</SCRIPT>

<div>
<h2 >Saisie d'achats</h2>
</div>
<br>
<div>

	<b class="boxHeader">Quel type d'entr&eacute;e de produit souhaitez vous effectuer ?</b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="19%">
                <label>
                  <input name="RadioGroup1" type="radio" id="RadioGroup1_0" onClick="block1()" value="bouton radio"   >
                  Réceptionner une commande</label>
                </td>
                <td width="81%">
                <label>
                  <input type="radio" name="RadioGroup1" value="bouton radio" id="RadioGroup1_1" onClick="block2()" checked>
                  Reprendre un produit et transferer vers un site</label>
               
              </td>
            </tr>
          </tbody>
        </table>
        </div>
  </div>
</div>
<br>
<hr>
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
<br>
<div>

	<div id="block1" style="display:block">
	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="32%"><table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="44%">Commande N&deg;</td>
                    <td width="56%"><select name="select" id="select">
                    </select></td>
                  </tr>
                  <tr>
                    <td>Facture N&deg;</td>
                    <td>
                    <input type="date" name="date" id="date"></td>
                  </tr>
                  <tr>
                    <td>Date de facture</td>
                    <td><input type="date" name="date3" id="date3"></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="36%"><table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="41%">Num&eacute;ro de livraison</td>
                    <td width="59%"><select name="select2" id="select2">
                    </select></td>
                  </tr>
                  <tr>
                    <td>Date de livraison</td>
                    <td><input type="date" name="date2" id="date2"></td>
                  </tr>
                </tbody>
              </table></td>
            </tr>
          </tbody>
        </table>
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
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot</div></td>
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
                    <td><div align="center">Date de p&eacute;remption</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="date" name="date5" id="date5">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute;</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input name="textfield5" type="text" id="textfield5">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Prix d'achat</div></td>
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
        <br>
        <table width="100%" border="1" align="left" cellpadding="7" cellspacing="0">
  <tbody>
    <tr>
      <td width="14%">Code article</td>
      <td width="23%">D&eacute;signation</td>
      <td width="13%">Num&eacute;ro du lot</td>
      <td width="15%">Date de p&eacute;remption</td>
      <td width="13%">Quantit&eacute;</td>
      <td width="14%">Prix d'achat</td>
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
</div>
</div>
</div>
<div id="block2" style="display:none">
<div>

	<b class="boxHeader"></b>
	<div class="box">
		<div class="searchWidgetContainer" id="findPatients" align="center">
        <table width="100%" border="0" cellpadding="7" cellspacing="0" >
          <tbody>
            <tr>
              <td width="18%"><table width="200">
                <tr>
                  <td><label>
                    <input type="radio" name="RadioGroup2" value="bouton radio" id="RadioGroup2_0" onClick="entree()" checked>
                    Entr&eacute;e de stock
                  </label></td>
                </tr>
                <tr>
                  <td><label>
                    <input type="radio" name="RadioGroup2" value="bouton radio" id="RadioGroup2_1" onClick="sortie()">
                    Sortie de stock</label></td>
                </tr>
              </table></td>
              <td width="27%"><table width="200">
                <tr>
                  <td>
                  <select id="entree" style="display:block">
                    <option value="1">r&eacute;ception de commande</option>
                    <option value="2">retour par un patient</option>
                    <option value="3">retour par un service</option>
                  </select>
                  </td>
                </tr>
                <tr>
                  <td>
                    <select id="sortie" style="display:none">
                      <option value="1">transfert vers un site</option>
                      <option value="2">produits avari&eacute;s</option>
                    </select>
                    </td>
                </tr>
              </table></td>
              <td width="55%"><table width="100%" border="0" cellpadding="7" cellspacing="0">
                <tbody>
                  <tr>
                    <td width="41%">Source/Destination</td>
                    <td width="59%"><input type="date" name="date4" id="date4"></td>
                  </tr>
                  <tr>
                    <td>Date de transfert des produits</td>
                    <td><input type="date" name="date2" id="date2"></td>
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
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Num&eacute;ro du lot</div></td>
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
                    <td><div align="center">Date de p&eacute;remption</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="date" name="date5" id="date5">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Quantit&eacute;</div></td>
                  </tr>
                  <tr>
                    <td><div align="center">
                      <input type="text" name="textfield5" id="textfield5">
                    </div></td>
                  </tr>
                </tbody>
              </table></td>
              <td width="14%"><table width="100%" border="0" cellspacing="0">
                <tbody>
                  <tr>
                    <td><div align="center">Prix d'achat</div></td>
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
        <br>
        <table width="100%" border="1" align="left" cellpadding="7" cellspacing="0">
  <tbody>
    <tr>
      <td width="14%">Code article</td>
      <td width="23%">D&eacute;signation</td>
      <td width="13%">Num&eacute;ro du lot</td>
      <td width="15%">Date de p&eacute;remption</td>
      <td width="13%">Quantit&eacute;</td>
      <td width="14%">Prix d'achat</td>
      <td width="8%">Action</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td><input type="submit" name="submit3" id="submit3" value="Supprimer"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td><input type="submit" name="submit4" id="submit4" value="Supprimer"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td><input type="submit" name="submit5" id="submit5" value="Supprimer"></td>
    </tr>
  </tbody>
</table>

        </div>
	</div>
</div>
</div>
