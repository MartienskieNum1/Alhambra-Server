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
        Map<BuildingType, Deque<Player>> playersWithMostOfType = new HashMap<>();
        Map<BuildingType, Integer> biggestValues = new HashMap<>();
        Deque<Player> deque = new LinkedList();

        for (Map.Entry<Player, Map<BuildingType, Integer>> entry : map.entrySet()) {
            Player playerToCheck = entry.getKey();

            for (Map.Entry<BuildingType, Integer> map2 : entry.getValue().entrySet()) {
                if (playersWithMostOfType.containsKey(map2.getKey()) && biggestValues.containsKey(map2.getKey())) {
                    if (map2.getValue() > biggestValues.get(map2.getKey())) {
                        biggestValues.replace(map2.getKey(), biggestValues.get(map2.getKey()), map2.getValue());
                        deque.addFirst(playerToCheck);
                        playersWithMostOfType.replace((map2.getKey()), playersWithMostOfType.get(map2.getKey()), deque);
                    }
                } else {
                    deque.addFirst(playerToCheck);
                    playersWithMostOfType.putIfAbsent(map2.getKey(), deque);
                    biggestValues.putIfAbsent(map2.getKey(), map2.getValue());
                }
            }
        }
        computeScores(round, playersWithMostOfType, typeOfScore, game);
    }

    public void computeScores(int round, Map<BuildingType, Deque<Player>> mapPlayerWithMostOfType, String typeOfScore, Game game){
        Map<BuildingType, List<Integer>> pointsPerBuildingType = new ScoringTable().makeRounds(3);

        for (Map.Entry<BuildingType, Deque<Player>> mapOfPlayerWithMost : mapPlayerWithMostOfType.entrySet()) {
            if (mapOfPlayerWithMost.getKey() != null){
                BuildingType type = mapOfPlayerWithMost.getKey();

                Player bestPlayer = new Player("test");
                Player secondBestPlayer = new Player("test");
                Player thirdBestPlayer = new Player("test");

                int bestScore = pointsPerBuildingType.get(type).get(0);
                int secondScore = pointsPerBuildingType.get(type).get(1);
                int thirdScore = pointsPerBuildingType.get(type).get(2);

                if (mapOfPlayerWithMost.getValue().size() == 1){
                    bestPlayer =  mapOfPlayerWithMost.getValue().pollFirst();
                    bestPlayer.setVirtualScore(bestPlayer.getVirtualScore() + thirdScore);
                } else if (mapOfPlayerWithMost.getValue().size() == 2){
                    bestPlayer =  mapOfPlayerWithMost.getValue().pollFirst();
                    secondBestPlayer =  mapOfPlayerWithMost.getValue().pollFirst();
                    bestPlayer.setVirtualScore(bestPlayer.getVirtualScore() + secondScore);
                    assert secondBestPlayer != null;
                    secondBestPlayer.setVirtualScore(secondBestPlayer.getVirtualScore() + thirdScore);
                } else if (mapOfPlayerWithMost.getValue().size() >= 3){
                    bestPlayer =  mapOfPlayerWithMost.getValue().pollFirst();
                    secondBestPlayer =  mapOfPlayerWithMost.getValue().pollFirst();
                    thirdBestPlayer = mapOfPlayerWithMost.getValue().pollFirst();
                    bestPlayer.setVirtualScore(bestPlayer.getVirtualScore() + bestScore);
                    assert secondBestPlayer != null;
                    secondBestPlayer.setVirtualScore(secondBestPlayer.getVirtualScore() + secondScore);
                    assert thirdBestPlayer != null;
                    thirdBestPlayer.setVirtualScore(thirdBestPlayer.getVirtualScore() + thirdScore);
                }

                if (typeOfScore.equals("score")){
                    if (round > 2) {
                        bestPlayer.setScore(bestPlayer.getScore() + bestScore);
                        secondBestPlayer.setScore(secondBestPlayer.getScore() + secondScore);
                        thirdBestPlayer.setScore(thirdBestPlayer.getScore() + thirdScore);
                        game.setEnded(true);

                    } else if (round == 2) {
                        bestPlayer.setScore(bestPlayer.getScore() + secondScore);
                        secondBestPlayer.setScore(secondBestPlayer.getScore() + thirdScore);
                    } else if (round == 1){
                        bestPlayer.setScore(bestPlayer.getScore() + thirdScore);
                    }
                }
            }
        }
    }
}