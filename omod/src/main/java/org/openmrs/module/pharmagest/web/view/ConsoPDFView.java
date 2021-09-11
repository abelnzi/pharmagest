package org.openmrs.module.pharmagest.web.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.module.pharmagest.metier.RapportConsoBean;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 * @author www.codejava.net
 *
 */
public class ConsoPDFView extends AbstractITextPdfView {

	private static Font normalText = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	private static Font normalTextGras = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);

	private static Font normalTitle = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);

	private static Font smallText = new Font(Font.FontFamily.COURIER, 6, Font.NORMAL);

	public static final String logoRessource1 = "http://localhost:8080/PROJECTRAPPORT/resource/images/ministere.png";
	public static final String logoRessource2 = "http://localhost:8080/PROJECTRAPPORT/resource/images/PSP.png";

	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<RapportConsoBean> listBooks = (List<RapportConsoBean>) model.get("listRapConso");
		// creerLogo(document);
		createEteteDoc(document);
		informationRapport(document);
		tableauRapport(document, listBooks);

	}

	/*******************
	 * Les méthodes qui suivent sont toutes appélées dans la méthode générales
	 ********************/

	public void creerLogo(Document document) throws MalformedURLException, IOException, DocumentException {
		PdfPTable tableauLogo = new PdfPTable(2);
		PdfPCell cell;

		// Logo MSP
		Image logoMSP = Image.getInstance(new URL(logoRessource1));
		logoMSP.scalePercent(25f);
		cell = new PdfPCell(logoMSP);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableauLogo.addCell(cell);

		// Logo PSP
		Image logoPSP = Image.getInstance(new URL(logoRessource2));
		logoPSP.scalePercent(20f);
		cell = new PdfPCell(logoPSP);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tableauLogo.addCell(cell);

		document.add(tableauLogo);
	}

	public void createEteteDoc(Document document) throws DocumentException {
		String titredocement = "RAPPORT MENSUEL - BIOHIMIE";
		PdfPTable tabEntete = new PdfPTable(1);
		tabEntete.setWidthPercentage(100);

		PdfPCell cell = new PdfPCell(new Phrase(titredocement, normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		tabEntete.addCell(cell);
		tabEntete.setSpacingAfter(10);
		document.add(tabEntete);
	}

	private void informationRapport(Document document) throws DocumentException {

		// Creation du tableau général
		PdfPTable tableauGrand = new PdfPTable(5);
		tableauGrand.setWidths(new int[] { 40, 8, 40, 8, 40 });
		tableauGrand.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		PdfPCell cell;
		PdfPCell cellSeparateur = new PdfPCell();
		cellSeparateur.setBorder(Rectangle.NO_BORDER);

		// Création du premier bloc d'informations
		PdfPTable tableBloc1 = new PdfPTable(2);
		// Ligne1
		tableBloc1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc1.addCell(new Phrase("Région sanitaire:", normalTextGras));
		cell = new PdfPCell(new Phrase("la Région ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc1.addCell(cell);
		// Ligne2
		tableBloc1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc1.addCell(new Phrase("District sanitaire:", normalTextGras));
		cell = new PdfPCell(new Phrase("district Ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc1.addCell(cell);
		// Ligne3
		tableBloc1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc1.addCell(new Phrase("Etablissement sanitaire:", normalTextGras));
		cell = new PdfPCell(new Phrase("etablissement ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc1.addCell(cell);
		// Ligne4
		tableBloc1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc1.addCell(new Phrase("Nom du service :", normalTextGras));
		cell = new PdfPCell(new Phrase("service Ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc1.addCell(cell);

		// Ligne5
		tableBloc1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc1.addCell(new Phrase("Code PSP:", normalTextGras));
		cell = new PdfPCell(new Phrase("PSP Ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc1.addCell(cell);

		tableauGrand.addCell(tableBloc1);
		// deuxieme colone grand tableau
		tableauGrand.addCell(cellSeparateur);

		// Creation du deuxieme bloc d'informations
		PdfPTable tableBloc2 = new PdfPTable(2);
		// Ligne1
		tableBloc2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc2.addCell(new Phrase("Date:", normalTextGras));
		cell = new PdfPCell(new Phrase("date ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc2.addCell(cell);
		// Ligne2
		tableBloc2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc2.addCell(new Phrase("Mois de :", normalTextGras));
		cell = new PdfPCell(new Phrase("Mois ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc2.addCell(cell);
		// Ligne3
		tableBloc2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc2.addCell(new Phrase("Rapport Urgent:", normalTextGras));
		cell = new PdfPCell(new Phrase("Urgent ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc2.addCell(cell);

		tableauGrand.addCell(tableBloc2);
		// troisieme colone grand tableau
		tableauGrand.addCell(cellSeparateur);

		// Création du troisieme bloc d'informations
		PdfPTable tableBloc3 = new PdfPTable(2);
		// Ligne1
		tableBloc3.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableBloc3.addCell(new Phrase("Niveau de stock Max:", normalTextGras));
		cell = new PdfPCell(new Phrase("Niveau ici", normalText));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableBloc3.addCell(cell);
		tableauGrand.addCell(tableBloc3);

		// Prise en compte du du tableau
		tableauGrand.setSpacingAfter(10);
		document.add(tableauGrand);
	}

	public void tableauRapport(Document document, Collection<RapportConsoBean> listConso) throws DocumentException {
		PdfPTable tableauRecap = new PdfPTable(11);
		PdfPCell cell;

		cell = new PdfPCell(new Phrase("Désignation", normalTitle));
		cell.setColspan(2);
		cell.setRowspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Unité", normalTitle));
		cell.setRowspan(2);
		// cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("STOCK initial", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Quantité récu", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Quantité Utilisé", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Perte", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Stock Disponible", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Nombre de jours de rupture", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Quantité Distribuée M-1", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		cell = new PdfPCell(new Phrase("Quantité Distribuée M-2", normalTitle));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);
		// Affichage des lettre sur la seconde ligne

		// colonne1
		cell = new PdfPCell(new Phrase(new Phrase("A", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne2
		cell = new PdfPCell(new Phrase(new Phrase("B", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne3
		cell = new PdfPCell(new Phrase(new Phrase("C", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne4
		cell = new PdfPCell(new Phrase(new Phrase("D", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne5
		cell = new PdfPCell(new Phrase(new Phrase("E", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne6
		cell = new PdfPCell(new Phrase(new Phrase("F", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne7
		cell = new PdfPCell(new Phrase(new Phrase("G", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// colonne8
		cell = new PdfPCell(new Phrase(new Phrase("H", normalTitle)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableauRecap.addCell(cell);

		// Affichage de la troisieme ligne
		cell = new PdfPCell(new Phrase(""));
		cell.setColspan(3);
		tableauRecap.addCell(cell);

		tableauRecap.addCell(new Phrase(new Phrase("Col. E du rapport du mois passé", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Fiches de Stock", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Fiches de Stock", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Fiches de Stock", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Fiches de Stock", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Fiches de Stock", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Col. C Du rapport du mois passé", smallText)));
		tableauRecap.addCell(new Phrase(new Phrase("Col. C Du rapport du mois M-2", smallText)));

		// Ici la liste pour renseigner le reste du Tableau
		// Charger la liste
		for (RapportConsoBean prodTemp : listConso) {
			// colonne1
			cell = new PdfPCell(new Phrase(prodTemp.getProduit().getProdCode(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableauRecap.addCell(cell);

			// colonne2
			cell = new PdfPCell(new Phrase(prodTemp.getProduit().getProdLib(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableauRecap.addCell(cell);

			// colonne3
			cell = new PdfPCell(new Phrase(prodTemp.getProduit().getProdUnite(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableauRecap.addCell(cell);

			// colonne4
			cell = new PdfPCell(new Phrase("" + prodTemp.getStockIni(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne5
			cell = new PdfPCell(new Phrase("" + prodTemp.getQteRecu(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne6
			cell = new PdfPCell(new Phrase("" + prodTemp.getQteUtil(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne7
			cell = new PdfPCell(new Phrase("" + prodTemp.getPertes(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne8
			cell = new PdfPCell(new Phrase("" + prodTemp.getStockDispo(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne9
			cell = new PdfPCell(new Phrase("" + prodTemp.getNbrJrsRup(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne10
			cell = new PdfPCell(new Phrase("" + prodTemp.getQteDistM1(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

			// colonne11
			cell = new PdfPCell(new Phrase("" + prodTemp.getQteDistM2(), normalText));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableauRecap.addCell(cell);

		}
		document.add(tableauRecap);
	}

	

}