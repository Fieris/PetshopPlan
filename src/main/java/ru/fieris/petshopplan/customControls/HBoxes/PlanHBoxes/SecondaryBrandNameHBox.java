package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import ru.fieris.petshopplan.customControls.HBoxes.HBoxStyles;
import ru.fieris.petshopplan.json.categories.SecondaryBrandCategory;

public class SecondaryBrandNameHBox extends PlanHBox {
    SecondaryBrandCategory secondaryBrandCategory;

    public SecondaryBrandNameHBox(SecondaryBrandCategory secondaryBrandCategory, HBoxStyles style) {
        super(secondaryBrandCategory, style);
        this.secondaryBrandCategory = secondaryBrandCategory;
    }
}

