package ru.graduation.topjavagraduationproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.topjavagraduationproject.VoteTestUtil;
import ru.graduation.topjavagraduationproject.model.Restaurant;
import ru.graduation.topjavagraduationproject.model.User;
import ru.graduation.topjavagraduationproject.model.Vote;
import ru.graduation.topjavagraduationproject.repository.RestaurantRepository;
import ru.graduation.topjavagraduationproject.repository.UserRepository;
import ru.graduation.topjavagraduationproject.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.topjavagraduationproject.UserTestUtil.ADMIN_MAIL;
import static ru.graduation.topjavagraduationproject.UserTestUtil.USER_MAIL;
import static ru.graduation.topjavagraduationproject.VoteTestUtil.*;

class VoteControllerTest extends AbstractControllerTest {

    static final String USER_URL = "/api/user/restaurant/vote";

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void addVoteByRestaurantId() throws Exception {
        Restaurant restaurantOne = restaurantRepository.findById(RESTAURANT1_ID).orElseThrow();
        Vote created = new Vote(LocalDate.now(), null, restaurantOne);
        perform(MockMvcRequestBuilders.post(USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonMatcher(created, VoteTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void changeVoteByRestaurantId() throws Exception {
        Restaurant restaurantOne = restaurantRepository.findById(RESTAURANT2_ID).orElseThrow();
        User user = userRepository.findById(USER2_ID).orElseThrow();
        Vote updated = new Vote(LocalDate.now(), user, restaurantOne);

        perform(MockMvcRequestBuilders.put(USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", String.valueOf(RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Vote> votes = voteRepository.findLastByUser(USER2_ID);
        Vote vote = votes.stream().findFirst().orElseThrow();
        VoteTestUtil.assertNoIdEquals(updated, vote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void isTooLateToChange() throws Exception {
        perform(MockMvcRequestBuilders.put(USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", String.valueOf(RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}