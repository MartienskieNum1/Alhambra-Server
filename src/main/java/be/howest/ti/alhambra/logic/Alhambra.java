package be.howest.ti.alhambra.logic;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    public static List<Game> games = new LinkedList<>();
    private static final String groupNr = "group27";
    private static int gameId = 000;

    public static void addGame(){
           games.add(new Game(groupNr, gameId, new LinkedList<>()));
           gameId += 1;
    }

    public static void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId() == gameToRemove.getGameId());
    }

    public static List<Game> getGames() {
        return games;
    }
}
