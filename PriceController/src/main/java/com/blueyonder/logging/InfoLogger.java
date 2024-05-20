package com.blueyonder.logging;

import com.blueyonder.model.Product;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class InfoLogger {


//    public static void logToExcel(LocalDateTime localDateTime, List<Product>productList, List<Integer> soldQuantity) throws IOException, InvalidFormatException {
//      String filePath = "C:\\Users\\1037782\\OneDrive - Blue Yonder\\Documents\\PriceControllerLogs.xlsx";
//      try(XSSFWorkbook workbook=new XSSFWorkbook(new File(filePath)))
//      {
//
//        XSSFSheet spreadsheet = workbook.createSheet("Product Order Data");
//        int rowNum=0;
////    try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
////    int index=0;
////    Row row = spreadsheet.createRow(rowNum++);
////    Cell cell = row.createCell(index++);
////    cell.setCellValue(localDateTime);
//////    for(int i=0;i< productList.size();i++)
//////    {
//////      cell = row.createCell(index++);
//////      Product p =productList.get(i);
//////      cell = row.createCell(index++);
//////      cell.setCellValue(p.getP_id().toString());
//////      cell = row.createCell(index++);
//////      cell.setCellValue(soldQuantity.get(i).toString());
//////      cell = row.createCell(index++);
//////      cell.setCellValue(p.getPrice());
//////      cell = row.createCell(index++);
//////      cell.setCellValue(p.getLast_updated_date_time().toString());
//////      cell = row.createCell(index++);
//////      cell.setCellValue(p.getLast_checked_date_time().toString());
//////    }
////      workbook.write(outputStream);
////    } catch (IOException e) {
////      e.printStackTrace();
////    }
////    finally {
////      workbook.close();
////    }
//  }
//
//  }


  public static void logDataToExcel(List<Product> productList) throws InvalidFormatException {

	  
    String filePath = "C:\\Users\\1037792\\OneDrive - Blue Yonder\\Documents\\Postman\\PriceControllerLogs.xlsx";

    try (XSSFWorkbook workbook = new XSSFWorkbook(new File(filePath));
         FileOutputStream fileOut = new FileOutputStream(filePath, true)) {

      Sheet sheet = workbook.getSheetAt(0);
      int lastRowNum = sheet.getLastRowNum();
      if (lastRowNum == -1) {
        Row row = sheet.createRow(0);
        lastRowNum++;
      }

      Row row = sheet.createRow(lastRowNum + 1);
      Row firstRow = sheet.getRow(0);

      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

      int index = 0;
      Cell cell;

      if (firstRow.getCell(index) == null || Objects.equals(firstRow.getCell(index).getStringCellValue(), "")) {
        cell = firstRow.createCell(index);
        cell.setCellValue("DATE TIME");
      }
      cell = row.createCell(index++);
      cell.setCellValue(currentTime.format(formatter));
      for (int i = 0; i < productList.size(); i++) {
        cell = row.createCell(index++);
        cell.setCellValue("");
        Product p = productList.get(i);

        if (firstRow.getCell(index) == null || Objects.equals(firstRow.getCell(index).getStringCellValue(), "")) {
          cell = firstRow.createCell(index);
          cell.setCellValue("ID");
        }
        cell = row.createCell(index++);
        cell.setCellValue(p.getP_id());

        if (firstRow.getCell(index) == null || Objects.equals(firstRow.getCell(index).getStringCellValue(), "")) {
          cell = firstRow.createCell(index);
          cell.setCellValue("SOLD QUANTITY");
        }
        cell = row.createCell(index++);
        cell.setCellValue(p.getSold_qty());

        if (firstRow.getCell(index) == null || Objects.equals(firstRow.getCell(index).getStringCellValue(), "")) {
          cell = firstRow.createCell(index);
          cell.setCellValue("PRICE");
        }
        cell = row.createCell(index++);
        cell.setCellValue(p.getPrice());

        if (firstRow.getCell(index) == null || ObjectUtils.isEmpty(firstRow.getCell(index).getStringCellValue())) {
          cell = firstRow.createCell(index);
          cell.setCellValue("LAST UPDATED");
        }
        cell = row.createCell(index++);
        cell.setCellValue(p.getLast_updated_date_time().format(formatter));

        if (firstRow.getCell(index) == null || ObjectUtils.isEmpty(firstRow.getCell(index).getStringCellValue())) {
          cell = firstRow.createCell(index);
          cell.setCellValue("LAST CHECKED");
        }
        cell = row.createCell(index++);
        cell.setCellValue(p.getLast_checked_date_time().format(formatter));
      }
      workbook.write(fileOut);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
