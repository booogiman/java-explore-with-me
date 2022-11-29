package ru.practicum.explorewithme.controller.exceptionHandling.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
