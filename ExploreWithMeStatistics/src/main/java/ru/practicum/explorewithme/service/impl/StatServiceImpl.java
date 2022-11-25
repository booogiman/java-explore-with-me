package ru.practicum.explorewithme.service.impl;

import ru.practicum.explorewithme.dto.StatInputDto;
import ru.practicum.explorewithme.dto.StatOutputDto;
import ru.practicum.explorewithme.dto.mapper.StatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repository.StatRepository;
import ru.practicum.explorewithme.service.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    public void saveStat(StatInputDto statInputDto) {
        statRepository.save(StatMapper.dtoToStatHit(statInputDto));
    }

    @Override
    public List<StatOutputDto> getStat(String start, String end, String[] uris, boolean isUnique) {
        List<StatOutputDto> statOutputDtoList = new ArrayList<>();
        LocalDateTime startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (String uri : uris) {
            if (!isUnique && startDateTime.equals(endDateTime)) {
                statOutputDtoList.add(StatMapper.statHitToDto(statRepository.findAllByUri(uri)));
            } else if (isUnique && startDateTime.equals(endDateTime)) {
                statOutputDtoList.add(StatMapper.statHitToDto(statRepository.findDistinctByUriAndIpAndApp(uri)));
            } else if (!isUnique && !startDateTime.equals(endDateTime)) {
                statOutputDtoList.add(StatMapper.statHitToDto(statRepository.findAllByUriAndTimestampBetween(uri, startDateTime, endDateTime)));
            } else if (isUnique && !startDateTime.equals(endDateTime)) {
                statOutputDtoList.add(StatMapper.statHitToDto(statRepository.findDistinctByUriAndTimestampBetween(uri, startDateTime, endDateTime)));
            }
        }
        return statOutputDtoList;
    }
}
