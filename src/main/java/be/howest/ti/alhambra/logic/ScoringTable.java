package be.howest.ti.alhambra.logic;

import java.util.*;

public class ScoringTable {

    Map<BuildingType, List<Integer>> buildingTypes = new EnumMap<>(BuildingType.class);

    public Map<BuildingType, List<Integer>> getScoringRound(int round){
        return makeRounds(round);
    }

    public Map<BuildingType, List<Integer>> makeRounds(int roundNr) {
        int firstRoundScore = 1;
        int secondRoundScore = 8;
        int thirdRoundScore = 16;

        for (BuildingType type: BuildingType.values()) {
            List<Integer> scores = new LinkedList<>();
            if (roundNr > 2) {
                scores.add(thirdRoundScore);
                thirdRoundScore++;
                scores.add(secondRoundScore);
                secondRoundScore++;
                scores.add(firstRoundScore);
                firstRoundScore ++;
            }

            else if (roundNr == 2) {
                scores.add(secondRoundScore);
                secondRoundScore++;
                scores.add(firstRoundScore);
                firstRoundScore ++;
            }

            else if (roundNr == 1){
                scores.add(firstRoundScore);
                firstRoundScore ++;
            }
            buildingTypes.put(type, scores);
        }
        return buildingTypes;
    }

}
