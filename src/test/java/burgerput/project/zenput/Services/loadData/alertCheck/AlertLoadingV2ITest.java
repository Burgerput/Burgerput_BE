package burgerput.project.zenput.Services.loadData.alertCheck;

import burgerput.project.zenput.ConstT;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenputV2T;
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenputV2T;
import burgerput.project.zenput.Services.saveData.SaveData;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

/*
* 기능 통합 테스트
* AlertLoading Integration Test
* */
@Slf4j
@SpringBootTest
//@WebMvcTest(LoadingController.class)
//@DataJpaTest //이것만 붙이며 에러 난다
class AlertLoadingV2ITest {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private SaveData saveData;

    @Autowired
    private CustomMachineRepository customMachineRepository;

    @Autowired
    private CustomFoodRepository customFoodRepository;

    @Test
//    @Transactional //Transactional을 붙이면 DB에 실제 적용되지 않는다.
    @DisplayName("unitLoadingV2")
    public void unitLoadingV2() {
/*        //서버에서 아침 8:35분에 요청해서 가져오는 값
        //로딩의 결과를 저장할 JSONArray 객체*/
        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("result", "true");
        JSONArray jsonArray = new JSONArray();

        try {
            //getInfo로 데이터 가져오기
            Map<Integer, Machine> infoMachine = machineLoadingAndEnterZenput.getInfo();

            //반환값을 jsonArray를 인자로 보내 저장
            alertLoading.MachineJsonMakerandDBSet(infoMachine, jsonArray);

        } catch (Exception e) {
            log.info("Machine getInfo logic False");
            log.info(e.toString());
            resultMap.put("result", "false");

//            return resultMap;
        }

        try {
            //getinfo로 food데이터 가져오기 시작
            Map<Integer, Food> infoFood = foodLoadingAndEnterZenput.getInfo();

            //결과값을 인자로 보낸 jsonArray에 저장
            alertLoading.FoodJsonMakerandDBSet(infoFood, jsonArray);

        } catch (Exception e) {
            resultMap.put("result", "false");
            log.info("Food getInfo logic false");
            log.info(e.toString());

//            return resultMap;
        }

        //최종 로딩의 결과 파일에 저장되는 값의 결과
        log.info("json ArrayResult = {}", jsonArray.toString());
        alertLoading.jsonMaker(jsonArray, ConstT.JSONPATH);
        //로딩의 성공 여부
        log.info("result Map = {}", resultMap);
//        return resultMap;
    }

    @Autowired //Autowired해서 위에서 주입받은 Repository들을 사용할 수 있도록 설정해야
    //에러가 나지 않는다.
    AlertLoading2 alertLoading = new AlertLoadingV2(machineRepository, foodRepository, customMachineRepository, customFoodRepository, saveData);
    private FoodLoadingAndEnterZenput foodLoadingAndEnterZenput = new FoodLoadingAndEnterZenputV2T();

    private MachineLoadingAndEnterZenputV2T machineLoadingAndEnterZenput = new MachineLoadingAndEnterZenputV2T();


}