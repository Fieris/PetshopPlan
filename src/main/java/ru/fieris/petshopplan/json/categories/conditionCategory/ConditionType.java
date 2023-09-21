package ru.fieris.petshopplan.json.categories.conditionCategory;

public enum ConditionType {
    VIA("Считает весь ВИА и ВВА товар"),
    VES("Считает весь весовой товар (Артикул заканчивается на 099 И длина больше 5)"),
    ALL("Считает весь товарооборот");

    final String info;

    ConditionType(String info){
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
