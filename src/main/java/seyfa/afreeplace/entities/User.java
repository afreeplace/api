package seyfa.afreeplace.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@ToString
@Getter
@Setter
@Entity(name = "User")
@Table(name = "User")
public class User implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    private int id;

    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 30)
    @Column(name = "firstname", length = 30, nullable = false)
    private String firstname;

    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 30)
    @Column(name = "lastname", length = 30, nullable = false)
    private String lastname;

    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 100)
    @Email
    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 300, nullable = false)
    private String password;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    ///////////////// ADMIN ROLES /////////////////

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "r_id")
    )
    private List <Role> roles = new ArrayList<>();

    ///////////////// CONSTRUCTORS /////////////////

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, LocalDateTime creationDate) {
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
    }

    ///////////////// EQUALS /////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

}



