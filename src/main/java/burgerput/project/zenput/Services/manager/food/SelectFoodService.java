package burgerput.project.zenput.Services.manager.food;

import java.util.ArrayList;
import java.util.Map;

public interface SelectFoodService {

    public ArrayList<Map> showFood();

    void saveSelected(ArrayList<Map> param);
}
