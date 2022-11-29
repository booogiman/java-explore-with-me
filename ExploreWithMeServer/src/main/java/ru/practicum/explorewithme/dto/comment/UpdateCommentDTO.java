package ru.practicum.explorewithme.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class UpdateCommentDTO {
    @JsonIgnore
    @NonNull
    private Integer id;
    @Size(max = 500)
    @NotBlank
    private String content;
    private String authorID;
    private String eventID;
    private String created;
}
