package ru.graduation.topjavagraduationproject.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.topjavagraduationproject.RestaurantTestUtil;
import ru.graduation.topjavagraduationproject.model.Restaurant;
import ru.graduation.topjavagraduationproject.repository.RestaurantRepository;
import ru.graduation.topjavagraduationproject.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.topjavagraduationproject.RestaurantTestUtil.*;
import static ru.graduation.topjavagraduationproject.UserTestUtil.ADMIN_MAIL;
import static ru.graduation.topjavagraduationproject.UserTestUtil.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {

    static final String ADMIN_URL = "/api/admin/restaurants/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createNewRestaurant() throws Exception {
        Restaurant created = getNew();
        perform(MockMvcRequestBuilders.post(ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(created, RestaurantTestUtil::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void shouldBeForbiddenForUser() throws Exception {
        Restaurant newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateRestaurantById() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(ADMIN_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        RestaurantTestUtil.assertEquals(updated, restaurantRepository.findById(RESTAURANT1_ID).orElseThrow());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteRestaurantById() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_URL + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertFalse(restaurantRepository.findById(RESTAURANT1_ID).isPresent());
    }
}
