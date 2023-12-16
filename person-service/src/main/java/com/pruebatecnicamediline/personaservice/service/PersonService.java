package com.pruebatecnicamediline.personaservice.service;

import com.pruebatecnicamediline.personaservice.dto.PersonResponse;
import com.pruebatecnicamediline.personaservice.model.Person;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final WebClient webClient;
    private final Map<Long, Person> peopleList = new HashMap<>();
    private long lastId = 1;
    @PostConstruct
    public void  init(){
        List<Long> idMovies = new ArrayList<>();
        idMovies.add(0L);
        peopleList.put(0L,new Person(0L,2,"pablo","lamberti", new Date(),true,idMovies));
    }
    public  Person PostPerson(Person person) throws  Exception{
        try{
            //Obtenemos una lista de las diferentes propiedades de la clase Person
            Field[] fields = person.getClass().getDeclaredFields();

            for (Field field : fields) {
                // Hacer que el campo sea accesible incluso si es privado
                field.setAccessible(true);

                // Obtener el valor del campo en el objeto Person
                Object value = field.get(person);

                // Verificar si el valor no es nulo antes de crearlo
                if (!field.getName().equals("id") && value == null) {
                    // Si hay un campo nulo a excepción de id devolvemos error
                    throw  new Exception("El campo "+field.getName()+" es null");
                }
            }
            //Le asignamos un id
            person.setId(this.lastId);
            this.lastId += 1;

            //Lo guardamos en la lista
            this.peopleList.put(person.getId(),person);
            UpdateMovieFav(person.getFavouriteMovies(),true);
            return person;
        }catch (Exception e){
            throw  new Exception("Error:" +e.getMessage());
        }

    }

    public  List<PersonResponse> GetAllPeople(boolean ascendente) throws Exception {
        try{
            List<PersonResponse> personResponseList = new ArrayList<>();
            for (Person p: peopleList.values()) {
                //Clonamos el objeto p a uno uno siendo de clase PersonResponse para devolverlo
                PersonResponse personResponse = PersonResponse.builder()
                        .id(p.getId())
                        .maxFavouriteMovies(p.getMaxFavouriteMovies())
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName()).birthdate(p.getBirthdate())
                        .hasInsurance(p.getHasInsurance()).build();
                List moviesList = new ArrayList<>();


                if (!p.getFavouriteMovies().isEmpty()){
                    //Enviamos el listado de id de peliculas y
                    // guardamos la lista que retorna nuesto servicio de peliculas
                    moviesList = webClient.put()
                            .uri("http://localhost:8080/api/movies/get-list")
                            .body(BodyInserters.fromValue(p.getFavouriteMovies()))
                            .retrieve()
                            .bodyToMono(List.class)
                            .block();
                }
                personResponse.setFavouriteMovies(moviesList);
                personResponseList.add(personResponse);
            }
            //Ahora creamos un objeto Comparator para poder ordenar nuestras personas
            Comparator<PersonResponse> comparator = Comparator.comparing(PersonResponse::getLastName).thenComparing(PersonResponse::getFirstName);

            if (!ascendente) {
                //En el caso de no ser ascendente lo invertimos
                comparator = comparator.reversed();
            }
            //Lo ordenamos y lo devolvemos
            Collections.sort(personResponseList, comparator);
            return personResponseList;
        }catch (Exception e){
            throw new Exception("Error: "+ e.getMessage());
        }
    }

    public Optional<PersonResponse>  GetOnePerson(Long id) throws Exception{

        try{
            //Obtenemos la persona usando el Id
            Person personFound = peopleList.get(id);
            PersonResponse personResponse = null;
            if (personFound != null){

                //verificamos que la persona existe y creamos el objeto personResponse
                // copiando los valores de personFound
                personResponse = PersonResponse.builder()
                        .id(personFound.getId())
                        .maxFavouriteMovies(personFound.getMaxFavouriteMovies())
                        .firstName(personFound.getFirstName())
                        .lastName(personFound.getLastName()).birthdate(personFound.getBirthdate())
                        .hasInsurance(personFound.getHasInsurance()).build();

                List moviesList = new ArrayList<>();
                if (!personFound.getFavouriteMovies().isEmpty()){
                    //Enviamos el listado de id de peliculas y
                    // guardamos la lista que retorna nuesto servicio de peliculas
                    moviesList = webClient.put()
                            .uri("http://localhost:8080/api/movies/get-list")
                            .body(BodyInserters.fromValue(personFound.getFavouriteMovies()))
                            .retrieve()
                            .bodyToMono(List.class)
                            .block();
                }
                //Guardamos las peliculas en nuestro objeto personResponse y lo devolvemos
                personResponse.setFavouriteMovies(moviesList);
                return Optional.ofNullable(personResponse);
            }

            return Optional.ofNullable(personResponse);
        }catch (Exception e){
            throw new Exception("Error: "+ e.getMessage());
        }

    }

    public List<Object>  GetMoviesFromPerson(Long id) throws Exception{

        try{
            //verificamos que la persona existe y creamos el objeto personResponse
            // copiando los valores de personFound
            Person personFound = peopleList.get(id);
            List moviesList = new ArrayList<>();
            if (personFound != null){
                // Verificamos que la persona existe y creamos el objeto personResponse
                // copiando los valores de personFound
                if (!personFound.getFavouriteMovies().isEmpty()){
                    //Enviamos el listado de id de peliculas y
                    // guardamos la lista que retorna nuesto servicio de peliculas
                    moviesList = webClient.put()
                            .uri("http://localhost:8080/api/movies/get-list")
                            .body(BodyInserters.fromValue(personFound.getFavouriteMovies()))
                            .retrieve()
                            .bodyToMono(List.class)
                            .block();
                }

            }else {
                throw new Exception("No se encontro la persona");
            }
            //Devolvemos la lista de peliculas
            return moviesList;
        }catch (Exception e){
            throw new Exception("Error: "+ e.getMessage());
        }

    }

    public List<PersonResponse>  SearchByName(String name,boolean asc) throws Exception{
        try{
            //Obtenemos la lista de Personas
            List<PersonResponse> baseList = GetAllPeople(asc);

            //Y luego la filtramos usando firstName y lastName
            return  baseList.stream().filter(person -> person.getFirstName().contains(name) || person.getLastName().contains(name)).toList();
        }catch (Exception e){
            throw new Exception("Error: "+ e.getMessage());
        }

    }
    public Person DeletePerson(Long id) throws Exception {
        try{
            //Eliminamos la persona usando el Id
            Person p = peopleList.remove(id);
            UpdateMovieFav(p.getFavouriteMovies(),false);
            return p;
        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }

    }

    public void  UpdateMovieFav (List<Long> idList,boolean add){
        //Verificamos que no sea una lista vacia
        if (!idList.isEmpty()){
            //Enviamos la lista de id a nuestro servicio de peliculas para actualizar la cantidad de favorito
            webClient.put()
                    .uri("http://localhost:8080/api/movies/"+(add?"add-fav":"delete-fav"))
                    .body(BodyInserters.fromValue(idList))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }
    public Optional<PersonResponse> PutPerson(Long id, Person person) throws Exception  {
        try{

            Person personFound = peopleList.get(id);
            if (personFound == null){
                throw  new Exception("No se encontro la persona");
            }
            //verificamos que la lista no sea null
            if (person.getMaxFavouriteMovies() != null){
                if (person.getMaxFavouriteMovies() < personFound.getMaxFavouriteMovies())
                    throw  new Exception("El nuevo monto de pelicuas favoritas maximas es menor a la cantidad actual de peliculas favoritas.Cantidad de peliculas actuales: " + personFound.getFavouriteMovies().size());

            }
            if (person.getFavouriteMovies() != null){

                //Eliminamos los duplicados
                person.setFavouriteMovies(new ArrayList<>(new LinkedHashSet<>(person.getFavouriteMovies())));

                if (person.getFavouriteMovies().size() > personFound.getMaxFavouriteMovies() && person.getMaxFavouriteMovies() == null)
                    throw  new Exception("La cantidad de peliculas supera el monto maximo. Monto maximo: " + personFound.getMaxFavouriteMovies());
                if (person.getMaxFavouriteMovies() != null && person.getFavouriteMovies().size() > person.getMaxFavouriteMovies())
                    throw  new Exception("La cantidad de peliculas supera el nuevo monto maximo.Nuevo monto maximo: " + person.getMaxFavouriteMovies());




                //Creamos una lista con los elementos que no estan en la nueva lista
                List<Long> arrayToRemove = personFound.getFavouriteMovies().stream()
                        .filter(elemento -> !person.getFavouriteMovies().contains(elemento))
                        .collect(Collectors.toList());
                //Creamos una lista con los elementos nuevos de la lista
                List<Long> arrayToAdd = person.getFavouriteMovies().stream()
                        .filter(elemento -> !personFound.getFavouriteMovies().contains(elemento))
                        .collect(Collectors.toList());

                //Enviamos los id de las peliculas a actualizar
                UpdateMovieFav(arrayToAdd,true);
                UpdateMovieFav(arrayToRemove,false);


            }
            // Obtener todas las propiedades declaradas en la clase Person
            Field[] fields = person.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    // Hacer que el campo sea accesible incluso si es privado
                    field.setAccessible(true);

                    // Obtener el valor del campo en el objeto Person
                    Object value = field.get(person);

                    // Verificar si el valor no es nulo antes de copiarlo
                    if (value != null) {
                        // Copiar el valor al objeto
                        field.set(personFound, value);
                    }
                } catch (Exception e) {
                    throw  new Exception("Error: " + e.getMessage());
                }
            }

            peopleList.put(id,personFound);
            Optional<PersonResponse> personResponse = GetOnePerson(id);
            return personResponse;
        }catch (Exception e){
            throw  new Exception("Error: " + e.getMessage());
        }
    }

}
