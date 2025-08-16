package ru.top.web.demo.controllers;

import org.springframework.web.bind.annotation.*;
import ru.top.web.demo.models.Person;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final List<Person> people = new ArrayList<>();

    public PersonController() {
        people.add(new Person("Farid", "Abdullayev", 29));
        people.add(new Person("Oleq", "Qazmanov", 74));
        people.add(new Person("Dima", "Bilan", 48));
        people.add(new Person("Alex", "Ivanov", 25));
        people.add(new Person("Masha", "Ivanova", 19));
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return people;
    }

    @GetMapping("/search")
    public List<Person> searchBySurname(@RequestParam String surname) {
        return people.stream()
                .filter(p -> p.getSurname() != null && p.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<Person> filterByAge(@RequestParam int min, @RequestParam int max) {
        int lo = Math.min(min, max);
        int hi = Math.max(min, max);
        return people.stream()
                .filter(p -> p.getAge() >= lo && p.getAge() <= hi)
                .collect(Collectors.toList());
    }

    @GetMapping("/sort")
    public List<Person> sort(@RequestParam String by,
                             @RequestParam(defaultValue = "asc") String dir) {

        Comparator<Person> cmp = switch (by) {
            case "name" -> Comparator.comparing(Person::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "surname" -> Comparator.comparing(Person::getSurname, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "age" -> Comparator.comparingInt(Person::getAge);
            default -> throw new IllegalArgumentException("by must be one of: name, surname, age");
        };

        if ("desc".equalsIgnoreCase(dir)) cmp = cmp.reversed();
        return people.stream().sorted(cmp).collect(Collectors.toList());
    }

    @PostMapping("/")
    public Boolean add(@RequestBody Person person) {
        if (person == null) return false;
        if (person.getName() == null || person.getSurname() == null || person.getAge() < 0) {
            return false;
        }
        people.add(person);
        return true;
    }

    @DeleteMapping("/deleteBySurname")
    public long deleteBySurname(@RequestParam String surname) {
        long count = people.stream()
                .filter(p -> p.getSurname() != null && p.getSurname().equalsIgnoreCase(surname))
                .count();
        people.removeIf(p -> p.getSurname() != null && p.getSurname().equalsIgnoreCase(surname));
        return count;
    }

    @GetMapping("/oldest")
    public Person oldest() {
        return people.stream()
                .max(Comparator.comparingInt(Person::getAge))
                .orElse(null);
    }

    @GetMapping("/youngest")
    public Person youngest() {
        return people.stream()
                .min(Comparator.comparingInt(Person::getAge))
                .orElse(null);
    }

    // Работает и как GET, и как POST
    @RequestMapping(value = "/get/beneficial", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Person> getBeneficial() {
        // пример логики: люди младше 30 лет
        return people.stream()
                .filter(p -> p.getAge() < 30)
                .collect(Collectors.toList());
    }
}
