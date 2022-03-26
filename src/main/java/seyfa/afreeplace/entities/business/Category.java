package seyfa.afreeplace.entities.business;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@ToString
@Getter
@Setter
@Entity(name = "Category")
@Table(name = "Category")
public class Category {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cat_id")
    private int id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }

}
