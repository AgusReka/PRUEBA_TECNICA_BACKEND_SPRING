package com.pruebatecnicamediline.personaservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private  Long id;
    private  Integer maxFavouriteMovies;
    private  String firstName;

    private  String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date birthdate;

    private Boolean hasInsurance;

    private List<Object> favouriteMovies;
}
