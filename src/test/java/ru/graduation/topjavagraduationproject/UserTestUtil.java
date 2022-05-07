package ru.graduation.topjavagraduationproject;


import ru.graduation.topjavagraduationproject.model.Role;
import ru.graduation.topjavagraduationproject.model.User;

import java.util.List;

public class UserTestUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.ru";

    public static User getNew() {
        return new User(null, "new@gmail.com", "New_First", "New_Last", "newpass", List.of(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "user_update@gmail.com", "User_First_Update", "User_Last_Update", "password_update", List.of(Role.USER));
    }
}
