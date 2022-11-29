package ru.practicum.explorewithme.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Координаты проведения события
 * lat - широта
 * lot - долгота
 **/
@Data
@AllArgsConstructor
public class LocationDto {
    private int id;
    private float lat;
    private float lon;
}
