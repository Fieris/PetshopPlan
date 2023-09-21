package ru.fieris.petshopplan.excel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Класс представляет строку из excel файла
public class ExcelSellLine {
    private final String shop;
    private final String article;
    private final String name;
    private final String analyticalCategory;
    private final String manufacturer;
    private final double weight;
    private final double pricePerOneExcludingBonuses;
    private final double pricePerKgExcludingBonuses;
    private final double totalPriceExcludingBonuses;
    private final double purchasePrice;
    private final double quantityPiece;
    private final double quantityKg;
    private final LocalDate dateOfSale;
    private final double spentPetshopBonuses;
    private final double spentSpasiboBonuses;
    private final double checkNumber;
    private final String deliveryMethod;


    public ExcelSellLine(String shop, String article, String name, String analyticalCategory,
                         String manufacturer, double weight, double pricePerOneExcludingBonuses,
                         double pricePerKgExcludingBonuses, double totalPriceExcludingBonuses,
                         double purchasePrice, double quantityPiece, double quantityKg,
                         String dateOfSale, double spentPetshopBonuses, double spentSpasiboBonuses,
                         double checkNumber, String deliveryMethod) {
        this.shop = shop;
        this.article = article;
        this.name = name;
        this.analyticalCategory = analyticalCategory;
        this.manufacturer = manufacturer;
        this.weight = weight;
        this.pricePerOneExcludingBonuses = pricePerOneExcludingBonuses;
        this.pricePerKgExcludingBonuses = pricePerKgExcludingBonuses;
        this.totalPriceExcludingBonuses = totalPriceExcludingBonuses;
        this.purchasePrice = purchasePrice;
        this.quantityPiece = quantityPiece;
        this.quantityKg = quantityKg;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        this.dateOfSale = LocalDate.parse(dateOfSale.trim(),formatter);

        this.spentPetshopBonuses = spentPetshopBonuses;
        this.spentSpasiboBonuses = spentSpasiboBonuses;
        this.checkNumber = checkNumber;
        this.deliveryMethod = deliveryMethod;
    }


    public String getShop() {
        return shop;
    }

    public String getArticle() {
        return article;
    }

    public String getName() {
        return name;
    }

    public String getAnalyticalCategory() {
        return analyticalCategory;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getWeight() {
        return weight;
    }

    public double getPricePerOneExcludingBonuses() {
        return pricePerOneExcludingBonuses;
    }

    public double getPricePerKgExcludingBonuses() {
        return pricePerKgExcludingBonuses;
    }

    public double getTotalPriceExcludingBonuses() {
        return totalPriceExcludingBonuses;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getQuantityPiece() {
        return quantityPiece;
    }

    public double getQuantityKg() {
        return quantityKg;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public double getSpentPetshopBonuses() {
        return spentPetshopBonuses;
    }

    public double getSpentSpasiboBonuses() {
        return spentSpasiboBonuses;
    }

    public double getCheckNumber() {
        return checkNumber;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }
}
