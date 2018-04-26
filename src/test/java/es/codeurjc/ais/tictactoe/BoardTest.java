
package es.codeurjc.ais.tictactoe;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.*;

/**
 * @author Marcos Arquero Castillo
 *
 */
class BoardTest {
	Board board;

	@BeforeEach
	void setUp(){
		board = new Board();
	}

	@Test
	void testPlayer1Wins() {
		int[] expNum = {2,5,8};
		int[] actNum;
		board.getCell(2).value = "1"; //Jugador 1 marca la casilla 2
		board.getCell(7).value = "2"; //Jugador 2 marca la casilla 7
		board.getCell(4).value = "1"; //Jugador 1 marca la casilla 4
		board.getCell(6).value = "2"; //Jugador 2 marca la casilla 6
		board.getCell(8).value = "1"; //Jugador 1 marca la casilla 8
		board.getCell(0).value = "2"; //Jugador 2 marca la casilla 0
		
		//Comprobamos que no ha ganado nadie todavía ni se ha empatado
		assertNull(board.getCellsIfWinner("1"));
		assertNull(board.getCellsIfWinner("2"));
		assertFalse(board.checkDraw());
		
		board.getCell(5).value = "1"; //Jugador 1 marca la casilla 5
		
		//Comprobamos que ha ganado el jugador 1
		actNum = board.getCellsIfWinner("1");
		assertNotNull(actNum);
		for(int i=0; i<3; i++) {
			assertEquals(actNum[i], expNum[i]);
		}
		assertNull(board.getCellsIfWinner("2"));
		assertFalse(board.checkDraw());
		
	}

	@Test
	void testPlayer1Looses() {
		int[] expNum = {6,4,2};
		int[] actNum;
		board.getCell(7).value = "1"; //Jugador 1 marca la casilla 7
		board.getCell(2).value = "2"; //Jugador 2 marca la casilla 2
		board.getCell(8).value = "1"; //Jugador 1 marca la casilla 8
		board.getCell(6).value = "2"; //Jugador 2 marca la casilla 6
		board.getCell(0).value = "1"; //Jugador 1 marca la casilla 0
		
		//Comprobamos que no ha ganado nadie todavía ni se ha empatado
		assertNull(board.getCellsIfWinner("1"));
		assertNull(board.getCellsIfWinner("2"));
		assertFalse(board.checkDraw());
		
		board.getCell(4).value = "2"; //Jugador 2 marca la casilla 4
		
		//Comprobamos que ha ganado el jugador 2
		actNum = board.getCellsIfWinner("2");
		assertNull(board.getCellsIfWinner("1"));
		assertNotNull(actNum);
		for(int i=0; i<3; i++) {
			assertEquals(actNum[i], expNum[i]);
		}
		assertFalse(board.checkDraw());
	}
	
	@Test
	void testDraw() {
		board.getCell(4).value = "1"; //Jugador 1 marca la casilla 4
		board.getCell(2).value = "2"; //Jugador 2 marca la casilla 2
		board.getCell(8).value = "1"; //Jugador 1 marca la casilla 8
		board.getCell(0).value = "2"; //Jugador 2 marca la casilla 0
		board.getCell(1).value = "1"; //Jugador 1 marca la casilla 1
		board.getCell(7).value = "2"; //Jugador 2 marca la casilla 7
		board.getCell(3).value = "1"; //Jugador 1 marca la casilla 3
		board.getCell(5).value = "2"; //Jugador 2 marca la casilla 5
		
		//Comprobamos que no ha ganado nadie todavía ni se ha empatado
		assertNull(board.getCellsIfWinner("1"));
		assertNull(board.getCellsIfWinner("2"));
		assertFalse(board.checkDraw());
		
		board.getCell(6).value = "1"; //Jugador 1 marca la casilla 6
		
		//Comprobamos que hay empate
		assertNull(board.getCellsIfWinner("1"));
		assertNull(board.getCellsIfWinner("2"));
		assertTrue(board.checkDraw());
	}

}
