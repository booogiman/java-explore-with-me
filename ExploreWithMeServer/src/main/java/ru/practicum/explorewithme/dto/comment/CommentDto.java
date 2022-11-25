package ru.practicum.explorewithme.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    @JsonProperty(required = true)
    @Size(max = 255)
    @NotBlank
    private String content;
    private String authorName;
    private String created;
}
