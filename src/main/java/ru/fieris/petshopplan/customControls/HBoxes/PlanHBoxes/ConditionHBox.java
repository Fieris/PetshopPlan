package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.fieris.petshopplan.customControls.PlanTextField;
import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.util.Locale;
import java.util.Objects;

public class ConditionHBox extends PlanHBox{
    ConditionCategory conditionCategory;

    public ConditionHBox(ConditionCategory conditionCategory) {
        super(conditionCategory);
        this.conditionCategory = conditionCategory;

    }
}
