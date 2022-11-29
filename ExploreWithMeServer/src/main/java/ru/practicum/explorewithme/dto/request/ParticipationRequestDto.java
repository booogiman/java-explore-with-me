package ru.practicum.explorewithme.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    private String created;
    private Integer event;
    private Integer id;
    private Integer requester;
    private String status;
}
