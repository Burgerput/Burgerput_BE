package burgerput.project.zenput.repository.machineRepository;

import burgerput.project.zenput.domain.CustomMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomMachineRepository extends JpaRepository<CustomMachine, Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value = "truncate table custom_machine", nativeQuery = true)
    public void deleteAllMine();

    @Modifying(clearAutomatically = true)
    @Query(value = "update custom_machine set min = :min, max = :max where id = :id", nativeQuery = true)
    public void updateMy(@Param("id") int id, @Param("min") int min, @Param("max") int max);

}
