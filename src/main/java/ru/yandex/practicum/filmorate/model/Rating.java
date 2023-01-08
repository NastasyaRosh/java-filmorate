package ru.yandex.practicum.filmorate.model;

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
}
