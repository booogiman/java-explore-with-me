package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Location")
@Table(name = "locations")
public class Location {

    /**
     *Координаты
     *latitude - широта
     *longitude - долгота
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
}
