package ru.graduation.topjavagraduationproject.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.topjavagraduationproject.error.NotFoundException;
import ru.graduation.topjavagraduationproject.model.Restaurant;
import ru.graduation.topjavagraduationproject.model.User;
import ru.graduation.topjavagraduationproject.model.Vote;
import ru.graduation.topjavagraduationproject.repository.RestaurantRepository;
import ru.graduation.topjavagraduationproject.repository.UserRepository;
import ru.graduation.topjavagraduationproject.repository.VoteRepository;
import ru.graduation.topjavagraduationproject.util.VoteUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Vote Controller")
public class VoteController {
    static final String URL = "/api/user/restaurant/vote";

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Vote> addVote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Voting user {} for restaurant {}", authUser.id(), restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant not found: id=" + restaurantId));
        User user = userRepository.findById(authUser.id()).orElseThrow(() -> new NotFoundException("User not found"));
        Vote vote = new Vote(LocalDate.now(), null, null);
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        Vote voted = voteRepository.save(vote);
        return ResponseEntity.ok().body(voted);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void changeVote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Change vote user {} for restaurant {}", authUser.id(), restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant not found: id=" + restaurantId));
        List<Vote> votes = voteRepository.findLastByUser(authUser.id());
        Vote vote = votes.stream().findFirst().orElseThrow(() -> new NotFoundException("Vote not Found"));
        VoteUtil.ifChangeNotAvailable(LocalTime.now(), vote.getDate());
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }
}

