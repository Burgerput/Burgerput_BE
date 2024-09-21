package burgerput.project.zenput.Services.utils.loadData.zenputLoading;

import burgerput.project.zenput.domain.Machine;
import org.openqa.selenium.WebElement;

import java.util.Map;

public interface MachineLoadingAndEnterZenput {
    public Machine extractIdTitle(WebElement field) throws Exception;

    public Map<Integer,Machine> getInfo() throws Exception;

    public void sendValue(String param);

    public Map<String, String> sendValueV2(String param);
}
