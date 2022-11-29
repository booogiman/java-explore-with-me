package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatEntry {
    private String app;
    private String uri;
    private int hits;
}
