package com.pruebatecnicamediline.personaservice.controller;

import com.pruebatecnicamediline.personaservice.model.Person;
import com.pruebatecnicamediline.personaservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class PersonController {

    private final PersonService personService;
    @PostMapping

    public ResponseEntity<?> PostPerson(@RequestBody Person personDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personService.PostPerson(personDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @GetMapping
    public ResponseEntity<?> GetAllPople(@RequestParam( value = "asc") boolean asc){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personService.GetAllPeople(asc));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> GetOnePerson(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personService.GetOnePerson(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/get-movies/{id}")
    public ResponseEntity<?> GetMoviesFromPerson(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personService.GetMoviesFromPerson(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> GetOnePerson(@RequestParam( value = "name") String nombre,@RequestParam( value = "asc") boolean asc){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personService.SearchByName(nombre,asc));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeletePerson(@PathVariable Long id) {
        try{
            ;
            return ResponseEntity.status(HttpStatus.OK).body(personService.DeletePerson(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> PutPerson(@PathVariable Long id,@RequestBody Person person) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personService.PutPerson(id, person));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
