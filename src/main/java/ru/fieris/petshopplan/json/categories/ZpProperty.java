package ru.fieris.petshopplan.json.categories;

import java.util.ArrayList;

public enum ZpProperty {

    STD_2_3_5("2/3/5%", ">100% = 5%\n>85% <99% = 3%\n<85% = 2%"),
    STD_2_3_6("2/3/6%",">100% = 6%\n>85% <99% = 3%\n<85% = 2%"),
    STD_2_4_6("2/4/6%", ">100% = 6%\n>85% <99% = 4%\n<85% = 2%"),
    RC("RC", ">100% = 2000р\n<100% = 0р"),
    PURINA("Purina", ">100% = 3%\n>95% <99,99% = 2%\n>90% <94,99% = 1%\n<90% = 0"),
    HILLS("Hill's", ">100% = 4%\n<100% = 0"),
    VIA("ВИА", ">100% = 5%\n<100% = 2%"),
    ALWAYS5("5%", "5%"),
    ALWAYS2("2%", "2%"),
    ALLTO("Весь ТО", ">=104% = 6750р\n100-103.99% = 5500р\n95-99.99% = 3500р\n90-94.99% = 2500р\n85-89.99% = 1000р\n<84.99 = 0р"),
    ZERO("0%", "Если за план нет %");

    final String name;
    final String description;


    ZpProperty(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<ZpProperty> getAllTypes(){
        ArrayList<ZpProperty> result = new ArrayList<>();
        result.add(STD_2_3_5);
        result.add(STD_2_3_6);
        result.add(STD_2_4_6);
        result.add(RC);
        result.add(PURINA);
        result.add(HILLS);
        result.add(VIA);
        result.add(ALWAYS5);
        result.add(ALWAYS2);
        result.add(ALLTO);
        result.add(ZERO);
        return result;
    }

    public ZpProperty findByName(String name){
        for (ZpProperty property: getAllTypes()) {
            if(property.getName().equals(name)){
                return property;
            }
        }
        return null;
    }
}
