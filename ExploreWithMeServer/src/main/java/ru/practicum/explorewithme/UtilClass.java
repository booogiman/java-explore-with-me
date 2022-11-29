package ru.practicum.explorewithme;

import java.time.format.DateTimeFormatter;

public class UtilClass {

    public static DateTimeFormatter getFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
