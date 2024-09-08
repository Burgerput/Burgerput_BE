package burgerput.project.zenput.Services.cheatService;

import java.util.ArrayList;
import java.util.Map;

public interface CheatService {
    //CheatFood의 정보를 보여주는 로직
    public Map<String, ArrayList<Map>> showCheatFood();

    //cheatFood에 저장되는 서비스 로직
    public void saveCheatFood(ArrayList<Map> param);


    //CheatMachine의 정보를 보여주는 로직
    public Map<String, ArrayList<Map>> showCheatMachine();

    //cheatMachine에 save되는 서비스 로직
    public void saveCheatMachine(ArrayList<Map> param);

}
