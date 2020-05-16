package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Alhambra alhambra = new Alhambra();
    Game myGame;
    Player player1 = new Player("maarten");
    Player player2 = new Player("jos");
    Building building1 = new Building(BuildingType.GARDEN, 9);

    @BeforeEach
    void fillAlhambra() {
        alhambra.addGame("group27"); //group27-000
        myGame = alhambra.findGame("group27-000");

        myGame.addPlayer("group27-000+maarten", player1);
        myGame.addPlayer("group27-000+jos", player2);
        myGame.setPlayerReady("group27-000+maarten");
        myGame.setPlayerReady("group27-000+jos");
    }

    @Test
    void buildBuilding() {
        //
    }

    @Test
    void placeInReserve() {
        //
    }
}