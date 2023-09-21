package ru.fieris.petshopplan.json.categories;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Category{
    protected String categoryName;
    protected double planValue;
    protected Date planChangeDate = new Date();
    protected boolean hidden = false;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPlanValue() {
        return planValue;
    }

    public void setPlanValue(double planValue) {
        this.planValue = planValue;
    }

    public Date getPlanChangeDate() {
        return planChangeDate;
    }

    public void setPlanChangeDate(Date planChangeDate) {
        this.planChangeDate = planChangeDate;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
