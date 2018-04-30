package es.codeurjc.ais.tictactoe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

class AplicationSystemTest {
	WebDriver d0, d1;
	WebElement nick0, nick1;
	WebElement[] casillas0, casillas1;
	WebDriverWait wait0, wait1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ChromeDriverManager.getInstance().setup();
		//WebApp.start();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//WebApp.stop();
	}

	@BeforeEach
	void setUp() throws Exception {
		d0 = new ChromeDriver();
		d1 = new ChromeDriver();
		wait0 = new WebDriverWait(d0, 30);
		wait1 = new WebDriverWait(d1, 30);
		d0.get("http://localhost:8080/");
		d1.get("http://localhost:8080/");
		d0.findElement(By.id("nickname")).sendKeys("Amanda");
		d0.findElement(By.id("startBtn")).click();
		d1.findElement(By.id("nickname")).sendKeys("Luis");
		d1.findElement(By.id("startBtn")).click();
		casillas0 = new WebElement[9];
		casillas1 = new WebElement[9];
		for (int i=0; i<9; i++) {
			casillas0[i] = d0.findElement(By.id("cell-"+i));
			casillas1[i] = d1.findElement(By.id("cell-"+i));
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		d0.quit();
		d1.quit();
	}

	void pulsar(int player, int cell) {
		if (player == 0) {
			wait0.until(ExpectedConditions.elementToBeClickable(By.id("cell-"+cell)));
			casillas0[cell].click();
		} else {
			wait1.until(ExpectedConditions.elementToBeClickable(By.id("cell-"+cell)));
			casillas1[cell].click();
		}
	}
	
	@Test
	void testPlayer0Wins() {
		pulsar(0,2);
		pulsar(1,7);
		pulsar(0,4);
		pulsar(1,6);
		pulsar(0,8);
		pulsar(1,0);
		pulsar(0,5);
		assertEquals(d0.switchTo().alert().getText(),"Amanda wins! Luis looses.");
		assertEquals(d1.switchTo().alert().getText(),"Amanda wins! Luis looses.");
	}
	
	@Test
	void testPlayer0Looses() {
		casillas0[7].click();
		casillas1[2].click();
		casillas0[8].click();
		casillas1[6].click();
		casillas0[0].click();
		casillas1[4].click();
		assertEquals(d0.switchTo().alert().getText(),"Luis wins! Amanda looses.");
		assertEquals(d1.switchTo().alert().getText(),"Luis wins! Amanda looses.");
	}
	
	@Test
	void testDraw() {
		casillas0[4].click();
		casillas1[2].click();
		casillas0[8].click();
		casillas1[0].click();
		casillas0[1].click();
		casillas1[7].click();
		casillas0[3].click();
		casillas1[5].click();
		casillas0[6].click();
		assertEquals(d0.switchTo().alert().getText(),"Draw!");
		assertEquals(d1.switchTo().alert().getText(),"Draw!");
	}

}
