package com.pruebatecnicamediline.peliculaservice.service;

import com.pruebatecnicamediline.peliculaservice.Model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final Map<Long, Movie> movieList = new HashMap<>();
    private long ultimoId = 0;
    public  void PostMovie(Movie movie) throws  Exception{
        try{
            movie.setId(this.ultimoId);
            this.ultimoId += 1;
            this.movieList.put(movie.getId(),movie);
        }catch (Exception e){
            throw  new Exception("Error:" +e.getMessage());
        }

    }

    public  List<Movie> GetAllMovie(){
        return new ArrayList<>(this.movieList.values());
    }
    public  Movie GetOneMovie(long id) throws Exception{
        try {
            Movie movieFound = this.movieList.get(id);
            return movieFound;
        }catch (Exception e){
            throw new Exception("Error: " + e.getMessage());
        }
    }
    public void DeleteMovie(Long id) throws Exception {
        try{
            movieList.remove(id);
        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }

    }

    public void PutMovie(Long id, Movie movie) throws Exception  {
        try{
            movieList.put(id,movie);

        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }

    }
}
