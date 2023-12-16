package com.pruebatecnicamediline.personaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private  Long id;

    private  Integer maxFavouriteMovies;

    private  String firstName;

    private  String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private  Date birthdate;

    private  Boolean hasInsurance;

    private  List<Long> favouriteMovies;
}
