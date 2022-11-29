package ru.practicum.explorewithme.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    @NotBlank
    private String title;
}
