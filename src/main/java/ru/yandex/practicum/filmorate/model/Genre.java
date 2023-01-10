package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Genre {
    private int id;

    @NotNull
    @NotBlank
    private String name;

    @Builder
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
