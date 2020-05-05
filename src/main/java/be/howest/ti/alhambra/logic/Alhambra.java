package be.howest.ti.alhambra.logic;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    private static List<Game> games = new LinkedList<>();
    private static final String GROUPNR = "group27";
    private static int gameId = 0;

    public static Game addGame(){
        Game newlyMadeGame = new Game(GROUPNR, gameId, new LinkedList<>());
           games.add(newlyMadeGame);
           gameId += 1;
           return newlyMadeGame;
    }

    public void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId() == gameToRemove.getGameId());
    }

    public static List<Game> getGames() {
        return games;
    }

    public static Game findGame(String gameId) {
        for (Game game : games) {
            if (game.getGameInfo().equals(gameId)) {
                return game;
            }
        }
        return null;
    }
}
