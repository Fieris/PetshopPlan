package ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.fieris.petshopplan.customControls.HBoxes.CustomHBox;
import ru.fieris.petshopplan.customControls.PlanTextField;
import ru.fieris.petshopplan.excel.Calculator;
import ru.fieris.petshopplan.json.categories.PrimaryBrandCategory;

import java.util.Locale;
import java.util.Objects;

public class PrimaryBrandNameHBox extends PlanHBox {
    PrimaryBrandCategory primaryBrandCategory;

    public PrimaryBrandNameHBox(PrimaryBrandCategory primaryBrandCategory){
        super(primaryBrandCategory);
        this.primaryBrandCategory = primaryBrandCategory;
    }
}
