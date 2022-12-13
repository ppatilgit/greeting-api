package com.example.greetings;

import com.example.greetings.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

//what we need to do to run the application
//how do we add endpoints
//what kind of endpoints can we add

//NOTE
// we have added a SQL dependency to our application in order to connect to a database in the future
// rn we don't have a database, or want to try and connect to one!
//we need to add a temp annotation to ignore the dependency

@RestController
@CrossOrigin(origins  =  "http://localhost:3000")
public class GreetingController {
      private static final Random RANDOM = new Random();


    @Autowired
    GreetingRepository repository;



    //in order to create an endpoint we need
    // an annotation to detail
    //the location of the endpoint and,
    //the associated HTTP method
    //a method that runs when that endpoint is "hit"/when a request is made to that endpoint
//	@GetMapping("/welcome")
//	public String sayHi(){
//		return "Welcome to SpringBoot!";
//	}
    //path variables & request parameters
    // why would we need these?
    // pass information on GET requests, from the client (REACT) to the server(SpringBoot)

    //challenge
    //add two more endpoints
    //one for greeting a greeting by its id
    //one for saying hello back to someone

    //GET a specific greeting
    @GetMapping("/welcome/{id}")
    public ResponseEntity<String> getGreetingById(@PathVariable int id){
        // what made up my response
            //status code
            //body -our actual greeting
            //headers - additional info re the request and response
        //ResponseEntity
            //we can configure our entire response using this
                //.status() configure the status code we receive
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repository.findByid(id).toString());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id +" Doesn't exist");
        }

    }

    //looks for a query sign, an ?, and reads from after that
//	@GetMapping("/greeting")
//	public String getCustomGreeting(@RequestParam String name){
//		return "Hello "+ name;
//	}

    //challenge
    //make an endpoint that returns a List of all the greetings
    //GET all greetings
    @GetMapping("/allGreeting")
    public ResponseEntity<List<Greeting>> getAllGreeting(){
        //List<Greeting> greetingsName = greetings.stream().map(greeting->greeting.toString()).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
        //return List.of("Namaskara","Hello", "Hi", "Hola", "Bonjour", "Howdy", "Shalom", "Namaste");
    }

    //GET a random greeting
    @GetMapping("/random")
    public ResponseEntity<Greeting> getRandomGreeting(){
        List<Greeting> greetings=repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(greetings.get(RANDOM.nextInt(greetings.size())));
    }

    //POST a new greeting
    @PostMapping("/greeting")
    public ResponseEntity<String> createGreeting(@RequestBody Greeting greeting){
        //greetings.add(greeting);
//        greeting.setId(greetings.size());
//        greeting.setCreatedBy("Pavitra");
//        greeting.setDateCreated(new Timestamp(System.currentTimeMillis()));
        repository.save(greeting);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(greeting.toString()+ " added");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.toString());
        }
    }

    //PUT /Update existing data
    @PutMapping("/greetings/{id}")
    public String updateGreeting(@PathVariable int id, @RequestBody Greeting newGreeting){
        Greeting curGreeting = repository.findByid(id);
        if(newGreeting.getCreatedBy() != null){
            curGreeting.setCreatedBy(newGreeting.getCreatedBy());
        }
        if(newGreeting.getGreeting()!=null){
            curGreeting.setGreeting(newGreeting.getGreeting());
        }
        if(newGreeting.getOriginCountry()!=null){
            curGreeting.setOriginCountry(newGreeting.getOriginCountry());
        }
        repository.save(curGreeting);
        return "Updated greeting : " + curGreeting;
    }

    //Delete a greeting
    @DeleteMapping("/greetings/{id}")
    public String deleteGreeting(@PathVariable int id){
        repository.delete(repository.findByid(id));
        //greetings.remove(greetings.stream().filter(greeting -> greeting.getId()==id).findFirst().get());
        return "Greetings with id: "+ id + " deleted";
    }
}
