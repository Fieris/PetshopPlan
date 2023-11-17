package ru.fieris.petshopplan.json.categories.conditionCategory;

import ru.fieris.petshopplan.json.categories.Category;
import ru.fieris.petshopplan.json.categories.ZpProperty;

public class ConditionCategory extends Category {
    private ConditionType conditionType;

    public ConditionCategory(String categoryName, ConditionType conditionType, double planValue, ZpProperty zpProperty){
        this.categoryName = categoryName;
        this.conditionType = conditionType;
        this.planValue = planValue;
        this.zpProperty = zpProperty;
    }

    public ConditionCategory(String categoryName){
        this.categoryName = categoryName;
        this.planValue = 0;
    }

    public ConditionCategory(){}


    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }
}
