package ru.fieris.petshopplan.excel;

import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;
import ru.fieris.petshopplan.json.JsonData;
import ru.fieris.petshopplan.json.JsonMapper;
import ru.fieris.petshopplan.json.categories.Category;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;
import ru.fieris.petshopplan.json.categories.ZpProperty;
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

    public String getShopName() {
        return arrayList.get(0).getShop();
    }

    //считает процент выполнения.
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
                        if(line.getName().toUpperCase().contains("ВИА") || line.getName().toUpperCase().contains("ВВА")){
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
                            if(line.getName().toUpperCase().contains("ВИА") || line.getName().toUpperCase().contains("ВВА")){
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
//        if (remains <= 0) return -1;

        int daysLeft = date.lengthOfMonth() - date.getDayOfMonth() + 1;

        //если осталось 0 дней, просто возвращает полный остаток
        if (daysLeft == 0) return remains;


        result = (remains + getFactToday(category)) / daysLeft;

        return result;
    }

    public double getZpPercent(double donePercent, ZpProperty zpProperty){
        if(Objects.isNull(zpProperty)) return 0;
        double result = 0;
        switch (zpProperty){
            case VIA:
                if(donePercent >= 100){
                    result = 5;
                } else {
                    result = 2;
                }
                break;
            case HILLS:
                if(donePercent >= 100){
                    result = 4;
                } else {
                    result = 0;
                }
                break;
            case ALWAYS2:
                result = 2;
                break;
            case ALWAYS5:
                result = 5;
                break;
            case STD_2_3_5:
                if(donePercent >= 100){
                    result = 5;
                } else if (donePercent >= 85 && donePercent < 99) {
                    result = 3;
                } else {
                    result = 2;
                }
                break;
            case STD_2_3_6:
                if(donePercent >= 100){
                    result = 6;
                } else if (donePercent >= 85 && donePercent < 99) {
                    result = 3;
                } else {
                    result = 2;
                }
                break;
            case STD_2_4_6:
                if(donePercent >= 100){
                    result = 6;
                } else if (donePercent >= 85 && donePercent < 99) {
                    result = 4;
                } else {
                    result = 2;
                }
                break;
            case STD_3_5_7:
                if(donePercent >= 100){
                    result = 7;
                } else if (donePercent >= 85 && donePercent < 99) {
                    result = 5;
                } else {
                    result = 3;
                }
                break;
            case PURINA:
                if(donePercent >= 100){
                    result = 3;
                } else if (donePercent >= 95 && donePercent < 99.99) {
                    result = 2;
                } else if (donePercent >= 90 && donePercent < 94.99) {
                    result = 1;
                } else {
                    result = 0;
                }
                break;
            case RC:
            case ALLTO:
            case PURINAMOTIV:
            case ZERO:
                result = 0;
                break;

        }

        return result;
    }

    public double getZpPerMan(double fact,double donePercent, double zpPercent, ZpProperty zpProperty){
        double result = 0;
        if(zpProperty == ZpProperty.RC){
            if(donePercent >= 100){
                return 2000;
            } else {
                return 0;
            }
        }

        //* При выполнении доп мотивации платят 6к на магазин
        if(zpProperty == ZpProperty.PURINAMOTIV){
            if(donePercent >= 100){
                try{
                    result = 6000.00 / jsonData.getNumberOfEmployers();
                } catch (Exception exc){
                    System.out.println(exc);
                }
                return result;
            }
        }
        
        if(zpProperty == ZpProperty.ALLTO){
            if(donePercent >= 104){
                return 6750;
            } else if (donePercent >= 100 && donePercent <= 103.99) {
                return 5500;
            } else if (donePercent >= 95 && donePercent <= 99.99) {
                return 3500;
            } else if (donePercent >= 90 && donePercent <= 94.99) {
                return 2500;
            } else if (donePercent >= 85 && donePercent <= 89.99) {
                return 1000;
            } else {
                return 0;
            }
        }

        try{
            result = fact * zpPercent / 100 / jsonData.getNumberOfEmployers();
        } catch (Exception exc){
            System.out.println(exc);
        }



        return result;
    }
}
