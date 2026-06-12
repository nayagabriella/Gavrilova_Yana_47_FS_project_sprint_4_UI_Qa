package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ==================== ЭЛЕМЕНТЫ СТРАНИЦЫ ЗАКАЗА ====================
    // -------------------- Первый экран: "Для кого самокат" --------------------
    //Поле ввода "Имя"
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    //Поле ввода "Фамилия"
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    //Поле ввода "Адрес: куда привезти заказ"
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    //Поле ввода "Станция метро"
    private final By metroInput = By.xpath("//input[@placeholder='* Станция метро']");
    //Поле ввода "Телефон"
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    //Кнопка "Далее"
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // -------------------- Второй экран: "Про аренду" --------------------
    //Поле выбора даты "Когда привезти самокат"
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    //Выпадающий список "Срок аренды"
    private final By rentalPeriodDropdown = By.xpath("//div[@class='Dropdown-placeholder']");
    //Чек-бокс "чёрный цвет самоката"
    private final By colorCheckboxBlack = By.id("black");
    //Чек-бокс "серый цвет самоката"
    private final By colorCheckboxGrey = By.id("grey");
    //Поле ввода "Комментарий для курьера"
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    //Кнопка "Заказать"
    private final By orderButton = By.xpath("//div[contains(@class, 'Order')]//button[text()='Заказать']");

    // -------------------- Модальные окна --------------------
    //Кнопка подтверждения "Да" в окне "Подтверждение заказа"
    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    //Сообщение об успешном создании заказа "Заказ оформлен"
    private final By successMessage = By.xpath("//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==================== МЕТОДЫ ДЛЯ РАБОТЫ С ФОРМАМИ ====================

    // Заполнение первой формы (Для кого самокат)
    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);

        driver.findElement(metroInput).click();
        driver.findElement(metroInput).sendKeys(metro);
        WebElement metroOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='select-search__select']//div[contains(text(), '" + metro + "')]")
        ));
        metroOption.click();

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполнение второй формы (Про аренду)
    public void fillSecondFormWithOptionalFields(String date, String period, boolean selectColor, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Про аренду')]")
        ));

        WebElement dateElement = driver.findElement(dateInput);
        dateElement.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'react-datepicker')]")));
        WebElement day = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'react-datepicker__day') and text()='" + date + "']")
        ));
        day.click();

        driver.findElement(rentalPeriodDropdown).click();
        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='Dropdown-option' and text()='" + period + "']")
        ));
        periodOption.click();

        if (selectColor) {
            driver.findElement(colorCheckboxBlack).click();
        }
        if (!comment.isEmpty()) {
            driver.findElement(commentInput).sendKeys(comment);
        }

        driver.findElement(orderButton).click();
    }

    // Подтверждение заказа в модальном окне (кнопка «Да»)
    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    // Проверка, что заказ успешно оформлен (сообщение «Заказ оформлен»)
    public boolean isOrderSuccessful() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).isDisplayed();
    }
}