package ru.fieris.petshopplan.json;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.ZpProperty;
import ru.fieris.petshopplan.json.categories.conditionCategory.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMapper {
    private static final String programDirectory = System.getProperty("user.home");
    private static final File outputFile = new File(programDirectory + "/PlanData.json");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    //private static final TablePlanData tablePlanData = initialize();
    private static JsonData jsonData = initialize();

    private JsonMapper() {
    }

    //Используется вместо конструктора
    private static JsonData initialize() {
        if (!outputFile.exists()) {
            jsonData = new JsonData();
            setDefault(jsonData);


            try {
                objectWriter.writeValue(outputFile, jsonData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                jsonData = objectMapper.readValue(outputFile, JsonData.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonData;
    }


    public static JsonData readFromJson() {
        return jsonData;
    }

    public static void writeToJson(JsonData jsonData) {
        JsonMapper.jsonData = jsonData;
        try {
            objectWriter.writeValue(outputFile, jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //метод для публичного ресета
    public static void setDefault(){
        setDefault(jsonData);
        try {
            objectWriter.writeValue(outputFile, jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Задает Json файлу значения по умолчанию
    private static void setDefault(JsonData jsonData) {
        jsonData.setNumberOfEmployers(6);

        //Брендовые категории
        ArrayList<PrimaryBrandCategory> brandCategories = new ArrayList<>();
        brandCategories.add(new PrimaryBrandCategory("Go!, Now", new ArrayList<>(List.of(new String[]{"GO!", "NOW FRESH"})), 0, ZpProperty.STD_2_3_5));
        brandCategories.add(new PrimaryBrandCategory("Almo Nature", new ArrayList<>(List.of(new String[]{"Almo Nature", "Almo Nature консервы"})), 0, ZpProperty.STD_2_3_5));
        brandCategories.add(new PrimaryBrandCategory("Ro Cat", new ArrayList<>(List.of(new String[]{"Canada Litter", "Ro Cat"})), 0, ZpProperty.STD_2_3_5));
        brandCategories.add(new PrimaryBrandCategory("Barking Heads", new ArrayList<>(List.of(new String[]{"Barking Heads", "Meowing Heads"})), 0, ZpProperty.STD_2_3_5));
        brandCategories.add(new PrimaryBrandCategory("Smart", new ArrayList<>(List.of(new String[]{"Smart Cat сухой корм", "Smart Cat лакомства", "Smart Cat паучи",
        "Smart Dog консервы", "Smart Dog лакомства", "Smart Dog паучи", "Smart Dog сухой корм"})), 0, ZpProperty.STD_2_4_6));
        brandCategories.add(new PrimaryBrandCategory("Organix", new ArrayList<>(List.of(new String[]{"Organix (профилактика)", "Organix консервы", "Organix консервы (профилактика)",
                "Organix лакомства", "Organix паучи", "Organix сухой корм"})), 0, ZpProperty.STD_2_4_6));
        brandCategories.add(new PrimaryBrandCategory("Florida", new ArrayList<>(List.of(new String[]{"FLORIDA", "FLORIDA (профилактика)",
                "FLORIDA консервы", "FLORIDA консервы (профилактика)", "FLORIDA паучи", "FLORIDA лакомства"})), 0, ZpProperty.STD_2_4_6));
        brandCategories.add(new PrimaryBrandCategory("Savita", new ArrayList<>(List.of(new String[]{"SAVITA сухой корм", "SAVITA консервы"})), 0, ZpProperty.STD_2_4_6));
        brandCategories.add(new PrimaryBrandCategory("Pronature Life", new ArrayList<>(List.of(new String[]{"Pronature Life"})), 0, ZpProperty.STD_2_3_6));
        brandCategories.add(new PrimaryBrandCategory("Purina", new ArrayList<>(List.of(new String[]{"Purina (вет. корма паучи)", "Purina (вет. корма)", "Purina Pro Plan", "Purina Pro Plan (паучи)"})), 0, ZpProperty.PURINA));
        brandCategories.add(new PrimaryBrandCategory("RC вет", new ArrayList<>(List.of(new String[]{"Royal Canin (вет.корма)", "Royal Canin (вет. паучи)"})), 0,ZpProperty.RC));
        brandCategories.add(new PrimaryBrandCategory("RC весь", new ArrayList<>(List.of(new String[]{"Royal Canin", "Royal Canin (вет. паучи)", "Royal Canin (вет.корма)", "Royal Canin паучи"})), 0, ZpProperty.RC));
        brandCategories.add(new PrimaryBrandCategory("Hill's PD", new ArrayList<>(List.of(new String[]{"Hill's Prescription Diet"})), 0, ZpProperty.HILLS));
        brandCategories.add(new PrimaryBrandCategory("Hill's SP", new ArrayList<>(List.of(new String[]{"Hill's Science Plan"})), 0, ZpProperty.HILLS));
        brandCategories.add(new PrimaryBrandCategory("Hill's вет конс.", new ArrayList<>(List.of(new String[]{"Hill's вет.консервы"})), 0, ZpProperty.HILLS));
        brandCategories.add(new PrimaryBrandCategory("Hill's консервы", new ArrayList<>(List.of(new String[]{"Hill's консервы"})), 0,ZpProperty.HILLS));
        brandCategories.add(new PrimaryBrandCategory("Bonsy", new ArrayList<>(List.of(new String[]{"Bonsy"})), 0,ZpProperty.ALWAYS5));

        //иницилизация импорта
        ArrayList<String> wholeImport = new ArrayList<>(List.of("AATU", "AATU Консервы", "All Cats", "All Dogs", "Applaws", "Applaws консервы","Classic (Versele-Laga)",
                "Frank's ProGold", "Frank's ProGold консервы", "GATHER", "Happy Life (Versele-Laga)", "NERO GOLD super premium", "Nero Gold консервы", "Ontario",
                "Ontario (консервы, лакомства)", "Summit", "Italian Way", "Italian Way Консервы", "Petsmack лакомства", "Kito", "Tauro", "Havlife",
                "Kittylife", "Almo Nature Cat Litter", "Easy Clean (Канада)", "NAPKINS наполнитель", "Sani Cat", "SEPICAT", "Smart Cat наполнитель",
                "Van Cat", "White Sand", "Organix наполнители", "2u", "Anju Beaute", "NAPKINS гигиенические пакеты", "NAPKINS пеленки", "Pet Head",
                "Petsmack", "Smart Dog пелёнки", "Misoko & Co", "PetshopRu гигиена", "Cortina", "DogSnap", "EduPet", "Ferribiella аксессуары", "Ferribiella одежда",
                "Kitty City", "Kong", "Kong рулетки", "LeLap амуниция", "Lelap когтеточки и лежаки", "Lelap одежда", "Lelap транспортировка", "Lion",
                "Lion лежанки", "Mighty", "Nylabone", "Papillon", "PetshopRu игрушки", "PetshopRu когтеточки", "PetshopRu миски", "Pinkaholic",
                "Puppia", "Rogz", "Silly Squeakers", "Tangle Angel", "Tappi амуниция", "Tappi игрушки", "Tappi когтеточки и лежаки", "Tappi миски", "Tappi одежда",
                "Tappi транспортировка", "Tuffy", "BOW WOW", "Lelap игрушки", "Curver PetLife", "Moderna", "Stefanplast", "Green Petcare",
                "Benelux аксессуары", "Benelux корма", "Cliffi (Италия)", "MR.Crisper", "Hamiform"));

        Collections.sort(wholeImport);
        brandCategories.add(new PrimaryBrandCategory("Весь импорт", wholeImport, 0, ZpProperty.ALWAYS2));
        jsonData.setPrimaryBrandCategories(brandCategories);

        //Условные категории
        ArrayList<ConditionCategory> conditionCategories = new ArrayList<>();
        //Иницилизация ВИА
        conditionCategories.add(new ConditionCategory("ВИА", ConditionType.VIA,0, ZpProperty.VIA));
        //Иницилизация весового
        conditionCategories.add(new ConditionCategory("Весовой",ConditionType.VES, 0, ZpProperty.VIA));
        conditionCategories.add(new ConditionCategory("Весь ТО", ConditionType.ALL, 0, ZpProperty.ALLTO));
        jsonData.setConditionCategories(conditionCategories);

        //Дополнительные категории
        ArrayList<SecondaryBrandCategory> secondaryBrandCategories = new ArrayList<>();
        secondaryBrandCategories.add(new SecondaryBrandCategory("Bonsy", new ArrayList<>(List.of(new String[]{"Bonsy"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("2u", new ArrayList<>(List.of(new String[]{"2u"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("MR.Crisper", new ArrayList<>(List.of(new String[]{"MR.Crisper"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("Petsmack", new ArrayList<>(List.of(new String[]{"Petsmack", "Petsmack лакомства"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("Tappi", new ArrayList<>(List.of(new String[]{"Tappi игрушки", "Tappi когтеточки и лежаки",
        "Tappi миски", "Tappi одежда", "Tappi транспортировка"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("LeLap", new ArrayList<>(List.of(new String[]{"LeLap амуниция", "Lelap игрушки",
                "Lelap когтеточки и лежаки", "Lelap одежда"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("Yami Yami", new ArrayList<>(List.of(new String[]{"Yami Yami амуниция",
        "Yami Yami гигиена", "Yami Yami игрушки", "Yami Yami когтеточки и лежаки", "Yami Yami миски", "Yami Yami транспортировка", "Yami-Yami",
        "Yami-Yami одежда"})), 0));

        secondaryBrandCategories.add(new SecondaryBrandCategory("PetshopRu", new ArrayList<>(List.of(new String[]{"PetshopRu игрушки", "PetshopRu когтеточки и лежаки",
        "PetshopRu МЕРЧ", "PetshopRu миски"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("NAPKINS", new ArrayList<>(List.of(new String[]{"NAPKINS гигиенические пакеты", "NAPKINS наполнитель",
        "NAPKINS пеленки"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("NERO GOLD", new ArrayList<>(List.of(new String[]{"NERO GOLD super premium", "Nero Gold консервы"})), 0));
        secondaryBrandCategories.add(new SecondaryBrandCategory("Frank's ProGold", new ArrayList<>(List.of(new String[]{"Frank's ProGold консервы"})), 0));
        jsonData.setSecondaryBrandCategories(secondaryBrandCategories);


    }


}