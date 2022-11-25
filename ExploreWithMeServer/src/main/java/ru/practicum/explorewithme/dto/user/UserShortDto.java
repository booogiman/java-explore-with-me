package ru.practicum.explorewithme.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @id Идентификатор
 * @name* String Имя
 */
@Data
@AllArgsConstructor
public class UserShortDto {
    private Integer id;
    private String name;
}
