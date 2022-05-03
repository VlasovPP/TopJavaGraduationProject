package ru.graduation.topjavagraduationproject.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.topjavagraduationproject.error.NotFoundException;
import ru.graduation.topjavagraduationproject.model.Restaurant;
import ru.graduation.topjavagraduationproject.repository.RestaurantRepository;
import ru.graduation.topjavagraduationproject.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
@Slf4j
@Tag(name = "Restaurant Controller")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    @GetMapping(value = "/user/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll();
    }

    @GetMapping(value = "/user/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant Not Found: id=" + id));
    }

    @PostMapping(value = "/admin/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/admin/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/admin/restaurants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantRepository.deleteById(id);
    }

    @GetMapping(value = "/user/restaurants/by-name", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Restaurant> getAllByName(@RequestParam String name) {
        log.info("getAllByName {}", name);
        return restaurantRepository.findAllByNameIsContainingIgnoreCase(name);
    }
}