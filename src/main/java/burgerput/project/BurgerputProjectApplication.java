package burgerput.project;

import burgerput.project.zenput.intercepter.CORSFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import burgerput.project.zenput.Config;
import org.springframework.web.bind.annotation.RequestMapping;

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

//	@Bean
//	public FilterRegistrationBean<CORSFilter> corsFilterRegistration() {
//		FilterRegistrationBean<CORSFilter> registrationBean = new FilterRegistrationBean<>(new CORSFilter());
//		registrationBean.setOrder(0); // 필터의 우선순위를 설정합니다.
//		return registrationBean;
//	}

}

