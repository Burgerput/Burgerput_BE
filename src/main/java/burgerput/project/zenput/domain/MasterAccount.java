package burgerput.project.zenput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class MasterAccount {

    @Id
    @JsonIgnore
    @Column(name="master_id")
    private String masterId;

    @Column(name="master_pw")
    private  String master;

}
