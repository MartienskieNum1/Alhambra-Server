package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
        assertThrows(IllegalArgumentException.class, () -> myGame.giveMoney("group27-000+jos", finalWantedCoins));

        coin1 = myGame.getBank()[0];
        Coin coin2 = myGame.getBank()[1];
        Coin coin3 = myGame.getBank()[2];
        Coin coin4 = myGame.getBank()[3];

        // IllegalArgument is thrown when too high value is taken
        wantedCoins = new Coin[]{coin1, coin2, coin3, coin4};
        Coin[] finalWantedCoins1 = wantedCoins;
        assertThrows(IllegalArgumentException.class, () -> myGame.giveMoney("group27-000+maarten", finalWantedCoins1));

        // IllegalArgument is thrown when player wants money that doesn't exist
        Coin illegalCoin = new Coin(Currency.BLUE, 15);
        wantedCoins = new Coin[]{illegalCoin};
        Coin[] finalWantedCoins2 = wantedCoins;
        assertThrows(IllegalArgumentException.class, () -> myGame.giveMoney("group27-000+maarten", finalWantedCoins2));
    }
}