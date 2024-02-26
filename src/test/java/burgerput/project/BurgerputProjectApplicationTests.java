package burgerput.project;

import burgerput.project.zenput.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Slf4j
@Import(Config.class)
@ContextConfiguration(classes = BurgerputProjectApplication.class)
class BurgerputProjectApplicationTests {



}
