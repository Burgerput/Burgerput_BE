package burgerput.project.zenput.Services.utils.printDatafromDB;

import java.util.ArrayList;
import java.util.Map;

/*
 * DB에서 데이터를 가져와 뿌려주는 서비스 계층
 * */

public interface PrintData {

    //모든 Machine의 데이터 출력 + ischecked
    //ischecked 판별을 위해서 customMachineRepository 함께 사용
    public ArrayList<Map> zenputMachine();

    //모든 Food의 데이터 출력 + ischecked
    // ischecked판별을 위해 CustomFoodRepository함께 사용
    public ArrayList<Map> zenputFood();


    //선택된 machine객체 출력
    // CustomMachineRepository사용
    //MachineRepository에서 id를 통해 이름 가져오기
    public ArrayList<Map> customMachine();

    //선택된 food객체 출력
    //CustomFoodRepository 사용
    //FoodRepository에서 id를 통해 이름 가져오기
    public ArrayList<Map> customFood();

    public ArrayList<Map> mgrList();

    //CustomMachineRepository에서 사용자가 지정한 온도값 가져오기
    //MachineRepository에서 init min,max값 이름 값 가져오기
    public ArrayList<Map> customCheatMachine();

    //customFoodRepository에서 사용자가 지정한 온도값 가져오기
    //FoodRepository 에서 init min,max값 가져오기
    public ArrayList<Map> customCheatFood();
}
