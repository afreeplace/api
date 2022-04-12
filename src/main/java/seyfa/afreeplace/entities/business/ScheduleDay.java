package seyfa.afreeplace.entities.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "ScheduleDay")
@Table(name = "ScheduleDay")
public class ScheduleDay {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dy_id")
    private int id;

    @Column(name = "date")
    private LocalDate specificDate;

    @Column(name = "beginHour")
    private DayOfWeek dayOfWeek;

    @Column(name = "isOpened")
    private boolean isOpen;

    // only if isOpen = true
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hours> hours = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "tr_id")
    private Trade trade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDay that = (ScheduleDay) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ScheduleDay{" +
                "id=" + id +
                ", specificDate=" + specificDate +
                ", dayOfWeek=" + dayOfWeek +
                ", isOpen=" + isOpen +
                ", trade=" + trade +
                '}';
    }
}
