package com.excilys.java.formation.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class DashboardTest {

    private static WebDriver driver;

    @BeforeAll
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/computer-database");
    }

    @AfterAll
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
