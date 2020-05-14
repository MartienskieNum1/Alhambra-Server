package be.howest.ti.alhambra.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ScoringTable {

    Map<BuildingType, List<Integer>> buildingTypes = new HashMap<>();

    public Map<BuildingType, List<Integer>> makeRound1(){
        int firstRoundScore = 1;

        for (BuildingType type: BuildingType.values()) {
            List<Integer> scores = new LinkedList<>();
            scores.add(firstRoundScore);
            firstRoundScore ++;
            buildingTypes.put(type, scores);
        }
        return buildingTypes;
    }

    public Map<BuildingType, List<Integer>> makeRound2() {
        int firstRoundScore = 1;
        int secondRoundScore = 8;

        for (BuildingType type: BuildingType.values()) {
            List<Integer> scores = new LinkedList<>();
            scores.add(secondRoundScore);
            secondRoundScore ++;
            scores.add(firstRoundScore);
            firstRoundScore ++;
            buildingTypes.put(type, scores);
        }
        return buildingTypes;
    }

    public Map<BuildingType, List<Integer>> makeRound3() {
        int firstRoundScore = 1;
        int secondRoundScore = 8;
        int thirdRoundScore = 16;

        for (BuildingType type: BuildingType.values()) {
            List<Integer> scores = new LinkedList<>();
            scores.add(thirdRoundScore);
            thirdRoundScore ++;
            scores.add(secondRoundScore);
            secondRoundScore ++;
            scores.add(firstRoundScore);
            firstRoundScore ++;
            buildingTypes.put(type, scores);
        }
        return buildingTypes;
    }
}
