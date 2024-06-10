package ru.fieris.petshopplan;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import ru.fieris.petshopplan.controllers.MainController;
import com.dustinredmond.fxtrayicon.*;
import ru.fieris.petshopplan.excel.ExcelConverter;


import java.io.IOException;
import java.util.EventObject;
import java.util.Objects;

public class Application extends javafx.application.Application {
    public static final String VERSION = "1.13";
    private static Stage mainStage = null;
    private static MainController mainController = null;
    private FXTrayIcon trayIcon = null;


    @Override
    public void start(Stage stage) throws IOException{
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainFrame.fxml"));


        Scene scene = new Scene(fxmlLoader.load());

        mainController = fxmlLoader.getController();

        stage.setTitle("Планы");
        stage.setScene(scene);

        //dragAndDrop файла
        scene.setOnDragOver(event -> {
            if(event.getGestureSource() != scene && event.getDragboard().hasFiles()){
                if(!event.getDragboard().getFiles().get(0).getName().endsWith("xlsx")) return;
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        scene.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasFiles()){
                success = true;
                mainController.openExcelFile(db.getFiles().get(0));
            }
            event.setDropCompleted(success);
            event.consume();
        });


        stage.setResizable(false);
        Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/petshop.png")));
        stage.getIcons().add(image);



        stage.show();
    }

    public static void main(String[] args) {
            launch();

    }

    public static MainController getMainController(){
        return mainController;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    //TODO наработки трея
//    private FXTrayIcon fxTrayInitializer(Stage stage){
//        FXTrayIcon icon = new FXTrayIcon(stage, getClass().getResource("icons/petshop.png"));
//        icon.setOnAction(actionEvent -> {
//            actionEvent.consume();
//            stage.show();
//        });
//        icon.addExitItem("Выход");
//        icon.show();
//        return icon;
//    }
//    public FXTrayIcon getTrayIcon() {
//        return trayIcon;
//    }
}
//TODO null план для импорта
//TODO lastOpenedFileLocation для JsonData, чтобы при открытии программы она пыталась подключить последний открытый файл