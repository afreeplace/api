package seyfa.afreeplace.entities;

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
@Entity(name = "Trade")
@Table(name = "Trade")
public class Trade {

    public enum Status {
        CREATED,
        VALIDATED
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    private int id;

    @NotBlank(message = "Email must not be blank")
    @Size(min = 2, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotBlank(message = "Phone number must not be blank")
    @Size(min = 2, max = 15)
    @Column(name = "phoneNumber", length = 15, nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 2, max = 200)
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Size(min = 2, max = 255)
    @Column(name = "websiteUrl", length = 255)
    private String websiteUrl;

    @Size(min = 2, max = 255)
    @Column(name = "logoUrl", length = 255)
    private String logoUrl;

    @Column(name = "available", length = 30)
    private boolean available;

    @Column(name = "status", nullable = false)
    private Trade.Status status;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

}
