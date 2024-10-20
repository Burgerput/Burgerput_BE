package burgerput.project.zenput;

import burgerput.project.zenput.Services.utils.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.utils.jsonObject.MyJsonParserV1;
import burgerput.project.zenput.Services.utils.jwtLogin.JwtTokenProvider;
import burgerput.project.zenput.Services.utils.loadData.alertCheck.AlertLoading2;
import burgerput.project.zenput.Services.utils.loadData.alertCheck.AlertLoadingV2;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.FoodLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.FoodLoadingAndEnterZenputV2;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.MachineLoadingAndEnterZenput;
import burgerput.project.zenput.Services.utils.loadData.zenputLoading.MachineLoadingAndEnterZenputV2;
import burgerput.project.zenput.Services.utils.movePage.MovePageService;
import burgerput.project.zenput.Services.utils.movePage.MovePageServiceV1;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.utils.printDatafromDB.PrintDataV2;
import burgerput.project.zenput.Services.utils.saveData.SaveData;
import burgerput.project.zenput.Services.utils.saveData.SaveDataV1;
import burgerput.project.zenput.intercepter.TokenInterceptor;
import burgerput.project.zenput.repository.driverRepository.FoodDriverRepository;
import burgerput.project.zenput.repository.driverRepository.FoodDriverRepositoryV1;
import burgerput.project.zenput.repository.driverRepository.MachineDriverRepository;
import burgerput.project.zenput.repository.driverRepository.MachineDriverRepositoryV1;
import burgerput.project.zenput.repository.foodRepository.CustomFoodRepository;
import burgerput.project.zenput.repository.foodRepository.FoodRepository;
import burgerput.project.zenput.repository.machineRepository.CustomMachineRepository;
import burgerput.project.zenput.repository.machineRepository.MachineRepository;
import burgerput.project.zenput.repository.mgrList.MgrListRepository;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class Config implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public Config(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(jwtTokenProvider))
                .addPathPatterns("/**")
                .excludePathPatterns("/signin", "/signin/**", "/refresh-token", "/refresh-token/**");

    }

// load machine list from zenput page

    @Bean
    public MachineLoadingAndEnterZenput LoadMachine(MovePageService movePageService,
                                                    MyJsonParser myJsonParser,
                                                    MachineRepository machineRepository,
                                                    MachineDriverRepository machineDriverRepository) {
        return new MachineLoadingAndEnterZenputV2(movePageService, myJsonParser, machineRepository, machineDriverRepository
        );
    }

    //load food list from zenput page
    @Bean
    public FoodLoadingAndEnterZenput LoadFood(MovePageService movePageService,
                                              MyJsonParser myJsonParser,
                                              FoodRepository foodRepository,
                                              FoodDriverRepository foodDriverRepository) {
        return new FoodLoadingAndEnterZenputV2(movePageService, myJsonParser, foodRepository, foodDriverRepository);
    }

    @Bean
    public MovePageService movePage(ZenputAccountRepository zenputAccountRepository) {
        return new MovePageServiceV1(zenputAccountRepository);
    }

    @Bean
    MyJsonParser myJsonParser() {
        return new MyJsonParserV1();
    }

    //Loading Alert
    @Bean
    AlertLoading2 alertLoading(MachineRepository machineRepository,
                               FoodRepository foodRepository,
                               CustomMachineRepository customMachineRepository,
                               CustomFoodRepository customFoodRepository,
                               SaveData saveData
    ) {

        return new AlertLoadingV2(machineRepository,
                foodRepository,
                customMachineRepository,
                customFoodRepository,
                saveData
        );
    }

    //WebDriver store
    @Bean
    FoodDriverRepository foodDriverRepository() {
        return new FoodDriverRepositoryV1();
    }

    @Bean
    MachineDriverRepository machineDriverRepository() {
        return new MachineDriverRepositoryV1();
    }


}
