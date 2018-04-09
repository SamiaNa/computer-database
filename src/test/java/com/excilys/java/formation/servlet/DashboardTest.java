package com.excilys.java.formation.servlet;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DashboardTest {

    private static Logger logger = LoggerFactory.getLogger(DashboardTest.class);

    @Test
    public void testPageTitle() {
        System.setProperty("webdriver.chrome.driver", "/home/excilys/Téléchargements/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/computer-database");
        //assertEquals(driver.getTitle(), "Computer Database");
        driver.close();
    }
}
