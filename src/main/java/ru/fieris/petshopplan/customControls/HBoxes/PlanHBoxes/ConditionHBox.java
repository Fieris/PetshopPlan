package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import ru.fieris.petshopplan.customControls.HBoxes.HBoxStyles;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

public class ConditionHBox extends PlanHBox{
    ConditionCategory conditionCategory;

    public ConditionHBox(ConditionCategory conditionCategory, HBoxStyles style) {
        super(conditionCategory, style);
        this.conditionCategory = conditionCategory;

    }
}
