package lesson1

import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL


class FirstTest() {
    private var driver: AndroidDriver<MobileElement>? = null


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
        driver?.quit()
    }
    @Test
    fun firstTest(){
        println("First test run")
    }
}