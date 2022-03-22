package seyfa.afreeplace.entities.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity(name = "Address")
@Table(name = "Address")
public class Address {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "addr_id")
    int id;

    // for google map
    @Column(name = "placeId")
    String placeId;

    @NotBlank(message = "Address must not be blank")
    @Column(name = "route", length = 70, nullable = false)
    String route;

    @NotBlank(message = "Postal Code must not be blank")
    @Column(name = "postalCode", length = 10,  nullable = false)
    String postalCode;

    @NotBlank(message = "City must not be blank")
    @Column(name = "city", length = 50,  nullable = false)
    String city;

    @NotBlank(message = "Region must not be blank")
    @Column(name = "region", length = 70,  nullable = false)
    String region;

    @NotBlank(message = "Country must not be blank")
    @Column(name = "country",  length = 20, nullable = false)
    String country;

    @Size(min = 2, max = 200)
    @Column(name = "description", length = 200)
    String description;

    @NotNull(message = "Coordinates could not be found")
    @Column(name = "latitude", nullable = false)
    double latitude;

    @NotNull(message = "Coordinates could not be found")
    @Column(name = "longitude", nullable = false)
    double longitude;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tr_id", referencedColumnName = "tr_id")
    private Trade trade;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", placeId='" + placeId + '\'' +
                ", route='" + route + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", trade=" + (trade == null ? "no trade": trade.getId()) +
                '}';
    }
}
