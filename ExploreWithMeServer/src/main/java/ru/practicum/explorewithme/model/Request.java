package ru.practicum.explorewithme.model;

import lombok.Data;
import ru.practicum.explorewithme.model.enumeration.RequestState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "Request")
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private RequestState state;
}
