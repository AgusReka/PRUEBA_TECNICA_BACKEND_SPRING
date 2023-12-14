package com.pruebatecnicamediline.personaservice.service;

import com.pruebatecnicamediline.personaservice.dto.PersonResponse;
import com.pruebatecnicamediline.personaservice.model.Person;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.BeanUtils;

import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final WebClient webClient;
    private final List<Person> peopleList = new ArrayList<>();
    private long ultimoId = 0;
    public  void PostPerson(Person person) throws  Exception{
        try{
            person.setId(this.ultimoId);
            this.ultimoId += 1;
            this.peopleList.add(person);
        }catch (Exception e){
            throw  new Exception("Error:" +e.getMessage());
        }

    }

    public  List<PersonResponse> GetAllPeople() throws Exception {
        try{
            List<PersonResponse> personResponseList = new ArrayList<>();
            for (Person p:
                    peopleList) {
                PersonResponse personResponse = PersonResponse.builder()
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName()).birthdate(p.getBirthdate())
                        .hasInsurance(p.isHasInsurance()).build();
                List<Object> moviesList = new ArrayList<>();
                for (long _index: p.getFavouriteMovies()) {
                    Object movieFound = webClient.get()
                            .uri("http://localhost:8080/api/peliculas/"+_index)
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<Object>() {})
                            .block();
                    if (movieFound != null) moviesList.add(movieFound);;
                }
                personResponse.setFavouriteMovies(moviesList);
                personResponseList.add(personResponse);
            }
            return personResponseList;
        }catch (Exception e){
            throw new Exception("Error: "+ e.getMessage());
        }
    }

    public void DeletePerson(Long id) throws Exception {
        try{
            Map<Long, Person> mapPersonas = peopleList.stream()
                    .collect(Collectors.toMap(Person::getId, Function.identity()));
            mapPersonas.remove(id);
            this.peopleList.clear();
            this.peopleList.addAll(mapPersonas.values());
        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }

    }


    public void PutPerson(Long id, Person person) throws Exception  {
        try{
            Map<Long, Person> mapPeople = peopleList.stream()
                    .collect(Collectors.toMap(Person::getId, Function.identity()));
            Person personFound = mapPeople.get(id);

            // Obtener todas las propiedades declaradas en la clase source
            Field[] fields = person.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    // Hacer que el campo sea accesible incluso si es privado
                    field.setAccessible(true);

                    // Obtener el valor del campo en el objeto source
                    Object value = field.get(person);

                    // Verificar si el valor no es nulo antes de copiarlo
                    if (value != null) {
                        // Copiar el valor al objeto destination
                        field.set(personFound, value);
                    }
                } catch (Exception e) {
                    throw  new Exception("Error: " + e.getMessage());
                }
            }

            mapPeople.put(id,personFound);

            this.peopleList.clear();
            this.peopleList.addAll(mapPeople.values());
        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }
    }

}
