package burgerput.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import burgerput.project.zenput.Config;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@Import(Config.class)
public class BurgerputProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(BurgerputProjectApplication.class, args);

	}
//	@Bean
//	public InitData testDataInit(LoadMachineData loadMachine, MachineRepositoryV1 saveMachine) {
//		return new InitData(loadMachine, saveMachine);
//	}

	//CONFIG SETTING

}

