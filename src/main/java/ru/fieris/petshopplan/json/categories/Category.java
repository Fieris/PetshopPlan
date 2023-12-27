package ru.fieris.petshopplan.json.categories;

import java.util.Date;

public abstract class Category{
    protected String categoryName;
    protected double planValue;
    protected boolean planActual;
    protected Date planChangeDate = new Date();
    protected boolean hidden = false;
    protected ZpProperty zpProperty;

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

    public ZpProperty getZpProperty() {
        return zpProperty;
    }

    public void setZpProperty(ZpProperty zpProperty) {
        this.zpProperty = zpProperty;
    }

    public boolean isPlanActual() {
        return planActual;
    }

    public void setPlanActual(boolean planActual) {
        this.planActual = planActual;
    }

}
