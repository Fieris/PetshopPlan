package ru.fieris.petshopplan.customControls.HBoxes;

import javafx.scene.layout.HBox;

public abstract class CustomHBox extends HBox {
    HBoxStyles style;
    public CustomHBox(HBoxStyles style){
        this.style = style;
        this.setSpacing(10);

    }

    public HBoxStyles getBoxStyle() {
        return style;
    }
}
