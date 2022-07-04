package me.study.springrestdocsrefdoc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/")
@RestController
public class IndexController {

    @GetMapping
    public void index() {
        log.info("call IndexController.index");
    }

    @GetMapping("/user")
    public Map<String, Map<String, String>> getUser() {
        log.info("call IndexController.getUser");
        return Map.of("contact", Map.of(
                "name", "Jane Doe",
                "email", "jane.dow@example.com"
        ));
    }

    @GetMapping("/book")
    public Map<String, String> getBook() {
        log.info("call IndexController.getBook");
        return Map.of(
                "title", "Pride and Prejudice",
                "author", "Jane Austen"
        );
    }

    @GetMapping("/books")
    public List<Map<String, String>> getBooks() {
        log.info("call IndexController.getBooks");
        return List.of(
                Map.of(
                        "title", "Pride and Prejudice",
                        "author", "Jane Austen"
                ),
                Map.of(
                        "title", "To Kill a Mockingbird",
                        "author", "Harper Lee"
                )
        );
    }

    @GetMapping("location")
    public Map<String, Map<String, Map<String, Double>>> getLocation() {
        log.info("call IndexController.getLocation");
        return Map.of("weather", Map.of(
                "wind", Map.of(
                        "speed", 15.3,
                        "direction", 287.0
                ),
                "temperature", Map.of(
                        "high", 21.2,
                        "low", 14.8
                )
        ));
    }

    @GetMapping("/users")
    public void getUsers(@RequestParam int page, @RequestParam int perPage) {
        log.info("call IndexController.getUsers");
        log.info("page={}, perPage={}", page, perPage);
    }

    @PostMapping("/user")
    public void postUser(@RequestParam String username) {
        log.info("call IndexController.postUser");
        log.info("username={}", username);
    }

    @GetMapping("/locations/{latitude}/{longitude}")
    public void getLocations(@PathVariable double latitude, @PathVariable double longitude) {
        log.info("call IndexController.getLocations");
        log.info("latitude={}, longitude={}", latitude, longitude);

    }
}
