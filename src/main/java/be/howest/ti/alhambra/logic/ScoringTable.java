package be.howest.ti.alhambra.logic;

import java.util.*;

public class ScoringTable {

    Map<BuildingType, List<Integer>> buildingTypes = new HashMap<>();

    public Map<BuildingType, List<Integer>> getScoringRound(int round, Game game){
        if (round == 1){
            return new ScoringTable().makeRound1(game);
        }
        if (round == 2){
            return new ScoringTable().makeRound2(game);
        } else {return null;}
    }

    public Map<BuildingType, List<Integer>> makeRound1(Game game){
        int firstRoundScore = 1;

        for (BuildingType type: BuildingType.values()) {
            List<Integer> scores = new LinkedList<>();
            scores.add(firstRoundScore);
            firstRoundScore ++;
            buildingTypes.put(type, scores);
        }
//        makeListOfBuildingtypesPerPlayer(game);
        return buildingTypes;
    }

    public Map<BuildingType, List<Integer>> makeRound2(Game game) {
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

    public Map<BuildingType, List<Integer>> makeRound3(Game game) {
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
    
//    public Map<BuildingType, Deque<Player>> makeListOfBuildingtypesPerPlayer(Game game ){
//        Map<Player, List<Map<BuildingType, Integer>>> mapOfListsPerBuildingType = new HashMap<>();
//        List<Map<BuildingType, Integer>> listPlayersBuildingsInCity = new LinkedList<>();
//        for (Player playerToCheck: game.getPlayersList()) {
//            Map<BuildingType, Integer> buildingsInCity = new HashMap<>();
//            for (LinkedList<Building> row: playerToCheck.getCity()) {
//                for (Building building: row) {
//                    BuildingType type = building.getBuildingType();
//                    if (!buildingsInCity.containsKey(type)){
//                        buildingsInCity.put(type, 1);
//                    } else {
//                        for (Map.Entry<BuildingType, Integer> entry: buildingsInCity.entrySet()) {
//                            if (entry.getKey().equals(type)){
//                                entry.setValue(entry.getValue()+1);
//                            }
//                        }
//                    }
//                }
//            }
//            listPlayersBuildingsInCity.add(buildingsInCity);
//            mapOfListsPerBuildingType.put(playerToCheck, listPlayersBuildingsInCity);
//        }
//        return computeScore(game, mapOfListsPerBuildingType);
//    }
//
//    public Map<BuildingType, Deque<Player>> computeScore(Game game, Map<Player, List<Map<BuildingType, Integer>>> map){
//        Map<BuildingType, Deque<Player>> playersWithMostOfType = new HashMap<>();
//        Map<BuildingType, Integer> biggestValues = new HashMap<>();
//
//        for (BuildingType type: BuildingType.values()) {
//            BuildingType buildingtype = type;
//            biggestValues.put(type, 0);
//            Deque<Player> deque = new LinkedList();
//            playersWithMostOfType.put(type, deque);
//            for (Map.Entry<Player, List<Map<BuildingType, Integer>>> entry: map.entrySet()) {
//                Player playerToCheck = entry.getKey();
//                for (Map<BuildingType, Integer> map1: entry.getValue()) {
//                    if (map1.containsKey(buildingtype)){
//                        for (Map.Entry<BuildingType, Integer> entry1: map1.entrySet()) {
//                            if (entry1.getKey().equals(buildingtype)){
//                                for (Map.Entry<BuildingType, Integer> entry2: biggestValues.entrySet()) {
//                                    if (entry1.getValue()>entry2.getValue()){
//                                        entry2.setValue(entry1.getValue());
//                                        for (Map.Entry<BuildingType, Deque<Player>> entry3: playersWithMostOfType.entrySet()) {
//                                            if (entry3.getKey().equals(entry1.getKey())){
//                                                entry3.getValue().addFirst(playerToCheck);
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return playersWithMostOfType;
//    }
    
}
