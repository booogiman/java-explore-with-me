package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.StatInputDto;
import ru.practicum.explorewithme.dto.StatOutputDto;

import java.util.List;

public interface StatService {
    void saveStat(StatInputDto statInputDto);

    List<StatOutputDto> getStat(String start, String  end, String[] uris, boolean isUnique);
}
