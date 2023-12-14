package com.pruebatecnicamediline.personaservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse {
    private  long id;
    @JsonProperty("first-name")
    private  String firstName;

    @JsonProperty("last-name")
    private  String lastName;

    private Date birthdate;

    @JsonProperty("has-insurance")
    private  boolean hasInsurance;

    @JsonProperty("favourite-movies")
    private List<Object> favouriteMovies;
}
