package ru.top.web.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.top.web.demo.models.Person;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController2 {
    private List<Person> people;

    public HomeController2() {
        people = new ArrayList<>();
        people.add(new Person("Farid", "Abdullayev", 29));
        people.add(new Person("Oleq", "Qazmanov", 74));
        people.add(new Person("Dima", "Bilan", 48));
    }


    @GetMapping("/add")
    public boolean add(
            @RequestParam(name = "name",required = true,defaultValue = "User") String name,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "age",required = false,defaultValue = "0") int age
    ) {
        return isCheck(name, surname, age);
    }

    private boolean isCheck(String name, String surname, int age) {
        if (name == null || surname == null) {
            return false;
        }
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getName().equals(name) &&
                    people.get(i).getSurname().equals(surname) &&
                    people.get(i).getAge() == age) {
                return false;
            }
        }

        people.add(new Person(name,surname,age));
        return true;
    }

    @GetMapping("/list")
    public List<Person> list() {
        return people;
    }
}
