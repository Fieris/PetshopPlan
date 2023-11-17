package ru.fieris.petshopplan.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.json.JsonData;
import ru.fieris.petshopplan.json.JsonMapper;

import java.util.Objects;

public class ProgramSettingsController {

    JsonData jsonData = JsonMapper.readFromJson();

    @FXML private Button saveBtn;

    @FXML private TextField emplNumberTF;

    @FXML public void initialize(){
        emplNumberTF.setText(String.valueOf(jsonData.getNumberOfEmployers()));
    }
    @FXML public void saveNumberOfEmployers(){
        int numberOfEmpl = 0;
        try{
            numberOfEmpl = Integer.parseInt(emplNumberTF.getText());
        } catch (NumberFormatException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("");
            alert.setContentText(exc.getMessage());

            alert.show();
            return;
        }

        jsonData.setNumberOfEmployers(numberOfEmpl);
        JsonMapper.writeToJson(jsonData);

//        Application.getMainController().initialize();
        if(Objects.nonNull(Application.getMainController().getExcelConverter())){
            if(Application.getMainController().getExcelConverter().isValidFile()){
                Application.getMainController().searchAndCalc();
            }
        }
    }
}
