package ru.practicum.explorewithme.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Schema(description = "Категория")
public class CategoryDto {
    @Positive
    @NotNull
    @Schema(description = "Идентификатор категории", example = "1")
    private Integer id;
    @NotNull
    @NotBlank
    @Schema(description = "Название категории", example = "концерты")
    private String name;
}
