package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Alhambra alhambra = new Alhambra();
    Game myGame;
    Player player1 = new Player("maarten");
    Player player2 = new Player("jos");
    Player player3 = new Player("jef");

    @BeforeEach
    void fillAlhambra() {
        alhambra.addGame("group27"); //group27-000
        myGame = alhambra.findGame("group27-000");

        myGame.addPlayer("group27-000+maarten", player1);
        myGame.addPlayer("group27-000+jos", player2);
        myGame.addPlayer("group27-000+jef", player3);
    }

    @Test
    void setPlayerReady() {
        assertFalse(player1.isReady());
        myGame.setPlayerReady("group27-000+maarten");
        assertTrue(player1.isReady());
    }

    @Test
    void setPlayerNotReady() {
        myGame.setPlayerReady("group27-000+maarten");
        assertTrue(player1.isReady());
        myGame.setPlayerNotReady("group27-000+maarten");
        assertFalse(player1.isReady());
    }

    @Test
    void startGame() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertNotNull(myGame.getCurrentPlayer());

        for (Map.Entry<Currency, Building> entry : myGame.getMarket().entrySet()) {
            assertNotNull(entry.getValue());
        }

        for (Coin coin: myGame.getBank()) {
            assertNotNull(coin);
        }

        int totalValue = 0;
        for (Player player : myGame.getPlayersList()) {
            for (Coin coin : player.getCoins()) {
                totalValue += coin.getAmount();
            }
        }

        assertTrue(totalValue >= 20);
    }

    @Test
    void nextTurn() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertEquals(player2, myGame.getCurrentPlayer());
        myGame.nextTurn();
        assertEquals(player1, myGame.getCurrentPlayer());
        myGame.nextTurn();
        assertEquals(player3, myGame.getCurrentPlayer());
        myGame.nextTurn();
        assertEquals(player2, myGame.getCurrentPlayer());
    }

    @Test
    void giveMoney() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        // player can receive demanded coins
        Coin coin1 = myGame.getBank()[0];
        Coin[] wantedCoins = new Coin[]{coin1};
        int initAmountCoins = player2.getCoins().size();
        myGame.giveMoney("group27-000+jos", wantedCoins);
        assertTrue(initAmountCoins < player2.getCoins().size());

        // IllegalArgument is thrown when its not your turn
        Coin[] finalWantedCoins = wantedCoins;
        assertThrows(IllegalArgumentException.class,
                () -> myGame.giveMoney("group27-000+jos", finalWantedCoins));

        coin1 = myGame.getBank()[0];
        Coin coin2 = myGame.getBank()[1];
        Coin coin3 = myGame.getBank()[2];
        Coin coin4 = myGame.getBank()[3];

        // IllegalArgument is thrown when too high value is taken
        wantedCoins = new Coin[]{coin1, coin2, coin3, coin4};
        Coin[] finalWantedCoins1 = wantedCoins;
        assertThrows(IllegalArgumentException.class,
                () -> myGame.giveMoney("group27-000+maarten", finalWantedCoins1));

        // IllegalArgument is thrown when player wants money that doesn't exist
        Coin illegalCoin = new Coin(Currency.BLUE, 15);
        wantedCoins = new Coin[]{illegalCoin};
        Coin[] finalWantedCoins2 = wantedCoins;
        assertThrows(IllegalArgumentException.class,
                () -> myGame.giveMoney("group27-000+maarten", finalWantedCoins2));
    }

    @Test
    void buyBuilding() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertEquals(Collections.emptyList(), player2.getBuildingsInHand());

        Building building = myGame.getMarket().get(Currency.BLUE);
        Coin selfMadeCoin = new Coin(Currency.BLUE, building.getCost());
        List<Coin> coins = new LinkedList<>();
        coins.add(selfMadeCoin);
        player2.addCoinToWallet(selfMadeCoin);
        myGame.buyBuilding("group27-000+jos", coins, Currency.BLUE);

        // player can buy a building
        assertEquals(1, player2.getBuildingsInHand().size());

        // IllegalArgument is thrown when its not your turn
        assertThrows(IllegalArgumentException.class,
                () -> myGame.buyBuilding("group27-000+maarten", coins, Currency.BLUE));
    }

    @Test
    void buyBuildingFalseMoney() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertEquals(Collections.emptyList(), player2.getBuildingsInHand());

        Coin selfMadeCoin = new Coin(Currency.BLUE, 15);
        List<Coin> coins = new LinkedList<>();
        coins.add(selfMadeCoin);

        // IllegalArgument is thrown when you pay with money you don't own
        assertThrows(IllegalArgumentException.class,
                () -> myGame.buyBuilding("group27-000+jos", coins, Currency.BLUE));
    }

    @Test
    void buyBuildingTooLess() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        assertEquals(Collections.emptyList(), player2.getBuildingsInHand());

        Building building = myGame.getMarket().get(Currency.BLUE);
        Coin selfMadeCoin = new Coin(Currency.BLUE, building.getCost()-1);
        List<Coin> coins = new LinkedList<>();
        coins.add(selfMadeCoin);
        player2.addCoinToWallet(selfMadeCoin);

        // IllegalArgument is thrown when you pay too less
        assertThrows(IllegalArgumentException.class,
                () -> myGame.buyBuilding("group27-000+jos", coins, Currency.BLUE));
    }

    @Test
    void buildBuilding() {
        LinkedList<LinkedList<Building>> init = new LinkedList<>();
        LinkedList<Building> row0 = new LinkedList<>();
        Building fountain = new Building(null, 0);
        fountain.putWallOnBuilding(false, false, false, false);
        row0.add(fountain);
        init.add(row0);

        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        Building building = myGame.getMarket().get(Currency.BLUE);
        Coin selfMadeCoin = new Coin(Currency.BLUE, building.getCost());
        List<Coin> coins = new LinkedList<>();
        coins.add(selfMadeCoin);
        player2.addCoinToWallet(selfMadeCoin);
        myGame.buyBuilding("group27-000+jos", coins, Currency.BLUE);

        // check init city + in hand
        assertEquals(init, player2.getCity());
        assertEquals(1, player2.getBuildingsInHand().size());

        // IllegalArgument is thrown when its not your turn
        assertThrows(IllegalArgumentException.class,
                () -> myGame.buildBuilding("group27-000+maarten", building, 0, -1));

        row0.add(0, null);
        row0.add(row0.size(), null);
        LinkedList<Building> rowMin1 = new LinkedList<>();
        LinkedList<Building> row1 = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            rowMin1.add(null);
            row1.add(null);
        }
        init.add(0, rowMin1);
        init.add(init.size(), row1);
        init.get(1).set(0, building);

        myGame.buildBuilding("group27-000+jos", building, 0, -1);

        // Player can place building in Alhambra + no more in hand
        assertEquals(init, player2.getCity());
        assertEquals(0, player2.getBuildingsInHand().size());
    }

    @Test
    void buildBuildingToReserve() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        Building building = myGame.getMarket().get(Currency.BLUE);
        Coin selfMadeCoin = new Coin(Currency.BLUE, building.getCost());
        List<Coin> coins = new LinkedList<>();
        coins.add(selfMadeCoin);
        player2.addCoinToWallet(selfMadeCoin);
        myGame.buyBuilding("group27-000+jos", coins, Currency.BLUE);

        // check init in hand + reserve
        assertEquals(1, player2.getBuildingsInHand().size());
        assertEquals(0, player2.getReserve().size());

        myGame.buildBuilding("group27-000+jos", building, 0, 0);

        // Player can place building in Alhambra + no more in hand
        assertEquals(1, player2.getReserve().size());
        assertEquals(0, player2.getBuildingsInHand().size());
    }

    @Test
    void redesign() {
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jef");
        myGame.setPlayerReady("group27-000+jos");

        Building building = myGame.getMarket().get(Currency.BLUE);
        Coin selfMadeCoin = new Coin(Currency.BLUE, building.getCost());
        List<Coin> coins = new LinkedList<>();
        coins.add(selfMadeCoin);
        player2.addCoinToWallet(selfMadeCoin);

        myGame.buyBuilding("group27-000+jos", coins, Currency.BLUE);

        myGame.buildBuilding("group27-000+jos", building, 0, -1);

        // check init reserve
        assertEquals(0, player2.getReserve().size());

        // IllegalArgument is thrown when its not your turn
        assertThrows(IllegalArgumentException.class, () -> myGame.redesign("group27-000+jos", 0, -1));

        myGame.nextTurn();
        myGame.nextTurn();

        // IllegalArgument is thrown when there is no building
        assertThrows(IllegalArgumentException.class, () -> myGame.redesign("group27-000+jos", 0, 1));

        myGame.redesign("group27-000+jos", 0, -1);

        // Player can put building from city in reserve
        assertEquals(1, player2.getReserve().size());
    }
}