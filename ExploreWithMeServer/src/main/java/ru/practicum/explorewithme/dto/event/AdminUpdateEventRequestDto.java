package ru.practicum.explorewithme.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewithme.dto.location.LocationDto;

@Data
@AllArgsConstructor
public class AdminUpdateEventRequestDto {
    private String annotation;
    private Integer category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
