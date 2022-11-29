package ru.practicum.explorewithme.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.dto.location.LocationDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class NewEventDto {
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private int category;
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
}
