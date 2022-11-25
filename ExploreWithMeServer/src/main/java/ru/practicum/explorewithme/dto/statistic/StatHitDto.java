package ru.practicum.explorewithme.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatHitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
