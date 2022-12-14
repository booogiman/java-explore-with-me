package ru.practicum.explorewithme.controller.exceptionHandling.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * description:	 Сведения об ошибке
 *
 * @errors List[String] Список стектрейсов или описания ошибок
 * @message String Сообщение об ошибке
 * @reason String Общее описание причины ошибки
 * @status {@link HttpStatus} Код статуса HTTP-ответа
 * @timestamp String Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private String timestamp;

}
