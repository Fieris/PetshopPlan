package ru.fieris.petshopplan.customControls.HBoxes;

public enum HBoxStyles {
    PLAN("Полный план выполнения"),
    ZP("Сокращенный вариант для отображения зп");

    final String description;

    HBoxStyles(String description){
        this.description = description;
    }
}
