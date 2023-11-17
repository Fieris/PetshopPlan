package ru.fieris.petshopplan.customControls.HBoxes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class TotalHBox extends CustomHBox {
    private final TextField totalTF = new TextField();
    public TotalHBox(HBoxStyles style){
        super(style);
        Font font = Font.font("Arial", FontWeight.BOLD, 13);

        Label space = new Label("");
        space.setMinWidth(430);
        space.setMaxWidth(430);
//        space.setStyle("-fx-border-color: grey");

        Label totalName = new Label("Итого:");
        totalName.setMinWidth(70);
        totalName.setMaxWidth(70);
        totalName.setFont(font);
        totalName.setAlignment(Pos.CENTER);
//        totalName.setStyle("-fx-border-color: grey");


        totalTF.setMinWidth(100);
        totalTF.setMaxWidth(100);
        totalTF.setFocusTraversable(false);
        totalTF.setEditable(false);
        totalTF.setText("0");
        totalTF.setAlignment(Pos.CENTER);

        this.getChildren().addAll(space, totalName, totalTF);
    }
    public void setTotalValue(String value){
        totalTF.setText(value);
    }
}
