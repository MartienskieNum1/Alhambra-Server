package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Building {
    private final BuildingType buildingType;
    private final int cost;
    private Map<String, Boolean> walls = new HashMap<>();

    @JsonCreator
    public Building(@JsonProperty("buildingType") BuildingType buildingType, @JsonProperty("cost") int cost, boolean north, boolean east, boolean south, boolean west) {
        this.buildingType = buildingType;
        this.cost = cost;
        this.walls.put("north", north);
        this.walls.put("east", east);
        this.walls.put("south", south);
        this.walls.put("west", west);
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getCost() {
        return cost;
    }

    public Map<String, Boolean> getWalls() {
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
