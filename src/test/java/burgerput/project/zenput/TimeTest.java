package burgerput.project.zenput;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
public class TimeTest {
    @Test
    public void timeResult(){
        String now = LocalTime.now().toString();
        String[] subnow = now.split(".");

        log.info("mesge= {},   {}",LocalDate.now(), LocalTime.now());
        for (String s : subnow) {
            log.info(s);
        }
    }
}
