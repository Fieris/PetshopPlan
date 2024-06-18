package ru.fieris.petshopplan.json;

import ru.fieris.petshopplan.json.categories.*;
import ru.fieris.petshopplan.json.categories.conditionCategory.ConditionCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

//POJO class
public class JsonData {
    private int numberOfEmployers;
    private File lastOpenedFile;
    private ArrayList<PrimaryBrandCategory> primaryBrandCategories;
    private ArrayList<ConditionCategory> conditionCategories;
    private ArrayList<SecondaryBrandCategory> secondaryBrandCategories;
    private ArrayList<ArticulToFindInstance> articulsToFindList = new ArrayList<ArticulToFindInstance>();

    public ArrayList<ArticulToFindInstance> getArticulsToFindList() {
        return articulsToFindList;
    }

    public void setArticulsToFindList(ArrayList<ArticulToFindInstance> articulsToFindList) {
        this.articulsToFindList = articulsToFindList;
    }

    public ArrayList<PrimaryBrandCategory> getPrimaryBrandCategories() {
        return primaryBrandCategories;
    }
    public void setPrimaryBrandCategories(ArrayList<PrimaryBrandCategory> primaryBrandCategories) {
        this.primaryBrandCategories = primaryBrandCategories;
    }

    public ArrayList<ConditionCategory> getConditionCategories() {
        return conditionCategories;
    }
    public void setConditionCategories(ArrayList<ConditionCategory> conditionCategories) {
        this.conditionCategories = conditionCategories;
    }

    public ArrayList<SecondaryBrandCategory> getSecondaryBrandCategories() {
        return secondaryBrandCategories;
    }

    public void setSecondaryBrandCategories(ArrayList<SecondaryBrandCategory> secondaryBrandCategories) {
        this.secondaryBrandCategories = secondaryBrandCategories;
    }

    public File getLastOpenedFile() {
        return lastOpenedFile;
    }

    public void setLastOpenedFile(File lastOpenedFile) {
        this.lastOpenedFile = lastOpenedFile;
    }

    public int getNumberOfEmployers() {
        return numberOfEmployers;
    }

    public void setNumberOfEmployers(int numberOfEmployers) {
        this.numberOfEmployers = numberOfEmployers;
    }
}
