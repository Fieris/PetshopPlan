package ru.fieris.petshopplan.excel;

import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.JsonData;
import ru.fieris.petshopplan.json.JsonMapper;
import ru.fieris.petshopplan.json.categories.Category;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.time.LocalDate;
import java.util.*;

//Класс проводит все вычисления из arrayList
public class Calculator {
    ArrayList<ExcelSellLine> arrayList;
    JsonData jsonData = JsonMapper.readFromJson();


    public Calculator(ArrayList<ExcelSellLine> arrayList) {
        this.arrayList = arrayList;
    }

    @Deprecated
    public double calculateSum() {
        Iterator<ExcelSellLine> iterator = arrayList.listIterator();
        double sum = 0;

        while (iterator.hasNext()) {
            sum += iterator.next().getTotalPriceExcludingBonuses();
        }

        return sum;
    }

    @Deprecated
    public double calculateSumWithBonuses() {
        Iterator<ExcelSellLine> iterator = arrayList.listIterator();
        double sum = 0;

        while (iterator.hasNext()) {
            ExcelSellLine sellString = iterator.next();
            sum += sellString.getTotalPriceExcludingBonuses() + sellString.getSpentPetshopBonuses() + sellString.getSpentSpasiboBonuses();
        }

        return sum;
    }

    public String getShopName() {
        return arrayList.get(0).getShop();
    }

    @Deprecated
    public double getGoNowFact() {
        double result = 0;
        for (ExcelSellLine line : arrayList) {
            if (line.getManufacturer().equals("GO!")) {
                result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
            } else if (line.getManufacturer().equals("NOW FRESH")) {
                result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
            }
        }
        return result;
    }

    //Получает название бренда и считает процент выполнения.
    public double getPercent(double planValue, double factValue) {
        if (planValue == 0 || factValue == 0) {
            return 0;
        }

        return factValue * 100 / planValue;
    }


    //Получает название бренда и считает сумму за данный бренд. Имена брендов берет из json файла
    //Если instanceof secondaryBrandName, то бонусы считать не нужно
    public double getFact(Category category) {
        double result = 0;
        if (category instanceof PrimaryBrandCategory) {
            ArrayList<String> brandNames = ((PrimaryBrandCategory) category).getBrandNames();
            for (ExcelSellLine line : arrayList) {
                for (String jsonBrandName : brandNames) {
                    if (jsonBrandName.equalsIgnoreCase(line.getManufacturer())) {
                        result += line.getTotalPriceExcludingBonuses() + line.getSpentPetshopBonuses() + line.getSpentSpasiboBonuses();
                    }
                }
            }
        } else if (category instanceof ConditionCategory) {
            for(ExcelSellLine line : arrayList){
                switch(((ConditionCategory) category).getConditionType()){
                    case VIA:
                        if(line.getName().toUpperCase().startsWith("ВИА") || line.getName().toUpperCase().startsWith("ВВА")){
                            result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
                        }
                        break;
                    case VES:
                        if(line.getArticle().endsWith("099") && line.getArticle().length() > 5){
                            result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
                        }
                        break;
                    case ALL:
                        result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
                        break;
                }
            }

        } else if (category instanceof SecondaryBrandCategory){
            ArrayList<String> brandNames = ((SecondaryBrandCategory) category).getBrandNames();
            for (ExcelSellLine line : arrayList) {
                for (String jsonBrandName : brandNames) {
                    if (jsonBrandName.equalsIgnoreCase(line.getManufacturer())) {
                        result += line.getTotalPriceExcludingBonuses();
                    }
                }
            }
        }


        return result;
    }

    //считает остатки
    public double getRemains(double planValue, double factValue) {
        return planValue - factValue;
    }

    public double getFactToday(Category category) {
        LocalDate date = LocalDate.now();
        double result = 0;
        if(category instanceof PrimaryBrandCategory){
            ArrayList<String> brandNames = ((PrimaryBrandCategory) category).getBrandNames();
            for (ExcelSellLine line : arrayList) {
                if (line.getDateOfSale().equals(date)) {
                    for (String jsonBrandName : brandNames) {
                        if (jsonBrandName.equalsIgnoreCase(line.getManufacturer())) {
                            result += line.getTotalPriceExcludingBonuses() + line.getSpentPetshopBonuses() + line.getSpentSpasiboBonuses();
                        }
                    }
                }
            }
        } else if (category instanceof ConditionCategory) {
            for(ExcelSellLine line :arrayList){
                if(line.getDateOfSale().equals(date)){
                    switch (((ConditionCategory) category).getConditionType()){
                        case VIA:
                            if(line.getName().toUpperCase().startsWith("ВИА") || line.getName().toUpperCase().startsWith("ВВА")){
                                result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
                            }
                            break;
                        case VES:
                            if(line.getArticle().endsWith("099") && line.getArticle().length() > 5){
                                result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
                            }
                            break;
                        case ALL:
                            result += line.getTotalPriceExcludingBonuses() + line.getSpentSpasiboBonuses() + line.getSpentPetshopBonuses();
                            break;
                    }
                }
            }
        } else if (category instanceof SecondaryBrandCategory) {
            ArrayList<String> brandNames = ((SecondaryBrandCategory) category).getBrandNames();
            for (ExcelSellLine line : arrayList) {
                if (line.getDateOfSale().equals(date)) {
                    for (String jsonBrandName : brandNames) {
                        if (jsonBrandName.equalsIgnoreCase(line.getManufacturer())) {
                            result += line.getTotalPriceExcludingBonuses();
                        }
                    }
                }
            }
        }


        return result;
    }

    public double getDoPerDay(double remains, Category category) {
        LocalDate date = LocalDate.now();
        double result = 0;

        //Если осталось меньше 0, значит план перевыполнен -> возвращает -1
        if (remains <= 0) return -1;

        int daysLeft = date.lengthOfMonth() - date.getDayOfMonth() + 1;

        //если осталось 0 дней, просто возвращает полный остаток
        if (daysLeft == 0) return remains;


        result = (remains - getFactToday(category)) / daysLeft;

        return result;
    }
}
