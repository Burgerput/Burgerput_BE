package burgerput.project.zenput.web.manager.zenputAccounts;


import burgerput.project.zenput.Services.manager.zenputAccounts.ZenputAccountsService;
import burgerput.project.zenput.domain.Accounts;
import burgerput.project.zenput.repository.zenputAccount.ZenputAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ZenputAccountsController {
    private final ZenputAccountsService zenputAccountsService;

    @GetMapping("back/accounts") //관리자 목록 출력
    @ResponseBody
    public Map<String, String> showZenputAccounts() {

        return zenputAccountsService.showZenputAccounts();
    }

    @PostMapping("/back/accounts")
    @ResponseBody
    public void saveZenputAccounts(@RequestBody Map<String, String> param) {

        zenputAccountsService.saveZenputAccounts(param);
    }
}
