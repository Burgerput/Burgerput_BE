package burgerput.project.zenput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
//Table(name="")
public class Food {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)//생성을 Db에 위임
    private Long num;

    private int id;
    private String name;
    private  int max;
    private  int min;
}
