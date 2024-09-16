package burgerput.project.zenput.Services.manager.zenputAccounts;

import java.util.Map;

public interface ZenputAccountsService {
    public Map<String, String> showZenputAccounts();

    void saveZenputAccounts(Map<String,String> param);

}
