package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.compilation.CompilationDto;
import ru.practicum.explorewithme.dto.compilation.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(int compilationId);

    void deleteEventFromCompilation(int compilationId, int eventId);

    void addEventToCompilation(int compilationId, int eventId);

    void unpinCompilation(int compilationId);

    void pinCompilation(int compilationId);

    List<CompilationDto> getCompilationList(boolean pinned, int from, int size);

    CompilationDto getCompilationById(int compilationId);
}
