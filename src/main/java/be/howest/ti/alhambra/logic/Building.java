package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Building {
    private final BuildingType buildingType;
    private final int cost;
    private final Map<Wall, Boolean> walls = new HashMap<>();


    @JsonCreator
    public Building(@JsonProperty("buildingType") BuildingType buildingType, @JsonProperty("cost") int cost) {
        this.buildingType = buildingType;
        this.cost = cost;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getCost() {
        return cost;
    }

    public Map<Wall, Boolean> getWalls() {
        return walls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return cost == building.cost &&
                buildingType == building.buildingType &&
                Objects.equals(walls, building.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingType, cost, walls);
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingType=" + buildingType +
                ", cost=" + cost +
                ", walls=" + walls +
                '}';
    }
}
