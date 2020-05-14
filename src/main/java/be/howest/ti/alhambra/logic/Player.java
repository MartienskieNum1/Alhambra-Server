package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(value = {"coins", "reserve", "buildingsInHand","city"})
public class Player {

    private final String username;
    private int score;
    private int virtualScore;
    private boolean ready;
    private List<Coin> coins;
    private List<Building> reserve;
    private List<Building> buildingsInHand;
    private List<Building> city;

    @JsonCreator
    public Player(@JsonProperty("playerName") String username) {
        this.username = username;
        this.score = 0;
        this.virtualScore = 0;
        this.ready = false;
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

    public boolean isReady() {
        return ready;
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

    public void setItselfReady(){ ready = true; }

    public void setItselfNotReady(){ ready = false; }

    public void addCoinToWallet(Coin coin){
        coins.add(coin);
    }

    public void addBuilding(Building building, List<Coin> coins) {
        for(Coin coin : coins) {
            this.coins.removeIf(coin::equals);
        }
        buildingsInHand.add(building);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score == player.score &&
                virtualScore == player.virtualScore &&
                ready == player.ready &&
                Objects.equals(username, player.username) &&
                Objects.equals(coins, player.coins) &&
                Objects.equals(reserve, player.reserve) &&
                Objects.equals(buildingsInHand, player.buildingsInHand) &&
                Objects.equals(city, player.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score, virtualScore, ready, coins, reserve, buildingsInHand, city);
    }
}
