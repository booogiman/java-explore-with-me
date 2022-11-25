package ru.practicum.explorewithme.controller.unauthorized;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.model.enumeration.EventSortValues;
import ru.practicum.explorewithme.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    /**
     * Принимает параметры для фильтрации выходного списка событий
     * @param text - текст для поиска событий
     * @param categories - в каких категориях искать
     * @param isPaid - платные события, или нет
     * @param rangeStart - начало временного периода поиска
     * @param rangeEnd - конец временного периода поиска
     * @param onlyAvailable - выдавать ли только доступные для регистрации события
     * @param sort - как сортировать (доступны варианты: EVENT_DATE - по дате; VIEWS - по количеству просмотров)
     * @param from - с какой страницы выдавать
     * @param size - количество записей на странице
     *
     * @return - возвращает список событий согласно заданным параметрам
     */
    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) int[] categories,
                                         @RequestParam(required = false) Boolean isPaid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                         @RequestParam(required = false) EventSortValues sort,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "10") int size,
                                         HttpServletRequest request) {
        log.info("Запрошен список событий по запросу {}, категории={}, платные={}, с={}, по={}, " +
                        "доступные={}, отсортированы по={}, from={}, size={}",
                text, categories, isPaid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.getEvents(request, text, categories, isPaid, rangeStart, rangeEnd, onlyAvailable,
                sort.name(), from, size);
    }

    /**
     * Возвращает найденное событие
     * @param eventId - идентификатор события для поиска
     *
     * @return - возвращает событие
     */
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable int eventId, HttpServletRequest request) {
        log.info("Запрошено событие {}", eventId);
        return eventService.getEventById(request, eventId);
    }
}
