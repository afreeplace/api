package seyfa.afreeplace.entities.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Rate")
@Table(name = "Rate")
public class Rate {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comm_id")
    private int id;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime editDate;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tr_id")
    private Trade trade;

}
