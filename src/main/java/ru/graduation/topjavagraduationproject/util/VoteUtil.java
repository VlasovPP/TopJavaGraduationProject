package ru.graduation.topjavagraduationproject.util;

import lombok.experimental.UtilityClass;
import ru.graduation.topjavagraduationproject.error.BoundaryTimeVoteException;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class VoteUtil {
    private static final LocalDate DATE_NOW = LocalDate.now();
    private static final LocalTime BOUNDARY_TIME = LocalTime.of(11, 0);

    public static void ifChangeNotAvailable(LocalTime time, LocalDate date) {
        boolean bool = DATE_NOW.isEqual(date.plusDays(1))
                ? time.isAfter(BOUNDARY_TIME)
                : DATE_NOW.isAfter(date);
        if (bool) {
            throw new BoundaryTimeVoteException("It's too late to change your voice today");
        }
    }
}
