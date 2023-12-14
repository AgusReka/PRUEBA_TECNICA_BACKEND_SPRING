package com.pruebatecnicamediline.personaservice.controller;

import com.pruebatecnicamediline.personaservice.dto.PersonResponse;
import com.pruebatecnicamediline.personaservice.model.Person;
import com.pruebatecnicamediline.personaservice.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;
    @PostMapping
    public ResponseEntity<String> CrearPersona(@RequestBody Person personDTO){
        try{
            personaService.PostPerson(personDTO);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se creo");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<PersonResponse>> ObtenerTodasPersonas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.GetAllPeople());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> EliminarPersona(@PathVariable Long id) {
        try{
            personaService.DeletePerson(id);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se borro");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> EditarPersona(@PathVariable Long id,@RequestBody Person personDTO) {
        try{
            personaService.PutPerson(id, personDTO);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se edito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
