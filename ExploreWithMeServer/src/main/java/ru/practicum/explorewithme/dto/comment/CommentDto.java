package ru.practicum.explorewithme.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    @JsonIgnore
    private Integer id;
    @Size(max = 500)
    @NotBlank
    private String content;
    private String authorName;
    private String created;
}
