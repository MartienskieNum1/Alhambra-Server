package be.howest.ti.alhambra.logic;

import java.util.LinkedList;
import java.util.List;

public class Alhambra {
    private List<Game> games = new LinkedList<>();
    private static final String GROUPNR = "group27";
    private int gameId = 0;

    public void addGame(){
           games.add(new Game(GROUPNR, gameId, new LinkedList<>()));
           gameId += 1;
    }

    public void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId() == gameToRemove.getGameId());
    }

    public List<Game> getGames() {
        return games;
    }

    public Game find(String gameId) {
        for (Game game : games) {
            if (game.getGameInfo().equals(gameId)) {
                return game;
            }
        }
        return null;
    }
}
