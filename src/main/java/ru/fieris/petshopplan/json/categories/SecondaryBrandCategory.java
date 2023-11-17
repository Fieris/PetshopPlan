package ru.fieris.petshopplan.json.categories;

import java.util.ArrayList;

public class SecondaryBrandCategory extends Category{
    ArrayList<String> brandNames;


    public SecondaryBrandCategory(String categoryName, ArrayList<String> brandNames, double planValue, ZpProperty zpProperty){
        this.categoryName = categoryName;
        this.brandNames = brandNames;
        this.planValue = planValue;
        this.zpProperty = zpProperty;
    }

    public SecondaryBrandCategory(String categoryName, ArrayList<String> brandNames, double planValue){
        this.categoryName = categoryName;
        this.brandNames = brandNames;
        this.planValue = planValue;
    }

    public SecondaryBrandCategory(String categoryName, ArrayList<String> brandNames){
        this.categoryName = categoryName;
        this.brandNames = brandNames;
        this.planValue = 0;
    }

    public SecondaryBrandCategory(String categoryName){
        this.categoryName = categoryName;
        this.brandNames = new ArrayList<>();
        this.planValue = 0;
    }

    public SecondaryBrandCategory(){};


    public ArrayList<String> getBrandNames() {
        return brandNames;
    }

    public void setBrandNames(ArrayList<String> brandNames) {
        this.brandNames = brandNames;
    }

}
