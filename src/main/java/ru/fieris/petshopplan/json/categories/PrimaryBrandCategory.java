package ru.fieris.petshopplan.json.categories;

import java.util.ArrayList;

public class PrimaryBrandCategory extends Category{
    ArrayList<String> brandNames;

    public PrimaryBrandCategory(String categoryName, ArrayList<String> brandNames, double planValue, ZpProperty zpProperty){
        this.categoryName = categoryName;
        this.brandNames = brandNames;
        this.planValue = planValue;
        this.zpProperty = zpProperty;
    }

    public PrimaryBrandCategory(String categoryName, ArrayList<String> brandNames){
        this.categoryName = categoryName;
        this.brandNames = brandNames;
        this.planValue = 0;
    }

    public PrimaryBrandCategory(String categoryName){
        this.categoryName = categoryName;
        this.brandNames = new ArrayList<>();
        this.planValue = 0;
    }

    public PrimaryBrandCategory(){};


    public ArrayList<String> getBrandNames() {
        return brandNames;
    }

    public void setBrandNames(ArrayList<String> brandNames) {
        this.brandNames = brandNames;
    }

}
