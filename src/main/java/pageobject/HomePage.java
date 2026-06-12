package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ==================== ЭЛЕМЕНТЫ ГЛАВНОЙ СТРАНИЦЫ ====================
    //Кнопка «Заказать» в верхней части страницы (в шапке)
    private final By orderButtonTop = By.xpath("//button[@class='Button_Button__ra12g']");
    // Кнопка «Заказать» в нижней части страницы (в футере)
    private final By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_UltraBig__UU3Lp')]");
    // Кнопка согласия на cookie-баннер
    private final By cookieAcceptButton = By.id("rcc-confirm-button");
    // Заголовки вопросов в разделе «Вопросы о важном» (аккордеон)
    private final By accordionHeaders = By.cssSelector(".accordion__button");
    // Панели с ответами в разделе «Вопросы о важном»
    private final By accordionPanels = By.cssSelector(".accordion__panel");



    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ==================== МЕТОДЫ ДЛЯ РАБОТЫ С COOKIE ====================
    public void acceptCookies() {
        List<WebElement> cookies = driver.findElements(cookieAcceptButton);
        if (!cookies.isEmpty()) {
            cookies.get(0).click();
        }
    }

    // ==================== МЕТОДЫ ДЛЯ АККОРДЕОНА ====================
    public void clickAccordionHeader(int questionIndex) {
        List<WebElement> headers = driver.findElements(accordionHeaders);
        wait.until(ExpectedConditions.elementToBeClickable(headers.get(questionIndex))).click();
    }

    public boolean isAccordionPanelDisplayed(int questionIndex) {
        List<WebElement> panels = driver.findElements(accordionPanels);
        return panels.get(questionIndex).isDisplayed();
    }

    public String getAccordionPanelText(int questionIndex) {
        List<WebElement> panels = driver.findElements(accordionPanels);
        return panels.get(questionIndex).getText();
    }

    // ==================== КНОПКИ "ЗАКАЗАТЬ" ====================
    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop)).click();
    }

    public void clickOrderButtonBottom() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom)).click();
    }
}