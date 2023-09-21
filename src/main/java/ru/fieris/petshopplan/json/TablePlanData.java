package ru.fieris.petshopplan.json;

//Класс в котором хранятся значения ячеек при запуске программы
@Deprecated
public class TablePlanData {
    private double goNow;
    private double almoNature;
    private double roCat;
    private double barkingHeads;
    private double organix;
    private double florida;
    private double savita;
    private double purina;
    private double rcVet;
    private double rcAll;
    private double hillsPD;
    private double hillsSP;
    private double hillsVetKonc;
    private double hillsKonc;
    private double via;
    private double ves;
    private double allTO;

    private String[] brands = new String[15];

    private String[] goNowNames;
    private String[] almoNatureNames;
    private String[] roCatNames;
    private String[] barkingNames;
    private String[] organixNames;
    private String[] floridaNames;
    private String[] savitaNames;
    private String[] purinaNames;
    private String[] rcVetNames;
    private String[] rcAllNames;
    private String[] hillsPDNames;
    private String[] hillsSPNames;
    private String[] hillsVetKoncNames;
    private String[] hillsKoncNames;
    private String[] importNames;

    public TablePlanData(){
        brands[0] = "Go!";
        brands[1] = "Almo Nature";
        brands[2] = "Ro Cat";
        brands[3] = "Barking Heads";
        brands[4] = "Organix";
        brands[5] = "Florida";
        brands[6] = "Savita";
        brands[7] = "Purina";
        brands[8] = "Royal Canin Вет";
        brands[9] = "Royal Canin Весь";
        brands[10] = "Hill's Prescription Diet";
        brands[11] = "Hill's Science Plan";
        brands[12] = "Hill's Вет консервы";
        brands[13] = "Hill's консервы";
        brands[14] = "Весь импорт";
    }

    public double getGoNow() {
        return goNow;
    }
    public void setGoNow(double goNow) {
        this.goNow = goNow;
    }
    public double getAlmoNature() {
        return almoNature;
    }
    public void setAlmoNature(double almoNature) {
        this.almoNature = almoNature;
    }
    public double getRoCat() {
        return roCat;
    }
    public void setRoCat(double roCat) {
        this.roCat = roCat;
    }
    public double getBarkingHeads() {
        return barkingHeads;
    }
    public void setBarkingHeads(double barkingHeads) {
        this.barkingHeads = barkingHeads;
    }
    public double getOrganix() {
        return organix;
    }
    public void setOrganix(double organix) {
        this.organix = organix;
    }
    public double getFlorida() {
        return florida;
    }
    public void setFlorida(double florida) {
        this.florida = florida;
    }
    public double getSavita() {
        return savita;
    }
    public void setSavita(double savita) {
        this.savita = savita;
    }
    public double getPurina() {
        return purina;
    }
    public void setPurina(double purina) {
        this.purina = purina;
    }
    public double getRcVet() {
        return rcVet;
    }
    public void setRcVet(double rcVet) {
        this.rcVet = rcVet;
    }
    public double getRcAll() {
        return rcAll;
    }
    public void setRcAll(double rcAll) {
        this.rcAll = rcAll;
    }
    public double getHillsPD() {
        return hillsPD;
    }
    public void setHillsPD(double hillsPD) {
        this.hillsPD = hillsPD;
    }
    public double getHillsSP() {
        return hillsSP;
    }
    public void setHillsSP(double hillsSP) {
        this.hillsSP = hillsSP;
    }
    public double getHillsVetKonc() {
        return hillsVetKonc;
    }
    public void setHillsVetKonc(double hillsVetKonc) {
        this.hillsVetKonc = hillsVetKonc;
    }
    public double getHillsKonc() {
        return hillsKonc;
    }
    public void setHillsKonc(double hillsKonc) {
        this.hillsKonc = hillsKonc;
    }
    public double getVia() {
        return via;
    }
    public void setVia(double via) {
        this.via = via;
    }
    public double getVes() {
        return ves;
    }
    public void setVes(double ves) {
        this.ves = ves;
    }
    public double getAllTO() {
        return allTO;
    }
    public void setAllTO(double allTO) {
        this.allTO = allTO;
    }
    public String[] getGoNowNames() {
        return goNowNames;
    }
    public void setGoNowNames(String[] goNowNames) {
        this.goNowNames = goNowNames;
    }
    public String[] getAlmoNatureNames() {
        return almoNatureNames;
    }
    public void setAlmoNatureNames(String[] almoNatureNames) {
        this.almoNatureNames = almoNatureNames;
    }
    public String[] getBrands() {
        return brands;
    }
    public void setBrands(String[] brands) {
        this.brands = brands;
    }
    public String[] getRoCatNames() {
        return roCatNames;
    }
    public void setRoCatNames(String[] roCatNames) {
        this.roCatNames = roCatNames;
    }
    public String[] getBarkingNames() {
        return barkingNames;
    }
    public void setBarkingNames(String[] barkingNames) {
        this.barkingNames = barkingNames;
    }
    public String[] getOrganixNames() {
        return organixNames;
    }
    public void setOrganixNames(String[] organixNames) {
        this.organixNames = organixNames;
    }
    public String[] getFloridaNames() {
        return floridaNames;
    }
    public void setFloridaNames(String[] floridaNames) {
        this.floridaNames = floridaNames;
    }
    public String[] getSavitaNames() {
        return savitaNames;
    }
    public void setSavitaNames(String[] savitaNames) {
        this.savitaNames = savitaNames;
    }
    public String[] getPurinaNames() {
        return purinaNames;
    }
    public void setPurinaNames(String[] purinaNames) {
        this.purinaNames = purinaNames;
    }
    public String[] getRcVetNames() {
        return rcVetNames;
    }
    public void setRcVetNames(String[] rcVetNames) {
        this.rcVetNames = rcVetNames;
    }
    public String[] getRcAllNames() {
        return rcAllNames;
    }
    public void setRcAllNames(String[] rcAllNames) {
        this.rcAllNames = rcAllNames;
    }
    public String[] getHillsPDNames() {
        return hillsPDNames;
    }
    public void setHillsPDNames(String[] hillsPDNames) {
        this.hillsPDNames = hillsPDNames;
    }
    public String[] getHillsSPNames() {
        return hillsSPNames;
    }
    public void setHillsSPNames(String[] hillsSPNames) {
        this.hillsSPNames = hillsSPNames;
    }
    public String[] getHillsVetKoncNames() {
        return hillsVetKoncNames;
    }
    public void setHillsVetKoncNames(String[] hillsVetKoncNames) {
        this.hillsVetKoncNames = hillsVetKoncNames;
    }
    public String[] getHillsKoncNames() {
        return hillsKoncNames;
    }
    public void setHillsKoncNames(String[] hillsKoncNames) {
        this.hillsKoncNames = hillsKoncNames;
    }
    public String[] getImportNames() {
        return importNames;
    }
    public void setImportNames(String[] importNames) {
        this.importNames = importNames;
    }
}
