package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto addRequest(int userId, int eventId);

    List<ParticipationRequestDto> getRequestsByUserId(int userId);

    ParticipationRequestDto removeRequest(int userId, int requestId);

    List<ParticipationRequestDto> getRequestForEventByUserId(int userId, int eventId);

    ParticipationRequestDto confirmRequest(int userId, int eventId, int requestId);

    ParticipationRequestDto rejectRequest(int userId, int eventId, int requestId);
}
