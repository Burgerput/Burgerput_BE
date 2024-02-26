package burgerput.project.zenput.repository.driverRepository;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Repository;


public class MachineDriverRepositoryV1 implements MachineDriverRepository {
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
