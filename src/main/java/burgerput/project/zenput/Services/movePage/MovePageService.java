package burgerput.project.zenput.Services.movePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface MovePageService {
    public WebDriver gotoList();

    public WebDriver gotoListWithLogin();

    public WebDriver clickAmFood();

    public WebDriver clickPmFood();

    public WebDriver clickAmMachine();

    public WebDriver clickPmMachine();

    public WebDriver sampleFood();

    public WebDriver sampleMachine();
}
