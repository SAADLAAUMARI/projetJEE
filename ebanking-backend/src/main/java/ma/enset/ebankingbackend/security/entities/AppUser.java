package ma.enset.ebankingbackend.security.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    @JsonProperty( access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch= FetchType.EAGER)
    private List<AppRole> role = new ArrayList<>();

}
