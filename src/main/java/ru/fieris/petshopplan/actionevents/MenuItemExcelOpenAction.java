package ru.fieris.petshopplan.actionevents;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MenuItemExcelOpenAction{
    private static final FileChooser fileChooser = new FileChooser();

    public static File open(Stage stage){
        fileChooser.setTitle("Открыть Excel файл");

        //Фильтры
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );

        return fileChooser.showOpenDialog(stage);
    }

}
