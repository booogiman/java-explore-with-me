package ru.practicum.explorewithme.controller.exceptionHandling.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}
