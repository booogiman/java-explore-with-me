package ru.practicum.explorewithme.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.dto.location.LocationDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class NewEventDto {
    //@JsonProperty(required = true)
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    //@JsonProperty(required = true)
    @NotNull
    private int category;
    //@JsonProperty(required = true)
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;
    //@JsonProperty(required = true)
    @NotNull
    private String eventDate;
    //@JsonProperty(required = true)
    @NotNull
    private LocationDto location;
    //@JsonProperty(defaultValue = "false")
    private boolean paid;
    //@JsonProperty(defaultValue = "0")
    private int participantLimit;
    //@JsonProperty(defaultValue = "false")
    private boolean requestModeration;
    //@JsonProperty(required = true)
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
}
