package ru.top.web.demo.controllers;

import org.springframework.web.bind.annotation.*;
import ru.top.web.demo.models.Person;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class HomeController {
    private List<Person> people;
    public HomeController() {
        people = new ArrayList<>();
        people.add(new Person("Farid","Abdullayev",29));
        people.add(new Person("Oleq","Qazmanov",74));
        people.add(new Person("Dima","Bilan",48));
    }


    @GetMapping("/hi")
    public String test(
            @RequestParam(name = "name",defaultValue = "User",required = false) String name,
            @RequestParam(name = "age") Integer age){
        System.out.println("name : " + name);
        System.out.println("age : " + age);
        return "Hello world!";
    }

//    @GetMapping("/hi/{age}/{name}/{surname}")
//    public String test2(
//          @PathVariable(name = "age") Integer age,
//          @PathVariable(name = "name") String name,
//          @PathVariable(name = "surname") String surname
//    ){
//        return "Hello world! " + age + " " + name + " " + surname;
//    }


//    @GetMapping("/hi/{id}/test/ok")
//    public String test2(
//            @PathVariable(name = "id") Integer id
//    ){
//        return "Hello world! " + id ;
//    }


    @GetMapping("/hi/{id}")
    public String test2(
            @PathVariable(name = "id") Integer id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "surname") String surname
    ){
        return "Hello world! " + id + " " + name + " " + surname;
    }


    //PathVariable                         @RequestParam

    // url посел /                         в query-aparams после ?
    // обязательно                         не обязательно
    // идентификация ресурса (id)          фильтрация , пагинация , сортировка , настройка
    // строгая последовательность          радномная
    // url может продолжаться              url не может продолжаться
    // PathVariable                        RequestParam

}
