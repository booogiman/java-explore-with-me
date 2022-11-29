package ru.practicum.explorewithme.client;

import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.statistic.StatHitDto;
import ru.practicum.explorewithme.model.StatEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
public class StatisticsClient {

    protected final RestTemplate rest;


    @Autowired
    public StatisticsClient(@Value("${stat-server.url}") String statServerUrl, RestTemplateBuilder builder) {
        rest = builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(statServerUrl))
                        .build();
    }

    public void sendHit(StatHitDto statHitDto) {
        rest.postForEntity("/hit", statHitDto, Object.class);
    }

    public StatEntry getEventStat(int id, String start, String end) {
        StatEntry[] result = rest.getForObject("/stats?start=" + URLEncoder.encode(start, StandardCharsets.UTF_8) +
                "&end=" + URLEncoder.encode(end, StandardCharsets.UTF_8) +
                "&uris=" + URLEncoder.encode("/events/" + id, StandardCharsets.UTF_8), StatEntry[].class);
        if (result == null) {
            throw new EntryNotFoundException("Отсутствует статистика");
        }
        return result[0];
    }


}
