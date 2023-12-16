package com.pruebatecnicamediline.peliculaservice.service;

import com.pruebatecnicamediline.peliculaservice.Model.Movie;
import com.pruebatecnicamediline.peliculaservice.Model.MovieGenre;
import com.pruebatecnicamediline.peliculaservice.dto.MovieDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final Map<Long, Movie> movieList = new HashMap<>();
    private long ultimoId = 2;
    @PostConstruct
    public void  init(){
        movieList.put(0L,new Movie(0L,"The Lord of the Rings", MovieGenre.fantasy,4.5f,1));
        movieList.put(1L,new Movie(1L,"Pulp Fiction", MovieGenre.action,3.2f,0));
    }
    public  Movie PostMovie(Movie movie) throws  Exception{
        try{
            movie.setId(this.ultimoId);
            this.ultimoId += 1;

            return  this.movieList.put(movie.getId(),movie);
        }catch (Exception e){
            throw  new Exception("Error:" +e.getMessage());
        }

    }

    public  List<Movie> GetAllMovie(){
        return new ArrayList<>(this.movieList.values());
    }

    public List<MovieDTO> GetMovieList(List<Long> IdList) throws Exception{
        try{
            List<MovieDTO> movieList = new ArrayList<>();
            for (Long id: IdList) {
                Movie mFound = GetOneMovie(id);
                if (mFound != null) movieList.add(new MovieDTO(mFound.getTitle(),mFound.getGenre(),mFound.getId()));
            }
            return  movieList;
        }catch (Exception e){
            throw new Exception("Error: " + e.getMessage());
        }

    }

    public void DeleteFav(List<Long> IdList) throws Exception{
        try{
            for (Long id : IdList){
                Movie movie = GetOneMovie(id);
                if (movie != null) movie.setFavoriteAmount(movie.getFavoriteAmount() - 1);
            }
        }catch (Exception e){
            throw new Exception("Error: " + e.getMessage());
        }
    }

    public void AddFav(List<Long> IdList) throws Exception{
        try{
            for (Long id : IdList){
                Movie movie = GetOneMovie(id);
                if (movie != null) movie.setFavoriteAmount(movie.getFavoriteAmount() + 1);
            }
        }catch (Exception e){
            throw new Exception("Error: " + e.getMessage());
        }
    }

    public  Movie GetOneMovie(long id) throws Exception{
        try {
            Movie movieFound = this.movieList.get(id);
            return movieFound;
        }catch (Exception e){
            throw new Exception("Error: " + e.getMessage());
        }
    }
    public Movie DeleteMovie(Long id) throws Exception {
        try{

           return movieList.remove(id);
        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }

    }

    public Movie PutMovie(Long id, Movie movie) throws Exception  {
        try{
            return movieList.put(id,movie);

        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }

    }
}
