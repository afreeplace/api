package seyfa.afreeplace.entities.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity(name = "Photo")
@Table(name = "Photo")
public class Photo {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ph_id")
    private int id;

    @Column(name = "url", length = 300, nullable = false)
    private String url;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "tr_id")
    private Trade trade;

}
