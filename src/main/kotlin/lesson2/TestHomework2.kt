package lesson2

import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

class TestHomework2 {
    private lateinit var driver: AndroidDriver<MobileElement>

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "Pixel-3")
        capabilities.setCapability("platformVersion", "12")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability(
            "app",
            "/Users/eemelyanenko/Desktop/KotlinAppiumAutomation/KotlinAppiumAutomation/apks/org.wikipedia.apk"
        )
        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }
    @After
    fun tearDown(){
        driver.quit()
    }
    @Test
    fun testSearch() {
        waitForElementAndClick(
            By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
            "Cannot find element Skip",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            errorMessage = "Cannot find element Search Wikipedia",
            5
        )

        waitForElementSandKeys(
            By.xpath("//*[@text='Search Wikipedia']"),
            "Nba",
            "Cannot find text Nba",
            5
        )
        val textNba1 = waitForElementPresent(
            By.xpath("//android.widget.TextView[@resource-id=\"org.wikipedia:id/page_list_item_description\" " +
                    "and @text=\"Men's basketball minor league\"]"),
            "Cannot find text Men's basketball minor league",
            10
        )

        val actuality1 = textNba1.getAttribute("text")

        Assert.assertEquals(
            "We see unexpected title!",
            "Men's basketball minor league",
            actuality1
        )
        val textNba2 = waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' " +
                    "and @text='Annual basketball game']"),
            "Cannot find text Men's basketball minor league",
            10
        )

        val actuality2 = textNba2.getAttribute("text")

        Assert.assertEquals(
            "We see unexpected title!",
            "Annual basketball game",
            actuality2
        )
        waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            errorMessage = "Cannot find element Search Wikipedia",
            6
        )
        waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            errorMessage = "Find element X",
            5
        )

    }
    private fun waitForElementPresent(by: By, errorMessage:String, timeoutInSeconds: Long ): WebElement {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }
    private fun waitForElementAndClick(by: By, errorMessage:String, timeoutInSeconds: Long ): WebElement {
        val element = waitForElementPresent(by,errorMessage,timeoutInSeconds)
        element.click()
        return element
    }
    private fun waitForElementSandKeys(by: By, value: String, errorMessage:String, timeoutInSeconds: Long ): WebElement {
        val element = waitForElementPresent(by,errorMessage,timeoutInSeconds)
        element.sendKeys(value)
        return element
    }
    private fun waitForElementNotPresent(by: By, errorMessage:String, timeoutInSeconds: Long): Boolean {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }
    private fun waitForElementAndClear(by: By, errorMessage:String, timeoutInSeconds: Long): WebElement {
        val element = waitForElementPresent(by,errorMessage,timeoutInSeconds)
        element.clear()
        return element
    }
}