package ru.practicum.explorewithme.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.service.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestListByUserId(@PathVariable int userId) {
        log.info("Пользователь id={} запросил список своих запросов на участие в событиях", userId);
        return requestService.getRequestsByUserId(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto postRequest(@PathVariable @Positive int userId,
                                               @RequestParam @Positive int eventId) {
        log.info("Пользователь id={} добавил запрос на участие в событии={}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive int userId,
                                                 @PathVariable @Positive int requestId) {
        log.info("Пользователь id={} отменил свой запрос на участие id={}", userId, requestId);
        return requestService.removeRequest(userId, requestId);
    }
}
