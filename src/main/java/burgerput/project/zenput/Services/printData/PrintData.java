package burgerput.project.zenput.Services.printData;

import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PrintData {
    public ArrayList<Map> zenputMachine();

    public ArrayList<Map> zenputFood();

    public ArrayList<Map> customMachine();

    public ArrayList<Map> customFood();

    public ArrayList<Map> mgrList();

    public ArrayList<Map> customCheatMachine();

    public ArrayList<Map> customCheatFood();
}
