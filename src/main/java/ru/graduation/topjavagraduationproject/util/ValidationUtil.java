package ru.graduation.topjavagraduationproject.util;

import ru.graduation.topjavagraduationproject.model.BaseEntity;

public class ValidationUtil {

    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + "entity id must be null");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + "must has id=" + id);
        }
    }
}
