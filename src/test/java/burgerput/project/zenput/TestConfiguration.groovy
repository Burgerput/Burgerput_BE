package burgerput.project.zenput

import burgerput.project.zenput.Services.loadData.zenputLoading.FoodLoadingAndEnterZenputV2T
import burgerput.project.zenput.Services.loadData.zenputLoading.MachineLoadingAndEnterZenputV2T
import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource

@org.springframework.boot.test.context.TestConfiguration
@EnableJpaRepositories(basePackages = "burgerput.project.zenput.repository")

class TestConfiguration {
    @Bean
    public MachineLoadingAndEnterZenputV2T machineLoadingAndEnterZenput() {
        return new MachineLoadingAndEnterZenputV2T();
    }

    @Bean
    public FoodLoadingAndEnterZenputV2T foodLoadingAndEnterZenput() {
        return new FoodLoadingAndEnterZenputV2T();
    }


}
