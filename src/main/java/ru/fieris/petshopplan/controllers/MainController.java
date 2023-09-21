package ru.fieris.petshopplan.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.actionevents.MenuItemExcelOpenAction;
import ru.fieris.petshopplan.customControls.HBoxes.*;
import ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes.ConditionHBox;
import ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes.PlanHBox;
import ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes.PrimaryBrandNameHBox;
import ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes.SecondaryBrandNameHBox;
import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.excel.ExcelConverter;
import ru.fieris.petshopplan.json.*;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainController {
    //Главный pane
    @FXML
    private TabPane mainPane = new TabPane();
    //Тут хранятся все Hbox
    ArrayList<CustomHBox> HBoxes = new ArrayList<>();

    //Конвертер
    private ExcelConverter excelConverter;

    public ExcelConverter getExcelConverter() {
        return excelConverter;
    }


    //Вкладка Файл->открыть excel...
    @FXML
    private MenuItem menuItemExcelOpen;

    //Вкладка Настройки->открыть настройки...
    @FXML
    private MenuItem menuItemOpenSettings;

    //label с названием магазина
    @FXML
    private Label lblShopName;

    //label с текущей датой
    @FXML
    private Label lblDate;


    @FXML
    private ScrollPane scrollPane;

    @FXML
    private MenuItem miSearchAndCalcButton;

    @FXML
    public void initialize() {
        mainPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        mainPane.setStyle("-fx-border-color: grey");

        //таб с основными планами
        Tab primaryPlansTab = new Tab();
        primaryPlansTab.setText("Основные планы (с бонусами)");
        ScrollPane primaryScrollPane = new ScrollPane();
        FlowPane primaryFlowPane = new FlowPane();
        primaryScrollPane.setContent(primaryFlowPane);
        primaryPlansTab.setContent(primaryScrollPane);


        //таб с дополнительными планами
        Tab secondaryPlansTab = new Tab();
        secondaryPlansTab.setText("Дополнительные планы (без бонусов)");
        ScrollPane secondaryScrollPane = new ScrollPane();
        FlowPane secondaryFlowPane = new FlowPane();
        secondaryScrollPane.setContent(secondaryFlowPane);
        secondaryPlansTab.setContent(secondaryScrollPane);


        //установка табов в mainPane
        mainPane.getTabs().setAll(primaryPlansTab, secondaryPlansTab);


        JsonData jsonData = JsonMapper.readFromJson();
        ArrayList<PrimaryBrandCategory> primaryBrandCategories = jsonData.getPrimaryBrandCategories();
        ArrayList<ConditionCategory> conditionCategories = jsonData.getConditionCategories();
        ArrayList<SecondaryBrandCategory> secondaryBrandCategories = jsonData.getSecondaryBrandCategories();


        //TitleHBox с наименованиями для основных категорий
        TitleHBox primaryTitle = new TitleHBox();
        HBoxes.add(primaryTitle);
        primaryFlowPane.getChildren().add(primaryTitle);

        //установка основных категорий на пейн
        for (PrimaryBrandCategory line : primaryBrandCategories) {
            PrimaryBrandNameHBox primaryBrandNameHBox = new PrimaryBrandNameHBox(line);
            HBoxes.add(primaryBrandNameHBox);
            primaryFlowPane.getChildren().add(primaryBrandNameHBox);
        }

        //установка условных категорий на пейн с проверкой на hidden
        for (ConditionCategory line : conditionCategories) {
            if (line.isHidden()) continue;
            ConditionHBox conditionHBox = new ConditionHBox(line);
            HBoxes.add(conditionHBox);
            primaryFlowPane.getChildren().add(conditionHBox);
        }


        //TitleHbox с наименованиями для дополнительных категорий
        TitleHBox secondaryTitle = new TitleHBox();
        HBoxes.add(secondaryTitle);
        secondaryFlowPane.getChildren().add(secondaryTitle);


        //Установка дополнительных планов на пейн
        for (SecondaryBrandCategory line : secondaryBrandCategories) {
            SecondaryBrandNameHBox secondaryBrandNameHBox = new SecondaryBrandNameHBox(line);
            HBoxes.add(secondaryBrandNameHBox);
            secondaryFlowPane.getChildren().add(secondaryBrandNameHBox);
        }

        secondaryFlowPane.setVgap(5);
        primaryFlowPane.setVgap(5);

    }


    //для открытия с кнопки
    //TODO переделать под один метод
    @FXML
    public void openExcelFile() {
        JsonData jsonData = JsonMapper.readFromJson();
        File file = MenuItemExcelOpenAction.open(Application.getMainStage());

        //Если окно открытия файла закрыть, передаст null
        if (Objects.nonNull(file)) {
            ExcelConverter excelConverter = new ExcelConverter(file);
            //Если передан невалидный файл, метод прерывается
            if (!excelConverter.isValidFile()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка открытия файла");
                alert.setHeaderText("");
                alert.setContentText("Не удалось открыть выбранный файл");
                alert.show();
                return;
            }
            this.excelConverter = excelConverter;
        } else {
            return;
        }

        miSearchAndCalcButton.setDisable(false);
        Application.getMainStage().setTitle("Планы - " + file.getAbsolutePath());

        //Записывает время открытия в label
        LocalDateTime date = LocalDateTime.now();
        lblDate.setVisible(true);
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        //Записывает данные файла и данные открытия в Excel файл
        jsonData.setLastOpenedFile(file);
        jsonData.setLastOpenedDate(new Date());
        JsonMapper.writeToJson(jsonData);

        searchAndCalc();
    }


    //для открытия через драг
    public void openExcelFile(File file) {
        JsonData jsonData = JsonMapper.readFromJson();
        ExcelConverter excelConverter = new ExcelConverter(file);
        //Если передан невалидный файл, метод прерывается
        if (!excelConverter.isValidFile()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка открытия файла");
            alert.setHeaderText("");
            alert.setContentText("Не удалось открыть выбранный файл");
            alert.show();
            return;
        }
        this.excelConverter = excelConverter;

        miSearchAndCalcButton.setDisable(false);
        Application.getMainStage().setTitle("Планы - " + file.getAbsolutePath());


        //Записывает время открытия в label
        LocalDateTime date = LocalDateTime.now();
        lblDate.setVisible(true);
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        //Записывает данные файла и данные открытия в Excel файл
        jsonData.setLastOpenedFile(file);
        jsonData.setLastOpenedDate(new Date());
        JsonMapper.writeToJson(jsonData);


        searchAndCalc();
    }


    //Поиск, подсчет и вставка планов (итоговый скрипт)
    @FXML
    public void searchAndCalc() {
        excelConverter.convertFromExcelToList();
        Calculator calculator = new Calculator(excelConverter.getExcelArray());


        //Вставка фактического выполнения
        for (CustomHBox customHBox : HBoxes) {
            if (customHBox instanceof PlanHBox) {
                ((PlanHBox) customHBox).calculate(calculator);
            }
        }
    }


    @FXML
    void openProgramSettings() {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("programSettingsFrame.fxml"));
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Настройки");
            Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/settings.png")));
            stage.getIcons().add(image);
            stage.initOwner(Application.getMainStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void openFilterSettings() {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("filterSettingsFrame.fxml"));
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Настройки поиска");
            Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/settings.png")));
            stage.getIcons().add(image);
            stage.initOwner(Application.getMainStage());
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnHiding(e -> {
                initialize();
                if (Objects.nonNull(excelConverter)) {
                    if (Objects.nonNull(excelConverter.getExcelFile())) {
                        searchAndCalc();
                    }
                }
            });

            stage.show();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void openGoogleTablePlans() {
        Stage stage = new Stage();
        WebView webView = new WebView();
        Scene scene = new Scene(webView);
        stage.setScene(scene);
        webView.getEngine().load("https://www.google.com");
        stage.show();
    }
}