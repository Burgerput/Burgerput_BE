package burgerput.project.zenput.Services.manager.food;

import java.util.ArrayList;
import java.util.Map;

public interface EnterFoodService {
    public Map<String, ArrayList<Map>> showFood();
    public Map<String, String> submitZenputFood(String param);
}
