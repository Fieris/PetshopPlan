package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;

import java.util.Locale;
import java.util.Objects;

public class SecondaryBrandNameHBox extends PlanHBox {
    SecondaryBrandCategory secondaryBrandCategory;

    public SecondaryBrandNameHBox(SecondaryBrandCategory secondaryBrandCategory) {
        super(secondaryBrandCategory);
        this.secondaryBrandCategory = secondaryBrandCategory;
    }
}

