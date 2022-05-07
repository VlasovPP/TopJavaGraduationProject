package ru.graduation.topjavagraduationproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.topjavagraduationproject.model.Menu;
import ru.graduation.topjavagraduationproject.repository.MenuRepository;
import ru.graduation.topjavagraduationproject.MenuTestUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.topjavagraduationproject.UserTestUtil.*;
import static ru.graduation.topjavagraduationproject.util.JsonUtil.writeValue;
import static ru.graduation.topjavagraduationproject.MenuTestUtil.*;

class MenuControllerTest extends AbstractControllerTest {

    static final String ADMIN_URL = "/api/admin/restaurants/";
    static final String USER_URL = "/api/user/restaurants/";

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createTodayMenuByRestaurantId() throws Exception {
        Menu newMenu = MenuTestUtil.getNew();
        perform(MockMvcRequestBuilders.post(ADMIN_URL + RESTAURANT5_ID + "/today")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(newMenu, MenuTestUtil::assertEquals));
    }
}