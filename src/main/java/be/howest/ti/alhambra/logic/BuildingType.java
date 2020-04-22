package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BuildingType {
    PAVILION, SERAGLIO, ARCADES, CHAMBERS, GARDEN, TOWER;

    @JsonValue
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
