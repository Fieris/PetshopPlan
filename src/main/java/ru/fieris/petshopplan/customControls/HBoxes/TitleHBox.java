package ru.fieris.petshopplan.customControls.HBoxes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleHBox extends CustomHBox{
    public TitleHBox(HBoxStyles style){
        super(style);
        Font font = Font.font("Arial", FontWeight.BOLD, 13);

        Label name = new Label("Наименование");
        name.setMaxWidth(130);
        name.setMinWidth(130);
        name.setAlignment(Pos.CENTER);
        name.setFont(font);

        Label planName = new Label("План");
        planName.setMaxWidth(100);
        planName.setMinWidth(100);
        planName.setAlignment(Pos.CENTER);
        planName.setFont(font);

        Label factName = new Label("Факт");
        factName.setMaxWidth(100);
        factName.setMinWidth(100);
        factName.setAlignment(Pos.CENTER);
        factName.setFont(font);

        Label percentName = new Label("%");
        percentName.setMaxWidth(70);
        percentName.setMinWidth(70);
        percentName.setAlignment(Pos.CENTER);
        percentName.setFont(font);

        if(style == HBoxStyles.PLAN){

            Label remainsName = new Label("Осталось");
            remainsName.setMaxWidth(100);
            remainsName.setMinWidth(100);
            remainsName.setAlignment(Pos.CENTER);
            remainsName.setFont(font);

            Label doPerDayName= new Label("В день");
            doPerDayName.setMaxWidth(100);
            doPerDayName.setMinWidth(100);
            doPerDayName.setAlignment(Pos.CENTER);
            doPerDayName.setFont(font);

            Label space = new Label("space");
            space.setMaxWidth(25);
            space.setMinWidth(25);
            space.setAlignment(Pos.CENTER);
            space.setVisible(false);

            Label factTodayName = new Label("Сделано");
            factTodayName.setMinWidth(100);
            factTodayName.setMaxWidth(100);
            factTodayName.setAlignment(Pos.CENTER);
            factTodayName.setFont(font);

            Label remainsTodayName = new Label("Осталось");
            remainsTodayName.setMinWidth(100);
            remainsTodayName.setMaxWidth(100);
            remainsTodayName.setAlignment(Pos.CENTER);
            remainsTodayName.setFont(font);

            this.getChildren().addAll(name, planName,factName, percentName, remainsName, doPerDayName, space, factTodayName, remainsTodayName);
        } else if (style == HBoxStyles.ZP) {
            Label percentGetZp = new Label("% в ЗП");
            percentGetZp.setMinWidth(70);
            percentGetZp.setMaxWidth(70);
            percentGetZp.setAlignment(Pos.CENTER);
            percentGetZp.setFont(font);

            Label zpPerMan = new Label("ЗП на человека");
            zpPerMan.setMinWidth(100);
            zpPerMan.setMaxWidth(100);
            zpPerMan.setAlignment(Pos.CENTER);
            zpPerMan.setFont(font);

            this.getChildren().addAll(name, planName, factName, percentName, percentGetZp, zpPerMan);
        }


    }
}
