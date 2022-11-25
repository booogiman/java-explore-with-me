package ru.practicum.explorewithme.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.compilation.CompilationDto;
import ru.practicum.explorewithme.service.CompilationService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam boolean pinned,
                                                @RequestParam int from,
                                                @RequestParam int size) {
        log.info("Запрошены подборки pinned={}, страница={}, размер={}", pinned, from, size);
        return compilationService.getCompilationList(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable int compId) {
        log.info("Запрошена подборка id={}", compId);
        return compilationService.getCompilationById(compId);
    }
}
