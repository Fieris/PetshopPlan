package ru.fieris.petshopplan.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.actionevents.MenuItemExcelOpenAction;
import ru.fieris.petshopplan.customControls.HBoxes.*;
import ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes.*;
import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.excel.ExcelConverter;
import ru.fieris.petshopplan.excel.ExcelSellLine;
import ru.fieris.petshopplan.json.*;
import ru.fieris.petshopplan.json.categories.Category;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.ZpProperty;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public final Font defaultFont = new Font("Arial", 13);

    /**
     * Stage выполнение в день
     */
    Stage perDayCompletionStage = null;


    @FXML
    private ScrollPane scrollPerDayPane;

    @FXML
    private MenuItem miSearchAndCalcButton;
    @FXML private MenuItem dopMenuPerDayButton;

    private int selectedTabIndex = 0;
    //счётчик нужен, тк при иницилизации таба по умолчанию выбирается 0 таб, счетчик будет показывать, сколько раз изменялся selectedTabIndex
    private int selectedTabIndexCounter = 0;

    TotalHBox totalHBox = new TotalHBox(HBoxStyles.ZP);

    //Лист с артикулами для поиска
    private TableView<ArticulToFindInstance> articulInfoTableView;

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
            if (Objects.isNull(line.getZpProperty())) continue;
            if (line.getZpProperty() == ZpProperty.ZERO) continue;
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

        //Таб для отслеживания продажи артов
        Tab articulSearcherTab = articulSearcherInitialize();
        mainPane.getTabs().add(articulSearcherTab);

        //TotalHBox с общей суммой зп
        hBoxes.add(totalHBox);
        zpFlowPane.getChildren().add(totalHBox);


        secondaryFlowPane.setVgap(5);
        primaryFlowPane.setVgap(5);
        zpFlowPane.setVgap(5);
    }

    /**
     * Инициализирует Таб вкладки "Отслеживание артов"
     * @return готовый Таб
     */
    private Tab articulSearcherInitialize(){
        JsonData jsonData = JsonMapper.readFromJson();

        Tab tab = new Tab("Отслеживание артов");
        AnchorPane anchorPane = new AnchorPane();


        //Инициализация таблички
        articulInfoTableView = new TableView<>();
        articulInfoTableView.setPlaceholder(new Label("Нет данных"));
        articulInfoTableView.setPrefSize(600,400);
        articulInfoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<ArticulToFindInstance,String> articulColumn = new TableColumn<>("Артикул");
        articulColumn.setCellValueFactory(new PropertyValueFactory<>("articul"));
        articulColumn.setPrefWidth(100);
        TableColumn<ArticulToFindInstance, String> dateColumn = new TableColumn<>("Дата продажи");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("lastDateOfSale"));
        dateColumn.setPrefWidth(150);
        TableColumn<ArticulToFindInstance, String> commentColumn = new TableColumn<>("Комментарий");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentColumn.setPrefWidth(300);




        articulInfoTableView.getColumns().add(articulColumn);
        articulInfoTableView.getColumns().add(dateColumn);
        articulInfoTableView.getColumns().add(commentColumn);

        Button addArticulButton = new Button("Добавить");
        addArticulButton.setFont(defaultFont);
        addArticulButton.setLayoutY(405);
        Button removeArticulButton = new Button("Удалить");
        removeArticulButton.setFont(defaultFont);
        removeArticulButton.setLayoutY(405);
        removeArticulButton.setLayoutX(80);
        Button commentArticulButton = new Button("Комментарий");
        commentArticulButton.setFont(defaultFont);
        commentArticulButton.setLayoutY(405);
        commentArticulButton.setLayoutX(153);



        anchorPane.getChildren().addAll(articulInfoTableView,addArticulButton,removeArticulButton,commentArticulButton);

        articulInfoTableView.getItems().setAll(jsonData.getArticulsToFindList());





        //Обработка нажатия на кнопку "Добавить"
        addArticulButton.setOnAction(actionEvent -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setContentText("Артикул:");
            textInputDialog.setTitle("Введите артикул");
            textInputDialog.setHeaderText("");
            Optional<String> optionalS = textInputDialog.showAndWait();
            if(optionalS.isEmpty()) {
                return;
            }
            if(optionalS.get().isBlank()){
                return;
            }
            jsonData.getArticulsToFindList().add(new ArticulToFindInstance(optionalS.get().trim()));
            JsonMapper.writeToJson(jsonData);
            articulInfoTableView.getItems().setAll(jsonData.getArticulsToFindList());
        });

        //Обработка нажатия на кнопку "Удалить"
        removeArticulButton.setOnAction(actionEvent ->{
            //Confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Подтвердить удаление");
            alert.setHeaderText("");



            //Лист выбранных элементов
            ObservableList<ArticulToFindInstance> selectedItems = articulInfoTableView.getSelectionModel().getSelectedItems();
            if(selectedItems.isEmpty()) return;

            //Подтверждение удаления
            alert.setContentText("Удалить " + selectedItems.size() + " элементов?");
            Optional<ButtonType> optionalButtonType = alert.showAndWait();
            if(optionalButtonType.isPresent() && !optionalButtonType.get().equals(ButtonType.OK)) return;



            for(ArticulToFindInstance instance : selectedItems){
                jsonData.getArticulsToFindList().remove(instance);
            }
            JsonMapper.writeToJson(jsonData);
            articulInfoTableView.getItems().setAll(jsonData.getArticulsToFindList());
        });

        //Обработка нажатия на кнопку "Комментарий"
        commentArticulButton.setOnAction(actionEvent -> {
            if(articulInfoTableView.getSelectionModel().getSelectedItems().isEmpty()) return;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("");
            dialog.setTitle("Введите комментарий");
            dialog.setContentText("Комментарий:");
            Optional<String> optionalS = dialog.showAndWait();
            if(optionalS.isEmpty()) return;
            if(optionalS.get().isBlank()) return;

            ObservableList<ArticulToFindInstance> selectedItems = articulInfoTableView.getSelectionModel().getSelectedItems();
            for(ArticulToFindInstance instance : selectedItems){
                jsonData.getArticulsToFindList().get(jsonData.getArticulsToFindList().indexOf(instance)).setComment(optionalS.get().trim());
            }
            JsonMapper.writeToJson(jsonData);
            articulInfoTableView.getItems().setAll(jsonData.getArticulsToFindList());
        });


        tab.setContent(anchorPane);
        return tab;
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
        dopMenuPerDayButton.setDisable(false);
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
        dopMenuPerDayButton.setDisable(false);
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
    //c 1.13 также ищет заданные артикулы
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
                if (customHBox.getBoxStyle() == HBoxStyles.ZP) {
                    totalZP += Double.parseDouble(((PlanHBox) customHBox).getZpPerManTextField());
                }
            }
            if (customHBox instanceof TotalHBox) {
                totalZpBoxInstance = (TotalHBox) customHBox;
            }
        }
        totalZpBoxInstance.setTotalValue(String.format(Locale.US, "%.2f", totalZP));

        articulInfoTableView.getItems().setAll(calculator.articulSearcherLogic(articulInfoTableView.getItems()));
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
    public void openVisualTable() {
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

    private FlowPane initializeVisualTable() {
        FlowPane flowPane = new FlowPane();

        Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/Tables.png")));
        ImageView imageView = new ImageView(image);

        flowPane.getChildren().add(imageView);

        return flowPane;
    }

    @FXML
    public void openPerDayCompletion() {
        StackPane rootPane = new StackPane();
        rootPane.setAlignment(Pos.TOP_LEFT);

        VBox categoriesVBox = new VBox();
        categoriesVBox.setMaxWidth(120);
        categoriesVBox.setMinWidth(120);

        HBox planHBox = new HBox();
        planHBox.setSpacing(20);
        ScrollPane scrollPerDayPane = new ScrollPane(planHBox);


        Label space = new Label();
        space.setMinWidth(120);
        space.setMaxWidth(120);
        planHBox.getChildren().add(0,space);


        rootPane.getChildren().add(0,scrollPerDayPane);
        rootPane.getChildren().add(1,categoriesVBox);

        Scene scene = new Scene(rootPane);


        perDayCompletionStage = new Stage();
        perDayCompletionStage.setTitle("Выполнение по дням");
        perDayCompletionStage.initOwner(Application.getMainStage());
        perDayCompletionStage.setScene(scene);
        perDayCompletionStage.setMaximized(false);

        perDayCompletionStage.setResizable(false);


        initializePerDayCompletion(categoriesVBox,planHBox);

        perDayCompletionStage.show();
    }

    private void initializePerDayCompletion(VBox ctgs, HBox plans) {
        ArrayList<ExcelSellLine> excelSellLineArrayList = Application.getMainController().getExcelConverter().getExcelArray();
        //хэшсет со всеми датами, которые есть в эксель документе
        HashSet<LocalDate> datesOfSale = new HashSet<>();

        //лист с нужными категориями
        ArrayList<Category> categories = new ArrayList<>();
        categories.addAll(JsonMapper.readFromJson().getPrimaryBrandCategories());
        categories.addAll(JsonMapper.readFromJson().getConditionCategories());

        Font titleFont = Font.font("Arial", FontWeight.BOLD, 14);
        Font font = Font.font("Arial", 14);

        //Инициализация хэшсета с датами и перенос его в лист
        for (ExcelSellLine line :
                excelSellLineArrayList) {
            datesOfSale.add(line.getDateOfSale());
        }
        List<LocalDate> listOfDatesOfSale = new ArrayList<>(datesOfSale);
        Collections.sort(listOfDatesOfSale);

        //VBox с наименованиями
        ctgs.setSpacing(5);
        ctgs.setAlignment(Pos.TOP_LEFT);
//        Label titleLbl = new Label("Дата:");
        Button titleBtn = new Button("Дата:");
        titleBtn.setFocusTraversable(false);
        titleBtn.setMinHeight(25);
        titleBtn.setMaxHeight(25);
        titleBtn.setMinWidth(120);
        titleBtn.setMaxWidth(120);
        titleBtn.setFont(titleFont);
        ctgs.getChildren().add(titleBtn);
        for (Category category : categories) {
            Button categoryBtn = new Button(category.getCategoryName() + ":");
            categoryBtn.setFocusTraversable(false);
            categoryBtn.setMinWidth(120);
            categoryBtn.setMaxWidth(120);
            categoryBtn.setMaxHeight(25);
            categoryBtn.setMinHeight(25);
            categoryBtn.setFont(titleFont);
            ctgs.getChildren().add(categoryBtn);
        }


        //для каждой даты свой VBox с данными
        for (LocalDate date :
                listOfDatesOfSale) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(5);
            Label label = new Label(date.toString());
            label.setMinHeight(25);
            label.setMaxHeight(25);
            label.setFont(titleFont);
            vBox.getChildren().add(label);

            for (Category category : categories) {
                double value = 0;
                TextField textField = new TextField();
                if (category instanceof PrimaryBrandCategory) {
                    ArrayList<String> brandNames = ((PrimaryBrandCategory) category).getBrandNames();
                    for (String brandName : brandNames) {
                        for (ExcelSellLine line : excelSellLineArrayList) {
                            if (line.getManufacturer().equals(brandName) && line.getDateOfSale().equals(date)) {
                                value += line.getTotalPriceExcludingBonuses() + line.getSpentPetshopBonuses() + line.getSpentSpasiboBonuses();
                            }
                        }
                    }
                } else if (category instanceof ConditionCategory) {
                    for(ExcelSellLine line : excelSellLineArrayList){
                        if (((ConditionCategory) category).getConditionType().equals(ConditionType.VIA)){
                            if((line.getName().toUpperCase().startsWith("ВИА") || line.getName().toUpperCase().startsWith("ВВА")) && line.getDateOfSale().equals(date)){
                                value += line.getTotalPriceExcludingBonuses() + line.getSpentPetshopBonuses() + line.getSpentSpasiboBonuses();
                            }
                        } else if (((ConditionCategory) category).getConditionType().equals(ConditionType.VES)) {
                            if((line.getArticle().endsWith("099") && line.getArticle().length() > 5) && line.getDateOfSale().equals(date)){
                                value += line.getTotalPriceExcludingBonuses() + line.getSpentPetshopBonuses() + line.getSpentSpasiboBonuses();
                            }
                        } else if (((ConditionCategory) category).getConditionType().equals(ConditionType.ALL)) {
                            if(line.getDateOfSale().equals(date)){
                                value += line.getTotalPriceExcludingBonuses() + line.getSpentPetshopBonuses() + line.getSpentSpasiboBonuses();
                            }
                        }
                    }
                }
                textField.setText(String.format(Locale.US, "%.2f", value));
                textField.setEditable(false);
                textField.setAlignment(Pos.CENTER);
                textField.setFont(font);
                textField.setMinHeight(25);
                textField.setMaxHeight(25);
                textField.setMinWidth(100);
                textField.setMaxWidth(100);
                textField.setFocusTraversable(false);
                vBox.getChildren().add(textField);
            }


            plans.getChildren().add(vBox);
        }
    }
}