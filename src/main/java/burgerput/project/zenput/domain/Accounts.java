package burgerput.project.zenput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="Accounts")
//@Table(name="Machine")
public class Accounts {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)//생성을 Db에 위임
    private int num;

    @Column(name="zenput_id")
    private  String zenputId;

    @Column(name="rbi_id")
    private  String rbiId;

    @Column(name="rbi_pw")
    private  String rbiPw;
}
