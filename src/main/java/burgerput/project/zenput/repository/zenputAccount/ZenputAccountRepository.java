package burgerput.project.zenput.repository.zenputAccount;

import burgerput.project.zenput.domain.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZenputAccountRepository extends JpaRepository<Accounts, Integer> {

    public Accounts findByZenputId(String zenputId);
}
