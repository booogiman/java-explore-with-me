package ru.practicum.explorewithme.controller.exceptionHandling.exception;

public class EntryNotFoundException extends RuntimeException {

    public EntryNotFoundException(String message) {
        super(message);
    }
}
