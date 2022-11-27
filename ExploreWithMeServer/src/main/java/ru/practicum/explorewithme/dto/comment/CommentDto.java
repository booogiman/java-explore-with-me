package ru.practicum.explorewithme.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    //@JsonProperty(required = true)
    @Size(max = 255)
    @NotBlank
    private String content;
    private String authorName;
    private String created;
}
