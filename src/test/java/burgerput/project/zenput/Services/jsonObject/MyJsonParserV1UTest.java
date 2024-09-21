package burgerput.project.zenput.Services.jsonObject;

import burgerput.project.zenput.Services.utils.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.utils.jsonObject.MyJsonParserV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
class MyJsonParserV1UTest {

    private MyJsonParser myJsonParser = new MyJsonParserV1();

    @Test
    @DisplayName("jsonTest")
    public void jsonTest(){
        String s = "[{\"name\":\"롱치킨 패티 (Long Chicken patty FULLY)\",\"id\":\"34\",\"min\":\"140\",\"code\":\"add\",\"max\":\"190\"},{\"name\":\"너겟킹 (Nugget King FULLY)\",\"diff\":\"[{min=[-100, 140]}]\",\"id\":\"351\",\"code\":\"edit\"},{\"name\":\"탄산음료 (SOFT DRINK) \",\"diff\":\"[{min=[-10, 32]}]\",\"id\":\"1168\",\"code\":\"edit\"}]";
        myJsonParser.stringToJSONArray(s);



    }

}