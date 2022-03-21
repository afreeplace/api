package seyfa.afreeplace.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
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

    @Column(name = "route")
    String route;

    @Column(name = "postalCode")
    String postalCode;

    @Column(name = "city")
    String city;

    @Column(name = "country")
    String country;

    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 2, max = 200)
    @Column(name = "description", length = 200)
    String description;

}
