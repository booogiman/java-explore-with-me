package ru.practicum.explorewithme.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {
    private Integer id;
    @JsonProperty(required = true)
    private String name;
    @Email
    @JsonProperty(required = true)
    private String email;
}
