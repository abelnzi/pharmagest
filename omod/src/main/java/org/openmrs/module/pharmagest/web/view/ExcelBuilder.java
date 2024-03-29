package org.openmrs.module.pharmagest.web.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

//public class ExcelBuilder extends AbstractJExcelView {
//
//	@Override
//	protected void buildExcelDocument(Map<String, Object> model, WritableWorkbook workbook, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		// get data model which is passed by the Spring container
//		List<Book> listBooks = (List<Book>) model.get("listBooks");
//
//		// create a new Excel sheet
//		WritableSheet sheet = workbook.createSheet("Java Books", 0);
//
//		// create header row
//		sheet.addCell(new Label(0, 0, "Book Title"));
//		sheet.addCell(new Label(1, 0, "Author"));
//		sheet.addCell(new Label(2, 0, "ISBN"));
//		sheet.addCell(new Label(3, 0, "Published Date"));
//		sheet.addCell(new Label(4, 0, "Price"));
//
//		// create data rows
//		int rowCount = 1;
//
//		for (Book aBook : listBooks) {
//			sheet.addCell(new Label(0, rowCount, aBook.getTitle()));
//			sheet.addCell(new Label(1, rowCount, aBook.getAuthor()));
//			sheet.addCell(new Label(2, rowCount, aBook.getIsbn()));
//			sheet.addCell(new Label(3, rowCount, aBook.getPublishedDate()));
//			sheet.addCell(new jxl.write.Number(4, rowCount, aBook.getPrice()));
//
//			rowCount++;
//		}
//	}
//}

public class ExcelBuilder  extends AbstractExcelView {
 
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, 
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        @SuppressWarnings("unchecked")
		List<Book> listBooks = (List<Book>) model.get("listBooks");
        
        //HSSFWorkbook workbook1 = new HSSFWorkbook();
        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Java Books");
        sheet.setDefaultColumnWidth(30);
         
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
         
        // create header row
        HSSFRow header = sheet.createRow(0);
         
        header.createCell(0).setCellValue("Book Title");
        header.getCell(0).setCellStyle(style);
         
        header.createCell(1).setCellValue("Author");
        header.getCell(1).setCellStyle(style);
         
        header.createCell(2).setCellValue("ISBN");
        header.getCell(2).setCellStyle(style);
         
        header.createCell(3).setCellValue("Published Date");
        header.getCell(3).setCellStyle(style);
         
        header.createCell(4).setCellValue("Price");
        header.getCell(4).setCellStyle(style);
         
        // create data rows
        int rowCount = 1;
         
        for (Book aBook : listBooks) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(aBook.getTitle());
            aRow.createCell(1).setCellValue(aBook.getAuthor());
            aRow.createCell(2).setCellValue(aBook.getIsbn());
            aRow.createCell(3).setCellValue(aBook.getPublishedDate());
            aRow.createCell(4).setCellValue(aBook.getPrice());
        }
    }
 
}