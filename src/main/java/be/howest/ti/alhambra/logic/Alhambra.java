package be.howest.ti.alhambra.logic;


import java.util.HashMap;
import java.util.Map;

public class Alhambra {
    private Map<String, Game> games = new HashMap<>();

    private int getCounter(String groupNr){
        int counter = 0;
        for (Map.Entry<String, Game>  entry : games.entrySet()){
            Game game = entry.getValue();
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

    public Game findGame(String gameToFind){
        return games.get(gameToFind);
    }

    public Map<String,Game> getGames() {
        return games;
    }
}
