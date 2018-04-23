package com.excilys.java.formation.servlet;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class DashboardTest {

    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/computer-database");
    }

    @AfterClass
    public static void close() {
        driver.close();
    }

    @Test
    public void testPageTitle() {
        assertEquals("Computer Database", driver.getTitle());
    }

    @Test
    public void testGoToAdd() {
        WebElement element = driver.findElement(By.id("addComputer"));
        element.click();
        assertEquals("http://localhost:8080/computer-database/AddComputer", driver.getCurrentUrl());
    }
}
