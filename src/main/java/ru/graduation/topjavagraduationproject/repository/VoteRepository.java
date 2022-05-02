package ru.graduation.topjavagraduationproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.topjavagraduationproject.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v WHERE v.date = :date AND v.user.id = :userId")
    Optional<Vote> findByDate(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.date DESC")
    List<Vote> findLastByUser(@Param("userId") int userId);
}
