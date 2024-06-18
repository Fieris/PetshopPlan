package ru.fieris.petshopplan.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
}
