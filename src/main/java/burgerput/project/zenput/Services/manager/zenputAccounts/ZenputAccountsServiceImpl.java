package burgerput.project.zenput.Services.manager.zenputAccounts;

import burgerput.project.zenput.domain.Accounts;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ZenputAccountsServiceImpl implements ZenputAccountsService{

    private final ZenputAccountRepository zenputAccountRepository;

    @Override
    public Map<String, String> showZenputAccounts() {
        //show MachineList from the Machine DB
        //[id, JSON(MAP)] 으로 리턴
        List<Accounts> accounts = zenputAccountRepository.findAll();

        Map<String, String> result = new HashMap<>();

        for (Accounts account : accounts) {
            result.put("zenputId", account.getZenputId());
            result.put("rbiId", account.getRbiId());
            result.put("rbiPw", account.getRbiPw());
        }

        return result;
    }

    @Override
    public void saveZenputAccounts(Map<String, String> param) {
        Accounts account = new Accounts();

        try {
            Accounts byZenputId = zenputAccountRepository.findByZenputId(param.get("zenputId"));

            //not empty!
            byZenputId.setRbiId(param.get("rbiId"));
            byZenputId.setRbiPw(param.get("rbiPw"));

            zenputAccountRepository.save(byZenputId);

        } catch (NullPointerException e) {
            //empty!
            account.setZenputId(param.get("zenputId"));
            account.setRbiId(param.get("rbiId"));
            account.setRbiPw(param.get("rbiPw"));

            zenputAccountRepository.save(account);
        }
    }
}
