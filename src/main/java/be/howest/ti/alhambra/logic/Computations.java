package be.howest.ti.alhambra.logic;

import java.util.*;

public class Computations {

    public Computations(int round, Game game, String typeOfScore) {
        makeListOfBuildingtypesPerPlayer(round, game, typeOfScore);
    }

    public void makeListOfBuildingtypesPerPlayer(int round, Game game, String typeOfScore) {
        Map<Player, List<Map<BuildingType, Integer>>> mapOfListsPerBuildingType = new HashMap<>();
        List<Map<BuildingType, Integer>> listPlayersBuildingsInCity = new LinkedList<>();
        for (Player playerToCheck : game.getPlayersList()) {
            Map<BuildingType, Integer> buildingsInCity = new EnumMap<>(BuildingType.class);
            for (LinkedList<Building> row : playerToCheck.getCity()) {
                for (Building building : row) {
                    BuildingType type = building.getBuildingType();
                    if (!buildingsInCity.containsKey(type)) {
                        buildingsInCity.put(type, 1);
                    } else {
                        for (Map.Entry<BuildingType, Integer> entry : buildingsInCity.entrySet()) {
                            if (entry.getKey().equals(type)) {
                                entry.setValue(entry.getValue() + 1);
                            }
                        }
                    }
                }
            }
            listPlayersBuildingsInCity.add(buildingsInCity);
            mapOfListsPerBuildingType.put(playerToCheck, listPlayersBuildingsInCity);
        }
        computeWhoHasMost(round, mapOfListsPerBuildingType, typeOfScore);
    }

    public void computeWhoHasMost(int round, Map<Player, List<Map<BuildingType, Integer>>> map, String typeOfScore) {
        Map<BuildingType, Deque<Player>> playersWithMostOfType = new EnumMap<>(BuildingType.class);
        Map<BuildingType, Integer> biggestValues = new EnumMap<>(BuildingType.class);// int
        Deque<Player> deque = new LinkedList();

        for (Map.Entry<Player, List<Map<BuildingType, Integer>>> entry : map.entrySet()) { // voor elke speler in map of lists
            Player playerToCheck = entry.getKey(); //in speler

            for (Map<BuildingType, Integer> mapInListOfPlayer : entry.getValue()) { // list of maps<BuildingType, Integer>
                for (Map.Entry<BuildingType, Integer> map2 : mapInListOfPlayer.entrySet()) {
                    if (playersWithMostOfType.containsKey(map2.getKey()) && biggestValues.containsKey(map2.getKey())) {
                        if (map2.getValue() > biggestValues.get(map2.getKey())) {
                            biggestValues.replace(map2.getKey(), map2.getValue());
                            deque.addFirst(playerToCheck);
                            playersWithMostOfType.replace(map2.getKey(), deque);
                        }
                    } else {
                        deque.addFirst(playerToCheck);
                        playersWithMostOfType.putIfAbsent(map2.getKey(), deque);
                        biggestValues.putIfAbsent(map2.getKey(), map2.getValue());
                    }
                }
            }
        }
        computeScores(round, playersWithMostOfType, typeOfScore);
    }

    public void computeScores(int round, Map<BuildingType, Deque<Player>> mapPlayerWithMostOfType, String typeOfScore){
        Map<BuildingType, List<Integer>> pointsPerBuildingType = new ScoringTable().makeRounds(3);

        for (Map.Entry<BuildingType, Deque<Player>> mapOfPlayerWithMost : mapPlayerWithMostOfType.entrySet()) {
            BuildingType type = mapOfPlayerWithMost.getKey();

            Player bestPlayer = mapOfPlayerWithMost.getValue().getFirst();

            mapOfPlayerWithMost.getValue().removeFirst();
            Player secondBestPlayer = mapOfPlayerWithMost.getValue().getFirst();

            mapOfPlayerWithMost.getValue().removeFirst();
            Player thirdBestPlayer = mapOfPlayerWithMost.getValue().getFirst();

            int bestScore = pointsPerBuildingType.get(type).get(0);
            int secondScore = pointsPerBuildingType.get(type).get(1);
            int thirdScore = pointsPerBuildingType.get(type).get(2);

            if (round > 2) {
                bestPlayer.setVirtualScore(bestScore);
                secondBestPlayer.setVirtualScore(secondScore);
                thirdBestPlayer.setVirtualScore(thirdScore);
            }

            if (round == 2) {
                bestPlayer.setVirtualScore(bestScore);
                secondBestPlayer.setVirtualScore(secondScore);
            }

            if (round == 1){
                bestPlayer.setVirtualScore(bestScore);
            }

            if (typeOfScore.equals("score")){
                if (round > 2) {
                    bestPlayer.setScore(bestScore);
                    secondBestPlayer.setScore(secondScore);
                    thirdBestPlayer.setScore(thirdScore);
                }

                if (round == 2) {
                    bestPlayer.setScore(bestScore);
                    secondBestPlayer.setScore(secondScore);
                }

                if (round == 1){
                    bestPlayer.setScore(bestScore);
                }
            }
        }
    }
}