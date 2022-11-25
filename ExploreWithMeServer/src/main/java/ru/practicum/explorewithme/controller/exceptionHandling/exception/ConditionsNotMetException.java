package ru.practicum.explorewithme.controller.exceptionHandling.exception;

public class ConditionsNotMetException extends RuntimeException {

    public ConditionsNotMetException(String message) {
        super(message);
    }
}
