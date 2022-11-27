package ru.practicum.explorewithme.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {
    private Integer id;
    //@JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String name;
    //@JsonProperty(required = true)
    @NotNull
    @NotBlank
    @Email
    private String email;
}
