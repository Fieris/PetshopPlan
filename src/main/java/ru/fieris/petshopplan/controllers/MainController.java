package ru.fieris.petshopplan.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.actionevents.MenuItemExcelOpenAction;
import ru.fieris.petshopplan.customControls.HBoxes.*;
import ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes.*;
import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.excel.ExcelConverter;
import ru.fieris.petshopplan.json.*;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.ZpProperty;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Flow;

public class MainController {
    //TODO Если это инстанс примари или кондишн, то просто копирует то же поле на третий таб, не создавая новый??
    //Главный pane
    @FXML
    private TabPane mainPane = new TabPane();
    //Тут хранятся все Hbox
    ArrayList<CustomHBox> hBoxes = new ArrayList<>();

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

    private int selectedTabIndex = 0;
    //счётчик нужен, тк при иницилизации таба по умолчанию выбирается 0 таб, счетчик будет показывать, сколько раз изменялся selectedTabIndex
    private int selectedTabIndexCounter = 0;

    TotalHBox totalHBox = new TotalHBox(HBoxStyles.ZP);

    @FXML
    public void initialize() {
        //Очищает hBoxes, чтобы они не дублировались!
        hBoxes.clear();

        //сбрасывает счётчик
        selectedTabIndexCounter = 0;


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


        //таб с ЗП
        Tab zpTab = new Tab();
        zpTab.setText("          ЗП          ");
        ScrollPane zpScrollPane = new ScrollPane();
        FlowPane zpFlowPane = new FlowPane();
        zpScrollPane.setContent(zpFlowPane);
        zpTab.setContent(zpScrollPane);


        //установка табов в mainPane
        mainPane.getTabs().setAll(primaryPlansTab, secondaryPlansTab, zpTab);
        mainPane.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {
            if (mainPane.getSelectionModel().getSelectedIndex() == 0 && selectedTabIndexCounter == 0) return;
            selectedTabIndex = mainPane.getSelectionModel().getSelectedIndex();
            selectedTabIndexCounter++;
        });
        mainPane.getSelectionModel().select(selectedTabIndex);


        JsonData jsonData = JsonMapper.readFromJson();
        ArrayList<PrimaryBrandCategory> primaryBrandCategories = jsonData.getPrimaryBrandCategories();
        ArrayList<ConditionCategory> conditionCategories = jsonData.getConditionCategories();
        ArrayList<SecondaryBrandCategory> secondaryBrandCategories = jsonData.getSecondaryBrandCategories();


        //TitleHBox с наименованиями для основных категорий
        TitleHBox primaryTitle = new TitleHBox(HBoxStyles.PLAN);
        hBoxes.add(primaryTitle);
        primaryFlowPane.getChildren().add(primaryTitle);

        //TitleHbox с наименованиями для дополнительных категорий
        TitleHBox secondaryTitle = new TitleHBox(HBoxStyles.PLAN);
        hBoxes.add(secondaryTitle);
        secondaryFlowPane.getChildren().add(secondaryTitle);

        //TitleHBox с наименованиями для ЗП
        TitleHBox zpTitle = new TitleHBox(HBoxStyles.ZP);
        hBoxes.add(zpTitle);
        zpFlowPane.getChildren().add(zpTitle);

        //установка основных категорий на основной и зпшный пейн
        for (PrimaryBrandCategory line : primaryBrandCategories) {
            if (line.isHidden()) continue;
            //Плановый бокс
            PrimaryBrandNameHBox primaryBrandNameHBox = new PrimaryBrandNameHBox(line, HBoxStyles.PLAN);
            hBoxes.add(primaryBrandNameHBox);
            primaryFlowPane.getChildren().add(primaryBrandNameHBox);

            //зпшный бокс
            primaryBrandNameHBox = new PrimaryBrandNameHBox(line, HBoxStyles.ZP);
            hBoxes.add(primaryBrandNameHBox);
            zpFlowPane.getChildren().add(primaryBrandNameHBox);
        }

