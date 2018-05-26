package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.*;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class AplicationSystemTest {
	WebDriver d0, d1;
	WebElement nick0, nick1;
	WebElement[] casillas0, casillas1;
	WebDriverWait wait0, wait1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ChromeDriverManager.getInstance().setup();
		WebApp.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		WebApp.stop();
	}

	@Before
	public void setUp() throws Exception {
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
		Thread.sleep(500);
		for (int i=0; i<9; i++) {
			casillas0[i] = d0.findElement(By.id("cell-"+i));
			casillas1[i] = d1.findElement(By.id("cell-"+i));
		}
	}

	@After
	public void tearDown() throws Exception {
		d0.quit();
		d1.quit();
	}

	void pulsar(int player, int cell) throws InterruptedException {
		Thread.sleep(500);
		if (player == 0) {
			wait0.until(ExpectedConditions.elementToBeClickable(By.id("cell-"+cell)));
			casillas0[cell].click();
		} else {
			wait1.until(ExpectedConditions.elementToBeClickable(By.id("cell-"+cell)));
			casillas1[cell].click();
		}
	}
	
	@Test
	public void testPlayer0Wins() throws InterruptedException {
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
	public void testPlayer0Looses() throws InterruptedException {
		pulsar(0,7);
		pulsar(1,2);
		pulsar(0,8);
		pulsar(1,6);
		pulsar(0,0);
		pulsar(1,4);
		assertEquals(d0.switchTo().alert().getText(),"Luis wins! Amanda looses.");
		assertEquals(d1.switchTo().alert().getText(),"Luis wins! Amanda looses.");
	}
	
	@Test
	public void testDraw() throws InterruptedException {
		pulsar(0,4);
		pulsar(1,2);
		pulsar(0,8);
		pulsar(1,0);
		pulsar(0,1);
		pulsar(1,7);
		pulsar(0,3);
		pulsar(1,5);
		pulsar(0,6);
		assertEquals(d0.switchTo().alert().getText(),"Draw!");
		assertEquals(d1.switchTo().alert().getText(),"Draw!");
	}

}
