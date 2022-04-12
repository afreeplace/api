package seyfa.afreeplace.entities.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hours hours = (Hours) o;
        return Objects.equals(begin, hours.begin) && Objects.equals(end, hours.end);
    }
}
