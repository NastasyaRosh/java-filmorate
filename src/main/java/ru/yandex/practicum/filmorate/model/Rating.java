package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Rating {
    private int id;
    @NotNull
    @NotBlank
    private String name;
    private String description;

    @Builder
    public Rating(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
