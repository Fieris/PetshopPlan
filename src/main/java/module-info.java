module ru.fieris.petshopplan {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires com.dustinredmond.fxtrayicon;


    opens ru.fieris.petshopplan to javafx.fxml;
    exports ru.fieris.petshopplan;
    exports ru.fieris.petshopplan.json;
    exports ru.fieris.petshopplan.customControls;
    exports ru.fieris.petshopplan.controllers;
    exports ru.fieris.petshopplan.excel;
    opens ru.fieris.petshopplan.controllers to javafx.fxml;
    exports ru.fieris.petshopplan.json.categories;
    exports ru.fieris.petshopplan.json.categories.conditionCategory;
    exports ru.fieris.petshopplan.customControls.HBoxes;
    exports ru.fieris.petshopplan.customControls.HBoxes.PlanHBoxes;
}