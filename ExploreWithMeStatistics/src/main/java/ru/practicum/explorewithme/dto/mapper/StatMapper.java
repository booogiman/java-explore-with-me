package ru.practicum.explorewithme.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.dto.StatInputDto;
import ru.practicum.explorewithme.dto.StatOutputDto;
import ru.practicum.explorewithme.model.App;
import ru.practicum.explorewithme.model.StatHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static StatHit dtoToStatHit(StatInputDto statInputDto) {
        StatHit statHit = new StatHit();
        App app = new App();
        app.setAppName(statInputDto.getApp());
        statHit.setApp(app);
        statHit.setIp(statInputDto.getIp());
        statHit.setUri(statInputDto.getUri());
        statHit.setTimestamp(LocalDateTime.parse(statInputDto.getTimestamp(), formatter));
        return statHit;
    }

    public static StatOutputDto statHitToDto(List<StatHit> statHit) {
        if (statHit.isEmpty()) {
            return new StatOutputDto();
        }
        return new StatOutputDto(
                statHit.get(0).getApp().getAppName(),
                statHit.get(0).getUri(),
                statHit.size()
        );
    }
}
