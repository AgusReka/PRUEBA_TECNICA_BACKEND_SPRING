package com.pruebatecnicamediline.personaservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private  long id;
    @JsonProperty("first-name")
    private  String firstName;

    @JsonProperty("last-name")
    private  String lastName;

    private   Date birthdate;

    @JsonProperty("has-insurance")
    private  boolean hasInsurance;

    @JsonProperty("favourite-movies")
    private  List<Long> favouriteMovies;
}
