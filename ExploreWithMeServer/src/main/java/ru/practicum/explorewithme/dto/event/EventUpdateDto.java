package ru.practicum.explorewithme.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class EventUpdateDto implements Serializable {
    @NotNull
    @Positive
    private Integer eventId;
    @Size(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Boolean paid;
    @Size(min = 3, max = 120)
    private String title;
    private Integer participantLimit;
}
