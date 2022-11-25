package ru.practicum.explorewithme.controller.exceptionHandling.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
