package ru.fieris.petshopplan.json.categories.conditionCategory;

@Deprecated
public enum ExcelColumnNames {
    SHOP("Магазин"),
    ARTICLE("Артикул"),
    NAME("Наименование"),
    ANALYTICALCATEGOTY("Аналитическая категория"),
    MANUFACTURER("Производитель"),
    WEIGHT("Масса, кг"),
    PRICEPERONEEXCLUDINGBONUSES("Цена за шт (за вычетом всех бонусов)"),
    PRICEPERKGEXCLUDINGBONUSES("Цена за кг (за вычетом всех бонусов)"),
    TOTALPRICEEXCLUDINGBONUSES("Цена общая (за вычетом всех бонусов)"),
    PURCHASEPRICE("Закупочная стоимость за шт"),
    QUANTITYPIECE("Кол-во шт"),
    QUANTITYKG("Кол-во кг"),
    DATEOFSALE("Дата продажи"),
    SPENTPETSHOPBONUSES("Потрачено бонусов «Petshop»"),
    SPENTSPASIBOBONUSES("Потрачено бонусов «Спасибо»"),
    CHECKNUMBER("№ чека/накладной"),
    DELIVERYMETHOD("Способ доставки");


    final String text;

    ExcelColumnNames(String text){
        this.text = text;
    }

}
