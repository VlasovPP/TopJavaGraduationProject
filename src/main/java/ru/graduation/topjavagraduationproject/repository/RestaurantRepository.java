package ru.graduation.topjavagraduationproject.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.topjavagraduationproject.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepositoryImplementation<Restaurant, Integer> {
    List<Restaurant> findAllByNameIsContainingIgnoreCase(String name);
}