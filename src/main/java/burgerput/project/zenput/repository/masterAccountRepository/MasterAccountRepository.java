package burgerput.project.zenput.repository.masterAccountRepository;

import burgerput.project.zenput.domain.MasterAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MasterAccountRepository extends JpaRepository<MasterAccount, String> {

}
