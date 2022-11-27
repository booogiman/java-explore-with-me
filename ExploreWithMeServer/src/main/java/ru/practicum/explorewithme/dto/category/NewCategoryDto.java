package ru.practicum.explorewithme.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class NewCategoryDto {
    //@JsonProperty(required = true)
    @NotBlank
    private String name;
}
