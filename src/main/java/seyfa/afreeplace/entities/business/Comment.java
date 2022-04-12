package seyfa.afreeplace.entities.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Comment")
@Table(name = "Comment")
public class Comment {


    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comm_id")
    private int id;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;



    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tr_id")
    private Trade trade;

}
