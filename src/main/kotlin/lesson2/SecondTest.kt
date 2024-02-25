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


class SecondTest() {
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
    fun testSearchValue(){
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']"),
            errorMessages = "Cannot find element Skip",
            5
        )
        waitForElementAndClick(
            By.xpath( "//*[contains(@text,'Search Wikipedia')]"),
            errorMessages = "Cannot find element Search Wikipedia",
            5
        )
        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            value = "Kotlin",
            errorMessages = "Cannot find input Kotlin",
            10
        )
        waitForElementNotPresentBy(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description'" +
                    " and @text='Russian island in the Gulf of Finland']"),
            "Cannot find text 'Russian island in the Gulf of Finland'",
            5
        )
        println("Second test run")
    }
    @Test
    fun testPresentCanselButton() {
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']"),
            errorMessages = "Cannot find element Skip",
            5
        )
        val waitForElementAndClick = waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            errorMessages = "Cannot find element Search Wikipedia",
            5
        )
        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            value = "Kotlin",
            errorMessages = "Cannot find input Kotlin",
            10
        )
        waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            errorMessages = "Cannot find button Cansel",
            5
        )
        waitForElementNotPresentBy(
            By.id("org.wikipedia:id/search_close_btn"),
            errorMessages = "X is still on the screen",
            5
        )
        println("Second test run")
    }
    @Test
    fun testPresentClear() {
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']"),
            errorMessages = "Cannot find element Skip",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            errorMessages = "Cannot find element Search Wikipedia",
            5
        )
        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            value = "Kotlin",
            errorMessages = "Cannot find input Kotlin",
            10
        )
        waitForElementClear(
            By.id("org.wikipedia:id/search_src_text"),
            errorMessages = "Cannot find element search field",
            5
        )
        waitForElementNotPresentBy(
            By.id("org.wikipedia:id/search_close_btn"),
            errorMessages = "X is still on the screen",
            5
        )
        println("Second test run")
    }
    @Test
    fun testCompareArticleTitle(){
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/fragment_onboarding_skip_button']"),
            errorMessages = "Cannot find element Skip",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            errorMessages = "Cannot find element Search Wikipedia",
            5
        )
        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            value = "Kotlin",
            errorMessages = "Cannot find input Kotlin",
            10
        )
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description'" +
                    " and @text='Russian island in the Gulf of Finland']"),
            "Cannot find text 'Russian island in the Gulf of Finland'",
            5
        )
        val titleArticle= waitForElementPresentBy(
            By.xpath("//*[@text='Kotlin Island']"),
            "Cannot find text Kotlin Island",
            15
        )
        val articleTitle = titleArticle.getAttribute("text")
        Assert.assertEquals(
            "We see unexpected title!",
            "Kotlin Island",
            articleTitle
        )
    }
    private fun waitForElementPresentBy(
        by: By, errorMessages: String, timeoutInSeconds: Long): WebElement {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage(errorMessages + "\n")
        return wait.until(
            ExpectedConditions.presenceOfElementLocated(by))
        // Метод который даёт задержку в 10 сек,
        // если после 10 секунд элемент не найдётся выведется сообщение errorMessages
    }
    private fun waitForElementPresentBy(by: By, errorMessages: String): WebElement {
        return waitForElementPresentBy(by, errorMessages)
    }
    // Метод для перегрузки, который может работать без таймаутов

    private fun waitForElementAndClick(
        by: By, errorMessages: String, timeoutInSeconds: Long): WebElement {
        val element = waitForElementPresentBy(by,errorMessages,timeoutInSeconds)
        element.click()
        return element
    }
    // Метод который делает нажатие по элементу

    private fun waitForElementAndSendKeys(
        by: By, value: String, errorMessages: String, timeoutInSeconds: Long): WebElement {
        val element = waitForElementPresentBy(by, errorMessages, timeoutInSeconds)
        element.sendKeys(value)
        return element
        // Метод который вставляет какой то текст "value" в элемент
    }
    private fun waitForElementNotPresentBy(
        by: By, errorMessages: String, timeoutInSeconds: Long): Boolean {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage(errorMessages + "\n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }
    // Метод который проверяет отсутствие элемента на странице
    private fun waitForElementClear(
        by: By, errorMessages: String, timeoutInSeconds: Long): WebElement {
        val element = waitForElementPresentBy(by, errorMessages, timeoutInSeconds)
        element.clear()
        return element
    }
    // Метод который очищает строку ввода от текста
}