package ru.practicum.explorewithme.dto.compilation;

import ru.practicum.explorewithme.dto.event.EventShortDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CompilationDto {
    private Integer id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}
