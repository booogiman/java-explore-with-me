package ru.practicum.explorewithme.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@RequiredArgsConstructor
@Entity(name = "App")
@Table(name = "applications")
public class App {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "app", nullable = false)
    private String appName;
}
