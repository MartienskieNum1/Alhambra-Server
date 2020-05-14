package be.howest.ti.alhambra.logic;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Alhambra {
    public Map<String, Game> games = new HashMap<>();
    private final String groupNr = "group27";
    private int gameIdCounter = 0;
    private String gameId = "";
    private List<Game> games = new LinkedList<>();

    private int getCounter(String groupNr){
        int counter = 0;
        for (Game game : games){
            if (game.getGroupNr().equals(groupNr)){
                counter++;
            }
        }
        return counter;
    }

    public void clearGames(){
        games.clear();
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
        games.put(gameId, newlyMadeGame);
        return newlyMadeGame;
    }

    public void removeGame(Game gameToRemove){
        games.removeIf(game -> game.getGameId().equals(gameToRemove.getGameId()));
    }

    public Game findGame(String gameToFind){
        Game matchingGame = games.get(gameToFind);
        return matchingGame;
    }

    public Map<String,Game> getGames() {
        return games;
    }
}
