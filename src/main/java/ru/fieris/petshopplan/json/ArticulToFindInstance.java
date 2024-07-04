package ru.fieris.petshopplan.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ArticulToFindInstance {
    private String articul;
    private String lastDateOfSale;
    private String comment;



    public ArticulToFindInstance(String articul, String comment) {
        this.articul = articul;
        this.comment = comment;
        this.lastDateOfSale = "-";
    }

    public ArticulToFindInstance(String articul) {
        this.articul = articul;
        this.comment = "-";
        this.lastDateOfSale = "-";
    }

    public ArticulToFindInstance() {
        this.articul = "-";
        this.comment = "-";
        this.lastDateOfSale = "-";
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public String getLastDateOfSale() {
        return lastDateOfSale;
    }

    public void setLastDateOfSale(String lastDateOfSale) {
        this.lastDateOfSale = lastDateOfSale;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
