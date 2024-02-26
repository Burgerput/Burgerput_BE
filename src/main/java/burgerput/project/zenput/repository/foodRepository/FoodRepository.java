package burgerput.project.zenput.repository.foodRepository;

import burgerput.project.zenput.domain.CustomMachine;
import burgerput.project.zenput.domain.Food;
import burgerput.project.zenput.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FoodRepository extends JpaRepository<Food,Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value = "truncate table food", nativeQuery = true)
    public void deleteAllMIne();


    @Query(value = "select * from food where id = :id ", nativeQuery = true)
    public Food findFoodById(@Param("id") String id);
}


