package com.pruebatecnicamediline.peliculaservice.controller;

import com.pruebatecnicamediline.peliculaservice.Model.Movie;
import com.pruebatecnicamediline.peliculaservice.Model.MovieGenre;
import com.pruebatecnicamediline.peliculaservice.dto.MovieDTO;
import com.pruebatecnicamediline.peliculaservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<?> PostMovie(@RequestBody Movie movie){
        try{
            ;
            return ResponseEntity.status(HttpStatus.OK).body(movieService.PostMovie(movie));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }
    @GetMapping
    public ResponseEntity<?> GetAllMovies(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.GetAllMovie());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetOneMovie(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.GetOneMovie(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<?> GetMovieGenres(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(MovieGenre.values());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping("/get-list")
    public ResponseEntity<?> GetMovieList(@RequestBody List<Long> IdList){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.GetMovieList(IdList));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteMovie(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(movieService.DeleteMovie(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> PutMovie(@PathVariable Long id,@RequestBody Movie movie) {
        try{

            return ResponseEntity.status(HttpStatus.OK).body(movieService.PutMovie(id, movie));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }
    @PutMapping("/delete-fav")
    public ResponseEntity<?> DeleteFav(@RequestBody List<Long> IdList) {
        try{
            movieService.DeleteFav(IdList);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true, "message", "Se edito con exito"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }
    @PutMapping("/add-fav")
    public ResponseEntity<?> AddFav(@RequestBody List<Long> IdList) {
        try{
            movieService.AddFav(IdList);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true, "message", "Se edito con exito"));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));

        }
    }
}
