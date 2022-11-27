package ru.practicum.explorewithme.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    //@JsonProperty(required = true)
    private String created;
    //@JsonProperty(required = true)
    private Integer event;
    //@JsonProperty(required = true)
    private Integer id;
    //@JsonProperty(required = true)
    private Integer requester;
    //@JsonProperty(required = true)
    private String status;
}
