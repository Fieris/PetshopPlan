package ru.fieris.petshopplan.json.categories.conditionCategory;

@Deprecated
public enum ConditionSigns {
    EQUALS("Равно"),
    NOT_EQUALS("Не равно"),
    CONTAINS("Содержит"),
    NOT_CONTAINS("Не содержит"),
    STARTS_WITH("Начинается на"),
    ENDS_WITH("Заканчивается на"),
    LENGTH("Длина равна"),
    LENGTH_MORE_THAN("Длина больше чем"),
    LENGTH_LESS_THAN("Длина меньше чем"),
    ALL("ВСЁ ВМЕСТЕ");

    final String text;

    ConditionSigns(String text){
        this.text = text;
    }
}
