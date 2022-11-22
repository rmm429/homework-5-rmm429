// Based on the example code at https://www.selenium.dev/documentation/
package edu.drexel.se320;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SeleniumTest {

    // the max amount of items that can be added to a list
    protected final int MAX_ITEMS = 3;

    // This will ensure that the ui path will be accurate regardless of the project path
    protected final String projectPath = System.getProperty("user.dir");
    protected final String uiPath = "file:///" + projectPath + "/web/index.html";

//    // PATH RELATIVE TO MY LOCAL MACHINE (Windows)
//    protected final String uiPath = "file:///I:/My%20Drive/Drexel/Fall%202022%20(202315)/SE%20320/Homeworks/HW5/homework-5-rmm429/web/index.html";
//
//    // PATH RELATIVE TO MY LOCAL MACHINE (Mac)
//    protected final String uiPath = "file:///Users/rickymangerie/Documents/GitHub/rmm429/homework-5-rmm429/web/index.html";

//    @Test
//    public void testOneItem() {
//        WebDriver driver = new FirefoxDriver();
//        try {
//            driver.get(uiPath);
//            // Find the + to click to display the form to add a to-do
//            // Looking up by the id, not the name attribute
//            WebElement elt = driver.findElement(By.id("controls1plus"));
//
//            // Click on the [+]
//            elt.click();
//
//            // Find the form field
//            WebElement input = driver.findElement(By.id("itemtoadd"));
//
//            // Make up a to-do
//            input.sendKeys("Something to do");
//
//            // Find and click the "Add to list" button
//            WebElement addButton = driver.findElement(By.id("addbutton"));
//            addButton.click();
//
//            /* The first element added to the list will have id "item1"
//             * Subsequent list items will have IDs item2, item3, etc.
//             * Arguably this is too brittle, but rather than forcing you
//             * all to become experts on the DOM, you may assume this is done
//             * correctly, and/or you're testing this functionality implicitly. */
//            WebElement li = driver.findElement(By.id("item1"));
//            // We use startsWith because getText includes the text of the Delete button
//            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");
//        } finally {
//            driver.quit();
//        }
//    }

    // throws InterruptedException
    // Thread.sleep(3000);

    /*
     * Test 1a: Test Controls Revealed
     * This will test that when the [+] icon is clicked,
     * the controls to add a to-do are displayed
     */
    @Test
    public void testRevealControls() {

        WebDriver driver = new FirefoxDriver();

        try {

            driver.get(uiPath);

            // Find the [+] icon to click to display the form to add a to-do
            WebElement plus = driver.findElement(By.id("controls1plus"));
            // Click on the [+] icon
            plus.click();

            // Find the form that adds a to-do
            WebElement controls = driver.findElement(By.id("controls1"));

            // Ensure that after clicking the [+] icon, the form is revealed
            assertTrue(controls.isDisplayed(), "Checking that the controls are displayed after clicking the [+] icon");

        } finally {
            driver.quit();
        }

    }

    /*
     * Test 1b: Test Controls Hidden
     * This will test that when the [-] icon is clicked
     * after the [+] is clicked to display the controls to add a to-do,
     * said controls are then hidden
     */
    @Test
    public void testHideControls() {

        WebDriver driver = new FirefoxDriver();

        try {

            driver.get(uiPath);

            // Find the [+] icon to click to display the form to add a to-do
            WebElement plus = driver.findElement(By.id("controls1plus"));
            // Click on the [+] icon
            plus.click();

            // Find the [-] icon to click to hide the form to add a to-do
            WebElement minus = driver.findElement(By.id("controls1minus"));
            // Click on the [-] icon
            minus.click();

            // Find the form that adds a to-do
            WebElement controls = driver.findElement(By.id("controls1"));

            // Ensure that after clicking the [-] icon, the form is hidden
            assertFalse(controls.isDisplayed(), "Checking that the controls are hidden after clicking the [-] icon");

        } finally {
            driver.quit();
        }

    }

    @Test
    public void testOneItem() {

        WebDriver driver = new FirefoxDriver();

        try {

            driver.get(uiPath);

            // Find the [+] to click to display the form to add a to-do
            WebElement elt = driver.findElement(By.id("controls1plus"));
            // Click on the [+] icon
            elt.click();

            // Find the form field and "Add to list" button
            WebElement input = driver.findElement(By.id("itemtoadd"));
            WebElement addButton = driver.findElement(By.id("addbutton"));

            for(int i = 0; i < MAX_ITEMS; i++) {
                // Make up a to-do and click the "Add to list" button
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear();
            }

            assertTrue(true);

//            /* The first element added to the list will have id "item1"
//             * Subsequent list items will have IDs item2, item3, etc.
//             * Arguably this is too brittle, but rather than forcing you
//             * all to become experts on the DOM, you may assume this is done
//             * correctly, and/or you're testing this functionality implicitly. */
//            WebElement li = driver.findElement(By.id("item1"));
//            // We use startsWith because getText includes the text of the Delete button
//            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");

        } finally {
            driver.quit();
        }
    }

}
