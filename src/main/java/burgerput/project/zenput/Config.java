package burgerput.project.zenput;

import burgerput.project.zenput.Services.jsonObject.MyJsonParser;
import burgerput.project.zenput.Services.jsonObject.MyJsonParserV1;
import burgerput.project.zenput.Services.loadData.alertCheck.AlertLoading2;
import burgerput.project.zenput.Services.loadData.alertCheck.AlertLoadingV2;
import burgerput.project.zenput.Services.loadData.zenputLoading.*;
import burgerput.project.zenput.Services.movePage.MovePageService;
import burgerput.project.zenput.Services.movePage.MovePageServiceV1;
import burgerput.project.zenput.Services.printDatafromDB.PrintData;
import burgerput.project.zenput.Services.printDatafromDB.PrintDataV2;
import burgerput.project.zenput.Services.saveData.SaveData;
import burgerput.project.zenput.Services.saveData.SaveDataV1;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class Config implements WebMvcConfigurer {

//    private final EntityManager em;
//
//    public Config(EntityManager em) {
//        this.em = em;
//    }

    //SSL cross-origin set-up ======================
//
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
//                .allowedOrigins("https://localhost:3000")
                .allowedOrigins("http://burgerput.co.kr.s3-website.ap-northeast-2.amazonaws.com/")
                .allowedOrigins("https://burgerput.co.kr/")
                .allowedMethods("GET", "POST")
                .allowedHeaders("*");
    }

    //Interceptor Settions===========================================
//    @Bean
//    public CheckSessionInterceptor checkSessionInterceptor() {
//        return new CheckSessionInterceptor();
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(checkSessionInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/*.ico", "/error", "/loading","/manifest.json", "/delCookie"
//                        , "/index.html", "/static/**", "/logo/*", "/logo192.png", "/*.json", "/data/*");
//
//    }
// load machine list from zenput page

        @Bean
    public MachineLoadingAndEnterZenput LoadMachine(MovePageService movePageService,
                                                    MyJsonParser myJsonParser,
                                                    MachineRepository machineRepository,
                                                    MachineDriverRepository machineDriverRepository) {
        return new MachineLoadingAndEnterZenputV2(movePageService,myJsonParser,machineRepository, machineDriverRepository
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
    //for memory DB Test Setting
//    @Bean
//    MemoryMachineRepository saveMachine() {
//        return new MemoryMachineRepositoryV1();
//    }

    //Save Data for the Saving data to DB
    @Bean
    SaveData saveData(
                      MachineRepository machineRepository,
                      FoodRepository foodRepository,
                      CustomFoodRepository customFoodRepository,
                      CustomMachineRepository customMachineRepository) {
        return new SaveDataV1(
                machineRepository,
                foodRepository,
                customFoodRepository,
                customMachineRepository);
    }

    //PrintData for the Printing Json Data
    @Bean
    PrintData printData(MachineRepository machineRepository,
                        CustomMachineRepository customMachineRepository,
                        FoodRepository foodRepository,
                        CustomFoodRepository customFoodRepository,
                        MgrListRepository mgrListRepository) {
        return new PrintDataV2(machineRepository,
                customMachineRepository,
                foodRepository,
                customFoodRepository, mgrListRepository);
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
