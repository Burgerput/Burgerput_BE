package burgerput.project.zenput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

//parent entity
@Entity(name="Custom_food")
@Data
public class CustomFood {
    //pk
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)//생성을 Db에 위임
    private Long num;

    @Id
    private int id;

    private int min;
    private int max;
    private int indexValue;

}
