package burgerput.project.zenput.Services.loadData.zenputLoading;

import burgerput.project.zenput.domain.Food;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.json.Json;

import java.util.Map;

public interface FoodLoadingAndEnterZenput {
    public Food extractIdTitle(WebElement field);

    public  Map<Integer, Food> getInfo();

    public void sendValue(String param); //사용하지 않음

    //adedd 20231217
    public Map<String, String> sendValueV2(String param);

}
