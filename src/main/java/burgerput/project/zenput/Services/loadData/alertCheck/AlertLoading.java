package burgerput.project.zenput.Services.loadData.alertCheck;

import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;

import java.util.ArrayList;
import java.util.Map;

public interface AlertLoading {
    public ArrayList<Map> editMachine(Map<Integer, Machine> zenputMachineData);
    public ArrayList<Map> editFood(Map<Integer, Food> zenputFoodData);

    public ArrayList<Map> addMachine(Map<Integer, Machine> zenputMachineData);
    public ArrayList<Map> addFood(Map<Integer, Food> zenputFoodData);

    public ArrayList<Map> delMachine(Map<Integer, Machine> zenputMachineData);
    public ArrayList<Map> delFood(Map<Integer, Food> zenputFoodData);


}
