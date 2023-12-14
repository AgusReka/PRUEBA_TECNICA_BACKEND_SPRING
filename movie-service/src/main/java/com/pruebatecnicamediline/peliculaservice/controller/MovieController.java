package com.pruebatecnicamediline.peliculaservice.controller;

import com.pruebatecnicamediline.peliculaservice.Model.Movie;
import com.pruebatecnicamediline.peliculaservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    @PostMapping
    public ResponseEntity<String> PostMovie(@RequestBody Movie movie){
        try{
            movieService.PostMovie(movie);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se creo");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Movie>> GetAllMovies(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.GetAllMovie());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> GetOneMovie(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.GetOneMovie(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteMovie(@PathVariable Long id) {
        try{
            movieService.DeleteMovie(id);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se borro");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> PutMovie(@PathVariable Long id,@RequestBody Movie movie) {
        try{
            movieService.PutMovie(id, movie);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se edito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
