package seyfa.afreeplace.entities.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "Hours")
@Table(name = "Hours")
public class Hours {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hr_id")
    private int id;

    @Column(name = "beginHour", nullable = false)
    private LocalTime begin;

    @Column(name = "endHour", nullable = false)
    private LocalTime end;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "dy_id")
    private ScheduleDay day;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hours hours = (Hours) o;
        return Objects.equals(begin, hours.begin) && Objects.equals(end, hours.end);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
