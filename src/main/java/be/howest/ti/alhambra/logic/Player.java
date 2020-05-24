package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

@JsonIgnoreProperties(value = {"coins", "reserve", "buildingsInHand","city"})
public class Player {

    private final String username;
    private int score;
    private int virtualScore;
    private boolean ready;
    private List<Coin> coins;
    private List<Building> reserve;
    private List<Building> buildingsInHand;
    private LinkedList<LinkedList<Building>> city;

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

    public List<LinkedList<Building>> getCity() {
        return city;
    }

    public void setItselfReady(){ ready = true; }

    public void setItselfNotReady(){ ready = false; }

    public void addCoinToWallet(Coin coin){
        coins.add(coin);
    }

    public void buyBuilding(Building building, List<Coin> coins) {
        for(Coin coin : coins) {
            this.coins.removeIf(coin::equals);
        }
        buildingsInHand.add(building);
    }

    public void addBaseToCity() {
        LinkedList<Building> basicList = new LinkedList<>();
        Building fountain = new Building(null, 0);
        fountain.putWallOnBuilding(false, false, false, false);
        basicList.add(fountain);
        city.add(basicList);
    }

    public void buildBuilding(Building building, int row, int col) {
        if (buildingsInHand.contains(building) || reserve.contains(building) &&city.size()/2 < Math.abs(row) || city.size()/2 < Math.abs(col)) {
                LinkedList<Building> initList1 = new LinkedList<>();
                initList1.add(null);
                LinkedList<Building> initList2 = new LinkedList<>();
                initList2.add(null);
                city.add(0,initList1);
                city.add(city.size(),initList2);
                for (LinkedList<Building> cityRow : city) {
                    while (cityRow.size() < city.size()) {
                        cityRow.add(0,null);
                        cityRow.add(cityRow.size(),null);
                    }
                }

            row = (city.size() / 2) + row;
            col = (city.size() / 2) + col;

            setBuildingInCity(row, col, building);

            buildingsInHand.remove(building);
        }
    }

    private void setBuildingInCity(int row, int col, Building building) {
        for (int cityRow = 0; cityRow < city.size(); cityRow++) {
            for (int cityCol = 0; cityCol < city.get(cityRow).size(); cityCol++) {
                if (cityRow == row && cityCol == col) {
                    city.get(cityRow).set(cityCol, building);
                }
            }
        }
    }

    public void placeInReserve(Building building) {
        for (Building buildingInHand : buildingsInHand) {
            if (building.equals(buildingInHand)) {
                reserve.add(building);
                buildingsInHand.remove(building);
            }

        }
    }

    public void redesign(Building building, int row, int col) {
        buildBuilding(building, row, col);
        reserve.remove(building);
    }

    public void redesign(int row, int col) {
        row = (city.size() / 2) + row;
        col = (city.size() / 2) + col;

        for (int cityRow = 0; cityRow < city.size(); cityRow++) {
            for (int cityCol = 0; cityCol < city.get(cityRow).size(); cityCol++) {
                if (cityRow == row && cityCol == col) {
                    Building building = city.get(cityRow).get(cityCol);
                    if (building == null) {
                        throw new IllegalArgumentException("There is no building!");
                    } else {
                        reserve.add(building);
                        city.get(cityRow).set(cityCol, null);
                    }
                }
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVirtualScore(int virtualScore) {
        this.virtualScore = virtualScore;
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
