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

    public void clearGames(){
        games.clear();
    }

    public Game addGame(){
         gameId = groupNr + "-" + gameIdCounter;
        if (gameIdCounter < 10){
            gameId = groupNr + "-" + "00" + gameIdCounter;
        }
        else if (gameIdCounter < 100){
            gameId = groupNr + "-" + "0" + gameIdCounter;
        }

        Game newlyMadeGame = new Game(gameId, groupNr);
        games.put(gameId, newlyMadeGame);
        gameIdCounter += 1;
        return newlyMadeGame;
    }

    public Game findGame(String gameToFind){
        Game matchingGame = games.get(gameToFind);
        return matchingGame;
    }

    public Map<String,Game> getGames() {
        return games;
    }
}
