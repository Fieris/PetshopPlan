package ru.fieris.petshopplan.excel;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.fieris.petshopplan.Application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

//Открывает Excel файл как File
@Deprecated
public class ExcelOpener {
    private static File excelSourceFile;


    public static File getExcelSourceFile() {
        return excelSourceFile;
    }


    public static void setExcelSourceFile(File excelSourceFile) {
        if(isValidFile(excelSourceFile)){
            ExcelOpener.excelSourceFile = excelSourceFile;
            setMainFrameIconAndTitle();
        }
    }


    @Deprecated
    public static boolean isValidFileWithException(File excelSourceFile) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(excelSourceFile)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (Objects.equals(sheet.getSheetName(), "Report")) {
                return true;
            }
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удается получить доступ к файлу");
            alert.setContentText(e.getLocalizedMessage() + ". Закройте Excel файл и повторите попытку");
            alert.show();
            return false;
        } catch (InvalidFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удается получить доступ к файлу");
            alert.setContentText(e.getLocalizedMessage());
            alert.show();
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Выбран неверный файл");
        alert.setContentText("Файл не прошел проверку.");
        alert.show();
        return false;
    }

    //Проверяет файл если подходит true если нет то false
    public static boolean isValidFile(File excelSourceFile) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(excelSourceFile)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (Objects.equals(sheet.getSheetName(), "Report")) {
                return true;
            }
        } catch (IOException | InvalidFormatException exc) {
            exc.printStackTrace();
        }
        return false;
    }

    private static void setMainFrameIconAndTitle() {
        Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/excel.png")));
        Application.getMainStage().getIcons().remove(0);
        Application.getMainStage().getIcons().add(image);
        Application.getMainStage().setTitle("Планы - " + excelSourceFile.getAbsolutePath());

    }
}
