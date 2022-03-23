package seyfa.afreeplace.entities.business;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity(name = "Tag")
@Table(name = "Tag")
public class Tag {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tg_id")
    private int id;

    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 2, max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 2, max = 30)
    @Column(name = "firstname", length = 30, nullable = false)
    private String logoUrl;

    @Column(name = "description", length = 30)
    private String description;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

}
