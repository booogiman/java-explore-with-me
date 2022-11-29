package ru.practicum.explorewithme.controller.exceptionHandling.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
