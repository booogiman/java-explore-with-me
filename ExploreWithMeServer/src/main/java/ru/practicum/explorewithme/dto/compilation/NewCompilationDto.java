package ru.practicum.explorewithme.dto.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    @JsonProperty(required = true)
    @NotBlank
    private String title;
}
