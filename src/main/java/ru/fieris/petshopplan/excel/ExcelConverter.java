package ru.fieris.petshopplan.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ExcelConverter {
    private File excelFile;
    private ArrayList<ExcelSellLine> excelArray;

    public ExcelConverter(File file){
        this.excelFile = file;
    }

    public void setExcelFile(File file){
        this.excelFile = file;
    }

    public File getExcelFile(){
        return excelFile;
    }

    public void convertFromExcelToList(){
        ArrayList<ExcelSellLine> excelSellLineArrayList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(excelFile)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {

                Row currentRow = rowIterator.next();

                Iterator<Cell> cellIterator = currentRow.cellIterator();
                ArrayList<Cell> cellArrayList = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    cellArrayList.add(currentCell);
                }

                final String shop = cellArrayList.get(0).getStringCellValue();
                final String article = cellArrayList.get(1).getStringCellValue();
                final String name = cellArrayList.get(2).getStringCellValue();
                final String analyticalCategory = cellArrayList.get(3).getStringCellValue();
                final String manufacturer = cellArrayList.get(4).getStringCellValue();
                final double weight = cellArrayList.get(5).getNumericCellValue();
                final double pricePerOneExcludingBonuses = cellArrayList.get(6).getNumericCellValue();
                final double pricePerKgExcludingBonuses = cellArrayList.get(7).getNumericCellValue();
                final double totalPriceExcludingBonuses = cellArrayList.get(8).getNumericCellValue();
                final double purchasePrice = cellArrayList.get(9).getNumericCellValue();
                final double quantityPiece = cellArrayList.get(10).getNumericCellValue();
                final double quantityKg = cellArrayList.get(11).getNumericCellValue();
                final String dateOfSale = cellArrayList.get(12).getStringCellValue();
                final double spentPetshopBonuses = cellArrayList.get(13).getNumericCellValue();
                final double spentSpasiboBonuses = cellArrayList.get(14).getNumericCellValue();
                final double checkNumber = cellArrayList.get(15).getNumericCellValue();
                final String deliveryMethod = cellArrayList.get(16).getStringCellValue();

                ExcelSellLine excelSellLine = new ExcelSellLine(shop, article, name, analyticalCategory, manufacturer, weight,
                        pricePerOneExcludingBonuses, pricePerKgExcludingBonuses, totalPriceExcludingBonuses,
                        purchasePrice, quantityPiece, quantityKg, dateOfSale, spentPetshopBonuses,
                        spentSpasiboBonuses, checkNumber, deliveryMethod);

                excelSellLineArrayList.add(excelSellLine);
            }

        } catch (IOException exc) {
            exc.printStackTrace();
        }

        excelArray = excelSellLineArrayList;
    }

    public ArrayList<ExcelSellLine> getExcelArray() {
        return excelArray;
    }

    public boolean isValidFile(){
        try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (Objects.equals(sheet.getSheetName(), "Report")) {
                return true;
            }
        } catch (IOException | InvalidFormatException exc) {
            exc.printStackTrace();
        }
        return false;
    }

}
