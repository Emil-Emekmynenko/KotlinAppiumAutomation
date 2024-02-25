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

class TestHomework1 {
    private lateinit var driver: AndroidDriver<MobileElement>

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "Pixel-3")
        capabilities.setCapability("platformVersion", "12")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appAct" +
                "ivity", ".main.MainActivity")
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
        val textSearch = waitForElementPresent(
            By.xpath("//*[@text='Search Wikipedia']"),
            "Cannot find text Search Wikipedia",
            5
        )
        val actualityText = textSearch.getAttribute("text")
        Assert.assertEquals(
            "We see unexpected title!",
            "Search Wikipedia",
            actualityText
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
}