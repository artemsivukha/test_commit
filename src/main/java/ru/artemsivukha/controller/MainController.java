package ru.artemsivukha.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class MainController {

    private Map<Integer, String> storage;

    public MainController() {
        storage = new HashMap<>();
        storage.put(0, "nul");
        storage.put(1, "odin");
        storage.put(2, "dva");
    }

    @GetMapping
    public String getAll() {

        StringBuilder stringBuilder = new StringBuilder("[\n");
        for (int i = 0; i < storage.size(); ++i) {
            stringBuilder.append("{ ")
                    .append("id: " + i + ",\n")
                    .append("name: " + storage.get(i) + "\n")
                    .append(" }\n");
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    @GetMapping("{id}")
    public String getById(@PathVariable int id) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ")
                .append("id: " + id + ",\n")
                .append("name: " + storage.get(id) + "\n")
                .append(" }\n");
        return stringBuilder.toString();
    }

    @PostMapping
    public void create(@RequestBody String body) {

        storage.put(storage.size(), body);
    }

    @PutMapping("{id}")
    public void update(@PathVariable int id, @RequestBody String body) {
        if (storage.containsKey(id)) {
            storage.put(id, body);
        } else {
            throw new RuntimeException();
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {

//        Map<Integer, String> newStorage = new HashMap<>();
//        for (int i = 0; i < storage.size(); i++) {
//            if (i != id) {
//                if (storage.containsKey(id)) {
//                    newStorage.put(newStorage.size(), storage.get(i));
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//        }
//        storage = newStorage;
//    }

        if (storage.containsKey(id)) {
            for (int i = 0; i < storage.size(); i++) {
                if (i == id) {
                    storage.remove(id);
                }
            }
            for (int j = id; j < storage.size(); j++) {
                    String data = storage.get(j + 1);
                    storage.put(j, data);
                    storage.remove(j + 1);
            }
        } else {
            throw new RuntimeException();
        }
    }
}