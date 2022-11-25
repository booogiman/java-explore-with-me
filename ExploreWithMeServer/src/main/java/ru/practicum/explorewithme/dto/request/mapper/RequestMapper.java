package ru.practicum.explorewithme.dto.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto requestToDto(Request request) {
        return new ParticipationRequestDto(
                LocalDateTime.now().format(FORMATTER),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getState().toString()
        );
    }

    public static List<ParticipationRequestDto> requestToDtoList(List<Request> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            return Collections.emptyList();
        }
        return requestList.stream().map((RequestMapper::requestToDto)).collect(Collectors.toList());
    }
}
