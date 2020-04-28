package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Wall {
    private final boolean north;
    private final boolean east;
    private final boolean south;
    private final boolean west;

    @JsonCreator
    public Wall(@JsonProperty("north")boolean north,@JsonProperty("east") boolean east,@JsonProperty("south")boolean south,@JsonProperty("west") boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return north == wall.north &&
                east == wall.east &&
                south == wall.south &&
                west == wall.west;
    }

    @Override
    public int hashCode() {
        return Objects.hash(north, east, south, west);
    }

    @Override
    public String toString() {
        return "Wall{" +
                "north=" + north +
                ", east=" + east +
                ", south=" + south +
                ", west=" + west +
                '}';
    }

}
