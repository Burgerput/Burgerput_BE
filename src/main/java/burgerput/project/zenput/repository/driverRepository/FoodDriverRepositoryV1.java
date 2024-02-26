package burgerput.project.zenput.repository.driverRepository;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
public class FoodDriverRepositoryV1 implements FoodDriverRepository{
    WebDriver driver;

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
