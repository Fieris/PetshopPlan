package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import ru.fieris.petshopplan.customControls.HBoxes.HBoxStyles;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;

public class PrimaryBrandNameHBox extends PlanHBox {
    PrimaryBrandCategory primaryBrandCategory;

    public PrimaryBrandNameHBox(PrimaryBrandCategory primaryBrandCategory, HBoxStyles style){
        super(primaryBrandCategory, style);
        this.primaryBrandCategory = primaryBrandCategory;
    }
}
