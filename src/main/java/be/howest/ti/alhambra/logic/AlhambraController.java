package be.howest.ti.alhambra.logic;

public class AlhambraController {

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

}
