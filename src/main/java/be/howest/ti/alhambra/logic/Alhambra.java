package be.howest.ti.alhambra.logic;


import io.vertx.core.json.Json;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    public List<Game> games = new LinkedList<>();

    private int getCounter(String groupNr){
        int counter = 0;
        for (Game game : games){
            if (game.getGroupNr().equals(groupNr)){
                counter++;
            }
        }
        return counter;
    }

    public Game addGame(String groupNr){
        int counter = getCounter(groupNr);
        String gameId = groupNr + "-" + counter;
        if (counter < 10){
            gameId = groupNr + "-" + "00" + counter;
        }
        else if (counter < 100){
            gameId = groupNr + "-" + "0" + counter;
        }


        Game newlyMadeGame = new Game(gameId, groupNr);
        games.add(newlyMadeGame);
        return newlyMadeGame;
    }


    public void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId().equals(gameToRemove.getGameId()));
    }

    public Game findGame(String gameToFind){
        Game matchingGame = null;
        for (Game game : games){
            if (game.getGameId().equals(gameToFind)){
                matchingGame = game;
            }
        }
        return matchingGame;
    }

    public List<Game> getGames() {
        return games;
    }
}
