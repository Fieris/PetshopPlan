package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.fieris.petshopplan.Application;
import ru.fieris.petshopplan.customControls.HBoxes.CustomHBox;
import ru.fieris.petshopplan.customControls.HBoxes.HBoxStyles;
import ru.fieris.petshopplan.customControls.PlanTextField;
import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.json.JsonData;
import ru.fieris.petshopplan.json.JsonMapper;
import ru.fieris.petshopplan.json.categories.Category;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.ZpProperty;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public abstract class PlanHBox extends CustomHBox {
    JsonData jsonData = JsonMapper.readFromJson();

    Category category;
    PlanTextField planTextField;
    TextField factTextField = new TextField();
    TextField percentTextField = new TextField();
    TextField remainsTextField = new TextField();
    TextField doPerDayTextField = new TextField();
    TextField factTodayTextField = new TextField();
    TextField remainsTodayTextField = new TextField();
    TextField percentGetZpTextField = new TextField();
    TextField zpPerManTextField = new TextField();
    MenuButton menuButton = new MenuButton();

    public PlanHBox(Category category, HBoxStyles style) {
        super(style);
        this.category = category;

        //иницилизация меню
        menuButton.setFocusTraversable(false);
        menuButton.setMinWidth(130);
        menuButton.setMaxWidth(130);
        menuButton.setText(category.getCategoryName());
        menuButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        menuButton.setAlignment(Pos.CENTER);

        //создание списка из определенной категории
        ArrayList<String> langs = new ArrayList<>();
        if (category instanceof PrimaryBrandCategory) {
            langs = ((PrimaryBrandCategory) category).getBrandNames();
        } else if (category instanceof SecondaryBrandCategory) {
            langs = ((SecondaryBrandCategory) category).getBrandNames();
        } else if (category instanceof ConditionCategory) {
            langs.add(((ConditionCategory) category).getConditionType().getInfo());
        }

        //Заполнение итемов
        if (langs.isEmpty()) {
            menuButton.getItems().add(new MenuItem("[пусто]"));
        }
        for (String lang : langs) {
            menuButton.getItems().add(new MenuItem(lang));
        }

        //PlanTextField с планом
        planTextField = new PlanTextField(category);
        planTextField.setPrefSize(100, 1);
        planTextField.setMinWidth(100);
        planTextField.setMaxWidth(100);


        //настройка TextField с фактическим выполнением
        factTextField.setEditable(false);
        factTextField.setMaxWidth(100);
        factTextField.setText("0");
        factTextField.setAlignment(Pos.CENTER);
        factTextField.setFocusTraversable(false);

        //настройка TextField с процентом
        percentTextField.setEditable(false);
        percentTextField.setMaxWidth(70);
        percentTextField.setText("-");
        percentTextField.setAlignment(Pos.CENTER);
        percentTextField.setFocusTraversable(false);

        if (style == HBoxStyles.PLAN) {

            //настройка TextField с остатком
            remainsTextField.setEditable(false);
            remainsTextField.setMaxWidth(100);
            remainsTextField.setText("-");
            remainsTextField.setAlignment(Pos.CENTER);
            remainsTextField.setFocusTraversable(false);

            doPerDayTextField.setEditable(false);
            doPerDayTextField.setMaxWidth(100);
            doPerDayTextField.setText("-");
            doPerDayTextField.setAlignment(Pos.CENTER);
            doPerDayTextField.setFocusTraversable(false);

            Label space = new Label("space");
            space.setMinWidth(25);
            space.setMaxWidth(25);
            space.setVisible(false);

            factTodayTextField.setEditable(false);
            factTodayTextField.setMaxWidth(100);
            factTodayTextField.setText("-");
            factTodayTextField.setAlignment(Pos.CENTER);
            factTodayTextField.setFocusTraversable(false);

            remainsTodayTextField.setEditable(false);
            remainsTodayTextField.setMaxWidth(100);
            remainsTodayTextField.setText("-");
            remainsTodayTextField.setAlignment(Pos.CENTER);
            remainsTodayTextField.setFocusTraversable(false);


            this.getChildren().addAll(menuButton, planTextField, factTextField, percentTextField,
                    remainsTextField, doPerDayTextField, space, factTodayTextField, remainsTodayTextField);


        } else if (style == HBoxStyles.ZP) {
            percentGetZpTextField.setEditable(false);
            percentGetZpTextField.setMaxWidth(70);
            percentGetZpTextField.setText("0");
            percentGetZpTextField.setAlignment(Pos.CENTER);
            percentGetZpTextField.setFocusTraversable(false);

            zpPerManTextField.setEditable(false);
            zpPerManTextField.setMaxWidth(100);
            zpPerManTextField.setText("0");
            zpPerManTextField.setAlignment(Pos.CENTER);
            zpPerManTextField.setFocusTraversable(false);

            this.getChildren().addAll(menuButton, planTextField, factTextField, percentTextField, percentGetZpTextField, zpPerManTextField);
        }


        //Если план не выставлен, блокирует остальные поля
        if (planTextField.getText().equals("0.0")) {
            percentTextField.setDisable(true);
            remainsTextField.setDisable(true);
            doPerDayTextField.setDisable(true);
            factTodayTextField.setDisable(true);
            remainsTodayTextField.setDisable(true);

        }

    }

    public void calculate(Calculator calculator) {
        if (Objects.isNull(category)) return;


        factTextField.setText(String.format(Locale.US, "%.2f", calculator.getFact(category)));


        if (!planTextField.getText().equals("0.0")) {
            String strPercent = String.format(Locale.US, "%.2f", calculator.getPercent(Double.parseDouble(planTextField.getText()), Double.parseDouble(factTextField.getText())));
            strPercent += " %";
            percentTextField.setText(strPercent);
            percentTextField.setDisable(false);
        } else {
            percentTextField.setText("-");
            percentTextField.setDisable(true);
        }

        if (!planTextField.getText().equals("0.0")) {
            remainsTextField.setText(String.format(Locale.US, "%.2f", calculator.getRemains(Double.parseDouble(planTextField.getText()), Double.parseDouble(factTextField.getText()))));
            remainsTextField.setDisable(false);
        } else {
            remainsTextField.setText("-");
            remainsTextField.setDisable(true);
        }


        //логика работы ячейки "В день"
        double perDay = 0;
        if (!planTextField.getText().equals("0.0")) {
            perDay = calculator.getDoPerDay(Double.parseDouble(remainsTextField.getText()), category);
            doPerDayTextField.setText(String.format(Locale.US, "%.2f", perDay));
            doPerDayTextField.setDisable(false);
        } else {
            doPerDayTextField.setText("-");
            doPerDayTextField.setDisable(true);
        }


        if (!planTextField.getText().equals("0.0")) {
            factTodayTextField.setText(String.format(Locale.US, "%.2f", calculator.getFactToday(category)));
            factTodayTextField.setDisable(false);
        } else {
            factTodayTextField.setText("-");
            factTodayTextField.setDisable(true);
        }


        //логика работы ячейки "Осталось в день"
        if (!planTextField.getText().equals("0.0")) {
            double remainsToday = perDay - Double.parseDouble(factTodayTextField.getText());
            remainsTodayTextField.setText(String.format(Locale.US, "%.2f", remainsToday));


            remainsTodayTextField.setDisable(false);
        } else {
            remainsTodayTextField.setText("-");
            remainsTodayTextField.setDisable(true);
        }


        //логика работы ячейки процент в зп
        percentGetZpTextField.setText(String.format(Locale.US, "%.2f", calculator.getZpPercent(calculator.getPercent(Double.parseDouble(planTextField.getText()),
                Double.parseDouble(factTextField.getText())), category.getZpProperty())));


        zpPerManTextField.setText(String.format(Locale.US, "%.2f", calculator.getZpPerMan(Double.parseDouble(factTextField.getText()),
                calculator.getPercent(Double.parseDouble(planTextField.getText()), Double.parseDouble(factTextField.getText())),
                Double.parseDouble(percentGetZpTextField.getText()), category.getZpProperty())));

        colorize();
    }

    private void colorize() {
        String completeStyle = "-fx-background-color: #32cd32;";
        double fact = 0;
        double plan = 0;
        double factToday = 0;
        double doPerDay = 0;
        try {
            fact = Double.parseDouble(factTextField.getText());
            plan = Double.parseDouble(planTextField.getText());
            factToday = Double.parseDouble(factTodayTextField.getText());
            doPerDay = Double.parseDouble(doPerDayTextField.getText());
        } catch (Exception exc) {
            //exc
        }


        if (fact >= plan && plan != 0) {
            menuButton.setStyle(completeStyle);
            planTextField.setStyle(completeStyle);
            factTextField.setStyle(completeStyle);
            percentTextField.setStyle(completeStyle);
            remainsTextField.setStyle(completeStyle);
            doPerDayTextField.setStyle(completeStyle);
            factTodayTextField.setStyle(completeStyle);
            remainsTodayTextField.setStyle(completeStyle);
            percentGetZpTextField.setStyle(completeStyle);
            zpPerManTextField.setStyle(completeStyle);
        } else if (factToday >= doPerDay && plan != 0) {
            menuButton.setStyle("");
            planTextField.setStyle("");
            factTextField.setStyle("");
            percentTextField.setStyle("");
            remainsTextField.setStyle("");
            doPerDayTextField.setStyle("");
            percentGetZpTextField.setStyle("");
            zpPerManTextField.setStyle(completeStyle);
            factTodayTextField.setStyle(completeStyle);
            remainsTodayTextField.setStyle(completeStyle);
        } else {
            menuButton.setStyle("");
            planTextField.setStyle("");
            factTextField.setStyle("");
            percentTextField.setStyle("");
            remainsTextField.setStyle("");
            doPerDayTextField.setStyle("");
            factTodayTextField.setStyle("");
            remainsTodayTextField.setStyle("");
            percentGetZpTextField.setStyle("");
            zpPerManTextField.setStyle("");
        }

    }

    public String getZpPerManTextField() {
        return zpPerManTextField.getText();
    }

}

