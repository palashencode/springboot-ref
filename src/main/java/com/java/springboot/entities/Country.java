package com.java.springboot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COUNTRIES")
public class Country {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;
}