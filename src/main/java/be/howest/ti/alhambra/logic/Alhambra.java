package be.howest.ti.alhambra.logic;

import org.apache.commons.lang3.builder.StandardToStringStyle;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    public static List<Game> games = new LinkedList<>();
    private static final String groupNr = "group27";
    private static int gameIdCounter = 0;
    private static String gameId = "";


    public static Game addGame(){
         gameId = groupNr + "-" + String.valueOf(gameIdCounter);
        if (gameIdCounter < 10){
            gameId = groupNr + "-" + "00" + gameIdCounter;
        }
        else if (gameIdCounter < 100){
            gameId = groupNr + "-" + "0" + gameIdCounter;
        }

        Game newlyMadeGame = new Game(gameId);
        games.add(newlyMadeGame);
        gameIdCounter += 1;
        return newlyMadeGame;
    }

    public static void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId() == gameToRemove.getGameId());
    }

    public static Game findGame(String gameToFind){
        Game matchingGame = null;
        for (Game game : games){
            if (game.getGameId().equals(gameToFind)){
                matchingGame = game;
            }
        }
        return matchingGame;
    }

    public static List<Game> getGames() {
        return games;
    }
}
