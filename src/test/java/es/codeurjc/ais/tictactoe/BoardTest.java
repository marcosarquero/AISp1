
package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.*;

/**
 * @author Marcos Arquero Castillo
 *
 */
class BoardTest {
	Board board;
	int[] actNum;
	List<Integer> l;

	@BeforeEach
	void setUp(){
		board = new Board();
		l = new LinkedList<Integer>();
	}

	@Test
	void testPlayer0Wins() {
		board.getCell(2).value = "0"; //Jugador 0 marca la casilla 2
		board.getCell(7).value = "1"; //Jugador 1 marca la casilla 7
		board.getCell(4).value = "0"; //Jugador 0 marca la casilla 4
		board.getCell(6).value = "1"; //Jugador 1 marca la casilla 6
		board.getCell(8).value = "0"; //Jugador 0 marca la casilla 8
		board.getCell(0).value = "1"; //Jugador 1 marca la casilla 0
		
		//Comprobamos que no ha ganado nadie todavía ni se ha empatado
		assertNull(board.getCellsIfWinner("0"));
		assertNull(board.getCellsIfWinner("1"));
		assertFalse(board.checkDraw());
		
		board.getCell(5).value = "0"; //Jugador 0 marca la casilla 5
		
		//getCellsIfWinner devulve array de enteros, pero necesitamos iterable, así que creamos una lista
		actNum = board.getCellsIfWinner("0"); 
		assertNotNull(actNum);
		for(int i=0; i<3; i++) {
			l.add(actNum[i]);
		}
		//Comprobamos que ha ganado el jugador 0
		assertThat(l,hasItems(2,5,8));
		assertNull(board.getCellsIfWinner("1"));
		assertFalse(board.checkDraw());
		
	}

	@Test
	void testPlayer0Looses() {
		board.getCell(7).value = "0"; //Jugador 0 marca la casilla 7
		board.getCell(2).value = "1"; //Jugador 1 marca la casilla 2
		board.getCell(8).value = "0"; //Jugador 0 marca la casilla 8
		board.getCell(6).value = "1"; //Jugador 1 marca la casilla 6
		board.getCell(0).value = "0"; //Jugador 0 marca la casilla 0
		
		//Comprobamos que no ha ganado nadie todavía ni se ha empatado
		assertNull(board.getCellsIfWinner("0"));
		assertNull(board.getCellsIfWinner("1"));
		assertFalse(board.checkDraw());
		
		board.getCell(4).value = "1"; //Jugador 1 marca la casilla 4
		
		
		//getCellsIfWinner devulve array de enteros, pero necesitamos iterable, así que creamos una lista
		actNum = board.getCellsIfWinner("1"); 
		assertNotNull(actNum);
		for(int i=0; i<3; i++) {
			l.add(actNum[i]);
		}
		//Comprobamos que ha ganado el jugador 0
		assertThat(l,hasItems(2,4,6));
		assertNull(board.getCellsIfWinner("0"));
		assertFalse(board.checkDraw());
	}
	
	@Test
	void testDraw() {
		board.getCell(4).value = "0"; //Jugador 0 marca la casilla 4
		board.getCell(2).value = "1"; //Jugador 1 marca la casilla 2
		board.getCell(8).value = "0"; //Jugador 0 marca la casilla 8
		board.getCell(0).value = "1"; //Jugador 1 marca la casilla 0
		board.getCell(1).value = "0"; //Jugador 0 marca la casilla 1
		board.getCell(7).value = "1"; //Jugador 1 marca la casilla 7
		board.getCell(3).value = "0"; //Jugador 0 marca la casilla 3
		board.getCell(5).value = "1"; //Jugador 1 marca la casilla 5
		
		//Comprobamos que no ha ganado nadie todavía ni se ha empatado
		assertNull(board.getCellsIfWinner("0"));
		assertNull(board.getCellsIfWinner("1"));
		assertFalse(board.checkDraw());
		
		board.getCell(6).value = "0"; //Jugador 0 marca la casilla 6
		
		//Comprobamos que hay empate
		assertNull(board.getCellsIfWinner("0"));
		assertNull(board.getCellsIfWinner("1"));
		assertTrue(board.checkDraw());
	}

}
