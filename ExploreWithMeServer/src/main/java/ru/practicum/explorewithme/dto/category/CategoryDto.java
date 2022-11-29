package ru.practicum.explorewithme.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CategoryDto {
    @Positive
    @NotNull
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
}
