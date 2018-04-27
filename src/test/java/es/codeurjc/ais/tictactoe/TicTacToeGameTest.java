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

class TicTacToeGameTest {
	TicTacToeGame game;
	Connection con1, con2;
	Player p0, p1;
	private List<Player> players = new CopyOnWriteArrayList<>();

	@BeforeEach
	void setUp(){
		game = new TicTacToeGame();
		con1 = mock(Connection.class);
		con2 = mock(Connection.class);
		p0 = new Player(0,"cris","Cristina");
		p1 = new Player(1,"raul", "Raul");
		game.addConnection(con1);
		game.addConnection(con2);
	}

	@Test
	void testPlayer0Wins() {
		game.addPlayer(p0);
		players.add(p0);
		//verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p1, p2)));
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		players.add(p1);
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		game.mark(2);
		game.mark(7);
		game.mark(4);
		game.mark(6);
		game.mark(8);
		game.mark(0);
		verify(con1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(con2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		game.mark(5);
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(con1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertNotNull(argument.getValue());
	    assertEquals(argument.getValue().player, p0);		
	}
	
	@Test
	void testPlayer0Looses() {
		game.addPlayer(p0);
		players.add(p0);
		//verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p1, p2)));
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		players.add(p1);
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		game.mark(7);
		game.mark(2);
		game.mark(8);
		game.mark(6);
		game.mark(0);
		verify(con1,times(2)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2,times(2)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(con2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		game.mark(4);
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(con1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertNotNull(argument.getValue());
	    assertEquals(argument.getValue().player, p1);	
	}
	
	@Test
	void testDraw() {
		game.addPlayer(p0);
		players.add(p0);
		//verify(con1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(p1, p2)));
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		reset(con1);
		reset(con2);
		game.addPlayer(p1);
		players.add(p1);
		verify(con1).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con2).sendEvent(eq(EventType.JOIN_GAME), eq(players));
		verify(con1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(con1);
		reset(con2);
		game.mark(4);
		game.mark(2);
		game.mark(8);
		game.mark(0);
		game.mark(1);
		game.mark(7);
		game.mark(3);
		game.mark(5);
		verify(con1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(con1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(con2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		game.mark(6);
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(con1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertNull(argument.getValue());
	}

}
