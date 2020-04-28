package be.howest.ti.alhambra.logic;

import java.util.HashMap;
import java.util.Map;

public class Building {
    private final BuildingType buildingType;
    private final int cost;
    private final Map<Wall, Boolean> walls = new HashMap<>();

    public Building(BuildingType buildingType, int cost) {
        this.buildingType = buildingType;
        this.cost = cost;
    }

    



}
