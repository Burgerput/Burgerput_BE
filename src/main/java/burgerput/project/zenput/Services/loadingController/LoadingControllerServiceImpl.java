package burgerput.project.zenput.Services.loadingController;

import burgerput.project.zenput.Const;
import burgerput.project.zenput.Services.utils.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.utils.loadData.alertCheck.AlertLoading2;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoadingControllerServiceImpl implements LoadingControllerService{

    private final AlertLoading2 alertLoading;
    private final MachineLoadingAndEnterZenput machineLoadingAndEnterZenput;
    private final FoodLoadingAndEnterZenput foodLoadingAndEnterZenput;
    private final MyJsonParser myJsonParser;

    /*
     * /loading
     * 1. getInfo를 통해서 data를 가져온다.
     * 2. alertLoading을 통해 Macihne/Food의 add,del,edit 데이터를 갖는 JsonArray를 만든다.
     * 3. JsonArray의 값을 /burgerput/loading의 경로에 jsonfile형식으로 저장한다.
     */
    @Override
    public Map<String,String> loading() {

/*      //서버에서 아침 8:35분에 요청해서 가져오는 값

        //로딩의 결과를 저장할 JSONArray 객체*/
        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("result", "true");
        JSONArray jsonArray = new JSONArray();

        try{
            //getInfo로 데이터 가져오기
            Map<Integer, Machine> infoMachine = machineLoadingAndEnterZenput.getInfo();

            //반환값을 jsonArray를 인자로 보내 저장
            alertLoading.MachineJsonMakerandDBSet(infoMachine, jsonArray);

        }catch(Exception e){
            log.info("Machine getInfo logic False");
            log.info(e.toString());
            resultMap.put("result", "false");

            return resultMap;
        }

        try{
            //getinfo로 food데이터 가져오기 시작
            Map<Integer, Food> infoFood = foodLoadingAndEnterZenput.getInfo();

            //결과값을 인자로 보낸 jsonArray에 저장
            alertLoading.FoodJsonMakerandDBSet(infoFood,jsonArray);

        }catch(Exception e){
            resultMap.put("result", "false");
            log.info("Food getInfo logic false");
            log.info(e.toString());

            return resultMap;
        }

        //최종 로딩의 결과 파일에 저장되는 값의 결과
        log.info("Loading json ArrayResult = {}", jsonArray.toString());
        alertLoading.jsonMaker(jsonArray, Const.JSONPATH);
        //로딩의 성공 여부
        log.info("result Map = {}", resultMap);
//        return resultMap;

        //로딩의 성공 여부
        return resultMap;

    }

    @Override
    public List<Map<String, Object>> readLoadingResult() {
        // JSON 파일 읽기
        String path = Const.JSONPATH;
//        String path = "C:/Users/bbubb/Desktop/Burgerput/jsonFiles/";
        Date now = Calendar.getInstance().getTime();
        // 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:50:24 KST 2022
        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 포맷팅 적용
        String formatedNow = formatter.format(now);

        String finalPath = path + formatedNow+ ".json";
        String result = "";

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            result = new String(Files.readAllBytes(Paths.get(finalPath)));

        } catch (IOException e) {
            //에러인 경우
            log.info("No file exist");
            return list;
        }
        list = myJsonParser.stringToJSONArray(result);

        log.info("result Arr  = {}", list.toString());
        return list;
    }
}
