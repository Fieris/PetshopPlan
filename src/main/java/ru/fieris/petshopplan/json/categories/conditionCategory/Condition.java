package ru.fieris.petshopplan.json.categories.conditionCategory;

@Deprecated
public class Condition {
    ExcelColumnNames column;
    ConditionSigns sign;
    String value;



    public Condition(){
    }

    public Condition(ExcelColumnNames column, ConditionSigns sign, String value){
        this.column = column;
        this.sign = sign;
        this.value = value;
    }



    public ExcelColumnNames getColumn() {
        return column;
    }
    public void setColumn(ExcelColumnNames column) {
        this.column = column;
    }
    public ConditionSigns getSign() {
        return sign;
    }
    public void setSign(ConditionSigns sign) {
        this.sign = sign;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


}
