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


}
