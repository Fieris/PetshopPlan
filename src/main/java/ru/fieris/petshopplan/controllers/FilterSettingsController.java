package ru.fieris.petshopplan.controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.json.*;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.ZpProperty;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class FilterSettingsController {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab primaryTab;
    @FXML
    private Tab secondaryTab;
    @FXML
    private Tab conditionTab;

    @FXML
    private ListView<String> lsPrimaryBrandCategories;
    @FXML
    private ListView<String> lsPrimaryBrandNames;
    @FXML
    private ListView<String> lsConditionCategories;
    @FXML
    private TextArea lsConditionInfo;
    @FXML
    private ListView<String> lsSecondaryBrandCategories;
    @FXML
    private ListView<String> lsSecondaryBrandNames;

    @FXML
    private ChoiceBox<String> primaryZpTypeCB;
    @FXML
    private ChoiceBox<String> secondaryZpTypeCB;
    @FXML
    private ChoiceBox<String> conditionZpTypeCB;


    @FXML
    private void initialize() {
        JsonData jsonData = JsonMapper.readFromJson();
        ArrayList<String> stringCategories = new ArrayList<>();

        //Иницилизация списка основных категорий
        for (PrimaryBrandCategory line : jsonData.getPrimaryBrandCategories()) {
            stringCategories.add(line.getCategoryName());
        }
        ObservableList<String> primaryBrandCategories = FXCollections.observableArrayList(stringCategories);
        lsPrimaryBrandCategories.setItems(primaryBrandCategories);

        //Иницилизация списка категорий по условиям
        stringCategories.clear();
        for (ConditionCategory line : jsonData.getConditionCategories()) {
            String convertedStr = line.getCategoryName();
            if (line.isHidden()) {
                convertedStr = "[СКРЫТ] " + convertedStr;
            }
            stringCategories.add(convertedStr);
        }
        ObservableList<String> conditionCategories = FXCollections.observableArrayList(stringCategories);
        lsConditionCategories.setItems(conditionCategories);

        //Иницилизация списка дополнительных категорий
        stringCategories.clear();
        for (SecondaryBrandCategory line : jsonData.getSecondaryBrandCategories()) {
            stringCategories.add(line.getCategoryName());
        }
        ObservableList<String> secondaryBrandCategories = FXCollections.observableArrayList(stringCategories);
        lsSecondaryBrandCategories.setItems(secondaryBrandCategories);


        //заполнение чекбоксов с типом ЗП
        lsPrimaryBrandCategories.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            int selectedIndex = lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) return;


            primaryZpTypeCB.getItems().clear();
            for (ZpProperty zpProperty : jsonData.getPrimaryBrandCategories().get(0).getZpProperty().getAllTypes()) {
                primaryZpTypeCB.getItems().add(zpProperty.getName());
            }

            if (Objects.nonNull(jsonData.getPrimaryBrandCategories().get(selectedIndex).getZpProperty())) {
                primaryZpTypeCB.getSelectionModel().select(jsonData.getPrimaryBrandCategories().get(selectedIndex).getZpProperty().getName());
            } else {
                primaryZpTypeCB.getSelectionModel().select("[Не указан]");
            }

            primaryZpTypeCB.getSelectionModel().selectedItemProperty().addListener((observableValue1, oldValue1, newValue1) -> {
                if (primaryZpTypeCB.getItems().isEmpty()) return;
                jsonData.getPrimaryBrandCategories().get(lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex()).setZpProperty(ZpProperty.ALLTO.findByName(newValue1));
                JsonMapper.writeToJson(jsonData);
            });

            ObservableList<String> brandNames = FXCollections.observableArrayList(jsonData.getPrimaryBrandCategories().get(selectedIndex).getBrandNames());
            lsPrimaryBrandNames.setItems(brandNames);
        });

        lsSecondaryBrandCategories.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            int selectedIndex = lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) return;

            secondaryZpTypeCB.getItems().clear();
            for(ZpProperty zpProperty : jsonData.getPrimaryBrandCategories().get(0).getZpProperty().getAllTypes()){
                secondaryZpTypeCB.getItems().add(zpProperty.getName());
            }

            if(Objects.nonNull(jsonData.getSecondaryBrandCategories().get(selectedIndex).getZpProperty())){
                secondaryZpTypeCB.getSelectionModel().select(jsonData.getSecondaryBrandCategories().get(selectedIndex).getZpProperty().getName());
            } else {
                secondaryZpTypeCB.getSelectionModel().select("[Не указан]");
            }

            secondaryZpTypeCB.getSelectionModel().selectedItemProperty().addListener((observableValue1, oldValue1, newValue1) -> {
                if(secondaryZpTypeCB.getItems().isEmpty()) return;
                jsonData.getSecondaryBrandCategories().get(lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex()).setZpProperty(ZpProperty.ALLTO.findByName(newValue1));
                JsonMapper.writeToJson(jsonData);
            });

            ObservableList<String> brandNames = FXCollections.observableArrayList(jsonData.getSecondaryBrandCategories().get(selectedIndex).getBrandNames());
            lsSecondaryBrandNames.setItems(brandNames);
        });

        lsConditionCategories.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            int selectedIndex = lsConditionCategories.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) return;

            conditionZpTypeCB.getItems().clear();
            for (ZpProperty zpProperty : jsonData.getConditionCategories().get(0).getZpProperty().getAllTypes()) {
                conditionZpTypeCB.getItems().add(zpProperty.getName());
            }

            if (Objects.nonNull(jsonData.getConditionCategories().get(selectedIndex).getZpProperty())) {
                conditionZpTypeCB.getSelectionModel().select(jsonData.getConditionCategories().get(selectedIndex).getZpProperty().getName());
            } else {
                primaryZpTypeCB.getSelectionModel().select("[Не указан]");
            }

            conditionZpTypeCB.getSelectionModel().selectedItemProperty().addListener((observableValue1, oldValue1, newValue1) -> {
                if (conditionZpTypeCB.getItems().isEmpty()) return;
                jsonData.getConditionCategories().get(lsConditionCategories.getSelectionModel().getSelectedIndex()).setZpProperty(ZpProperty.ALLTO.findByName(newValue1));
                JsonMapper.writeToJson(jsonData);
            });

            lsConditionInfo.setText(jsonData.getConditionCategories().get(selectedIndex).getConditionType().getInfo());
        });




        //в каждом табе в левом листе выбирается 0 элемент, чтобы избежать ошибок
        lsPrimaryBrandCategories.getSelectionModel().select(0);
        lsSecondaryBrandCategories.getSelectionModel().select(0);
        lsConditionCategories.getSelectionModel().select(0);
    }

    //кнопка добавления нового поля
    @FXML
    private void addBrandItem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Название бренда");
        dialog.setContentText("");
        dialog.setHeaderText("");


        final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, event -> {
            dialog.getEditor().setText(dialog.getEditor().getText().trim());

            //проверка на введенное пустое значение
            if (dialog.getEditor().getText().isEmpty()) {
                return;
            }

            JsonData jsonData = JsonMapper.readFromJson();

            if (tabPane.getSelectionModel().getSelectedItem().equals(primaryTab)) {
                ArrayList<String> brandNames = jsonData.getPrimaryBrandCategories().get(lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex()).getBrandNames();
                //Проверка на повторение
                for (String line : brandNames) {
                    if (line.equalsIgnoreCase(dialog.getEditor().getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("");
                        alert.setTitle("Ошибка");
                        alert.setContentText("Поле с названием " + dialog.getEditor().getText() + " уже существует");
                        alert.show();
                        return;
                    }
                }

                brandNames.add(dialog.getEditor().getText());
                jsonData.getPrimaryBrandCategories().get(lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex()).setBrandNames(brandNames);

                JsonMapper.writeToJson(jsonData);

                int selectedIndex = lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex();
                if (selectedIndex == -1) {
                    return;
                }
                ObservableList<String> langs = FXCollections.observableArrayList(jsonData.getPrimaryBrandCategories().get(selectedIndex).getBrandNames());
                lsPrimaryBrandNames.setItems(langs);
            } else if (tabPane.getSelectionModel().getSelectedItem().equals(secondaryTab)) {
                ArrayList<String> brandNames = jsonData.getSecondaryBrandCategories().get(lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex()).getBrandNames();
                //Проверка на повторение
                for (String line : brandNames) {
                    if (line.equalsIgnoreCase(dialog.getEditor().getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("");
                        alert.setTitle("Ошибка");
                        alert.setContentText("Поле с названием " + dialog.getEditor().getText() + " уже существует");
                        alert.show();
                        return;
                    }
                }

                brandNames.add(dialog.getEditor().getText());
                jsonData.getSecondaryBrandCategories().get(lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex()).setBrandNames(brandNames);

                JsonMapper.writeToJson(jsonData);

                int selectedIndex = lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex();
                if (selectedIndex == -1) return;
                ObservableList<String> langs = FXCollections.observableArrayList(jsonData.getSecondaryBrandCategories().get(selectedIndex).getBrandNames());
                lsSecondaryBrandNames.setItems(langs);
            }


        });

        dialog.showAndWait();


    }

    @FXML
    private void removeBrandItem() {
        Alert alert;
        if (tabPane.getSelectionModel().getSelectedItem().equals(primaryTab)) {
            if (Objects.isNull(lsPrimaryBrandNames.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Выберите поле для удаления");
                alert.setHeaderText("");
                alert.setTitle("Ошибка");
                alert.show();
                return;
            }
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтвердить удаление");
            alert.setHeaderText("");
            alert.setContentText("Удалить объект " + lsPrimaryBrandNames.getSelectionModel().getSelectedItem() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }

            JsonData jsonData = JsonMapper.readFromJson();
            ArrayList<String> brandNames = jsonData.getPrimaryBrandCategories().get(lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex()).getBrandNames();
            brandNames.remove(lsPrimaryBrandNames.getSelectionModel().getSelectedIndex());
            JsonMapper.writeToJson(jsonData);

            int selectedIndex = lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                return;
            }
            ObservableList<String> langs = FXCollections.observableArrayList(jsonData.getPrimaryBrandCategories().get(selectedIndex).getBrandNames());
            lsPrimaryBrandNames.setItems(langs);
        } else if (tabPane.getSelectionModel().getSelectedItem().equals(secondaryTab)) {
            if (Objects.isNull(lsSecondaryBrandNames.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Выберите поле для удаления");
                alert.setHeaderText("");
                alert.setTitle("Ошибка");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтвердить удаление");
            alert.setHeaderText("");
            alert.setContentText("Удалить объект " + lsSecondaryBrandNames.getSelectionModel().getSelectedItem() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }

            JsonData jsonData = JsonMapper.readFromJson();
            ArrayList<String> brandNames = jsonData.getSecondaryBrandCategories().get(lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex()).getBrandNames();
            brandNames.remove(lsSecondaryBrandNames.getSelectionModel().getSelectedIndex());
            JsonMapper.writeToJson(jsonData);

            int selectedIndex = lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                return;
            }
            ObservableList<String> langs = FXCollections.observableArrayList(jsonData.getSecondaryBrandCategories().get(selectedIndex).getBrandNames());
            lsSecondaryBrandNames.setItems(langs);
        }


    }

    @FXML
    private void addCategoryItem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Название категории");
        dialog.setContentText("");
        dialog.setHeaderText("");

        final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, event -> {
            dialog.getEditor().setText(dialog.getEditor().getText().trim());

            if (dialog.getEditor().getText().isEmpty()) {
                return;
            }

            if (tabPane.getSelectionModel().getSelectedItem().equals(primaryTab)) {
                JsonData jsonData = JsonMapper.readFromJson();
                ArrayList<PrimaryBrandCategory> brandCategories = jsonData.getPrimaryBrandCategories();

                //Проверка на повторение имен
                for (PrimaryBrandCategory line : brandCategories) {
                    if (line.getCategoryName().equalsIgnoreCase(dialog.getEditor().getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("");
                        alert.setTitle("Ошибка");
                        alert.setContentText("Категория с названием " + dialog.getEditor().getText() + " уже существует");
                        alert.show();
                        return;
                    }
                }

                brandCategories.add(new PrimaryBrandCategory(dialog.getEditor().getText()));
                JsonMapper.writeToJson(jsonData);
                reloadScene();
            } else if (tabPane.getSelectionModel().getSelectedItem().equals(secondaryTab)) {
                JsonData jsonData = JsonMapper.readFromJson();
                ArrayList<SecondaryBrandCategory> brandCategories = jsonData.getSecondaryBrandCategories();

                //Проверка на повторение имен
                for (SecondaryBrandCategory line : brandCategories) {
                    if (line.getCategoryName().equalsIgnoreCase(dialog.getEditor().getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("");
                        alert.setTitle("Ошибка");
                        alert.setContentText("Категория с названием " + dialog.getEditor().getText() + " уже существует");
                        alert.show();
                        return;
                    }
                }

                brandCategories.add(new SecondaryBrandCategory(dialog.getEditor().getText()));
                JsonMapper.writeToJson(jsonData);
                reloadScene();
            }

        });

        dialog.showAndWait();


    }

    @FXML
    private void removeCategoryItem() {
        Alert alert;
        if (tabPane.getSelectionModel().getSelectedItem().equals(primaryTab)) {
            if (Objects.isNull(lsPrimaryBrandCategories.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Выберите категорию для удаления");
                alert.setHeaderText("");
                alert.setTitle("Ошибка");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтвердить удаление");
            alert.setHeaderText("");
            alert.setContentText("Удалить категорию " + lsPrimaryBrandCategories.getSelectionModel().getSelectedItem() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }

            JsonData jsonData = JsonMapper.readFromJson();
            ArrayList<PrimaryBrandCategory> brandCategories = jsonData.getPrimaryBrandCategories();
            brandCategories.remove(lsPrimaryBrandCategories.getSelectionModel().getSelectedIndex());
            JsonMapper.writeToJson(jsonData);
            reloadScene();

            //Если удаляются все значения из списка категории, заполняет правую таблицу пустотой
            if (lsPrimaryBrandCategories.getItems().isEmpty()) lsPrimaryBrandNames.setItems(null);
        } else if (tabPane.getSelectionModel().getSelectedItem().equals(secondaryTab)) {
            if (Objects.isNull(lsSecondaryBrandCategories.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Выберите категорию для удаления");
                alert.setHeaderText("");
                alert.setTitle("Ошибка");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтвердить удаление");
            alert.setHeaderText("");
            alert.setContentText("Удалить категорию " + lsSecondaryBrandCategories.getSelectionModel().getSelectedItem() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }

            JsonData jsonData = JsonMapper.readFromJson();
            ArrayList<SecondaryBrandCategory> brandCategories = jsonData.getSecondaryBrandCategories();
            brandCategories.remove(lsSecondaryBrandCategories.getSelectionModel().getSelectedIndex());
            JsonMapper.writeToJson(jsonData);
            reloadScene();

            //Если удаляются все значения из списка категории, заполняет правую таблицу пустотой
            if (lsSecondaryBrandCategories.getItems().isEmpty()) lsSecondaryBrandNames.setItems(null);
        }

    }

    @FXML
    private void resetCategories() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Application.getMainStage());
        alert.setTitle("Подтверждение сброса");
        alert.setHeaderText("");
        alert.setContentText("Все категории будут выставлены по умолчанию, продолжить?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            JsonMapper.setDefault();
            reloadScene();
        }
    }

    //TODO пока работает только с conditionCategory
    //последний выбранный индекс
    int lastSelectedIndex = 0;

    @FXML
    private void hideCategory() {
        Alert alert;
        JsonData jsonData = JsonMapper.readFromJson();
        boolean hidden;
        if (tabPane.getSelectionModel().getSelectedItem().equals(conditionTab)) {
            if (Objects.isNull(lsConditionCategories.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Выберите поле для скрыть/показать");
                alert.setHeaderText("");
                alert.setTitle("Ошибка");
                alert.show();
                return;
            }

            lastSelectedIndex = lsConditionCategories.getSelectionModel().getSelectedIndex();

            ArrayList<ConditionCategory> categories = jsonData.getConditionCategories();
            hidden = categories.get(lsConditionCategories.getSelectionModel().getSelectedIndex()).isHidden();
            if (hidden) {
                categories.get(lsConditionCategories.getSelectionModel().getSelectedIndex()).setHidden(false);
            } else {
                categories.get(lsConditionCategories.getSelectionModel().getSelectedIndex()).setHidden(true);
            }
            JsonMapper.writeToJson(jsonData);
            reloadScene();
        }
    }

    void reloadScene() {
        JsonData jsonData = JsonMapper.readFromJson();
        ArrayList<String> stringCategories = new ArrayList<>();
        for (PrimaryBrandCategory line : jsonData.getPrimaryBrandCategories()) {
            stringCategories.add(line.getCategoryName());
        }


        ObservableList<String> primaryCategories = FXCollections.observableArrayList(stringCategories);
        lsPrimaryBrandCategories.setItems(primaryCategories);
        lsPrimaryBrandCategories.getSelectionModel().select(0);

        stringCategories.clear();
        for (SecondaryBrandCategory line : jsonData.getSecondaryBrandCategories()) {
            stringCategories.add(line.getCategoryName());
        }
        ObservableList<String> secondaryCategories = FXCollections.observableArrayList(stringCategories);
        lsSecondaryBrandCategories.setItems(secondaryCategories);
        lsSecondaryBrandCategories.getSelectionModel().select(0);

        stringCategories.clear();
        for (ConditionCategory line : jsonData.getConditionCategories()) {
            String convertedStr = line.getCategoryName();
            if (line.isHidden()) {
                convertedStr = "[СКРЫТ] " + convertedStr;
            }
            stringCategories.add(convertedStr);
        }
        ObservableList<String> conditionCategories = FXCollections.observableArrayList(stringCategories);
        lsConditionCategories.setItems(conditionCategories);
        lsConditionCategories.getSelectionModel().select(lastSelectedIndex);
    }

}

