package be.howest.ti.alhambra.logic;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    public static List<Game> games = new LinkedList<>();
    private static final String groupNr = "group27";
    private static int gameId = 0;

    public static Game addGame(){
        Game newlyMadeGame = new Game(groupNr, gameId, new LinkedList<>());
           games.add(newlyMadeGame);
           gameId += 1;
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
