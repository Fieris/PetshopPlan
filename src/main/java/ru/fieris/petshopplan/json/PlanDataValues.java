package ru.fieris.petshopplan.json;


//Поле name Должно совпадать в полями в конструкторе PlanData
@Deprecated
public enum PlanDataValues {
    GO("Go!"),
    ALMO("Almo Nature"),
    ROCAT("Ro Cat"),
    BARKING("Barking Heads"),
    ORGANIX("Organix"),
    FLORIDA("Florida"),
    SAVITA("Savita"),
    PURINA("Purina"),
    RCVET("Royal Canin Вет"),
    RCALL("Royal Canin Весь"),
    HILLSPD("Hill's Prescription Diet"),
    HILLSSP("Hill's Science Plan"),
    HILLSVETKONC("Hill's Вет консервы"),
    HILLSKONC("Hill's консервы"),
    VIA("ВИА"),
    VES("ВЕС"),
    ALLTO("Весь ТО"),
    IMPORT("Весь импорт");

    private final String name;

    PlanDataValues(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
