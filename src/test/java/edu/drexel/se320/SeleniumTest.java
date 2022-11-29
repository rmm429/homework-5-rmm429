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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // .\\gradlew.bat test --tests SeleniumTest.*METHOD_NAME*

    // throws InterruptedException
    // Thread.sleep(3000);

    /*
     * Test 1a: Test Controls Revealed
     * This will test that when the [+] icon is clicked,
     * the controls to add a to-do are displayed.
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
     * said controls are then hidden.
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

    /*
     * Test 2: Test Added Item Shows Up in Right Place
     * This will test that when an item is added to a list,
     * the item will appear in the correct place in the list.
     * More specifically, this will test that newly-added items
     * are appended to the list, rather than added somewhere else.
     */
    @Test
    public void testAddedItemInRightPlace() {

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

            // Add two items to the to-do list
            for(int i = 0; i < 2; i++) {
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear(); // Clear the input box (ensures next item text will not be appended to previous text)
            }

            // Add an additional third item to the to-do list
            input.sendKeys("To-do item 3");
            addButton.click();

            // Ensure that the most recently added item shows up as the third item in the list
            WebElement li = driver.findElement(By.id("item3"));
            assertTrue(li.getText().startsWith("To-do item 3"), "Checking that the most recently added item shows up in the right place in the list");

        } finally {
            driver.quit();
        }
    }

    /*
     * Test 3a: Test Removed Items Disappears
     * This will test that when an item is removed from a list,
     * the item will no longer appear in the list.
     */
    @Test
    public void testRemovedItemDisappears() {

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

            // Add three items to the to-do list
            for(int i = 0; i < MAX_ITEMS; i++) {
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear(); // Clear the input box (ensures next item text will not be appended to previous text)
            }

            // Remove the 2nd item from the list
            WebElement todoItem = driver.findElement(By.id("item2"));
            todoItem.findElement(By.tagName("button")).click();

            // Ensure that the removed item no longer shows up in the list
            assertTrue(driver.findElements(By.id("item2")).size() == 0, "Checking that the removed item no longer shows up in list");

        } finally {
            driver.quit();
        }
    }

    /*
     * Test 3b: Test Unaffected Item ID After Removed Item
     * This will test that when an item is removed from a list,
     * the IDs of the other items in the list are unaffected.
     */
    @Test
    public void testUnaffectedItemID() {

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

            // Add three items to the to-do list
            for(int i = 0; i < MAX_ITEMS; i++) {
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear(); // Clear the input box (ensures next item text will not be appended to previous text)
            }

            // Remove the 2nd item from the list
            WebElement todoItem = driver.findElement(By.id("item2"));
            todoItem.findElement(By.tagName("button")).click();

            // Ensure that the IDs for the first and third items
            // are unaffected by the removal of the 2nd item
            // NOTE: assertTrue() is used instead of assertEquals() so that a message can be supplied
            // as a precise explanation in case one of the two assertions fail
            assertTrue(driver.findElements(By.id("item1")).size() == 1, "Checking that the first item ID is not affected by the removed item");
            assertTrue(driver.findElements(By.id("item3")).size() == 1, "Checking that the third item ID is not affected by the removed item");

        } finally {
            driver.quit();
        }
    }

    /*
     * Test 3c: Test Unaffected Item Text After Removed Item
     * This will test that when an item is removed from a list,
     * the to-do text of the other items in the list are unaffected.
     */
    @Test
    public void testUnaffectedItemText() {

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

            // Add three items to the to-do list
            for(int i = 0; i < MAX_ITEMS; i++) {
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear(); // Clear the input box (ensures next item text will not be appended to previous text)
            }

            // Remove the 2nd item from the list
            WebElement todoItem = driver.findElement(By.id("item2"));
            todoItem.findElement(By.tagName("button")).click();

            // Ensure that the to-do text for the first and third items
            // are unaffected by the removal of the 2nd item
            // NOTE: assertTrue() is used instead of assertEquals() so that a message can be supplied
            // as a precise explanation in case one of the two assertions fail
            assertTrue(driver.findElement(By.id("item1")).getText().startsWith("To-do item 1"), "Checking that the text of the first item is not affected by the removed item");
            assertTrue(driver.findElement(By.id("item3")).getText().startsWith("To-do item 3"), "Checking that the text of the third item is not affected by the removed item");

        } finally {
            driver.quit();
        }
    }

    /*
     * Test 4a: Test Add Item Button Hidden After Max Items
     * This will test that when the max number of items is added to the list,
     * the "Add to list" button will become hidden.
     */
    @Test
    public void testAddItemButtonHidden() {

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

            // Add three items to the to-do list
            for(int i = 0; i < MAX_ITEMS; i++) {
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear(); // Clear the input box (ensures next item text will not be appended to previous text)
            }

            // Ensure that "Add to list" button becomes hidden after
            // the max number of items is reached within the list
            assertFalse(driver.findElement(By.id("addbutton")).isDisplayed(), "Checking that the button to add an item becomes hidden after max items have been added");

        } finally {
            driver.quit();
        }
    }

    /*
     * Test 4b: Test Add Item Button Reappears When No Longer at Max Items
     * This will test that when the max number of items is added to the list
     * and then an item is removed, the "Add to list" button will reappear
     */
    @Test
    public void testAddItemButtonReappears() {

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

            // Add three items to the to-do list
            for(int i = 0; i < MAX_ITEMS; i++) {
                input.sendKeys("To-do item " + (i+1));
                addButton.click();
                input.clear(); // Clear the input box (ensures next item text will not be appended to previous text)
            }

            // Remove the 3rd item from the list (no longer at max items)
            WebElement todoItem = driver.findElement(By.id("item3"));
            todoItem.findElement(By.tagName("button")).click();

            // Ensure that "Add to list" button reappears after
            // the list no longer contains the max number of items
            assertTrue(driver.findElement(By.id("addbutton")).isDisplayed(), "Checking that the button to add an item reappears when the list is no longer full");

        } finally {
            driver.quit();
        }
    }

}
