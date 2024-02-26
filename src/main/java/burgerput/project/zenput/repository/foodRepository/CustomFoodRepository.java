package burgerput.project.zenput.repository.foodRepository;

import burgerput.project.zenput.domain.CustomFood;
import org.checkerframework.checker.units.qual.min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomFoodRepository extends JpaRepository<CustomFood, Integer> {
    @Modifying(clearAutomatically = true)
    @Query(value = "truncate table Custom_food", nativeQuery = true)
    public void deleteAllMine();


    @Modifying(clearAutomatically = true)
    @Query(value = "delete from custom_food where id = :id", nativeQuery = true)
    public void deleteBymineId(@Param("id") String id);


    @Modifying(clearAutomatically = true)
    @Query(value = "update custom_food set min = :min, max = :max where id = :id", nativeQuery = true)
    public void updateMy(@Param("id")int id, @Param("min") int min, @Param("max") int max);

}
