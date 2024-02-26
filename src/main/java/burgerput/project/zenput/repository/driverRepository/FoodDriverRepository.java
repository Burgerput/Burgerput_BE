package burgerput.project.zenput.repository.driverRepository;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodDriverRepository {
    public WebDriver getDriver();

    public void setDriver(WebDriver driver);
}
