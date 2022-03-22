package seyfa.afreeplace.entities.business;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@ToString
@Entity(name = "AdminRole")
@Table (name = "AdminRole")
public class Role implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "r_id")
    private int id;

    @Basic()
    @Column(name = "name", length = 50, unique = true)
    private String name;

    @Basic()
    @Column(name = "description", length = 500, nullable = true)
    private String description;

    ///////////////// CONSTRUCTORS /////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role adminRole = (Role) o;
        return id == adminRole.id && Objects.equals(name, adminRole.name);
    }

}
