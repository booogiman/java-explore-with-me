package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "Compilation")
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "is_pinned")
    private boolean isPinned;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compilations_events", joinColumns = {@JoinColumn(name = "compilation_id")}, inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private Set<Event> events = new HashSet<>();
}
