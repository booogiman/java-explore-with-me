package ru.practicum.explorewithme.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.ConditionsNotMetException;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.dto.request.mapper.RequestMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.enumeration.EventState;
import ru.practicum.explorewithme.model.enumeration.RequestState;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.RequestService;
import ru.practicum.explorewithme.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventService eventService;
    private final UserService userService;

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(int userId, int eventId) {
        Event event = getEventOrThrow(eventId);
        User requester = getUserOrThrow(userId);
        Request newRequest = new Request();
        newRequest.setEvent(event);
        newRequest.setRequester(requester);
        newRequest.setCreatedOn(LocalDateTime.now());
        newRequest.setState(RequestState.PENDING);
        List<Request> eventRequests = event.getRequests();
        if (eventRequests.stream().anyMatch(request -> request.getRequester().getId() == userId)) {
            throw new ConditionsNotMetException("Вы уже участвуете в этом событии");
        }
        if (requester.getId() == event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Нельзя участвовать в своем же событии.");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsNotMetException("Нельзя участвовать в неопубликованном событии.");
        }
        if (event.getParticipantLimit() != 0 && eventRequests.size() >= event.getParticipantLimit()) {
            throw new ConditionsNotMetException("Лимит заявок на участие исчерпан");
        }
        if (!event.getRequestModeration()) {
            newRequest.setState(RequestState.CONFIRMED);
        }
        requestRepository.save(newRequest);
        return RequestMapper.requestToDto(newRequest);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUserId(int userId) {
        getUserOrThrow(userId);
        return RequestMapper.requestToDtoList(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto removeRequest(int userId, int requestId) {
        Request request = getRequestOrThrow(requestId);
        User user = getUserOrThrow(userId);
        if (user.getId() != request.getRequester().getId()) {
            throw new ConditionsNotMetException("Несовпадение пользователя и участника.");
        }
        requestRepository.delete(request);
        request.setState(RequestState.CANCELED);
        return RequestMapper.requestToDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getRequestForEventByUserId(int userId, int eventId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        if (user.getId() != event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Это не ваше событие");
        }
        return RequestMapper.requestToDtoList(event.getRequests());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(int userId, int eventId, int requestId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Request request = getRequestOrThrow(requestId);
        if (user.getId() != event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Это не ваше событие");
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConditionsNotMetException("Подтверждение заявок не требуется");
        }
        if (event.getParticipantLimit() == event.getRequests().size()) {
            throw new ConditionsNotMetException("Свободных мест нет");
        }
        request.setState(RequestState.CONFIRMED);
        List<Request> requestList = event.getRequests();
        long approvedCount = requestList.stream().filter(request1 -> request1.getState() == RequestState.CONFIRMED).count();
        if (event.getParticipantLimit() == approvedCount + 1) {
            for (Request request1 : requestList) {
                if (request1.getState().equals(RequestState.PENDING))
                    request1.setState(RequestState.REJECTED);
            }
        }
        return RequestMapper.requestToDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(int userId, int eventId, int requestId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Request request = getRequestOrThrow(requestId);
        if (user.getId() != event.getInitiator().getId()) {
            throw new ConditionsNotMetException("Это не ваше событие");
        }
        request.setState(RequestState.REJECTED);
        return RequestMapper.requestToDto(request);
    }

    private Request getRequestOrThrow(int requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует запрос с id: " + requestId));
    }

    private User getUserOrThrow(int userId) {
        return userService.getUserOrThrow(userId);
    }

    private Event getEventOrThrow(int eventId) {
        return eventService.getEventOrThrow(eventId);
    }
}
