package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;

import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

/**
 * @author Marcos Arquero Castillo
 *
 */

public class TicTacToeGameTest {
	TicTacToeGame game;
	Connection con1, con2;
	Player p0, p1;

	@BeforeEach
	public void setUp(){
		game = new TicTacToeGame();
		con1 = mock(Connection.class);
		con2 = mock(Connection.class);
		p0 = new Player(0,"cris","Cristina");
		p1 = new Player(1,"raul", "Raul");
		game.addConnection(con1);
		game.addConnection(con2);
		
		// /*
		game.addPlayer(p0);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y el jugador p0
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y los jugadores p0 y p1
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		//Comprobamos que ambas conexiones reciben SET_TURN, con el jugador p0
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		// */
		
	}

	@Test
	public void testPlayer0Wins() {
		/*
		game.addPlayer(p0);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y el jugador p0
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y los jugadores p0 y p1
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		//Comprobamos que ambas conexiones reciben SET_TURN, con el jugador p0
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		*/
		game.mark(2); //Jugador 0 marca casilla 2
		game.mark(7); //Jugador 1 marca casilla 7
		game.mark(4); //Jugador 0 marca casilla 4
		game.mark(6); //Jugador 1 marca casilla 6
		game.mark(8); //Jugador 0 marca casilla 8
		game.mark(0); //Jugador 1 marca casilla 0
		
		//Comprobamos las veces que cada conexion ha recibido SET_TURN con cada jugador
		verify(con1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(con2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		
		game.mark(5); //Jugador 0 marca casilla 5
		
		//Comprobamos que ha ganado el jugador 0
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(con1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertNotNull(argument.getValue());
	    assertEquals(argument.getValue().player, p0);		
	}
	
	@Test
	public void testPlayer0Looses() {
		/*
		game.addPlayer(p0);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y el jugador p0
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y los jugadores p0 y p1
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		//Comprobamos que ambas conexiones reciben SET_TURN, con el jugador p0
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		*/
		game.mark(7); //Jugador 0 marca casilla 7
		game.mark(2); //Jugador 1 marca casilla 2
		game.mark(8); //Jugador 0 marca casilla 8
		game.mark(6); //Jugador 1 marca casilla 6
		game.mark(0); //Jugador 0 marca casilla 0
		
		//Comprobamos las veces que cada conexion ha recibido SET_TURN con cada jugador
		verify(con1,times(2)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2,times(2)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(con2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		
		game.mark(4); //Jugador 1 marca casilla 4
		
		//Comprobamos que ha ganado el jugador 1
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(con1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertNotNull(argument.getValue());
	    assertEquals(argument.getValue().player, p1);	
	}
	
	@Test
	public void testDraw() {
		/*
		game.addPlayer(p0);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y el jugador p0
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0)));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		//Comprobamos que ambas conexiones reciben JOIN_GAME, y los jugadores p0 y p1
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p0,p1)));
		//Comprobamos que ambas conexiones reciben SET_TURN, con el jugador p0
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		*/
		game.mark(4); //Jugador 0 marca casilla 4
		game.mark(2); //Jugador 1 marca casilla 2
		game.mark(8); //Jugador 0 marca casilla 8
		game.mark(0); //Jugador 1 marca casilla 0
		game.mark(1); //Jugador 0 marca casilla 1
		game.mark(7); //Jugador 1 marca casilla 7
		game.mark(3); //Jugador 0 marca casilla 3
		game.mark(5); //Jugador 1 marca casilla 5
		
		//Comprobamos las veces que cada conexion ha recibido SET_TURN con cada jugador
		verify(con1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(con2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		
		game.mark(6); //Jugador 0 marca casilla 6
		
		//Comprobamos que han empatado
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(con1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertNull(argument.getValue());
	}

}
