package com.pruebatecnicamediline.peliculaservice.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Movie {
    private long id;
    private String title;
    private MovieGenre genre;
}
