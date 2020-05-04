package be.howest.ti.alhambra.logic;

import org.apache.commons.lang3.builder.StandardToStringStyle;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    public static List<Game> games = new LinkedList<>();
    private static final String groupNr = "group27";
    private static int gameIdCounter = 0;
    private static String gameIdString = "";
    private static int gameId = 0;

    public static Game addGame(){
        gameId = gameIdCounter;
        if (gameIdCounter < 10){
            gameIdString = "00" + gameIdCounter;
        }
        else if (gameIdCounter < 100){
            gameIdString = "0" + gameIdCounter;
        }
        gameId = Integer.parseInt(gameIdString);
        Game newlyMadeGame = new Game(groupNr, gameId, new LinkedList<>());
           games.add(newlyMadeGame);
           gameIdCounter += 1;
           return newlyMadeGame;
    }

    public static void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId() == gameToRemove.getGameId());
    }

    public static Game findGame(Game gameToFind){
        Game matchingGame = null;
        for (Game game : games){
            if (game.getGameId()==gameToFind.getGameId()){
                matchingGame = game;
            }
        }
        return matchingGame;
    }

    public static List<Game> getGames() {
        return games;
    }
}
