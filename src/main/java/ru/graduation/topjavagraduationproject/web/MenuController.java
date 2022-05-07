package ru.graduation.topjavagraduationproject.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.topjavagraduationproject.error.NotFoundException;
import ru.graduation.topjavagraduationproject.model.Menu;
import ru.graduation.topjavagraduationproject.model.Restaurant;
import ru.graduation.topjavagraduationproject.repository.MenuRepository;
import ru.graduation.topjavagraduationproject.repository.RestaurantRepository;
import ru.graduation.topjavagraduationproject.to.MenuTo;
import ru.graduation.topjavagraduationproject.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = MenuController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Menu Controller")
public class MenuController {
    static final String URL = "/api";

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping(value = "/user/restaurants/{restaurantId}/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable int restaurantId) {
        log.info("get {}", restaurantId);
        return getTodayMenu(restaurantId);
    }

    @PostMapping(value = "/admin/restaurants/{restaurantId}/today", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> createTodayMenuWithLocation(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("create today's {} for restaurant {}", menuTo, restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant Not Found: id=" + restaurantId));
        Menu created = createNewMenuWithDishesToday(menuTo);
        ValidationUtil.checkNew(created);
        created.setRestaurant(restaurant);
        menuRepository.save(created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private Menu getTodayMenu(int restaurantId) {
        return getByDateMenu(restaurantId, LocalDate.now());
    }

    private Menu getByDateMenu(int restaurantId, LocalDate date) {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant Not Found: id=" + restaurantId));
        return menuRepository.findMenuByDate(restaurantId, date).orElseThrow(() -> new NotFoundException("Not found menu with date=" + date));
    }

    private Menu createNewMenuWithDishesToday(MenuTo menuTo) {
        return new Menu(LocalDate.now(), null, menuTo.getDishes());
    }

}
