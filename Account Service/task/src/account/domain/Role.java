package account.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column
    private Boolean isAdministrative;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(String code, Boolean isAdministrative) {
        this.code = code;
        this.isAdministrative = isAdministrative;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isAdministrative() {
        return isAdministrative;
    }

    public void setAdministrative(Boolean administrative) {
        isAdministrative = administrative;
    }
}
