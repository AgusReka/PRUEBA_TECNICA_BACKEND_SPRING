package com.pruebatecnicamediline.peliculaservice.dto;

import com.pruebatecnicamediline.peliculaservice.Model.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private String title;
    private MovieGenre genre;
    private Long id;

}
