package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Category")
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
