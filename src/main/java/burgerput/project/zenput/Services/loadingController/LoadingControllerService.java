package burgerput.project.zenput.Services.loadingController;

import java.util.List;
import java.util.Map;

public interface LoadingControllerService {
    public Map<String,String> loading();
    public List<Map<String,Object>> readLoadingResult();

}
