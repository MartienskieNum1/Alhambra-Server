package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private final String username;
    private int score;
    private int virtualScore;
    private List<Coin> coins;
    private List<Building> reserve;
    private List<Building> buildingsInHand;
    private List<Building> city;

    @JsonCreator
    public Player(@JsonProperty("playerName") String username) {
        this.username = username;
        this.score = 0;
        this.virtualScore = 0;
        this.coins =  new LinkedList<>();
        this.reserve = new LinkedList<>();
        this.buildingsInHand = new LinkedList<>();
        this.city = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getVirtualScore() {
        return virtualScore;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public List<Building> getReserve() {
        return reserve;
    }

    public List<Building> getBuildingsInHand() {
        return buildingsInHand;
    }

    public List<Building> getCity() {
        return city;
    }
}