        //Установка дополнительных планов на основной и зпшный пейн
        for (SecondaryBrandCategory line : secondaryBrandCategories) {
            if (line.isHidden()) continue;
            //Плановый бокс
            SecondaryBrandNameHBox secondaryBrandNameHBox = new SecondaryBrandNameHBox(line, HBoxStyles.PLAN);
            hBoxes.add(secondaryBrandNameHBox);
            secondaryFlowPane.getChildren().add(secondaryBrandNameHBox);

            //зпшный бокс
            //проверка на установлен ли план, если нет, то не отображает его
            if(Objects.isNull(line.getZpProperty())) continue;
            if(line.getZpProperty() == ZpProperty.ZERO) continue;
            secondaryBrandNameHBox = new SecondaryBrandNameHBox(line, HBoxStyles.ZP);
            hBoxes.add(secondaryBrandNameHBox);
            zpFlowPane.getChildren().add(secondaryBrandNameHBox);
        }

        //установка условных категорий на пейн с проверкой на hidden
        for (ConditionCategory line : conditionCategories) {
            if (line.isHidden()) continue;
            //Плановый бокс
            ConditionHBox conditionHBox = new ConditionHBox(line, HBoxStyles.PLAN);
            hBoxes.add(conditionHBox);
            primaryFlowPane.getChildren().add(conditionHBox);

            //Зпшный бокс
            conditionHBox = new ConditionHBox(line, HBoxStyles.ZP);
            hBoxes.add(conditionHBox);
            zpFlowPane.getChildren().add(conditionHBox);


        }

        //TotalHBox с общей суммой зп
        hBoxes.add(totalHBox);
        zpFlowPane.getChildren().add(totalHBox);








        secondaryFlowPane.setVgap(5);
        primaryFlowPane.setVgap(5);
        zpFlowPane.setVgap(5);
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

        //Записывает время создания файла в label
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            LocalDateTime date = LocalDateTime.ofInstant(creationTime.toInstant(), TimeZone.getDefault().toZoneId());
            lblDate.setVisible(true);
            lblDate.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        } catch (IOException exc) {
            System.out.println("не удалось считать время открытия файла" + exc);
        }

        //Записывает данные файла и данные открытия в Excel файл
        jsonData.setLastOpenedFile(file);
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


        //Записывает время создания файла в label
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            LocalDateTime date = LocalDateTime.ofInstant(creationTime.toInstant(), TimeZone.getDefault().toZoneId());
            lblDate.setVisible(true);
            lblDate.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        } catch (IOException exc) {
            System.out.println("не удалось считать время открытия файла" + exc);
        }


        //Записывает данные файла и данные открытия в Excel файл
        jsonData.setLastOpenedFile(file);
        JsonMapper.writeToJson(jsonData);


        searchAndCalc();
    }


    //Поиск, подсчет и вставка планов (итоговый скрипт)
    @FXML
    public void searchAndCalc() {
        excelConverter.convertFromExcelToList();
        Calculator calculator = new Calculator(excelConverter.getExcelArray());


        //Вставка фактического выполнения
        double totalZP = 0;
        TotalHBox totalZpBoxInstance = new TotalHBox(HBoxStyles.ZP);
        for (CustomHBox customHBox : hBoxes) {
            if (customHBox instanceof PlanHBox) {
                ((PlanHBox) customHBox).calculate(calculator);
                if(customHBox.getBoxStyle() == HBoxStyles.ZP){
                    totalZP += Double.parseDouble(((PlanHBox) customHBox).getZpPerManTextField());
                }
            }
            if (customHBox instanceof TotalHBox){
               totalZpBoxInstance = (TotalHBox) customHBox;
            }
        }
        totalZpBoxInstance.setTotalValue(String.format(Locale.US, "%.2f", totalZP));

    }


    @FXML
    void openProgramSettings() {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("programSettingsFrame.fxml"));
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setAlwaysOnTop(false);
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
            stage.setTitle("Настройки категорий");
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
        webView.getEngine().load("https://docs.google.com/spreadsheets/d/1995oScgcL_OluFNSLkGGUjl2AGYckT3rmz9tO_FP7tw/edit?usp=sharing");
        stage.show();
    }

    @FXML
    public void openVisualTable(){
        Stage stage = new Stage();
        Scene scene = new Scene(initializeVisualTable());


        Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/petshop.png")));
        stage.getIcons().add(image);
        stage.setTitle("% выплат");
        stage.setScene(scene);
//        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initOwner(Application.getMainStage());
        
        
        
        
        
        stage.show();
    }

    private FlowPane initializeVisualTable(){
        FlowPane flowPane = new FlowPane();

        Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/Tables.png")));
        ImageView imageView = new ImageView(image);

        flowPane.getChildren().add(imageView);

        return flowPane;
    }
}