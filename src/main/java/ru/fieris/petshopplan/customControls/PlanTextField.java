package ru.fieris.petshopplan.customControls;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.JsonData;
import ru.fieris.petshopplan.json.JsonMapper;
import ru.fieris.petshopplan.json.categories.Category;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class PlanTextField extends TextField {
    MenuItem changeMenuItem = new MenuItem("Изменить");
    ContextMenu contextMenu = new ContextMenu(changeMenuItem);
    JsonData jsonData = JsonMapper.readFromJson();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");

    public PlanTextField(Category category){
        setContextMenu(contextMenu);
        this.setFocusTraversable(true);
        this.setText(String.valueOf(category.getPlanValue()));
        this.setAlignment(Pos.CENTER);
        this.setEditable(false);



        //tooltip
        Tooltip tooltip = new Tooltip(simpleDateFormat.format(category.getPlanChangeDate()));
        this.setTooltip(tooltip);

        //onAction в пкм меню
        changeMenuItem.setOnAction(e -> {
            onAction(category, tooltip);
        });


        //onAction по нажатию Enter
        this.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                onAction(category, tooltip);
            }
        });

    }

    //Проверяет переданную строку на совместимость с double, если введено число или пустое значение
    //возвращает -1
    private double parseStringToDouble(String string) {
        //создание алерта
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("");

        double result;
        //убирание пробелов слева и справа
        string = string.trim();
        if(string.contains(",")) string = string.replace(",", ".");
        try{
            result = Double.parseDouble(string);
        } catch (NullPointerException exc){
            alert.setContentText(exc.toString());
            alert.show();
            return -1;
        } catch (NumberFormatException exc) {
            alert.setContentText(exc.getLocalizedMessage());
            alert.show();
            return -1;
        }

        return result;
    }

    private void onAction(Category category, Tooltip tooltip){
        this.selectAll();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("");
        dialog.initOwner(Application.getMainStage());
        dialog.setTitle(category.getCategoryName());
        dialog.getEditor().setText(this.getText());
        final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, event -> {

            double value = parseStringToDouble(dialog.getEditor().getText());

            if(value != -1){
                this.setText(String.valueOf(value));
                if(category instanceof PrimaryBrandCategory){
                    ArrayList<PrimaryBrandCategory> brandCategories = jsonData.getPrimaryBrandCategories();
                    jsonData.getPrimaryBrandCategories().get(brandCategories.indexOf(category)).setPlanValue(value);
                } else if (category instanceof ConditionCategory) {
                    ArrayList<ConditionCategory> conditionCategories = jsonData.getConditionCategories();
                    jsonData.getConditionCategories().get(conditionCategories.indexOf(category)).setPlanValue(value);
                } else if (category instanceof SecondaryBrandCategory) {
                    ArrayList<SecondaryBrandCategory> secondaryBrandCategories = jsonData.getSecondaryBrandCategories();
                    jsonData.getSecondaryBrandCategories().get(secondaryBrandCategories.indexOf(category)).setPlanValue(value);
                }


                //установка времени в tooltip
                Date date = new Date();
                tooltip.setText(simpleDateFormat.format(date));
                if(category instanceof PrimaryBrandCategory){
                    ArrayList<PrimaryBrandCategory> brandCategories = jsonData.getPrimaryBrandCategories();
                    jsonData.getPrimaryBrandCategories().get(brandCategories.indexOf(category)).setPlanChangeDate(date);
                } else if (category instanceof ConditionCategory) {
                    ArrayList<ConditionCategory> conditionCategories = jsonData.getConditionCategories();
                    jsonData.getConditionCategories().get(conditionCategories.indexOf(category)).setPlanChangeDate(date);
                } else if (category instanceof SecondaryBrandCategory) {
                    ArrayList<SecondaryBrandCategory> secondaryBrandCategories = jsonData.getSecondaryBrandCategories();
                    jsonData.getSecondaryBrandCategories().get(secondaryBrandCategories.indexOf(category)).setPlanChangeDate(date);
                }


                //Если эксель конвертер существует и в нем находится валидный файл -> запускает просчет
                if(Objects.nonNull(Application.getMainController().getExcelConverter())){
                    if(Application.getMainController().getExcelConverter().isValidFile()){
                        //PlanHBox customHBox = (PlanHBox) this.getParent();
                        //customHBox.calculate(new Calculator(Application.getMainController().getExcelConverter().getExcelArray()));
                        Application.getMainController().initialize();
                        Application.getMainController().searchAndCalc();

                    }
                }

                JsonMapper.writeToJson(jsonData);
            }
        });
        dialog.showAndWait();



    }
}
