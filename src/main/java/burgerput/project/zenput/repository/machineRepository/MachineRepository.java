package burgerput.project.zenput.repository.machineRepository;

import burgerput.project.zenput.domain.Machine;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MachineRepository extends JpaRepository<Machine, Integer> {
    @Modifying(clearAutomatically = true)
    @Query(value = "truncate table Machine", nativeQuery = true)
    public void deleteAllMIne();

    @Query(value = "select * from Machine where id = :id ", nativeQuery = true)
    public Machine findMachineById(@Param("id") String id);
}