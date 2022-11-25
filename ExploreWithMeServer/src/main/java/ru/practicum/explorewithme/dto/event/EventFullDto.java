package ru.practicum.explorewithme.dto.event;

import lombok.*;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.location.LocationDto;
import ru.practicum.explorewithme.dto.user.UserShortDto;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EventFullDto extends EventShortDto {
    private String createdOn;
    private String description;
    private LocationDto location;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private List<CommentDto> comments;

    public EventFullDto(
            Integer id,
            String annotation,
            CategoryDto category,
            long confirmedRequests,
            String eventDate,
            UserShortDto initiator,
            boolean paid,
            String title,
            int views,
            String createdOn,
            String description,
            LocationDto location,
            int participantLimit,
            String publishedOn,
            boolean requestModeration,
            String state,
            List<CommentDto> comments
    ) {
        super(id, annotation, category, confirmedRequests, eventDate, initiator, paid, title, views);
        this.createdOn = createdOn;
        this.description = description;
        this.location = location;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.comments = comments;
    }
}
