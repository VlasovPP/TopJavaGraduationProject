package ru.graduation.topjavagraduationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@Table(name = "dish")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "dishName", nullable = false)
    private String dishName;

    @Min(1)
    @Column(name = "dishPrice", nullable = false)
    private int dishPrice;
}
