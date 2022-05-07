package ru.graduation.topjavagraduationproject.util;

import lombok.experimental.UtilityClass;
import ru.graduation.topjavagraduationproject.error.IllegalRequestDataException;
import ru.graduation.topjavagraduationproject.model.BaseEntity;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " entity id must be null");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must has id=" + id);
        }
    }
}
