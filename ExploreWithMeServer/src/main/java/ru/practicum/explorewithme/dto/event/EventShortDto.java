package ru.practicum.explorewithme.dto.event;


import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    @Positive
    private Integer id;
    @NotNull
    @NotBlank
    private String annotation;
    @NotNull
    private CategoryDto category;
    private long confirmedRequests;
    @NotNull
    @NotBlank
    private String eventDate;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    @NotBlank
    private boolean paid;
    @NotNull
    @NotBlank
    private String title;
    private int views;
}
