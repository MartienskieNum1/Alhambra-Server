package be.howest.ti.alhambra.logic;

import java.util.*;

public class Computations {

    public Computations(int round, Game game, String typeOfScore) {
        makeListOfBuildingtypesPerPlayer(round, game, typeOfScore);
    }

    public void makeListOfBuildingtypesPerPlayer(int round, Game game, String typeOfScore) {
        Map<Player, Map<BuildingType, Integer>> mapOfMapPerBuildingType = new HashMap<>();
        for (Player playerToCheck : game.getPlayersList()) {
            Map<BuildingType, Integer> mapPlayersBuildingsInCity = new HashMap<>();
            for (LinkedList<Building> row : playerToCheck.getCity()) {
                for (Building building : row) {
                    if (building != null && building.getBuildingType() != null){
                        BuildingType type = building.getBuildingType();
                        if (!mapPlayersBuildingsInCity.containsKey(type)) {
                            mapPlayersBuildingsInCity.put(type, 1);
                        } else {
                            for (Map.Entry<BuildingType, Integer> entry : mapPlayersBuildingsInCity.entrySet()) {
                                if (entry.getKey().equals(type)) {
                                    entry.setValue(entry.getValue() + 1);
                                }
                            }
                        }
                    }
                }
            }
            mapOfMapPerBuildingType.put(playerToCheck, mapPlayersBuildingsInCity);
        }
        computeWhoHasMost(round, mapOfMapPerBuildingType, typeOfScore, game);
    }

    public void computeWhoHasMost(int round, Map<Player, Map<BuildingType, Integer>> map, String typeOfScore, Game game) {
        Map<BuildingType, List<Player>> playersWithMostOfType = new HashMap<>();
        Map<BuildingType, Integer> biggestValues = new HashMap<>();
        List<Player> deque = new LinkedList();

        for (Map.Entry<Player, Map<BuildingType, Integer>> entry : map.entrySet()) {
            Player playerToCheck = entry.getKey();

            for (Map.Entry<BuildingType, Integer> map2 : entry.getValue().entrySet()) {
                if (playersWithMostOfType.containsKey(map2.getKey()) && biggestValues.containsKey(map2.getKey())) {
                    if (map2.getValue() > biggestValues.get(map2.getKey())) {
                        biggestValues.remove(map2.getKey());
                        biggestValues.put(map2.getKey(), map2.getValue());
                        deque.add(playerToCheck);
                        playersWithMostOfType.remove(map2.getKey(), deque);
                        playersWithMostOfType.put(map2.getKey(), deque);
                    }
                } else {
                    deque.add(playerToCheck);
                    playersWithMostOfType.putIfAbsent(map2.getKey(), deque);
                    biggestValues.putIfAbsent(map2.getKey(), map2.getValue());
                }
            }
        }
        computeScores(round, playersWithMostOfType, typeOfScore, game);
    }

    public void computeScores(int round, Map<BuildingType, List<Player>> mapPlayerWithMostOfType, String typeOfScore, Game game){
        Map<BuildingType, List<Integer>> pointsPerBuildingType = new ScoringTable().makeRounds(3);

        for (Map.Entry<BuildingType, List<Player>> mapOfPlayerWithMost : mapPlayerWithMostOfType.entrySet()) {
            if (mapOfPlayerWithMost.getKey() != null){
                BuildingType type = mapOfPlayerWithMost.getKey();

                Player bestPlayer = new Player("test");
                Player secondBestPlayer = new Player("test");
                Player thirdBestPlayer = new Player("test");

                if (mapOfPlayerWithMost.getValue().size() == 1){
                    bestPlayer = mapOfPlayerWithMost.getValue().get(0);
                } else if (mapOfPlayerWithMost.getValue().size() == 2){
                    bestPlayer = mapOfPlayerWithMost.getValue().get(0);
                    secondBestPlayer = mapOfPlayerWithMost.getValue().get(1);
                } else if (mapOfPlayerWithMost.getValue().size() >= 3){
                    bestPlayer = mapOfPlayerWithMost.getValue().get(0);
                    secondBestPlayer = mapOfPlayerWithMost.getValue().get(1);
                    thirdBestPlayer = mapOfPlayerWithMost.getValue().get(2);
                }

                int bestScore = pointsPerBuildingType.get(type).get(0);
                int secondScore = pointsPerBuildingType.get(type).get(1);
                int thirdScore = pointsPerBuildingType.get(type).get(2);

                if (round > 2) {
                    bestPlayer.setVirtualScore(bestScore);
                    secondBestPlayer.setVirtualScore(secondScore);
                    thirdBestPlayer.setVirtualScore(thirdScore);
                } else if (round == 2) {
                    bestPlayer.setVirtualScore(bestScore);
                    secondBestPlayer.setVirtualScore(secondScore);
                } else {
                    bestPlayer.setVirtualScore(bestScore);
                }

                if (typeOfScore.equals("score")){
                    if (round > 2) {
                        bestPlayer.setScore(bestScore);
                        secondBestPlayer.setScore(secondScore);
                        thirdBestPlayer.setScore(thirdScore);
                        game.setEnded(true);

                    } else if (round == 2) {
                        bestPlayer.setScore(bestScore);
                        secondBestPlayer.setScore(secondScore);
                    } else if (round == 1){
                        bestPlayer.setScore(bestScore);
                    }
                }
            }
        }
    }
}