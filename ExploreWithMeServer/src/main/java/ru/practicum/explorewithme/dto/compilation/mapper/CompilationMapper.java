package ru.practicum.explorewithme.dto.compilation.mapper;

import ru.practicum.explorewithme.dto.compilation.CompilationDto;
import ru.practicum.explorewithme.dto.compilation.NewCompilationDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.event.mapper.EventMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.StatEntry;

import java.util.*;

public class CompilationMapper {
    public static CompilationDto compilationToDto(Compilation compilation, Map<Integer, StatEntry> statEntryHashMap) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        Set<Event> eventList = compilation.getEvents();
        for (Event event : eventList) {
            eventShortDtoList.add(EventMapper.eventToShortDto(event, statEntryHashMap.get(event.getId())));
        }
        compilationDto.setEvents(eventShortDtoList);
        return compilationDto;
    }

    public static Compilation dtoToCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.isPinned());
        return compilation;
    }
}
