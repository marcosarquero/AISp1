package es.codeurjc.ais.tictactoe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Marcos Arquero Castillo
 *
 */

class TicTacToeGameTest {
	TicTacToeGame game;
	Connection con1, con2;
	Player p1, p2;

	@BeforeEach
	void setUp(){
		game = new TicTacToeGame();
		con1 = mock(Connection.class);
		con2 = mock(Connection.class);
		p1 = new Player(1,"cris","Cristina");
		p2 = new Player(2,"raul", "Raul");
		game.addConnection(con1);
		game.addConnection(con2);
		game.addPlayer(p1);
		game.addPlayer(p2);
	}

	@Test
	void testPlayer1Wins() {
		//Acciones de los mocks
		
		
	}
	
	@Test
	void testPlayer1Looses() {
		fail("Not yet implemented");
	}
	
	@Test
	void testDraw() {
		fail("Not yet implemented");
	}

}
